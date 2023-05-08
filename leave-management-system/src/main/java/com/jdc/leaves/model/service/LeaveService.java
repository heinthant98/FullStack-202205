package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.leaves.model.dto.input.LeaveForm;
import com.jdc.leaves.model.dto.output.LeaveListVO;
import com.jdc.leaves.model.dto.output.LeaveSummaryVO;

@Service
public class LeaveService {
	
	private static final String LEAVES_COUNT_SQL = """
			select count(leave_date) from leaves_day 
			where leaves_classes_id = :classId and leave_date = :target
			""";

	private static final String SELECT_PROJECTION = """
			select distinct l.classes_id classId, l.student_id studentId, l.apply_date applyDate, l.start_date startDate, l.days, l.reason, 
			c.description classInfo, c.start_date classStart, 
			t.id teacherId, ta.name teacher, sa.name student, s.phone studentPhone 
			from leaves l 
			join student s on l.student_id = s.id join account sa on s.id = sa.id 
			join classes c on l.classes_id = c.id 
			join teacher t on c.teacher_id = t.id join account ta on t.id = ta.id
			join leaves_day ld on ld.leaves_apply_date = l.apply_date and ld.leaves_classes_id = l.classes_id and ld.leaves_student_id = l.student_id 
			""";
	
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert leavesInsert;

	private SimpleJdbcInsert leavesDayInsert;
	
	@Autowired
	private ClassService classService;
	
	public LeaveService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		
		leavesInsert = new SimpleJdbcInsert(dataSource);
		leavesInsert.setTableName("leaves");
		
		leavesDayInsert = new SimpleJdbcInsert(dataSource);
		leavesDayInsert.setTableName("leaves_day");
	}

	public List<LeaveListVO> search(Optional<Integer> classId, Optional<LocalDate> from, Optional<LocalDate> to) {
		var sql = new StringBuffer(SELECT_PROJECTION);
		var params = new HashMap<String, Object>();

		sql.append(" where 1 = 1");
		
		var auth = SecurityContextHolder.getContext().getAuthentication();
		Function<String, Boolean> hasAuthority = authority -> 
								auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));
		
		if (hasAuthority.apply("Student")) {
			sql.append(" and sa.email = :name");
			params.put("name", auth.getName());
		}
								
		sql.append(classId.filter(c -> c > 0).map(c -> {
			params.put("classId", c);
			return " and l.classes_id = :classId";
		}).orElse(""));
		
		sql.append(from.map(f -> {
			params.put("from", Date.valueOf(f));
			return " and ld.leave_date >= :from";
		}).orElse(""));
		
		sql.append(to.map(t -> {
			params.put("to", Date.valueOf(t));
			return " and ld.leave_date <= :to";
		}).orElse(""));

		sql.append(" order by l.start_date, l.apply_date, sa.name");
		return template.query(sql.toString(), params, new BeanPropertyRowMapper<>(LeaveListVO.class));
	}

	@Transactional
	public void save(LeaveForm form) {
		leavesInsert.execute(Map.of(
					"apply_date", Date.valueOf(form.getApplyDate()),
					"classes_id", form.getClassId(),
					"student_id", form.getStudent(),
					"start_date", Date.valueOf(form.getStartDate()),
					"days", form.getDays(),
					"reason", form.getReason()
				));
		
		for (var param : form.leavesDayInsertParams()) {
			leavesDayInsert.execute(param);
		}
	}

	public List<LeaveSummaryVO> searchSummary(Optional<LocalDate> target) {
		var classes = classService.search(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
		
		var result = classes.stream().map(LeaveSummaryVO::new).toList();
		
		for (var vo : result) {
			vo.setLeaves(findLeavesForClass(vo.getClassId(), target.orElse(LocalDate.now())));
		}
		
		return result;
	}

	private long findLeavesForClass(int classId, LocalDate target) {
		return template.queryForObject(LEAVES_COUNT_SQL,
				Map.of("classId", classId, "target", Date.valueOf(target)), Long.class);
	}

}

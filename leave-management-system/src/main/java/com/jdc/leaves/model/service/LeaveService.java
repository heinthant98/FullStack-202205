package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
	
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert leavesInsert;

	private SimpleJdbcInsert leavesDayInsert;
	
	@Autowired
	private ClassService classService;
	
	public LeaveService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		
		leavesInsert = new SimpleJdbcInsert(dataSource);
		leavesInsert.setTableName("leaves");
		leavesInsert.setColumnNames(List.of(
				"apply_date", "classes_id", "student_id", "start_date", "days", "reason"
				));
		
		leavesDayInsert = new SimpleJdbcInsert(dataSource);
		leavesDayInsert.setTableName("leaves_day");
		leavesDayInsert.setColumnNames(List.of(
				"leave_date", "leaves_apply_date", "leaves_classes_id", "leaves_student_id"
				));
		
	}

	public List<LeaveListVO> search(Optional<Integer> classId, Optional<LocalDate> from, Optional<LocalDate> to) {
		// TODO implement here
		return List.of();
	}

	@Transactional
	public void save(LeaveForm form) {
		leavesInsert.execute(form.leavesInsertParams());
		
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

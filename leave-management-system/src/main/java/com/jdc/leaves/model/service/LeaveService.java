package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
	
	@Autowired
	private ClassService classService;
	
	public LeaveService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<LeaveListVO> search(Optional<Integer> classId, Optional<String> studentName, Optional<LocalDate> from,
			Optional<LocalDate> to) {
		// TODO implement here
		return List.of();
	}

	public LeaveForm findById(int id) {
		// TODO implement here
		return null;
	}

	@Transactional
	public int save(LeaveForm form) {
		// TODO implement here
		return 0;
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

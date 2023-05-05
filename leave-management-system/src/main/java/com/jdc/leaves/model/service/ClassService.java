package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.leaves.model.dto.input.ClassForm;
import com.jdc.leaves.model.dto.output.ClassDetailsVO;
import com.jdc.leaves.model.dto.output.ClassListVO;

@Service
public class ClassService {
	
	private static final String SELECT_PROJECTION = """
		select c.id id, t.id teacherId, a.name teacherName, t.phone teacherPhone, c.start_date startDate, c.months, c.description, count(r.classes_id) studentCount 
		from classes c join teacher t on t.id = c.teacher_id 
		join account a on a.id = t.id 
		left join registration r on c.id = r.classes_id
		""";

	private static final String SELECT_GROUPBY = " group by c.id, t.id, a.name, t.phone, c.start_date, c.months, c.description";
	
	@Autowired
	private RegistrationService regService;
	@Autowired
	private LeaveService leaveService;
	
	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert insert;
	
	public ClassService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		insert = new SimpleJdbcInsert(dataSource);
		
		insert.setTableName("classes");
		insert.setGeneratedKeyName("id");
		insert.setColumnNames(List.of("teacher_id", "start_date", "months", "description"));
	}

	public List<ClassListVO> search(Optional<String> teacher, Optional<LocalDate> from, Optional<LocalDate> to) {
		var sb = new StringBuffer(SELECT_PROJECTION);
		sb.append(" where 1 = 1");
		
		var params = new HashMap<String, Object>();
		
		sb.append(teacher.filter(StringUtils::hasText).map(a -> {
			params.put("teacher", a.toLowerCase().concat("%"));
			return " and lower(a.name) like :teacher";
		}).orElse(""));
		
		sb.append(from.map(a -> {
			params.put("from", Date.valueOf(a));
			return " and c.start_date >= :from";
		}).orElse(""));

		sb.append(to.map(a -> {
			params.put("to", Date.valueOf(a));
			return " and c.start_date <= :to";
		}).orElse(""));

		sb.append(SELECT_GROUPBY);
		
		return template.query(sb.toString(), params, new BeanPropertyRowMapper<>(ClassListVO.class));
	}

	public ClassForm findById(int id) {
		return template.queryForObject("select id, teacher_id teacher, start_date start, months, description from classes where id = :id", Map.of("id", id), new BeanPropertyRowMapper<>(ClassForm.class));
	}

	public ClassListVO findInfoById(int classId) {
		var sql = "%s where c.id = :id %s".formatted(SELECT_PROJECTION, SELECT_GROUPBY);
		return template.queryForObject(sql, Map.of("id", classId), new BeanPropertyRowMapper<>(ClassListVO.class));
	}

	public ClassDetailsVO findDetailsById(int id) {
		var classDetails = new ClassDetailsVO();
		
		var sql = "%s where c.id = :id %s".formatted(SELECT_PROJECTION, SELECT_GROUPBY);
		var classList = template.queryForObject(sql, Map.of("id", id), new BeanPropertyRowMapper<>(ClassListVO.class));
		
		//add classInfo 
		classDetails.setClassInfo(classList);
		
		//add registration
		classDetails.setRegistrations(regService.searchByClassId(id));
		
		//add leaves
		classDetails.setLeaves(leaveService.search(Optional.of(id), Optional.empty(), Optional.empty()));
		
		return classDetails;
	}

	@Transactional
	public int save(ClassForm form) {
		if (form.getId() == 0) {
			return insert(form);
		}
		return update(form);
	}

	private int update(ClassForm form) {
		template.update("""
				update classes set teacher_id = :teacher, start_date = :start, months = :months, description = :desp where id = :id
				""", 
				Map.of(
				"teacher", form.getTeacher(),
				"start", Date.valueOf(form.getStart()),
				"months", form.getMonths(),
				"desp", form.getDescription(),
				"id", form.getId()
				));
		return form.getId();
	}

	private int insert(ClassForm form) {
		var generatedId = insert.executeAndReturnKey(Map.of(
				"teacher_id", form.getTeacher(),
				"start_date", Date.valueOf(form.getStart()),
				"months", form.getMonths(),
				"description", form.getDescription()
				));
		return generatedId.intValue();
	}

}

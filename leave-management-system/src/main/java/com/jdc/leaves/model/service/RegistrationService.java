package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.dto.output.RegistrationDetailsVO;
import com.jdc.leaves.model.dto.output.RegistrationListVO;

@Service
public class RegistrationService {
	
	private static final String SELECT_PROJECTION = """
			select r.classes_id classId, c.teacher_id teacherId, ta.name teacher, c.description classInfo, 
			c.start_date startDate, r.student_id studentId, sa.name student, s.phone studentPhone, r.registration_date registrationDate
			from registration r 
			join classes c on c.id = r.classes_id 
			join teacher t on t.id = c.teacher_id join account ta on ta.id = t.id 
			join student s on s.id = r.student_id join account sa on sa.id = s.id
			""";

	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert insert;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ClassService classService;
	
	public RegistrationService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		insert = new SimpleJdbcInsert(dataSource);
		
		insert.setTableName("registration");
	}

	@Transactional
	public void save(RegistrationForm form) {
		if (form.getStudentId() > 0) {
			update(form);
			return;
		}
		
		insert(form);
	}

	public RegistrationDetailsVO findDetailsById(int classId, int studentId) {
		var registDetail = new RegistrationDetailsVO();
		
		var registDate = template.queryForObject("""
				select registration_date from registration where classes_id = :classId and student_id = :studentId
				""", 
				Map.of(
					"classId", classId,
					"studentId", studentId
				), Date.class);
		
		registDetail.setRegistDate(registDate.toLocalDate());
		
		registDetail.setStudent(studentService.findInfoById(studentId));
		
		registDetail.setClassInfo(classService.findInfoById(classId));
		
		return registDetail;
	}

	public RegistrationForm getFormById(int classId, int studentId) {
		var sql = """
				select r.classes_id classId, r.student_id studentId, r.registration_date registDate, 
				a.name studentName, a.email, s.phone, s.education 
				from registration r 
				join student s on s.id = r.student_id join account a on a.id = s.id 
				where r.classes_id = :classId and r.student_id = :studentId
			""";
		return template.queryForObject(sql,
				Map.of(
					"classId", classId,
					"studentId", studentId
					), 
				new BeanPropertyRowMapper<>(RegistrationForm.class));
	}

	public List<RegistrationListVO> searchByStudentId(int id) {
		var sql = "%s where r.student_id = :studentId".formatted(SELECT_PROJECTION);
		return template.query(sql, Map.of("studentId", id), new BeanPropertyRowMapper<>(RegistrationListVO.class));
	}

	public List<RegistrationListVO> searchByClassId(int id) {
		var sql = "%s where r.classes_id = :classId".formatted(SELECT_PROJECTION);
		return template.query(sql, Map.of("classId", id), new BeanPropertyRowMapper<>(RegistrationListVO.class));
}

	private void update(RegistrationForm form) {
		template.update("""
				update registration set registration_date = :date 
				where classes_id = :classId and student_id = :studentId
				""", 
				Map.of(
					"date", form.getRegistDate(),
					"classId", form.getClassId(),
					"studentId", form.getStudentId()
				));
		
		template.update("""
				update student set phone = :phone, education = :education where id = :id
				""", 
				Map.of(
					"phone", form.getPhone(),
					"education", form.getEducation(),
					"id", form.getStudentId()
				));
	}

	private void insert(RegistrationForm form) {
		var studentId = studentService.findStudentByEmail(form.getEmail());
		
		if (null == studentId) {
			studentId = studentService.createStudent(form);
		}
		
		form.setStudentId(studentId);
		
		if (form.getRegistDate() == null) {
			form.setRegistDate(LocalDate.now());
		}
		
		insert.execute(Map.of(
				"student_id", form.getStudentId(),
				"classes_id", form.getClassId(),
				"registration_date", Date.valueOf(form.getRegistDate())
				));
	}
	
}

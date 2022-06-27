package com.jdc.assignment.model.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.RegistrationModel;

public class RegistrationModelImpl implements RegistrationModel {

	private static final String SELECT_SQL = """
			select rg.id, rg.student, rg.phone, rg.email,
			oc.id openClassId, oc.start_date, oc.teacher,
			c.id courseId, c.name, c.fees, c.duration, c.description
			from registration rg
			join open_class oc on oc.id = rg.open_class_id
			join course c on c.id = oc.course_id
			where oc.id = ?
			""";

	private static final String INSERT_SQL = "insert into registration(open_class_id, student, phone, email) values(?, ?, ?, ?)";
	
	private DataSource dataSource;

	public RegistrationModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Registration> findByOpenClassId(int openClassId) {
		var list = new ArrayList<Registration>();
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(SELECT_SQL)){
			
			stmt.setInt(1, openClassId);
			
			var rs = stmt.executeQuery();
			while(rs.next()) {
				var c = new Course();
				c.setId(rs.getInt("courseId"));
				c.setName(rs.getString("name"));
				c.setFees(rs.getInt("fees"));
				c.setDuration(rs.getInt("duration"));
				c.setDescription(rs.getString("description"));
				
				var oc = new OpenClass();
				oc.setId(rs.getInt("openClassId"));
				oc.setCourse(c);
				oc.setStartDate(rs.getDate("start_date").toLocalDate());
				oc.setTeacher(rs.getString("teacher"));
				
				var reg = new Registration();
				reg.setId(rs.getInt("id"));
				reg.setOpenClass(oc);
				reg.setStudent(rs.getString("student"));
				reg.setPhone(rs.getString("phone"));
				reg.setEmail(rs.getString("email"));
				
				list.add(reg);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void register(Registration registration) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(INSERT_SQL)){
			stmt.setInt(1, registration.getOpenClass().getId());
			stmt.setString(2, registration.getStudent());
			stmt.setString(3, registration.getPhone());
			stmt.setString(4, registration.getEmail());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

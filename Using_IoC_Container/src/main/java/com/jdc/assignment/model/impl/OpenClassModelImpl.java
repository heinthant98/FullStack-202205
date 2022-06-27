package com.jdc.assignment.model.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.model.OpenClassModel;

public class OpenClassModelImpl implements OpenClassModel {

	private static final String SELECT_SQL = """
			select oc.id, oc.start_date, oc.teacher,
			c.id courseId, c.name, c.fees, c.duration, c.description 
			from open_class oc join course c on c.id = oc.course_id
			where c.id = ?
			""";

	private static final String CREATE_NEW = "insert into open_class(course_id, start_date, teacher) values (?, ?, ?)";

	private static final String FIND_ONE = """
			select oc.id, oc.start_date, oc.teacher, c.id courseId, c.name, c.fees, c.duration, c.description 
			from open_class oc join course c on c.id = oc.course_id
			where oc.id = ?
			""";
	
	private DataSource dataSource;

	public OpenClassModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<OpenClass> findByCouseId(int courseId) {
		var list = new ArrayList<OpenClass>();
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(SELECT_SQL)){
			
			stmt.setInt(1, courseId);
			
			var rs = stmt.executeQuery();
			
			while(rs.next()) {
				var c = new Course();
				c.setId(rs.getInt("courseId"));
				c.setName(rs.getString("name"));
				c.setFees(rs.getInt("fees"));
				c.setDuration(rs.getInt("duration"));
				c.setDescription(rs.getString("description"));
				
				var oc = new OpenClass();
				oc.setCourse(c);
				oc.setId(rs.getInt("id"));
				oc.setStartDate(rs.getDate("start_date").toLocalDate());
				oc.setTeacher(rs.getString("teacher"));
				
				list.add(oc);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void create(OpenClass openClass) {
		Course c = openClass.getCourse();
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(CREATE_NEW)){
				stmt.setInt(1, c.getId());
				stmt.setString(2, openClass.getStartDate().toString());
				stmt.setString(3, openClass.getTeacher());
				
				stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OpenClass findById(int openClassId) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(FIND_ONE)){
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
				oc.setId(rs.getInt("id"));
				oc.setCourse(c);
				oc.setTeacher("teacher");
				oc.setStartDate(rs.getDate("start_date").toLocalDate());
				
				return oc;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}

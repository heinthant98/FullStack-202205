package com.jdc.assignment.model.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.model.CourseModel;

public class CourseModelImpl implements CourseModel {

	private static final String FIND_ALL = "select * from course";

	private static final String INSERT = "insert into course (name, duration, fees, description) values (?, ?, ?, ?)";

	private static final String FIND_ONE = "select * from course where id = ?";
	
	private DataSource dataSource;

	public CourseModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Course> findAll() {
		var list = new ArrayList<Course>();
		try (var conn = dataSource.getConnection(); 
				var stmt = conn.prepareStatement(FIND_ALL)) {

			var rs = stmt.executeQuery();
			
			while (rs.next()) {
				Course c = new Course();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDuration(rs.getInt("duration"));
				c.setFees(rs.getInt("fees"));
				c.setDescription(rs.getString("description"));
				
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void save(Course course) {
		try (var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(INSERT)) {
			stmt.setString(1, course.getName());
			stmt.setInt(2, course.getDuration());
			stmt.setInt(3, course.getFees());
			stmt.setString(4, course.getDescription());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Course findById(int id) {
		try (var conn = dataSource.getConnection(); 
				var stmt = conn.prepareStatement(FIND_ONE)) {
			
			stmt.setInt(1, id);
			var rs = stmt.executeQuery();
			
			while (rs.next()) {
				Course c = new Course();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDuration(rs.getInt("duration"));
				c.setFees(rs.getInt("fees"));
				c.setDescription(rs.getString("description"));

				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

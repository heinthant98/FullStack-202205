package com.jdc.assignment.model;

import java.util.List;

import com.jdc.assignment.domain.Course;

public interface CourseModel {

	List<Course> findAll();
	void save(Course course);
	Course findById(int courseId);
}

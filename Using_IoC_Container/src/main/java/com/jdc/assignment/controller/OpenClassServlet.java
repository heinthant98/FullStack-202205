package com.jdc.assignment.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.model.CourseModel;
import com.jdc.assignment.model.OpenClassModel;

@WebServlet(urlPatterns = {
		"/classes",
		"/class-edit"
})
public class OpenClassServlet extends AbstractBeanFactoryServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//get courseId
		var courseId = Integer.parseInt(req.getParameter("courseId"));
		
		
		//get course
		var courseModel = getBean("courseModel", CourseModel.class);
		req.setAttribute("course", courseModel.findById(courseId));
		
		var page = switch(req.getServletPath()) {
		case "/classes" -> {
			//get Open Class
			var model = getBean("openClassModel", OpenClassModel.class);
			req.setAttribute("classes", model.findByCouseId(courseId));			
			yield "classes";
		}
		default -> "class-edit";
		};
		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//get Parameters
		var courseId = Integer.parseInt(req.getParameter("courseId"));
		
		var courseModel = getBean("courseModel", CourseModel.class);
		var c = courseModel.findById(courseId);
		var startDate = req.getParameter("startDate");
		var teacher = req.getParameter("teacher");
		
		var oc = new OpenClass();
		oc.setCourse(c);
		oc.setStartDate(LocalDate.parse(startDate));
		oc.setTeacher(teacher);
		
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		openClassModel.create(oc);
		
		resp.sendRedirect("/classes?courseId=%s".formatted(courseId));
		
	}

}

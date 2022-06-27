package com.jdc.assignment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.listener.SpringContextManager;
import com.jdc.assignment.model.CourseModel;

@WebServlet(urlPatterns = {
		"/",
		"/courses",
		"/course-edit"
})
public class CourseServlet extends AbstractBeanFactoryServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var page = switch(req.getServletPath()) {
		case "/course-edit" -> "/course-edit.jsp";
		default -> {
			//Get Data
			var model = getBean("courseModel", CourseModel.class);
			getServletContext().setAttribute("courses", model.findAll());
			yield "/index.jsp";
		}
		};
		
		getServletContext().getRequestDispatcher(page).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get parameters
		var name = req.getParameter("name");
		var duration = req.getParameter("duration");
		var fees = req.getParameter("fees");
		var description = req.getParameter("description");
		//create courses
		Course course = new Course();
		course.setName(name);
		course.setDuration(Integer.parseInt(duration));
		course.setFees(Integer.parseInt(fees));
		course.setDescription(description);
		
		//save data
		getBean("courseModel", CourseModel.class).save(course);
		//redirect url
		resp.sendRedirect("/");
	}


}

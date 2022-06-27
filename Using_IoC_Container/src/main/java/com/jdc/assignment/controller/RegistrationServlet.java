package com.jdc.assignment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.OpenClassModel;
import com.jdc.assignment.model.RegistrationModel;

@WebServlet(urlPatterns = {
		"/registration",
		"/registration-edit"
})
public class RegistrationServlet extends AbstractBeanFactoryServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var openClassId = Integer.parseInt(req.getParameter("openClassId"));
		
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var openClass = openClassModel.findById(openClassId);
		req.setAttribute("openClass", openClass);
		
		//setting course
		req.setAttribute("course", openClass.getCourse());
		
		var page = switch(req.getServletPath()) {
		case "/registration" ->{
			//search data
			var registerModel = getBean("registerModel", RegistrationModel.class);
			req.setAttribute("registrations", registerModel.findByOpenClassId(openClassId));
			yield "registration";
		}
		default -> "registration-edit";
		};
		
		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get parameters
		var openClassId = Integer.parseInt(req.getParameter("openClassId"));
		var student = req.getParameter("student");
		var phone = req.getParameter("phone");
		var email = req.getParameter("email");
		
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var oc = openClassModel.findById(openClassId);
		
		var register = new Registration();
		register.setOpenClass(oc);
		register.setStudent(student);
		register.setPhone(phone);
		register.setEmail(email);
		
		var registrationModel = getBean("registerModel", RegistrationModel.class);
		registrationModel.register(register);
		
		resp.sendRedirect("/registration?openClassId=%s".formatted(openClassId));
	}

}

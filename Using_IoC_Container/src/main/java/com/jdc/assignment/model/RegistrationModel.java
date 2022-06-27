package com.jdc.assignment.model;

import java.util.List;

import com.jdc.assignment.domain.Registration;

public interface RegistrationModel {
	
	List<Registration> findByOpenClassId(int openClassId);
	
	void register(Registration registration);

}

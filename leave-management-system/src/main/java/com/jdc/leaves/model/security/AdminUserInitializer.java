package com.jdc.leaves.model.security;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminUserInitializer {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@EventListener(classes = ContextRefreshedEvent.class)
	public void initAdminUser() {
		if (isNoAdminUser()) {
			createAdminUser();
		}
	}
	
	private boolean isNoAdminUser() {
		return new NamedParameterJdbcTemplate(dataSource)
				.queryForObject("select count(id) from account where role = :role", 
								Map.of("role", "Admin"), Long.class) == 0;
	}

	private void createAdminUser() {
		var insert = new SimpleJdbcInsert(dataSource);
		insert.setTableName("account");
		insert.setGeneratedKeyName("id");
		insert.setColumnNames(List.of("name", "role", "email", "password"));
		
		insert.execute(Map.of(
				"name", "Admin",
				"role", "Admin",
				"email", "admin@gmail.com",
				"password", passwordEncoder.encode("adminpwd")
				));
	}

}

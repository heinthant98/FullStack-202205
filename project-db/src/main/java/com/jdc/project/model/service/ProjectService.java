package com.jdc.project.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jdc.project.model.dto.Project;
import com.jdc.project.model.service.utils.ProjectHelper;

@Service
public class ProjectService {
	@Value("${project.findById}")
	String findById;
	@Value("${project.update}")
	String update;
	@Value("${project.delete}")
	String delete;
	@Value("${project.search}")
	String search;

	@Autowired
	private ProjectHelper projectHelper;

	@Autowired
	private SimpleJdbcInsert projectInsert;

	@Autowired
	private NamedParameterJdbcTemplate template;

	private RowMapper<Project> rowMapper;

	public ProjectService() {
		rowMapper = new BeanPropertyRowMapper<>(Project.class);
	}

	public int create(Project project) {

		projectHelper.validate(project);

		return projectInsert.executeAndReturnKey(projectHelper.insertParams(project)).intValue();

	}

	public Project findById(int id) {
		return template.queryForObject(findById, Map.of("id",id), rowMapper);
	}

	public List<Project> search(String project, String manager, LocalDate dateFrom, LocalDate dateTo) {
		var str = new StringBuffer(search);
		var params = new HashMap<String, Object>();
		
		if(StringUtils.hasLength(project)) {
			str.append(" and lower(p.name) like :project");
			params.put("project", project.toLowerCase().concat("%"));
		}
		
		if(StringUtils.hasLength(manager)) {
			str.append(" and lower(m.name) like :manager");
			params.put("manager", manager.toLowerCase().concat("%"));
		}
		
		if(dateFrom != null) {
			str.append(" and p.start >= :dateFrom");
			params.put("dateFrom", Date.valueOf(dateFrom));
		}
		
		if(dateTo != null) {
			str.append(" and p.start <= :dateTo");
			params.put("dateTo", Date.valueOf(dateTo));
		}
		
		return template.query(str.toString(), params, rowMapper);
	}

	public int update(int id, String name, String description, LocalDate startDate, int month) {
		
		return template.update(update,Map.of("id",id,"name",name,"description",description,"start",startDate,"months",month));
	}

	public int deleteById(int id) {
		return template.update(delete, Map.of("id",id));
	}

}

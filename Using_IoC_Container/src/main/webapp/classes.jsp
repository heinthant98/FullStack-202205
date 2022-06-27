<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Using IOC container</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</head>
<body>

	<div class="container mt-4">
	
		<h1>Using IOC Container</h1>

		<h3>Classes for ${ course.name }</h3>
	
		
			<div>
				<c:url var="addNew" value="/class-edit">
					<c:param name="courseId" value="${ course.id }"></c:param>
				</c:url>
				<a href="${ addNew }" class="btn btn-primary">Add New Class</a>
			</div>
		
			<div class="mt-4">
				<c:choose>
						<c:when test="${ empty classes }">				
							<div class="alert alert-warning">
								There is no class for ${ course.name }.Please create new Class.
							</div>
						</c:when>
						
						<c:otherwise>
							<table class="table table-striped">
								
								<thead>
									<tr>
										<th>ID</th>
										<th>Course Name</th>
										<th>Teacher</th>
										<th>Start Date</th>
										<th>Fees</th>
										<th>Durations</th>
										<th>Description</th>
										<th>Registration</th>
									</tr>
								</thead>
								
								<tbody>
									<c:forEach var="c" items="${classes}">
										<tr>
											<td>${ c.id }</td>
											<td>${ c.course.name }</td>
											<td>${ c.teacher }</td>
											<td>${ c.startDate }</td>
											<td>${ c.course.fees }</td>
											<td>${ c.course.duration } months</td>
											<td>${ c.course.description }</td>
											<td>
												<c:url var="register" value="/registration">
													<c:param name="openClassId" value="${c.id}"></c:param>
												</c:url>
												<a href="${register}">Register Now!</a>
											</td>
										</tr>
									
									</c:forEach>
								</tbody>
								
							</table>
						</c:otherwise>
					</c:choose>
					
				</div>
	</div>
</body>
</html>
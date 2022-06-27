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
		
		<h3>Registration of ${course.name} for ${openClass.startDate}</h3>
		
			<div>
				<c:url var="register" value="/registration-edit">
					<c:param name="openClassId" value="${openClass.id}"></c:param>
				</c:url>
				<a href="${ register }" class="btn btn-primary">REGISTER NOW!</a>
			</div>
			
			<div class="mt-4">
				<c:choose>
					<c:when test="${ empty registrations }">
							<div class="alert alert-warning">
								There is no registrations of ${ course.name } for ${ openClass.startDate }.Please register for this class!
							</div>
					</c:when>
					
					<c:otherwise>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Registration_ID</th>
									<th>Course Name</th>
									<th>Teacher Name</th>
									<th>Start Date</th>
									<th>Duration</th>
									<th>Student Name</th>
									<th>Phone Number</th>
									<th>Email Address</th>
								</tr>
							</thead>
							
							<tbody>
								
								<c:forEach var="reg" items="${ registrations }">
								
									<tr>
										<td>${reg.id}</td>
										<td>${course.name}</td>
										<td>${reg.openClass.teacher}</td>
										<td>${reg.openClass.startDate}</td>
										<td>${course.duration} months</td>
										<td>${reg.student}</td>
										<td>${reg.phone}</td>
										<td>${reg.email}</td>
									</tr>
										
								</c:forEach>
								
							</tbody>
						
						</table>
						
					</c:otherwise>
					
				</c:choose>
				
			</div>
		
			<div>
				<c:url var="classes" value="/classes">
					<c:param name="courseId" value="${course.id}"></c:param>
				</c:url>
				<a href="${ classes }" class="btn btn-primary">Return to open class</a>
			</div>
		
			<div>
				<c:url var="course" value="/"></c:url>
				<a href="${ course }" class="btn btn-primary">Return to Course</a>
			</div>
		
		
	</div>
</body>
</html>

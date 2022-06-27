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
		<h3>Courses</h3>
		
		<div>
			<a href="course-edit.jsp" class="btn btn-primary">Add New Course</a>
		</div>
		
		<div class="mt-4">
			
			<c:choose>
				<c:when test="${ empty courses }">
					<div class="alert alert-warning">
						There is no Course.Please create new Course.
					</div>
				</c:when>
			
				<c:otherwise>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Fees</th>
								<th>Durations</th>
								<th>Description</th>
								<th>Open Classes</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach var="c" items="${ courses }">
								<tr>
									<td>${c.id}</td>
									<td>${c.name}</td>
									<td>${c.fees}</td>
									<td>${c.duration} months</td>
									<td>${c.description}</td>
									<td>
										<c:url var="classes" value="/classes">
											<c:param name="courseId" value="${c.id}"></c:param>
										</c:url>
										<a href="${classes}" >Open Classes</a>
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
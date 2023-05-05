<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Leaves | Home</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">

<c:url var="commonCss" value="/resources/application.css"></c:url>
<link rel="stylesheet" href="${ commonCss }">

</head>
<body>

	<c:import url="/jsp/include/navbar.jsp">
		<c:param name="view" value="students"></c:param>
	</c:import>
	
	<div class="container">
		
		<h3 class="my-4">Student List</h3>
		
		<form class="row mb-4">
			<div class="col-auto">
				<label class="form-label">Name</label>
				<input type="text" name="name" value="${ param.name }" class="form-control" placeHolder="Enter Name" />
			</div>

			<div class="col-auto">
				<label class="form-label">Phone Number</label>
				<input type="tel" name="phone" value="${ param.phone }" class="form-control" placeHolder="Enter Phone Number" />
			</div>

			<div class="col-auto">
				<label class="form-label">Email Address</label>
				<input type="email" name="email" value="${ param.email }" class="form-control" placeHolder="Enter Email Address" />
			</div>

			<div class="col btn-wrapper">
				<button type="submit" class="btn btn-outline-success">
					<i class="bi bi-search"></i> Search
				</button>
			</div>
		</form>
		
		<c:choose>
			
			<c:when test="${ empty studentList }">
				<div class="alert alert-info">
					There is no students.
				</div>
			</c:when>
			
			<c:otherwise>
				<table class="table table-hover">
					<thead>
						<tr>
							<td>Id</td>
							<td>Name</td>
							<td>Phone Number</td>
							<td>Email Address</td>
							<td>Education</td>
							<td>Classes</td>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="student" items="${ studentList }">
							<tr>
								<td>${ student.id }</td>
								<td>${ student.name }</td>
								<td>${ student.phone }</td>
								<td>${ student.email }</td>
								<td>${ student.education }</td>
								<td>${ student.classCount }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		
		</c:choose>
		
		
	</div>

</body>
</html>
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
<link rel="stylesheet" href="${ commonCss }" />

</head>
<body>

	<c:import url="/jsp/include/navbar.jsp">
		<c:param name="view" value="classes"></c:param>
	</c:import>
	
	<div class="container">
		
		<h3 class="my-4">Classes Management</h3>
		
		<!-- Search Classes Bar -->
		<form class="row mb-4">

			<div class="col-auto">
				<label class="form-label">Name</label>
				<input type="text" name="teacher" value="${ teacher }" placeHolder="Search Teacher's Name" class="form-control"  />
			</div>

			<div class="col-auto">
				<label class="form-label">Date From</label>
				<input type="date" name="from" value="${ from }" class="form-control" />
			</div>

			<div class="col-auto">
				<label class="form-label">Date To</label>
				<input type="date" name="to" value="${ to }" class="form-control" />
			</div>

			<div class="col btn-wrapper">
				<button class="btn btn-outline-success me-2"><i class="bi bi-search"></i> Search</button>
				<c:url var="addNew" value="classes/edit"></c:url>
				<a href="${ addNew }" class="btn btn-outline-danger"><i class="bi bi-plus-lg"></i> Add New</a>
			</div>

		</form>
		
		<c:choose>
			
			<c:when test="${ empty classList }">
				<div class="alert alert-info">
					There is no data.
				</div>
			</c:when>
			
			<c:otherwise>
				<!-- Show Classes Data -->
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Id</th>
							<th>Teacher</th>
							<th>Teacher's Phone</th>
							<th>Start Date</th>
							<th>Months</th>
							<th>Students</th>
							<th>Description</th>
							<th></th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${ classList }" var="item">
							<tr>
								<td>${ item.id }</td>
								<td>${ item.teacherName }</td>
								<td>${ item.teacherPhone }</td>
								<td>${ item.startDate }</td>
								<td>${ item.months }</td>
								<td>${ item.studentCount }</td>
								<td>${ item.description }</td>
								<td>
									<c:url var="classEdit" value="/classes/edit">
										<c:param name="id" value="${ item.id }"></c:param>
									</c:url>
									<a href="${ classEdit }"><i class="bi bi-pencil me-2"></i></a>
									
									<c:url var="details" value="/classes/${ item.id }"></c:url>
									<a href="${ details }"><i class="bi bi-cursor"></i></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		
		</c:choose>
				
	</div>

</body>
</html>
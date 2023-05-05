<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Leaves | Home</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">

</head>
<body>

	<c:import url="/jsp/include/navbar.jsp">
		<c:param name="view" value="leaves"></c:param>
	</c:import>

	<div class="container">
		<h3 class="my-4">Leave Application</h3>
		
		<div class="row">
			<div class="col-lg-6 col-md-9 col-sm-12">
				
				<c:url var="save" value="/leaves"></c:url>
				<sf:form action="${ save }" method="post" modelAttribute="form">
					<sf:hidden path="classId"/>
					<sf:hidden path="student"/>
					<sf:hidden path="applyDate"/>
					
					<div class="col mb-4">
						<label class="form-label">Class</label>
						<span class="form-control">${ classInfo.description } (${ classInfo.startDate })</span>
					</div>
					
					<div class="col mb-4">
						<label class="form-label">Teacher Name</label>
						<span class="form-control">${ classInfo.teacherName }</span>
					</div>

					<div class="col mb-4">
						<label class="form-label">Student Name</label>
						<span class="form-control">${ studentInfo.name }</span>
					</div>
					<div class="row">
						<div class="col mb-4">
							<label class="form-label">Start Date</label>
							<sf:input path="startDate" type="date" cssClass="form-control"/>
							<sf:errors path="startDate" cssClass="text-danger"></sf:errors>
						</div>
						
						<div class="col mb-4">
							<label class="form-label">Days</label>
							<sf:input path="days" type="number" cssClass="form-control"/>
							<sf:errors path="days" cssClass="text-danger"></sf:errors>
						</div>
					</div>
					
					<div class="col mb-4">
						<label class="form-label">Reason</label>
						<sf:textarea path="reason" cssClass="form-control"/>
						<sf:errors path="reason" cssClass="text-danger"></sf:errors>
					</div>
									
					<div>
						<button type="submit" class="btn btn-outline-danger">
							<i class="bi bi-save"></i> Save
						</button>
					</div>
				</sf:form>
			</div>
		</div>		
	</div>

</body>
</html>
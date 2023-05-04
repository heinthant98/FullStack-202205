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
		<c:param name="view" value="classes"></c:param>
	</c:import>

	<div class="container">
		<h3 class="my-4">
			${ empty param.studentId ? 'Add New' : 'Edit' } Registration
		</h3>
		
		<div class="row">
			<sf:form method="post" modelAttribute="registForm" cssClass="col-6">
				<sf:hidden path="classId"/>
				<sf:hidden path="studentId"/>
				<sf:hidden path="registDate"/>
				
				<div class="mb-3">
					<label class="form-label">Start Date</label>
					<span class="form-control">
						${ param.startDate }
					</span>
				</div>
				
				<div class="mb-3">
					<label class="form-label">Teacher</label>
					<span class="form-control">
						${ param.teacherName }
					</span>
				</div>
				
				<div class="mb-3">
					<label class="form-label">Student</label>
					<sf:input path="studentName" placeHolder="Enter Name" class="form-control"/>
					<sf:errors path="studentName" cssClass="text-danger"></sf:errors>
				</div>
				
				<div class="mb-3">
					<label class="form-label">Email Address</label>
					<sf:input path="email" type="email" placeHolder="Enter Email Address" class="form-control"/>
					<sf:errors path="email" cssClass="text-danger"></sf:errors>
				</div>

				<div class="mb-3">
					<label class="form-label">Phone Number</label>
					<sf:input path="phone" type="tel" placeHolder="Enter Phone Number" class="form-control"/>
					<sf:errors path="phone" cssClass="text-danger"></sf:errors>
				</div>

				<div class="mb-3">
					<label class="form-label">Last Education</label>
					<sf:input path="education" placeHolder="Enter Last Education" class="form-control"/>
				</div>

				<div>
					<button type="submit" class="btn btn-outline-danger">
						<i class="bi bi-save"></i> Save
					</button>				
				</div>
			</sf:form>
		</div>
		
	</div>

</body>
</html>
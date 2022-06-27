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
		
		<h3>Register for ${course.name} of ${openClass.startDate}</h3>
		
		<div class="row">
			<div class="col-4">
			<c:url var="register" value="/registration">
				<c:param name="openClassId" value="${openClass.id}"></c:param>
			</c:url>
			<form action="${register}" method="post">
				<div class="mb-4">
					<label class="form-label">Student Name</label>
					<input name="student" type="text" placeholder="Enter Student Name" class="form-control"/>
				</div>
			
				<div class="mb-4">
					<label class="form-label">Phone Number</label>
					<input name="phone" type="text" placeholder="Enter Phone Number" class="form-control"/>
				</div>
				
				<div class="mb-4">
					<label class="form-label">Email Address</label>
					<input name="email" type="text" placeholder="Enter Email Address" class="form-control"/>
				</div>
				
				<input type="submit" value="Register" class="btn btn-primary" />
				
				<div>
					<c:url var="register" value="/registration">
						<c:param name="openClassId" value="${openClass.id}"></c:param>
					</c:url>
					<a href="${ register }">Return to registration</a>
				</div>
			</form>
				
				
				
			</div>
		</div>
	</div>
</body>
</html>

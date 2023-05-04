<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Leaves | Sign In</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">

<c:url var="commonCss" value="/resources/application.css"></c:url>
<link rel="stylesheet" href="${commonCss}" />

</head>
<body class="vh-100">


	<div class="d-flex vh-100 justify-content-center align-items-center">
		
		<div class="card login-form">
			
			<div class="card-header">
				<i class="bi bi-door-open-fill"></i> Sign In
			</div>
			
			<div class="card-body">
			
				<c:url var="signin" value="/signin"></c:url>
				<sf:form action="${signin}" method="post">
					
					<c:if test="${ not empty param.error }">
						<div class="alert alert-warning">Login Error</div>
					</c:if>
				
					<div class="mb-3">
						<label class="form-label">Email Address</label>
						<input type="email" name="username" placeholder="Enter Email Address" class="form-control" />
					</div>
					
					<div class="mb-3">
						<label class="form-label">Password</label>
						<input type="password" name="password" placeholder="Enter Password" class="form-control" />
					</div>
					
					<div>
						<button type="submit" class="btn btn-outline-success">
							<i class="bi bi-door-open"></i> Sign In
						</button>
					</div>
				</sf:form>
								
			</div>
		
		</div>
	
	</div>

</body>
</html>
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
		
		<h3>Create New Course</h3>
		
		<div class="row">
			<div class="col-4">
				
				<c:url var="save" value="/courses"></c:url>
				<form action="${save}" method="post">
					
					<div class="mb-4">
						<label class="form-label">Name</label>
						<input type="text" name="name" placeholder="Enter Course Name" class="form-control" />
					</div>
					
					
					<div class="mb-4">
						<label class="form-label">Duration</label>
						<input type="text" name="duration" placeholder="Enter Course Duration" class="form-control" />
					</div>
					
					<div class="mb-4">
						<label class="form-label">Fees</label>
						<input type="text" name="fees" placeholder="Enter Course Fees" class="form-control" />
					</div>
					
					<div class="mb-4">
						<label class="form-label">Description</label>
						<textarea rows="4" name="description" cols="40"></textarea>
					</div>
					
					<input type="submit" value="Save Course" class="btn btn-primary" />
					
					<div>
						<c:url var="course" value="/"></c:url>
						<a href="${ course }">Return to courses</a>
					</div>
					
				</form>
				
			</div>
		</div>
	</div>
</body>
</html>

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

		<h3>Create New Class for ${ course.name }</h3>
		
		
		<div class="row">
			<div class="col-4">
			
				<c:url var="create" value="/classes">
					<c:param name="courseId" value="${ course.id }"></c:param>
				</c:url>
				<form action="${create}" method="post">
					<div class="mb-4">
						<label class="form-label" >Start Date</label>
						<input name="startDate" type="date" class="form-control"/>
					</div>
					
					<div class="mb-4">
						<label class="form-label">Teacher Name</label>
						<input name="teacher" type="text" placeholder="Enter Teacher Name" class="form-control" />
					</div>
					
					<input type="submit" value="Create Class" class="btn btn-primary" />
				
				</form>

				
			</div>
		</div>
	</div>
</body>
</html>
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
		<c:param name="view" value="teachers"></c:param>
	</c:import>

	<div class="container">
		<h3 class="my-4">
			${ empty param.id ? 'Add New' : 'Edit' } Teacher
		</h3>
		
		<div class="row">
			<c:url var="save" value="/teachers"></c:url>
			<sf:form method="post" action="${ save }" modelAttribute="form" cssClass="col-lg-6 col-md-9 col-sm-12">
				
				<sf:hidden path="id"/>
				
				<div class="mb-4">
					<label cssClass="form-label">Name</label>
					<sf:input path="name" placeHolder="Enter Teacher Name" cssClass="form-control"/>
					<sf:errors path="name" cssClass="text-danger"></sf:errors>
				</div>

				<div class="mb-4">
					<label cssClass="form-label">Phone</label>
					<sf:input path="phone" type="tel" placeHolder="Enter Teacher Phone" cssClass="form-control"/>
					<sf:errors path="phone" cssClass="text-danger"></sf:errors>
				</div>

				<div class="mb-4 ${ empty param.id ? '' : 'd-none' }">
					<label cssClass="form-label">Email</label>
					<sf:input path="email" type="email" placeHolder="Enter Teacher Email Address" cssClass="form-control"/>
					<sf:errors path="email" cssClass="text-danger"></sf:errors>
				</div>
				
				<div class="mb-4">
					<label cssClass="form-label">Assign Date</label>
					<sf:input path="assignDate" type="date" cssClass="form-control"/>
					<sf:errors path="assignDate" cssClass="text-danger"></sf:errors>
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
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
		<c:param name="view" value="leaves"></c:param>
	</c:import>

	<div class="container">
		<h3 class="my-4">Leaves</h3>
		
		<form class="row mb-4">
			<div class="col-auto">
				<label class="form-label">Date From</label>
				<input type="date" name="from" value="${ param.from }" class="form-control" />
			</div>

			<div class="col-auto">
				<label class="form-label">Date To</label>
				<input type="date" name="to" value="${ param.to }" class="form-control" />
			</div>
			
			<div class="col btn-wrapper">
				<button class="btn btn-outline-success">
					<i class="bi bi-search"></i> Search
				</button>
			</div>
		</form>
		
		<c:choose>
			<c:when test="${ empty leavesList }">
				<div class="alert alert-info">
					There is no leaves data.
				</div>
			</c:when>
			
			<c:otherwise>
				<table class="table table-hover">
					<thead>
						<tr>
							<td>Class</td>
							<td>Teacher</td>
							<td>Apply Date</td>
							<td>Leave Start Date</td>
							<td>Leaves Days</td>
							<td>Reason</td>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="item" items="${ leavesList }">
							<tr>
								<td>${ item.classInfo } (${ item.classStart })</td>
								<td>${ item.teacher }</td>
								<td>${ item.applyDate }</td>
								<td>${ item.startDate }</td>
								<td>${ item.days } Days</td>
								<td>${ item.reason }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
		
	</div>

</body>
</html>
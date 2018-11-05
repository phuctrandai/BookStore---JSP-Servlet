<%@page import="bean.Bill"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Minh Khai Store</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link href="https://fonts.googleapis.com/css?family=K2D|Oswald" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> 
	<link rel="stylesheet" href="./css/style.css">
</head>
<body>
		
	<jsp:include page="header.jsp"></jsp:include>
	
	<jsp:include page="search.jsp"></jsp:include>
	
	<jsp:include page="categorySideBar.jsp"></jsp:include>
	
	<jsp:include page="content.jsp"></jsp:include>
	
	<!-- Scroll to top -->
	<button id="scrollToTopBtn" class="btn btn-danger" title="Về đầu trang">
		<i class="fa fa-chevron-circle-up" style="font-size:24px;"></i>
	</button>
	
	<!-- Scroll to bottom -->
	<button id="scrollToBotBtn" class="btn btn-danger" title="Về cuối trang">
		<i class="fa fa-chevron-circle-down" style="font-size:24px;"></i>
	</button>
</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script> 
	<script src="./js/script.js"></script>
</html>
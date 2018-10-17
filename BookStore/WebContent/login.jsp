<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="bean.Item"%>
<%@page import="bean.Book"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="bean.Cart"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Đăng nhập - Đăng ký</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<link href="https://fonts.googleapis.com/css?family=K2D|Oswald" rel="stylesheet">
	<link rel="stylesheet" href="./css/style.css">
</head>
<body>
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<jsp:include page="search.jsp"></jsp:include>
	
	<jsp:include page="categorySideBar.jsp"></jsp:include>
	
<!-- Sign up -->
	<div id="loginContent">
		<h3 style="color: red;">Đăng ký tài khoản</h3><br>
		<form action="account" method="post">
			<input type="hidden" name="command" value="signUp">
			<div class="form-group">
				<label for="accountName">Tên tài khoản:</label>
				<input type="text" class="form-control" id="accountName" name="accountName">
			</div>
			<div class="form-group">
				<label for="accountPassword">Mật khẩu:</label>
				<input type="password" class="form-control" id="accountPassword" name="accountPassword">
			</div>
			<div class="form-group">
				<label class="mr-sm-3">Bạn là:</label>
				<label class="radio-inline mr-sm-3">
					<input type="radio" id="newCustomer" name="accountRole" value="newCustomer">
					 Khách hàng mới 
			 	</label>
				<label class="radio-inline">
					<input type="radio" id="closeCustomer" name="accountRole" value="closeCustomer">
					Khách hàng thân thiết 
				</label>
			</div>
			<div class="form-group">
				<label for="name">Họ và tên:</label>
				<input type="text" class="form-control" id="customerName" name="customerName">
			</div>
			<div class="form-group">
				<label for="address">Địa chỉ:</label>
				<input type="text" class="form-control" id="customerAddress" name="customerAddress">
			</div>
			<div class="form-group">
				<label for="phoneNumber">Số điện thoại:</label>
				<input type="text" class="form-control" id="customerPhoneNumber" name="customerPhoneNumber">
			</div>
			<div class="form-group">
				<label for="emailAddress">Email:</label>
				<input type="email" class="form-control" id="customerEmailAddress" name="customerEmailAddress">
			</div>
			<button type="submit" class="btn btn-primary">Đăng ký</button>
		</form>
	</div>
	
</body>
</html>
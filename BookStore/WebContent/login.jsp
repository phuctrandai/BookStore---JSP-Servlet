<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="bean.Item"%>
<%@page import="bean.Book"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="bean.Bill"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Đăng nhập - Đăng ký</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

<link href="https://fonts.googleapis.com/css?family=K2D|Oswald"
	rel="stylesheet">

<link rel="stylesheet" href="./css/style.css">
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>

	<jsp:include page="search.jsp"></jsp:include>

	<jsp:include page="categorySideBar.jsp"></jsp:include>


	<div class="float-left ml-5 w-50">

		<!-- Menu -->
		<ul class="nav nav-tabs mb-5">
			<li class="nav-item"><a class="nav-link active text-primary" data-toggle="tab" href="#login">Khách hàng thân thiết</a></li>
			<li class="nav-item"><a class="nav-link text-danger" data-toggle="tab" href="#signUp">Khách hàng mới</a></li>
		</ul>

		<div class="tab-content">
			<!-- Login -->
			<div id="login" class="tab-pane container active">
				<h3 class="text-primary">Đăng nhập</h3><br>
				<form action="account" method="post">
					<input type="hidden" name="command" value="login">
					<div class="form-group">
						<label for="userName">Tên tài khoản:</label>
						<input type="text" class="form-control" id="userName" name="userName" placeholder=""></input>
					</div>
					<div class="form-group">
						<label for="password">Mật khẩu:</label>
						<input type="password" class="form-control" id="password" name="password"></input>
					</div>
					<button type="submit" class="btn btn-primary float-right">Đăng nhập</button>
				</form>
			</div>
			<!-- Sign up -->
			<div id="signUp" class="tab-pane container fade">
				<h3 class="text-danger">Đăng ký</h3><br>
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
					<button type="submit" class="btn btn-danger float-right">Đăng ký</button>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>
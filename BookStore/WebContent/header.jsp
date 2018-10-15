<%@page import="bean.Cart"%>
<%@page import="bean.Book"%>
<%@page import="bean.Item"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

	<%
	Cart cart = (Cart) session.getAttribute("cart");
	%>
	
	<header id="header">
	    <!-- logo -->
		<div id="logo">
			<img alt="logo" src="./images/minhkhai-logo.png" width="100%">
		</div>
		
		<!-- header menu -->
		<nav id="headerMenu" class="navbar navbar-expand-sm justify-content-center">
		  <ul class="navbar-nav">
		    <li class="nav-item col-xs-12">
		    	<img alt="icon1.png" src="./images/icon1.png">
		      	<a class="nav-link" href="#">Giao hàng<br>24 giờ</a>
		    </li>
		    <li class="nav-item col-xs-12">
		    	<img alt="icon1.png" src="./images/icon2.png">
		      	<a class="nav-link" href="#">Đặt hàng<br>Amazon</a>
		    </li>
		    <li class="nav-item col-xs-12">
		    	<img alt="icon1.png" src="./images/icon3.png">
		      	<a class="nav-link" data-toggle="modal" data-target="#loginForm" style="cursor: pointer;">
		      		Đăng nhập<br>
		      		<span style="font-weight: normal; font-size: 13px;">Tài khoản và Đơn hàng</span>
	      		</a>
		    </li>
		    <li class="nav-item col-xs-12">
		    	<img alt="icon1.png" src="./images/icon4.png">
		      	<a class="nav-link" href="cart">Giỏ hàng</a><br>
		      	<span style="font-size: 14px; font-weight: normal;">Đang có ( <span id="totalItem">
		      	<%
		      		if(cart != null) 
		      			out.print(cart.getTotalItem());
     				else
		      			out.print(0);
		      	%> </span> )
		      		sản phẩm
		      	</span><br>
		      	<span style="font-size: 11px">ĐT: (028) 3925 0590 - Hotline: 0903 10 14 79</span>
		    </li>
		  </ul>
		</nav>
	</header>
	
	
	<!-- top nav -->
	<nav id="topNav" class="navbar navbar-expand-sm justify-content-center">
		<ul class="navbar-nav">
			<li class="nav-item col-xs-12"><a class="nav-link" href="home">Trang chủ</a></li>
			<li class="nav-item col-xs-12"><a class="nav-link" href="#">Giới thiệu</a></li>
			<li class="nav-item col-xs-12"><a class="nav-link" href="#">Gian hàng</a></li>
			<li class="nav-item col-xs-12"><a class="nav-link" href="#">Sách độc quyền</a></li>
			<li class="nav-item col-xs-12"><a class="nav-link" href="#">Hướng dẫn mua hàng</a></li>
			<li class="nav-item col-xs-12"><a class="nav-link" href="#">Download</a></li>
			<li class="nav-item col-xs-12"><a class="nav-link" href="#">Liên hệ</a></li>
			<li class="nav-item col-xs-12"><a class="nav-link" href="#">Giảm giá đặc biệt</a></li>
			<li class="nav-item col-xs-12"><a class="nav-link" href="#">Bản tin Minh Khai</a></li>
		</ul>
	</nav>
	
	<!-- Login form -->
	<div class="modal modal fade" id="loginForm">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
	
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Đăng nhập</h4>
				</div>
	
				<!-- Modal body -->
				<div class="modal-body">
					<form class="form">
						<label class="" for="userName">Tên tài khoản:</label>
						<input type="text" name="userName" id="userName" class="form-control"><br>
						<label class="" for="password">Mật khẩu:</label>
						<input type="text" name="userName" id="password" class="form-control"><br>
					</form>
				</div>
	
				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Thôi</button>
					<button type="button" class="btn btn-success" id="loginBtn">Đăng nhập</button>
				</div>
	
			</div>
		</div>
	</div>

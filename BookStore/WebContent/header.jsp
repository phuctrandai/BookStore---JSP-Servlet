<%@page import="bean.Bill"%>
<%@page import="bean.Book"%>
<%@page import="bean.Item"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

	<%
	Bill bill = (Bill) session.getAttribute("bill");
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
		      	<a class="nav-link" href="#" title="Liên hệ và phản hồi về website và sách">Liên hệ</a><br>
		      	<span style="font-size: 13px">ĐT: (028) 3925 0590</span>
		      	<div class="nav-item-footer"></div>
		    </li>
		    
		    <li class="nav-item col-xs-12">
		    	<img alt="icon1.png" src="./images/icon2.png">
		      	<a class="nav-link" href="bill?command=billHistory" title="Kiểm tra trạng thái đơn hàng của bạn">Theo dõi đơn hàng</a><br>
		      	<span style="font-weight: normal; font-size: 13px;">Lịch sử mua hàng của bạn</span>
		      	<div class="nav-item-footer"></div>
		    </li>
		    
		    <li class="nav-item col-xs-12">
		    	<img alt="icon1.png" src="./images/icon4.png">
		      	<a class="nav-link" href="bill" title="Giỏ hàng của bạn">Giỏ hàng</a><br>
		      	<span style="font-size: 13px; font-weight: normal;">Đang có ( <span id="totalItem">
		      	<%
		      		if(bill != null) 
		      			out.print(bill.getTotalItem());
     				else
		      			out.print(0);
		      	%> </span> )
		      		sản phẩm
		      	</span><br>
		      	<div class="nav-item-footer"></div>
		    </li>
		    
		    <li class="nav-item col-xs-12">
		    	<img alt="icon1.png" src="./images/icon3.png">
		      	
		      	<%Object userName = session.getAttribute("userName");
		      	if(userName == null) {%>
		      	
		      	<a class="nav-link" data-toggle="modal" data-target="#loginForm" style="cursor: pointer;" title="Đăng nhập">
		      		Đăng nhập
	      		</a><br>
	      		<span style="font-weight: normal; font-size: 13px;">Tài khoản và Đơn hàng</span>
	      		
	      		<%} else {%>
	      		<div class="dropdown" style="display: inline-block;">
	      			<a class="nav-link dropdown-toggle" data-toggle="dropdown" style="cursor: pointer;" title="Tài khoản của bạn">
			      		Chào <%=userName %>
		      		</a>
		      		<div class="dropdown-menu dropdown-menu-right" style="margin-top: 10px; right: -25px; z-index: 1021">
				    	<a class="dropdown-item" href="#">Tài khoản</a>
				    	<div class="dropdown-divider"></div>
					    <a class="dropdown-item" href="account?command=doLogout">Đăng xuất</a>
				  	</div>
				</div><br>
				<span style="font-weight: normal; font-size: 13px;">Tài khoản</span>
	      		<%} %>
	      		<div class="nav-item-footer"></div>
		    </li>
		  </ul>
		</nav>
	</header>
	
	
	<!-- top nav -->
	<nav id="topNav" class="shadow navbar navbar-expand-sm sticky-top justify-content-center w-100 p-0">
		<ul class="navbar-nav w-100 p-0">
			<li class="nav-item col-md-2 col-xs-12"><a class="nav-link" href="home">Trang chủ</a></li>
			<li class="nav-item col-md-2 col-xs-12"><a class="nav-link" href="#">Giảm giá đặc biệt</a></li>
			<li class="nav-item col-md-2 col-xs-12"><a class="nav-link" href="#">Hướng dẫn mua hàng</a></li>
			<li class="nav-item col-md-2 col-xs-12"><a class="nav-link" href="#">Giới thiệu</a></li>
			<li class="nav-item col-md-4 col-xs-12">
				<form action="home" method="post" class="form-inline w-100 h-100">
					<input type="hidden" name="command" id="command" value="search">
					<div class="input-group w-100">
						<input type="text" name="keyWord" id="keyWord" class='form-control' placeholder="Từ khóa ..." maxlength="100" value="">
					
						<input type="submit" name="searchBtn" id="searchBtn" class="input-group-append btn btn-secondary"
							value="Search" title="Tìm kiếm sách/quà tặng- Bạn gõ font Unicode hoặc tiếng Việt không dấu">
					</div>
<!-- 					<select hidden="" id="optionSearch" class="form-control" name="optionSearch" style="cursor: pointer;"> -->
			
<!-- 						<option value="0">Tên/Tựa</option> -->
			
<!-- 						<option value="1">Thể loại</option> -->
							
<!-- 					</select> -->
				</form>
			</li>
		</ul>
		<a href="" class="icon" onclick="toggleTopNav()">
		  	<i class="fa fa-bars"></i>
		</a>
	</nav>
	
	<!-- Login form -->
	<div class="modal modal fade" id="loginForm">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Đăng nhập</h4>
				</div>
				<form class="form" action="account" method="post">
					<!-- Modal body -->
					<div class="modal-body">
						<label for="userName">Tên tài khoản:</label>
						<input type="text" name="userName" id="userName" class="form-control" autocomplete="userName"><br>
						
						<label for="password">Mật khẩu:</label>
						<input type="password" name="password" id="password" class="form-control" autocomplete="password"><br>
						
						<div>
							<a href="account?command=signUp">Đăng ký</a>
						</div>
					</div>
					<div class="modal-footer">
						<input type="hidden" name="command" value="doLogin">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Thôi</button>
						<button type="submit" class="btn btn-success" id="loginBtn">Đăng nhập</button>
					</div>
				</form>
			</div>
		</div>
	</div>

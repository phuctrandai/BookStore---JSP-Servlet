<%@page import="bean.Bill"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Quản lý đơn hàng</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<link href="https://fonts.googleapis.com/css?family=K2D|Oswald" rel="stylesheet">
	<link rel="stylesheet" href="./css/style.css">
</head>
<body>
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<jsp:include page="search.jsp"></jsp:include>
	
	<!-- Lịch sử mua hàng -->	
	<%	
	ArrayList<Bill> billList = (ArrayList<Bill>) request.getAttribute("billList");
	%>
	<div class="m-auto w-75">
		<p style="color: #1e7e34; font-size: 2em; font-weight: bold;">Đơn hàng của tôi:</p>
		<hr>
		<div id="accordion">
		<%for(int i=0 ; i < billList.size() ; i++) { %>
			<div class="card w-100 mt-3 shadow">
				<div class="card-header">
					<h4 class="float-left">Hóa đơn: <%=billList.get(i).getId() %></h4>
					<p class="float-right">Ngày lập: <%=billList.get(i).getDOP() %></p>
					<div class="clearfix"></div>
					<p class="float-right">
						<%if(billList.get(i).isPaid()) out.print("<span class='text-success'>Đã thanh toán</span>"); 	
						else out.print("<span class='text-danger'>Chưa thanh toán</span>"); %>
					</p>
					<a class="card-link" data-toggle="collapse" href="#collapse<%=i%>">
			         	Xem chi tiết
			        </a>
				</div>
				<div id="collapse<%=i%>" class="collapse" data-parent="#accordion">
					<div class="card-body">
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
					</div>
				</div>
				<div class="card-footer">
					<h5	class="float-left">Trị giá hóa đơn: <%=2 %></h5>
					<a class="btn btn-danger float-right text-light">Xóa</a>
					<%if(!billList.get(i).isPaid()) {%>
						<a class="btn btn-success float-right mr-3 text-light">Thanh toán</a>
					<%} %>
				</div>
			</div>
		<%} %>
		</div>
	</div>
</body>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script src="./js/script.js"></script>
	
</html>
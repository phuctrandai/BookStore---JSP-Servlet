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
		<%for(int i=0 ; i < billList.size() ; i++) { %>
			<div class="card w-100 mt-3">
				<div class="card-header">
					<h4>Mã hóa đơn: <%=billList.get(i).getId() %></h4>
					<h5>Ngày lập: <%=billList.get(i).getDOP() %></h5>
					<h6>Trạng thái: <%=billList.get(i).isPaid() %></h6>
				</div>
				<div class="card-body">
					
				</div>
				<div class="card-footer">
					<h5	class="float-left">Tổng tiền thanh toán: <%=2 %></h5>
					<a class="btn btn-danger float-right text-light">Xóa</a>
					<%if(!billList.get(i).isPaid()) {%>
						<a class="btn btn-success float-right mr-3 text-light">Thanh toán</a>
					<%} %>
				</div>
			</div>
		<%} %>
	</div>
</body>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script src="./js/script.js"></script>
	
</html>
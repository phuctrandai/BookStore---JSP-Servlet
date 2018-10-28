<%@page import="bo.BillBo"%>
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
	<title>Giỏ hàng</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link href="https://fonts.googleapis.com/css?family=K2D|Oswald" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<link rel="stylesheet" href="./css/style.css">	
</head>
<body>
	
	<%
		Bill bill = (Bill) session.getAttribute("bill");
		BillBo billBo = new BillBo(bill);
	%>
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<jsp:include page="search.jsp"></jsp:include>
	
	<jsp:include page="categorySideBar.jsp"></jsp:include>
	
<!-- Cart's content -->
	<div id="cartContent">
		<p style="color: #1e7e34; font-size: 2em; font-weight: bold;">Giỏ hàng của bạn:</p>
		<hr>
		<table style="width: 100%;">
			<%
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vie", "VN"));
		
		for (Map.Entry<String, Item> list : bill.getItems().entrySet()) {
			%>
			<tr id="<%=list.getKey() %>">
				<td style="width: 60px;">
					<img alt="Hình ảnh sách" src="<%=list.getValue().getBook().getImage()%>" class="bookImage" />
				</td>
				<td>					
					<form class="form-inline">
						<input type="text" readonly="readonly"	value="<%=list.getValue().getBook().getName()%>" id="bookName<%=list.getKey() %>"
							style = "border: none; font-size: 18px; width: 55%;"><br>
						<p class="itemInfo mr-sm-3 mr-xs-3">
							<span style="font-weight: bold; color: #F4B344;">Giá:</span>
							<span id="bookPrice<%=list.getKey() %>"><%="  " + nf.format(list.getValue().getBook().getPrice()) + " x "%></span>
						</p>
						<input type="number" value=<%=list.getValue().getQuantity()%> min="1" id="itemQuality<%=list.getKey() %>" class="form-control mr-sm-3 mr-xs-3"
							style = "text-align: center; width: 60px;">
						<input type="button" value="Cập nhật" id="<%=list.getKey() %>" class="btn btn-primary updateBtn mr-sm-3 mr-xs-3">
						<input type="button" value="Trả lại" id="<%=list.getKey() %>" class="btn btn-danger removeBtn">
					</form>
				</td>
			</tr>
			<tr id=<%=list.getKey() %>><td colspan="2"><hr></td></tr>
			<%
				}
			%>
<!-- Show when cart has no item -->
			<tr>
			<td colspan="2" id="emptyCart" <%if(billBo.getTotalItem() > 0) { %>style="visibility:collapse;" <%}%>>
				<h2 style="display: inline-block">
					<span style="color: #F4B344">Hiện đang trống</span>
				</h2>
				<a href="home" style="display: inline; margin-left:15px;">
					<button class="btn btn-success">Mua hàng</button>
				</a> <!--<a href="#"><button class="btn-danger">Thanh toán</button></a>-->
			</td>
			</tr>
			<tr>
<!-- Show when cart has item -->
			<td colspan="2" id="hasItem" <%if(billBo.getTotalItem() == 0) { %>style="visibility:collapse;"<%}%>>
				<h2>
					<span style="color: #F4B344">Tổng cộng:</span>
					<span id="totalPrice"><%="   " + nf.format(billBo.getTotalPrice())%></span>
				</h2><br>
				<a class="btn btn-success" href="home">
					Tiếp tục mua hàng
				</a>
				<form action="bill" method="post" style="display: inline-block">
					<input type="hidden" name="command" value="checkout">
					<input type="submit" class="btn btn-danger ml-sm-3" value="Xác nhận và thanh toán">
				</form>
			</td>
			</tr>
		</table>
	</div>	
</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script src="./JS/script.js"></script>
</html>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Map"%>
<%@page import="bean.Item"%>
<%@page import="java.util.HashMap"%>
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
	NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vie", "VN"));
	%>
	<div class="m-auto w-75">
	<%if(billList != null && billList.size() > 0) {%>
		<p style="color: #1e7e34; font-size: 2em; font-weight: bold;">Đơn hàng của tôi:</p>
		<hr>
		<div id="accordion">
		<%for(int i=0 ; i < billList.size() ; i++) { %>
			<div class="card w-100 mt-3 shadow">
				<div class="card-header">
					<div class="w-100">
						
							<div class="row">
								<div class="col-sm-12 col-md-2">Mã hóa đơn</div>
								<div class="col-sm-12 col-md-2">Ngày lập</div>
								<div class="col-sm-12 col-md-2">Tổng tiền</div>
								<div class="col-sm-12 col-md-2">Trạng thái</div>
								<div class="col-sm-12 col-md-4"></div>
							</div>
						
							<div class="row">
								<div class="col-sm-12 col-md-2 text-dark"><%=billList.get(i).getId()%></div>
								<div class="col-sm-12 col-md-2 text-dark"><%=billList.get(i).getDOP() %></div>
								<div class="col-sm-12 col-md-2 text-success"><%=nf.format(billList.get(i).getTotalPrice()) %></div>
								<div class="col-sm-12 col-md-2">
									<%if(billList.get(i).isPaid()) out.print("<span class='text-success'>Đã thanh toán</span>"); 	
									else out.print("<span class='text-danger'>Chưa thanh toán</span>"); %>
								</div>
								<div class="col-sm-12 col-md-4">
									<a class="float-left" data-toggle="collapse" href="#collapse<%=i%>">
							         	Xem chi tiết
						        	</a>
									<a class="ml-5 float-right" href="bill?command=delete&billId=<%=billList.get(i).getId()%>">Xóa</a>
									<%if(!billList.get(i).isPaid()) {%>
										<a class="ml-5 float-right" href="bill?command=pay&billId=<%=billList.get(i).getId()%>">Thanh toán</a>
									<%} %>
								</div>
							</div>
						
					</div>
				</div>
				<div id="collapse<%=i%>" class="collapse" data-parent="#accordion">
					<div class="card-body">
						<%
						HashMap<String, Item> items = billList.get(i).getItems();
						for(Map.Entry<String, Item> item : items.entrySet()) {%>
							<div class="container w-100">
								<img alt="Avatar" src="<%=item.getValue().getBook().getImage()%>"
									 class="float-left mb-3 mr-3" style="width: 55px; height: 75px">
								 <div class="row float-left" style="margin-top: 0.5rem; width: 90%;">
								 	<div class="col-sm-12 col-md-5">
								 		<h6>Tên sách</h6>
								 		<p class=""><%=item.getValue().getBook().getName() %></p>	
								 	</div>
									<div class="col-sm-12 col-md-2">
										<h6>Giá bán</h6>
										<p><%=nf.format(item.getValue().getBook().getPrice())%></p>
									</div>
									<div class="col-sm-12 col-md-2" style="text-align: center;">
										<h6>Số lượng</h6>
										<p class=""><%=item.getValue().getQuantity() %></p>
									</div>
									<div class="col-sm-12 col-md-2">
										<h6>Thành tiền</h6>
										<p class=""><%=nf.format(item.getValue().getBook().getPrice() * item.getValue().getQuantity())%></p>
									</div>
									<div class="col-sm-12 col-md-1">
										<%if(!billList.get(i).isPaid()) {%>
											<a href="bill?command=removeBook&billId=<%=billList.get(i).getId()%>&bookId=<%=item.getKey()%>">Trả lại</a>
										<%} %>
									</div>
								 </div>
							</div>
							<div class="clearfix"></div>				
						<%} %>
					</div>
				</div>
			</div>
		<%} // End loop for %>
		</div>
	<%} // End if bill list != null 
	else {
	%>
		<div class="w-75 ml-auto mr-auto card p-5" style="text-align: center;">
			<h2 style="color: #F4B344">
				Hiện bạn không có đơn hàng nào !
			</h2><br>
			<a href="home" class="btn btn-warning ml-auto mr-auto w-25">
				Mua hàng
			</a>
		</div>
	<%} %>
	</div>
</body>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script src="./js/script.js"></script>
	
</html>
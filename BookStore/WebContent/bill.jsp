<%@page import="java.util.ArrayList"%>
<%@page import="bean.Customer"%>
<%@page import="bo.CartBo"%>
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
	<title>Quản lý đơn hàng</title>
	<meta charset="utf-8">
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
	
	<%
	session.setAttribute("prevPage", "bill");
	
	String command = (String) request.getAttribute("command");
	if(command != null) {
		// Thanh toán hóa đơn
		if(command.equals("checkout")) {
			Customer customer = (Customer) request.getSession().getAttribute("customer");
			Cart cart = (Cart) session.getAttribute("cart");
			CartBo cartBo = new CartBo(cart);
	%>
		<!-- Thanh toán hóa đơn -->
		<div class="mw-100 ml-3 p-0">
		
			<!-- Chi tiết hóa đơn -->
			<div class="w-75 float-left ml-1 card">
				<div class="card-body">
					<h5 class="card-title" style="color: #1e7e34; font-size: 2em; font-weight: bold;">Đơn hàng hiện tại:</h5>
					<br>
						<%
					NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vie", "VN"));
					
					for (Map.Entry<String, Item> list : cart.getItems().entrySet()) {
						%>
						<div class="card mb-3">
							<div class="card-body">
								<div class="w-10 float-left">
									<img alt="Hình ảnh sách" src="<%=list.getValue().getBook().getImage()%>" class="bookImage" />
								</div>
								<div class="w-75 float-left">
									<label style = "border: none; font-size: 18px; width: 100%;"><%=list.getValue().getBook().getName()%></label><br>
									<p class="itemInfo mr-sm-3 mr-xs-3">
										<span style="font-weight: bold; color: #F4B344;">Giá:</span>
										<span><%=nf.format(list.getValue().getBook().getPrice())%></span>
										<span class="ml-3">x</span>
										<span class="ml-3"><b><%=list.getValue().getQuality()%></b></span>
										<span class="ml-3">=</span>
										<span class="ml-3 text-danger"><b><%=nf.format(list.getValue().getQuality() * list.getValue().getBook().getPrice())%></b></span>
									</p>
								</div>
							</div>
						</div>
					<%
					} // End for hien thi hoa don
					%>
				</div>
			</div>
			
			<!-- Thông tin hóa đơn -->
			<div class="float-right mr-4 card">
				<div class="card-body">
					<p style="color: #1e7e34; font-size: 2em; font-weight: bold;">Thông tin đơn hàng:</p>
					<p>
						<span style="color: #000">Tổng tiền thanh toán:</span>
						<span class="text-danger ml-3" style="font-size: 24px"><%=nf.format(cartBo.getTotalPrice())%></span>
					</p>
					<p style="color: #000">
						<span>Ngày mua hàng:</span>
						<span class="ml-3"><%=cartBo.getCart().getNgayMua()%></span>
					</p>
					<br>
					<h5 style="color: #1e7e34; font-size: 2em; font-weight: bold;">Địa chỉ giao hàng:</h5>
					<p style="color: #000">
						<span>Khách hàng:</span>
						<span class="ml-3"><b><%=customer.getName()%></b></span>
					</p>
					<p style="color: #000">
						<span>Địa chỉ:</span>
						<span class="ml-3"><b><%=customer.getAddress()%></b></span>
					</p>
					<p>
						<span style="color: #000">Số điện thoại:</span>
						<span class="ml-3"><b><%=customer.getPhoneNumber()%></b></span>
					</p>
					<a href="#">Thay đổi địa chỉ</a>
					<a class="btn btn-danger w-100 mt-5 float-left" href="bill?command=pay">
						Thanh toán
					</a>
				</div>
			</div>
		
		<%	} // End if(command.equals("checkout")) 
			else if(command.equals("billHistory")) {
				ArrayList<Cart> billList = (ArrayList<Cart>) request.getAttribute("listBill");		
		%>
			<div class="m-auto w-75">
				<p style="color: #1e7e34; font-size: 2em; font-weight: bold;">Đơn hàng của tôi:</p>
				<hr>
				<%for(int i=0 ; i < billList.size() ; i++) { %>
					<div class="card w-100 mt-3">
						<div class="card-header">
							<h4>Mã hóa đơn: <%=billList.get(i).getMaHoaDon() %></h4>
							<h5>Ngày lập: <%=billList.get(i).getNgayMua() %></h5>
							<h6>Trạng thái: <%=billList.get(i).isDaThanhToan() %></h6>
						</div>
						<div class="card-body">
							
						</div>
						<div class="card-footer">
							<h5	class="float-left">Tổng tiền thanh toán: <%=2 %></h5>
							<a class="btn btn-danger float-right text-light">Xóa</a>
							<%if(!billList.get(i).isDaThanhToan()) {%>
								<a class="btn btn-success float-right mr-3 text-light">Thanh toán</a>
							<%} %>
						</div>
					</div>
				<%} %>
			</div>
		</div>
	<%
			} // end else Neu quan ly don hang
	} // End if(command != null) %>
</body>

<script src="./JS/script.js"></script>

</html>
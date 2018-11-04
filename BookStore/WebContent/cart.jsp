<%@page import="bean.Item"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="bean.Bill"%>
<%@page import="bean.Customer"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Xác nhận đơn hàng</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<link href="https://fonts.googleapis.com/css?family=K2D|Oswald" rel="stylesheet">
	<link rel="stylesheet" href="./css/style.css">
</head>
<body>
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<jsp:include page="search.jsp"></jsp:include>
	
	<%
	Customer customer = (Customer) session.getAttribute("customer");
	Bill bill = (Bill) session.getAttribute("bill");
	%>

	<!-- Hóa đơn -->
	<div class="hasItem mw-100 ml-3 p-0" <%if(bill.getTotalItem() == 0) { %>style="display: none;" <%}%>>
	
		<!-- Chi tiết hóa đơn -->
		<div class="w-75 float-left ml-1 card">
			<div class="card-body">
				<h5 class="card-title" style="color: #1e7e34; font-size: 2em; font-weight: bold;">Giỏ hàng:</h5>
				<br>
					<%
				NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vie", "VN"));
				
				for (Map.Entry<String, Item> list : bill.getItems().entrySet()) {
					%>
					<div class="card mb-3" id="<%=list.getKey()%>">
						<div class="card-body">
							<div class="w-10 float-left">
								<img alt="Hình ảnh sách" src="<%=list.getValue().getBook().getImage()%>" class="bookImage" />
							</div>
							<div class="w-50 float-left">
								<label style = "border: none; font-size: 18px; width: 100%;"><%=list.getValue().getBook().getName()%></label>
								<label class="itemInfo">
									<span style="font-weight: bold; color: #F4B344;">Giá:</span>
									<span><%=nf.format(list.getValue().getBook().getPrice())%></span>
								</label><br>
								<a id="<%=list.getKey() %>" class="removeBtn" href="#">Trả lại</a>
							</div>
							<div class="float-right w-30" >
								<label class="float-left">Số lượng mua:</label>
							  	<input type="number" value=<%=list.getValue().getQuantity()%> min="1" max="100" id="itemQuantity<%=list.getKey() %>" class="form-control float-left ml-2"
							style = "text-align: center; width: 80px;">
								<button id="<%=list.getKey() %>" class="btn btn-primary text-light float-left ml-2 updateBtn">Cập nhật</button>
						  	</div>
						</div>
					</div>
				<%
				} // End for hien thi hoa don
				%>
			</div>
		</div>
		
		<!-- Thông tin hóa đơn -->
		<div class="float-right mr-5 card w-20">
			<div class="card-body">
				<p style="color: #1e7e34; font-size: 1.5em; font-weight: bold;">Thông tin đơn hàng:</p>
				<p style="color: #000">Tổng tiền thanh toán:</p>
				<p class="text-danger totalPrice" style="font-size: 24px"><%=nf.format(bill.getTotalPrice())%></p>
			<%if(customer != null) { %>
				<p style="color: #000">
					<span>Ngày mua hàng:</span>
					<span class="ml-3"><%=bill.getDOP()%></span>
				</p>
				<br>
				<h5 style="color: #1e7e34; font-size: 1.5em; font-weight: bold;">Địa chỉ giao hàng:</h5>
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
			<%} %>
				<form action="bill" method="post" class="w-100">
					<input type="hidden" name="command" value="pay">
					<input type="submit" class="btn btn-danger w-100 mt-5 float-left" value="Thanh toán">
				</form>
				<a href="home" class="btn btn-warning mt-3 w-100">Tiếp tục mua hàng</a>
			</div>
		</div>
	</div>
		
	<div class="emptyCart w-75 ml-auto mr-auto card p-5" style="text-align: center; <%if(bill.getTotalItem() > 0) out.print("display: none");%>">
		<h2 style="color: #F4B344">
			Giỏ hàng hiện đang trống
		</h2><br>
		<a href="home" class="btn btn-warning ml-auto mr-auto w-25">
			Mua hàng
		</a>
	</div>
	
</body>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script src="./js/script.js"></script>

</html>
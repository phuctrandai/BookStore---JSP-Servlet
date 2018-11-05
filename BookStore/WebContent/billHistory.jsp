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
						<table class="w-100">
							<tr>
								<td>Mã hóa đơn</td>
								<td>Ngày lập</td>
								<td class="w-20">Trạng thái</td>
								<td class="w-12">Tổng tiền</td>
								<td class="w-30"></td>
							</tr>
						
							<tr>
								<td><%=billList.get(i).getId()%></td>
								<td><%=billList.get(i).getDOP() %></td>
								<td>
									<%if(billList.get(i).isPaid()) out.print("<span class='text-success'>Đã thanh toán</span>"); 	
									else out.print("<span class='text-danger'>Chưa thanh toán</span>"); %>
								</td>
								<td class="text-success"><%=nf.format(billList.get(i).getTotalPrice()) %></td>
								<td>
									<a class="float-left" data-toggle="collapse" href="#collapse<%=i%>">
							         	Xem chi tiết
						        	</a>
									<a class="ml-5 float-right" href="bill?command=delete&billId=<%=billList.get(i).getId()%>">Xóa</a>
									<%if(!billList.get(i).isPaid()) {%>
										<a class="float-right" href="bill?command=pay&billId=<%=billList.get(i).getId()%>">Thanh toán</a>
									<%} %>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="collapse<%=i%>" class="collapse" data-parent="#accordion">
					<div class="card-body">
						<table class="w-100">
						<%
						HashMap<String, Item> items = billList.get(i).getItems();
						for(Map.Entry<String, Item> item : items.entrySet()) {%>
							<tr>
								<td class="w-40">
									<img alt="Avatar" src="<%=item.getValue().getBook().getImage()%>"
									 class="float-left 	mb-3 mr-3 book-image">
									 <div class="float-left">
								 		<p class=""><%=item.getValue().getBook().getName() %></p>	
								 		<p class="text-success"><%=nf.format(item.getValue().getBook().getPrice())%></p>
								 	</div>
								 </td>
								 <td class="w-10">
								 	<label class="mr-3">x</label>
								 	<label class=""><b><%=item.getValue().getQuantity() %></b></label>
								 	<label class="ml-3"><b>=</b></label>
								 </td>
								 <td class="w-25 text-center">
								 	<p class="text-success"><%=nf.format(item.getValue().getBook().getPrice() * item.getValue().getQuantity())%></p>
								 </td>
								 <td class="float-right">
								 	<%if(!billList.get(i).isPaid()) {%>
										<a href="bill?command=removeBook&billId=<%=billList.get(i).getId()%>&bookId=<%=item.getKey()%>">Trả lại</a>
									<%} %>
								 </td>
							</tr>
										
						<%} %>
						</table>
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
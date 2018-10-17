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
	<title>Giỏ hàng của bạn</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<link href="https://fonts.googleapis.com/css?family=K2D|Oswald" rel="stylesheet">
	<link rel="stylesheet" href="./css/style.css">
	
	<style>
		
	</style>
	
</head>
<body>
	
	<%
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
	        cart = new Cart();
	        session.setAttribute("cart", cart);
	    }
		session.setAttribute("prevPage", "cart");
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
				Locale locale = new Locale("vie", "VN");
				NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
				for (Map.Entry<String, Item> list : cart.getItems().entrySet()) {
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
						<input type="number" value=<%=list.getValue().getQuality()%> min="1" id="itemQuality<%=list.getKey() %>" class="form-control mr-sm-3 mr-xs-3"
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
			<td colspan="2" id="emptyCart" <%if(cart.getTotalItem() > 0) { %>style="visibility:collapse;" <%}%>>
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
			<td colspan="2" id="hasItem" <%if(cart.getTotalItem() == 0) { %>style="visibility:collapse;"<%}%>>
				<h2>
					<span style="color: #F4B344">Tổng cộng:</span>
					<span id="totalPrice"><%="   " + nf.format(cart.getTotalPrice())%></span>
				</h2>
				<a href="home">
					<button class="btn btn-success">Tiếp tục mua hàng</button>
				</a> <!--<a href="#"><button class="btn-danger">Thanh toán</button></a>-->
			</td>
			</tr>
		</table>
	</div>
</body>
<script>
	$('.updateBtn').click(function() {
		var bookId = $(this).attr("id");
		var itemQuality = $('#itemQuality'+bookId).val();
		$.post('cart',
		{
			updateBtn : "updateBtn",
			command : "modify",
			bookId : bookId,
			itemQuality : itemQuality
		}, function(data, status) {
			
			var str = data.split(";");
			$('#totalPrice').html(str[0]);
		});
	});
	
	$('.removeBtn').click(function() {
		var bookId = $(this).attr("id");
		$.post('cart',
		{	removeBtn : "removeBtn",
			command : "modify",
			bookId : bookId
		}, function(data, status) {
			var str = data.split(";");
			$('#totalPrice').html(str[0]);
			$('tr[id='+bookId+']').remove();
			$('#totalItem').html(str[1]);

			if(str[1] == 0) {
				$('#emptyCart').css("visibility","visible");
				$('#hasItem').remove();
				
			} else {
				$('#emptyCart').css("visibility","collapse");
				$('#hasItem').css("visibility","visible");
			}
		});
	});
</script>
</html>
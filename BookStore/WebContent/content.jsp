<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="bean.Loai"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="bean.Book"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

	<!-- Content -->
	<div id="contentView" class="d-flex flex-wrap">
	<%    
	HashMap<String, Book> bookList = (HashMap<String, Book>) request.getAttribute("bookList");
	
	if(bookList != null && bookList.size() > 0) {		
		Locale locale = new Locale("vie","VN");
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
	
		for (Map.Entry<String, Book> item : bookList.entrySet()) {
		%>
		<div class="p-2 col-sm-3 col-6 card bookCol">
			<a href="#">
				<img class="card-img-top" height="250rem" alt="<%=item.getValue().getName() %>" src="<%=item.getValue().getImage() %>">
			</a><br>
			<div class="book-col-body">
				<a class="add-to-cart" href="cart?command=add&bookId=<%=item.getValue().getId()%>">
					<img src="./images/buynow.jpg">
				</a><br><br>
				<label class="card-title" style="color: #000; font-size: 16px; margin: 0px;"><strong><%= item.getValue().getName()%></strong></label><br>
				<label><b>Giá bán: <span style="color:#6d2524; font-size:medium; font-weight:bold"><%=nf.format(item.getValue().getPrice()) %></span></b></label>
			</div>
		</div>
		<%}%>
	</div>
	
	<!-- Pagination -->
	<div class="container" style="clear: right">
		<ul class="pagination justify-content-center" style="margin:20px 0">
		<%
		int pageNumber = 1;
		if (request.getParameter("pageNumber") != null)
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		int totalPage = (int) request.getAttribute("totalPage");
		
		if (totalPage != 0) {
			for(int j=1 ; j<=totalPage ; j++) {%>
	    	
	    	<li class="page-item  <%if(pageNumber == j) out.print("active"); %>">
	    		<a class="page-link" href="home?pageNumber=<%=j%>"><%=j %></a>
    		</li>
			
			<%}
		}%>
		</ul>
	</div>
			
	<%
	} else {%>
		<h2 style="margin: 30px">Loại sách này hiện chưa có !</h2>
	<%} %>


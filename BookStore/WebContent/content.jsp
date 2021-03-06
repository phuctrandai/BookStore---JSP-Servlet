<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="bean.Category"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="bean.Book"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

	<!-- Content -->
	<div class="d-flex flex-wrap float-right w-75">
	<%    
	HashMap<String, Book> bookList = (HashMap<String, Book>) request.getAttribute("bookList");
	
	if(bookList != null && bookList.size() > 0) {		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vie","VN"));
	
		for (Map.Entry<String, Book> item : bookList.entrySet()) {
		%>
		<div class="p-2 col-sm-3 col-6 card ml-4 mr-4 mb-4 book-col">
			<a href="#">
				<img class="card-img-top" height="250rem" alt="<%=item.getValue().getName() %>" src="<%=item.getValue().getImage() %>">
			</a><br>
			<div class="card-body">
				<label class="card-title" style="color: #000; font-size: 16px;"><strong><%= item.getValue().getName()%></strong></label><br>
				<label class=""><b>Giá bán: <span style="color:#6d2524; font-size:medium; font-weight:bold"><%=nf.format(item.getValue().getPrice()) %></span></b></label>
				<br>
				<p class=""><b>Hiện có:</b> <%=item.getValue().getQuantity()%> quyển</p>
			</div>
			<div class="card-footer" style="text-align: center; background: #fff;">
				<a class="btn text-light w-100" style="background: #9b231c;" href="bill?command=add&bookId=<%=item.getValue().getId()%>">
					Thêm vào giỏ hàng
				</a>
			</div>
		</div>
		<%}%>
		
		<!-- Pagination -->
		<div class="container">
			<ul class="pagination justify-content-center mt-3 ml-20 w-50">
			<%
			int pageNumber = 1;
			if (request.getParameter("pageNumber") != null)
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			
			int totalPage = (int) request.getAttribute("totalPage");
			String categoryId = request.getParameter("categoryId");
			
			if (totalPage != 0) {
				for(int j=1 ; j<=totalPage ; j++) {%>
		    	
		    	<li class="page-item  <%if(pageNumber == j) out.print("active"); %>">
		    		<a class="page-link" href="home?categoryId=<%=categoryId%>&pageNumber=<%=j%>"><%=j %></a>
	    		</li>
				
				<%}
			}%>
			</ul>
		</div>	
	<%
	} else {%>
		<h2 style="margin: 30px">Loại sách này hiện chưa có !</h2>
	<%} %>
	</div>


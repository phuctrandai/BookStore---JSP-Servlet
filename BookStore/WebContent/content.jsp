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
	ArrayList<Book> bookList = (ArrayList<Book>) request.getAttribute("bookList");
	
	if(bookList != null && bookList.size() > 0) {		
		Locale locale = new Locale("vie","VN");
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
	
		for (int i=0; i < bookList.size(); i++) {
		%>
		<div class="p-2 col-sm-3 col-6 card bookCol">
			<a href="#">
				<img class="card-img-top" height="250rem" alt="<%=bookList.get(i).getName() %>" src="<%=bookList.get(i).getImage() %>">
			</a><br>
			<div class="book-col-body">
				<a class="add-to-cart" href="cart?command=add&bookId=<%=bookList.get(i).getId()%>">
					<img src="./images/buynow.jpg">
				</a><br><br>
				<label class="card-title" style="color: #000; font-size: 16px; margin: 0px;"><strong><%= bookList.get(i).getName()%></strong></label><br>
				<label><b>Giá bán: <span style="color:#6d2524; font-size:medium; font-weight:bold"><%=nf.format(bookList.get(i).getPrice()) %></span></b></label>
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
	
	<!-- Scroll to top -->
	<button onclick="scrollToTop()" id="scrollToTopBtn" class="btn btn-danger" title="Về đầu trang">Top</button>

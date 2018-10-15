<%@page import="bean.Loai"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
	<!-- Category side bar -->
	<%
	ArrayList<Loai> loaiList = (ArrayList<Loai>) request.getAttribute("loaiList");
	%>
	<nav id="categorySideBar" class="navbar bg-light">
		<%
		String categoryId = request.getParameter("categoryId");
		boolean isActive = false; %>
		<ul class="navbar-nav" style="">
		<li class="nav-item" style="padding: 1rem !important; color: #fff; background-color: #666">Danh mục loại sách</li>
		<%for (int i = 0; i < loaiList.size(); i++) {
			if(loaiList.get(i).getId().equals(categoryId)) isActive = true;
		%>
		<li class="nav-item category-item <%if(isActive) { out.print("category-item-active"); isActive = false;}%>">
			<a class="nav-link" href="home?categoryId=<%=loaiList.get(i).getId()%>"><%=loaiList.get(i).getName() %></a>
		</li>
		<%}%>
		</ul>
	</nav>
<%@page import="bean.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- Category side bar -->
<%
	ArrayList<Category> categoryList = (ArrayList<Category>) request.getAttribute("categoryList");
%>
<nav id="categorySideBar" class="navbar bg-light">
	<%
		String categoryId = request.getParameter("categoryId");
		boolean isActive = false;
	%>
	<ul class="navbar-nav" class="w-100">
		<li class="nav-item w-100" style="padding: 1rem !important; color: #fff; background-color: #666">Danh mục loại sách</li>
		<%
			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryList.get(i).getId().equals(categoryId))
					isActive = true;
		%>
		<li class="nav-item category-item w-100 <%if (isActive) {
					out.print("category-item-active");
					isActive = false;
				}%>">
			<a class="nav-link" href="home?categoryId=<%=categoryList.get(i).getId()%>"><%=categoryList.get(i).getName()%></a>
		</li>
		<%
			}
		%>
	</ul>
</nav>
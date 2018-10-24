<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
	
	<!-- Search -->
	<div style="width: 80%; margin: 30px auto 10px auto" class="row">
		<div style="display: inline-block;" class="col-sm-2">
			<h3 style="padding-top: 3">TÌM KIẾM:</h3>
		</div>
		<form id="search_box" action="home" method="post" style="display: inline-block" class="form-inline col-sm-10">
			<input type="hidden" name="command" id="command" value="search">
			<input type="text" name="keyWord" id="keyWord" class='form-control mr-sm-3' placeholder="Từ khóa ..." maxlength="100" value="" style="width: 70%">
			<input type="submit" name="searchBtn" id="searchBtn" class="form-control btn btn-secondary mr-sm-3" value="Search" title="Tìm kiếm sách/quà tặng- Bạn gõ font Unicode hoặc tiếng Việt không dấu"
				  onclick="" style="">
			<select id="optionSearch" class="form-control" name="optionSearch" style="font-weight: bold; color: #333; cursor: pointer;">
	
				<option value="0">Tên/Tựa</option>
	
				<option value="1">Thể loại</option>
					
			</select>
		</form>
	</div>
	<hr>
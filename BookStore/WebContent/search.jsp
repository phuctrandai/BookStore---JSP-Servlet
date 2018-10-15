<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
	
	<!-- Search -->
	<div style="width: 80%; margin: 30px auto 10px auto" class="row">
		<div style="display: inline-block;" class="col-sm-2">
			<h3 style="padding-top: 3">TÌM KIẾM:</h3>
		</div>
		<form id="search_box" style="display: inline-block" class="form-inline col-sm-8">
			<input class='form-control' type="text" placeholder="Từ khóa ..." maxlength="100" style="width:80%">
			<input class="form-control btn btn-secondary" value="Search" title="Tìm kiếm sách/quà tặng- Bạn gõ font Unicode hoặc tiếng Việt không dấu"
				type="button" onclick="" style="width:15%">
		</form>
		<div class="form-group col-sm-2" style="display: inline-block" >
			<select id="optionSearch" class="form-control" name="sellist" style="font-weight: bold; color: #333; cursor: pointer;">
	
				<option value="0">Tên/Tựa</option>
	
				<option value="1">Tác giả</option>
	
				<option value="2" selected>Sách</option>
	
				
			</select>
		</div>
	</div>
	<div style="height: auto">
		<hr>
	</div>

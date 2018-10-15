<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
	
	<!-- Search -->
	<div style="width: 80%; margin: 30px auto 10px auto" class="row">
		<div style="display: inline-block;" class="col-sm-2">
			<h3 style="padding-top: 3">TÌM KIẾM:</h3>
		</div>
		<form id="search_box" action="home" method="post" style="display: inline-block" class="form-inline col-sm-10">
			<input type="hidden" name="command" value="search">
			<input type="text" name="keyWord" id="keyWord" class='form-control' placeholder="Từ khóa ..." maxlength="100" style="width: 80%">
			<input type="submit" name="searchBtn" id="searchBtn" class="form-control btn btn-secondary" value="Search" title="Tìm kiếm sách/quà tặng- Bạn gõ font Unicode hoặc tiếng Việt không dấu"
				  onclick="" style="">
			<select id="optionSearch" class="form-control" name="optionSearch" style="font-weight: bold; color: #333; cursor: pointer;">
	
				<option value="0">Tên/Tựa</option>
	
				<option value="1">Thể loại</option>
					
			</select>
		</form>
<!-- 		<div class="form-group col-sm-2" style="display: inline-block" > -->
			
<!-- 		</div> -->
	</div>
	<hr>
	
	<script>
		$('#searchBtn').click(function(){
			//var opt = $('#optionSearch option:selected').text();
			
			
			//alert(opt);
		});
	</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../include/header.jsp"%>
<style>
	div.uploadDiv{
		overflow: hidden;
	}
	div.item{
		float: left;
		height: 100px;
		position: relative;
		margin: 0px 3px;
	}
	div.item .btnDel{
		position: absolute;
		right: 3px;
		top: 3px;
		font-weight: bold;
	}
</style>


<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ BOARD</h3>
				</div>
				<!-- /.box-header -->

<form role="form" action="modifyPage" method="post" enctype="multipart/form-data">
	<input type="hidden" name="page" value="${cri.page }">
	<input type="hidden" name="searchType" value="${cri.searchType }">
	<input type="hidden" name="keyword" value="${cri.keyword }">
	<div class="box-body">

		<div class="form-group">
			<label for="exampleInputEmail1">BNO</label> 
			<input type="text"	name='bno' class="form-control" value="${board.bno}"
				readonly="readonly">
		</div>

		<div class="form-group">
			<label for="exampleInputEmail1">Title</label> 
			<input type="text"	name='title' class="form-control" value="${board.title}">
		</div>
		<div class="form-group">
			<label for="exampleInputPassword1">Content</label>
			<textarea class="form-control" name="content" rows="3">${board.content}</textarea>
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Writer</label> 
			<input type="text" name="writer" class="form-control" value="${board.writer}">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">files</label> 
			<input type="file" name="imgfiles" class="form-control">
		</div>
		
		<div class="form-group uploadDiv">
			<c:forEach var="file" items="${board.files }">
				<div class="item">
					<img src="displayFile?filename=${file }">
					<button type="button" class="btnDel" data-src="${file }">X</button>  
				</div>
			</c:forEach>
		</div>
		
	</div>
	<!-- /.box-body -->
	<div class="box-footer">
		<button type="submit" class="btn btn-primary">SAVE</button>
		<button type="button" class="btn btn-warning">CANCEL</button>
	</div>
	
	
</form>




<script>
	$(document).ready(function() {
		$(".btn-warning").on("click", function() {
			self.location = "${pageContext.request.contextPath}/board/readPage?bno=${board.bno}";
		});
		
		$(".btnDel").click(function() {
			$(this).parent().hide();
			var $input = $("<input>").attr("type","hidden").attr("name","delFiles").attr("value",$(this).attr("data-src"));
			$(".uploadDiv").append($input);
		})
	});
</script>




			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->

<!-- /.content-wrapper -->

<%@include file="../include/footer.jsp"%>
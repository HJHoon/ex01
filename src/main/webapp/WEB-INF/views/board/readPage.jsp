<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-primary">
					<!-- box-header -->
					<div class="box-header">
						<h3 class="box-title">
							READ PAGE
						</h3>
					</div>					
					<!-- box-body -->
					<div class="box-body">
						<form role="form" method="post" id="f1">						
							<input type='hidden' name='bno' value="${board.bno}">	
							<input type="hidden" name="page" value="${cri.page }">					
						</form>
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" 
								placeholder="Enter Title" value="${board.title }" readonly="readonly">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="8" cols="" name="content" class="form-control" 
							placeholder="Enter Content" readonly="readonly">${board.content }</textarea>								
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" 
							placeholder="Enter Writer" value="${board.writer }" readonly="readonly">
						</div>
					</div>
					<!-- box-footer -->
					<div class="box-footer">
						<button type="submit" id="btnModify" class="btn btn-warning">Modify</button>
						<button type="submit" id="btnRemove" class="btn btn-danger">REMOVE</button>
						<button type="submit" id="btnList" class="btn btn-primary">GO LIST</button>						
					</div>
					
				</div>
			</div>
		</div>
		<script>
			$("#btnModify").click(function(){
				$("#f1").attr("action","modifyPage");
				$("#f1").attr("method", "get");
				$("#f1").submit();
			})
			
			$("#btnRemove").click(function(){
				$("#f1").attr("action","removePage");
				$("#f1").attr("method", "post");
				$("#f1").submit();
			})
			
			$("#btnList").click(function(){
				location.href = "${pageContext.request.contextPath}/board/listPage?page="+${cri.page};
			})
		</script>
	
	</section>
<%@ include file="../include/footer.jsp" %>
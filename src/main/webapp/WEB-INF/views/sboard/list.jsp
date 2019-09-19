<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="../include/header.jsp" %>
	<section class="content">
		<div class="row">
			<div class="col-md-12">				
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">Search Board</h3>
					</div>
					<div class="box-body">
						<select name="searchType">
							<option value="n" ${cri.searchType==null?'selected':'' }>---</option>
							<option value="t" ${cri.searchType=='t'?'selected':'' }>Title</option>
							<option value="c" ${cri.searchType=='c'?'selected':'' }>Content</option>
							<option value="w" ${cri.searchType=='w'?'selected':'' }>Writer</option>
							<option value="tc" ${cri.searchType=='tc'?'selected':'' }>Title OR Content</option>
							<option value="cw" ${cri.searchType=='cw'?'selected':'' }>Content OR Writer</option>
							<option value="tcw" ${cri.searchType=='tcw'?'selected':'' }>Title OR Content OR Writer</option>
						</select>
						<input type="text" id="keywordInput" name="keyword" value="${cri.keyword }">
						<button id="btnSearch">Search</button>
						<button id="btnAdd">New Board</button>
					</div>
				</div>
				<div class="box box-primary">
					<!-- box-header -->
					<div class="box-header">
						<h3 class="box-title">
							List Search Page
						</h3>
					</div>					
					<!-- box-body -->
					<div class="box-body">
						<table class="table table-bordered">
							<tr>
								<th style="width:10px;">BNO</th>
								<th>Title</th>
								<th>Writer</th>
								<th>RegDate</th>
								<th style="width:40px;">ViewCnt</th>
							</tr>
							<c:forEach var="board" items="${list }">
								<tr>
									<td>${board.bno }</td>
									<td><a href="readPage?bno=${board.bno}&page=${pageMaker.cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}">${board.title } [${board.replycnt }]</a></td>
									<td>${board.writer }</td>
									<td><fmt:formatDate value="${board.regdate }" pattern="yyyy-MM-dd HH:mm"/></td>
									<td><span class="badge bg-red">${board.viewcnt }</span></td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<!-- box-footer -->
					<div class="box-footer">	
						<div class="text-center">
							<ul class="pagination">
								<c:if test="${pageMaker.prev }">
									<li><a href="list?page=${pageMaker.startPage-1 }&searchType=${cri.searchType}&keyword=${cri.keyword}"> &laquo; </a></li>
								</c:if>								
								<c:forEach var="idx" begin="${pageMaker.startPage }" end="${pageMaker.endPage }" >
									<li ${pageMaker.cri.page == idx ? 'class="active"' :'' } ><a href="list?page=${idx }&searchType=${cri.searchType}&keyword=${cri.keyword}">${idx }</a></li>
								</c:forEach>
								<c:if test="${pageMaker.next }">
									<li><a href="list?page=${pageMaker.endPage+1 }&searchType=${cri.searchType}&keyword=${cri.keyword}"> &raquo; </a></li>
								</c:if>	
							</ul>
						</div>					
					</div>
					
				</div>
			</div>
		</div>
	
	</section>
	<script>
		$("#btnSearch").click(function(){
			var select = $("select[name='searchType']").val();
			var keyword = $("input[name='keyword']").val();
			
			location.href = "list?page="+${cri.page}+"&searchType="+select+"&keyword="+keyword;
		})
		
		$("#btnAdd").click(function(){
			location.href = "register";
		})
	</script>
<%@ include file="../include/footer.jsp" %>







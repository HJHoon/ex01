<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="../include/header.jsp" %>
	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-primary">
					<!-- box-header -->
					<div class="box-header">
						<h3 class="box-title">
							List Page
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
									<td><a href="readPage?bno=${board.bno}&page=${pageMaker.cri.page}">${board.title }</a></td>
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
									<li><a href="listPage?page=${pageMaker.startPage-1 }"> &laquo; </a></li>
								</c:if>								
								<c:forEach var="idx" begin="${pageMaker.startPage }" end="${pageMaker.endPage }" >
									<li ${pageMaker.cri.page == idx ? 'class="active"' :'' } ><a href="listPage?page=${idx }">${idx }</a></li>
								</c:forEach>
								<c:if test="${pageMaker.next }">
									<li><a href="listPage?page=${pageMaker.endPage+1 }"> &raquo; </a></li>
								</c:if>	
							</ul>
						</div>					
					</div>
					
				</div>
			</div>
		</div>
	
	</section>
<%@ include file="../include/footer.jsp" %>
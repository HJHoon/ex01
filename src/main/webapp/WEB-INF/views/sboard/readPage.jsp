<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
							<input type="hidden" name="searchType" value="${cri.searchType }">
							<input type="hidden" name="keyword" value="${cri.keyword }">				
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
						
						<div class="form-group">
							<c:forEach var="file" items="${board.files }">
								<img src="displayFile?filename=${file }">
							</c:forEach>
						</div>
					</div>
					<!-- box-footer -->
					
					<div class="box-footer">
					<c:if test="${board.writer == Auth.userid }">
						<button type="submit" id="btnModify" class="btn btn-warning">Modify</button>
						<button type="submit" id="btnRemove" class="btn btn-danger">REMOVE</button>
					</c:if> 
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
				location.href = "${pageContext.request.contextPath}/sboard/list?page=${cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}";
			})
		</script>
		<div class="row">
			<div class="col-md-12">
				<div class="box box-success">
					<div class="box-header">
						<h3 class="box-title">ADD NEW REPLY</h3>
					</div>
					<div class="box-body">
						<label >Writer</label>
						<input type="text" placeholder="user id" class="form-control" id="newReplyWriter" readonly="readonly" value="${Auth.userid }">
						<br>
						<label>Reply Text</label>
						<input type="text" placeholder="text" class="form-control" id="newReplyText">
					</div>
					<div class="box-footer">
						<button type="button" class="btn btn-primary" id="btnReplyAdd">ADD REPLY</button>
					</div>
				</div>
				<ul class="timeline">
					<li class="time-label" id="btnReplyList">
						<span class="bg-green">Replies List[${board.replycnt }]</span>
					</li>
				</ul>
				<div class="text-center">
					<ul id="pagination" class="pagination pagination-sm no-margin"></ul>
				</div>
			</div>
		</div>
		
		
		
		
		<div id="modifyModal" class="modal modal-primary fade" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"></h4> <!-- rno번호 넣음 -->
					</div>
					<div class="modal-body" data-rno=""> <!--  rno번호 넣음 -->
						<p><input type="text" id="replytext" class="form-control"></p> <!-- input에 text넣음 -->
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" id="btnReplySaveMod">Modify</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">close</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<script id="template" type="text/x-handlebars-template">
		{{#each.}}
		<li class="replyLi" data-rno="{{rno}}">
			<i class="fa fa-comments bg-blue"></i>
			<div class="timeline-item">
				<span class="time">
					<i class="fa fa-clock-o"></i>{{tempdate regdate}}
				</span>
				<h3 class="timeline-header">
					<strong>{{rno}}</strong> - {{replyer}}
				</h3>
				<div class="timeline-body">{{replytext}}</div>
				{{#if replyer}}
				<div class="timeline-footer">
					<a class="btn btn-warning btn-xs btnReplyModify" data-toggle="modal" data-target="#modifyModal">Modify</a>
					<a class="btn btn-danger btn-xs btnReplyDelete">Delete</a>
				</div>
				{{/if}}
			</div>
		</li>
		{{/each}}
		</script>
		
		<script>
		//if문을 사용시, options는 항상 있는 매개변수임
		//if 헬퍼 사이에 구문 통채로 options 매개변수에 넘어옴
			Handlebars.registerHelper("if", function(replyer, options) {
				if(replyer == '${Auth.userid}'){
					return options.fn(this);//헬퍼사이의 구문이 통채로 return
				}else{
					return '';//빈 글자만 return
				}
			});
			var bno = ${board.bno}; //${board.bno} 
			var currentPage = 1;
			
			function getListAll(page){
				$.ajax({
					url:"${pageContext.request.contextPath}/replies/"+bno+"/"+page,
					type:"get",				
					dataType:"json",
					success:function(res){
						console.log(res);
						$(".replyLi").remove();
						
						Handlebars.registerHelper("tempdate",function(time){
							var date = new Date(time);
							var year = date.getFullYear();
							var month = date.getMonth() + 1;
							var day = date.getDate();
							var hour = date.getHours();
							var minute = date.getMinutes();
							return year + "-" + month + "-" + day + " " + hour + ":" + minute;
						})
						
						var source = $("#template").html();
						var fn = Handlebars.compile(source);
						var str = fn(res.list);
						$(".timeline").append(str);
						
						printPaging(res);
					}
				})
			}
			
			function printPaging(res){
				$(".pagination").empty();
				for(var i = res.pageMaker.startPage; i<=res.pageMaker.endPage; i++){
					var $li = $("<li>");
					var $a = $("<a>").text(i).attr("href","#");		
					if(res.pageMaker.cri.page == i){
						$li.addClass("active");
					}
					$li.append($a);
					$(".pagination").append($li);
				}
			}
		
			$("#btnReplyList").click(function(){
				getListAll(1);
			})
			
			$(document).on("click",".pagination a", function(e) {
			e.preventDefault();
			var page = $(this).text();
			currentPage = page;
			getListAll(page);
		})
		
		
		
		
		$("#btnReplyAdd").click(function() {
			var replyer = $("#newReplyWriter").val();
			var replytext = $("#newReplyText").val();
			//@RequestBody - 서버에 오는 json string을  parsing하여 key와 맞는 변수에 값을 넣음
			//@RequestBody 를 사용하면 서버로 보내는 값은 json String이여야함
			//@RequestBody 를 사용하는 서버에 값을 보낼때에는 반드시 headers에 Content-Type(application/json)을 넣어줘야함
			
			//1.json String 으로 변환하여 전송
			//2. headers 추가
			var json = {bno:bno, replyer:replyer, replytext:replytext};
			var data = JSON.stringify(json);
			
			$.ajax({
				url:"${pageContext.request.contextPath}/replies",
				type:"post",
				headers:{
					"Content-Type":"application/json"
				},
				data:data,
				dataType:"text",
				success:function(res){
					console.log(res);
					if(res == "success"){
						getListAll(1);
					}
				}
			})
		})
		
	
		
		
		$(document).on("click", ".btnReplyDelete", function() {
				var rno = $(this).parent().prev().prev().find("strong").text();
				
				$.ajax({
					url:"${pageContext.request.contextPath}/replies/"+rno,
					type:"delete",
					dataType:"text",
					success:function(res){
						console.log(res);
						if(res == "success"){
							alert("삭제됨");
							getListAll(1);
						}
					}
				})
			
			
		})
		
		
		
		
		$(document).on("click", ".btnReplyModify",function() {
			var modalBno = $(this).parent().prev().prev().find("strong").text();
			$(".modal-title").text(modalBno);
			$(".modal-body").attr("data-rno",modalBno);
			var modaltext = $(this).parent().prev().text();
			$("#replytext").val(modaltext);
		
		})
		
		
		$("#btnReplySaveMod").click(function() {
			var rno = $(".modal-title").text();
			/* var rno = $(this).parents("#modWrap") */
			var replytext = $("#replytext").val();
			var json = {rno:rno, replytext:replytext};
			var data = JSON.stringify(json);
			
			$.ajax({
				url:"${pageContext.request.contextPath}/replies/"+rno,
				type:"put",
				headers:{
					"Content-Type":"application/json"
				},
				data:data,
				dataType:"text",
				success:function(res){
					console.log(res);
					if(res == "success"){
						$("#modifyModal").modal("hide"); //모달 끔
						getListAll(currentPage);
					}
				}
			})
		})
		</script>
	
	</section>
<%@ include file="../include/footer.jsp" %>










<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("br", "<br/>"); pageContext.setAttribute("cn", "\n"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시글 상세 보기</title>
</head>
<body onload="CommentEvent()">
    <div class="board">
        <table class="contentBoard">
            <tr>
                <td class="boardleft">작성일</td>
                <td class="boardright">${board.date }</td>
            </tr>
            <tr>
                <td class="boardleft">작성자</td>
                <td class="boardright">${board.id }</td>
            </tr>
            <tr>
                <td class="boardleft">제목</td>
                <td class="boardright">${board.subject }</td>
            </tr>
            <tr>
                <td class="boardleft">내용</td>
                <td class="boardright">
                    <div class="contentscroll">
                        ${fn:replace(board.content, cn, br) }
                    </div>
                </td>
            </tr>
            <tr>
                <td class="boardleft">첨부파일</td>
                <td class="boardright">
                    <a href="FileDownloadAction.brd?file=${board.file}">${board.file }</a>
                </td>
            </tr>
        </table>
        <div class="boardbutton">
            <c:if test="${sessionScope.sessionID != null }">
                <c:if test="${sessionScope.sessionID == board.id }">
                    <input type="button" value="수정" onclick="javascript:location.href='BoardUpdateFormAction.brd?num=${board.num}&page=${pageNum}'">
                    <input type="button" value="삭제" onclick="javascript:location.href='BoardDeleteAction.brd?num=${board.num}'">
                </c:if>
                <input type="button" value="답글" onclick="javascript:location.href='BoardReplyFormAction.brd?num=${board.num}&page=${pageNum}'">
            </c:if>
            <input type="button" value="목록" onclick="javascript:location.href='BoardListAction.brd?page=${pageNum}'">
            <input type="button" value="댓글보기" class="show">
        </div>
    </div>
    <!-- 로그인 했을 경우만 댓글 작성가능 -->
    <div class="mask">
	    <div class="mask-con">
		    <!-- 댓글 부분 -->
		    <div class="commentTable">
		        <!-- 댓글 목록 -->
		        <c:if test="${requestScope.commentList != null }">
		        <c:forEach var="comment" items="${requestScope.commentList}">
					<div class="commentbox clearfix">
			            <c:if test="${comment.level > 0 }">
			                <div class="emptyBox">
			                    <div class="emptyBoxArrow"></div>
			                </div>
			            </c:if>
						<ul>
							<li class="commentId">${comment.cid}</li>
							<li class="commentContent">${fn:replace(comment.ccontent, cn, br)}</li>
							<li class="commentDate">${comment.cdate}</li>
						</ul>
	                    <div class= "commentBtn">
	                        <c:if test="${sessionScope.sessionID !=null and comment.level < 1}">
	                        <a href="#" onclick="cmReplyOpen(${comment.cnum})">답변</a>
	                        </c:if>
	                        <c:if test="${comment.cid == sessionScope.sessionID}">
	                        <a href="#" onclick="cmUpdateOpen(${comment.cnum})">수정</a>
	                        <a href="#" onclick="cmDeleteOpen(${comment.cnum})">삭제</a>
	                        </c:if>
	                    </div>
					</div>
		        </c:forEach>
		        </c:if>
		    </div>
		    <c:if test="${sessionScope.sessionID !=null}">
	            <!-- 아이디-->
	            <form id="writeCommentForm">
	            <input type="hidden" name="board" value="${board.num}">
	            <input type="hidden" name="cid" value="${sessionScope.sessionID}">
	                <!-- 본문 작성-->
	                <div>
	                    <textarea name="ccontent" rows="3" cols="50" ></textarea>
	                </div>
	                <!-- 댓글 등록 버튼 -->
	                <input type="button" value="댓글쓰기" onclick="writeCmt()">
	            </form>
	        </c:if>
	    </div>
    </div>
</body>
</html>
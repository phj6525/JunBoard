<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글목록</title>
</head>
<body>
<div class="boardwrap">
    <div class="board">
        <table>
            <tr>
                <td class="boardnum">No</td>
                <td class="boardsub">제목</td>
                <td class="boardid">작성자</td>
                <td class="boardday">작성일</td>
                <td class="boardck">조회수</td>
            </tr>
        <c:forEach var="board" items="${boardList }">
            <tr>
                <td class="bnum">${board.num}</td>
                <td class="bsub" align="left">
                    <c:if test="${board.lev > 0 }">
                        <c:forEach begin="1" end="${board.lev }">
                            &nbsp;&nbsp; <!-- 답변글제목 앞에 공백  -->
                        </c:forEach>
                        <span>Re :</span>
                    </c:if>
                    <a href="BoardDetailAction.brd?num=${board.num }&pageNum=${spage }">
                        ${board.subject}
                    </a>
                </td>
                <td>
                    <a href="#">
                        ${board.id}
                    </a>
                </td>
                <td>${board.date}</td>
                <td>${board.count}</td>
            </tr>
        </c:forEach>
        </table>
    </div>
    <div class="pageForm">
        <!-- 페이지 번호 -->
        <c:if test="${opt == null }">
	        <c:if test="${startPage != 1 }">
	            <a href="BoardListAction.brd?page=${startPage-1}">[Prev]</a>
	        </c:if>
	        <c:forEach var="pageNum" begin="${startPage }" end="${endPage }">
	            <c:if test="${pageNum == spage }">
	                ${pageNum}
	            </c:if>
	            <c:if test="${pageNum != spage }">
	                <a href="BoardListAction.brd?page=${pageNum }">${pageNum }</a>
	            </c:if>
	        </c:forEach>
	        <c:if test="${endPage != maxPage }">
	            <a href="BoardListAction.brd?page=${endPage+1}">[Next]</a>
	        </c:if>
        </c:if>
        <c:if test="${opt != null }">
            <c:if test="${startPage != 1 }">
                <a href="BoardListAction.brd?page=${startPage-1}&opt=${opt}&condition=${condition}">[Prev]</a>
            </c:if>
            <c:forEach var="pageNum" begin="${startPage }" end="${endPage }">
                <c:if test="${pageNum == spage }">
                    ${pageNum}
                </c:if>
                <c:if test="${pageNum != spage }">
                    <a href="BoardListAction.brd?page=${pageNum }&opt=${opt}&condition=${condition}">${pageNum }</a>
                </c:if>
            </c:forEach>
            <c:if test="${endPage != maxPage }">
                <a href="BoardListAction.brd?page=${endPage+1}&opt=${opt}&condition=${condition}">[Next]</a>
            </c:if>
        </c:if>
    </div>
    <div class="writeBox">
        <c:if test="${sessionScope.sessionID != null }">
            <input onclick="writeForm()" value="글쓰기">
        </c:if>
        <c:if test="${sessionScope.sessionID == null }">
            <p>글쓰기는 <a href="javascript:location.href='LoginForm.jun'">로그인</a> 후 가능</p>
        </c:if>
    </div>
    <div class="searchForm">
        <form>
            <select name="opt">
                <option value="0">제목</option>
                <option value="1">내용</option>
                <option value="2">제목+내용</option>
                <option value="3">글쓴이</option>
            </select>
            <input id="search" type="text" size="20" name="condition">
            <input id="searchbtn"type="submit" value="검색">
        </form>
    </div>
</div>
</body>
</html>
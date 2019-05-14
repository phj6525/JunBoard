<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시글 - 수정</title>
</head>
<body>
	<div class="writeBoard">
	<form method="post" action="BoardUpdateAction.brd?page=${pageNum}" name="boardForm" enctype="multipart/form-data">
		<input type="hidden" name="num" value="${board.num}">
		<input type="hidden" name="file" value="${board.file}">
		<table>
			<tr>
				<td class="boardleft">작성자</td>
				<td class="boardright">${board.id}</td>
			</tr>
			<tr>
				<td class="boardleft">제목</td>
				<td class="boardright">
				    <input class="updateSub" name="subject" type="text" size="70" maxlength="100" value="${board.subject}">
		        </td>
			</tr>
			<tr>
				<td class="boardleft">내용</td>
				<td class="boardright">
				    <textarea name="content" cols="72" rows="20">${board.content}</textarea>
			    </td>
			</tr>
			<!-- 답글이 아닐 경우에만 파일 첨부 가능하도록 처리 -->
			<c:if test="${board.lev==0}">
				<tr>
					<td class="boardleft">기존 파일</td>
					<td class="boardright">${board.file}</td>
				</tr>
				<tr>
					<td  class="boardfile">첨부파일</td>
					<td>
					   <input class="upload-name" value="File Select" disabled="disabled">
                       <label for="file">Upload File</label>
                       <input class="boardfileinput" type="file" name="file" id="file">
                    </td>
				</tr>
			</c:if>
		</table>
		<input type="reset" value="작성취소">
        <input type="submit" value="수정" >
        <input type="button" value="목록" onclick="javascript:location.href='BoardListAction.brd?page=${pageNum}'">
	</form>
	</div>
</body>
</html>
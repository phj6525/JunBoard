<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판 - 답글</title>
</head>
<body>
<div class="replyBoard">
    <form action="BoardReplyAction.brd?page=${page}" method="post" name="boardForm">
        <input type="hidden" name="id" value="${sessionScope.sessionID }">
        <input type="hidden" name="num" value="${board.num }">
        <input type="hidden" name="ref" value="${board.ref }">
        <input type="hidden" name="lev" value="${board.lev }">
        <input type="hidden" name="seq" value="${board.seq }">
        <table>
            <tr>
                <td class="boardleft">작성자</td>
                <td class="boardright">${sessionScope.sessionID }</td>
            </tr>
            <tr>
                <td class="boardleft">제목</td>
                <td class="boardright"><input class="replySub" name="subject" type="text" size="70" maxlength="100" value=""></td>
            </tr>
            <tr>
                <td class="boardleft">내용</td>
                <td class="boardright"><textarea name="content" cols="72" rows="20"></textarea></td>
            </tr>
        </table>
            <input type="reset" value="작성취소">
            <input type="submit" value="등록">
            <input type="button" value="뒤로" onclick="javascript:history.go(-1)">
    </form>
</div>
</body>
</html>
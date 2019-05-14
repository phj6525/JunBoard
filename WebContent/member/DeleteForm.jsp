<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
</head>
<body onload="warningNeon()">
	<div class="delete">
	<h1>회원 탈퇴</h1>
	    <form name="deleteform" method="post" action="MemberDeleteAction.jun" onsubmit="return deleteCheckPW()">
            <label for="password"><span id="warning">주의</span> 계정 정보가 모두 삭제됩니다</label>
            <input id="password" type="password" name="password" maxlength="50" placeholder="비밀번호를 입력하시고 확인을 눌러주세요">
	        <input id="deleteInfo" type="submit" value="확인">
	        <input type="button" onclick="history.go(-1)" value="이전">
            <input type="button" onclick="goIndex()" value="메인으로">
            <c:set var="msg" value="${requestScope.DeleteFail }"/>
            <c:if test="${msg != null }">
                <c:choose>
                    <c:when test="${msg == '0' }">
                        <p>비밀번호가 다릅니다.</p>
                    </c:when>
                </c:choose>
            </c:if>
	    </form>
	</div>
</body>
</html>
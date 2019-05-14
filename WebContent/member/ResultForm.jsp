<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>결과 페이지</title>
</head>
<body>
	<div class="result">
	    <c:set var="msg" value="${sessionScope.msg }" scope="session"/>
	    <c:choose>
	        <c:when test="${msg != null && msg == '1' }">
	            <h1>회원가입을 축하드립니다</h1>
	            <c:remove var="msg" scope="session"/>
	        </c:when>
	        <c:when test="${msg != null && msg == '2' }">
	            <h1>회원정보가 수정되었습니다</h1>
	            <c:remove var="msg" scope="session"/>
	        </c:when>
	        <c:otherwise>
	            <h1>회원정보가 삭제되었습니다</h1>
	        </c:otherwise>
	    </c:choose>
	    <button onclick="goIndex()">메인으로</button>
	</div>
</body>
</html>
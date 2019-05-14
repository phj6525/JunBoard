<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
    <div class="first">
	    <c:if test="${sessionScope.sessionID == null }">
		    <p class="f30">Welcome to <span>Jun's</span> World</p>
		</c:if>
	    <c:if test="${sessionScope.sessionID != null }">
	        <p class="f30"><span>${sessionScope.sessionID }</span>'s Hello</p>
	    </c:if>
    </div>
</body>
</html>
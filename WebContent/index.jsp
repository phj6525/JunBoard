<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Jun's World</title>
<link rel="stylesheet" href="css/default.css">
</head>
<body>
    <header class="clearfix">
        <jsp:include page="Header.jsp"/>
    </header>
    <div class="wrap">
        <div class="main clearfix">
            <c:set var="contentPage" value="${param.contentPage }"/>
            <c:if test="${contentPage == null }">
                <jsp:include page="FirstView.jsp"/>
            </c:if>
            <c:if test="${contentPage != null }">
                <jsp:include page="${contentPage }"/>
            </c:if>
        </div>
    </div>
        <footer class="clearfix">
            <jsp:include page="Footer.jsp"/>
        </footer>
<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="js/default.js"></script>
</body>
</html>
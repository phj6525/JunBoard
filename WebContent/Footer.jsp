<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>하단 영역</title>
</head>
<body>
<div class="wrap clearfix">
    <div class="todaybox">
        <p class="today">TODAY : ${sessionScope.todayCount } / ${sessionScope.totalCount }</p>
    </div>
    <div class="copyrightbox">
        <p><small>ⓒ2019. Jun. all rights reserved.</small></p>
    </div>
</div>
</body>
</html>
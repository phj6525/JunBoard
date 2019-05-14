<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그아웃</title>
</head>
<body>
<%
session.invalidate(); //모든 세션 정보 삭제
response.sendRedirect("../../index.jsp"); // 메인으로 다시 이동
%>
</body>
</html>
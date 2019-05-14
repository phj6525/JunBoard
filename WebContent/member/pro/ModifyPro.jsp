<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.MemberDAO"%>
<%@ page import="member.model.MemberBean"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 정보 수정 처리</title>
</head>
<body>
    <jsp:useBean id="memberBean" class="member.model.MemberBean"/>
    <jsp:setProperty property="*" name="memberBean"/>
    <%
    String id = (String)session.getAttribute("sessionID");
    memberBean.setId(id);
    
    MemberDAO dao = MemberDAO.getInstance();
    dao.updateMember(memberBean);
    %>
    <h1>회원 정보가 수정 되었습니다.</h1>
    <button onclick="goIndex()">메인으로</button>
</body>
</html>
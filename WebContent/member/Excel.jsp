<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="member.model.MemberBean" %>
<%@ page import="member.model.MemberDAO" %>
<%
MemberDAO dao = MemberDAO.getInstance();
ArrayList<MemberBean> memberList = dao.getMemberList();
request.setAttribute("memberList", memberList);
    response.setHeader("Content-Disposition","attachment; filename=memberList.xls");
    response.setHeader("Content-Description","JSP Generated Date");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>엑셀</title>
</head>
<body>
    <table>
        <tr>
            <td>아이디</td>
            <td>비밀번호</td>
            <td>이름</td>
            <td>생년월일</td>
            <td>성별</td>
            <td>이메일</td>
            <td>휴대전화</td>
            <td>가입일</td>
        </tr>
        <c:forEach var="member" items="${memberList }">
            <tr>
                <td>${member.id }</td>
                <td>${member.password }</td>
                <td>${member.name }</td>
                <td>${member.birthyy }</td>
                <td>${member.gender }</td>
                <td>${member.mail_id }</td>
                <td>${member.phone }</td>
                <td>${member.reg }</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
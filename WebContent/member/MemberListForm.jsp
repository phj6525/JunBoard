<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="member.model.MemberBean" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 리스트 - 관리자 전용</title>
</head>
<body>
    <div class="allMemberBox">
    <table>
        <tr class="ambTitle">
            <td>아이디</td>
            <td>비밀번호</td>
            <td>이름</td>
            <td>생년월일</td>
            <td>성별</td>
            <td>이메일</td>
            <td>휴대전화</td>
            <td>가입일</td>
        </tr>
        <c:set var="memberList" value="${requestScope.memberList }"/>
	    <c:forEach var="member" items="${memberList }">
	        <tr class="ambSub">
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
    <a href="member/Excel.jsp">Excel 파일로 다운로드</a>
    </div>
</body>
</html>
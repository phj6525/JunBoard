<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="member.model.MemberDAO"%>
<%@ page import="member.model.MemberBean"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>내 정보</title>
</head>
<body>
	<c:set var="member" value="${requestScope.memberInfo }"/>
	<h1>My Account</h1>
	<div class="myAccount">
		<table>
			<tr>
				<td class="myAccntLeft">아이디</td>
				<td>${member.id }</td>
			</tr>
			<tr>
				<td class="myAccntLeft">비밀번호</td>
				<td>${member.password }</td>
			</tr>
			<tr>
				<td class="myAccntLeft">이름</td>
				<td>${member.name }</td>
			</tr>
			<tr>
				<td class="myAccntLeft">생년월일</td>
				<td>${member.birthyy }년 ${member.birthmm }월 ${member.birthdd }일</td>
			</tr>
			<tr>
				<td class="myAccntLeft">성별</td>
				<td>${member.gender }</td>
			</tr>
			<tr>
				<td class="myAccntLeft">이메일</td>
				<td>${member.mail_id }@${member.mail_addr }</td>
			</tr>
			<tr>
				<td class="myAccntLeft">휴대전화</td>
				<td>${member.phone }</td>
			</tr>
		</table>
		<button class="myAccntbtn" onclick="changeForm(0)">뒤로</button>
		<button class="myAccntbtn" onclick="changeForm(1)">변경</button>
		<button class="myAccntbtn" onclick="changeForm(2)">탈퇴</button>
	</div>
</body>
</html>
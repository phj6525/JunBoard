<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="member.model.MemberDAO"%>
<%@ page import="member.model.MemberBean"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
</head>
<body onload="init()">
	<c:set var="member" value="${requestScope.memberInfo }"/>
	<h1>회원 정보 수정</h1>
	<div class="modify">
		<form method="post"	action="MemberModifyAction.jun" name="userInfo" onsubmit="return checkPW()">
			<table>
				<tr>
					<td class="modifyLeft">아이디</td>
					<td>${member.id }</td>
				</tr>
				<tr>
					<td class="modifyLeft">비밀번호</td>
					<td><input id="password" type="password" name="password" maxlength="50" value="${member.password }"></td>
				</tr>
				<tr>
					<td class="modifyLeft">이름</td>
					<td>${member.name }</td>
				</tr>
				<tr>
					<td class="modifyLeft">성별</td>
					<td>${member.gender }</td>
				</tr>
				<tr>
					<td class="modifyLeft">생년월일</td>
					<td>
					    ${member.birthyy }년
					    ${member.birthmm }월
						${member.birthdd }일
					</td>
				</tr>
				<tr>
					<td class="modifyLeft">이메일</td>
					<td>
					    <input type="text" name="mail_id" id="mail_id" maxlength="50" value="${member.mail_id }">@
					    <select name="mail_addr" id="mail_addr">
							<option value="naver.com">naver.com</option>
							<option value="gmail.com">gmail.com</option>
							<option value="daum.net">daum.net</option>
							<option value="nate.com">nate.com</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td class="modifyLeft">휴대전화</td>
					<td>
					    <input id="phone" type="text" name="phone" value="${member.phone }" />
				    </td>
				</tr>
			</table>
			<input type="submit" value="수정"/>
	    	<input type="button" onclick="history.go(-1)" value="이전">
	    	<input type="button" onclick="goIndex()" value="메인으로">
		</form>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<% request.setCharacterEncoding("utf-8"); %>
    <div class="clearfix">
        <form class="loginform" name="loginInfo" method="post" action="MemberLoginAction.jun" onsubmit="return checkLogin()">
            <table>
                <tr>
                    <td>
                        <label for="id">ID</label>
                        <input type="text" name="id" id="id">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="password">Password</label>
                        <input type="password" name="password" id="password">
                    </td>
                </tr>
            </table>
        <!-- 아이디, 비밀번호가 틀릴경우 화면에 메세지 표시
        LoginPro.jsp 에서 로그인 처리 결과에 따른 메세지를 보낸다. -->
        <c:set var="msg" value="${requestScope.fail }"/>
        <c:if test="${msg != null }">
            <c:choose>
                <c:when test="${msg == '0' }">
                    <p>비밀번호를 확인해 주세요.</p>
                </c:when>
                <c:otherwise>
                    <p>아이디를 확인하시거나 회원가입을 해주세요.</p>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${msg == null }">
            <p>처음 방문이신가요? 회원가입을 해주세요.</p>
        </c:if>
            <input class="loginbutton" type="submit" value="Login">
            <input class="homebutton" type="button" value="Sign Up" onclick="goSignUp()">
        </form>
    </div>
</body>
</html>
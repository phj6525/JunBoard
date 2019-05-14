<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav>
    <h1 class="logo">
        <a href="#" id="neon">Jun's World</a>
    </h1>
    <ul>
        <li><a onclick="changeView(0)">HOME</a></li>
        <c:if test="${sessionScope.sessionID == null }">
        <li><a onclick="changeView(1)">Login</a></li>
        <li><a onclick="changeView(2)">Sign Up</a></li>
        </c:if>
        <c:if test="${sessionScope.sessionID != null }">
        <li><a onclick="changeView(3)">Logout</a></li>
        <li><a onclick="changeView(4)">My Account</a></li>
        </c:if>
        <li><a onclick="changeView(6)">Board</a></li>
        <c:if test="${sessionScope.sessionID == 'admin' }">
        <li><a onclick="changeView(5)">회원보기</a></li>
        </c:if>
        <li><a href="phj6525.cafe24.com/portfolio">JS Portfolio</a></li>
    </ul>
</nav>
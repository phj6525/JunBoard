<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.MemberBean" %>
<%@ page import="member.model.MemberDAO" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 가입 처리</title>
</head>
<body>
    <%
    	//한글 깨짐을 방지하기 위한 인코딩
        request.setCharacterEncoding("utf-8");
    %>
    <jsp:useBean id="memberBean" class="member.model.MemberBean"/>
    <jsp:setProperty property="*" name="memberBean"/>
    <%
    MemberDAO dao = MemberDAO.getInstance();
    dao.insertMember(memberBean);
    %>
    
    <div class="wrap">
        <h1>회원 가입 정보를 확인하세요</h1>
        <h2><%=memberBean.getName() %>님</h2>
        <p>가입을 축하드립니다</p>
        <table>
            <tr>
                <td>아이디</td>
                <td><%=memberBean.getId() %></td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td><%=memberBean.getPassword() %></td>
            </tr>
            <tr>
                <td>이름</td>
                <td><%=memberBean.getName() %></td>
            </tr>
            <tr>
                <td>생일</td>
                <td>
                    <%=memberBean.getBirthyy() %>년
                    <%=memberBean.getBirthmm() %>월
                    <%=memberBean.getBirthdd() %>일
                </td>
            </tr>
            <tr>
                <td>성별</td>
                <td>
                    <%=memberBean.getGender() %>
                </td>
            </tr>
            <tr>
                <td>이메일</td>
                <td>
                    <%=memberBean.getMail_id() %>@<%=memberBean.getMail_addr() %>
                </td>
            </tr>
            <tr>
                <td>휴대전화</td>
                <td><%=memberBean.getPhone() %></td>
            </tr>
        </table>
        <button onclick="goIndex()">확인</button>
    </div>
<script src="../../default.js"></script>
</body>
</html>
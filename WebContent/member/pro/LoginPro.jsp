<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.MemberDAO" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인 처리</title>
</head>
<body>
<%
request.setCharacterEncoding("utf-8");
//로그인 화면에 입력된 아이디와 비밀번호를 가져온다.
String id = request.getParameter("id");
String pw = request.getParameter("password");
//db에서 아이디, 비밀번호 확인
MemberDAO dao = MemberDAO.getInstance();
int check = dao.loginCheck(id, pw);
//URL 및 로그인 관련 전달 메시지
String msg = "";
if(check == 1) {//로그인 성공
	//세션에 현재 아이디 세팅
	session.setAttribute("sessionID", id);
    msg = "../../index.jsp";
} else if(check == 0) { //비밀번호가 틀림
	msg = "../../index.jsp?contentPage=member/view/LoginForm.jsp?msg=0";
} else { //아이디가 틀림
	msg = "../../index.jsp?contentPage=member/view/LoginForm.jsp?msg=-1";
}
//sendRedirect(String URL) : 해당 URL로 이동
//URL뒤에 get방식 처럼 데이터를 전달 가능
response.sendRedirect(msg);
%>

</body>
</html>
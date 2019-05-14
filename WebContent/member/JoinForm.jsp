<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
</head>
<body>
    <form class="joinform" method="post" action="MemberJoinAction.jun" name="userInfo" onsubmit="return JoinFormCheck()">
        <ul>
            <li>
                <label for="id">아이디</label>
                <input type="text" id="id" name="id" maxlength="20">
                <input type="button" class="idchk" onclick="openIDChk()" value="중복 확인">
                <input type="hidden" name="idDuplication" value="idUncheck">
            </li>
            <li>
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" maxlength="20">
            </li>
            <li>
             <label for="passwordcheck">비밀번호 재확인</label>
             <input type="password" id="passwordcheck" name="passwordcheck" maxlength="20">
            </li>
            <li>
                <label for="name">이름</label>
                <input type="text" id="name" name="name" maxlength="40">
            </li>
            <li>
                <label for="birth">생년월일</label>
                <input type="text" id="birth" name="birthyy" maxlength="4" placeholder="년(4자)">
                <select name="birthmm">
                    <option value="">월</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
                <input class="birthdd" type="text" name="birthdd" maxlength="2" placeholder="일">
                <select id="gender" name="gender">
                    <option value="">성별</option>
                    <option value="남자">남자</option>
                    <option value="여자">여자</option>
                </select>
            </li>
            <li>
                <label for="mail">이메일</label>
                <input type="text" id="mail" name="mail_id" maxlength="30">
                <p>@</p>
                <select class="mail_addr" name="mail_addr">
                    <option>naver.com</option>
                    <option>daum.net</option>
                    <option>gmail.com</option>
                    <option>nate.com</option>
                </select>
            </li>
            <li>
                <label for="phone">휴대전화</label>
                <input id="phone" type="text" name="phone">
            </li>
            <li class="joinbtnbox">
                <input class="joinbtn" type="submit" value="가입">
                <button onclick="goIndex()">취소</button>
            </li>
        </ul>
    </form>
</body>
</html>
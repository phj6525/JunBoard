<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>댓글 답변</title>
<style>
body {
background: #000000;
text-align: center;
}
.commentReply h1 {
width: 300px;
margin: 10px auto;
font-size: 20px;
color: #ffffff;
}
#commentReplyForm {
width: 500px;
height: 250px;
margin: auto;
}
#replyInfoForm textarea {
width: 400px;
height: 200px;
overflow: auto;
background: rgba(0,0,0,0);
color: #ffffff;
font-size: 13px;
border: 1px solid rgb(255,215,0);
margin: 15px auto;
display: block;
padding: 20px;
}
#replyInfoForm input {
width: 150px;
background: #000000;
color: #ffffff;
font-size: 15px;
font-weight: bold;
border:2px solid;
border-image: linear-gradient(to left, #743ad5 0%, #d53a9d 100%);
border-image-slice: 1;
cursor: pointer;
}
#replyInfoForm input:hover {
border: 2px solid rgb(255,215,0);
color: rgb(255,215,0);
}
</style>
</head>
<body>
	<div class="wrap commentReply">
        <h1>댓글 답변</h1>
		<div id="commentReplyForm">
			<form id="replyInfoForm" name="replyInfo" target="parentForm">
				<textarea rows="7" cols="50" name="ccontent"></textarea>
				<input type="button" value="등록" onclick="checkValueComment()">
				<input type="button" value="창닫기" onclick="window.close()">
			</form>
		</div>
	</div>
<script>
	var httpRequest = null;
	// httpRequest 객체 생성
	function getXMLHttpRequest(){
	    var httpRequest = null;
	
	    if(window.ActiveXObject){
	        try{
	            httpRequest = new ActiveXObject("Msxml2.XMLHTTP");    
	        } catch(e) {
	            try{
	                httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	            } catch (e2) { httpRequest = null; }
	        }
	    }
	    else if(window.XMLHttpRequest){
	        httpRequest = new window.XMLHttpRequest();
	    }
	    return httpRequest;    
	}
	//댓글
	function checkValueComment() {
	    var form = document.getElementById("replyInfoForm");
	    // 전송할 값을 변수에 담는다.    
	    var cnum = "${comment.cnum}";
	    var board = "${comment.board}";
	    var cid = "${sessionScope.sessionID}";
	    var ccontent = form.ccontent.value;
	    var cref = "${comment.cref}";
	    var level = "${comment.level}";
	    var cseq = "${comment.cseq}";
	    
	    if(!ccontent) {
	        alert("내용을 입력하세요");
	        return false;
	    } else {
	        var param="cnum="+cnum+"&board="+board+"&cid="+cid+"&ccontent="+ccontent+"&cref="+cref+"&level="+level+"&cseq="+cseq;
	        httpRequest = getXMLHttpRequest();
	        httpRequest.onreadystatechange = checkFuncComment;
	        httpRequest.open("POST", "CommentReplyAction.cm", true);
	        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8'); 
	        httpRequest.send(param);
	    }
	}
	
	function checkFuncComment(){
	    if(httpRequest.readyState == 4){
	        // 결과값을 가져온다.
	        var resultText = httpRequest.responseText;
	        if(resultText == 1){
	            if (opener != null) {
	                // 부모창 새로고침
	                window.opener.document.location.reload(); 
	                opener.replyForm = null;
	                self.close();
	            }
	        }
	    }
	}
</script>
</body>
</html>
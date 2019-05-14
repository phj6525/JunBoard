<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>댓글 수정</title>
<style>
body {
background: #000000;
text-align: center;
}
.commentUpdate h1 {
width: 300px;
margin: 10px auto;
font-size: 20px;
color: #ffffff;
}
#commentUpdateForm {
width: 500px;
height: 250px;
margin: auto;
}
#commentUpdate textarea {
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
#commentUpdate input {
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
#commentUpdate input:hover {
border: 2px solid rgb(255,215,0);
color: rgb(255,215,0);
}
</style>
</head>
<body>
    <div class="wrap commentUpdate">
        <h1>댓글 수정</h1> 
        <div id="commentUpdateForm">
            <form id="commentUpdate" name="updateInfo" target="parentForm">        
                <textarea rows="7" cols="70" name="ccontent">${comment.ccontent}</textarea>
                <input type="button" value="등록" onclick="checkValue()">
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
	function checkValue() {
	    var form = document.forms[0];
	    // 전송할 값을 변수에 담는다.
	    var cnum = "${comment.cnum}";
	    var ccontent = form.ccontent.value
	    
	    if(!ccontent) {
	        alert("내용을 입력하세요");
	        return false;
	    }
	    else{
	        var param="cnum="+cnum+"&ccontent="+ccontent;
	        httpRequest = getXMLHttpRequest();
	        httpRequest.onreadystatechange = checkFunc;
	        httpRequest.open("POST", "CommentUpdateAction.cm", true);    
	        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8'); 
	        httpRequest.send(param);
	    }
	}
	
	function checkFunc(){
	    if(httpRequest.readyState == 4){
	        // 결과값을 가져온다.
	        var resultText = httpRequest.responseText;
	        if(resultText == 1){
	            if (opener != null) {
	                // 부모창 새로고침
	                window.opener.document.location.reload(); 
	                opener.updateForm = null;
	                self.close();
	            }
	        }
	    }
	}
</script>
</body>
</html>
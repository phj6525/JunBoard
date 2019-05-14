<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 - 글쓰기</title>
</head>
<body>
    <div class="writeBoard">
	    <form method="post" action="BoardWriteAction.brd" name="boardForm" enctype="multipart/form-data">
	        <input type="hidden" name="id" value="${sessionScope.sessionID }">
	        <table>
	            <tr>
	                <td class="boardleft">제목</td>
	                <td class="boardright">
	                    <input class="writeSub" name="subject" type="text" size="70" maxlength="100" value=""/>
	                </td>
	            </tr>
	            <tr>
	                <td class="boardleft">내용</td>
	                <td class="boardright">
	                    <textarea name="content" cols="72" rows="20"></textarea>
	                </td>
	            </tr>
	            <tr>
	                <td class="boardfile">파일첨부</td>
	                <td>
	                    <input class="upload-name" value="File Select" disabled="disabled">
	                    <label for="file">Upload File</label>
	                    <input class="boardfileinput" type="file" name="file" id="file">
	                </td>
	            </tr>
	        </table>
            <input type="submit" value="작성">
            <input type="button" value="뒤로" onclick="javascript:history.go(-1)">
	    </form>
    </div>
<script src="http://code.jquery.com/jquery.min.js"></script>
<script>
$(document).ready(function(){
	var fileTarget = $('.writeBoard .boardfileinput');
	fileTarget.on('change', function(){ // 값이 변경되면 
		if(window.FileReader){ // modern browser 
			var filename = $(this)[0].files[0].name; 
		} else { // old IE 
			var filename = $(this).val().split('/').pop().split('\\').pop(); // 파일명만 추출 
		} // 추출한 파일명 삽입 
		$(this).siblings('.upload-name').val(filename); 
	}); 
});
</script>
</body>
</html>
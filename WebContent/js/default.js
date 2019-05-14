//Header.jsp 관련 자바스크립트
	function changeView(value) {
		if(value == "0") { //HOME 버튼 클릭시 index페이지로 이동
			location.href = "index.jun";
		}
		if(value == "1") { //로그인 버튼 클릭시 로그인 화면으로 이동
			location.href = "LoginForm.jun";
		}
		if(value == "2") { //회원가입 버튼 클릭시 회원가입 화면으로 이동
			location.href = "JoinForm.jun";
		}
		if(value == "3") { //로그아웃 버튼 클릭시 로그아웃 처리
			location.href = "MemberLogoutAction.jun";
		}
		if(value == "4") { //내정보 버튼 클릭시 나의 정보를 보여주는 화면으로 이동
			location.href = "MemberInfoAction.jun";
		}
		if(value == "5") { //관리자 전용 모든 회원 보기 이동
			location.href = "MemberListAction.jun";
		}
		if(value == "6") { //게시판 버튼 클릭시 게시판 글목록으로 이동
			location.href = "BoardListAction.brd";
		}
	}

//JoinForm.jsp 관련 자바스크립트
	function JoinFormCheck() {
		var form = document.userInfo;
		
		if(!form.id.value) {
			alert("아이디를 입력하세요.");
			return false;
		}
		if(form.idDuplication.value != "idCheck") {
			alert("아이디 중복체크를 해주세요.");
			return false;
		}
		if(!form.password.value) {
			alert("비밀번호를 입력하세요.");
			return false;
		}
		if(form.password.value != form.passwordcheck.value) {
			alert("비밀번호를 동일하게 입력하세요");
			return false;
		}
		if(!form.name.value) {
			alert("이름을 입력하세요.");
			return false;
		}
		if(!form.birthyy.value) {
			alert("년도를 입력하세요.");
			return false;
		}
		if(isNaN(form.birthyy.value)) {
			alert("년도는 숫자만 입력가능합니다.");
			return false;
		}
		if(form.birthmm.value == "00") {
			alert("월을 선택하세요.");
			return false;
		}
		if(!form.birthdd.value) {
			alert("날짜를 입력하세요.");
			return false;
		}
		if(isNaN(form.birthdd.value)) {
			alert("날짜는 숫자만 입력가능합니다");
			return false;
		}
		if(!form.mail_id.value) {
			alert("메일 주소를 입력하세요");
			return false;
		}
		if(isNaN(form.phone.value)) {
			alert("- 제외한 숫자만 입력하세요.");
			return false;
		}
	}
	//아이디 중복체크 화면
	function openIDChk() {
		window.name = "parentForm";
		window.open("member/IdCheckForm.jsp", "chkForm", "width=500, height=300, resizable=no, scrollbars=no");
	}
	function inputIdChk() {
		document.userInfo.idDuplication.value = "idUncheck";
	}
	
	
//LoginForm.jsp 관련 자바스크립트
	function checkLogin() {
		inputForm = eval("document.loginInfo");
		if(!inputForm.id.value) {
			alert("아이디를 입력하세요");
			inputForm.id.focus();
			return false;
		}
		if(!inputForm.password.value) {
			alert("비밀번호를 입력하세요");
			inputForm.password.focus();
			return false;
		}
	}
	function goSignUp() { //버튼 클릭시 메인으로 이동
		window.location.href = "JoinForm.jun";
	}
	function goIndex() {
		window.location.href = "index.jun";
	}
	
//UserInfoForm.jsp 관련 자바스크립트
	function changeForm(value){
        if(value == "0"){
            location.href="index.jun";
        }else if(value == "1"){
            location.href="MemberModifyFormAction.jun";
        }else if(value == "2"){
            location.href="DeleteForm.jun";
        }
    }
	
//ModifyForm.jsp 관련 자바스크립트
	function init(){
        setComboValue("<%=member.getMail_addr %>");
    }

    function setComboValue(val) {
        var selectMail = document.getElementById('mail_addr'); // select 아이디를 가져온다.
        for (i = 0, j = selectMail.length; i < j; i++) { // select 하단 option 수만큼 반복문 돌린다.
            if (selectMail.options[i].value == val) { // 입력된값과 option의 value가 같은지 비교
                selectMail.options[i].selected = true; // 같은경우라면 체크되도록 한다.
                break;
            }
        }
    }
    // 비밀번호 입력여부 체크
    function checkPW() {
        if(!document.userInfo.password.value){
            alert("비밀번호를 입력하세요.");
            return false;
        }
    }
    function deleteCheckPW() {
    	if(!document.deleteform.password.value) {
    		alert("비밀번호를 다시 확인하세요.")
    		return false;
    	}
    }

//게시판 관련 자바스크립트
    function writeForm() {
		location.href = "BoardWriteForm.brd";
	}
    //답글달기
    function boardChangeForm(value) {
    	if (value == 0) {
    		location.href="BoardListAction.brd?page=${pageNum}";
    	} else if (value == 1) {
    		location.href="BoardReplyFormAction.brd?num=${board.num}&page=${pageNum}";
    	}
    }
    
//댓글 관련 자바스크립트
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
    
    function writeCmt() {
        var form = document.getElementById("writeCommentForm");
        var board = form.board.value
        var cid = form.cid.value
        var ccontent = form.ccontent.value;
        if(!ccontent) {
            alert("내용을 입력하세요.");
            return false;
        } else {
            var param="board="+board+"&cid="+cid+"&ccontent="+ccontent;
            httpRequest = getXMLHttpRequest();
            httpRequest.onreadystatechange = checkFunc;
            httpRequest.open("POST", "CommentWriteAction.cm", true);
            httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8'); 
            httpRequest.send(param);
        }
    }
    
    function checkFunc(){
        if(httpRequest.readyState == 4){
            // 결과값을 가져온다.
            var resultText = httpRequest.responseText;
            if(resultText == 1){ 
                document.location.reload(); // 상세보기 창 새로고침
            }
        }
    }
    
    function cmReplyOpen(cnum) {
        var userId = '${sessionScope.sessionID}';
        if(userId == "" || userId == null){
            alert("로그인후 사용가능합니다.");
            return false;
        } else {
            // 댓글 답변창 open
            window.name = "parentForm";
            window.open("CommentReplyFormAction.cm?num="+cnum, "replyForm", "width=570, height=350, resizable = no, scrollbars = no");
        }
    }
    
    function cmDeleteOpen(cnum) {
    	var msg = confirm("댓글을 삭제합니다.");
    	if (msg == true) {
    		deleteCmt(cnum);
    	} else {
    		return false;
    	}
    }
    
    function deleteCmt(cnum) {
    	var param = "cnum="+cnum;
    	httpRequest = getXMLHttpRequest();
    	httpRequest.onreadystatechange = checkFunc;
    	httpRequest.open("POST", "CommentDeleteAction.cm", true);
    	httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8');
    	httpRequest.send(param);
    }
    
    function cmUpdateOpen(cnum) {
    	window.name = "parentForm";
    	window.open("CommentUpdateFormAction.cm?num="+cnum, "updateForm", "width=570, height=350, resizable = no, scrollbars = no");
    }

    
//홈페이지 꾸미기 자바스크립트
    $( document ).ready(function() {
        neon();
    });
    var	el = document.getElementById('neon');
    var cnt = 0;
    function neon() {//네온 효과
    	if(cnt == 7) {
    		cnt = 0;
    	}
    	cnt += 0.01;
    	el.style.color = "rgba"+"("+255+","+215+","+0+","+ (Math.sin(cnt)+1)/2 +")";
    	setTimeout("neon()",10);
    }
    var warning = document.getElementById('warning');
    var fastcnt = 0;
    function warningNeon() {
    	if(fastcnt == 7) {
    		fastcnt = 0;
    	}
    	fastcnt += 0.1;
    	warning.style.color = "rgba"+"("+255+","+0+","+0+","+ (Math.sin(fastcnt)+1)/2 +")";
    	setTimeout("warningNeon()", 10);
    }
    
    function CommentEvent() {
	    var mask = document.querySelector('.mask');
	    var showComment = document.querySelector('.show');
	    
	    function toggleComment() {
	    	mask.classList.toggle("mask-show");
	    }
	    function windowOnclick(event) {
	    	if(event.target === mask) {
	    		toggleComment();
	    	}
	    }
	    
	    showComment.addEventListener("click", toggleComment);
	    window.addEventListener("click", windowOnclick);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
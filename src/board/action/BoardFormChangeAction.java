package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.action.Action;
import common.action.ActionForward;

/**
 * 단순한 화면전환을 처리하기 위한 클래스
 *
 */
public class BoardFormChangeAction implements Action {
	private String form = "index.jsp?contentPage=board/";
	private String path;
	/**
	 * 명령어로부터 다음 이동할 페이지 경로를 생성
	 */
	public void setCommand(String command) {
		int idx = command.indexOf(".");
		path = command.substring(0, idx)+".jsp";
	}
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 단순한 화면 전환이기 때문에 forward 사용
		 * DB나 세션에 변화가 없으므로 setRedirect(false)
		 */
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		
		if(path.equals("index.jsp")) //메인화면일 경우 경로가 다르기 때문에 따로 지정
			forward.setNextPath(path);
		else
			forward.setNextPath(form+path);
		return forward;
	}
}

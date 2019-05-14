package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.action.Action;
import common.action.ActionForward;

public class MemberLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 사용자가 로그아웃 했을 때 세션의 모든 정보를 삭제한다.
		 * 세션의 변화가 있기 때문에 setRedirect(true)
		 * 로그아웃 후에 메인화면으로 이동
		 */
		ActionForward forward = new ActionForward();
		/**
		 * 기존의 세션을 삭제하는 부분에서 세션에 담긴 ID값만 삭제하도록 변경
		 * 세션을 삭제하면 세션이 변경되어 로그아웃 시 방문자 수가 증가하게 되므로
		 * ID값만 삭제하도록 처리
		 */
		//request.getSession().invalidate();
		request.getSession().removeAttribute("sessionID");
		forward.setRedirect(true);
		forward.setNextPath("index.jun");
		return forward;
	}

}

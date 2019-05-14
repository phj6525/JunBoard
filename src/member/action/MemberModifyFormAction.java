package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.action.Action;
import common.action.ActionForward;
import member.model.MemberBean;
import member.model.MemberDAO;

public class MemberModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 회원 정보 수정 화면에 현재 회원 정보를 출력
		 * 세션이 가지고 있는 로그인 아이디 정보를 가져옴
		 * 수정할 회원 정보를 가져옴
		 * 세션에 변화가 없으므로 setRedirect(false)
		 */
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberBean member = dao.getUserInfo(id);
		
		request.setAttribute("memberInfo", member);
		
		forward.setRedirect(false);
		forward.setNextPath("ModifyForm.jun");
		
		return forward;
	}

}

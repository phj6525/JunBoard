package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.action.Action;
import common.action.ActionForward;
import member.model.MemberBean;
import member.model.MemberDAO;

public class MemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 로그인한 아이디를 이용해서 사용자의 정보를 찾는다.
		 * 찾은 사용자 정보를 request에 담고 UserInfoForm.jsp에 전달
		 * 세션에 변화가 없으므로 setRedirect(false)
		 */
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberBean member = dao.getUserInfo(id);
		request.setAttribute("memberInfo", member);
		
		forward.setRedirect(false);
		forward.setNextPath("UserInfoForm.jun");
		
		return forward;
	}

}

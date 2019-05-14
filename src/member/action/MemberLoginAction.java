package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.action.Action;
import common.action.ActionForward;
import member.model.MemberDAO;

public class MemberLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		//아이디와 비밀번호를 가져온다.
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		//가져온 아이디와 비밀번호를 DB에서 확인
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.loginCheck(id, password);
		/**
		 * 비밀번호가 잘못되거나 아이디가 없는 경우 로그인 실패에 대한 처리
		 * 로그인이 실패 했을 경우 세션에 변화가 없으므로 setRedirect(false)
		 * 로그인이 실패하면 메시지를 request에 담고 로그인 화면으로 이동
		 * 로그인 화면에서는 request에 담긴 실패 메시지를 화면에 출력
		 * 로그인에 성공하면 아이디를 세션에 담고 메인화면으로 이동
		 */
		if(check == 0) {//비밀번호가 틀림 -> 다시 로그인 화면으로 이동
			// 로그인 실패시 메시지를 request에 담는다.
			request.setAttribute("fail", "0");
			forward.setRedirect(false);
			forward.setNextPath("LoginForm.jun");
		} else if (check == -1) {//아이디가 없음 -> 다시 로그인 화면으로 이동
			request.setAttribute("fail", "-1");
			forward.setRedirect(false);
			forward.setNextPath("LoginForm.jun");
		} else { //로그인 성공
			//세션에 아이디를 저장
			session.setAttribute("sessionID", id);
			//로그인 성공후 index.jsp로 이동
			forward.setRedirect(true);
			forward.setNextPath("index.jun");
		}
		return forward;
	}

}

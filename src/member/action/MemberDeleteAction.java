package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.action.Action;
import common.action.ActionForward;
import member.model.MemberDAO;

public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 아이디와 비밀번호를 이용해 회원 삭제 작업 처리
		 * 회원 삭제가 정상적으로 처리된 경우 세션 정보 삭제
		 * 세션과 DB에 변화가 있으므로 setRedirect(true)
		 * 처리 결과 표시 setNextPath("Result.jun")로 경로 지정
		 */
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		String password = request.getParameter("password");
		
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.deleteMember(id, password);
		
		if (check == 1) {
			/**
			 * 기존의 세션을 삭제하는 부분에서 세션에 담긴 ID값만 삭제하도록 변경
			 * 세션을 삭제하면 세션이 변경되어 로그아웃 시 방문자 수가 증가하게 되므로
			 * ID값만 삭제하도록 처리
			 */
			//session.invalidate();
			session.removeAttribute("sessionID");
			
			forward.setRedirect(true);
			forward.setNextPath("ResultForm.jun");
		} else if (check == 0) {
			request.setAttribute("DeleteFail", "0");
			forward.setRedirect(false);
			forward.setNextPath("DeleteForm.jun");
			System.out.println("회원 삭제 실패");
		}
		return forward;
	}

}

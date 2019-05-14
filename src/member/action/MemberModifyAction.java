package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.action.Action;
import common.action.ActionForward;
import member.model.MemberBean;
import member.model.MemberDAO;

public class MemberModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * ModifyForm.jsp에서 넘겨받은 파라미터 값을 request에서 추출하고 MemberBean에 세팅
		 * 회원 정보 수정 성공시 ActionForward에 값을 세팅
		 * DB에 변화가 있는 요청이므로 setRedirect(true)
		 * 수정 후 결과를 표시하기 위해 setNextPath("Result.jun")로 경로를 지정
		 * 수정 성공 여부를 세션에 저장
		 */
		request.setCharacterEncoding("utf-8"); //인코딩
		
		ActionForward forward = new ActionForward();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		
		MemberBean member = new MemberBean();
		member.setId(id);
		member.setPassword(request.getParameter("password"));
		member.setMail_id(request.getParameter("mail_id"));
		member.setMail_addr(request.getParameterValues("mail_addr")[0]);
		member.setPhone(request.getParameter("phone"));
		dao.updateMember(member);
		
		forward.setRedirect(true);
		forward.setNextPath("ResultForm.jun");
		
		session.setAttribute("msg", "2");
		
		return forward;
	}

}

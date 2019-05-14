package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.action.Action;
import common.action.ActionForward;
import member.model.MemberBean;
import member.model.MemberDAO;

public class MemberJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8"); //인코딩
		ActionForward forward = new ActionForward();
		MemberDAO dao = MemberDAO.getInstance();
		MemberBean member = new MemberBean();
		//JoinForm.jsp에서 넘겨받은 파라미터 값을 request에서 추출
		//추출한 값을 MemberBean에 적용
		member.setId(request.getParameter("id"));
		member.setPassword(request.getParameter("password"));
		member.setName(request.getParameter("name"));
		member.setBirthyy(request.getParameter("birthyy"));
		member.setBirthmm(request.getParameterValues("birthmm")[0]);
		member.setBirthdd(request.getParameter("birthdd"));
		member.setGender(request.getParameter("gender"));
		member.setMail_id(request.getParameter("mail_id"));
		member.setMail_addr(request.getParameterValues("mail_addr")[0]);
		member.setPhone(request.getParameter("phone"));
		//회원가입실행
		dao.insertMember(member);
		//가입 성공 시 ActionForward에 값을 전달
		//회원가입의 경우 DB에 변화가 있는 요청이므로 Redirect 방식을 이용 setRedirect(true)
		//결과 표시를 위해 setNextPath("Result.jun")로 이동경로를 지정
		//회원가입 성공 여부를 세션에 저장
		forward.setRedirect(true);
		forward.setNextPath("ResultForm.jun");
		request.getSession().setAttribute("msg", "1");
		return forward;
	}

}

package member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.action.Action;
import common.action.ActionForward;
import member.model.MemberBean;
import member.model.MemberDAO;

public class MemberExcelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		//회원 정보를 가져옴
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberBean> memberList = dao.getMemberList();
		//회원정보를 전달하기 위해 request에 MemberBean 세팅
		request.setAttribute("memberList", memberList);
		//request를 유지해야 하므로 setRedirect(false) 지정
		forward.setRedirect(false);
		forward.setNextPath("Excel.jsp");
		return forward;
	}

}

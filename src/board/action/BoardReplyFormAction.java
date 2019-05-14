package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardBean;
import board.model.BoardDAO;
import common.action.Action;
import common.action.ActionForward;
/**
 * 상세보기에서 답글 버튼 클릭시 답글 작성화면으로 이동 BoardReplyForm
 */
public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		BoardDAO dao = BoardDAO.getInstance();
		int num = Integer.parseInt(request.getParameter("num"));
		//답글 작성 후 원래 페이지로 돌아가기 위해 페이지 번호 필요
		String pageNum = request.getParameter("page");
		
		BoardBean board = dao.getDetail(num);
		request.setAttribute("board", board);
		request.setAttribute("page", pageNum);
		
		forward.setRedirect(false);
		forward.setNextPath("BoardReplyForm.brd");
		
		return forward;
	}

}

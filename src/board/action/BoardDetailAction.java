package board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardBean;
import board.model.BoardDAO;
import comment.model.CommentBean;
import comment.model.CommentDAO;
import common.action.Action;
import common.action.ActionForward;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		String num = request.getParameter("num");
		int boardNum = Integer.parseInt(num);
		
		String pageNum = request.getParameter("pageNum");
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardBean board = dao.getDetail(boardNum);
		
		boolean result = dao.updateCount(boardNum);
		
		//게시글 번호를 이용하여 해당 글에 있는 댓글 목록을 가져온다
		CommentDAO commentDAO = CommentDAO.getInstance();
		ArrayList<CommentBean> commentList = commentDAO.getCommentList(boardNum);
		//댓글이 1개라도 있다면 request에 list를 세팅
		if (commentList.size() > 0) {
			request.setAttribute("commentList", commentList);
		}
		
		request.setAttribute("board", board);
		request.setAttribute("pageNum", pageNum);
		
		if (result) {
			forward.setRedirect(false);
			forward.setNextPath("BoardDetailForm.brd");
		}
		
		return forward;
	}

}

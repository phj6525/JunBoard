package comment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.CommentBean;
import comment.model.CommentDAO;
import common.action.Action;
import common.action.ActionForward;

public class CommentReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		// 부모글의 글번호를 가져온다.
		int cnum = Integer.parseInt(request.getParameter("num"));
		
		CommentDAO dao = CommentDAO.getInstance();
		CommentBean comment = dao.getComment(cnum);
		
		// 댓글 정보를 request에 세팅한다.
		request.setAttribute("comment", comment);
		
		forward.setRedirect(false);
		forward.setNextPath("board/comment/CommentReplyForm.jsp");
		
		return forward;
	}

}

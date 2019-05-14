package comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.CommentBean;
import comment.model.CommentDAO;
import common.action.Action;
import common.action.ActionForward;

public class CommentUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 파라미터를 가져온다.
		int cnum = Integer.parseInt(request.getParameter("cnum"));
		String ccontent = request.getParameter("ccontent");

		CommentDAO dao = CommentDAO.getInstance();
        
		CommentBean comment = new CommentBean();
		comment.setCnum(cnum);
		comment.setCcontent(ccontent);
        
		boolean result = dao.updateComment(comment);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		// 정상적으로 댓글을 수정했을경우 1을 전달한다.
		if(result) out.println("1");

		out.close();

		return null;
	}
}

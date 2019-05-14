package comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.CommentDAO;
import common.action.Action;
import common.action.ActionForward;

public class CommentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int cnum = Integer.parseInt(request.getParameter("cnum"));
		
		CommentDAO dao = CommentDAO.getInstance();
		boolean result = dao.deleteComment(cnum);
		
		response.setContentType("text/html;charset=utf-8");
		        PrintWriter out = response.getWriter();
		 
		        // 정상적으로 댓글을 삭제했을경우 1을 전달한다.
		if(result) out.println("1");
		
		out.close();
		return null;
    }
}

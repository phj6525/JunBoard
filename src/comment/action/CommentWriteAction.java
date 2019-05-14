package comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.CommentBean;
import comment.model.CommentDAO;
import common.action.Action;
import common.action.ActionForward;

public class CommentWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommentDAO dao = CommentDAO.getInstance();
        CommentBean comment = new CommentBean();
        
        // 파리미터 값을 가져온다.
        int board = Integer.parseInt(request.getParameter("board"));
        String cid = request.getParameter("cid");
        String ccontent = request.getParameter("ccontent");
        
        comment.setCnum(dao.getSeq());    // 댓글 번호는 시퀀스값으로
        comment.setBoard(board);
        comment.setCid(cid);
        comment.setCcontent(ccontent);
        
        boolean result = dao.insertComment(comment);
 
        if(result){
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("1");
            out.close();
        }
            
        return null;
	}

}

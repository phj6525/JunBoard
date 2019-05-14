package comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.CommentBean;
import comment.model.CommentDAO;
import common.action.Action;
import common.action.ActionForward;

public class CommentReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommentDAO dao = CommentDAO.getInstance();
		CommentBean comment = new CommentBean();    
		// 파라미터를 가져온다.
        int cnum = Integer.parseInt(request.getParameter("cnum"));
        int board = Integer.parseInt(request.getParameter("board"));
        String cid = request.getParameter("cid");
        String ccontent = request.getParameter("ccontent");
		int cref = Integer.parseInt(request.getParameter("cref"));
		int level = Integer.parseInt(request.getParameter("level"));
		int cseq = Integer.parseInt(request.getParameter("cseq"));
		
		comment.setCref(cref);
		comment.setLevel(level);
		comment.setCseq(cseq);
		dao.updateReCseq(comment);
		
        comment.setCnum(dao.getSeq());    // 시퀀스를 가져와 세팅한다
        comment.setBoard(board);
        comment.setCid(cid);
        comment.setCcontent(ccontent);
        comment.setCparent(cnum);  // 부모댓글의 글번호를 세팅
        comment.setCref(cref);
        comment.setLevel(level+1);
        comment.setCseq(cseq+1);
        
        boolean result = dao.insertComment(comment);
        
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        // 정상적으로 댓글을 저장했을경우 1을 전달한다.
        if(result) out.println("1");
        else out.println("0");
        
        out.close();
        
        return null;
	}

}

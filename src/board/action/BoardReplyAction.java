package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardBean;
import board.model.BoardDAO;
import common.action.Action;
import common.action.ActionForward;
/**
 * 답글 작성을 처리하는 Action
 * BoardReplyForm.jsp에서 등록 버튼을 누르면 실행
 */
public class BoardReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		ActionForward forward = new ActionForward();
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardBean boardData = new BoardBean();
		
		//답글 작성 후 원래 페이지로 돌아가기 위해 페이지 번호가 필요
		String pageNum = request.getParameter("page");
		//파라미터 값을 가져옴
		String id = request.getParameter("id");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		int ref = Integer.parseInt(request.getParameter("ref"));
		int lev = Integer.parseInt(request.getParameter("lev"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		//답글중 가장 최근 답글이 위로 올라가게 처리
		//그러기 위해 답글의 순서인 seq를 1증가
		boardData.setRef(ref);
		boardData.setLev(lev);
		boardData.setSeq(seq);
		dao.updateReSeq(boardData);
		//답글 저장
		boardData.setNum(dao.getSeq());
		boardData.setId(id);
		boardData.setSubject(subject);
		boardData.setContent(content);
		boardData.setRef(ref);
		boardData.setLev(lev+1);
		boardData.setSeq(seq+1);
		
		boolean result = dao.boardInsert(boardData);
		
		if(result) {
			forward.setRedirect(true);
			//원래 있던 페이지로 돌아가기 위해 페이지 번호를 전달
			forward.setNextPath("BoardListAction.brd?page="+pageNum);
		}
		return forward;
	}

}

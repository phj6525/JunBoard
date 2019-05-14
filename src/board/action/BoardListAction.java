package board.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardBean;
import board.model.BoardDAO;
import common.action.Action;
import common.action.ActionForward;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		//현재 페이지 번호 만들기
		int spage = 1;
		String page = request.getParameter("page"); //화면에서 전달한 page라는 파라미터값을 가져온다.
		
		if (page != null) {
			spage = Integer.parseInt(page);
		}
		//검색조건과 검색내용을 가져온다
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		//검색조건과 내용을 Map에 담는다
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		listOpt.put("start", spage*10-10);
		
		//DAO에 전달하고 DAO로 부터 글의 개수와 글 목록을 받는다
		BoardDAO dao = BoardDAO.getInstance();
		int listCount = dao.getBoardListCount(listOpt);
		ArrayList<BoardBean> boardList = dao.getBoardList(listOpt);
		
		//한 화면에 10개의 글이 보여진다
		//페이지 번호는 총 5개, 이후로는 [다음]표시
		
		//전체 페이지 수
		int maxPage = (int)(listCount/10.0 + 0.9);
		//시작 페이지 번호
		int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
		//마지막 페이지 번호
		int endPage = startPage + 4;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		//4개 페이지 번호 request 저장
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		//글의 총 갯수와 글 목록, 검색조건 저장
		request.setAttribute("boardList", boardList);
		request.setAttribute("opt", opt);
		request.setAttribute("condition", condition);
		//단순 조회이므로 forward 사용
		//DB의 상태 변화가 없으므로 false
		forward.setRedirect(false);
		forward.setNextPath("BoardListForm.brd");
		return forward;
	}

}

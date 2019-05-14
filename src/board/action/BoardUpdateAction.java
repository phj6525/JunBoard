package board.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.model.BoardBean;
import board.model.BoardDAO;
import common.action.Action;
import common.action.ActionForward;

public class BoardUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();

		// 원래 페이지로 돌아가기 위해 페이지 번호가 필요하다.
		String pageNum = request.getParameter("page");

		// 업로드 파일 사이즈
		int fileSize = 1024 * 1024 * 15;
		// 업로드될 폴더 절대경로
		String uploadPath = request.getServletContext().getRealPath("UploadFolder");

		try {
			MultipartRequest multi = new MultipartRequest(request, uploadPath, fileSize, "utf-8", new DefaultFileRenamePolicy());

			// 파리미터 값을 가져온다.
			int num = Integer.parseInt(multi.getParameter("num")); // 글 번호
			String subject = multi.getParameter("subject"); // 글 제목
			String content = multi.getParameter("content"); // 글 내용
			String existFile = multi.getParameter("file"); // 기존 첨부 파일

			// 파라미터 값을 자바빈에 세팅한다.
			BoardBean board = new BoardBean();
			board.setNum(num);
			board.setSubject(subject);
			board.setContent(content);

			// 글 수정 시 업로드된 파일 가져오기
			@SuppressWarnings("unchecked")
			Enumeration<String> fileNames = multi.getFileNames();
			if (fileNames.hasMoreElements()) {
				String fileName = fileNames.nextElement();
				String updateFile = multi.getFilesystemName(fileName);

				if (updateFile == null) // 수정시 새로운 파일을 첨부 안했다면 기존 파일명을 세팅
					board.setFile(existFile);
				else // 새로운 파일을 첨부했을 경우
					board.setFile(updateFile);
			}

			BoardDAO dao = BoardDAO.getInstance();
			boolean result = dao.updateBoard(board);

			if (result) {
				forward.setRedirect(true);
				// 원래있던 페이지로 돌아가기 위해 페이지번호를 전달한다.
				forward.setNextPath("BoardListAction.brd?page="+pageNum);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("글 수정 오류 : " + e.getMessage());
		}

		return forward;
	}
}

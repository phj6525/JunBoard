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

public class BoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		//업로드 폴더 경로
		String uploadPath = request.getServletContext().getRealPath("UploadFolder");
		//업로드 파일 사이즈 (15MB)
		int fileSize = 1024*1024*15;
		//인코딩
		String encType = "utf-8";
		
		try {
			//파일 업로드
			MultipartRequest multi = new MultipartRequest(request, uploadPath, fileSize, encType, new DefaultFileRenamePolicy());
			//파일 이름 가져오기
			String fileName = "";
			@SuppressWarnings("unchecked")
			Enumeration<String> names = multi.getFileNames();
			if(names.hasMoreElements()) {
				String name = names.nextElement();
				fileName = multi.getFilesystemName(name);
			}
			BoardDAO dao = BoardDAO.getInstance();
			BoardBean boarddata = new BoardBean();
			
			boarddata.setNum(dao.getSeq()); //시퀀스값 가져와서 세팅
			boarddata.setId(multi.getParameter("id")); //히든값
			boarddata.setSubject(multi.getParameter("subject"));
			boarddata.setContent(multi.getParameter("content"));
			boarddata.setFile(fileName);
			
			boolean result = dao.boardInsert(boarddata);
			
			if (result) {
				forward.setRedirect(true);
				forward.setNextPath("BoardListAction.brd");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("글 작성 오류 : " + e.getMessage());
		}
		return forward;
	}

}

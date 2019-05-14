package board.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.action.Action;
import common.action.ActionForward;

/**
 *회원 관련 Controller
 */
@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Action> commandMap;

    public BoardController() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
    	loadProp("board.properties/BoardCommand");
    }
    /**
     * properties 파일에서 키값과 클래스 정보를 추출하여 Map에 저장
     * @param filePath : properties파일 경로
     */
	private void loadProp(String filePath) {
		commandMap = new HashMap<String, Action>();
		
		ResourceBundle rb = ResourceBundle.getBundle(filePath);
		Enumeration<String> actionEnum = rb.getKeys(); //키값을 가져옴
		
		while (actionEnum.hasMoreElements()) {
			String command = actionEnum.nextElement(); //명령어를 가져옴
			String className = rb.getString(command); // 명령어에 해당하는 Action 클래스 이름을 가져옴
			try {
				Class<?> actionClass = Class.forName(className); //클래스 생성
				Action actionInstance = (Action)actionClass.newInstance(); //클래스 객체를 생성
				
				//화면전환 Action 인지 확인! Action이면 명령어 전달
				if(className.equals("board.action.BoardFormChangeAction")) {
					BoardFormChangeAction mfca = (BoardFormChangeAction)actionInstance;
					mfca.setCommand(command);
				}
				commandMap.put(command, actionInstance); //맵에 명령어와 Action을 담는다
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Get 방식 doGet()
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * Post 방식 doPost()
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	/**
	 * 명령어에 따른 해당 Action을 지정
	 */
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request에서 URL을 가져와 명령어 분리 작업
		String requestURI = request.getRequestURI();
		int cmdIdx = requestURI.lastIndexOf("/")+1;
		
		String command = requestURI.substring(cmdIdx);
		
		//URI, command 확인
		System.out.println("requestURI : "+requestURI);
		System.out.println("Board command : "+command);
		
		ActionForward forward = null;
		Action action = null;
		//보여줄 화면 URL
		//String form = "index.jsp?contentPage=member/";
		//커맨드에 해당하는 액션을 실행
		try {
			/**
			 * 단순한 화면 전환만을 처리하므로 Action 클래스가 없음
			 * ActionForward 객체를 생성해서 Redirect 사용 여부와 경로 지정
			 *
			 * Map에서 명령어에 해당하는 Action을 가져온다.
			 */
			action = commandMap.get(command);
			
			if(action == null) {
				System.out.println("명령어 : " + command + "는 잘못된 명령어입니다");
				return;
			}
			
			forward = action.execute(request, response);
			
			/**
			 * ActionForward 객체가 가진 값에 따라 화면 이동을 처리
			 * isRedirect = true 이면 sendRedirect()를 통해 화면 이동을 처리
			 * isRedirect = false 이면 forward()를 통해 화면 이동을 처리
			 * 세션이나 DB에 변화가 생기는 경우 sendRedirect()
			 * 세션이나 DB에 변화가 생기지 않는 경우(화면전환, 조회 등등) forward()
			 * sendRedirect : 새로운 페이지에서는 request와 response 객체가 새롭게 생성된다.
			 * forward : 현재 실행중인 페이지와 forward에 의해 호출될 페이지는 request와 response 객체를 공유
			 */
			if (forward != null) {
				if (forward.isRedirect()) {
					response.sendRedirect(forward.getNextPath());
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getNextPath());
					dispatcher.forward(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}













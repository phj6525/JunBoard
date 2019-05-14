package visit.action;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import visit.model.VisitCountDAO;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class VisitSessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public VisitSessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	//세션이 새로 생성되면 execute() 실행
		if(se.getSession().isNew()) {
			execute(se);
		}
    }

	private void execute(HttpSessionEvent se) {
		VisitCountDAO dao = VisitCountDAO.getInstance();
		try {
			dao.setTotalCount(); //총 방문자 수 증가
			int totalCount = dao.getTotalCount(); //총 방문자 수
			int todayCount = dao.getTodayCount(); //오늘 방문자 수
			HttpSession session = se.getSession();
			//세션에 방문자 수를 담는다.
			session.setAttribute("totalCount", totalCount);
			session.setAttribute("todayCount", todayCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    }
	
}

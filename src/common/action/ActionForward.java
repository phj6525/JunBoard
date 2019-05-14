package common.action;
/**
 * 페이지 이동을 처리하기 위한 클래스
 * isRedirect : sendRedirect와 forward 중 어떤 것을 이용해서 페이지를 이동할지 결정하기 위한 boolean변수
 * nextPath : 이동할 페이지 경로값을 갖고 있는 변수
 */
public class ActionForward {
	private boolean isRedirect = false;
	private String nextPath = null; //이동할 다음 화면(nextPath)
	/**
	 * Redirect 사용여부, false -> Forward 사용
	 */
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getNextPath() {
		return nextPath;
	}
	public void setNextPath(String nextPath) {
		this.nextPath = nextPath;
	}
}

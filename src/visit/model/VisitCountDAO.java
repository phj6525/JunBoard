package visit.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import common.util.DBConnection;

public class VisitCountDAO {
	//싱글톤
	//클래스안에 클래스(Holder)를 두고 JVM의 Class loader 매커니즘과 Class가 로드되는 시점을 이용한 방법
	private VisitCountDAO() {
		
	}
	private static class Holder {
		public static final VisitCountDAO INSTANCE = new VisitCountDAO();
	}
	public static VisitCountDAO getInstance() {
		return Holder.INSTANCE;
	}
	/**
	 * 총 방문자 수 증가
	 */
	public void setTotalCount() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			//쿼리생성
			//총 방문자 수를 증가시키기 위해 테이블에 현재 날짜 값 추가
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO phj6525.JunProject_visit VALUES (NOW())");
			//커넥션을 가져온다
			con = DBConnection.openConnection();
			//자동 커밋 false
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql.toString());
			pstmt.executeUpdate();//쿼리 실행
			con.commit();//완료시 커밋
		} catch (ClassNotFoundException | NamingException | SQLException sqle) {
			//오류시 롤백
			con.rollback();
			throw new RuntimeException(sqle.getMessage());
		} finally {
			//Connection, PreparedStatement를 담는다
			try {
				DBConnection.closeConnection();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	/**
	 * 총 방문자 수를 가져온다
	 */
	public int getTotalCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		int totalCount = 0;
		
		try {
			//테이블의 데이터 수를 가져온다
			//데이터 수 = 총 방문자 수
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS totalcount FROM phj6525.JunProject_visit");
			
			con = DBConnection.openConnection();
			pstmt = con.prepareStatement(sql.toString());
			res = pstmt.executeQuery();
			
			if (res.next())
				totalCount = res.getInt("totalcount");
			return totalCount;
			
		} catch (Exception sqle) {
			throw new RuntimeException(sqle.getMessage());
		} finally {
			try {
				DBConnection.closeConnection();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	/**
	 * 오늘 방문자 수를 가져온다.
	 */
	public int getTodayCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		int todayCount = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS todaycount FROM phj6525.JunProject_visit ");
			sql.append("WHERE DATE(visitdate) = date(NOW())");
			
			con = DBConnection.openConnection();
			pstmt = con.prepareStatement(sql.toString());
			res = pstmt.executeQuery();
			
			if (res.next()) 
				todayCount = res.getInt("todaycount");
			return todayCount;
			
		} catch (Exception sqle) {
			throw new RuntimeException(sqle.getMessage());
		} finally {
			try {
				DBConnection.closeConnection();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
}






















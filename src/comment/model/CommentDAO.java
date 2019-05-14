package comment.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.util.DBConnection;


public class CommentDAO {
	//싱글톤
	//클래스안에 클래스(Holder)를 두고 JVM의 Class loader 매커니즘과 Class가 로드되는 시점을 이용한 방법
	private CommentDAO() {
		
	}
	private static class Holder {
		public static final CommentDAO INSTANCE = new CommentDAO();
	}
	public static CommentDAO getInstance() {
		return Holder.INSTANCE;
	}
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet res;
	
	//시퀀스를 가져온다.
	public int getSeq() {
		int result = 1;
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select case count(*) when 0 then 1 else max(cnum) + 1 end from phj6525.JunProject_comment");
			
			pstmt = con.prepareStatement(sql.toString());
			res = pstmt.executeQuery();
			
			if (res.next())
				result = res.getInt(1);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}
	//댓글 등록
	public boolean insertComment(CommentBean comment) {
		boolean result = false;
		try {
			con = DBConnection.openConnection();
			// 자동 커밋을 false로 한다.
			con.setAutoCommit(false);
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO phj6525.JunProject_comment");
			sql.append(" (cnum, board, cid, cdate, cparent, ccontent, cref, level, cseq)");
			sql.append(" VALUES(?, ?, ?, now(), ?, ?, ?, ?, ?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, comment.getCnum());
			pstmt.setInt(2, comment.getBoard());
			pstmt.setString(3, comment.getCid());
			pstmt.setInt(4, comment.getCparent());
			pstmt.setString(5, comment.getCcontent());
			if(comment.getCseq() == 0) {
				pstmt.setInt(6, comment.getCnum());
			} else {
				pstmt.setInt(6, comment.getCref());
			}
			pstmt.setInt(7, comment.getLevel());
			pstmt.setInt(8, comment.getCseq());
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				con.commit(); // 완료시 커밋
			}
		} catch (Exception e) {
			try {
				con.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} 
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}

	// 댓글 목록 가져오기
	public ArrayList<CommentBean> getCommentList(int boardNum) {
		ArrayList<CommentBean> commentList = new ArrayList<CommentBean>();
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.num, b.cnum, b.board, b.cid, b.cdate, b.cparent, b.ccontent, b.cref, b.level, b.cseq ");
			sql.append("FROM phj6525.JunProject_board a ");
			sql.append("RIGHT OUTER JOIN phj6525.JunProject_comment b ");
			sql.append("ON a.num = b.board ");
			sql.append("WHERE a.num = ? ");
			sql.append("AND (b.cref IS NOT NULL OR b.level IS NOT NULL OR b.cseq IS NOT NULL) ");
			sql.append("ORDER BY b.cref ASC, b.level ASC, b.cseq DESC");
			
			/**
			 * 게시판 DB와 댓글 DB를 right outer join 후 게시글에 맞는 댓글을 가져와 정렬
			 * SELECT a.num, b.cnum, b.board, b.cid, b.cdate, b.cparent, b.ccontent, b.cref, b.level, b.cseq
			 * FROM phj6525.JunProject_board a RIGHT OUTER JOIN phj6525.JunProject_comment b
			 * ON a.num = b.board
			 * WHERE a.num = 29 AND (b.cref IS NOT NULL OR b.level IS NOT NULL OR b.cseq IS NOT NULL) 
			 * ORDER BY b.cref ASC, b.level ASC, b.cseq ASC
			 */

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			res = pstmt.executeQuery();
			while(res.next()) {
				CommentBean comment = new CommentBean();
				comment.setCnum(res.getInt("cnum"));
				comment.setBoard(res.getInt("board"));
				comment.setCid(res.getString("cid"));
				comment.setCdate(res.getDate("cdate"));
				comment.setCparent(res.getInt("cparent"));
				comment.setCcontent(res.getString("ccontent"));
				comment.setCref(res.getInt("cref"));
				comment.setLevel(res.getInt("level"));
				comment.setCseq(res.getInt("cseq"));
				commentList.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return commentList;
	}
	//댓글 순서
	public boolean updateReCseq(CommentBean comment) {
		boolean result = false;
		int cref = comment.getCref();
		int cseq = comment.getCseq();
		
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer();
			con.setAutoCommit(false);
			sql.append("UPDATE phj6525.JunProject_comment SET cseq = cseq+1 ");
			sql.append("WHERE cref = ? AND cseq > ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cref);
			pstmt.setInt(2, cseq);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0) {
				result = true;
				con.commit();
			}
		} catch(Exception e) {
			try {
				con.rollback();
			} catch(SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}
	
	//댓글 1개의 정보를 가져온다.
	public CommentBean getComment(int cnum) {
		CommentBean comment = null;
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM phj6525.JunProject_comment where cnum=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cnum);
			
			res = pstmt.executeQuery();
			while (res.next()) {
				comment = new CommentBean();
				comment.setCnum(res.getInt("cnum"));
				comment.setBoard(res.getInt("board"));
				comment.setCid(res.getString("cid"));
				comment.setCdate(res.getDate("cdate"));
				comment.setCparent(res.getInt("cparent"));
				comment.setCcontent(res.getString("ccontent"));
				comment.setCref(res.getInt("cref"));
				comment.setLevel(res.getInt("level"));
				comment.setCseq(res.getInt("cseq"));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return comment;
	}
	
	// 댓글 삭제
	public boolean deleteComment(int cnum) {
		boolean result = false;

		try {
			con = DBConnection.openConnection();
			con.setAutoCommit(false); // 자동 커밋을 false로 한다.

			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM phj6525.JunProject_comment ");
			sql.append("WHERE cnum=? or cparent=?");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cnum);
			pstmt.setInt(2, cnum);

			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				con.commit(); // 완료시 커밋
			}

		} catch (Exception e) {
			try {
				con.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
 
		DBclose();
		return result;
	}

	// 댓글 수정
	public boolean updateComment(CommentBean comment) {
		boolean result = false;

		try{
			con = DBConnection.openConnection();
			con.setAutoCommit(false); // 자동 커밋을 false로 한다.

			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE phj6525.JunProject_comment SET");
			sql.append(" ccontent = ?");
			sql.append(" WHERE cnum = ?");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, comment.getCcontent());
			pstmt.setInt(2, comment.getCnum());

			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				con.commit(); // 완료시 커밋
			}
		} catch (Exception e) {
			try {
				con.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}

	//DB 해제
	private void DBclose() {
		try {
			DBConnection.closeConnection();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
}

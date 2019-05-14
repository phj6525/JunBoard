package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import common.util.DBConnection;

public class BoardDAO {
	//싱글톤
	//클래스안에 클래스(Holder)를 두고 JVM의 Class loader 매커니즘과 Class가 로드되는 시점을 이용한 방법
	private BoardDAO() {
		
	}
	private static class Holder {
		public static final BoardDAO INSTANCE = new BoardDAO();
	}
	public static BoardDAO getInstance() {
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
			//시퀀스 값을 가져온다. (DUAL : 시퀀스 값을 가져오기 위한 임시 테이블)
			StringBuffer sql = new StringBuffer();
			sql.append("select case count(*) when 0 then 1 else max(num) + 1 end from phj6525.JunProject_board");
			
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
	//글 삽입
	public boolean boardInsert(BoardBean board) {
		boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into phj6525.JunProject_board");
			sql.append("(num, id, subject, content, file, ref, lev, seq, count, date) ");
			sql.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, now())");
			
			int num = board.getNum();
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getId());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getFile());
			
			if(board.getSeq() == 0) { //seq 답변글이 없는 경우, 즉 부모글일 경우
				pstmt.setInt(6, num);
			} else {
				pstmt.setInt(6, board.getRef());
			}
			
			pstmt.setInt(7, board.getLev());
			pstmt.setInt(8, board.getSeq());
			pstmt.setInt(9, 0);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0) {
				result = true;
				con.commit();
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}
	//글 목록 가져오기
	/**
	 * 글 목록을 가져오는 메서드
	 * 글 목록을 ArrayList에 담아서 BoardListAction으로 전달
	 * 글 검색도 같이 처리하기 위해 인자로 검색 옵션, 내용, 현재 페이지 번호를 갖고있는 HashMap을 받는다
	 */
	public ArrayList<BoardBean> getBoardList(HashMap<String, Object> listOpt) {
		ArrayList<BoardBean> boardList = new ArrayList<BoardBean>();
		
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		int start = (Integer)listOpt.get("start");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer(); //재사용을 위해 작업이 끝나면 비워둔다.
			
			//글 목록 전체 보기
			if (opt == null) {
				/**
				 * ref(그룹번호) 내림차순 정렬 후 동일한 그룹번호 일 때는
				 * seq(답변글 순서) 오름차순으로 정렬
				 * 10개의 글을 한 화면에 보여주는 쿼리
				 * desc : 내림차순, asc : 오름차순
				 */
				sql.append("select @rownum := @rownum +1 as rnum, ");
				sql.append("num, id, subject, content, file, ref, lev, seq, count, date from ");
				sql.append("phj6525.JunProject_board, ");
				sql.append("(select @rownum := ?) a ");
				sql.append("order by ref desc, seq asc limit ?, 10");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, start);
				
				sql.delete(0, sql.toString().length()); //StringBuffer 비우기
			} else if (opt.equals("0")) {
				//글 제목 검색
				sql.append("select @rownum := @rownum +1 as rnum, ");
				sql.append("num, id, subject, content, file, ref, lev, seq, count, date from ");
				sql.append("phj6525.JunProject_board, ");
				sql.append("(select @rownum := ?) a where subject like ? "); //검색에 관한 부분
				sql.append("order by ref desc, seq asc limit ?, 10");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setString(2, "%"+condition+"%"); //어딘가에 포함되면 모두 검색 %condition%
				pstmt.setInt(3, start);
				
				sql.delete(0, sql.toString().length());
			} else if (opt.equals("1")) {
				//내용 검색
				sql.append("select @rownum := @rownum +1 as rnum, ");
				sql.append("num, id, subject, content, file, ref, lev, seq, count, date from ");
				sql.append("phj6525.JunProject_board, ");
				sql.append("(select @rownum := ?) a where content like ? "); //검색에 관한 부분
				sql.append("order by ref desc, seq asc limit ?, 10");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setString(2, "%"+condition+"%");//어딘가에 포함되면 모두 검색 %condition%
				pstmt.setInt(3, start);
				
				sql.delete(0, sql.toString().length());
			} else if (opt.equals("2")) {
				//글 제목 + 내용 검색
				sql.append("select @rownum := @rownum +1 as rnum, ");
				sql.append("num, id, subject, content, file, ref, lev, seq, count, date from ");
				sql.append("phj6525.JunProject_board, ");
				sql.append("(select @rownum := ?) a where subject like ? or content like ? "); //검색에 관한 부분
				sql.append("order by ref desc, seq asc limit ?, 10");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setString(2, "%"+condition+"%");//어딘가에 포함되면 모두 검색 %condition%
				pstmt.setString(3, "%"+condition+"%");
				pstmt.setInt(4, start);
				
				sql.delete(0, sql.toString().length());
			} else if (opt.equals("3")) {
				//작성자 검색
				sql.append("select @rownum := @rownum +1 as rnum, ");
				sql.append("num, id, subject, content, file, ref, lev, seq, count, date from ");
				sql.append("phj6525.JunProject_board, ");
				sql.append("(select @rownum := ?) a where id like ? "); //검색에 관한 부분
				sql.append("order by ref desc, seq asc limit ?, 10");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setString(2, "%"+condition+"%");//어딘가에 포함되면 모두 검색 %condition%
				pstmt.setInt(3, start);
				
				sql.delete(0, sql.toString().length());
			}
			res = pstmt.executeQuery();
			while(res.next()) {
				BoardBean board = new BoardBean();
				board.setNum(res.getInt("num"));
				board.setId(res.getString("id"));
				board.setSubject(res.getString("subject"));
				board.setContent(res.getString("content"));
				board.setFile(res.getString("file"));
				board.setRef(res.getInt("ref"));
				board.setLev(res.getInt("lev"));
				board.setSeq(res.getInt("seq"));
				board.setCount(res.getInt("count"));
				board.setDate(res.getDate("date"));
				boardList.add(board);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return boardList;
	}
	/**
	 * 글의 개수를 가져오는 메서드
	 * getBoardList()메서드와 동일한 인자를 넘겨받아 글의 개수를 검색
	 * 페이지 처리에 글의 개수가 필요하기 때문
	 */
	public int getBoardListCount(HashMap<String, Object> listOpt) {
		int result = 0;
		String opt = (String)listOpt.get("opt"); //검색 옵션
		String condition = (String)listOpt.get("condition"); //검색 내용
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer();
			
			if(opt == null) {
				//전체 글의 개수
				sql.append("select count(*) from phj6525.JunProject_board");
				pstmt = con.prepareStatement(sql.toString());
				sql.delete(0, sql.toString().length());
			} else if (opt.equals("0")) {
				//제목으로 검색한 글의 개수
				sql.append("select count(*) from phj6525.JunProject_board");
				sql.append(" where subject like ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				sql.delete(0, sql.toString().length());
			} else if (opt.equals("1")) {
				//내용으로 검색한 글의 개수
				sql.append("select count(*) from phj6525.JunProject_board");
				sql.append(" where content like ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				sql.delete(0, sql.toString().length());
			} else if (opt.equals("2")) {
				//제목+내용으로 검색한 글의 개수
				sql.append("select count(*) from phj6525.JunProject_board");
				sql.append(" where subject like ? or content like ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				pstmt.setString(2, "%"+condition+"%");
				sql.delete(0, sql.toString().length());
			} else if (opt.equals("3")) {
				//작성자로 검색한 글의 개수
				sql.append("select count(*) from phj6525.JunProject_board");
				sql.append(" where id like ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				sql.delete(0, sql.toString().length());
			}
			res = pstmt.executeQuery();
			if(res.next()) {
				result = res.getInt(1);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}
	
	//상세보기
	public BoardBean getDetail(int boardNum) {
		BoardBean board = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select * from phj6525.JunProject_board where num = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			res = pstmt.executeQuery();
			if(res.next()) {
				board = new BoardBean();
				board.setNum(boardNum);
				board.setId(res.getString("id"));
				board.setSubject(res.getString("subject"));
				board.setContent(res.getString("content"));
				board.setFile(res.getString("file"));
				board.setCount(res.getInt("count"));
				board.setRef(res.getInt("ref"));
				board.setLev(res.getInt("lev"));
				board.setSeq(res.getInt("seq"));
				board.setDate(res.getDate("date"));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return board;
	}
	
	//조회수 증가
	public boolean updateCount(int boardNum) {
		boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBConnection.openConnection();
			
			con.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE phj6525.JunProject_board SET COUNT = COUNT+1 ");
			sql.append("WHERE num = ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0) {
				result = true;
				con.commit();
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}
	
	public boolean updateReSeq(BoardBean board) {
		boolean result = false;
		
		int ref = board.getRef();	//원본글 번호
		int seq = board.getSeq();	//답변글 순서
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			con = DBConnection.openConnection();
			con.setAutoCommit(false);
			
			sql.append("UPDATE phj6525.JunProject_board SET seq = seq+1");
			sql.append(" WHERE ref = ? AND seq > ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, ref);
			pstmt.setInt(2, seq);
		
			int flag = pstmt.executeUpdate();
			if(flag > 0) {
				result = true;
				con.commit();
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}
	
	//파일 삭제
	public String getFileName(int boardNum) {
		String fileName = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			con = DBConnection.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select file from phj6525.JunProject_board where num=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			res = pstmt.executeQuery();
			if(res.next()) {
				fileName = res.getString("file");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return fileName;
	}
	
	//게시글 삭제
	public boolean deleteBoard(int boardNum) {
		boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBConnection.openConnection();
			con.setAutoCommit(false);
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM phj6525.JunProject_board WHERE num=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			int flag = pstmt.executeUpdate();
			if (flag > 0) {
				result = true;
				con.commit();
			}
		} catch (Exception e) {
			try {
				con.rollback();		//오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		DBclose();
		return result;
	}
	
	//게시글 수정
	public boolean updateBoard(BoardBean board) {
		boolean result = false;
		Connection con = null;
		try{
			con = DBConnection.openConnection();
			con.setAutoCommit(false); // 자동 커밋을 false로 한다.
		
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE phj6525.JunProject_board SET ");
			sql.append("subject=?, content=?, file=?, date=now() ");
			sql.append("WHERE num=?");
		 
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getFile());
			pstmt.setInt(4, board.getNum());
		
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
	} // end updateBoard
	
	//DB 해제
	private void DBclose() {
		try {
			DBConnection.closeConnection();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
}

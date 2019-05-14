package member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;

import javax.naming.NamingException;

import common.util.DBConnection;

/**
 * DB 테이블과 연관된 DAO로 회원 데이터를 처리한다
 */
public class MemberDAO {
	//싱글톤
	//클래스안에 클래스(Holder)를 두고 JVM의 Class loader 매커니즘과 Class가 로드되는 시점을 이용한 방법
	private MemberDAO() {
		
	}
	private static class Holder {
		public static final MemberDAO INSTANCE = new MemberDAO();
	}
	public static MemberDAO getInstance() {
		return Holder.INSTANCE;
	}
	
	//String --> Date로 변경하는 메서드
	//문자열로 된 생년월일을 Date로 변경
	public Date StringToDate(MemberBean member) {
		String year = member.getBirthyy();
		String month = member.getBirthmm();
		String day = member.getBirthdd();
		
		Date birthday = Date.valueOf(year + "-" + month + "-" + day);
		
		return birthday;
	}
	
	public void insertMember(MemberBean member) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try { //커넥션을 가져옴
			con = DBConnection.openConnection();
			//자동 커밋을 false로 설정
			con.setAutoCommit(false);
			//쿼리 생성
			StringBuffer sql = new StringBuffer();
			sql.append("insert into phj6525.JunProject_member values");
			sql.append("(?, ?, ?, ?, ?, ?, ?, ?)");
			StringToDate(member);
			//StringBuffer에 담긴 값을 얻으려면 toString()메서드를 사용
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setDate(4, StringToDate(member));
			pstmt.setString(5, member.getGender());
			pstmt.setString(6, member.getMail_id() + "@" + member.getMail_addr());
			pstmt.setString(7, member.getPhone());
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			pstmt.setTimestamp(8, ts);
			//쿼리 실행
			pstmt.executeUpdate();
			//완료시 커밋
			con.commit();
		} catch (ClassNotFoundException | NamingException | SQLException sqle) {
			//오류시 롤백
			con.rollback();
			throw new RuntimeException(sqle.getMessage());
		} finally {
			//Connection, PreparedStatement를 닫음
			try {
				DBConnection.closeConnection();
			} catch(Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	//로그인 시 아이디, 비밀번호 체크
	//아이디와 비밀번호를 인자로 받는다.
	public int loginCheck(String id, String pw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		String dbpassword = ""; //db에서 꺼낸 비밀번호를 담을 변수
		int x = -1;
		
		try { //query - 입력된 아이디로 db에서 비밀번호 조회
			StringBuffer query = new StringBuffer();
			query.append("select password from phj6525.JunProject_member where id=?");
			con = DBConnection.openConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, id);
			res = pstmt.executeQuery();
			if(res.next()) { //입력된 아이디에 해당하는 비밀번호가 있을 경우
				dbpassword = res.getString("password"); //비밀번호를 dbpassword변수에 담는다.
				if(dbpassword.equals(pw)) {
					x = 1; //변수에 담은 비밀번호와 db의 비밀번호가 같으면 인증 성공
				} else {
					x = 0; //변수에 담은 비밀번호와 db의 비밀번호가 다르면 인증 실패
				}
			} else {
				x = -1; //해당 아이디가 없을 경우
			}
			return x;
		} catch (Exception sqle) {
			throw new RuntimeException(sqle.getMessage());
		} finally {
			try {
				DBConnection.closeConnection();
			} catch(Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	//아이디를 이용해 현재 회원정보를 가져온다.
	public MemberBean getUserInfo(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		MemberBean member = null;
		
		try {
			StringBuffer query = new StringBuffer();
			query.append("select * from phj6525.JunProject_member where id=?");
			con = DBConnection.openConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, id);
			res = pstmt.executeQuery();
			
			if (res.next()) { // 회원정보를 DTO에 담는다.
				//DB의 생년월일 정보를 년, 월, 일로 문자열로 자른다.
				String birthday = res.getDate("birth").toString();
				String year = birthday.substring(0, 4);
				String month = birthday.substring(5, 7);
				String day = birthday.substring(8, 10);
				
				//이메일을 @ 기준으로 자른다.
				String email = res.getString("email");
				int idx = email.indexOf("@");
				String mail_id = email.substring(0, idx);
				String mail_addr = email.substring(idx+1);
				
				//자바빈에 정보를 담는다.
				member = new MemberBean();
				member.setId(res.getString("id"));
				member.setPassword(res.getString("password"));
				member.setName(res.getString("name"));
				member.setBirthyy(year);
				member.setBirthmm(month);
				member.setBirthdd(day);
				member.setGender(res.getString("gender"));
				member.setMail_id(mail_id);
				member.setMail_addr(mail_addr);
				member.setPhone(res.getString("phone"));
				member.setReg(res.getTimestamp("reg"));
			}
			return member;
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
	 * 아이디 중복 확인을 하는 메서드
	 * @param id
	 * @return x : 아이디 중복여부 확인값
	 */
	public boolean duplicateIdCheck(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		boolean x= false;
		try {
			// 쿼리
			StringBuffer query = new StringBuffer();
			query.append("SELECT id FROM phj6525.JunProject_member WHERE id=?");

			con = DBConnection.openConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, id);
			res = pstmt.executeQuery();

			if(res.next()) x= true; //해당 아이디 존재

			return x;

		} catch (Exception sqle) {
			throw new RuntimeException(sqle.getMessage());
		} finally {
			try{
				DBConnection.closeConnection();
			}catch(Exception e){
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	//회원 정보를 수정한다.
	public void updateMember(MemberBean member) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			StringBuffer query = new StringBuffer();
			query.append("UPDATE phj6525.JunProject_member SET password=?, email=?, phone=? WHERE id=?");
			
			con = DBConnection.openConnection();
			pstmt = con.prepareStatement(query.toString());
			
			con.setAutoCommit(false);
			
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMail_id()+"@"+member.getMail_addr());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getId());
			
			pstmt.executeQuery();
			con.commit();
		} catch (Exception sqle) {
			con.rollback();
			throw new RuntimeException(sqle.getMessage());
		} finally {
			try {
				DBConnection.closeConnection();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	//회원정보를 삭제한다.
	public int deleteMember(String id, String pw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		String dbPW = "";
		int x = -1;
		
		try {
			//비밀번호 조회
			StringBuffer query1 = new StringBuffer();
			query1.append("SELECT password FROM phj6525.JunProject_member WHERE id=?");
			//회원 삭제
			StringBuffer query2 = new StringBuffer();
			query2.append("DELETE FROM phj6525.JunProject_member WHERE id=?");
			
			con = DBConnection.openConnection();
			
			con.setAutoCommit(false);
			
			//1. 아이디에 해당하는 비밀번호를 조회
			pstmt = con.prepareStatement(query1.toString());
			pstmt.setString(1, id);
			res = pstmt.executeQuery();
			
			if (res.next()) {
				dbPW = res.getString("password");
				if(dbPW.equals(pw)) {
					//입력된 비밀번호와 DB의 비밀번호가 같으면 회원삭제 진행
					pstmt = con.prepareStatement(query2.toString());
					pstmt.setString(1, id);
					pstmt.executeQuery();
					con.commit();
					x = 1; // 삭제
				} else {
					x = 0; // 비밀번호 비교결과 다름
				}
			}
			return x;
		} catch (Exception sqle) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	 * 모든 회원 정보를 가져온다. %관리자 권한%
	 */
	public ArrayList<MemberBean> getMemberList() {
		ArrayList<MemberBean> memberList = new ArrayList<MemberBean>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		MemberBean member = null;
		
		try {
			StringBuffer query = new StringBuffer();
			query.append("select * from phj6525.JunProject_member");
			
			con = DBConnection.openConnection();
			pstmt = con.prepareStatement(query.toString());
			res = pstmt.executeQuery();
			
			while (res.next()) {
				member = new MemberBean();
				member.setId(res.getString("id"));
				member.setPassword(res.getString("password"));
				member.setName(res.getString("name"));
				member.setBirthyy(res.getDate("birth").toString());
				member.setGender(res.getString("gender"));
				member.setMail_id(res.getString("email"));
				member.setPhone(res.getString("phone"));
				member.setReg(res.getTimestamp("reg"));
				memberList.add(member);
			}
			return memberList;
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










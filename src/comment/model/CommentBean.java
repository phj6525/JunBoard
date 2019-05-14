package comment.model;

import java.sql.Date;

public class CommentBean {
	private int cnum;        // 댓글 글번호
    private int board;        // 게시글 번호
    private String cid;        // 댓글 작성자
    private Date cdate;        // 댓글 작성일
    private String ccontent;    // 댓글 내용
    private int cparent;        // 부모글
    private int cref;
    private int level;
    private int cseq;
    
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public int getBoard() {
		return board;
	}
	public void setBoard(int board) {
		this.board = board;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public String getCcontent() {
		return ccontent;
	}
	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}
	public int getCparent() {
		return cparent;
	}
	public void setCparent(int cparent) {
		this.cparent = cparent;
	}
	public int getCref() {
		return cref;
	}
	public void setCref(int cref) {
		this.cref = cref;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getCseq() {
		return cseq;
	}
	public void setCseq(int cseq) {
		this.cseq = cseq;
	}
    

}

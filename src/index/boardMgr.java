package index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;


public class boardMgr {
	
	private DBConnectionMgr pool;
	
	public boardMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public boolean insertReple(boardBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "insert main_reple(writer,content,parent)"
					+ "values(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getContent());
			pstmt.setInt(3, bean.getParent());
			if(pstmt.executeUpdate()==1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	public boolean deleteReple(int idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "delete from main_reple where idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			if(pstmt.executeUpdate()==1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	public boardBean getReple(int idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boardBean regBean = new boardBean();
		try {
			con = pool.getConnection();
			sql = "select * from main_reple where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				regBean.setIdx(rs.getInt("idx"));
				regBean.setWriter(rs.getString("writer"));
				regBean.setContent(rs.getString("content"));
				regBean.setParent(rs.getInt("parent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con);
		}
		return regBean;
	}
	public Vector<boardBean> getRepleList(int parent) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<boardBean> vlist = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select * from main_reple where parent = ? order by idx desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, parent);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				boardBean regBean = new boardBean();
				regBean.setIdx(rs.getInt("idx"));
				regBean.setWriter(rs.getString("writer"));
				regBean.setContent(rs.getString("content"));
				regBean.setParent(rs.getInt("parent"));
				vlist.addElement(regBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con);
		}
		return vlist;
	}	
}

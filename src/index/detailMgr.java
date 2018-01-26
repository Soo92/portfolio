package index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;


public class detailMgr {
	
	private DBConnectionMgr pool;
	
	public detailMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public boolean insertMember(detailBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "insert main_detail(idx,cate,parent,content)"
					+ "values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bean.getIdx());
			pstmt.setString(2, bean.getCate());
			pstmt.setInt(3, bean.getParent());
			pstmt.setString(4, bean.getContent());
			if(pstmt.executeUpdate()==1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	public boolean deleteClient(int idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "delete from main_detail where idx = ?";
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
	public detailBean getMember(int idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		detailBean regBean = new detailBean();
		try {
			con = pool.getConnection();
			sql = "select * from main_detail where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				regBean.setIdx(rs.getInt("idx"));
				regBean.setCate(rs.getString("cate"));
				regBean.setParent(rs.getInt("parent"));
				regBean.setContent(rs.getString("content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con);
		}
		return regBean;
	}
	public Vector<detailBean> getCartList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<detailBean> vlist = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select * from main_detail order by view";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				detailBean regBean = new detailBean();
				regBean.setIdx(rs.getInt("idx"));
				regBean.setCate(rs.getString("cate"));
				regBean.setParent(rs.getInt("parent"));
				regBean.setContent(rs.getString("content"));
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

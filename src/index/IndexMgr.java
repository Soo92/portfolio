package index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;


public class IndexMgr {
	
	private DBConnectionMgr pool;
	
	public IndexMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public boolean insertMember(IndexBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "insert main_index(idx,title,mainpic,regdate,url,view,cate,icon,content)"
					+ "values(?,?,?,date_format(now(), '%Y.%m.%d'),?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bean.getIdx());
			pstmt.setString(2, bean.getTitle());
			pstmt.setString(3, bean.getMainpic());
			pstmt.setString(4, bean.getUrl());
			pstmt.setInt(5, bean.getView());
			pstmt.setString(6, bean.getCate());
			pstmt.setString(7, bean.getIcon());
			pstmt.setString(8, bean.getContent());
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
			sql = "delete from main_index where idx = ?";
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
	public boolean Count(int idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "update main_index set view=view+1 where idx = ?";
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
	public IndexBean getMember(int idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		IndexBean regBean = new IndexBean();
		try {
			con = pool.getConnection();
			sql = "select * from main_index where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				regBean.setIdx(rs.getInt("idx"));
				regBean.setTitle(rs.getString("title"));
				regBean.setMainpic(rs.getString("mainpic"));
				regBean.setRegdate(rs.getString("regdate"));
				regBean.setUrl(rs.getString("url"));
				regBean.setView(rs.getInt("view"));
				regBean.setCate(rs.getString("cate"));
				regBean.setIcon(rs.getString("icon"));
				regBean.setContent(rs.getString("content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con);
		}
		return regBean;
	}
	public Vector<IndexBean> getCartList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<IndexBean> vlist = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select * from main_index order by view desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				IndexBean regBean = new IndexBean();
				regBean.setIdx(rs.getInt("idx"));
				regBean.setTitle(rs.getString("title"));
				regBean.setMainpic(rs.getString("mainpic"));
				regBean.setRegdate(rs.getString("regdate"));
				regBean.setUrl(rs.getString("url"));
				regBean.setView(rs.getInt("view"));
				regBean.setCate(rs.getString("cate"));
				regBean.setIcon(rs.getString("icon"));
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
	public Vector<String> geticonList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<String> vlist = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select distinct icon from main_index order by view";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vlist.addElement(rs.getString("icon"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con);
		}
		return vlist;
	}	
}

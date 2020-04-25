/**
 * 
 */
package com.ss.training.wk2.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author jalveste
 *
 */
public abstract class BaseDAO<T> {

	public Connection conn = null;
	
	public BaseDAO(Connection conn)	{
		this.conn = conn;
	}

	public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException {

		// create a statement Object
		PreparedStatement pstmt = conn.prepareStatement(sql);

		if (vals != null) {
			int index = 1;
			for (Object o : vals) {
				pstmt.setObject(index, o);
				index++;
			}
		}
		
		pstmt.executeUpdate();
	}
	
	public Integer saveWithPK(String sql, Object[] vals) throws ClassNotFoundException, SQLException {

		// create a statement Object
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		if (vals != null) {
			int index = 1;
			for (Object o : vals) {
				pstmt.setObject(index, o);
				index++;
			}
		}
		
		pstmt.executeUpdate();
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())	{
			return rs.getInt(1);
		}
		return null;
	}
	
	public List<T> read(String sql, Object[] vals) throws ClassNotFoundException, SQLException {

		// create a statement Object
		PreparedStatement pstmt = conn.prepareStatement(sql);

		if (vals != null) {
			int index = 1;
			for (Object o : vals) {
				pstmt.setObject(index, o);
				index++;
			}
		}
		
		return extractData(pstmt.executeQuery());
	}
	
	public abstract List<T> extractData(ResultSet rs) throws SQLException;

}

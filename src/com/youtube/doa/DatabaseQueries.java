package com.youtube.doa;

import java.sql.*;
import org.codehaus.jettison.json.JSONArray;
import com.youtube.util.ToJSON;

public class DatabaseQueries extends SQLServerDataSource{

	/**
	 * This method allows you to delete a row from PC_PARTS table
	 * 
	 * If you need to do a delete, consider moving the data to a archive table,
	 * then delete. Or just make the data invisible to the user. Delete data can
	 * be very dangerous.
	 */
	public int deletePC_PARTS(int pk) throws Exception{

		PreparedStatement query = null;
		Connection conn = null;

		try{
			/*
			 * If this was a real application, you should do data validation
			 * here before deleting data.
			 */

			conn = this.getConnection();
			query = conn.prepareStatement("delete from PC_PARTS " + "where PC_PARTS_PK = ? ");

			query.setInt(1, pk);
			query.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
			return 500;
		}finally{
			if(conn != null)
				conn.close();
		}

		return 200;
	}

	// This method allows you to update PC_PARTS table
	// if this was a real project you must do validation here!
	public int updatePC_PARTS(int pk, int avail) throws Exception{

		PreparedStatement query = null;
		Connection conn = null;

		try{

			// If this was a real application, you should do data validation
			// here before updating data.

			conn = this.getConnection();
			query = conn.prepareStatement("update PC_PARTS " + "set PC_PARTS_AVAIL = ? " + "where PC_PARTS_PK = ? ");

			query.setInt(1, avail);
			query.setInt(2, pk);
			query.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
			return 500;
		}finally{
			if(conn != null)
				conn.close();
		}

		return 200;
	}

	/**
	 * This method will insert a record into the PC_PARTS table.
	 * 
	 * Note: there is no validation being done... if this was a real project you
	 * must do validation here!
	 * 
	 * @param PC_PARTS_TITLE
	 * @param PC_PARTS_CODE
	 * @param PC_PARTS_MAKER
	 * @param PC_PARTS_AVAIL
	 *            - integer column
	 * @param PC_PARTS_DESC
	 * @return integer 200 for success, 500 for error
	 * @throws Exception
	 */
	public int insertIntoPC_PARTS(String PC_PARTS_TITLE, String PC_PARTS_CODE, String PC_PARTS_MAKER,
			String PC_PARTS_AVAIL, String PC_PARTS_DESC) throws Exception{

		PreparedStatement query = null;
		Connection conn = null;

		try{
			/*
			 * If this was a real application, you should do data validation
			 * here before starting to insert data into the database.
			 */
			conn = this.getConnection();
			query = conn.prepareStatement("insert into PC_PARTS "
					+ "(PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC) "
					+ "VALUES ( ?, ?, ?, ?, ? ) ");

			query.setString(1, PC_PARTS_TITLE);
			query.setString(2, PC_PARTS_CODE);
			query.setString(3, PC_PARTS_MAKER);

			// PC_PARTS_AVAIL is a number column, so we need to convert the
			// String into a integer
			int avilInt = Integer.parseInt(PC_PARTS_AVAIL);
			query.setInt(4, avilInt);

			query.setString(5, PC_PARTS_DESC);
			query.executeUpdate(); // note the new command for insert statement

		}catch(Exception e){
			e.printStackTrace();
			return 500; // if a error occurs, return a 500
		}finally{
			if(conn != null)
				conn.close();
		}

		return 200;
	}

	// This method will search for a specific brand from the PC_PARTS table.
	public JSONArray queryReturnBrandParts(String brand) throws Exception{

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try{
			conn = this.getConnection();
			query = conn.prepareStatement(
					"select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
							+ "from PC_PARTS " + "where UPPER(PC_PARTS_MAKER) = ? ");

			query.setString(1, brand.toUpperCase()); // protect against sql
														// injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close();

		}catch(SQLException sqlError){
			sqlError.printStackTrace();
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return json;
		}finally{
			if(conn != null)
				conn.close();
		}

		return json;
	}

	// This method will search for the specific brand and the brands item number
	public JSONArray queryReturnBrandItemNumber(String brand, int item_number) throws Exception{

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try{
			conn = this.getConnection();
			query = conn.prepareStatement(
					"select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
							+ "from PC_PARTS " + "where UPPER(PC_PARTS_MAKER) = ? " + "and PC_PARTS_CODE = ?");

			/*
			 * protect against sql injection when you have more than one ?, it
			 * will go in chronological order.
			 */
			query.setString(1, brand.toUpperCase()); // first ?
			query.setInt(2, item_number); // second ?
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close();

		}catch(SQLException sqlError){
			sqlError.printStackTrace();
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return json;
		}finally{
			if(conn != null)
				conn.close();
		}

		return json;
	}

	// This method will return all PC parts in json format
	public JSONArray queryAllPcParts() throws Exception{

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try{
			conn = this.getConnection();
			query = conn.prepareStatement(
					"select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
							+ "from PC_PARTS");

			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); // close connection

		}catch(SQLException sqlError){
			sqlError.printStackTrace();
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return json;
		}finally{
			if(conn != null)
				conn.close();
		}

		return json;
	}

	// This method will return a time/stamp from database in json format
	public JSONArray queryCheckDbConnection() throws Exception{

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try{
			conn = this.getConnection();
			query = conn.prepareStatement("SELECT GETDATE() AS DATETIME");

			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close();

		}catch(SQLException sqlError){
			sqlError.printStackTrace();
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return json;
		}finally{
			if(conn != null)
				conn.close();
		}

		return json;
	}
}

/*
 * By using prepareStatement and the ?, we are protecting against sql injection.
 * Never add parameter straight into the prepareStatement
 */
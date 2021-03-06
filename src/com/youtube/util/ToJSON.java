package com.youtube.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import java.sql.ResultSet;
import org.owasp.esapi.ESAPI; 

public class ToJSON{ // JSONLint - JSON Validator

	// ESAPI - helps catch db poisoning
	public JSONArray toJSONArray(ResultSet rs) throws Exception{

		// JSON array that will be returned, holds all records
		JSONArray json = new JSONArray();
		String temp = null;

		try{

			// we will need the column count & names, saves the table meta-data
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();

			// loop through the ResultSet
			while(rs.next()){

				int numColumns = rsmd.getColumnCount();

				// each row in the ResultSet will be converted to a JSON Object
				JSONObject obj = new JSONObject();

				// loop through all columns and place them into JSON Object
				for(int i = 1; i < numColumns + 1; i++){

					String column_name = rsmd.getColumnName(i);

					if(rsmd.getColumnType(i) == java.sql.Types.ARRAY){
						obj.put(column_name, rs.getArray(column_name));
						System.out.println("ToJson: ARRAY");

					}else if(rsmd.getColumnType(i) == java.sql.Types.BIGINT){
						obj.put(column_name, rs.getInt(column_name));
						System.out.println("ToJson: BIGINT");

					}else if(rsmd.getColumnType(i) == java.sql.Types.BOOLEAN){
						obj.put(column_name, rs.getBoolean(column_name));
						System.out.println("ToJson: BOOLEAN");

					}else if(rsmd.getColumnType(i) == java.sql.Types.BLOB){
						obj.put(column_name, rs.getBlob(column_name));
						System.out.println("ToJson: BLOB");

					}else if(rsmd.getColumnType(i) == java.sql.Types.DOUBLE){
						obj.put(column_name, rs.getDouble(column_name));
						System.out.println("ToJson: DOUBLE");

					}else if(rsmd.getColumnType(i) == java.sql.Types.FLOAT){
						obj.put(column_name, rs.getFloat(column_name));
						System.out.println("ToJson: FLOAT");

					}else if(rsmd.getColumnType(i) == java.sql.Types.INTEGER){
						obj.put(column_name, rs.getInt(column_name));
						System.out.println("ToJson: INTEGER");

					}else if(rsmd.getColumnType(i) == java.sql.Types.NVARCHAR){
						obj.put(column_name, rs.getNString(column_name));
						System.out.println("ToJson: NVARCHAR");

					}else if(rsmd.getColumnType(i) == java.sql.Types.VARCHAR){
						temp = rs.getString(column_name); // col name to string
						// un-encodes everything, then re-encodes to html
						temp = ESAPI.encoder().canonicalize(temp);
						temp = ESAPI.encoder().encodeForHTMLAttribute(temp);
						obj.put(column_name, temp); // adds to object

						// obj.put(column_name, rs.getString(column_name));
						// System.out.println("ToJson: VARCHAR");

					}else if(rsmd.getColumnType(i) == java.sql.Types.TINYINT){
						obj.put(column_name, rs.getInt(column_name));
						System.out.println("ToJson: TINYINT");

					}else if(rsmd.getColumnType(i) == java.sql.Types.SMALLINT){
						obj.put(column_name, rs.getInt(column_name));
						System.out.println("ToJson: SMALLINT");

					}else if(rsmd.getColumnType(i) == java.sql.Types.DATE){
						obj.put(column_name, rs.getDate(column_name));
						System.out.println("ToJson: DATE");

					}else if(rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP){
						obj.put(column_name, rs.getTimestamp(column_name));
						System.out.println("ToJson: TIMESTAMP");

					}else if(rsmd.getColumnType(i) == java.sql.Types.NUMERIC){
						obj.put(column_name, rs.getBigDecimal(column_name));
						System.out.println("ToJson: NUMERIC");

					}else{
						obj.put(column_name, rs.getObject(column_name));
						System.out.println("ToJson: Object " + column_name);
					}
				}

				json.put(obj);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return json; // return JSON array
	}
}

/**
 * This utility will convert a database data into JSON format. Note: this java
 * class requires the ESAPI 1.4.4 jar file ESAPI is used to encode data
 */

/**
 * This will convert database records into a JSON Array Simply pass in a
 * ResultSet from a database connection and it loop return a JSON array.
 * 
 * It important to check to make sure that all DataType that are being used is
 * properly encoding.
 * 
 * varchar is currently the only dataType that is being encode by ESAPI
 * 
 * @param rs
 *            - database ResultSet
 * @return - JSON array
 * @throws Exception
 */

// }else if(rsmd.getColumnType(i) == java.sql.Types.VARCHAR){
//
// temp = rs.getString(column_name); // saving column data
// // to temp variable
// // temp = ESAPI.encoder().canonicalize(temp); //
// // decoding
// // data to
// // base
// // state
// // temp = ESAPI.encoder().encodeForHTML(temp); //
// // encoding
// // to be
// // browser
// // safe
// obj.put(column_name, temp); // putting data into JSON
// // object
//
// // obj.put(column_name, rs.getString(column_name));
// // /*Debug*/ System.out.println("ToJson: VARCHAR");
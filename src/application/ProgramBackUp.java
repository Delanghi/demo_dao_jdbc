package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import db.DB;

public class ProgramBackUp {

	public static void main(String[] args) {

 // OBJECTS
		Connection conn = null;
		PreparedStatement st = null;
		
 // METHODS
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE "
					+ "Id = ? " );
			
			st.setInt(1, 2); 
		
			int rowsAffected = st.executeUpdate();
				System.out.println("Done! Rows affected: " + rowsAffected);			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
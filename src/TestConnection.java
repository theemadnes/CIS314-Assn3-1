import java.sql.*;


public class TestConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final String DB_URL = "jdbc:derby:testDB;create=true";
		
		try
		{
			Connection conn = DriverManager.getConnection(DB_URL);
			System.out.println("Connected to DB " + DB_URL);
			conn.close();
		}
		catch (Exception ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
		

	}

}

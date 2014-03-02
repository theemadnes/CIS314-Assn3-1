// Note *** IMPORTANT ***
// At execution this app builds the database needed
// At execution completion it removes all tables
// If execution is interupted and the DB still exists, the app will error
// To fix simply change the DB name in the DB_URL string to another name and execute

import java.sql.*;
import java.util.Scanner;
import java.util.UUID;


public class Assn3Database {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		final String DB_URL = "jdbc:derby:amattson;create=true";
		Statement stmt = null;
		Scanner userInput = new Scanner(System.in);
		boolean keepGoing = true;
		int userInputInt;
		String userInputString;
		int userInputInt2;
		String userInputString2;
		// String sql;
		
		// Create the database, if it doesn't already exist, and start working with the user
		try
		{
			conn = DriverManager.getConnection(DB_URL);
			stmt = conn.createStatement();
			System.out.println("Connected to DB " + DB_URL);
			generateDatabase(stmt);
			
			// Go through the main process
			while (keepGoing)
			{
				try
				{
					displayMainMenu();
					userInputString = userInput.nextLine();
					if (userInputString.equalsIgnoreCase("q"))
					{
						keepGoing = false;
						continue;
					}
					else 
					{
						userInputInt = Integer.parseInt(userInputString);
					}
					
					displayTableMenu();
					userInputString2 = userInput.nextLine();
					if (userInputString2.equalsIgnoreCase("b"))
					{
						continue;
					}
					else 
					{
						userInputInt2 = Integer.parseInt(userInputString2);
					}
					
					// System.out.println(userInputInt + " " + userInputInt2);
					
					if (userInputInt == 1 && userInputInt2 == 1)
						displayTableBranch(stmt);
					else if (userInputInt == 1 && userInputInt2 == 2)
						displayTableStaff(stmt);
					else if (userInputInt == 1 && userInputInt2 == 3)
						displayTableSession(stmt);
					else if (userInputInt == 1 && userInputInt2 == 4)
						displayTableBSS(stmt);
					else if (userInputInt == 2 && userInputInt2 == 1)
						addBranch(stmt);
					else if (userInputInt == 2 && userInputInt2 == 2)
						addStaff(stmt);
					else if (userInputInt == 2 && userInputInt2 == 3)
						addSession(stmt);
					else if (userInputInt == 2 && userInputInt2 == 4)
						addBSS(stmt);
					else if (userInputInt == 3 && userInputInt2 == 1)
						updateBranch(stmt);
					else if (userInputInt == 3 && userInputInt2 == 2)
						updateStaff(stmt);
					else if (userInputInt == 3 && userInputInt2 == 3)
						updateSession(stmt);
					else if (userInputInt == 3 && userInputInt2 == 4)
						updateBSS(stmt);
					else if (userInputInt == 4 && userInputInt2 == 1)
						deleteBranch(stmt);
					else if (userInputInt == 4 && userInputInt2 == 2)
						deleteStaff(stmt);
					else if (userInputInt == 4 && userInputInt2 == 3)
						deleteSession(stmt);
					else if (userInputInt == 4 && userInputInt2 == 4)
						deleteBSS(stmt);
					
				
				}
				catch (NumberFormatException | SQLException ex)
				{
					System.out.println("ERROR: " + ex.getMessage());
				}
			}
			
		}
		catch (SQLException | NumberFormatException ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
		
		
		
		
		
		// End the session
		try
		{	
			databaseTeardown(stmt);
			// Close out DB at completion
			System.out.println("Closing connection to DB " + DB_URL);
			stmt.close();
			conn.close();
		}
		catch (SQLException se)
		{
			System.out.println("ERROR: " + se.getMessage());
		}
		
	}
	
	// used to generate primary keys 
	public static String getUUID ()
	{
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	public static void displayTableBranch(Statement stmt) throws SQLException
	{
		String sql;
		ResultSet rs;
		
		sql = "SELECT * FROM Branch";
		rs = stmt.executeQuery(sql);
		
		System.out.println("Branch Details");
		while(rs.next()){
			System.out.println("Branch ID: " + rs.getString("BranchID"));
			System.out.println("Name: " + rs.getString("Name"));
			System.out.println("Address: " + rs.getString("Address"));
			System.out.println("City: " + rs.getString("City"));
			System.out.println("State: " + rs.getString("State"));
			System.out.println("Zip Code: " + rs.getString("Zip"));
		}
	}
		public static void displayTableStaff(Statement stmt) throws SQLException
		{
			String sql;
			ResultSet rs;
			
			sql = "SELECT * FROM Staff";
			rs = stmt.executeQuery(sql);
			
			System.out.println("Staff Details");
			while(rs.next()){
				System.out.println("Staff ID: " + rs.getString("StaffID"));
				System.out.println("First Name: " + rs.getString("FirstName"));
				System.out.println("Last Name: " + rs.getString("LastName"));
				System.out.println("Position: " + rs.getString("Position"));

			}
		
		}
		
		public static void displayTableSession(Statement stmt) throws SQLException
		{
			String sql;
			ResultSet rs;
			
			sql = "SELECT * FROM Session";
			rs = stmt.executeQuery(sql);
			
			System.out.println("Session Details");
			while(rs.next()){
				System.out.println("Session ID: " + rs.getString("SessionID"));
				System.out.println("Name: " + rs.getString("Name"));

			}
		
		}
		
		public static void displayTableBSS(Statement stmt) throws SQLException
		{
			String sql;
			ResultSet rs;
			
			sql = "SELECT * FROM BranchStaffSession";
			rs = stmt.executeQuery(sql);
			System.out.println("Branch, Staff, & Session Relationship Details - Refer to Source tables for proper names");
			while(rs.next()){
				System.out.println("Branch ID: " + rs.getString("BranchID"));
				System.out.println("Staff ID: " + rs.getString("StaffID"));
				System.out.println("Session ID: " + rs.getString("SessionID"));
				System.out.println("Date: " + rs.getString("Date"));
				System.out.println("AM / PM: " + rs.getString("amPM"));
				System.out.println("Fee: $" + rs.getDouble("Fee"));
			}
		}

		public static void generateDatabase(Statement stmt) throws SQLException
		{
			// Build the databases
			String sql;
			sql = "CREATE TABLE Branch " + 
								"(BranchID CHAR(36) NOT NULL PRIMARY KEY, " + 
								"Name CHAR(25), " +
								"Address CHAR(25), " +
								"City CHAR(20), " +
								"State CHAR(2), " +
								"Zip CHAR(5) )";
						
			stmt.execute(sql);
						
			sql = "CREATE TABLE Staff " + 
								"(StaffID CHAR(36) NOT NULL PRIMARY KEY, " + 
								"FirstName CHAR(25), " +
								"LastName CHAR(25), " +
								"Position CHAR(20) )"; 
						
			stmt.execute(sql);

			sql = "CREATE TABLE Session " + 
								"(SessionID CHAR(36) NOT NULL PRIMARY KEY, " + 
								"Name CHAR(25) )";
						
			stmt.execute(sql);
						
			sql = "CREATE TABLE BranchStaffSession " + 
								"(BranchID CHAR(36) NOT NULL REFERENCES Branch(BranchID), " + 
								"StaffID CHAR(36) NOT NULL REFERENCES Staff(StaffID), " +
								"SessionID CHAR(36) NOT NULL REFERENCES Session(SessionID), " +
								"Date CHAR(20), " +
								"amPM CHAR(2), " +
								"Fee DOUBLE )";
						
			stmt.execute(sql);
						
			// generate some data
						
			sql = "INSERT INTO Branch (BranchID, Name, Address, City, State, Zip) " +
								"VALUES('" + "ad4bb147-b062-47c1-8d2b-b4de235e8dc0" + "', 'Location 1', '123 Main St', 'Chicago', 'IL', '60606')";	
			stmt.executeUpdate(sql);
						
			sql = "INSERT INTO Branch (BranchID, Name, Address, City, State, Zip) " +
								"VALUES('" + "427dc66e-8969-4593-afbd-b59d6ff549cd" + "', 'Location 2', '456 Main St', 'Chicago', 'IL', '60606')";	
			stmt.executeUpdate(sql);
						
			sql = "INSERT INTO Branch (BranchID, Name, Address, City, State, Zip) " +
								"VALUES('" + "5d8227e2-94c2-43a6-ac54-16f46ca35487" + "', 'Location 3', '456 South St', 'Waukegan', 'IL', '65606')";	
			stmt.executeUpdate(sql);
						
			sql = "INSERT INTO Staff (StaffID, FirstName, LastName, Position) " +
								"VALUES('" + "238a1fb5-812e-4a4c-b4cd-a2223d3cbfa2" + "', 'Joe', 'Smith', 'Manager')";	
			stmt.executeUpdate(sql);
						
			sql = "INSERT INTO Staff (StaffID, FirstName, LastName, Position) " +
								"VALUES('" + "e3e5ed2e-df46-4907-9bbe-007766865cd5" + "', 'Julie', 'North', 'Teacher')";	
			stmt.executeUpdate(sql);
						
			sql = "INSERT INTO Staff (StaffID, FirstName, LastName, Position) " +
								"VALUES('" + "e6f2aeb3-4a6e-4d72-aac7-bf08441f0843" + "', 'Jerry', 'South', 'Teacher')";	
			stmt.executeUpdate(sql);
						
			sql = "INSERT INTO Session (SessionID, Name) " +
								"VALUES('" + "4b4c18dc-a15a-410f-8e51-86ebce026f96" + "', 'Art')";	
			stmt.executeUpdate(sql);
						
			sql = "INSERT INTO Session (SessionID, Name) " +
								"VALUES('" + "f9ef61c9-48fe-4526-907c-6de5c985d58c" + "', 'History')";	
			stmt.executeUpdate(sql);
						
			sql = "INSERT INTO Session (SessionID, Name) " +
					"VALUES('" + "dc3f970a-c26a-41db-acd2-78f2eee345f6" + "', 'Crafts')";	
			stmt.executeUpdate(sql);		
			
			sql = "INSERT INTO BranchStaffSession (BranchID, StaffID, SessionID, Date, amPM, Fee) " +
					"VALUES('ad4bb147-b062-47c1-8d2b-b4de235e8dc0', 'e3e5ed2e-df46-4907-9bbe-007766865cd5', '4b4c18dc-a15a-410f-8e51-86ebce026f96', 'Dec 25', 'AM', 50.00)";
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO BranchStaffSession (BranchID, StaffID, SessionID, Date, amPM, Fee) " +
					"VALUES('427dc66e-8969-4593-afbd-b59d6ff549cd', 'e6f2aeb3-4a6e-4d72-aac7-bf08441f0843', 'f9ef61c9-48fe-4526-907c-6de5c985d58c', 'Dec 29', 'AM', 55.00)";
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO BranchStaffSession (BranchID, StaffID, SessionID, Date, amPM, Fee) " +
					"VALUES('5d8227e2-94c2-43a6-ac54-16f46ca35487', 'e6f2aeb3-4a6e-4d72-aac7-bf08441f0843', 'f9ef61c9-48fe-4526-907c-6de5c985d58c', 'Dec 30', 'PM', 10.00)";
			stmt.executeUpdate(sql);
		}
		
		public static void databaseTeardown(Statement stmt) throws SQLException
		{
			String sql;
			
			// Drop the DB tables 
			sql = "DROP TABLE BranchStaffSession";
			stmt.execute(sql);
						
			sql = "DROP TABLE Branch";
			stmt.execute(sql);
						
			sql = "DROP TABLE Staff";
			stmt.execute(sql);
						
			sql = "DROP TABLE Session";
			stmt.execute(sql);
		}
		
		public static void addBranch(Statement stmt) throws SQLException
		{
			String sql, userInputName, userInputAddress, userInputCity, userInputState, userInputZip;
			Scanner userInputAdd = new Scanner(System.in);
			System.out.println("Enter Branch Name (up to 25 characters): ");
			userInputName = userInputAdd.nextLine();
			System.out.println("Enter Branch Street Address (up to 25 characters): ");
			userInputAddress = userInputAdd.nextLine();
			System.out.println("Enter Branch City (up to 20 characters): ");
			userInputCity = userInputAdd.nextLine();
			System.out.println("Enter Branch State (up to 2 characters): ");
			userInputState = userInputAdd.nextLine();
			System.out.println("Enter Branch Zip Code (up to 6 characters): ");
			userInputZip = userInputAdd.nextLine();
			
			sql = "INSERT INTO Branch (BranchID, Name, Address, City, State, Zip) " +
					"VALUES('" + getUUID() + "', '" + userInputName + "', '" + userInputAddress + "', '" + userInputCity + "', '" + userInputState + "', '" + userInputZip + "')";	
			stmt.executeUpdate(sql);
		}
		
		public static void addStaff(Statement stmt) throws SQLException
		{
			String sql, userInputFirstName, userInputLastName, userInputPosition;
			Scanner userInputAdd = new Scanner(System.in);
			System.out.println("Enter Staff First Name (up to 25 characters): ");
			userInputFirstName = userInputAdd.nextLine();
			System.out.println("Enter Staff Last Name (up to 25 characters): ");
			userInputLastName = userInputAdd.nextLine();
			System.out.println("Enter Position (up to 20 characters): ");
			userInputPosition = userInputAdd.nextLine();
			
			sql = "INSERT INTO Staff (StaffID, FirstName, LastName, Position) " +
					"VALUES('" + getUUID() + "', '" + userInputFirstName + "', '" + userInputLastName + "', '" + userInputPosition + "')";	
			stmt.executeUpdate(sql);
		}
		
		public static void addSession(Statement stmt) throws SQLException
		{
			String sql, userInputName;
			Scanner userInputAdd = new Scanner(System.in);
			System.out.println("Enter Session Name (up to 25 characters): ");
			userInputName = userInputAdd.nextLine();
			
			sql = "INSERT INTO Session (SessionID, Name) " +
					"VALUES('" + getUUID() + "', '" + userInputName + "')";	
			stmt.executeUpdate(sql);
		}
		
		public static void addBSS(Statement stmt) throws SQLException
		{
			String sql, userInputSessionID, userInputBranchID, userInputStaffID, userInputDate, userInputAMPM, userInputFee;
			double userInputFeeDouble;
			Scanner userInputAdd = new Scanner(System.in);
			
			System.out.println("Enter Branch ID (cut n paste from Branch table): ");
			userInputBranchID = userInputAdd.nextLine();
			System.out.println("Enter Staff ID (cut n paste from Staff table): ");
			userInputStaffID = userInputAdd.nextLine();
			System.out.println("Enter Session ID (cut n paste from Session table): ");
			userInputSessionID = userInputAdd.nextLine();
			System.out.println("Enter Registration Date (up to 20 characters): ");
			userInputDate = userInputAdd.nextLine();
			
			// handle AM/PM selection by defaulting to AM
			System.out.println("Enter AM/PM session (up to 2 characters - invalid input will default to AM): ");
			userInputAMPM = userInputAdd.nextLine().toUpperCase();
			if (!userInputAMPM.equals("AM") && !userInputAMPM.equals("PM")) 
				userInputAMPM = "AM";
			
			System.out.println("Enter session fee (non-negative value; if invalid or negative, default value of 50.00 will be used): ");
			// handle input of fee	
			userInputFee = userInputAdd.nextLine();
			
			try{
				
				userInputFeeDouble = Double.parseDouble(userInputFee);
				if (userInputFeeDouble < 0.0)
				{
					System.out.println("Negative value supplied; using default $50.00 fee.");
					userInputFeeDouble = 50.00;
				}
					
			} 
			catch (NumberFormatException | NullPointerException ex){
				System.out.println("ERROR: " + ex.getMessage());
				System.out.println("Non-numeric value supplied; using default $50.00 fee.");
				userInputFeeDouble = 50.00;
			}
			
			sql = "INSERT INTO BranchStaffSession (BranchID, StaffID, SessionID, Date, amPM, Fee) " +
					"VALUES('" + userInputBranchID + "', '" + userInputStaffID + "', '" + userInputSessionID + "', '" + userInputDate + "', '" + userInputAMPM + "', " + userInputFeeDouble + ")";	
			stmt.executeUpdate(sql);
			
		}
		
		public static void deleteBranch(Statement stmt) throws SQLException
		{
			String sql, userInputBranchID;
			Scanner userInputDelete = new Scanner(System.in);
			
			System.out.println("Enter Branch ID (cut n paste from Branch table): ");
			userInputBranchID = userInputDelete.nextLine();
			
			sql = "DELETE FROM Branch WHERE BranchID = '" + userInputBranchID + "'";
			stmt.executeUpdate(sql);
		}
		
		public static void deleteStaff(Statement stmt) throws SQLException
		{
			String sql, userInputStaffID;
			Scanner userInputDelete = new Scanner(System.in);
			
			System.out.println("Enter Staff ID (cut n paste from Staff table): ");
			userInputStaffID = userInputDelete.nextLine();
			
			sql = "DELETE FROM Staff WHERE StaffID = '" + userInputStaffID + "'";
			stmt.executeUpdate(sql);
		}
		
		public static void deleteSession(Statement stmt) throws SQLException
		{
			String sql, userInputSessionID;
			Scanner userInputDelete = new Scanner(System.in);
			
			System.out.println("Enter Session ID (cut n paste from Session table): ");
			userInputSessionID = userInputDelete.nextLine();
			
			sql = "DELETE FROM Session WHERE SessionID = '" + userInputSessionID + "'";
			stmt.executeUpdate(sql);
		}
		
		public static void deleteBSS(Statement stmt) throws SQLException
		{
			String sql, userInputBranchID, userInputStaffID, userInputSessionID;
			Scanner userInputDelete = new Scanner(System.in);	
			
			// Need details on all 3 FKs
			System.out.println("Enter Branch ID (cut n paste from Branch table): ");
			userInputBranchID = userInputDelete.nextLine();
			
			System.out.println("Enter Staff ID (cut n paste from Staff table): ");
			userInputStaffID = userInputDelete.nextLine();
			
			System.out.println("Enter Session ID (cut n paste from Session table): ");
			userInputSessionID = userInputDelete.nextLine();
			
			sql = "DELETE FROM BranchStaffSession WHERE SessionID = '" + 
			userInputSessionID + "' AND BranchID = '" + userInputBranchID + 
			"' AND StaffID = '" + userInputStaffID + "'";
			
			stmt.executeUpdate(sql);
		}
		
		public static void updateBranch(Statement stmt) throws SQLException
		{
			String sql, userInputBranchID, userInputName, userInputAddress, userInputCity, userInputState, userInputZip;
			Scanner userInputUpdate = new Scanner(System.in);
			
			System.out.println("Enter Branch ID (cut n paste from Branch table): ");
			userInputBranchID = userInputUpdate.nextLine();
			
			System.out.println("Enter Branch Name (up to 25 characters): ");
			userInputName = userInputUpdate.nextLine();
			
			System.out.println("Enter Branch Street Address (up to 25 characters): ");
			userInputAddress = userInputUpdate.nextLine();
			
			System.out.println("Enter Branch City (up to 20 characters): ");
			userInputCity = userInputUpdate.nextLine();
			
			System.out.println("Enter Branch State (up to 2 characters): ");
			userInputState = userInputUpdate.nextLine();
			
			System.out.println("Enter Branch Zip Code (up to 6 characters): ");
			userInputZip = userInputUpdate.nextLine();
			
			sql = "UPDATE Branch SET Name = '" + userInputName + 
					"' WHERE BranchID = '" + userInputBranchID + "'";
			stmt.executeUpdate(sql);
			
			sql = "UPDATE Branch SET Address = '" + userInputAddress + 
					"' WHERE BranchID = '" + userInputBranchID + "'";
			stmt.executeUpdate(sql);
			
			sql = "UPDATE Branch SET City = '" + userInputCity + 
					"' WHERE BranchID = '" + userInputBranchID + "'";
			stmt.executeUpdate(sql);
			
			sql = "UPDATE Branch SET State = '" + userInputState + 
					"' WHERE BranchID = '" + userInputBranchID + "'";
			stmt.executeUpdate(sql);
			
			sql = "UPDATE Branch SET Zip = '" + userInputZip + 
					"' WHERE BranchID = '" + userInputBranchID + "'";
			stmt.executeUpdate(sql);
		}
		
		public static void updateStaff(Statement stmt) throws SQLException
		{
			String sql, userInputStaffID, userInputFirstName, userInputLastName, userInputPosition;
			Scanner userInputUpdate = new Scanner(System.in);
			
			System.out.println("Enter Staff ID (cut n paste from Staff table): ");
			userInputStaffID = userInputUpdate.nextLine();
			
			System.out.println("Enter Staff First Name (up to 25 characters): ");
			userInputFirstName = userInputUpdate.nextLine();
			
			System.out.println("Enter Staff Last Name (up to 25 characters): ");
			userInputLastName = userInputUpdate.nextLine();
			
			System.out.println("Enter Position (up to 20 characters): ");
			userInputPosition = userInputUpdate.nextLine();
			
			sql = "UPDATE Staff SET FirstName = '" + userInputFirstName + 
					"' WHERE StaffID = '" + userInputStaffID + "'";
			stmt.executeUpdate(sql);
			
			sql = "UPDATE Staff SET LastName = '" + userInputLastName + 
					"' WHERE StaffID = '" + userInputStaffID + "'";
			stmt.executeUpdate(sql);
			
			sql = "UPDATE Staff SET Position = '" + userInputPosition + 
					"' WHERE StaffID = '" + userInputStaffID + "'";
			stmt.executeUpdate(sql);	

		}
		
		public static void updateSession(Statement stmt) throws SQLException
		{
			String sql, userInputSessionID, userInputName;
			Scanner userInputUpdate = new Scanner(System.in);
			
			System.out.println("Enter Session ID (cut n paste from Session table): ");
			userInputSessionID = userInputUpdate.nextLine();
			
			System.out.println("Enter Session Name (up to 25 characters): ");
			userInputName = userInputUpdate.nextLine();
			
			sql = "UPDATE Session SET Name = '" + userInputName + 
					"' WHERE SessionID = '" + userInputSessionID + "'";
			stmt.executeUpdate(sql);
		}
		
		public static void updateBSS(Statement stmt) throws SQLException
		{
			String sql, userInputSessionID, userInputStaffID, userInputBranchID, userInputDate, userInputAMPM, userInputFee;
			double userInputFeeDouble;
			Scanner userInputUpdate = new Scanner(System.in);
			
			System.out.println("Enter Branch ID (cut n paste from Branch table): ");
			userInputBranchID = userInputUpdate.nextLine();
			
			System.out.println("Enter Staff ID (cut n paste from Staff table): ");
			userInputStaffID = userInputUpdate.nextLine();
			
			System.out.println("Enter Session ID (cut n paste from Session table): ");
			userInputSessionID = userInputUpdate.nextLine();
			
			System.out.println("Enter Registration Date (up to 20 characters): ");
			userInputDate = userInputUpdate.nextLine();	

			sql = "UPDATE BranchStaffSession SET Date = '" + userInputDate + 
					"' WHERE SessionID = '" + userInputSessionID + 
					"' AND BranchID = '" + userInputBranchID + 
					"' AND StaffID = '" + userInputStaffID + "'";
			stmt.executeUpdate(sql);
			
			// handle AM/PM selection by defaulting to AM
			System.out.println("Enter AM/PM session (up to 2 characters - invalid input will default to AM): ");
			userInputAMPM = userInputUpdate.nextLine().toUpperCase();
			if (!userInputAMPM.equals("AM") && !userInputAMPM.equals("PM")) 
				userInputAMPM = "AM";
			
			sql = "UPDATE BranchStaffSession SET amPM = '" + userInputAMPM + 
					"' WHERE SessionID = '" + userInputSessionID + 
					"' AND BranchID = '" + userInputBranchID + 
					"' AND StaffID = '" + userInputStaffID + "'";
			stmt.executeUpdate(sql);
			
			System.out.println("Enter session fee (non-negative value; if invalid or negative, default value of 50.00 will be used): ");
			// handle input of fee	
			userInputFee = userInputUpdate.nextLine();
			
			try{
				
				userInputFeeDouble = Double.parseDouble(userInputFee);
				if (userInputFeeDouble < 0.0)
				{
					System.out.println("Negative value supplied; using default $50.00 fee.");
					userInputFeeDouble = 50.00;
				}
					
			} 
			catch (NumberFormatException | NullPointerException ex){
				System.out.println("ERROR: " + ex.getMessage());
				System.out.println("Non-numeric value supplied; using default $50.00 fee.");
				userInputFeeDouble = 50.00;
			}
			
			sql = "UPDATE BranchStaffSession SET Fee = " + userInputFeeDouble + 
					" WHERE SessionID = '" + userInputSessionID + 
					"' AND BranchID = '" + userInputBranchID + 
					"' AND StaffID = '" + userInputStaffID + "'";
			stmt.executeUpdate(sql);
			
		}
		
		public static void displayMainMenu()
		{
			System.out.println("Welcome to the DB App");
			System.out.println("1 - Display DB Table");
			System.out.println("2 - Add to DB Table");
			System.out.println("3 - Update row in DB Table");
			System.out.println("4 - Delete row in DB Table");
			System.out.println("Q - Quit");
		}
		
		public static void displayTableMenu()
		{
			System.out.println("Select table to use");
			System.out.println("1 - Branch");
			System.out.println("2 - Staff");
			System.out.println("3 - Session");
			System.out.println("4 - Branch / Staff / Session");
			System.out.println("B - Go Back");
		}
}

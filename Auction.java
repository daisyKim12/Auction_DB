import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text. *;
import java.util. *;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Auction {
	private static Scanner scanner = new Scanner(System.in);
	private static String userID;
	private static Connection conn;

	enum Category {
		ELECTRONICS, 
		BOOKS,
		HOME,
		CLOTHING,
		SPORTINGGOODS,
		OTHERS
	}
	enum Condition {
		NEW,
		LIKE_NEW,
		GOOD,
		ACCEPTABLE
	}

	// Done
	private static boolean LoginMenu(){
		String entered_userID, entered_userPW;

		System.out.print("----< User Login >\n" +
				" ** To go back, enter 'back' in user ID.\n" +
				"     user ID: ");
		try{
			entered_userID = scanner.next();
			scanner.nextLine();

			if(entered_userID.equalsIgnoreCase("back")){
				return false;
			}

			System.out.print("     password: ");
			entered_userPW = scanner.next();
			scanner.nextLine();
		}catch (java.util.InputMismatchException e) {
			System.out.println("Error: Invalid input is entered. Try again.");
			entered_userID = null;
			return false;
		}

		/* Your code should come here to check ID and password */ 
        String selectQuery = "SELECT UserID, Password, IsAdmin FROM Users WHERE UserID = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(selectQuery)){
			// Set statment
            ps.setString(1, entered_userID);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Check if a user with the provided username exists
            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                boolean storedIsAdmin = rs.getBoolean("IsAdmin");

                // Compare the entered password with the stored password
                if (entered_userPW.equals(storedPassword)) {
					userID = entered_userID;
                } else {
					System.out.println("Error: Incorrect user name or password");
					return false;
				}
            }
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		System.out.println("You are successfully logged in.\n");
		return true;
	}

	private static boolean SignupMenu() {
		/* 2. Sign Up */
		String new_userID, new_userpass;
		boolean new_isAdmin;
		System.out.print("----< Sign Up >\n" + 
				" ** To go back, enter 'back' in user ID.\n" +
				"---- user name: ");
		try {
			new_userID = scanner.next();
			scanner.nextLine();
			if(new_userID.equalsIgnoreCase("back")){
				return false;
			}
			System.out.print("---- password: ");
			new_userpass = scanner.next();
			scanner.nextLine();
			System.out.print("---- In this user an administrator? (Y/N): ");
			String temp = scanner.next().toLowerCase();
			if (temp.equals("Y")) {
				new_isAdmin = true;
			} else if (temp.equals("N")) {
				new_isAdmin = false;
			} else {
				return false;
			}
			scanner.nextLine();
		} catch (java.util.InputMismatchException e) {
			System.out.println("Error: Invalid input is entered. Please select again.");
			return false;
		}

		/* TODO: Your code should come here to create a user account in your database */
		// Create a prepared statement for inserting user data into the Users table
		String insertQuery = "INSERT INTO Users (UserID, Password, IsAdmin) VALUES (?, ?, ?)";
		
		try(PreparedStatement ps = conn.prepareStatement(insertQuery)){
			// Set values for the prepared statement
			ps.setString(1, new_userID);
			ps.setString(2, new_userpass);
			ps.setBoolean(3, new_isAdmin);

			// Execute the insert query
			ps.executeUpdate();
		}
		catch (SQLException e) {
			handleSQLException(e);
			switch(Integer.parseInt(e.getSQLState())){
				case 23505:
					System.out.println("Error: User ID already exists. Please choose a different ID.");
					break;
				default:
					System.out.println("Error: Unable to create the user account.");
					break;
			}
		}

		System.out.println("Your account has been successfully created.\n");
		return true;
	}

	private static boolean SellMenu() {
		Category category = null;
		Condition condition = null;
		String description = " ";
		char choice;
		int price;
		Timestamp datePosted;
		boolean flag_catg = true, flag_cond = true;

		do{
			System.out.println(
					"----< Sell Item >\n" +
					"---- Choose a category.\n" +
					"    1. Electronics\n" +
					"    2. Books\n" +
					"    3. Home\n" +
					"    4. Clothing\n" +
					"    5. Sporting Goods\n" +
					"    6. Other Categories\n" +
					"    P. Go Back to Previous Menu"
					);

			try {
				choice = scanner.next().charAt(0);;
			}catch (java.util.InputMismatchException e) {
				System.out.println("Error: Invalid input is entered. Try again.");
				continue;
			}

			flag_catg = true;

			switch ((int) choice){
				case '1':
					category = Category.ELECTRONICS;
					continue;
				case '2':
					category = Category.BOOKS;
					continue;
				case '3':
					category = Category.HOME;
					continue;
				case '4':
					category = Category.CLOTHING;
					continue;
				case '5':
					category = Category.SPORTINGGOODS;
					continue;
				case '6':
					category = Category.OTHERS;
					continue;
				case 'p':
				case 'P':
					return false;
				default:
					System.out.println("Error: Invalid input is entered. Try again.");
					flag_catg = false;
					continue;
			}
		}while(!flag_catg);

		do{
			System.out.println(
					"---- Select the condition of the item to sell.\n" +
					"   1. New\n" +
					"   2. Like-new\n" +
					"   3. Used (Good)\n" +
					"   4. Used (Acceptable)\n" +
					"   P. Go Back to Previous Menu"
					);

			try {
				choice = scanner.next().charAt(0);;
				scanner.nextLine();
			}catch (java.util.InputMismatchException e) {
				System.out.println("Error: Invalid input is entered. Try again.");
				continue;
			}

			flag_cond = true;

			switch (choice) {
				case '1':
					condition = Condition.NEW;
					break;
				case '2':
					condition = Condition.LIKE_NEW;
					break;
				case '3':
					condition = Condition.GOOD;
					break;
				case '4':
					condition = Condition.ACCEPTABLE;
					break;
				case 'p':
				case 'P':
					return false;
				default:
					System.out.println("Error: Invalid input is entered. Try again.");
					flag_cond = false;
					continue;
			}
		}while(!flag_cond);

		try {
			System.out.println("---- Description of the item (one line): ");
			description = scanner.nextLine();
			System.out.println("---- Buy-It-Now price: ");

			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("Invalid input is entered. Please enter Buy-It-Now price: ");
			}

			price = scanner.nextInt();
			scanner.nextLine();

			System.out.print("---- Bid closing date and time (YYYY-MM-DD HH:MM): ");
			// you may assume users always enter valid date/time
			String date = scanner.nextLine();  /* "2023-03-04 11:30"; */
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
			datePosted = Timestamp.valueOf(localDateTime);

		}catch (Exception e) {
			System.out.println("Error: Invalid input is entered. Going back to the previous menu.");
			return false;
		}

		/* TODO: Your code should come here to store the user inputs in your database */

		String insertQuery = "INSERT INTO Items (Category, Description, Condition, SellerID, BuyItNowPrice, DatePosted) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(insertQuery)) {
          
            // Set values for the prepared statement
            ps.setString(1, category.toString());
            ps.setString(2, description);
            ps.setString(3, condition.toString());
            ps.setString(4, userID); // Use the ID of the currently logged-in user
            ps.setInt(5, price);
			ps.setTimestamp(6, datePosted);

            // Execute the INSERT query
            ps.executeUpdate();

        } catch (SQLException e) {
			handleSQLException(e);
		}

		System.out.println("Your item has been successfully listed.\n");
		return true;
	}

	public static void CheckSellStatus(){
		/* TODO: Check the status of the item the current user is selling */

		System.out.printf("%-10s | %-30s | %-20s | %-10s | %s%n", "Item ID", "Description", "Bidder (Buyer ID)", "Bid Price", "Bidding Date/Time");
		System.out.println("------------------------------------------------------------");

		
		String query = "SELECT i.ItemID, i.Description, b.BidderID, b.BidPrice, i.DatePosted "
				+ "FROM Items i "
				+ "LEFT JOIN Biding b ON i.ItemID = b.ItemID "
				+ "WHERE i.SellerID = ? and DatePosted > NOW() - INTERVAL '1 SECOND'";

		try(PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, userID); // Replace with the actual current user's ID

			// Execute the query
			ResultSet rs = ps.executeQuery();

			// Iterate through the result set and print the data
			while (rs.next()) {
				
				String itemID = String.valueOf(rs.getInt("ItemID"));
				String description = rs.getString("Description");
				String bidderID = rs.getString("BidderID");
				BigDecimal bidPrice = rs.getBigDecimal("BidPrice");
				Timestamp datePosted = rs.getTimestamp("DatePosted");

				// Print the data in a tabular format
				System.out.printf("%-10s | %-30s | %-20s | %-10s | %s%n",
						itemID, description, bidderID, bidPrice, datePosted);

			}

		}
		catch(SQLException e) {
			handleSQLException(e);
		}

	}

	// In Progress
	private static boolean AdminMenu() {
		/* 3. Login as Administrator */
		char choice;
		String adminname, adminpass;
		String keyword, seller;
		System.out.print("----< Login as Administrator >\n" +
				" ** To go back, enter 'back' in user ID.\n" +
				"---- admin ID: ");

		try {
			adminname = scanner.next();
			scanner.nextLine();
			if(adminname.equalsIgnoreCase("back")){
				return false;
			}
			System.out.print("---- password: ");
			adminpass = scanner.nextLine();
			// TODO: check the admin's account and password.
		} catch (java.util.InputMismatchException e) {
			System.out.println("Error: Invalid input is entered. Try again.");
			return false;
		}

		// check login pw and isAdmin
        String selectQuery = "SELECT UserID, Password, IsAdmin FROM Users WHERE UserID = ?";
		boolean login_success = false;

		try(PreparedStatement ps = conn.prepareStatement(selectQuery)){
			// Set statment
            ps.setString(1, adminname);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Check if a user with the provided username exists
            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                boolean storedIsAdmin = rs.getBoolean("IsAdmin");

                // Compare the entered password with the stored password
                if (adminpass.equals(storedPassword) && storedIsAdmin == true) {
					userID = adminname;
					System.out.println("You are successfully logged in.\n");
					login_success = true;
                } else {
					System.out.println("Error: Incorrect user name or password or not a Admin");
					login_success = false;
				}
            }
		}
		catch(SQLException e){
			handleSQLException(e);
		}

		if(!login_success){
			// login failed. go back to the previous menu.
			return false;
		}

		do {
			System.out.println(
					"----< Admin menu > \n" +
					"    1. Print Sold Items per Category \n" +
					"    2. Print Account Balance for Seller \n" +
					"    3. Print Seller Ranking \n" +
					"    4. Print Buyer Ranking \n" +
					"    P. Go Back to Previous Menu"
					);

			try {
				choice = scanner.next().charAt(0);;
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Error: Invalid input is entered. Try again.");
				continue;
			}

			if (choice == '1') {
				System.out.println("----Enter Category to search : ");
				keyword = scanner.next();
				scanner.nextLine();
				/*TODO: Print Sold Items per Category */
				System.out.println("sold item       | sold date       | seller ID   | buyer ID   | price | commi`ssions");
				System.out.println("----------------------------------------------------------------------------------");
				/*
				   while(rset.next()){
				   }
				 */
				continue;
			} else if (choice == '2') {
				/*TODO: Print Account Balance for Seller */
				System.out.println("---- Enter Seller ID to search : ");
				seller = scanner.next();
				scanner.nextLine();
				System.out.println("sold item       | sold date       | buyer ID   | price | commissions");
				System.out.println("--------------------------------------------------------------------");
				/*
				   while(rset.next()){
				   }
				 */
				continue;
			} else if (choice == '3') {
				/*TODO: Print Seller Ranking */
				System.out.println("seller ID   | # of items sold | Total Profit (excluding commissions)");
				System.out.println("--------------------------------------------------------------------");
				/*
				   while(rset.next()){
				   }
				 */
				continue;
			} else if (choice == '4') {
				/*TODO: Print Buyer Ranking */
				System.out.println("buyer ID   | # of items purchased | Total Money Spent ");
				System.out.println("------------------------------------------------------");
				/*
				   while(rset.next()){
				   }
				 */
				continue;
			} else if (choice == 'P' || choice == 'p') {
				return false;
			} else {
				System.out.println("Error: Invalid input is entered. Try again.");
				continue;
			}
		} while(true);
	}

	public static boolean BuyItem(){
		Category category = null;
		Condition condition = null;
		char choice;
		int price;
		String keyword, seller, datePosted;
		boolean flag_catg = true, flag_cond = true;
		
		do {

			System.out.println( "----< Select category > : \n" +
					"    1. Electronics\n"+
					"    2. Books\n" + 
					"    3. Home\n" + 
					"    4. Clothing\n" + 
					"    5. Sporting Goods\n" +
					"    6. Other categories\n" +
					"    7. Any category\n" +
					"    P. Go Back to Previous Menu"
					);

			try {
				choice = scanner.next().charAt(0);;
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Error: Invalid input is entered. Try again.");
				return false;
			}

			flag_catg = true;

			switch (choice) {
				case '1':
					category = Category.ELECTRONICS;
					break;
				case '2':
					category = Category.BOOKS;
					break;
				case '3':
					category = Category.HOME;
					break;
				case '4':
					category = Category.CLOTHING;
					break;
				case '5':
					category = Category.SPORTINGGOODS;
					break;
				case '6':
					category = Category.OTHERS;
					break;
				case '7':
					break;
				case 'p':
				case 'P':
					return false;
				default:
					System.out.println("Error: Invalid input is entered. Try again.");
					flag_catg = false;
					continue;
			}
		} while(!flag_catg);

		do {

			System.out.println(
					"----< Select the condition > \n" +
					"   1. New\n" +
					"   2. Like-new\n" +
					"   3. Used (Good)\n" +
					"   4. Used (Acceptable)\n" +
					"   P. Go Back to Previous Menu"
					);
			try {
				choice = scanner.next().charAt(0);;
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Error: Invalid input is entered. Try again.");
				return false;
			}

			flag_cond = true;

			switch (choice) {
				case '1':
					condition = Condition.NEW;
					break;
				case '2':
					condition = Condition.LIKE_NEW;
					break;
				case '3':
					condition = Condition.GOOD;
					break;
				case '4':
					condition = Condition.ACCEPTABLE;
					break;
				case 'p':
				case 'P':
					return false;
				default:
					System.out.println("Error: Invalid input is entered. Try again.");
					flag_cond = false;
					continue;
				}
		} while(!flag_cond);

		try {
			System.out.println("---- Enter keyword to search the description : ");
			keyword = scanner.next();
			scanner.nextLine();

			System.out.println("---- Enter Seller ID to search : ");
			System.out.println(" ** Enter 'any' if you want to see items from any seller. ");
			seller = scanner.next();
			scanner.nextLine();

			System.out.println("---- Enter date posted (YYYY-MM-DD): ");
			System.out.println(" ** This will search items that have been posted after the designated date.");
			datePosted = scanner.next();
			scanner.nextLine();
		} catch (java.util.InputMismatchException e) {
			System.out.println("Error: Invalid input is entered. Try again.");
			return false;
		}




		/* TODO: Query condition: item category */
		/* TODO: Query condition: item condition */
		/* TODO: Query condition: items whose description match the keyword (use LIKE operator) */
		/* TODO: Query condition: items from a particular seller */
		/* TODO: Query condition: posted date of item */

		/* TODO: List all items that match the query condition */
		System.out.printf("%-10d | %-30s | %-15s | %-15s | %-12.2f | %-12.2f | %-15s | %-15s | %s%n",
                    "Item ID", "Item description", "Condition", "Seller", "Buy-It-Now", "Current Bid", "hightest bidder", "Time left", "bid close");
		System.out.println("-------------------------------------------------------------------------------------------------------");


		/* TODO: write a sql query to print out in this format using user input*/
		String query = "SELECT " +
				"I.ItemID AS \"Item ID\", " +
				"I.Description AS \"Item description\", " +
				"I.Condition AS \"Condition\", " +
				"I.SellerID AS \"Seller\", " +
				"I.BuyItNowPrice AS \"Buy-It-Now\", " +
				"B.BidPrice AS \"Current Bid\", " +
				"B.BidderID AS \"Highest Bidder\", " +
				"CASE " +
				"    WHEN I.DatePosted + INTERVAL '7 days' < NOW() THEN 'Auction Ended' " +
				"    ELSE TO_CHAR(I.DatePosted + INTERVAL '7 days' - NOW(), 'DD \"days\" HH24 \"hours\" MI \"minutes\"') " +
				"END AS \"Time Left\", " +
				"TO_CHAR(I.DatePosted + INTERVAL '7 days', 'YYYY-MM-DD HH24:MI') AS \"Bid Close\" " +
				"FROM Items I " +
				"LEFT JOIN ( " +
				"    SELECT ItemID, BidPrice, BidderID " +
				"    FROM Biding B1 " +
				"    WHERE B1.DatePurchase = ( " +
				"        SELECT MAX(DatePurchase) " +
				"        FROM Biding B2 " +
				"        WHERE B1.ItemID = B2.ItemID " +
				"    ) " +
				") B ON I.ItemID = B.ItemID " +
				"WHERE 1=1 "; // This condition ensures that we can always append conditions

		// Append conditions based on user input
		query += "AND I.Category = ? ";
		query += "AND I.Condition = ? ";
		query += "AND I.Description LIKE ? ";
		query += "AND I.SellerID = ? ";
		query += "AND I.DatePosted >= ? ";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			// Set parameter values based on user input
			ps.setString(1, category.toString());
			ps.setString(2, condition.toString());
			ps.setString(3, "%" + keyword + "%");
			ps.setString(4, seller);
			ps.setTimestamp(5, Timestamp.valueOf(datePosted));


			// Execute the query
			ResultSet rs = ps.executeQuery();

			// Process and print the results
			while (rs.next()) {
				int get_itemID = rs.getInt("ItemID");
				String get_description = rs.getString("Description");
				String get_condition = rs.getString("Condition");
				String get_sellerID = rs.getString("SellerID");
				BigDecimal get_buyItNowPrice = rs.getBigDecimal("BuyItNowPrice");
				BigDecimal get_bidPrice = rs.getBigDecimal("BidPrice");
				String get_bidderID = rs.getString("BidderID");
				Timestamp get_datePosted = rs.getTimestamp("DatePosted");

				// Print the data in a tabular format (customize as needed)
				System.out.printf("%-10d | %-30s | %-15s | %-15s | %-12.2f | %-12.2f | %-15s | %s%n",
						get_itemID, get_description, get_condition, get_sellerID, get_buyItNowPrice, get_bidPrice, get_bidderID, get_datePosted);
			}


		} catch (SQLException e) {
			handleSQLException(e);
		}
		
   

		System.out.println("---- Select Item ID to buy or bid: ");

		try {
			choice = scanner.next().charAt(0);;
			scanner.nextLine();
			System.out.println("     Price: ");
			price = scanner.nextInt();
			scanner.nextLine();
		} catch (java.util.InputMismatchException e) {
			System.out.println("Error: Invalid input is entered. Try again.");
			return false;
		}

		/* TODO: Buy-it-now or bid: If the entered price is higher or equal to Buy-It-Now price, the bid ends. */
		/* Even if the bid price is higher than the Buy-It-Now price, the buyer pays the B-I-N price. */

                /* TODO: if you won, print the following */
		System.out.println("Congratulations, the item is yours now.\n"); 
                /* TODO: if you are the current highest bidder, print the following */
		System.out.println("Congratulations, you are the highest bidder.\n"); 
		return true;
	}

	public static void CheckBuyStatus(){
		/* TODO: Check the status of the item the current buyer is bidding on */
		/* Even if you are outbidded or the bid closing date has passed, all the items this user has bidded on must be displayed */

		System.out.println("item ID   | item description   | highest bidder | highest bidding price | your bidding price | bid closing date/time");
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		/*
		   while(rset.next(){
		   System.out.println();
		   }
		 */
	}

	public static void CheckAccount(){
		/* TODO: Check the balance of the current user.  */
		System.out.println("[Sold Items] \n");
		System.out.println("item category  | item ID   | sold date | sold price  | buyer ID | commission  ");
		System.out.println("------------------------------------------------------------------------------");
		/*
		   while(rset.next(){
		   System.out.println();
		   }
		 */
		System.out.println("[Purchased Items] \n");
		System.out.println("item category  | item ID   | purchased date | puchased price  | seller ID ");
		System.out.println("--------------------------------------------------------------------------");
		/*
		   while(rset.next(){
		   System.out.println();
		   }
		 */
	}

	private static void handleSQLException(SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }

	public static void main(String[] args) {
		char choice;
		boolean ret;

		// if(args.length<2){
		// 	System.out.println("Usage: java Auction postgres_id password");
		// 	System.exit(1);
		// }

		try{
            //    	conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+args[0], args[0], args[1]); 
            // Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/bnam", "bnam", "changethis"); 
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/auction", "daisy12", "dnjf1000"); 
		}
		catch(SQLException e){
			System.out.println("SQLException : " + e);	
			System.exit(1);
		}

		do {
			userID = null;
			System.out.println(
					"----< Login menu >\n" + 
					"----(1) Login\n" +
					"----(2) Sign up\n" +
					"----(3) Login as Administrator\n" +
					"----(Q) Quit"
					);

			try {
				choice = scanner.next().charAt(0);;
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Error: Invalid input is entered. Try again.");
				continue;
			}

			try {
				switch ((int) choice) {
					case '1':
						ret = LoginMenu();
						if(!ret) continue;
						break;
					case '2':
						ret = SignupMenu();
						if(!ret) continue;
						break;
					case '3':
						ret = AdminMenu();
						if(!ret) continue;
					case 'q':
					case 'Q':
						System.out.println("Good Bye");
						/* TODO: close the connection and clean up everything here */
						conn.close();
						System.exit(1);
					default:
						System.out.println("Error: Invalid input is entered. Try again.");
				}
			} catch (SQLException e) {
				handleSQLException(e);
			}
		} while (userID==null || userID.equalsIgnoreCase("back"));  

		// logged in as a normal user 
		do {
			System.out.println(
					"---< Main menu > :\n" +
					"----(1) Sell Item\n" +
					"----(2) Status of Your Item Listed on Auction\n" +
					"----(3) Buy Item\n" +
					"----(4) Check Status of your Bid \n" +
					"----(5) Check your Account \n" +
					"----(Q) Quit"
					);

			try {
				choice = scanner.next().charAt(0);;
				scanner.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Error: Invalid input is entered. Try again.");
				continue;
			}

			try{
				switch (choice) {
					case '1':
						ret = SellMenu();
						if(!ret) continue;
						break;
					case '2':
						CheckSellStatus();
						break;
					case '3':
						ret = BuyItem();
						if(!ret) continue;
						break;
					case '4':
						CheckBuyStatus();
						break;
					case '5':
						CheckAccount();
						break;
					case 'q':
					case 'Q':
						System.out.println("Good Bye");
						/* TODO: close the connection and clean up everything here */
						conn.close();
						System.exit(1);
				}
			} catch (SQLException e) {
				handleSQLException(e);
				System.exit(1);
			}
		} while(true);
	} // End of main 

} // End of class

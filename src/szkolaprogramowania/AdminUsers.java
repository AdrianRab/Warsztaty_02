package szkolaprogramowania;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminUsers {

	public static void main(String[] args) {
		try (Connection conn = (new Connect()).getConnection()){	
			Scanner adminChoice = new Scanner(System.in);
			listOfAllUsers(conn);
			System.out.println("Please select one of below options: ");	
			System.out.println("add user");
			System.out.println("edit user");
			System.out.println("delete user");
			System.out.println("quit");
			String adminInput = "";
			while(!(adminInput.equalsIgnoreCase("quit"))) {
				adminInput = adminChoice.nextLine();
				if(adminInput.contains("add")) {
					System.out.println("Please specify user name");
					String newUserName = adminChoice.nextLine();
					System.out.println("Please specify user password");
					String newUserPasswrd = adminChoice.next();
					System.out.println("Please specify user e-mail address");
					String newUserMail = adminChoice.next();
					System.out.println("Please specify user group - provide group_id");
					try{
						Group newUserGroup = Group.loadById(conn, adminChoice.nextInt());
						User user = new User(newUserName, newUserPasswrd, newUserMail, newUserGroup);
						user.saveToDB(conn);}
					catch (NullPointerException npe){
						System.out.println("There is no such group. Record was not added. Try again.");
					}
				}else if(adminInput.contains("edit")) {
					System.out.println("Please specify user name");
					String userName = adminChoice.nextLine();
					System.out.println("Please specify user password");
					String userPasswrd = adminChoice.next();
					System.out.println("Please specify user e-mail address");
					String userMail = adminChoice.next();
					System.out.println("Please specify user group - provide group ID");
					try{
						Group userGroup = Group.loadById(conn, adminChoice.nextInt());
						System.out.println("Please provide user ID");
						int userID = adminChoice.nextInt();
						User user = User.loadUserById(conn, userID);
						user.setUsername(userName);
						user.setEmail(userMail);
						user.setPassword(userPasswrd);
						user.setGroup(userGroup);
						user.saveToDB(conn);}
					catch (NullPointerException npe){
						System.out.println("There is no such group or user. Record was not amended. Try again.");
					}
				}else if(adminInput.contains("delete")) {
					System.out.println("Please provide user ID");
					int userID = adminChoice.nextInt();
					try{
						User user = User.loadUserById(conn, userID);
						user.delete(conn);}
						catch(NullPointerException npe) {
						System.out.println("Nu such user. Please try again.");
					}
				}else if(adminInput.contains("quit")){
					listOfAllUsers(conn);
				}else {
					listOfAllUsers(conn);
					System.out.println("Please select one of below options: ");	
					System.out.println("add user");
					System.out.println("edit user");
					System.out.println("delete user");
					System.out.println("\"quit\" to exit program");
				}
			}

			adminChoice.close();	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public static void listOfAllUsers(Connection conn) throws SQLException {
		User[] allUsers = User.loadAll(conn);
		System.out.println("Total number of users: "+ allUsers.length);
		for(User user : allUsers) {
			System.out.println(user);
		}

	}

}

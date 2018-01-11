package szkolaprogramowania;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminGroups {

	public static void main(String[] args) {
		try (Connection conn = (new Connect()).getConnection()){	
			Scanner adminChoice = new Scanner(System.in);
			listOfAllGroups(conn);
			System.out.println("Please select one of below options: ");	
			System.out.println("add group");
			System.out.println("edit group");
			System.out.println("delete group");
			System.out.println("quit");
			String adminInput = "";
			while(!(adminInput.equalsIgnoreCase("quit"))) {
				adminInput = adminChoice.nextLine();
				if(adminInput.contains("add")) {
					System.out.println("Please specify group name");
					String newGroupName = adminChoice.nextLine();
					Group group = new Group(newGroupName);
					group.saveToDB(conn);
				}else if(adminInput.contains("edit")) {
					System.out.println("Please specify group name");
					String groupName = adminChoice.nextLine();
					System.out.println("Please specify group ID");
					int groupId = adminChoice.nextInt();
					try{ 
						Group group = Group.loadById(conn, groupId);
						group.setName(groupName);
						group.saveToDB(conn);}
					catch (NullPointerException npe){
						System.out.println("There is no such group. Record was not amended. Try again.");
					}
				}else if(adminInput.contains("delete")) {
					System.out.println("Please specify group ID");
					int groupId = adminChoice.nextInt();;
					try{
						Group group = Group.loadById(conn, groupId);
						group.delete(conn);}
					catch(NullPointerException npe) {
						System.out.println("Nu such group. Please try again.");
					}
				}else if(adminInput.contains("quit")){
					listOfAllGroups(conn);
				}else {
					listOfAllGroups(conn);
					System.out.println("Please select one of below options: ");	
					System.out.println("add group");
					System.out.println("edit group");
					System.out.println("delete group");
					System.out.println("\"quit\" to exit program");
				}
			}

			adminChoice.close();	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public static void listOfAllGroups(Connection conn) throws SQLException {
		Group[] allGroups = Group.loadAll(conn);
		System.out.println("Total number of groups: "+ allGroups.length);
		for(Group group : allGroups) {
			System.out.println(group);
		}

	}

}

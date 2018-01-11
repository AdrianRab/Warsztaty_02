package szkolaprogramowania;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminExercises {

	public static void main(String[] args) {
		try (Connection conn = (new Connect()).getConnection()){	
			Scanner adminChoice = new Scanner(System.in);
			listOfAllExerices(conn);
			System.out.println("Please select one of below options: ");	
			System.out.println("add exercise");
			System.out.println("edit exercise");
			System.out.println("delete exercise");
			System.out.println("quit");
			String adminInput = "";
			while(!(adminInput.equalsIgnoreCase("quit"))) {
				adminInput = adminChoice.nextLine();
				if(adminInput.contains("add")) {
					System.out.println("Please specify exercise title");
					String newExerciseTitle = adminChoice.nextLine();
					System.out.println("Please specify exercise description");
					String newExerciseDescription = adminChoice.nextLine();
					Exercise exercise = new Exercise(newExerciseTitle, newExerciseDescription);
					exercise.saveToDB(conn);
				}else if(adminInput.contains("edit")) {
					System.out.println("Please specify exercise title");
					String exerciseTitle = adminChoice.nextLine();
					System.out.println("Please specify exercise description");
					String exerciseDescription = adminChoice.nextLine();
					System.out.println("Please specify excercise ID");
					int exerciseId = adminChoice.nextInt();
					try{ 
						Exercise exercise = Exercise.loadById(conn, exerciseId);
						exercise.setTitle(exerciseTitle);
						exercise.setDescription(exerciseDescription);
						exercise.saveToDB(conn);}
					catch (NullPointerException npe){
						System.out.println("There is no such exercise. Record was not amended. Try again.");
					}
				}else if(adminInput.contains("delete")) {
					System.out.println("Please specify excercise ID");
					int exerciseId = adminChoice.nextInt();
					try{
						Exercise exercise = Exercise.loadById(conn, exerciseId);
						exercise.delete(conn);}
					catch(NullPointerException npe) {
						System.out.println("Nu such exercise. Please try again.");
					}
				}else if(adminInput.contains("quit")){
					listOfAllExerices(conn);
				}else {
					listOfAllExerices(conn);
					System.out.println("Please select one of below options: ");	
					System.out.println("add exercise");
					System.out.println("edit exercise");
					System.out.println("delete exercise");
					System.out.println("\"quit\" to exit program");
				}
			}

			adminChoice.close();	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public static void listOfAllExerices(Connection conn) throws SQLException {
		Exercise[] allExercises = Exercise.loadAll(conn);
		System.out.println("Total number of exercises: "+ allExercises.length);
		for(Exercise exercise : allExercises) {
			System.out.println(exercise);
		}

	}

}

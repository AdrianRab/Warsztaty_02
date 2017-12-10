package szkolaprogramowania;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Solution {
	private int id;
	private Date created;
	private Date updated;
	private String description;
	private Exercise exercise_id;
	private User user_id;
	
	public Solution(Date created, Date updated, String description, Exercise exercise_id, User user_id) {
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.exercise_id = exercise_id;
		this.user_id = user_id;
	}
	
	public Solution() {}
	
	
	public int getId() {
		return id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Exercise getExercise_id() {
		return exercise_id;
	}
	public void setExercise_id(Exercise exercise_id) {
		this.exercise_id = exercise_id;
	}
	public User getUser_id() {
		return user_id;
	}
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}

	public static void createTable(Connection conn) {
		String query = "CREATE TABLE solutions(\n" + 
				"	id INT AUTO_INCREMENT,\n" + 
				"	created DATETIME,\n" + 
				"    updated DATETIME,\n" + 
				"    description TEXT,	\n" + 
				"    exercises_id INT NOT NULL,\n" + 
				"    users_id BIGINT, \n" + 
				"    PRIMARY KEY (id),\n" + 
				"    FOREIGN KEY(exercises_id) REFERENCES exercises(id),\n" + 
				"    FOREIGN KEY(users_id) REFERENCES users(id)\n" + 
				")";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.execute();
		}catch(SQLException e) {
			System.out.println("Nie mozna utworzyć tabeli exercises");
		}
	}
	
}

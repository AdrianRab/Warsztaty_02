package szkolaprogramowania;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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
				"    users_id BIGINT NOT NULL, \n" + 
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


	public void	saveToDB(Connection	conn) throws SQLException	{
		if (this.id == 0)	{
			String sql = "INSERT INTO solutions(created,updated, description, exercises_id, users_id) VALUES (?,?,?,?,?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setDate(1, this.created);
			preparedStatement.setDate(2, this.updated);
			preparedStatement.setString(3,	this.description);
			preparedStatement.setInt(4, this.exercise_id.getId());
			preparedStatement.setInt(5, this.user_id.getId());
			preparedStatement.executeUpdate();
			ResultSet rs  = preparedStatement.getGeneratedKeys();
			if (rs.next()) { 
				this.id	= rs.getInt(1);
			}
		}else {
			String	sql =	"UPDATE	solutions SET created=?, updated=?, description=?, exercises_id= ?, users_id=? WHERE id=?";
			PreparedStatement	preparedStatement1 = conn.prepareStatement(sql);
			preparedStatement1.setDate(1, this.created);
			preparedStatement1.setDate(2, this.updated);
			preparedStatement1.setString(3,	this.description);
			preparedStatement1.setInt(4, this.exercise_id.getId());
			preparedStatement1.setInt(5, this.user_id.getId());
			preparedStatement1.setInt(6, this.id);
			preparedStatement1.executeUpdate();
		}
	}


	static	public	Solution loadById(Connection conn, int id) throws SQLException {
		String	sql	= "SELECT *	FROM solutions	WHERE id=?";
		PreparedStatement preparedStatement =	conn.prepareStatement(sql);
		preparedStatement.setInt(1,	id);
		ResultSet resultSet	= preparedStatement.executeQuery();
		if	(resultSet.next()) {
			Solution loadedSolution= new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created	= resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = Exercise.loadById(conn, resultSet.getInt("id"));
			loadedSolution.user_id = User.loadUserById(conn, resultSet.getInt("id"));
			return	loadedSolution;
		}
		return	null;
	}

	static	public	Solution[] loadAll(Connection conn) throws	SQLException	{
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String	sql	="SELECT* FROM solutions";	
		PreparedStatement preparedStatement =	conn.prepareStatement(sql);
		ResultSet resultSet	= preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution= new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created	= resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = Exercise.loadById(conn, resultSet.getInt("id"));
			loadedSolution.user_id = User.loadUserById(conn, resultSet.getInt("id"));
			solutions.add(loadedSolution);}
		Solution[]	sArray	= new Solution[solutions.size()];
		sArray = solutions.toArray(sArray);
		return	sArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM solutions	WHERE id= ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
	
	static public Solution[] loadAllByExerciseId(Connection conn, int id) throws SQLException{
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solutions WHERE exercises_id = ? ORDER BY created ASC";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Solution loadedSolution= new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created	= resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = Exercise.loadById(conn, resultSet.getInt("id"));
			loadedSolution.user_id = User.loadUserById(conn, resultSet.getInt("id"));
			solutions.add(loadedSolution);
			}
		Solution[] sArray = new Solution[solutions.size()];
		sArray = solutions.toArray(sArray);
		return	sArray;
		}

	public String toString() {
		return this.id+" "+this.created + " " + this.updated + " "+ this.description + " "+ this.exercise_id +  " "+ this.user_id;
	}

}

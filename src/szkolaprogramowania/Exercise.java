package szkolaprogramowania;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
	private int id;
	private String title;
	private String description;


	public Exercise(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public Exercise() {}

	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public static void createTable(Connection conn) {
		String query = "CREATE TABLE exercises(\n" + 
				"	id INT AUTO_INCREMENT,\n" + 
				"	title VARCHAR(255),\n" + 
				"    description TEXT,\n" + 
				"    PRIMARY KEY (id)\n" + 
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
			String sql = "INSERT INTO exercises(title,description ) VALUES (?,?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1,	this.title);
			preparedStatement.setString(2,	this.description);
			preparedStatement.executeUpdate();
			ResultSet rs  = preparedStatement.getGeneratedKeys();
			if (rs.next()) { 
				this.id	= rs.getInt(1);
			}
		}else	{
			String	sql = "UPDATE	exercises SET title=?, description=? WHERE	id=?";
			PreparedStatement	preparedStatement1 = conn.prepareStatement(sql);
			preparedStatement1.setString(1, this.title);
			preparedStatement1.setString(2, this.description);
			preparedStatement1.setInt(3, this.id);
			preparedStatement1.executeUpdate();
		}
	}


	static	public	Exercise loadById(Connection conn, int id) throws SQLException {
		String	sql	= "SELECT *	FROM exercises	WHERE id=?";
		PreparedStatement preparedStatement =	conn.prepareStatement(sql);
		preparedStatement.setInt(1,	id);
		ResultSet resultSet	= preparedStatement.executeQuery();
		if	(resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title	= resultSet.getString("title");
			loadedExercise.description = resultSet.getString("description");
			return	loadedExercise;
		}
		return	null;
	}

	static	public	Exercise[] loadAll(Connection conn) throws	SQLException	{
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		String	sql	="SELECT* FROM exercises";	
		PreparedStatement preparedStatement =	conn.prepareStatement(sql);
		ResultSet resultSet	= preparedStatement.executeQuery();
		while (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title	= resultSet.getString("title");
			loadedExercise.description	= resultSet.getString("description");
			exercises.add(loadedExercise);}
		Exercise[]	eArray	= new Exercise[exercises.size()];
		eArray = exercises.toArray(eArray);
		return	eArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM exercises	WHERE id= ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

	static public Exercise loadAllByUserId(Connection conn, int id) throws SQLException{ // dopisac wysietlanie rozwiazan
		String	sql	= "SELECT * FROM exercises JOIN solutions ON exercises.id = solutions.exercises_id WHERE solutions.users_id = ?";
		PreparedStatement preparedStatement =	conn.prepareStatement(sql);
		preparedStatement.setInt(1,	id);
		ResultSet resultSet	= preparedStatement.executeQuery();
		if	(resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title	= resultSet.getString("title");
			loadedExercise.description = resultSet.getString("description");
			return	loadedExercise;
		}
		return	null;
	}
	
	public String toString() {
		return this.id+" "+this.title + " " + this.description;
	}

}

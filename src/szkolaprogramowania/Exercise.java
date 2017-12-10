package szkolaprogramowania;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1,	this.title);
			preparedStatement.setString(2,	this.description);
			preparedStatement.executeUpdate();
			ResultSet rs  = preparedStatement.getGeneratedKeys();
			if (rs.next()) { 
				this.id	= rs.getInt(1);
			}else	{
				String	sql1 =	"UPDATE	groups SET title=?, description=? WHERE	id=?";
				PreparedStatement	preparedStatement1;
				preparedStatement1	= conn.prepareStatement(sql1);
				preparedStatement1.setString(1, this.title);
				preparedStatement1.setString(2, this.description);
				preparedStatement1.setInt(3, this.id);
				preparedStatement1.executeUpdate();
			}
		}
	}
	
}

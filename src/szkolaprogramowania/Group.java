package szkolaprogramowania;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Group {

	private int id;
	private String name;

	public Group(String name) {
		this.name = name;
	}

	public Group() {}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void createTable(Connection conn) {
		String query = "CREATE TABLE groups(\n" + 
				"	id INT AUTO_INCREMENT,\n" + 
				"	name VARCHAR(255),\n" + 
				"    PRIMARY KEY (id)\n" + 
				")";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.execute();
		}catch(SQLException e) {
			System.out.println("Nie mozna utworzyć tabeli Groups");
			e.printStackTrace();
		}
	}

	public void	saveToDB(Connection	conn) throws SQLException	{
		if (this.id == 0)	{
			String sql = "INSERT INTO groups(name) VALUES (?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1,	this.name);
			preparedStatement.executeUpdate();
			ResultSet rs  = preparedStatement.getGeneratedKeys();
			if (rs.next()) { 
				this.id	= rs.getInt(1);
			}
		}
	}

}

package szkolaprogramowania;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		}else	{
			String	sql =	"UPDATE	groups SET name=? WHERE	id=?";
			PreparedStatement	preparedStatement1;
			preparedStatement1	= conn.prepareStatement(sql);
			preparedStatement1.setString(1, this.name); //dodalem sam
			preparedStatement1.setInt(2, this.id);
			preparedStatement1.executeUpdate();
		}
	}


	static	public	Group loadById(Connection conn, int id) throws SQLException {
		String	sql	= "SELECT *	FROM groups	WHERE id=?";
		PreparedStatement preparedStatement;
		preparedStatement =	conn.prepareStatement(sql);
		preparedStatement.setInt(1,	id);
		ResultSet resultSet	= preparedStatement.executeQuery();
		if	(resultSet.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name	= resultSet.getString("name");
			return	loadedGroup;
		}
		return	null;}

	static	public	Group[] loadAll(Connection conn) throws	SQLException	{
		ArrayList<Group> groups = new ArrayList<Group>();
		String	sql	="SELECT* FROM groups";	
		PreparedStatement preparedStatement;
		preparedStatement =	conn.prepareStatement(sql);
		ResultSet resultSet	= preparedStatement.executeQuery();
		while (resultSet.next()) {
			Group loadedGoup = new Group();
			loadedGoup.id = resultSet.getInt("id");
			loadedGoup.name	= resultSet.getString("name");
			groups.add(loadedGoup);}
		Group[]	gArray	= new Group[groups.size()];
		gArray = groups.toArray(gArray);
		return	gArray;}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE	FROM	groups	WHERE	id=	?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

	public String toString() {
		return this.id + " " + this.name;
	}
}

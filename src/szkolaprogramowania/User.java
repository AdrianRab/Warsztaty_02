package szkolaprogramowania;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	private Group group;
//	public static int nextID=1;

	public User(String username, String password, String email, Group group) {
		this.username = username;
		this.email = email;
		this.group = group;
		setPassword(password);
//	    this.id = nextID;
//	    this.nextID++;
	}

	public User() {}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}

	public static void createTable(Connection conn) {
		String query = "CREATE TABLE users(\n" + 
				"	id BIGINT AUTO_INCREMENT,\n" + 
				"	username VARCHAR(255),\n" + 
				"    email VARCHAR(255) UNIQUE,\n" + 
				"    password VARCHAR(255),	\n" + 
				"    group_id INT NOT NULL,\n" + 
				"    PRIMARY KEY (id),\n" + 
				"    FOREIGN KEY(group_id) REFERENCES groups(id)\n" + 
				")";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.execute();
		}catch(SQLException e) {
			System.out.println("Nie mozna utworzyć tabeli users");
		}
	}


	public void	saveToDB(Connection	conn) throws SQLException	{
		if (this.id == 0)	{
			String sql = "INSERT INTO users(username, email, password, group_id) VALUES (?, ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.group.getId());
			preparedStatement.executeUpdate();
			ResultSet rs  = preparedStatement.getGeneratedKeys();
			if (rs.next()) { 
				this.id	= rs.getInt(1);
			}else	{
				String	sql1 =	"UPDATE	users SET username=?, email=?, password=?, group_id=? WHERE	id	=?";
				PreparedStatement	preparedStatement1;
				preparedStatement1	= conn.prepareStatement(sql1);
				preparedStatement1.setString(1,this.username);
				preparedStatement1.setString(2,this.email);
				preparedStatement1.setString(3,this.password);
				preparedStatement.setInt(4, this.group.getId());
				preparedStatement1.setInt(5, this.id);
				preparedStatement1.executeUpdate();
			}
		}
	}

	static	public	User loadUserById(Connection conn, int id) throws SQLException {
		String	sql	= "SELECT *	FROM users	WHERE id=?";
		PreparedStatement preparedStatement;
		preparedStatement =	conn.prepareStatement(sql);
		preparedStatement.setInt(1,	id);
		ResultSet resultSet	= preparedStatement.executeQuery();
		if	(resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username	= resultSet.getString("username");
			loadedUser.email = resultSet.getString("email");
			loadedUser.password = resultSet.getString("password");
			loadedUser.group = Group.loadById(conn, resultSet.getInt("id"));
			return	loadedUser;
		}
		return	null;}

	static	public	User[] loadAll(Connection conn) throws	SQLException	{
		ArrayList<User>	users = new	ArrayList<User>();
		String	sql	="SELECT* FROM users";	
		PreparedStatement preparedStatement;
		preparedStatement =	conn.prepareStatement(sql);
		ResultSet resultSet	= preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser	= new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username	= resultSet.getString("username");
			loadedUser.password	= resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			loadedUser.group = Group.loadById(conn, resultSet.getInt("id"));
			users.add(loadedUser);}
		User[]	uArray	= new User[users.size()];
		uArray = users.toArray(uArray);
		return	uArray;}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE	FROM	users	WHERE	id=	?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

	public String toString() {
		return this.id+" "+this.username + " " + this.email + " "+ this.group.getName();
	}

}

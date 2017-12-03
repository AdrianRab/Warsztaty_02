package szkolaprogramowania;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		try (Connection conn = (new Connect()).getConnection()){
			//Group.createTable(conn);
			Group group  = new Group();
			group.setName("Grupa_Java");
			group.saveToDB(conn);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}

}

package szkolaprogramowania;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Main {

	public static void main(String[] args) {
		try (Connection conn = (new Connect()).getConnection()){
//			Group.createTable(conn);
//			User.createTable(conn);
//			Exercise.createTable(conn);
//			Solution.createTable(conn);
//			Group group  = new Group();
//			group.setName("Grupa_Java");
//			group.saveToDB(conn);
//			System.out.println(group.loadById(conn, 1));
			
//			Exercise zadanie1 = new Exercise();
//			
//			zadanie1.setTitle("Podzial napisow");
//			zadanie1.setDescription("W pliku `Main1.java` napisz program, który:\n" + 
//					"\n" + 
//					"1. Dla znajdującej się w pliku zmiennej `str` podzieli napis na wyrazy. \n" + 
//					"2. Wyświetli na konsoli każdy w oddzielnej lini.");
//			
//
//			
//			zadanie1.setDescription("W pliku `Main1.java` napisz program, ktory:\n" + 
//					"\n" + 
//					"1. Dla znajdujacej sie w pliku zmiennej `str` podzieli napis na wyrazy. \n" + 
//					"2. Wyswietli na konsoli kazdy w oddzielnej lini.");
//			zadanie1.saveToDB(conn);
			
			Group group = Group.loadById(conn, 1); //wywołujemy metodę statyczną bezposrednio na klasie
//			User user = new User("Adrian", "haslo", "adi@t.pl",  group);
			
			User user2 = new User("Marcin",  "password","marc@t.pl", group);
			
			User user3 = new User("Max", "tinaTurner", "madmax@t.pl", group);
			User user4 = new User("Aecjusz", "PolaKatalaunijskie451", "ostatni.rzymiani@rome.pl", group);
			User cezar = new User("Gaius Julis Ceaser", "Alezja52", "jule@cezar", group);
			
			Group grupa2 = new Group("Test_group");
			//grupa2.saveToDB(conn);
			grupa2.setName("Grupa_Python");
//			grupa2.saveToDB(conn);
			System.out.println(User.loadUserById(conn, 2)); //wywołujemy metodę statyczną bezposrednio na klasie
			System.out.println(User.loadAll(conn));
			System.out.println(Exercise.loadById(conn, 1));

			
			System.out.println(grupa2.getId());
			System.out.println(grupa2.getName());
			System.out.println((grupa2.loadById(conn, grupa2.getId())));
//			
//			
////			System.out.println(user.getPassword());
			
			Date created = new Date(0);
			Date updated = new Date(0);
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			try {
				 created = new Date(df.parse("12-10-2017").getTime());
				 updated = new Date(df.parse("12-11-2017").getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			User user = User.loadUserById(conn, 1);
			Exercise zadanie1 =  Exercise.loadById(conn, 1);
			Solution sol = new Solution();
			sol.setCreated(created);
			sol.setUpdated(updated);
			sol.setDescription("Test description");
			sol.setUser_id(user);
			sol.setExercise_id(zadanie1);
			
			sol.saveToDB(conn);
			
			System.out.println(sol.loadById(conn, 1));
//			user.saveToDB(conn);
//			user2.saveToDB(conn);
//			user3.saveToDB(conn);
//			user4.saveToDB(conn);
//			cezar.saveToDB(conn);

//			user4.setEmail("ostatni.rzymiani@rome.pl");
//			user4.setPassword("PolaKatalaunijskie451");
//			user4.saveToDB(conn);
			
			System.out.println(user4.getEmail());
			System.out.println(user4.getPassword());

//			User user3 = User.loadUserById(conn, 1);
//			System.out.println(user.getUsername());
//			
//			user.loadUserById(conn, 1);
//			user.setEmail("adi@poczta.pl");
//			user.saveToDB(conn);
//			
			System.out.println(user.getId() + " "+user.getUsername()+ " "+ user.getEmail() + " "+ user.getGroup());
			System.out.println(user2.getId() + " "+user2.getUsername()+ " "+ user2.getEmail() + " "+ user2.getGroup());
			System.out.println(user3.getId() + " "+user3.getUsername()+ " "+ user3.getEmail() + " "+ user3.getGroup());
			System.out.println(user4.getId() + " "+user4.getUsername()+ " "+ user4.getEmail() + " "+ user4.getGroup());
			System.out.println(cezar.getId()+" "+cezar.getUsername()+ " "+ cezar.getEmail() + " "+ cezar.getGroup());
			
			System.out.println(cezar.loadUserById(conn, 1));
			System.out.println(user.loadUserById(conn, 2));
			System.out.println(user2.loadUserById(conn, 3));
			//System.out.println(user3.loadUserById(conn, 4));
			//System.out.println(user4.loadUserById(conn, 5));
			System.out.println((group.loadById(conn, 2)));
//			User[]	users = User.loadAll(conn);//wydruk wszystkich usersow
//			System.out.println(users.toString());
//			user.delete(conn);
//			user2.delete(conn);
//			user3.delete(conn);
//			user4.delete(conn);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}

}

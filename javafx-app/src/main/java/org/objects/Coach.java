import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class Coach{

	private String name;

	public Coach (String name){
		this.name = name;
	}

	public String getName() { return name; }
	public void setName(String name) {
		this.name = name; 
		saveToDatabase();
	}

	public boolean saveToDatabase(){
		try (Connection con = DatabaseConnector.connect()) {
			CallableStatement call = con.prepareCall("{CALL update_coach(?, ?) }");

			call.setString(1, this.name);
			call.setInt(2, 11);
			call.execute();
			System.out.println("Coach updated successfully");
			return true;
		}
		catch (SQLException e){
			System.out.println("Something went wrong saving the coach");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}

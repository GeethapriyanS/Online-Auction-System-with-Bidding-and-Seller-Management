import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    Connection con=null;
    String url="jdbc:mysql://localhost:3306/Online_Auction_System";
    String username="root";
    String password="W7301@jqir#";
    
  public Connection getConnection() {
    if(con==null) {
    	try{
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	con= DriverManager.getConnection(url,username,password);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
      }
    return con;
    }
}

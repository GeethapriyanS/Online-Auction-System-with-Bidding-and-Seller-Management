import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    protected int userId;
    protected String name;
    protected String email;
    protected String role;
    protected String password;
    public User() {};
    public User(int userId, String name, String email, String role, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public static User checkUserlogin(String username,String password) {
		   String query="select * from Users where name = ? and password = ?";
		   new DatabaseConnection();
		try(Connection con = new DatabaseConnection().getConnection();
					  PreparedStatement pst=con.prepareStatement(query)){
				  pst.setString(1, username);
				  pst.setString(2, password );
				  ResultSet res=pst.executeQuery();
				  if(res.next()) {
					 return new User(res.getInt("user_id"),res.getString("name"),res.getString("email"),res.getString("role"),res.getString("password"));
				  }else {
					  return null;
				  }
			  }
			  catch(Exception e){
				  e.printStackTrace();
			  }
			  return null;
	   }
    
	public static int AddBuyer(String newusername, String email2, String newpassword, String string, String string2) {
		String query="INSERT INTO Users (name, email, password, role, status) VALUES (?,?,?,?,?)";
		new DatabaseConnection();
		try(Connection con = new DatabaseConnection().getConnection();
					  PreparedStatement pst=con.prepareStatement(query)){
			pst.setString(1, newusername);
			pst.setString(2, email2);
			pst.setString(3, newpassword);
			pst.setString(4,string);
			pst.setString(5,string2);
			return pst.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void notification(int id3) {
        String query = "SELECT * FROM Notifications where user_id=?";
        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1,id3);
            ResultSet rs = pst.executeQuery();

            System.out.println("**** Notification Details ****");
            System.out.printf("%-20s %-15s %-50s %-10s %-25s\n",
                    "Notification_ID", "User_ID", "Message", "Status", "Sent_At");

            while (rs.next()) {
                System.out.printf("%-20d %-15d %-50s %-10s %-25s\n",
                        rs.getInt("notification_id"),
                        rs.getInt("user_id"),
                        rs.getString("message"),
                        rs.getString("status"),
                        rs.getTimestamp("sent_at"));
            }
            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

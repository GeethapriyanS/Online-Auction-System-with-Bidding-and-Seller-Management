import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Seller extends User {
	Seller(){};
    public float CurrentHighestBid;
    public String status;
    public Seller(int userId, String name, String email, String password) {
        super(userId, name, email, "seller", password);
    }

    public void listItem(Item item) {
        System.out.println("Item listed: " + item.getName());
    }

	public static int AddSeller(String newusername, String email, String status, String newpassword) {
		int applicationId = 0;
        try (Connection connection = new DatabaseConnection().getConnection();
             PreparedStatement appStmt = connection.prepareStatement(
                     "INSERT INTO SellerApplications (username, password, status,email) VALUES (?, ?, ?,?)", 
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
			appStmt.setString(1, newusername);
            appStmt.setString(2, newpassword);
            appStmt.setString(3, status);
            appStmt.setString(4, email);
            
            appStmt.executeUpdate();
            ResultSet rs = appStmt.getGeneratedKeys();
            if (rs.next()) {
                applicationId = rs.getInt(1);
                System.out.println("Application submitted with ID: " + applicationId);
                return 1; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return 0;
	}
}

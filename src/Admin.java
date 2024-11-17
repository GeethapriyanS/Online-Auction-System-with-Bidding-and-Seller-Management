import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Admin extends User {
	
    public Admin(){};
	
    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, "admin", password);
    }

    public static void reportGeneration(int id3,String type,String data) {
    	String insertQuery = "INSERT INTO Reports (admin_id, report_type, data) VALUES (?, ?, ?)";
        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(insertQuery)) {
            pst.setInt(1, id3);
            pst.setString(2, type);
            pst.setString(3, data);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Report inserted successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void approveSellers() {
    	String query = "SELECT * FROM SellerApplications";
        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
             
            ResultSet rs = pst.executeQuery();

            System.out.println("**** Seller Applications Details ****");
            System.out.printf("%-20s %-20s %-25s %-20s %-15s %-30s\n",
                    "Application_ID", "Username", "Submitted_At", "Password", "Status", "Email");

            while (rs.next()) {
                System.out.printf("%-20d %-20s %-25s %-20s %-15s %-30s\n",
                        rs.getInt("application_id"),
                        rs.getString("username"),
                        rs.getTimestamp("submitted_at"),
                        rs.getString("password"),
                        rs.getString("status"),
                        rs.getString("email"));
            }
            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        System.out.println("\nEnetr Id to accept Seller Application :");
        int id2=sc.nextInt();
        
        String query1 = "SELECT * FROM SellerApplications where application_id=?";
        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(query1)) {
             pst.setInt(1, id2);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
            	User.AddBuyer(rs.getString("username"),rs.getString("email"),rs.getString("password"),"seller",rs.getString("status"));       
            }
            System.out.print("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }

    
	public static boolean addItems(int sellerId, String name, String description, float startingPrice,float currentPrice, String auctionEndTime) {
		String insertQuery = "INSERT INTO Items (seller_id, name, description, starting_price, current_price, auction_end_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(insertQuery)) {
            pst.setInt(1, sellerId);
            pst.setString(2, name);
            pst.setString(3, description);
            pst.setFloat(4, startingPrice);
            pst.setFloat(5, currentPrice);
            pst.setString(6, auctionEndTime);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}

public static boolean removeItem(int sellerId1) {
		String deleteQuery = "DELETE FROM Items WHERE item_id = ?";

        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(deleteQuery)) {

            pst.setInt(1, sellerId1);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } 
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
}

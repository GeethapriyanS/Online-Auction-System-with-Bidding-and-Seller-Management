import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Bid {
    private int bidId;
    private int itemId;
    private int userId;
    private float bidAmount;
    private Timestamp bidTime;
    private boolean isAutoBid;
    public Bid(int bidId, int itemId, int userId, float bidAmount, Timestamp bidTime, boolean isAutoBid) {
        this.bidId = bidId;
        this.itemId = itemId;
        this.userId = userId;
        this.bidAmount = bidAmount;
        this.bidTime = bidTime;
        this.isAutoBid = isAutoBid;
    }

    public Bid(int itemId2, int userId2, float bidAmount2, boolean isAutoBid2) {
        this.itemId = itemId2;
        this.userId = userId2;
        this.bidAmount = bidAmount2;
        this.isAutoBid = isAutoBid2;
	}

	public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(float bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Timestamp getBidTime() {
        return bidTime;
    }

    public void setBidTime(Timestamp bidTime) {
        this.bidTime = bidTime;
    }

    public boolean isAutoBid() {
        return isAutoBid;
    }

    public void setAutoBid(boolean autoBid) {
        isAutoBid = autoBid;
    }

    public void displayBidInfo() {
        System.out.println("Item ID: " + itemId);
        System.out.println("User ID: " + userId);
        System.out.println("Bid Amount: $" + bidAmount);
        System.out.println("Is Auto Bid: " + isAutoBid);
    }

    public static Bid placeBid(int itemId, int userId, float bidAmount, boolean isAutoBid) {
    	 String query = "INSERT INTO Bids (item_id, user_id, bid_amount, is_auto_bid) VALUES (?, ?, ?, ?)";
         try (Connection con = new DatabaseConnection().getConnection();
              PreparedStatement pst = con.prepareStatement(query)) {

             pst.setInt(1, itemId);
             pst.setInt(2, userId);
             pst.setFloat(3, bidAmount); 
             pst.setBoolean(4, isAutoBid); 
             int rowsAffected = pst.executeUpdate();

             if (rowsAffected > 0) {
                 System.out.println("Bid inserted successfully!");
             } else {
                 System.out.println("Failed to insert bid.");
             }

         } catch (SQLException e) {
             e.printStackTrace();
         }
        return new Bid(itemId, userId, bidAmount, isAutoBid);
    }

	public static void history() {
    String query = "SELECT * FROM Bids";
        
        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
             
            ResultSet rs = pst.executeQuery();

            System.out.println("**** Bid details ****");
            System.out.printf("%-15s %-15s %-15s %-15s %-19s %-15s\n",
                    "Bid_id", "Item_id", "User_id", "Bid_Amount", "Bid_Time", "Auto_Bid");

            while (rs.next()) {
                System.out.printf("%-15d %-15d %-15d %-15.2f %-19s %-15s\n",
                        rs.getInt("bid_id"),
                        rs.getInt("item_id"),
                        rs.getInt("user_id"),
                        rs.getFloat("bid_amount"),
                        rs.getTimestamp("bid_time"),
                        rs.getBoolean("is_auto_bid") ? "YES" : "NO");
            }
            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
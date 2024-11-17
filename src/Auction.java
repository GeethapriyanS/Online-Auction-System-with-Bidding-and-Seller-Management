import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auction {
	
    public static void closeAuctionitem(int id1) {
    	try (Connection con = new DatabaseConnection().getConnection();
                PreparedStatement pst = con.prepareStatement("UPDATE Items SET status='closed' WHERE item_id=?")) {
                pst.setInt(1, id1);
                if(pst.executeUpdate()==1) {
                	System.out.println("Auction close for Item id :"+id1);
                }
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }

    public static void AuctionHistory() {
         String query = "SELECT * FROM AuctionHistory";
        
        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
             
            ResultSet rs = pst.executeQuery();

            System.out.println("**** Auction History Details ****");
            System.out.printf("%-20s %-15s %-15s %-15s %-25s\n",
                    "Auction_History_ID", "Item_ID", "Winner_ID", "Final_Price", "Closed_At");

            while (rs.next()) {
                System.out.printf("%-20d %-15d %-15d %-15.2f %-25s\n",
                        rs.getInt("auction_history_id"),
                        rs.getInt("item_id"),
                        rs.getInt("winner_id"),
                        rs.getFloat("final_price"),
                        rs.getTimestamp("closed_at"));
            }
            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

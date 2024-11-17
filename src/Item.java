import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Item {
    private int itemId;
    private int sellerId;
    private String name;
    private String description;
    private double startingPrice;
    private double currentPrice;
    private Date auctionEndTime;


    public Item(int itemId, int sellerId, String name, String description, double startingPrice, Date auctionEndTime) {
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.auctionEndTime = auctionEndTime;
    }

    public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(double startingPrice) {
		this.startingPrice = startingPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getAuctionEndTime() {
		return auctionEndTime;
	}

	public void setAuctionEndTime(Date auctionEndTime) {
		this.auctionEndTime = auctionEndTime;
	}
	
    public String getName() {
        return name;
    }

	public static void displayItems() {
		String query = "SELECT * FROM Items";
        try (Connection con = new DatabaseConnection().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();

            System.out.println("**** Item details ****");
            System.out.printf("%-15s %-15s %-20s %-15s %-15s %-19s %-15s\n",
                    "Item_id", "Seller_id", "Name", "Starting_Price", "Current_Price", "Auction_End_Time", "Status");

            while (rs.next()) {
                System.out.printf("%-15d %-15d %-20s %-15.2f %-15.2f %-19s %-15s\n",
                        rs.getInt("item_id"),
                        rs.getInt("seller_id"),
                        rs.getString("name"),
                        rs.getFloat("starting_price"),
                        rs.getFloat("current_price"),
                        rs.getTimestamp("auction_end_time"),
                        rs.getString("status"));
            }
            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}

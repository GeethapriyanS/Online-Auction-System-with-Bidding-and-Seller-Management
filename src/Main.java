import java.util.*;
public class Main {
    public static void main(String[] args) {
        User objuser = null;
        User objadmin = null;
        User objseller = null;
        boolean loginstatus = true;
        ArrayList<Bid> bid_details = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Online Auction System with Bidding and Seller Management !!");

        while (loginstatus) {
            System.out.println("\n1. Login");
            System.out.println("2. Create Account or Seller Registration");
            System.out.println("3. List Items");
            System.out.println("4. Place a Bid (for User only)");
            System.out.println("5. View Bid History (for Seller and Admin only)");
            System.out.println("6. Manage Auction (Admin)");
            System.out.println("7. Notifications");
            System.out.println("8. Logout");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
           
            if (objuser == null && objseller == null && objadmin == null && choice > 2 && choice < 6) {
                System.out.println("Please log in first.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (objuser == null && objseller == null && objadmin == null) {
                        System.out.print("Login as (Admin/User/Seller): ");
                        String userType = sc.next();

                        if (userType.equalsIgnoreCase("User")) {
                            System.out.print("Enter Username: ");
                            String username = sc.next();
                            System.out.print("Enter Password: ");
                            String password = sc.next();
                            objuser = User.checkUserlogin(username, password);

                            if (objuser != null) {
                                System.out.println("User logged in successfully.\n" + "Hi " + objuser.name + " !!");
                            } else {
                                System.out.println("Invalid credentials.");
                            }
                        } else if (userType.equalsIgnoreCase("Admin")) {
                            System.out.print("Enter Admin Name: ");
                            String adminName = sc.next();
                            System.out.print("Enter Password: ");
                            String adminPassword = sc.next();
                            objadmin = User.checkUserlogin(adminName, adminPassword);

                            if (objadmin != null) {
                                System.out.println("Admin logged in successfully.");
                            } else {
                                System.out.println("Invalid credentials.");
                            }
                        } else if (userType.equalsIgnoreCase("Seller")) {
                            System.out.print("Enter Seller Name: ");
                            String sellerName = sc.next();
                            System.out.print("Enter Password: ");
                            String sellerPassword = sc.next();
                            objseller = User.checkUserlogin(sellerName, sellerPassword);

                            if (objseller != null) {
                                System.out.println("Seller logged in successfully.");
                            } else {
                                System.out.println("Invalid credentials.");
                            }
                        } else {
                            System.out.println("Invalid option. Please try again.");
                        }
                    } else {
                        System.out.println("Please logout from existing account and then login.");
                    }
                    break;

                case 2:
                    if (objuser == null && objadmin == null && objseller == null) {
                        System.out.println("\n1. Register Application for Seller Role");
                        System.out.println("2. Create Buyer Account");
                        System.out.print("Enter your choice: ");
                        int option = sc.nextInt();

                        if (option == 1) {
                            System.out.print("Enter Username: ");
                            String newusername = sc.next();
                            System.out.print("Enter Password: ");
                            String newpassword = sc.next();
                            System.out.print("Enter Email: ");
                            String email = sc.next();
                            int status = Seller.AddSeller(newusername, email, "pending", newpassword);

                            if (status == 1) {
                                System.out.println("Seller Application Submitted Successfully.");
                            } else {
                                System.out.println("Invalid credentials.");
                            }
                        } else if (option == 2) {
                            System.out.print("Enter Username: ");
                            String newusername = sc.next();
                            System.out.print("Enter Password: ");
                            String newpassword = sc.next();
                            System.out.print("Enter Email: ");
                            String email = sc.next();
                            int status1 = User.AddBuyer(newusername, email, newpassword, "buyer", "approved");

                            if (status1 == 1) {
                                System.out.println("User Account Created Successfully.");
                            } else {
                                System.out.println("Invalid credentials.");
                            }
                        } else {
                            System.out.println("Invalid option. Please try again.");
                        }
                    } else {
                        System.out.println("Logout from existing account and then create new account!\n");
                    }
                    break;

                case 3:
                    Item.displayItems();
                    break;

                case 4:
                    if (objuser != null && objadmin == null && objseller == null) {
                        System.out.print("Enter ItemId: ");
                        int id1 = sc.nextInt();
                        System.out.print("Enter Bid Amount: ");
                        float amount = sc.nextFloat();
                        bid_details.add(Bid.placeBid(id1, objuser.userId, amount, false));
                    } else {
                        System.out.println("Please login as User and then Bid.");
                    }
                    break;

                case 5:
                    if (objseller != null || objadmin != null && objuser == null) {
                        Bid.history();
                    } else {
                        System.out.println("Access denied. Only Admin or Seller can view bid history.");
                    }
                    break;

                case 6:
                    if (objadmin != null && objuser == null && objseller == null) {
                        System.out.println("Admin Options:");
                        System.out.println("1. Add Item");
                        System.out.println("2. Remove Item");
                        System.out.println("3. Close Auction");
                        System.out.println("4. Approve Sellers");
                        System.out.println("5. Report Generation");
                        System.out.println("6. Auction History");
                        System.out.println("7. Logout");
                        System.out.print("Enter your choice: ");
                        int adminChoice = sc.nextInt();

                        switch (adminChoice) {
                            case 1:
                                System.out.print("Enter Seller ID: ");
                                int sellerId = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Enter Item Name: ");
                                String name = sc.nextLine();
                                System.out.print("Enter Description: ");
                                String description = sc.nextLine();
                                System.out.print("Enter Starting Price: ");
                                float startingPrice = sc.nextFloat();
                                System.out.print("Enter Current Price: ");
                                float currentPrice = sc.nextFloat();
                                System.out.print("Enter Auction End Time (YYYY-MM-DD HH:MM:SS): ");
                                sc.nextLine();
                                String auctionEndTime = sc.nextLine();

                                if (Admin.addItems(sellerId, name, description, startingPrice, currentPrice, auctionEndTime)) {
                                    System.out.println("Item Added Successfully.");
                                } else {
                                    System.out.println("Item not added.");
                                }
                                break;

                            case 2:
                                System.out.print("Enter ItemID to remove: ");
                                int itemId = sc.nextInt();
                                if (Admin.removeItem(itemId)) {
                                    System.out.println("Item removed Successfully.");
                                } else {
                                    System.out.println("Item removal failed.");
                                }
                                break;

                            case 3:
                                System.out.print("Enter ItemId to Close Auction: ");
                                int id2 = sc.nextInt();
                                Auction.closeAuctionitem(id2);
                                break;

                            case 4:
                                Admin.approveSellers();
                                break;

                            case 5:
                                System.out.print("Enter the Report Type: ");
                                sc.nextLine();
                                String reportType = sc.nextLine();
                                System.out.print("Enter the Data: ");
                                String data = sc.nextLine();
                                Admin.reportGeneration(objadmin.userId, reportType, data);
                                break;

                            case 6:
                                Auction.AuctionHistory();
                                break;

                            case 7:
                                objadmin = null;
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    } else {
                        System.out.println("Please log in as an Admin to manage auctions.");
                    }
                    break;

                case 7:
                    if (objuser != null) {
                        User.notification(objuser.userId);
                    } else if (objseller != null) {
                        User.notification(objseller.userId);
                    } else {
                        System.out.println("Only Buyers and Sellers can view notifications.");
                    }
                    break;

                case 8:
                    System.out.println("Logging out...");
                    objuser = null;
                    objadmin = null;
                    objseller = null;
                    break;

                case 9:
                    loginstatus = false;
                    System.out.println("** Thank you for using the system **");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
}

package gym.membership.management.system;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminPanel {
    // OOP: Encapsulation (private fields), Association (uses Admin and MembershipManager)
    private Admin admin;
    private MembershipManager manager;

    public AdminPanel(Admin admin, MembershipManager manager) {
        this.admin = admin;
        this.manager = manager;
    }

    public void launch(Scanner sc) {
        while (true) {
            UIUtils.clearScreen();
            System.out.println("\nADMIN PANEL - Welcome, " + admin.getUsername()); // Use getUsername()
            System.out.println("1. View All Members");
            System.out.println("2. Calculate Total Revenue");
            System.out.println("3. Search Members by Expiry Date");
            System.out.println("4. Return to Main Menu");
            System.out.print("Choose: ");
            String input = sc.nextLine();

            switch (input) {
                case "1" -> {
                    UIUtils.clearScreen();
                    manager.listMembers();
                    UIUtils.pause();
                }
                case "2" -> {
                    UIUtils.clearScreen();
                    manager.calculateTotalSales(); // Use calculateTotalSales()
                    UIUtils.pause();
                }
                case "3" -> {
                    UIUtils.clearScreen();
                    System.out.println("=== Search Members by Expiry Date ===");
                    System.out.print("Enter expiry date (yyyy-MM-dd): ");
                    String dateStr = sc.nextLine().trim();
                    try {
                        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        manager.searchByExpiryDate(date);
                    } catch (Exception e) {
                        System.out.println("Invalid date format.");
                    }
                    UIUtils.pause();
                }
                case "4" -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid input.");
                    UIUtils.pause();
                }
            }
        }
    }
}

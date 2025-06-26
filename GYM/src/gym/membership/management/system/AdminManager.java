package gym.membership.management.system;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class AdminManager {
    // OOP: Encapsulation (private list), Aggregation (has Admins)
    private ArrayList<Admin> admins = new ArrayList<>();

    public AdminManager() {
        // Default admin account
        admins.add(new Admin("admin", "admin123"));
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public boolean login(Scanner sc) {
        System.out.print(UIUtils.INDENT + "Username: ");
        String username = sc.nextLine().trim();
        System.out.print(UIUtils.INDENT + "Password: ");
        String password = sc.nextLine().trim();

        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.checkPassword(password)) {
                System.out.println(UIUtils.INDENT + "Login successful!");
                return true;
            }
        }
        System.out.println(UIUtils.INDENT + "Invalid credentials.");
        return false;
    }

    public void showAdminPanel(MembershipManager memberManager, TrainerManager trainerManager, Scanner sc) {
        while (true) {
            UIUtils.clearScreen();
            System.out.println(UIUtils.INDENT + "+-------------------------------------------+");
            System.out.println(UIUtils.INDENT + "|                ADMIN PANEL                |");
            System.out.println(UIUtils.INDENT + "+-------------------------------------------+");
            System.out.println(UIUtils.INDENT + "[1] View All Members");
            System.out.println(UIUtils.INDENT + "[2] View Trainers with Clients");
            System.out.println(UIUtils.INDENT + "[3] Search Member by Name");
            System.out.println(UIUtils.INDENT + "[4] Search Members by Expiry Date");
            System.out.println(UIUtils.INDENT + "[5] Calculate Total Revenue");
            System.out.println(UIUtils.INDENT + "[6] Logout");

            System.out.print(UIUtils.INDENT + "Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    memberManager.listMembers();
                    UIUtils.pause();
                    break;
                case "2":
                    trainerManager.viewTrainersWithClients(memberManager.getAllMembers());
                    UIUtils.pause();
                    break;
                case "3":
                    System.out.println(UIUtils.INDENT + "--- Search Member by Name ---");
                    String searchName = InputHelper.readNonEmptyString(sc, "Enter name to search: ");
                    memberManager.searchByName(searchName);
                    UIUtils.pause();
                    break;
                case "4":
                    System.out.println(UIUtils.INDENT + "--- Search Members by Expiry Date ---");
                    System.out.print(UIUtils.INDENT + "Enter expiry date (yyyy-MM-dd): ");
                    String dateStr = sc.nextLine().trim();
                    try {
                        LocalDate date = LocalDate.parse(dateStr);
                        memberManager.searchByExpiryDate(date);
                    } catch (Exception e) {
                        System.out.println(UIUtils.INDENT + "Invalid date format.");
                    }
                    UIUtils.pause();
                    break;
                case "5":
                    memberManager.calculateTotalSales();
                    UIUtils.pause();
                    break;
                case "6":
                    return;
                default:
                    System.out.println(UIUtils.INDENT + "Invalid choice.");
                    UIUtils.pause();
            }
        }
    }
}

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
            System.out.println("\n" + UIUtils.INDENT + "[1] View All Members");
            System.out.println(UIUtils.INDENT + "[2] View All REGULAR Members");
            System.out.println(UIUtils.INDENT + "[3] View All VIP Members");
            System.out.println(UIUtils.INDENT + "[4] View All SVIP Members");
            System.out.println(UIUtils.INDENT + "[5] View Trainers with Clients");
            System.out.println(UIUtils.INDENT + "[6] Search Member by Name");
            System.out.println(UIUtils.INDENT + "[7] View All Members by Expiry Date");
            System.out.println(UIUtils.INDENT + "[8] Calculate Total Revenue");
            System.out.println(UIUtils.INDENT + "[9] Update Admin Username/Password");
            System.out.println(UIUtils.INDENT + "[10] Logout\n");

            System.out.print(UIUtils.INDENT + "Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    UIUtils.clearScreen();
                    memberManager.listMembers();
                    UIUtils.pause();
                    break;
                case "2":
                    UIUtils.clearScreen();
                    memberManager.listMembersByType("REGULAR");
                    UIUtils.pause();
                    break;
                case "3":
                    UIUtils.clearScreen();
                    memberManager.listMembersByType("VIP");
                    UIUtils.pause();
                    break;
                case "4":
                    UIUtils.clearScreen();
                    memberManager.listMembersByType("SVIP");
                    UIUtils.pause();
                    break;
                case "5":
                    UIUtils.clearScreen();
                    trainerManager.viewTrainersWithClients(memberManager.getAllMembers());
                    UIUtils.pause();
                    break;
                case "6":
                    UIUtils.clearScreen();
                    System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
                    System.out.println(UIUtils.INDENT + "|              SEARCH MEMBER BY NAME             |");
                    System.out.println(UIUtils.INDENT + "+------------------------------------------------+\n");
                    String searchName = InputHelper.readNonEmptyString(sc, "Enter name to search: ");
                    memberManager.searchByName(searchName);
                    UIUtils.pause();
                    break;
                case "7":
                    UIUtils.clearScreen();
                    // View all members sorted by expiry date, with sub-option to search
                    memberManager.listMembersSortedByExpiryDate();
                    System.out.println("\n" + UIUtils.INDENT + "[1] Search Member by Expiry Date");
                    System.out.println(UIUtils.INDENT + "[2] Back");
                    System.out.print(UIUtils.INDENT + "Choose option: ");
                    String subChoice = sc.nextLine().trim();
                    if (subChoice.equals("1")) {
                        System.out.print("\n" + UIUtils.INDENT + "Enter expiry date (yyyy-MM-dd): ");
                        String dateStr = sc.nextLine().trim();
                        try {
                            LocalDate date = LocalDate.parse(dateStr);
                            memberManager.searchByExpiryDate(date);
                        } catch (Exception e) {
                            System.out.println("\n" + UIUtils.INDENT + "Invalid date format.");
                        }
                        UIUtils.pause();
                    }
                    break;
                case "8":
                    UIUtils.clearScreen();
                    memberManager.calculateTotalSales();
                    UIUtils.pause();
                    break;
                case "9":
                    UIUtils.clearScreen();
                    System.out.println(UIUtils.INDENT + "+-------------------------------------------+");
                    System.out.println(UIUtils.INDENT + "|        UPDATE ADMIN CREDENTIALS           |");
                    System.out.println(UIUtils.INDENT + "+-------------------------------------------+");
                    System.out.println(UIUtils.INDENT + "[1] Update Username");
                    System.out.println(UIUtils.INDENT + "[2] Update Password");
                    System.out.println(UIUtils.INDENT + "[3] Back\n");
                    System.out.print(UIUtils.INDENT + "Choose an option: ");
                    String updateChoice = sc.nextLine().trim();
                    Admin currentAdmin = null;
                    // Find the currently logged-in admin (if tracked, otherwise use the first admin)
                    if (!admins.isEmpty()) currentAdmin = admins.get(0);
                    switch (updateChoice) {
                        case "1":
                            System.out.print(UIUtils.INDENT + "Enter new username: ");
                            String newAdminUsername = sc.nextLine().trim();
                            if (newAdminUsername.isEmpty()) {
                                System.out.println(UIUtils.INDENT + "Username cannot be empty.");
                                UIUtils.pause();
                                break;
                            }
                            // Check uniqueness across admins
                            boolean taken = false;
                            for (Admin a : admins) {
                                if (a.getUsername().equals(newAdminUsername)) {
                                    taken = true;
                                    break;
                                }
                            }
                            if (taken) {
                                System.out.println(UIUtils.INDENT + "Username already taken by another admin.");
                                UIUtils.pause();
                                break;
                            }
                            currentAdmin.setUsername(newAdminUsername);
                            System.out.println(UIUtils.INDENT + "Username updated.");
                            UIUtils.pause();
                            break;
                        case "2":
                            System.out.print(UIUtils.INDENT + "Enter current password: ");
                            String currentPass = sc.nextLine();
                            if (!currentAdmin.checkPassword(currentPass)) {
                                System.out.println(UIUtils.INDENT + "Incorrect current password.");
                                UIUtils.pause();
                                break;
                            }
                            System.out.print(UIUtils.INDENT + "Enter new password: ");
                            String newPass = sc.nextLine();
                            if (newPass.equals(currentPass)) {
                                System.out.println(UIUtils.INDENT + "New password cannot be the same as the old password.");
                                UIUtils.pause();
                                break;
                            }
                            currentAdmin.setPassword(newPass);
                            System.out.println(UIUtils.INDENT + "Password updated.");
                            UIUtils.pause();
                            break;
                        case "3":
                            break;
                        default:
                            System.out.println(UIUtils.INDENT + "Invalid option.");
                            UIUtils.pause();
                    }
                    break;
                case "10":
                    return;
                default:
                    System.out.println(UIUtils.INDENT + "Invalid option.");
                    UIUtils.pause();
            }
        }
    }
}

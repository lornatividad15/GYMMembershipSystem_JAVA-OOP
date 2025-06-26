//main
package gym.membership.management.system;

import java.util.Scanner;

/**
 * Main class for the Gym Membership Management System.
 * Demonstrates OOP: encapsulation, inheritance, polymorphism, abstraction, modularity.
 */
public class GymMembershipManagementSystem {
    public static void main(String[] args) {
        // Instantiate managers for members, trainers, and admins
        MembershipManager memberManager = new MembershipManager();
        TrainerManager trainerManager = new TrainerManager();
        AdminManager adminManager = new AdminManager();
        Scanner sc = new Scanner(System.in);

        // Main menu loop
        while (true) {
            UIUtils.clearScreen();
            System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
            System.out.println(UIUtils.INDENT + "|       GYM MEMBERSHIP MANAGEMENT SYSTEM         |");
            System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
            System.out.println("\n" + UIUtils.INDENT + "[1] Register as Member");
            System.out.println(UIUtils.INDENT + "[2] Member Login");
            System.out.println(UIUtils.INDENT + "[3] Admin Login");
            System.out.println(UIUtils.INDENT + "[4] Exit\n");
            System.out.print(UIUtils.INDENT + "Choose an option: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    // Member registration with username/password
                    UIUtils.clearScreen();
                    System.out.println(UIUtils.INDENT + "--- Member Registration ---");
                    // Prompt for username and check immediately for duplicates
                    String username;
                    while (true) {
                        username = InputHelper.readNonEmptyString(sc, "Enter Username: ");
                        if (memberManager.isUsernameTaken(username)) {
                            System.out.println(UIUtils.INDENT + "Username already taken. Please choose another.");
                        } else {
                            break;
                        }
                    }
                    String password = InputHelper.readNonEmptyString(sc, "Enter Password: ");
                    String name = InputHelper.readNonEmptyString(sc, "Enter Name: ");
                    System.out.print(UIUtils.INDENT + "Enter Age: ");
                    int age = InputHelper.readInt(sc);
                    String gender = InputHelper.readNonEmptyString(sc, "Enter Gender: ");
                    String membershipType = InputHelper.readMembershipType(sc);

                    // Show perks after type selection
                    System.out.println("\n" + UIUtils.INDENT + "Membership Perks:");
                    if (membershipType.equalsIgnoreCase("REGULAR")) {
                        System.out.println(UIUtils.INDENT + "- REGULAR: Access to gym equipment and group classes");
                    } else if (membershipType.equalsIgnoreCase("VIP")) {
                        System.out.println(UIUtils.INDENT + "- VIP: Free access to sauna, trainer, and drinks");
                    } else if (membershipType.equalsIgnoreCase("SVIP")) {
                        System.out.println(UIUtils.INDENT + "- SVIP: All VIP perks + priority booking, exclusive locker, and premium support");
                    }
                    UIUtils.pause();

                    double weight = InputHelper.readDouble(sc, "Enter weight (kg): ");
                    double height = InputHelper.readDouble(sc, "Enter height (cm): ");
                    memberManager.addMember(username, password, name, age, gender, membershipType, weight, height);
                    UIUtils.pause();
                    break;
                case "2":
                    // Member login with username/password
                    UIUtils.clearScreen();
                    System.out.println(UIUtils.INDENT + "--- Member Login ---");
                    String loginUsername = InputHelper.readNonEmptyString(sc, "Enter Username: ");
                    String loginPassword = InputHelper.readNonEmptyString(sc, "Enter Password: ");
                    Member member = memberManager.getMemberByUsername(loginUsername);
                    if (member != null && member.checkPassword(loginPassword)) {
                        memberMenu(memberManager, trainerManager, sc, member);
                    } else {
                        System.out.println(UIUtils.INDENT + "Invalid username or password.");
                        UIUtils.pause();
                    }
                    break;
                case "3":
                    // Admin login
                    UIUtils.clearScreen();
                    System.out.println(UIUtils.INDENT + "--- Admin Login ---");
                    System.out.print(UIUtils.INDENT + "Username: ");
                    String adminUsername = sc.nextLine().trim();
                    System.out.print(UIUtils.INDENT + "Password: ");
                    String adminPassword = sc.nextLine().trim();
                    Admin loggedInAdmin = null;
                    for (Admin admin : adminManager.getAdmins()) {
                        if (admin.getUsername().equals(adminUsername) && admin.checkPassword(adminPassword)) {
                            loggedInAdmin = admin;
                            System.out.println(UIUtils.INDENT + "Login successful!");
                            break;
                        }
                    }
                    if (loggedInAdmin != null) {
                        adminManager.showAdminPanel(memberManager, trainerManager, sc);
                    } else {
                        System.out.println(UIUtils.INDENT + "Invalid credentials.");
                        UIUtils.pause();
                    }
                    break;
                case "4":
                    // Exit system
                    System.out.println(UIUtils.INDENT + "Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println(UIUtils.INDENT + "Invalid option.");
                    UIUtils.pause();
            }
        }
    }

    /**
     * Member menu for logged-in members. Demonstrates OOP: dynamic method dispatch, encapsulation, modularity.
     */
    private static void memberMenu(MembershipManager manager, TrainerManager trainerManager, Scanner sc, Member member) {
        while (true) {
            UIUtils.clearScreen();
            System.out.println(UIUtils.INDENT + "+---------------- MEMBER PANEL ------------------+");
            System.out.println(UIUtils.INDENT + "Welcome, " + member.getName());
            System.out.println(UIUtils.INDENT + "[0] Logout");
            System.out.println(UIUtils.INDENT + "[1] View My Info");
            System.out.println(UIUtils.INDENT + "[2] Edit My Info");
            System.out.println(UIUtils.INDENT + "[3] Delete My Account");
            System.out.println(UIUtils.INDENT + "[4] View Trainers");
            System.out.println(UIUtils.INDENT + "[5] Assign Trainer");
            System.out.println(UIUtils.INDENT + "[6] Search Members");
            System.out.println(UIUtils.INDENT + "[7] Exit to Main Menu\n");
            System.out.print(UIUtils.INDENT + "Choose an option: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "0":
                case "7":
                    // Logout/Exit
                    return;
                case "1":
                    // View member info (polymorphism: displayInfo overridden in subclasses)
                    UIUtils.clearScreen();
                    member.displayInfo();
                    UIUtils.pause();
                    break;
                case "2":
                    // Edit member info with submenu
                    member = editMemberInfo(manager, sc, member); // <-- update reference here
                    break;
                case "3":
                    // Delete account
                    if (InputHelper.confirm(sc, "Are you sure you want to delete your account?")) {
                        manager.deleteMember(member.getName(), sc);
                        System.out.println(UIUtils.INDENT + "Account deleted.");
                        UIUtils.pause();
                        return;
                    }
                    break;
                case "4":
                    // View trainers (no clients shown)
                    trainerManager.listTrainersSimple();
                    UIUtils.pause();
                    break;
                case "5":
                    // Assign trainer
                    trainerManager.assignTrainerToMember(member, sc);
                    UIUtils.pause();
                    break;
                case "6":
                    // Search members by name
                    UIUtils.clearScreen();
                    System.out.println(UIUtils.INDENT + "--- Search Members ---");
                    String searchName = InputHelper.readNonEmptyString(sc, "Enter name to search: ");
                    manager.searchByName(searchName);
                    UIUtils.pause();
                    break;
                default:
                    System.out.println(UIUtils.INDENT + "Invalid option.");
                    UIUtils.pause();
            }
        }
    }

    /**
     * Edit member info submenu: allows editing name, age, gender, weight, height, membership type, password.
     */
    private static Member editMemberInfo(MembershipManager manager, Scanner sc, Member member) {
        while (true) {
            UIUtils.clearScreen();
            System.out.println(UIUtils.INDENT + "--- Edit My Info ---");
            System.out.println(UIUtils.INDENT + "[1] Name");
            System.out.println(UIUtils.INDENT + "[2] Age");
            System.out.println(UIUtils.INDENT + "[3] Gender");
            System.out.println(UIUtils.INDENT + "[4] Weight");
            System.out.println(UIUtils.INDENT + "[5] Height");
            System.out.println(UIUtils.INDENT + "[6] Membership Type");
            System.out.println(UIUtils.INDENT + "[7] Password");
            System.out.println(UIUtils.INDENT + "[8] Back\n");
            System.out.print(UIUtils.INDENT + "Choose what to edit: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    member.setName(InputHelper.readNonEmptyString(sc, "Enter new Name: "));
                    System.out.println(UIUtils.INDENT + "Name updated.");
                    UIUtils.pause();
                    break;
                case "2":
                    System.out.print(UIUtils.INDENT + "Enter new Age: ");
                    member.setAge(InputHelper.readInt(sc));
                    System.out.println(UIUtils.INDENT + "Age updated.");
                    UIUtils.pause();
                    break;
                case "3":
                    member.setGender(InputHelper.readNonEmptyString(sc, "Enter new Gender: "));
                    System.out.println(UIUtils.INDENT + "Gender updated.");
                    UIUtils.pause();
                    break;
                case "4":
                    if (member instanceof BMIMember) {
                        ((BMIMember) member).setWeightKg(InputHelper.readDouble(sc, "Enter new Weight (kg): "));
                        System.out.println(UIUtils.INDENT + "Weight updated.");
                    } else {
                        System.out.println(UIUtils.INDENT + "Weight only available for BMI members.");
                    }
                    UIUtils.pause();
                    break;
                case "5":
                    if (member instanceof BMIMember) {
                        ((BMIMember) member).setHeightCm(InputHelper.readDouble(sc, "Enter new Height (cm): "));
                        System.out.println(UIUtils.INDENT + "Height updated.");
                    } else {
                        System.out.println(UIUtils.INDENT + "Height only available for BMI members.");
                    }
                    UIUtils.pause();
                    break;
                case "6":
                    String newType = InputHelper.readMembershipType(sc);
                    member = manager.updateMemberType(member, newType);
                    System.out.println(UIUtils.INDENT + "Membership type updated.");
                    UIUtils.pause();
                    break;
                case "7":
                    member.setPassword(InputHelper.readNonEmptyString(sc, "Enter new Password: "));
                    System.out.println(UIUtils.INDENT + "Password updated.");
                    UIUtils.pause();
                    break;
                case "8":
                    return member;
                default:
                    System.out.println(UIUtils.INDENT + "Invalid option.");
                    UIUtils.pause();
            }
        }
    }
}

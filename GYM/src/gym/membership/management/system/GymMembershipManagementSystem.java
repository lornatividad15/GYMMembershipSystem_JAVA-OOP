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
                    System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
                    System.out.println(UIUtils.INDENT + "|               MEMBER REGISTRATION              |");
                    System.out.println(UIUtils.INDENT + "+------------------------------------------------+\n");
                    // Prompt for username and check immediately for duplicates
                    String username;
                    while (true) {
                        username = InputHelper.readNonEmptyString(sc, "Enter Username: ");
                        if (memberManager.isUsernameTaken(username, adminManager.getAdmins())) {
                            System.out.println("\n" + UIUtils.INDENT + "Username already taken. Please choose another.\n");
                        } else {
                            break;
                        }
                    }
                    String password = InputHelper.readNonEmptyString(sc, "Enter Password: ");
                    String name = InputHelper.readNonEmptyString(sc, "Enter Name: ");
                    System.out.print(UIUtils.INDENT + "Enter Age: ");
                    int age = InputHelper.readInt(sc);
                    // Gender input with strict validation for registration
                    String gender;
                    while (true) {
                        String genderInput = InputHelper.readNonEmptyString(sc, "Enter Gender: ");
                        String genderUpper = genderInput.trim().toUpperCase();
                        if (genderUpper.equals("MALE") || genderUpper.equals("FEMALE") || genderUpper.equals("M") || genderUpper.equals("F")) {
                            if (genderUpper.equals("M")) gender = "Male";
                            else if (genderUpper.equals("F")) gender = "Female";
                            else gender = genderUpper.charAt(0) + genderUpper.substring(1).toLowerCase();
                            break;
                        } else {
                            System.out.println("\n" + UIUtils.INDENT + "Invalid gender. Please enter MALE, FEMALE, M, or F.");
                        }
                    }
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
                    System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
                    System.out.println(UIUtils.INDENT + "|                  MEMBER LOGIN                  |");
                    System.out.println(UIUtils.INDENT + "+------------------------------------------------+\n");
                    String loginUsername = InputHelper.readNonEmptyString(sc, "Enter Username: ");
                    String loginPassword = InputHelper.readNonEmptyString(sc, "Enter Password: ");
                    Member member = memberManager.getMemberByUsername(loginUsername);
                    if (member != null && member.checkPassword(loginPassword)) {
                        memberMenu(memberManager, trainerManager, adminManager, sc, member);
                    } else {
                        System.out.println("\n" + UIUtils.INDENT + "Invalid username or password.\n");
                        UIUtils.pause();
                    }
                    break;
                case "3":
                    // Admin login
                    UIUtils.clearScreen();
                    System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
                    System.out.println(UIUtils.INDENT + "|                   ADMIN LOGIN                  |");
                    System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
                    System.out.print("\n" + UIUtils.INDENT + "Username: ");
                    String adminUsername = sc.nextLine().trim();
                    System.out.print(UIUtils.INDENT + "Password: ");
                    String adminPassword = sc.nextLine().trim();
                    Admin loggedInAdmin = null;
                    for (Admin admin : adminManager.getAdmins()) {
                        if (admin.getUsername().equals(adminUsername) && admin.checkPassword(adminPassword)) {
                            loggedInAdmin = admin;
                            System.out.println("\n" + UIUtils.INDENT + "Login successful!");
                            break;
                        }
                    }
                    if (loggedInAdmin != null) {
                        adminManager.showAdminPanel(memberManager, trainerManager, sc);
                    } else {
                        System.out.println("\n" + UIUtils.INDENT + "Invalid credentials.");
                        UIUtils.pause();
                    }
                    break;
                case "4":
                    // Exit system
                    System.out.println("\n" + UIUtils.INDENT + "Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("\n" + UIUtils.INDENT + "Invalid option.");
                    UIUtils.pause();
            }
        }
    }

     //Member menu for logged-in members. Demonstrates OOP: dynamic method dispatch, encapsulation, modularity.
    private static void memberMenu(MembershipManager manager, TrainerManager trainerManager, AdminManager adminManager, Scanner sc, Member member) {
        while (true) {
            UIUtils.clearScreen();
            System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
            System.out.println(UIUtils.INDENT + "|                  MEMBER PANEL                  |");
            System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
            System.out.println("\n" + UIUtils.INDENT + "Welcome, " + member.getName());
            System.out.println(UIUtils.INDENT + "[1] View My Info");
            System.out.println(UIUtils.INDENT + "[2] Edit My Info");
            System.out.println(UIUtils.INDENT + "[3] Delete My Account");
            System.out.println(UIUtils.INDENT + "[4] View Trainers");
            System.out.println(UIUtils.INDENT + "[5] Assign Trainer");
            System.out.println(UIUtils.INDENT + "[6] Exit to Main Menu\n");
            System.out.print(UIUtils.INDENT + "Choose an option: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "6":
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
                    member = editMemberInfo(manager, adminManager, sc, member); // <-- update reference here
                    break;
                case "3":
                    // Delete account
                    if (InputHelper.confirm(sc, "Are you sure you want to delete your account?")) {
                        manager.deleteMember(member.getName(), sc);
                        System.out.println("\n" + UIUtils.INDENT + "Account deleted.");
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
                default:
                    System.out.println("\n" + UIUtils.INDENT + "Invalid option.");
                    UIUtils.pause();
            }
        }
    }

    /**
     * Edit member info submenu: allows editing name, age, gender, weight, height, membership type, password.
     */
    private static Member editMemberInfo(MembershipManager manager, AdminManager adminManager, Scanner sc, Member member) {
        while (true) {
            UIUtils.clearScreen();
            System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
            System.out.println(UIUtils.INDENT + "|                  EDIT MY INFO                  |");
            System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
            System.out.println("\n" + UIUtils.INDENT + "[1] Name");
            System.out.println(UIUtils.INDENT + "[2] Age");
            System.out.println(UIUtils.INDENT + "[3] Gender");
            System.out.println(UIUtils.INDENT + "[4] Weight (if applicable)");
            System.out.println(UIUtils.INDENT + "[5] Height (if applicable)");
            System.out.println(UIUtils.INDENT + "[6] Password");
            System.out.println(UIUtils.INDENT + "[7] Username");
            System.out.println(UIUtils.INDENT + "[8] Back\n");
            System.out.print(UIUtils.INDENT + "Choose an option: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    String newName = InputHelper.readNonEmptyString(sc, "Enter new name: ");
                    member.setName(newName);
                    System.out.println(UIUtils.INDENT + "Name updated.");
                    UIUtils.pause();
                    break;
                case "2":
                    System.out.print(UIUtils.INDENT + "Enter new age: ");
                    int newAge = InputHelper.readInt(sc);
                    member.setAge(newAge);
                    System.out.println(UIUtils.INDENT + "Age updated.");
                    UIUtils.pause();
                    break;
                case "3":
                    while (true) {
                        System.out.print(UIUtils.INDENT + "Enter new gender (M/F/MALE/FEMALE): ");
                        String gender = sc.nextLine().trim().toUpperCase();
                        if (gender.equals("M") || gender.equals("F") || gender.equals("MALE") || gender.equals("FEMALE")) {
                            member.setGender(gender);
                            System.out.println(UIUtils.INDENT + "Gender updated.");
                            break;
                        } else {
                            System.out.println(UIUtils.INDENT + "Invalid gender. Please enter M, F, MALE, or FEMALE.");
                        }
                    }
                    UIUtils.pause();
                    break;
                case "4":
                    if (member instanceof BMIMember) {
                        double newWeight = InputHelper.readDouble(sc, "Enter new weight (kg): ");
                        ((BMIMember) member).setWeightKg(newWeight);
                        System.out.println(UIUtils.INDENT + "Weight updated.");
                    } else {
                        System.out.println(UIUtils.INDENT + "Weight is only applicable for BMI members.");
                    }
                    UIUtils.pause();
                    break;
                case "5":
                    if (member instanceof BMIMember) {
                        double newHeight = InputHelper.readDouble(sc, "Enter new height (cm): ");
                        ((BMIMember) member).setHeightCm(newHeight);
                        System.out.println(UIUtils.INDENT + "Height updated.");
                    } else {
                        System.out.println(UIUtils.INDENT + "Height is only applicable for BMI members.");
                    }
                    UIUtils.pause();
                    break;
                case "6":
                    System.out.print(UIUtils.INDENT + "Enter current password: ");
                    String currentPass = sc.nextLine();
                    if (!member.checkPassword(currentPass)) {
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
                    member.setPassword(newPass);
                    System.out.println(UIUtils.INDENT + "Password updated.");
                    UIUtils.pause();
                    break;
                case "7":
                    System.out.print(UIUtils.INDENT + "Enter new username: ");
                    String newUsername = sc.nextLine().trim();
                    if (newUsername.isEmpty()) {
                        System.out.println(UIUtils.INDENT + "Username cannot be empty.");
                        UIUtils.pause();
                        break;
                    }
                    // Check uniqueness across members and admins using AdminManager
                    boolean takenByMember = manager.isUsernameTaken(newUsername);
                    boolean takenByAdmin = false;
                    for (Admin a : adminManager.getAdmins()) {
                        if (a.getUsername().equalsIgnoreCase(newUsername)) {
                            takenByAdmin = true;
                            break;
                        }
                    }
                    if (takenByMember || takenByAdmin) {
                        System.out.println(UIUtils.INDENT + "Username already taken by a member or admin.");
                        UIUtils.pause();
                        break;
                    }
                    member.setUsername(newUsername);
                    System.out.println(UIUtils.INDENT + "Username updated.");
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

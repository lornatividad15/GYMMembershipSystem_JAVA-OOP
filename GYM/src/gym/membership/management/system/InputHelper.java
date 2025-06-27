package gym.membership.management.system;

import java.util.Scanner;

public class InputHelper {

    // Read integer with validation
    public static int readInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("\n" + UIUtils.INDENT + "Invalid number. Try again: ");
            }
        }
    }

    // ✅ Read non-empty string with prompt
    public static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(UIUtils.INDENT + prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("\n" + UIUtils.INDENT + "Input cannot be empty. Please try again.\n");
        }
    }

    // ✅ Read membership type with validation
    public static String readMembershipType(Scanner sc) {
        while (true) {
            System.out.print(UIUtils.INDENT + "Enter Membership Type (REGULAR, VIP, SVIP): ");
            String type = sc.nextLine().trim().toUpperCase();
            if (type.equals("REGULAR") || type.equals("VIP") || type.equals("SVIP")) {
                return type;
            }
            System.out.println("\n" + UIUtils.INDENT + "Invalid type. Please enter REGULAR, VIP, or SVIP.\n");
        }
    }

    // ✅ Confirmation for deletions (Y/N)
    public static boolean confirm(Scanner sc, String message) {
        System.out.print("\n" + UIUtils.INDENT + message + " (Y/N): ");
        String response = sc.nextLine().trim().toUpperCase();
        return response.equals("Y");
    }
    
    public static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(UIUtils.INDENT + prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n" + UIUtils.INDENT + "Invalid number. Try again.\n");
            }
        }
    }

}

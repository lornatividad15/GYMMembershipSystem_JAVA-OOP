package gym.membership.management.system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

// OOP: Manages Member objects, encapsulates member list, supports CRUD and reporting
public class MembershipManager {
    // OOP: Encapsulation (private field)
    private ArrayList<Member> members;

    public MembershipManager() {
        members = new ArrayList<Member>();
    }

    // OOP: Polymorphism (BMIMember, VIPMember)
    public boolean isUsernameTaken(String username) {
        for (Member m : members) {
            if (m.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // Check if username is taken by a member or admin
    public boolean isUsernameTaken(String username, ArrayList<Admin> admins) {
        for (Member m : members) {
            if (m.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        for (Admin a : admins) {
            if (a.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public void addMember(String username, String password, String name, int age, String gender, String membershipType, double weight, double height) {
        if (isUsernameTaken(username)) {
            System.out.println(UIUtils.INDENT + "Username already taken. Please choose another.");
            return;
        }
        Member newMember;
        if (membershipType.equalsIgnoreCase("VIP") || membershipType.equalsIgnoreCase("SVIP")) {
            newMember = new VIPMember(username, password, name, age, gender, membershipType);
        } else {
            newMember = new BMIMember(username, password, name, age, gender, membershipType, weight, height);
        }
        members.add(newMember);
        System.out.println("\n" + UIUtils.INDENT + "Member added successfully!");
    }

    // OOP: Encapsulation
    public void listMembers() {
        if (members.isEmpty()) {
            System.out.println("\n" + UIUtils.INDENT + "No members found.");
        } else {
            for (Member member : members) {
                member.displayInfo(); // OOP: Polymorphism (overridden displayInfo)
            }
        }
    }

    // OOP: List members by membership type
    public void listMembersByType(String type) {
        boolean found = false;
        for (Member member : members) {
            if (member.getMembershipType().equalsIgnoreCase(type)) {
                member.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println(UIUtils.INDENT + "No members found with type: " + type);
        }
    }

    public void searchByName(String name) {
        boolean found = false;
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                member.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("\n" + UIUtils.INDENT + "No member found with that name.");
        }
    }

    public void searchByExpiryDate(LocalDate date) {
        boolean found = false;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Member member : members) {
            if (member.getExpiryDate().toLocalDate().equals(date)) {
                member.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("\n" + UIUtils.INDENT + "No members expiring on " + date.format(fmt));
        }
    }

    public void updateMember(String name, Scanner sc) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                System.out.println("\n" + UIUtils.INDENT + "Editing member: " + member.getName());
                String newName = InputHelper.readNonEmptyString(sc, "Enter new name: ");
                int newAge = InputHelper.readInt(sc);
                String newGender = InputHelper.readNonEmptyString(sc, "Enter new gender: ");
                String newType = InputHelper.readMembershipType(sc);

                // Use updateMemberType for OOP correctness
                Member updated = updateMemberType(member, newType);
                updated.setName(newName);
                updated.setAge(newAge);
                updated.setGender(newGender);

                System.out.println("\n" + UIUtils.INDENT + "Member updated successfully!");
                return;
            }
        }
        System.out.println("\n" + UIUtils.INDENT + "No member found with that name.");
    }

    public void deleteMember(String name, Scanner sc) {
        Iterator<Member> iterator = members.iterator();
        while (iterator.hasNext()) {
            Member member = iterator.next();
            if (member.getName().equalsIgnoreCase(name)) {
                if (InputHelper.confirm(sc, "Are you sure you want to delete this member?")) {
                    iterator.remove();
                    System.out.println(UIUtils.INDENT + "Member deleted successfully!");
                }
                return;
            }
        }
        System.out.println(UIUtils.INDENT + "No member found with that name.");
    }

    public void reportSummary() {
        int regular = 0, vip = 0, svip = 0;
        for (Member member : members) {
            String type = member.getMembershipType().toUpperCase();
            if (type.equals("VIP")) {
                vip++;
            } else if (type.equals("SVIP")) {
                svip++;
            } else {
                regular++;
            }
        }
        int total = regular + vip + svip;
        System.out.println(UIUtils.INDENT + "+-------------------------------------------+");
        System.out.println(UIUtils.INDENT + "|             MEMBERSHIP REPORT             |");
        System.out.println(UIUtils.INDENT + "+-------------------------------------------+\n");
        System.out.println(UIUtils.INDENT + "Total Members : " + total);
        System.out.println(UIUtils.INDENT + "REGULAR        : " + regular);
        System.out.println(UIUtils.INDENT + "VIP            : " + vip);
        System.out.println(UIUtils.INDENT + "SVIP           : " + svip);
    }

    public void calculateTotalSales() {
        double totalSales = 0;
        for (Member member : members) {
            totalSales += member.getMembershipCost();
        }
        System.out.println(UIUtils.INDENT + "+-------------------------------------------+");
        System.out.println(UIUtils.INDENT + "|             TOTAL SALES REPORT            |");
        System.out.println(UIUtils.INDENT + "+-------------------------------------------+\n");
        System.out.printf(UIUtils.INDENT + "Total Revenue from Memberships: Php %.2f\n", totalSales);
    }

    public Member getMemberByName(String name) {
        for (Member m : members) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public Member getMemberByUsername(String username) {
        for (Member m : members) {
            if (m.getUsername().equalsIgnoreCase(username)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Member> getAllMembers() {
        return members;
    }

    public Member updateMemberType(Member member, String newType) {
        members.remove(member);

        Member updated;
        if (newType.equalsIgnoreCase("VIP") || newType.equalsIgnoreCase("SVIP")) {
            updated = new VIPMember(
                member.getUsername(),
                member.getPassword(),
                member.getName(),
                member.getAge(),
                member.getGender(),
                newType
            );
        } else if (member instanceof BMIMember) {
            double weight = ((BMIMember) member).getWeightKg();
            double height = ((BMIMember) member).getHeightCm();
            updated = new BMIMember(
                member.getUsername(),
                member.getPassword(),
                member.getName(),
                member.getAge(),
                member.getGender(),
                newType,
                weight,
                height
            );
        } else {
            // If the old member was not a BMIMember, create a plain Member
            updated = new Member(
                member.getUsername(),
                member.getPassword(),
                member.getName(),
                member.getAge(),
                member.getGender(),
                newType
            );
        }
        updated.setHiredTrainer(member.getHiredTrainer());
        members.add(updated);
        return updated;
    }

    // OOP: List all members sorted by expiry date
    public void listMembersSortedByExpiryDate() {
        if (members.isEmpty()) {
            System.out.println("\n" + UIUtils.INDENT + "No members found.");
            return;
        }
        ArrayList<Member> sorted = new ArrayList<>(members);
        sorted.sort((a, b) -> a.getExpiryDate().compareTo(b.getExpiryDate()));
        System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
        System.out.println(UIUtils.INDENT + "|         MEMBERS SORTED BY EXPIRY DATE          |");
        System.out.println(UIUtils.INDENT + "+------------------------------------------------+\n");
        for (Member member : sorted) {
            member.displayInfo();
        }
    }

    // Update member password with validation
    public boolean updateMemberPassword(Member member, String newPassword) {
        if (member.getPassword().equals(newPassword)) {
            System.out.println(UIUtils.INDENT + "New password cannot be the same as the old password.");
            return false;
        }
        member.setPassword(newPassword);
        System.out.println(UIUtils.INDENT + "Password updated successfully.");
        return true;
    }
}
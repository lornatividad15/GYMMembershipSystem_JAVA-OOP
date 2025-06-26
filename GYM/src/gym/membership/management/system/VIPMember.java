// inheritance & polymorphism
package gym.membership.management.system;

// OOP: Inheritance (extends Member), Polymorphism (overrides displayInfo), Encapsulation
public class VIPMember extends Member {
    private String perks;

    public VIPMember(String username, String password, String name, int age, String gender, String membershipType) {
        super(username, password, name, age, gender, membershipType);
        this.perks = "Free access to sauna, trainer, and drinks";
    }

    public String getPerks() {
        return perks;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        if (getMembershipType().equalsIgnoreCase("VIP") || getMembershipType().equalsIgnoreCase("SVIP")) {
            System.out.println(UIUtils.INDENT + "VIP Perks: " + perks);
        } else if (getMembershipType().equalsIgnoreCase("REGULAR")) {
            System.out.println(UIUtils.INDENT + "No VIP perks for REGULAR membership.");
        }
    }
}
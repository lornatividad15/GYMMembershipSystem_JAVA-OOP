// OOP: Inheritance (extends Person), Encapsulation (private fields), Polymorphism (overrides displayInfo), Implements Serializable
package gym.membership.management.system;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Member extends Person implements Serializable {
    private String membershipType;
    private double membershipCost;
    private String hiredTrainer = "None";
    private String username;
    private String password;
    private LocalDateTime registrationDate;
    private LocalDateTime expiryDate;

    public Member(String username, String password, String name, int age, String gender, String membershipType) {
        super(name, age, gender); // OOP: Inheritance
        this.username = username;
        this.password = password;
        this.membershipType = membershipType;
        this.membershipCost = determineCost(membershipType);
        setRegistrationAndExpiry(membershipType);
    }

    protected void setRegistrationAndExpiry(String membershipType) {
        this.registrationDate = LocalDateTime.now();
        int duration = 15;
        if (membershipType.equalsIgnoreCase("VIP")) duration = 30;
        else if (membershipType.equalsIgnoreCase("SVIP")) duration = 45;
        this.expiryDate = registrationDate.plusDays(duration);
    }

    private double determineCost(String type) {
        if (type.equalsIgnoreCase("VIP")) {
            return 1500.0;
        } else if (type.equalsIgnoreCase("SVIP")) {
            return 2500.0;
        } else {
            return 1000.0;
        }
    }

    public double getMembershipCost() {
        return membershipCost;
    }

    public void setMembershipCost(double cost) {
        this.membershipCost = cost;
    }

    public String getHiredTrainer() {
        return hiredTrainer;
    }

    public void setHiredTrainer(String trainerName) {
        this.hiredTrainer = trainerName;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
        this.membershipCost = determineCost(membershipType);
        setRegistrationAndExpiry(membershipType); // Reset dates on type change
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkPassword(String input) {
        return password.equals(input);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public LocalDateTime getExpiryDate() { return expiryDate; }

    @Override
    public void displayInfo() {
        System.out.println("\n" + UIUtils.INDENT + "Username: " + getUsername());
        System.out.println(UIUtils.INDENT + "Name: " + getName());
        System.out.println(UIUtils.INDENT + "Age: " + getAge());
        System.out.println(UIUtils.INDENT + "Gender: " + getGender());
        System.out.println(UIUtils.INDENT + "Membership Type: " + membershipType);
        System.out.println(UIUtils.INDENT + "Hired Trainer: " + hiredTrainer);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println(UIUtils.INDENT + "Registration Date: " + registrationDate.format(fmt));
        System.out.println(UIUtils.INDENT + "Expiry Date: " + expiryDate.format(fmt));
    }
}

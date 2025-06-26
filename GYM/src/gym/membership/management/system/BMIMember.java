package gym.membership.management.system;

// Inherits from Member and adds BMI attributes
public class BMIMember extends Member {
    private double weightKg;
    private double heightCm;

    public BMIMember(String username, String password, String name, int age, String gender, String membershipType, double weightKg, double heightCm) {
        super(username, password, name, age, gender, membershipType);
        this.weightKg = weightKg;
        this.heightCm = heightCm;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public double getHeightCm() {
        return heightCm;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public void setHeightCm(double heightCm) {
        this.heightCm = heightCm;
    }

    public double calculateBMI() {
        return BMIUtils.calculateBMI(weightKg, heightCm);
    }

    public String getBMICategory() {
        return BMIUtils.getBMICategory(calculateBMI());
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf(UIUtils.INDENT + "Weight: %.2f kg\n", weightKg);
        System.out.printf(UIUtils.INDENT + "Height: %.2f cm\n", heightCm);
        System.out.printf(UIUtils.INDENT + "BMI: %.2f (%s)\n", calculateBMI(), getBMICategory());
    }
}

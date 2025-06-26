package gym.membership.management.system;

import java.util.Random;

public class Trainer {
    private String name;
    private String expertise;
    private String availability; // OOP: Encapsulation

    // Generates a random availability time slot
    private String generateRandomAvailability() {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        int startHour = 6 + new Random().nextInt(8); // Between 6AM and 1PM
        int endHour = startHour + 4 + new Random().nextInt(3); // 4-6 hour slot
        String day = days[new Random().nextInt(days.length)];
        String start = String.format("%02d:00 %s", (startHour <= 12 ? startHour : startHour - 12), (startHour < 12 ? "AM" : "PM"));
        String end = String.format("%02d:00 %s", (endHour <= 12 ? endHour : endHour - 12), (endHour < 12 ? "AM" : "PM"));
        return day + " " + start + " - " + end;
    }

    // Constructor with random availability
    public Trainer(String name, String expertise) {
        this.name = name;
        this.expertise = expertise;
        this.availability = generateRandomAvailability();
    }

    // Overloaded constructor for custom availability
    public Trainer(String name, String expertise, String availability) {
        this.name = name;
        this.expertise = expertise;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public String getExpertise() {
        return expertise;
    }

    public String getAvailability() {
        return availability;
    }
}

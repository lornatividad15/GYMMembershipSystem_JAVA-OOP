package gym.membership.management.system;

import java.util.*;

// OOP: Modular code, manages Trainer objects and their assignment to members
public class TrainerManager {
    // OOP: Encapsulation (private field)
    private ArrayList<Trainer> trainers = new ArrayList<>();

    public TrainerManager() {
        // Default trainers with random availability
        trainers.add(new Trainer("Leo Santiago", "Weightlifting"));
        trainers.add(new Trainer("Marie Cruz", "Cardio & Zumba"));
        trainers.add(new Trainer("Ken Villanueva", "Strength Training"));
    }

    // OOP: Encapsulation, modularity
    public void assignTrainerToMember(Member member, Scanner sc) {
        listTrainers();
        System.out.print("\n" + UIUtils.INDENT + "Enter trainer name to assign: ");
        String name = sc.nextLine();

        boolean found = false;
        for (Trainer t : trainers) {
            if (t.getName().equalsIgnoreCase(name)) {
                member.setHiredTrainer(t.getName());
                System.out.println("\n" + UIUtils.INDENT + "Trainer assigned successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("\n" + UIUtils.INDENT + "Trainer not found.");
        }
    }

    // OOP: Polymorphism (uses Member's getHiredTrainer, which can be overridden)
    public void viewTrainersWithClients(ArrayList<Member> members) {
        UIUtils.clearScreen();
        System.out.println(UIUtils.INDENT + "+------------------------------------------------+");
        System.out.println(UIUtils.INDENT + "|            TRAINER AND THEIR CLIENTS           |");
        System.out.println(UIUtils.INDENT + "+------------------------------------------------+");

        if (trainers.isEmpty()) {
            System.out.println("\n" + UIUtils.INDENT + "No trainers available.");
            return;
        }

        // Sort trainers by name
        trainers.sort(Comparator.comparing(Trainer::getName));

        for (Trainer trainer : trainers) {
            System.out.println("\n" + UIUtils.INDENT + "Trainer: " + trainer.getName());
            System.out.println(UIUtils.INDENT + "Expertise: " + trainer.getExpertise());
            System.out.println(UIUtils.INDENT + "Availability: " + trainer.getAvailability());
            System.out.println(UIUtils.INDENT + "Clients:");

            List<String> clients = new ArrayList<>();

            for (Member member : members) {
                if (member.getHiredTrainer().equalsIgnoreCase(trainer.getName())) {
                    clients.add(member.getName());
                }
            }

            if (clients.isEmpty()) {
                System.out.println("\n" + UIUtils.INDENT + "  No clients assigned.");
            } else {
                Collections.sort(clients);
                for (String client : clients) {
                    System.out.println(UIUtils.INDENT + "  - " + client);
                }
            }
        }
    }

    // OOP: Encapsulation
    public void listTrainers() {
        System.out.println("\n" + UIUtils.INDENT + "Available Trainers:");
        for (Trainer t : trainers) {
            System.out.println(UIUtils.INDENT + "- " + t.getName() + " (" + t.getExpertise() + ") | Availability: " + t.getAvailability());
        }
    }

    /**
     * List trainers (name and expertise only) for member panel.
     */
    public void listTrainersSimple() {
        System.out.println("\n" + UIUtils.INDENT + "Available Trainers:");
        for (Trainer t : trainers) {
            System.out.println(UIUtils.INDENT + "- " + t.getName() + " (" + t.getExpertise() + ") | Availability: " + t.getAvailability());
        }
    }

    // OOP: Encapsulation (getter for trainers, if needed)
    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }
}
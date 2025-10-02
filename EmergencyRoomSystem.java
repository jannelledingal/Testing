import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Patient class
class Patient implements Comparable<Patient> {
    static int counter = 0; // static counter for unique IDs
    int id;                 // patient ID
    String name;
    int priority; // 1 = highest, 4 = lowest
    String condition;
    String arrivalTime;

    public Patient(String name, int priority, String condition, String arrivalTime) {
        this.id = ++counter; // auto-increment patient ID
        this.name = name;
        this.priority = priority;
        this.condition = condition;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public int compareTo(Patient other) {
        // Compare by priority first
        if (this.priority < other.priority) return -1;
        else if (this.priority > other.priority) return 1;
        else {
            // Same priority → compare arrival time
            return this.arrivalTime.compareTo(other.arrivalTime);
        }
    }

    @Override
    public String toString() {
        return "#" + id + " [P" + priority + "] " + name + " - " + condition + " (" + arrivalTime + ")";
    }
}

// ERQueue class
class ERQueue {
    private PriorityQueue<Patient> queue;
    private List<Patient> history; // store treated patients

    public ERQueue() {
        queue = new PriorityQueue<>();
        history = new ArrayList<>();
    }

    // Add new patient
    public void arrive(String name, int priority, String condition) {
        // Auto-generate current time
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        Patient p = new Patient(name, priority, condition, time);
        queue.add(p);
    }

    // Treat the next patient
    public void treatNext() {
        if (queue.isEmpty()) {
            System.out.println("No patients to treat.");
        } else {
            Patient p = queue.poll();
            history.add(p); // add to history
            System.out.println(">>> Treating patient now...");
            System.out.println("Treated: " + p);
        }
    }

    // Display queue in order
    public void displayQueue() {
        System.out.println("=== UPDATED QUEUE ===");
        System.out.println("Patients Waiting: " + queue.size());

        if (queue.isEmpty()) {
            System.out.println("No patients in queue.");
            return;
        }

        // Convert to list for sorted display
        List<Patient> list = new ArrayList<>(queue);
        Collections.sort(list);

        int i = 1;
        for (Patient p : list) {
            System.out.println(i + ". " + p);
            i++;
        }
    }

    // Display treated patients history
    public void displayHistory() {
        System.out.println("=== TREATED PATIENTS HISTORY ===");
        if (history.isEmpty()) {
            System.out.println("No patients have been treated yet.");
        } else {
            int i = 1;
            for (Patient p : history) {
                System.out.println(i + ". " + p);
                i++;
            }
        }
    }
}

// Main class
public class EmergencyRoomSystem {
    public static void main(String[] args) {
        ERQueue er = new ERQueue();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== EMERGENCY ROOM SYSTEM ===");
            System.out.println("1. Add Patient");
            System.out.println("2. Treat Next Patient");
            System.out.println("3. Display Queue");
            System.out.println("4. View Treated Patients History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter priority (1=highest, 4=lowest): ");
                    int priority = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter condition: ");
                    String condition = sc.nextLine();

                    er.arrive(name, priority, condition);
                    System.out.println("Patient added successfully (arrival time & ID auto-set).");
                    break;

                case 2:
                    er.treatNext();
                    break;

                case 3:
                    er.displayQueue();
                    break;

                case 4:
                    er.displayHistory();
                    break;

                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}

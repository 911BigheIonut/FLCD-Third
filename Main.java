package code;



import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton();
        finiteAutomaton.readFromFile("src/fa.in");

        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Everything");
            System.out.println("2. Set of States");
            System.out.println("3. Alphabet");
            System.out.println("4. Transitions");
            System.out.println("5. Initial State");
            System.out.println("6. Set of Final States");
            System.out.println("7. Check if it's dfa");
            System.out.println("8. Verify Sequence");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    finiteAutomaton.displayMenu();
                    break;
                case 2:
                    System.out.println("States " + finiteAutomaton.getStates());
                    break;
                case 3:
                    System.out.println("Alphabet: " + finiteAutomaton.getAlphabet());
                    break;
                case 4:
                    System.out.println("Transitions: " + finiteAutomaton.getTransitions());
                    break;
                case 5:
                    System.out.println("Initial State: " + finiteAutomaton.getInitialState());
                    break;
                case 6:
                    System.out.println("Set of Final States: " + finiteAutomaton.getFinalStates());
                    break;
                case 7:
                    boolean isDFA = finiteAutomaton.isDFA();
                    System.out.println("Is it a DFA? " + (isDFA ? "Yes" : "No"));
                    break;
                case 8:
                    System.out.print("Enter the sequence to verify: ");
                    String sequence = scanner.next();
                    boolean isAccepted = finiteAutomaton.verifySequence(sequence);
                    System.out.println("Sequence \"" + sequence + "\" is " + (isAccepted ? "accepted" : "rejected"));
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

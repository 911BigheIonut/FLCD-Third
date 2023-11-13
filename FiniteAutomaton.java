package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FiniteAutomaton {
    private Set<String> states;
    private Set<String> alphabet;
    private Set<String> transitions;
    private String initialState;
    private Set<String> finalStates;

    public FiniteAutomaton() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        transitions = new HashSet<>();
        finalStates = new HashSet<>();
    }

    public void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("States:")) {
                    readSet(line.substring(8), states);
                } else if (line.startsWith("Alphabet:")) {
                    readSet(line.substring(10), alphabet);
                } else if (line.startsWith("Initial:")) {
                    initialState = line.substring(8).trim();
                } else if (line.startsWith("Final:")) {
                    readSet(line.substring(6), finalStates);
                } else if (line.startsWith("Transitions:")) {
                    readTransitions(br);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSet(String line, Set<String> set) {
        String[] elements = line.trim().split("\\s+");
        for (String element : elements) {
            set.add(element);
        }
    }

    private void readTransitions(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            transitions.add(line.trim());
        }
    }

    public void displayMenu() {
        System.out.println("1. Set of States: " + states);
        System.out.println("2. Alphabet: " + alphabet);
        System.out.println("3. Transitions: " + transitions);
        System.out.println("4. Initial State: " + initialState);
        System.out.println("5. Set of Final States: " + finalStates);
    }


    public boolean isDFA() {
        // Check for each state
        for (String state : states) {
            Set<String> symbolsSeen = new HashSet<>();

            // Check for each transition from the current state
            for (String transition : transitions) {
                String[] parts = transition.split(" ");

                // Check if the transition starts from the current state
                if (parts[0].equals(state)) {
                    String symbol = parts[1];

                    // Check if the symbol has been seen before
                    if (symbolsSeen.contains(symbol)) {
                        return false; // Symbol occurs more than once for the current state
                    }

                    symbolsSeen.add(symbol);
                }
            }
        }

        return true; // Passed all checks, it is a DFA
    }



    // Function to verify whether a given sequence is accepted by the DFA
    public boolean verifySequence(String sequence) {
        if (sequence.isEmpty()) {
            // If the sequence is empty, check if the initial state is one of the final states
            return finalStates.contains(initialState);
        }
        String currentState = initialState;

        for (char symbol : sequence.toCharArray()) {
            String transition = currentState + " " + symbol;
            String transitionaux = currentState + " " + symbol + " " + getDestinationState(transition);
            transition = transitionaux;
            transitionaux = currentState + " " + symbol;
            // Check if a valid transition exists for the current state and input symbol
            if (!transitions.contains(transition)) {
                // No valid transition, reject the sequence
                return false;
            }

            // Update the current state based on the transition
            currentState = getDestinationState(transitionaux);

            // If currentState is null, it means there's no valid transition for the symbol
            if (currentState == null) {
                return false;
            }
        }

        // Check if the final state is reached after processing the entire sequence
        return finalStates.contains(currentState);
    }

    // Helper function to get the destination state from a transition
    private String getDestinationState(String transition) {
        // Assuming the transitions are in the form "currentState symbol nextState"
        for (String t : transitions) {
            String[] parts = t.split(" ");
            if (parts[0].equals(transition.split(" ")[0]) && parts[1].equals(transition.split(" ")[1])) {
                return parts[2];
            }
        }
        return null;
    }


    public Set<String> getAlphabet() {
        return alphabet;
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<String> getTransitions() {
        return transitions;
    }

    public String getInitialState() {
        return initialState;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }
}

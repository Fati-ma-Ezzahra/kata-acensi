package com.prisonersdilemma;

import com.prisonersdilemma.domain.InterrogationSimulator;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\uFE0F\uD83D\uDC6E Welcome to prisoners dilemma! \uFE0F\uD83D\uDC6E");
        boolean tryAgain = true;
        InterrogationSimulator interrogationSimulator = new InterrogationSimulator();
        Scanner scanner = new Scanner(System.in);
        while (tryAgain){
            try {
                List<Integer> result = interrogationSimulator.start();
                System.out.println("\uD83D\uDCCA InterrogationSimulator results: ");
                System.out.println("Suspect 1: " + result.get(0));
                System.out.println("Suspect 2: " + result.get(1));
                // Ask the user if they want to continue
                System.out.print("Do you want to try again? (y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();

                // Check if the user wants to try again
                if (!response.equals("y")) {
                    tryAgain = false; // Exit the loop if the user doesn't want to continue
                    System.out.println("Exiting the program...");
                }
            } catch (InvalidChoiceHistory e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }
}
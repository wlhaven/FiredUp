package edu.pcc.cis233j.FiredUp;

import java.util.*;

/**
 * FiredUpDB Project
 * FiredUpUI class.  Provides a text based user interface for interacting with the FiredUP database
 *
 * @author Wally Haven
 * @version 12/16/2016
 *          -refactored PrintCustomerDetail to remove switch logic and simplify the method.
 *          -create new method checkState() to validate that state entry is a valid state.
 *          -modified printCustomerInfo to validate that input is a valid state
 *          -modified printCustomerInfo to return info to the user that no customers exist for that state
 *          -added validateState array to hold list of valid state entries.
 * @since 12/08/2016 - initial version
 */

class FiredUpUI {
    private Scanner scanner;
    private final String[] validStates = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
            "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
            "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA",
            "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WI", "WY"};

    private final FiredUpDB firedUp = new FiredUpDB();

    FiredUpUI() {
        scanner = new Scanner(System.in);
    }

    /**
     * Menu providing various queries for the use to run.
     */
    void Menu() {
        String choice = null;

        while (!Objects.equals(choice, "5")) {
            System.out.println("\n\t\tFIREDUP DATABASE QUERY PROGRAM:");
            System.out.println("\nPlease choose an option from the choices below: ");
            System.out.println("\t1. Retrieve customer and email information by state.");
            System.out.println("\t2. Retrieve customer and phone information by state.");
            System.out.println("\t3. Retrieve customer, email, and phone information by state.");
            System.out.println("\t4. Retrieve all part details for a part.");
            System.out.println("\t5. Quit ");
            switch (choice = getUserString()) {
                case "1":
                    printCustomerInfo("eMail");
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case "2":
                    printCustomerInfo("Phone");
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case "3":
                    printCustomerInfo("Both");
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case "4":
                    printPartInfo();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case "5":
                    System.out.println("\nGoodbye and have a wonderful day!");
                    return;
                default:
                    System.out.println("Unknown value. Please try again.");
            }
        }
    }

    /**
     * Prints the customer data for the selected query chosen by the user
     *
     * @param type value to identify how output should be formatted.
     */
    private void printCustomerInfo(String type) {
        String input;

        System.out.println("\nEnter the two letter State abbreviation");
        input = checkState();
        if (input.equals("Invalid")) {
            System.out.println("Invalid two letter State abbreviation.  Please retry");
            return;
        }
        List<Customer> customers = firedUp.readCustomers(input);
        if (customers.isEmpty()) {
            System.out.println("No customers from the state you queried exist in the database. Please try again. ");
            return;
        }
        for (Customer cust : customers) {
            System.out.println();
            System.out.println("ID: " + cust.getId() +
                    ", Name: " + cust.getName() +
                    ", City: " + cust.getCity() +
                    ", State: " + cust.getState());
            switch (type) {
                case "Phone":
                    printCustomerDetail("Phone Number:", cust.getPhoneNumber());
                    break;
                case "eMail":
                    printCustomerDetail("Email:", cust.getEmailAddresses());
                    break;
                default:
                    printCustomerDetail("Phone Number:", cust.getPhoneNumber());
                    printCustomerDetail("Email:", cust.getEmailAddresses());
                    break;
            }
        }
    }

    /**
     * Prints additional customer details for the customer list
     *
     * @param label    Identifies which detail to print
     * @param customer list of customers
     */
    private void printCustomerDetail(String label, ArrayList<String> customer) {
        if (customer.isEmpty()) {
            System.out.println(label + " N/A");
        } else {
            System.out.println(label + "  " + customer);
        }
    }

    /**
     * Prints all the part information to the screen for Parts query
     */
    private void printPartInfo() {
        double input;

        System.out.println("\nEnter minimum price for a part: ");
        input = getUserDouble();
        List<Part> parts = firedUp.readParts(input);
        for (Part part : parts) {
            System.out.println();
            System.out.println("Part Number: " + part.getPartNbr() +
                    "\nDescription: " + part.getDescription() +
                    "\nCost: $" + part.getCost() +
                    "\nSales Price: $" + part.getSalesPrice() +
                    "\nAverage Quantity " + String.format("%.02f", part.getAvgQuantity()));
        }
    }

    /**
     * Gets a string from the user
     *
     * @return String value to uppercase
     */
    private String getUserString() {
        String inputLine;
        scanner = new Scanner(System.in);

        System.out.print("> ");     // print prompt
        inputLine = scanner.nextLine();
        return inputLine;
    }

    /**
     * Input validation: Checks that expected value is the correct type.  Prompts user to reenter value
     * until type matches expected type
     *
     * @return double value to calling method
     */
    private Double getUserDouble() {
        System.out.print("> ");

        for (; ; ) {
            Scanner scanner = new Scanner(System.in);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Input was not a number. Please retry.");
            }
        }
    }

    /**
     * Input validation: Checks that state/province is a valid value
     *
     * @return the state as a uppercase string  or "Invalid" if state is not a valid entry
     */
    private String checkState() {
        String inputLine;
        inputLine = getUserString();
        inputLine = inputLine.toUpperCase();
        for (String states : validStates) {
            if (inputLine.equals(states)) {
                return inputLine;
            }
        }
        return "Invalid";
    }
}



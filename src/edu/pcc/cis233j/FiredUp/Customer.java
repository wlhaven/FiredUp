package edu.pcc.cis233j.FiredUp;

import java.util.ArrayList;

/**
 * FiredUp Database project
 * Class to hold Customer information
 *
 * @author Wally Haven
 * @version 12/08/2016
 */
class Customer {
    private final int id;
    private final String name;
    private final String city;
    private final String state;
    private final ArrayList<String> emailAddresses;
    private final ArrayList<String> phoneNumber;

    Customer(int id, String name, String city, String state) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        emailAddresses = new ArrayList<>();
        phoneNumber = new ArrayList<>();
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getCity() {
        return city;
    }

    String getState() {
        return state;
    }

    ArrayList<String> getEmailAddresses() {
        return emailAddresses;
    }

    ArrayList<String> getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Add email data to an ArrayList
     *
     * @param email Email address from the database
     */
    void addEmailAddress(String email) {
        emailAddresses.add(email);
    }

    /**
     * Add phone data to an ArrayList
     *
     * @param phone Phone number from the database
     */
    void addPhoneNumber(String phone) {
        phoneNumber.add(phone);
    }
}

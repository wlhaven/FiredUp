package edu.pcc.cis233j.FiredUp;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * FiredUp Database project
 * FiredUPDB Class to connect to database and retrieve the requested data
 *
 * @author Wally Haven
 * @version 12/16/2016
 *          -removed logic to return error message from readCustomerBasics and put into FireupUI class.
 * @since 12/12/2016
 *          -Changed method readCustomerBasics to check to see if state value entered returns any rows.
 *          if not, then prints error message and returns user back to main menu
 *          -Changed readEmailAddresses method and readPhoneNumber method to check to see if customer list
 *          is populated. If not, returns from method without creating an unnecessary connection to the
 *          database as there are no records to retrieve.
 * @since 12/08/2016 - initial version
 */
class FiredUpDB {

    private static final String FIREDUP_DB = "jdbc:sqlite:FiredUp.db";

    private static final String CUSTOMER_SQL = "SELECT CustomerID, Name, City, StateProvince FROM CUSTOMER WHERE StateProvince = ?";

    private static final String EMAIL_SQL = "SELECT EMailAddress FROM EMAIL WHERE FK_CustomerID = ?;";

    private static final String PHONE_SQL = "SELECT PhoneNbr FROM PHONE WHERE FK_CustomerID = ?;";

    private static final String PART_SQL = "SELECT PartNbr, Description, Cost, SalesPrice, AVG(Quantity) AS 'AvgQuantity'" +
            "FROM PART, PO_LINE_ITEM WHERE PartNbr = FK_PartNbr AND Cost > ?" +
            "GROUP BY PartNbr, Description, Cost, SalesPrice ORDER BY Cost;";


    /**
     * @return a connection to the FiredUp database
     * @throws SQLException if unable to connect
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(FIREDUP_DB);
    }

    /**
     * Read all customers from the FiredUp database and return them as a list of Customer objects
     *
     * @param input State information used in the query
     * @return a list of customers from the FiredUp database
     */
    public List<Customer> readCustomers(String input) {
        ArrayList<Customer> customers = readCustomerBasics(input);
        readEmailAddresses(customers);
        readPhoneNumber(customers);
        return customers;
    }

    /**
     * Read all parts from the FiredUp database and return them as a list of Part objects
     *
     * @param input price value provided by the user
     * @return a list of parts from the FiredUp database
     */
    public List<Part> readParts(double input) {
        return readPartBasics(input);
    }

    /**
     * Read customers from the database, including their basic properties from the Part table,
     * including part data from related table PO_LINE_ITEM
     *
     * @param input price of the part to pass to SQL query
     * @return return a list of Part objects
     */
    private ArrayList<Part> readPartBasics(double input) {
        ArrayList<Part> parts = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(PART_SQL)) {
            stmt.setDouble(1, input);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                parts.add(new Part(rs.getInt("PartNbr"),
                        rs.getString("Description"),
                        rs.getDouble("Cost"),
                        rs.getDouble("SalesPrice"),
                        rs.getDouble("AvgQuantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parts;
    }

    /**
     * Read customers from the database, including their basic properties from the CUSTOMER table,
     * but not including customer data from related tables such as email addresses or phone numbers
     *
     * @return a list of Customer objects
     */
    private ArrayList<Customer> readCustomerBasics(String input) {
        ArrayList<Customer> customers = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(CUSTOMER_SQL)) {
            stmt.setString(1, input);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("CustomerID"),
                        rs.getString("Name"),
                        rs.getString("City"),
                        rs.getString("StateProvince")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * If there are customers in the list, read email addresses from the database for
     * each customer in the given list, adding the email addresses found to the corresponding
     * Customer object
     *
     * @param customers list of customers whose email addresses should be read
     */
    private void readEmailAddresses(ArrayList<Customer> customers) {
        if (customers.isEmpty()) {
            return;
        }
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(EMAIL_SQL)) {
            for (Customer cust : customers) {
                stmt.setInt(1, cust.getId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    cust.addEmailAddress(rs.getString("EMailAddress"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * If there are customers in the list, read phone number(s) from the database
     * for each customer in the given list and add the phone numbers found to the
     * corresponding Customer object
     *
     * @param customers list of customers whose phone numbers should be read
     */
    private void readPhoneNumber(ArrayList<Customer> customers) {
        if (customers.isEmpty()) {
            return;
        }
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(PHONE_SQL)) {
            for (Customer cust : customers) {
                stmt.setInt(1, cust.getId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    cust.addPhoneNumber(rs.getString("PhoneNbr"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

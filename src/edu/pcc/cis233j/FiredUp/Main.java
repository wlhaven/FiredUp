package edu.pcc.cis233j.FiredUp;

/**
 * FiredUp Database project
 * Project will present a text based UI allowing user to choose various Sql Queries that will return
 * the requested information from a SQLite database called FiredUP
 *
 * @author Wally Haven
 * @version 12/08/2016
 */
class Main {
    public static void main(String[] args) {
        FiredUpUI FiredUI = new FiredUpUI();
        FiredUI.Menu();
    }
}

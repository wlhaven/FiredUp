package edu.pcc.cis233j.FiredUp;

/**
 * FiredUp Databases Project
 * Parts Class to hold part information
 *
 * @author Wally Haven
 * @version 12/8/2016
 */

class Part {
    private final int partNbr;
    private final String description;
    private final double cost;
    private final double salesPrice;
    private final double avgQuantity;

    Part(int partNbr, String description, double cost, double salesPrice, double avgQuantity) {
        this.partNbr = partNbr;
        this.description = description;
        this.cost = cost;
        this.salesPrice = salesPrice;
        this.avgQuantity = avgQuantity;
    }

    int getPartNbr() {
        return partNbr;
    }

    String getDescription() {
        return description;
    }

    double getCost() {
        return cost;
    }

    double getSalesPrice() {
        return salesPrice;
    }

    double getAvgQuantity() {
        return avgQuantity;
    }
}

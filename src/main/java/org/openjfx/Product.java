package org.openjfx;

import java.text.DecimalFormat;

/**
 * Sinead Cahill
 *
 * A product in a vending machine.
 *
 * Functions:
 *      + getProductName() -> return String
 *      + getPrice() -> return double
 *      + getLocation() -> return String
 *      + getGridLocation(String) -> return int
 *      + getQuantity() -> return int
 *      + setQuantity(int) -> void
 *      + equals(Object other) -> return boolean
 *      + toString() -> return String
 */
public class Product {
    private String productName, location;
    private double price;
    private int quantity;

    /**
     * Constructs a Product object
     * @param productName the name of the product
     * @param price the price of the product
     * @param location the location of the product
     * @param quantity the quantity amount of the product
     */
    public Product(String productName, double price, String location, int quantity){
        this.productName = productName;
        this.price = price;
        this.location = location;
        this.quantity = quantity;
    }

    /**
     * Gets the name of the product.
     * @return the product name
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * Gets the price of the product
     * @return the products price
     */
    public double getPrice() { return price; }

    /**
     * Gets the product location in machine.
     * @return the location of the product
     */
    public String getLocation() { return location; }

    /**
     * Gets the product column/row location on grid
     * @param pos position required (col or row)
     * @return the row/column location of th product
     */
    public int getGridLocation(String pos) {
        int value;

        if (pos.equalsIgnoreCase("row")){
            if (location.charAt(0) == 'A') {
                value = 0;
            } else if (location.charAt(0) == 'B') {
                value = 1;
            } else {
                value = 2;
            }
        }else{
            value = Integer.parseInt(String.valueOf(location.charAt(1)))-1;
        }
        return value;
    }


    /**
     * Gets the product quantity.
     * @return the product quantity
     */
    public int getQuantity() { return quantity; }


    /**
     * Set Product Quantity
     * @param newQuantity value of new quantity
     */
    public void setQuantity(int newQuantity) { quantity = newQuantity; }


    /**
     * Determines if this product is the same as the other product.
     * @param other the other product
     * @return true if the products are equal, false otherwise
     */
    public boolean equals(Object other){
        if (other == null) { return false;}
        Product b = (Product) other;
        return productName.equals(b.productName) && price == b.price;
    }

    /**
     * Formats the product's name, price & quantity.
     * @return new string format
     */
    public String toString(){
        DecimalFormat df = new DecimalFormat("####0.00");
        return productName + "\nPrice: €" + df.format(price) + "\n\n" + quantity + " Available";
    }

}

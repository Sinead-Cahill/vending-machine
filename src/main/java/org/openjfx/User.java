package org.openjfx;

/**
 * Sinead Cahill
 *
 * A Vending Machine User - Client
 *
 * Functions:
 *      + getUsername() -> return String
 *      + getPassword() -> return int
 *      + getCredit() -> return double
 *      + setCredit(double) -> void
 *      + validate(String, int) -> boolean
 */
public class User {
    private double credit;
    private int password;
    private String username;

    /**
     * Constructs User Object
     * @param name the users name
     * @param password the users password
     * @param credit the users credit balance
     */
    public User(String name, int password, double credit){
        this.username = name;
        this.password = password;
        this.credit = credit;
    }

    /**
     * Gets the users username
     * @return the username
     */
    public String getUsername(){ return username; }

    /**
     * Gets the users password
     * @return the password
     */
    public int getPassword(){ return password; }

    /**
     * gets the users credit
     * @return the credit value
     */
    public double getCredit() { return credit; }

    /**
     * Sets the users credit
     * @param newCredit new credit value
     */
    public void setCredit(double newCredit) { credit = newCredit; }

    /**
     * Determines if this user's name and password is the same as another.
     * @param name the other username
     * @param pass the other password
     * @return true if username and password is equal, false otherwise
     */
    public boolean validate(String name, int pass){
        return this.username.equals(name) && this.password == pass;
    }
}

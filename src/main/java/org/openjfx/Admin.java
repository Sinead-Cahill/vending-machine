package org.openjfx;

/**
 * Sinead Cahill
 *
 * A Vending Machine Admin
 */
public class Admin extends User {

    /**
     * Constructs Admin Object
     * @param username the users name
     * @param password the users password
     */
    public Admin (String username, int password){
        super(username, password, -1);
    }
}

package org.openjfx;

/**
 * Sinead Cahill
 *
 * Throws Exception in Vending Machine
 */
public class VendingException extends RuntimeException {

    /**
     * Constructs Vending Machine Exception
     * @param reason exception reason
     */
    public VendingException(String reason) { super(reason); }

}

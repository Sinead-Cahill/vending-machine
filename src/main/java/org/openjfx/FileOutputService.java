package org.openjfx;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Sinead Cahill
 *
 * File Writer Class
 *
 * Functions:
 *      + writeClientsFile(ArrayList<User>) -> void
 *      + writeProductsFile(ArrayList<Product>) -> void
 *      + writeAdminsFile(ArrayList<Admin>) -> void
 */
public class FileOutputService {
    private PrintWriter writer;
    private FileOutputStream byteStream;

    String line;
    DecimalFormat df;

    public FileOutputService(){
        writer = null;
        df = new DecimalFormat("####0.00");
    }

    /**
     * Writes back to the Clients data file
     * @param clients client ArrayList
     */
    public void writeClientsFile(ArrayList<User> clients) throws Exception {
        try{
            byteStream = new FileOutputStream(".\\files\\Clients.txt");
            writer = new PrintWriter(new OutputStreamWriter(byteStream, "UTF8"));

            for(User c: clients){
                line = c.getUsername() + ", " + df.format(c.getCredit()) + ", " + c.getPassword();
                writer.println(line);
            }
        }
        finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    /**
     * Writes back in the Product data file
     * @param products Product ArrayList
     */
    public void writeProductsFile(ArrayList<Product> products) throws Exception{
        try {
            byteStream = new FileOutputStream(".\\files\\Products.txt");
            writer = new PrintWriter(new OutputStreamWriter(byteStream, "UTF8"));

            for(Product p: products){
                line = p.getProductName()+ ", " + p.getLocation() + ", " + p.getPrice()+ ", " + p.getQuantity();
                writer.println(line);
            }
        }
        finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    /**
     * Writes back in the Admins data file
     * Not Currently Required
     * @param admins Admin ArrayList
     */
    public void writeAdminsFile(ArrayList<Admin> admins) throws Exception{
        try {
            byteStream = new FileOutputStream(".\\files\\Admins.txt");
            writer = new PrintWriter(new OutputStreamWriter(byteStream, "UTF8"));

            for(Admin a: admins){
                line = a.getUsername() + ", " + a.getPassword();
                writer.println(line);
            }
        }
        finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}

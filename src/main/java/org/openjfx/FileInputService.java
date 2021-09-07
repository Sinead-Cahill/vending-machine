package org.openjfx;

import java.io.*;
import java.util.ArrayList;

/**
 * Sinead Cahill
 *
 * File Reader Class
 *
 * Functions:
 *      + readClientsFile() -> return ArrayList<User>
 *      + readAdminsFile() -> return ArrayList<Admin>
 *      + readProductsFile() -> return ArrayList<Product>
 */
public class FileInputService {

    private BufferedReader reader;
    private FileInputStream byteStream;

    private String username, line;
    private double credit;
    private int password;

    public FileInputService(){
        reader = null;
    }

    /**
     * Reads the Clients data file
     * @return User object ArrayList
     */
    public ArrayList<User> readClientsFile() throws Exception {
        try{
            byteStream = new FileInputStream(".\\files\\Clients.txt");
            reader = new BufferedReader(new InputStreamReader(byteStream, "UTF8"));

            ArrayList<User> clients = new ArrayList<User>();

            while((line = reader.readLine()) != null){
                username = line.substring(0, line.indexOf(","));
                line = line.substring(username.length()+2); //starting string after username, comma and space to easily read credit amount
                credit = Double.parseDouble(line.substring(0, line.indexOf(",")));
                password = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));

                clients.add (new User(username, password, credit)); //creating client user object
            }
            return clients;
        }
        finally {
            if(reader != null){
                reader.close();
            }
        }
    }

    /**
     * Reads the Admins data file
     * @return Admin object ArrayList
     */
    public ArrayList<Admin> readAdminsFile() throws Exception{
        try {
            byteStream = new FileInputStream(".\\files\\Admins.txt");
            reader = new BufferedReader(new InputStreamReader(byteStream, "UTF8"));

            ArrayList<Admin> admins = new ArrayList<Admin>();

            while((line = reader.readLine()) != null){
                username = line.substring(0, line.indexOf(","));
                password = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));

                admins.add (new Admin(username, password)); //creating admin object
            }
            return admins;
        }
        finally {
            if(reader != null){
                reader.close();
            }
        }
    }

    /**
     * Reads the Product data file
     * @return Product object ArrayList
     */
    public ArrayList<Product> readProductsFile() throws Exception{
        try {
            byteStream = new FileInputStream(".\\files\\Products.txt");
            reader = new BufferedReader(new InputStreamReader(byteStream, "UTF8"));

            ArrayList<Product> products = new ArrayList<Product>();

            String name, location;
            int quantity;
            double price;

            while((line = reader.readLine()) != null){
                name = line.substring(0, line.indexOf(","));
                line = line.substring(name.length()+2); //starting string after username, comma and space to easily read location

                location = line.substring(0, line.indexOf(","));
                line = line.substring(location.length()+2); //starting string after location, comma and space to easily read price amount

                price = Double.parseDouble(line.substring(0, line.indexOf(",")));
                quantity = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));

                products.add (new Product(name, price, location, quantity)); //creating product object
            }
            return products;
        }
        finally {
            if(reader != null){
                reader.close();
            }
        }
    }
}

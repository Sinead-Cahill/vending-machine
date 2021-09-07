package org.openjfx;
import java.util.ArrayList;

/**
 * Sinead Cahill
 *
 * A vending machine
 *
 * Functions:
 *      + getProductTypes() -> return Product[]
 *      + buyProduct(Product) -> void
 *      + reloadProduct(Product, int) -> void
 *      + updateFiles() -> void
 */
public class VendingMachine {
    private ArrayList<Product> products;
    private ArrayList<User> clientsList;
    private User client;
    private FileOutputService writeFile;

    private final int machineCapacity = 9; // 3x3 grid

    /**
     * Constructs a VendingMachine object.
     */
    public VendingMachine(ArrayList<Product> products, ArrayList<User> clientsList, User client) {
        //Initialising Variables
        this.products = products; //Product List
        this.clientsList = clientsList; //Client List
        this.client = client; //Specific client logged in

        writeFile = new FileOutputService();
    }

    /**
     * Gets the type of products in the vending machine.
     * @return an array of products sold in this machine.
     */
    public Product[] getProductTypes() {

        Product[] types = new Product[machineCapacity];

        for (int i = 0; i < machineCapacity; i++) {
            types[i] = products.get(i);
        }
        return types;
    }

    /**
     * Buys a product from the vending machine.
     * @param p the product object
     */
    public void buyProduct(Product p) {

        for(Product prod : products) {
            if (prod.equals(p)) {
                double credit = client.getCredit();

                if (p.getPrice() <= credit) {
                    p.setQuantity(p.getQuantity() - 1);
                    client.setCredit(client.getCredit() - p.getPrice());
                } else {
                    throw new VendingException("Insufficient Credit");
                }
            }
        }
    }

    /**
     * Adds a product to the vending machine.
     * @param p the product object
     * @param quantity the amount to add back in
     */
    public void reloadProduct(Product p, int quantity){
        p.setQuantity(p.getQuantity() + quantity);
    }


    /**
     * Writes Product and Client Files
     * @throws Exception
     */
    public void updateFiles() throws Exception {
        writeFile.writeProductsFile(products);

        if(clientsList != null) {
            writeFile.writeClientsFile(clientsList);
        }
    }

}

package org.openjfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.text.DecimalFormat;

/**
 * Sinead Cahill
 *
 * Client Menu Screen
 *
 * Functions:
 *      + displayMenu() -> void
 *      - displayProducts() -> return GridPane
 *      - selectedProduct(Button) -> void
 *      - displayMessageInfo() -> return VBox
 *      - buyProduct()-> void
 *      + quit() -> void
 */
public class StandardMenu extends Pane implements Menu {
    private VendingMachine machine;
    private User client;
    private Product selectedProduct;

    private Label  credit, responseMessage;
    private Button lastBtn;
    private DecimalFormat df;
    private boolean isSelected;
    private String mainBtnStyle, selectedBtnStyle;

    /**
     * Constructs a Client Menu
     */
    public StandardMenu(VendingMachine machine, User client){
        //Initialising Variables
        this.machine = machine;
        this.client = client;

        df = new DecimalFormat("####0.00");
        isSelected = false;

        // Un/selected button styles
        mainBtnStyle = "-fx-background-color: #415A77; -fx-background-radius: 5; -fx-effect: innershadow(gaussian, rgba(74, 123, 157, 1.0), 10, 0, 7, 7);-fx-font: 18 Verdana;";
        selectedBtnStyle = "-fx-background-color: #1B263B; -fx-background-radius: 5; -fx-font: 18 Verdana;";
    }

    /**
     * Displays Menu Screen (excluding logout button)
     */
    public void displayMenu(){
        HBox displayContainer = new HBox(30);

        GridPane productGrid = displayProducts(); //Product Buttons
        productGrid.setHgap(10);
        productGrid.setVgap(15);

        VBox notificationContainer = displayMessageInfo(); //Client Message Information

        displayContainer.getChildren().addAll(productGrid, notificationContainer);
        displayContainer.setPadding(new Insets(70, 0, 0, 35));

        getChildren().add(displayContainer);
    }

    /**
     * Creates the Product Buttons
     * @return GridPane containing Product Buttons
     */
    private GridPane displayProducts(){
        GridPane grid = new GridPane();

        int i = 0;
        //Create Product List into Buttons
        for (Product p : machine.getProductTypes()) {
            Button productBtn = new Button(p.getProductName() + "\n€" + df.format(p.getPrice())); //Product Name + Price
            productBtn.setId(String.valueOf(i));
            i++;

            //Styling Button
            productBtn.setTextFill(Color.WHITE);
            productBtn.setTextAlignment(TextAlignment.CENTER);
            productBtn.setStyle(mainBtnStyle);
            productBtn.setPrefWidth(175);
            productBtn.setPrefHeight(125);

            //Set position based on Vending Machine location
            int col = p.getGridLocation("col");
            int row = p.getGridLocation("row");
            grid.add(productBtn, col, row);

            if(p.getQuantity() == 0){ //If Out of Stock
                Rectangle overlay = new Rectangle(175, 127);
                overlay.setArcWidth(5);
                overlay.setArcHeight(5);
                overlay.setStyle("-fx-opacity: 0.7; -fx-fill: #1B263B;");

                Label outOfStock = new Label("OUT OF STOCK");
                outOfStock.setStyle("-fx-font: 20 verdana; -fx-text-fill: white;");
                outOfStock.setRotate(-35);

                grid.add(new StackPane(overlay, outOfStock), col, row);
            }

            productBtn.setOnAction(e -> selectedProduct(productBtn));
        }
        return grid;
    }

    /**
     * Product Buttons Response when Selected (Button FX & Corresponding Response)
     * @param btnSelected the current selected button
     */
    private void selectedProduct(Button btnSelected){
        String productName = btnSelected.getText().substring(0, btnSelected.getText().indexOf("\n"));

        //Display Selected Product Info
        for(Product p: machine.getProductTypes()){
            if(p.getProductName().equals(productName)){
                selectedProduct = p;
                responseMessage.setText(p.toString());
            }
        }

        if(!isSelected){ //If No Button Selected
            btnSelected.setStyle(selectedBtnStyle);
            lastBtn = btnSelected;
            isSelected = true;

        }else{ //If Button Already Selected
            lastBtn.setStyle(mainBtnStyle); //change last selected button fx

            if(lastBtn.equals(btnSelected)){ //If Same Button Selected
                responseMessage.setText("Please Select a Product");
                isSelected = false; //deselect button
                selectedProduct = null;

            }else{ //If Different Button Selected
                btnSelected.setStyle(selectedBtnStyle);
                lastBtn = btnSelected;
            }
        }
    }

    /**
     * Creates the Client Menu Info Screen (Client Info, Response Messages & Buy Btn)
     * @return VBox containing Menu Information Screen
     */
    private VBox displayMessageInfo(){
        //Notification Background FX
        Rectangle notificationBG = new Rectangle(300, 340);
        notificationBG.setStyle("-fx-fill: linear-gradient(#0D1B2A, #415A77);");

        //Welcome User
        Label welcome = new Label("Hello " + client.getUsername() + "!");
        welcome.setStyle("-fx-font: 25 Verdana; -fx-text-fill: white;");

        //Display Credit Balance
        credit = new Label("Balance: €" + df.format(client.getCredit()));
        credit.setStyle("-fx-font: 18 Verdana; -fx-text-fill: white;");

        VBox clientDetails = new VBox(5); //Welcome + Client Details Container
        clientDetails.getChildren().addAll(welcome, credit);
        clientDetails.setAlignment(Pos.CENTER);

        //Response Message FX -> Product Info / Message Updates
        Rectangle responseBG = new Rectangle(225, 150);
        responseBG.setFill(Color.WHITE);

        responseMessage = new Label("Please Select a Product");
        responseMessage.setWrapText(true);
        responseMessage.setMaxSize(responseBG.getWidth(), responseBG.getHeight()); //Keep text in Rectangle BG
        responseMessage.setStyle("-fx-font: 20 Verdana;-fx-text-alignment: center;");
        responseMessage.setAlignment(Pos.CENTER);

        BorderPane infoMessages = new BorderPane(); //Client Info + Response Message Container
        infoMessages.setTop(clientDetails);
        infoMessages.setMargin(clientDetails, new Insets(50, 0, 0, 0));
        infoMessages.setCenter(new StackPane(responseBG, responseMessage));

        //Purchase Button FX
        Button purchaseBtn = new Button("Purchase");
        purchaseBtn.setPrefSize(100, 50);
        purchaseBtn.setStyle("-fx-background-color: linear-gradient(#FF4000, #b32d00); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 5, 0, 0, 1); -fx-font: 15 Verdana; -fx-text-fill: white;");

        purchaseBtn.setOnAction(e -> buyProduct());

        HBox purchaseBtnHBox = new HBox(); //Purchase Button Container
        purchaseBtnHBox.getChildren().add(purchaseBtn);
        purchaseBtnHBox.setAlignment(Pos.CENTER); //Align purchase button

        VBox container = new VBox(10); //Contains all menu information elements
        container.getChildren().addAll(new StackPane(notificationBG, infoMessages), purchaseBtnHBox);
        return container;
    }

    /**
     * Response to selecting Purchase Button
     * if valid, buys product
     * otherwise updates response message accordingly
     */
    private void buyProduct(){
        if(selectedProduct != null){
            if (selectedProduct.getPrice() > client.getCredit()) {
                responseMessage.setText("Insufficient Credit");
            } else {
                machine.buyProduct(selectedProduct);
                responseMessage.setText("Thank You");
                credit.setText("Balance: €" + df.format(client.getCredit())); //Update credit balance

                if(selectedProduct.getQuantity() == 0){
                    displayMenu(); //update out of stock buttons
                }
            }
            lastBtn.setStyle(mainBtnStyle);
            isSelected = false;

        }else{
            responseMessage.setText("No Product Selected");
        }
    }

    /**
     * Logs Client out of Account & machine updates
     */
    @Override
    public void quit() {
        try {
            machine.updateFiles(); //updates files
        } catch (Exception anError) {
            anError.printStackTrace();
        }

    }
}

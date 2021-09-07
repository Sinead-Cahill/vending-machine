package org.openjfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

/**
 * Sinead Cahill
 *
 * Admin Menu Screen
 *
 * Functions:
 *      + displayMenu() -> void
 *      - displayProducts() -> return GridPane
 *      - selectedProduct(Button) -> void
 *      - displayMessageInfo() -> return VBox
 *      - displayReloadCounter() -> void
 *      - isCounterValid() -> return boolean
 *      - reloadProduct() -> void
 *      + quit() -> void
 */
public class AdminMenu extends Pane implements Menu {
    private VendingMachine machine;
    private User admin;
    private Product selectedProduct;

    private Label counterLabel, responseMessage;
    private Button addBtn, subtractBtn, lastBtn;
    private BorderPane counterPane;

    private int reloadCounter;
    private boolean isSelected;
    private String mainBtnStyle, selectedBtnStyle;

    final int productCapacity = 10;

    /**
     * Constructs a Admin Menu
     */
    public AdminMenu(VendingMachine machine, User admin){
        //Initialising Variables
        this.machine = machine;
        this.admin = admin;

        reloadCounter = 0;
        isSelected = false;

        // Un/selected button styles
        mainBtnStyle = "-fx-background-color: #415A77; -fx-background-radius: 5; -fx-effect: innershadow(gaussian, rgba(74, 123, 157, 1.0), 10, 0, 7, 7);-fx-font: 18 Verdana;";
        selectedBtnStyle = "-fx-background-color: #1B263B; -fx-background-radius: 5; -fx-font: 18 Verdana;";
    }

    /**
     * Displays Menu Screen (excluding logout button)
     */
    @Override
    public void displayMenu(){
        HBox displayContainer = new HBox(30);

        GridPane productGrid = displayProducts(); //Product Buttons
        productGrid.setHgap(10);
        productGrid.setVgap(15);

        VBox notificationContainer = displayMessageInfo(); //Admin Message Information

        displayContainer.getChildren().addAll(productGrid, notificationContainer);
        displayContainer.setPadding(new Insets(70, 0, 0, 35));

        getChildren().add(displayContainer);
    }

    /**
     * Creates the Product Buttons
     * @return  GridPane containing Product Buttons
     */
    private GridPane displayProducts(){
        GridPane grid = new GridPane();

        int i = 0;
        //Create Product List into Buttons
        for (Product p : machine.getProductTypes()) {
            Button productBtn = new Button(p.getProductName() + "\nQuantity: " + p.getQuantity()); //Product Name + Quantity
            productBtn.setId(String.valueOf(i));
            i++;

            //Styling Button
            productBtn.setTextFill(Color.WHITE);
            productBtn.setTextAlignment(TextAlignment.CENTER);
            productBtn.setStyle(mainBtnStyle);
            productBtn.setPrefWidth(175);
            productBtn.setPrefHeight(125);

            //Set position based on location
            int col = p.getGridLocation("col");
            int row = p.getGridLocation("row");
            grid.add(productBtn, col, row);

            productBtn.setOnAction(e -> selectedProduct(productBtn));
        }
        return grid;
    }

    /**
     * Product Buttons Response when Selected (Button FX & Corresponding Product Details)
     * @param btnSelected the selected button
     */
    private void selectedProduct(Button btnSelected){
        String productName = btnSelected.getText().substring(0, btnSelected.getText().indexOf("\n"));

        //Get Selected Product Info
        for(Product p: machine.getProductTypes()){
            if(p.getProductName().equals(productName)){
                selectedProduct = p;
                responseMessage.setText(selectedProduct.toString());
            }
        }

        if(!isSelected){ //If No Button is currently Selected
            btnSelected.setStyle(selectedBtnStyle);
            lastBtn = btnSelected;
            isSelected = true;
            counterPane.setVisible(true);

            if(selectedProduct.getQuantity() == productCapacity){ //If Product capacity is full
                counterLabel.setText("Add: FULL");
                addBtn.setDisable(true);
                subtractBtn.setDisable(true);
            }

        }else{ //If a Button is Already Selected
            lastBtn.setStyle(mainBtnStyle); //change last selected button fx
            reloadCounter = 0;

            if(selectedProduct.getQuantity() == productCapacity){
                counterLabel.setText("Add: FULL");
                addBtn.setDisable(true);
            } else{
                counterLabel.setText("Add: " + reloadCounter);
                addBtn.setDisable(false);
            }

            subtractBtn.setDisable(true);

            if(lastBtn.equals(btnSelected)){ //If Same Button is Selected Again
                selectedProduct = null;
                responseMessage.setText("Select a Product to Reload");
                isSelected = false;
                counterPane.setVisible(false);

            }else{ //If Different Button Selected
                btnSelected.setStyle(selectedBtnStyle);
                lastBtn = btnSelected;
            }
        }
    }

    /**
     * Creates the Admin Info Screen (Product Info, Response Messages & Reload Btn)
     * @return VBox containing Menu Information Screen
     */
    private VBox displayMessageInfo(){
        displayReloadCounter(); //Displays Operation Buttons to add or subtract value

        //Notification Background FX
        Rectangle notificationBG = new Rectangle(300, 340);
        notificationBG.setStyle("-fx-fill: linear-gradient(#0D1B2A, #415A77);");

        //Welcome User
        Label welcome = new Label("Hello " + admin.getUsername() + "!");
        welcome.setStyle("-fx-font: 25 Verdana; -fx-text-fill: white;");

        //Response Message FX -> Product Info / Message Updates / Reload Counter
        Rectangle responseBG = new Rectangle(225, 200);
        responseBG.setFill(Color.WHITE);

        responseMessage = new Label("Select a Product to Reload");
        responseMessage.setStyle("-fx-font: 20 Verdana;-fx-text-alignment: center;");
        responseMessage.setWrapText(true);

        //Counter and Response Message Container
        BorderPane responseContainer = new BorderPane();
        responseContainer.setMaxSize(responseBG.getWidth(), responseBG.getHeight());
        responseContainer.setCenter(responseMessage);
        responseContainer.setBottom(counterPane);
        responseContainer.setMargin(counterPane, new Insets(0, 0, 15, 0));

        //Welcome & All Response Container
        VBox  infoContainer = new VBox(30);
        infoContainer.getChildren().addAll(welcome, new StackPane(responseBG, responseContainer));
        infoContainer.setStyle("-fx-padding: 20, 0, 0, 0; -fx-alignment: center;");

        //Reload Product Button
        Button reloadProductBtn = new Button("Reload Product");
        reloadProductBtn.setPrefSize(150, 50);
        reloadProductBtn.setStyle("-fx-background-color: linear-gradient(#FF4000, #b32d00); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 5, 0, 0, 1); -fx-font: 15 Verdana; -fx-text-fill: white;");
        reloadProductBtn.setOnAction(e -> reloadProduct());

        HBox reloadButtonHBox = new HBox();
        reloadButtonHBox.getChildren().add(reloadProductBtn);
        reloadButtonHBox.setAlignment(Pos.CENTER); //center reload button

        VBox container = new VBox(10);
        container.getChildren().addAll(new StackPane(notificationBG, infoContainer), reloadButtonHBox);
        return container;
    }

    /**
     * Creates Operation Buttons and Reload Counter Label
     */
    private void displayReloadCounter(){
        counterLabel = new Label("Add: " + reloadCounter);

        addBtn = new Button ("+");
        addBtn.setPrefSize(35, 35);
        addBtn.setStyle("-fx-background-color: green; -fx-font: 15 Verdana; -fx-text-fill: white; -fx-effect: innershadow(gaussian, rgba(0, 154, 0, 0.6), 10, 0, 7, 7);");

        subtractBtn = new Button("-");
        subtractBtn.setPrefSize(35, 35);
        subtractBtn.setStyle("-fx-background-color: red; -fx-font: 15 Verdana; -fx-text-fill: white; -fx-effect: innershadow(gaussian, rgba(255, 51, 51, 0.6), 10, 0, 7, 7);");
        subtractBtn.setDisable(true);

        addBtn.setOnAction(e -> { reloadCounter++; subtractBtn.setDisable(false);
                                  if(!isCounterValid()){ addBtn.setDisable(true); }});

        subtractBtn.setOnAction(e -> { reloadCounter--; addBtn.setDisable(false);
                                       if(!isCounterValid()){ subtractBtn.setDisable(true); }});

        HBox operationsBtns = new HBox(5); // Add/Subtract Button Container
        operationsBtns.getChildren().addAll(addBtn, subtractBtn);

        //Adding all counter elements to BorderPane
        counterPane = new BorderPane();
        BorderPane.setAlignment(counterLabel, Pos.CENTER_LEFT);
        counterPane.setRight(operationsBtns);
        counterPane.setLeft(counterLabel);
        counterPane.setPadding(new Insets(0, 30, 0, 40));
        counterPane.setVisible(false);
    }


    /**
     * Is reloadCounter value valid -> counterLabel updated accordingly
     * @return boolean -> true if current counter is valid, false otherwise
     */
    private boolean isCounterValid(){
        boolean isValid = true;

        if(addBtn.isDisabled() && subtractBtn.isDisabled()){ //if both operation buttons are disabled == Full capacity
            isValid = false;
            counterLabel.setText("Add: FULL");
        }else if(reloadCounter <= 0){ //If Counter == 0
            isValid = false;
            counterLabel.setText("Add: " + 0);
        }else if(selectedProduct.getQuantity() + reloadCounter >= productCapacity){
            isValid = false;
            counterLabel.setText("Add: " + (reloadCounter) + " (MAX)");
        }else{
            counterLabel.setText("Add: " + reloadCounter);
        }

        return isValid;
    }


    /**
     * Response to selecting Reload Button
     * if valid, updates product
     * otherwise updates response message accordingly
     */
    private void reloadProduct(){
        if(selectedProduct == null || reloadCounter == 0 || selectedProduct.getQuantity() == productCapacity){
            return;
        }else {
            machine.reloadProduct(selectedProduct, reloadCounter);  //Reload Product
            isSelected = false;

            displayMenu(); //Update product button quantity value
        }
    }

    /**
     * Shuts Down System & Files Updated
     */
    @Override
    public void quit() {
        try {
            machine.updateFiles();
            System.exit(0);
        } catch (Exception anError) {
            anError.printStackTrace();
        }
    }

}

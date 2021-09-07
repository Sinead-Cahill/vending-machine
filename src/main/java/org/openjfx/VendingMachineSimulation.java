package org.openjfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Sinead Cahill
 *
 * Vending Machine Driver Class
 *
 * Functions:
 *      + start(Stage) -> void
 *      - displayLogin(StackPane) -> void
 *      - userLoginCheck(TextField, TextField, Label) -> void
 *      - displayMenuGUI(String) -> void
 */
public class VendingMachineSimulation extends Application{
    private FileInputService readFile;

    private ArrayList<User> clients;
    private ArrayList<Admin> admins;
    private ArrayList<Product> products;

    private VendingMachine machine;
    private StandardMenu clientMenu;
    private AdminMenu adminMenu;

    private StackPane allElements, loginPane;

    public VendingMachineSimulation() throws Exception {
        //Initialising Variables
        readFile = new FileInputService(); //Read File

        clients = readFile.readClientsFile(); //Client List
        admins = readFile.readAdminsFile(); //Admin List
        products = readFile.readProductsFile(); //Product List
    }

    /**
     * Calls Login Screen & Creates Stage
     * @param primaryStage placeholder for scene
     */
    @Override
    public void start(Stage primaryStage){
        loginPane = new StackPane();
        displayLogin(loginPane);

        //Navy Background
        Rectangle background = new Rectangle(950, 550);
        background.setFill(Color.WHITE);
        background.setFill(Color.valueOf("#0D1B2A"));

        //Final Container -> All Screen Elements
        allElements = new StackPane();
        allElements.getChildren().addAll(background, loginPane);
        allElements.setAlignment(Pos.CENTER);

        // Creating the scene and place it in the stage
        Scene scene = new Scene(allElements,950, 550);
        primaryStage.setTitle("Vending Machine"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.setResizable(false); //Stage can't be resized
        primaryStage.show(); // Display the stage
    }

    /**
     * Creates Login Screen
     * @param pane contains all login screen elements
     */
    private void displayLogin(StackPane pane){
        //Welcome Title
        Label welcomeTitle = new Label("WELCOME");
        welcomeTitle.setStyle("-fx-text-fill: #0D1B2A; -fx-font: 60 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");
        HBox welcomeTitleHBox = new HBox(); // Welcome Title Container
        welcomeTitleHBox.getChildren().add(welcomeTitle);
        welcomeTitleHBox.setAlignment(Pos.CENTER);

        //Username
        Label userNameLbl = new Label("Username: ");
        userNameLbl.setStyle("-fx-text-fill: #0D1B2A;; -fx-font: 20 verdana;");
        TextField userNameInput = new TextField();

        //Password
        Label passwordLbl = new Label("Password: ");
        passwordLbl.setStyle("-fx-text-fill: #0D1B2A; -fx-font: 20 verdana;");
        TextField passwordInput = new TextField();

        //Error Message
        Label errorMessage = new Label("Incorrect Username or Password!");
        errorMessage.setStyle("-fx-text-fill: red; -fx-font: 10 verdana");
        errorMessage.setTextAlignment(TextAlignment.RIGHT);
        errorMessage.setVisible(false);
        HBox errorHBox = new HBox(); //Error Message Container
        errorHBox.getChildren().add(errorMessage);
        errorHBox.setAlignment(Pos.BOTTOM_RIGHT);

        //Login Button
        Button loginBtn = new Button("Login");
        loginBtn.setPrefSize(100, 30);

        loginBtn.setOnAction(e -> userLoginCheck(userNameInput, passwordInput, errorMessage));

        HBox loginBtnHBox = new HBox(); //Login Button Container
        loginBtnHBox.getChildren().add(loginBtn);
        loginBtnHBox.setAlignment(Pos.BOTTOM_RIGHT);

        //Login Grid Container
        GridPane loginGrid = new GridPane();
        loginGrid.setVgap(15);
        loginGrid.setAlignment(Pos.CENTER);
        //Grid Children
        loginGrid.add(welcomeTitleHBox, 0, 0, 2, 1); //col, row, colSpan, rowSpan
        loginGrid.add(userNameLbl, 0, 1);
        loginGrid.add(userNameInput, 1, 1);
        loginGrid.add(passwordLbl, 0, 2);
        loginGrid.add(passwordInput, 1, 2);
        loginGrid.add(errorHBox, 0, 3, 2, 1);
        loginGrid.add(loginBtnHBox, 0, 4, 2, 1);

        //White Background
        Rectangle loginBG = new Rectangle(600, 300);
        loginBG.setStyle("-fx-fill: white; -fx-arc-width: 50; -fx-arc-height: 50;");

        pane.getChildren().addAll(loginBG, loginGrid);
    }

    /**
     * Determines if user input is valid
     * @param username the username input
     * @param password the password input
     * @param message the error message label
     */
    private void userLoginCheck(TextField username,TextField password, Label message){

        if(username.getText().isEmpty() || password.getText().isEmpty()){ //If Input Field is Empty
            message.setVisible(true);
            if(username.getText().isEmpty() && password.getText().isEmpty()){ //Both Empty
                message.setText("Please Enter Username and Password");
            }else if(username.getText().isEmpty()) {
                message.setText("Please Enter Username");
            }else{
                message.setText("Please Enter Password");
            }
        }else{
            try{
                String userResult = "";

                for(Admin a : admins) {
                    if(a.validate(username.getText(), Integer.parseInt(password.getText()))) {
                        userResult = "Admin " + admins.indexOf(a);
                    }
                }
                for(User c: clients){
                    if(c.validate(username.getText(), Integer.parseInt(password.getText()))) {
                        userResult = "Client " + clients.indexOf(c);
                    }
                }

                if(userResult.equals("")){ //If Username or Password not found
                    message.setText("Incorrect Username or Password!");
                    message.setVisible(true);

                }else{//If Correct Login Details
                    message.setVisible(false);
                    displayMenuGUI(userResult); //Display Menu Screen
                    username.clear(); //Clear username input
                    password.clear(); //clear password input
                }
            }
            catch(NumberFormatException anError){
                message.setText("Invalid Password (0-9)");
                message.setVisible(true);
                //throw new VendingException("Invalid Password");
            }
        }
    }


    /**
     * Displays the appropriate User Menu with quit button
     * @param userStr user type and index
     */
    private void displayMenuGUI(String userStr){
        loginPane.setVisible(false); //Hide Login Screen

        BorderPane menuScreen = new BorderPane(); //Menu Screen Container

        //Quit Button
        Button quitBtn = new Button("Log Out");
        quitBtn.setPrefSize(130, 35);
        quitBtn.setStyle("-fx-background-color: #415A77; -fx-font: 15 verdana; -fx-text-fill: white;");

        String user = userStr.substring(0, userStr.indexOf(" ")); //User Type
        int index = Integer.parseInt(userStr.substring(userStr.indexOf(" ")+1)); //User Index

        if(user.equalsIgnoreCase("Admin")){
            machine = new VendingMachine(products, null, null); //Create new Vending Machine Object
            adminMenu = new AdminMenu(machine, admins.get(index)); //Create new Admin Menu Object

            adminMenu.displayMenu(); //Display Products

            menuScreen.setCenter(adminMenu);
            menuScreen.setMargin(adminMenu, new Insets(0, 0, 20, 0));

            quitBtn.setText("Shut Down");
            quitBtn.setOnAction(e -> adminMenu.quit());

        }else{
            machine = new VendingMachine(products, clients, clients.get(index)); //Create new Vending Machine Object
            clientMenu = new StandardMenu(machine, clients.get(index)); //Create new Client Menu Object

            clientMenu.displayMenu(); //Display Products

            menuScreen.setCenter(clientMenu);
            menuScreen.setMargin(clientMenu, new Insets(0, 0, 20, 0));

            //Removes Menu Screen && Displays Login Screen
            quitBtn.setOnAction(e -> {clientMenu.quit(); allElements.getChildren().remove(2); loginPane.setVisible(true);});
        }

        HBox quitHBox = new HBox(); //Quit Button Container
        quitHBox.getChildren().add(quitBtn);
        quitHBox.setAlignment(Pos.BOTTOM_RIGHT);
        menuScreen.setBottom(quitHBox); //Adds Quit Button to Menu Screen Container
        menuScreen.setMargin(quitHBox, new Insets(0, 15, 15, 0));

        //Adds Menu Screen to Main Container
        allElements.getChildren().add(2, menuScreen);
    }



    public static void main(String[] args) { launch(args); }


}

package com.example.ecommerce;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Ecommerce extends Application {

    private final int width=500,height=400,headerLine=50;
    ProductList productList=new ProductList();

    Pane bodyPane;

    Button singInButton=new Button("Sign In");

    Label welcomeLabel=new Label("Welcome Customer");
    Customer loggedInCustomer=null;

    private GridPane headerBar(){
        GridPane header=new GridPane();
        TextField searchBar=new TextField();
        Button searchButton=new Button("Search");

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.getAllProducts());
            }
        });

        singInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });
        header.setHgap(10);
        header.add(searchBar, 0,0);
        header.add(searchButton, 1,0);
        header.add(singInButton,2,0);
        header.add(welcomeLabel,3,0);

        return header;
    }
    private GridPane loginPage(){
        Label userLabel=new Label("User Name");
        Label passLabel=new Label("Password");
        TextField userName=new TextField();
        userName.setPromptText("Enter User Name");
        PasswordField password= new PasswordField();
        password.setPromptText("Enter Password");
        Button loginButton=new Button("Login");
        Label messageLabel=new Label("Login - Message");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user=userName.getText();
                String pass=password.getText();
                loggedInCustomer=Login.customerLogin(user,pass);
                if(loggedInCustomer!=null) {
                    messageLabel.setText("Login Successful");
                    welcomeLabel.setText("Welcome "+ loggedInCustomer.getName());
                }
                else messageLabel.setText("Login failed");
            }
        });

        GridPane loginPane=new GridPane();
        loginPane.setTranslateY(50);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.add(userLabel,0,0);
        loginPane.add(userName,1,0);
        loginPane.add(passLabel,0,1);
        loginPane.add(password,1,1);
        loginPane.add(loginButton,0,2);
        loginPane.add(messageLabel,1,2);
        return loginPane;
    }

    private void showDialogue(String message){
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("Order Status");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText(message);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();

    }
    private GridPane footerBar(){
        Button buyNowButton=new Button("Buy Now");
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productList.getSelectedProduct();
                boolean orderStatus=false;
                if(product!=null && loggedInCustomer!=null){
                    orderStatus=Order.placeOrder(loggedInCustomer,product);
                }
                if(orderStatus){
                    showDialogue("Order Successful");
                }
                else{
                    //
                }
            }
        });

        GridPane footer=new GridPane();
        footer.setTranslateY(headerLine+height);
        footer.add(buyNowButton,0,0);
        return footer;
    }
    private Pane createContent(){
        Pane root=new Pane();
        root.setPrefSize(width,height+2*headerLine);
        bodyPane=new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateY(headerLine);
        bodyPane.setTranslateX(10);
        bodyPane.getChildren().addAll(loginPage());
        root.getChildren().addAll(headerBar()
                //            ,loginPage()
                //            ,productList.getAllProducts()
                ,bodyPane
                ,footerBar()
        );
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(Ecommerce.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}
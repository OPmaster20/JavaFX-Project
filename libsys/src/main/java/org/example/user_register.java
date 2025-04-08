package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static org.example.SQLConnector.For_sys;


public class user_register {

    @FXML
    private Button apply_but;

    @FXML
    private Button cancel_but;

    @FXML
    private ProgressIndicator probar;

    @FXML
    private PasswordField register_password;

    @FXML
    private PasswordField register_password_confirm;

    @FXML
    private ChoiceBox<String> register_types = new ChoiceBox<>();

    @FXML
    private TextField register_username;

    @FXML
    private Tab tab;

    @FXML
    void apply_function(ActionEvent event) throws IOException {
        probar.setOpacity(1);

        if(register_username.getText().length() == 0 || register_password.getText().length() == 0 || register_password_confirm.getText().length() == 0){
            System.out.println("incomplete");

        }else if(!(register_password.getText().equals(register_password_confirm.getText()))){
            System.out.println("Password inconsistency");

        }else{
            probar.setProgress(100);
            SQLConnector.set_register(register_username.getText(),register_username.getText(),register_types.getValue());
            SQLConnector.Logintype = register_types.getValue();
            Stage main_Window = new Stage();
            VBox s = FXMLLoader.load(getClass().getClassLoader().getResource("bookssys.fxml"));

            Scene scene = new Scene(s);
            main_Window.setTitle("Book sys interface");
            main_Window.setScene(scene);
            main_Window.show();
            SQLConnector.MainStage.hide();
            For_sys = main_Window;
            SQLConnector.For_register.close();
            SQLConnector.MainStage.hide();
        }
    }

    @FXML
    void Update_clicked(MouseEvent event) {
        if(register_types.getItems().isEmpty()){
            register_types.getItems().addAll("Sad","Ad");
        }

    }

    @FXML
    void cancel_function(ActionEvent event) {
        SQLConnector.For_register.close();
        SQLConnector.MainStage.show();
    }

}

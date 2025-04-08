package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static org.example.SQLConnector.*;

public class main_interface {

    public String datetime;
    public String usertype;

    @FXML
    private Tab Amlogin;

    @FXML
    private Button Feedbackbut;

    @FXML
    private Button Loginbut;

    @FXML
    private Button Signinbut;

    @FXML
    private TextField Stuid;

    @FXML
    private Tab Stulogin;

    @FXML
    private Button Stuloginbut;

    @FXML
    private Button Systemop;

    @FXML
    private RadioButton aa;

    @FXML
    private DatePicker log_time;

    @FXML
    private PasswordField password;

    @FXML
    private TextField rootname;

    @FXML
    private RadioButton sa;

    @FXML
    private RadioButton ua;

    @FXML
    private TextField username;

    @FXML
    void If_selected(ActionEvent event) {

    }

    @FXML
    void On_clicked_Sys(ActionEvent event) {

    }

    @FXML
    void On_clicked_aa(MouseEvent event) {
        if(sa.isSelected() || ua.isSelected()){
            sa.setSelected(false);
            ua.setSelected(false);
            Stulogin.setDisable(true);
            Amlogin.setDisable(false);
            usertype = "Ad";
        }
    }

    @FXML
    void On_clicked_feedback(ActionEvent event) {

    }
    public boolean If_condition_true(){
        if(log_time.getEditor().getText().length() == 0){
            System.out.println("No date");
            return false;
        }else{
            datetime = log_time.getEditor().getText();
            System.out.println(datetime);
        }
        return true;
    }

    @FXML
    void On_clicked_login(ActionEvent event) throws IOException {
        if(If_condition_true()){
            SQLConnector.logdate = log_time.getEditor().getText();
            if(username.getText().length() == 0 || password.getText().length() == 0){
                System.out.println("NULL");
            }else{
                if(verify_user_infor(username.getText(),password.getText(),rootname.getText())){
                    update_infor(user_id,usertype);
                    System.out.println("Login successful");
                    Logintype = usertype;
                    display_sys();


                } else {
                    System.out.println("Login failure");
                }
            }

            if(rootname.getText().length() == 0){
                System.out.println("Root is NULL");

            }
        }

    }

    @FXML
    void On_clicked_sa(MouseEvent event) {
        if(aa.isSelected() || ua.isSelected()){
            aa.setSelected(false);
            ua.setSelected(false);
            Stulogin.setDisable(true);
            Amlogin.setDisable(false);
            usertype = "Sad";
        }

    }

    @FXML
    void On_clicked_signin(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        TabPane s = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));

        Scene scene = new Scene(s);
        newWindow.setTitle("Registration interface");
        newWindow.setScene(scene);
        newWindow.show();
        SQLConnector.MainStage.hide();
        For_register = newWindow;

    }

    void display_sys() throws IOException {
        Stage main_Window = new Stage();
        VBox s = FXMLLoader.load(getClass().getClassLoader().getResource("bookssys.fxml"));

        Scene scene = new Scene(s);
        main_Window.setTitle("Book sys interface");
        main_Window.setScene(scene);
        main_Window.show();
        SQLConnector.MainStage.hide();
        For_sys = main_Window;
    }

    @FXML
    void On_clicked_stulogin(ActionEvent event) throws IOException {
        if(If_condition_true()){
            logdate = log_time.getEditor().getText();
            if(Stuid.getText().length() == 0){
                System.out.println("NULL");
            } else {
                if(verify_user_infor("std",Stuid.getText(),"std")){
                    update_infor(user_id,usertype);
                    System.out.println("Login successful");
                    Logintype = usertype;
                    display_sys();
                } else {
                    System.out.println("Login failure");
                }

            }
        }
    }

    @FXML
    void On_clicked_ua(MouseEvent event) {
        if(sa.isSelected() || aa.isSelected()){
            aa.setSelected(false);
            sa.setSelected(false);
            Stulogin.setDisable(false);
            Amlogin.setDisable(true);
            usertype = "Stu";
        }
    }

}


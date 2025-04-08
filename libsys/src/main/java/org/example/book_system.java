package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
public class book_system {

    @FXML
    private MenuItem about;

    @FXML
    private MenuItem access_record;

    @FXML
    private MenuItem book_manage;

    @FXML
    private MenuItem borrow_books;

    @FXML
    private MenuItem database;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private HBox imgarea;

    @FXML
    private MenuItem query_books;

    @FXML
    private MenuItem quit;

    @FXML
    private MenuItem record_manage;

    @FXML
    private MenuItem register_book;

    @FXML
    private MenuItem settings;

    @FXML
    private MenuItem user_infor;

    public void reset_img(){
        System.out.println(img1.getImage());
    }

    @FXML
    void about_function(ActionEvent event) {
        reset_img();
    }

    @FXML
    void access_function(ActionEvent event) {

    }

    @FXML
    void borrow_function(ActionEvent event) throws IOException {
        SQLConnector.For_sys.hide();
        Stage bookWindow = new Stage();
        TabPane k = FXMLLoader.load(getClass().getClassLoader().getResource("booksselect.fxml"));
        Scene scene = new Scene(k);
        bookWindow.setTitle("Book lib interface");
        bookWindow.setScene(scene);
        bookWindow.show();
        SQLConnector.Book_borrow = bookWindow;



    }

    @FXML
    void database_function(ActionEvent event) {

    }

    @FXML
    void infor_function(ActionEvent event) {

    }

    @FXML
    void manage_function(ActionEvent event) throws IOException {
        if(SQLConnector.Logintype.equals("Stu")){
            register_book.setDisable(true);
            System.out.println("Invaild user");
        }else {
            SQLConnector.For_sys.hide();
            Stage datamgWindow = new Stage();
            TabPane k = FXMLLoader.load(getClass().getClassLoader().getResource("libmg.fxml"));
            Scene scene = new Scene(k);
            datamgWindow.setTitle("Lib management interface");
            datamgWindow.setScene(scene);
            datamgWindow.show();
            SQLConnector.Lib_MA = datamgWindow;

        }
    }

    @FXML
    void query_function(ActionEvent event) throws IOException {
        SQLConnector.For_sys.hide();
        Stage bookqueryWindow = new Stage();
        VBox k = FXMLLoader.load(getClass().getClassLoader().getResource("bookquery.fxml"));
        Scene scene = new Scene(k);
        bookqueryWindow.setTitle("Book search interface");
        bookqueryWindow.setScene(scene);
        bookqueryWindow.show();
        SQLConnector.Book_query = bookqueryWindow;
    }

    @FXML
    void quit_function(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void record_function(ActionEvent event) {

    }

    @FXML
    void register_function(ActionEvent event) throws IOException {
        if(SQLConnector.Logintype.equals("Stu")){
            register_book.setDisable(true);
            System.out.println("Invaild user");
        }else {
            SQLConnector.For_sys.hide();
            Stage bookregisterWindow = new Stage();
            SplitPane k = FXMLLoader.load(getClass().getClassLoader().getResource("bookregist.fxml"));
            Scene scene = new Scene(k);
            bookregisterWindow.setTitle("Book register interface");
            bookregisterWindow.setScene(scene);
            bookregisterWindow.show();
            SQLConnector.Book_register = bookregisterWindow;
        }

    }

    @FXML
    void settings_function(ActionEvent event) {

    }

}

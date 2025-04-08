package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("System");
        Class<? extends Main> Class = getClass();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("test.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        SQLConnector.MainStage = primaryStage;
        primaryStage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}

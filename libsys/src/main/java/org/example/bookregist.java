package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class bookregist {

    @FXML
    private Button back_but;

    @FXML
    private ProgressIndicator pro_id;

    @FXML
    private Button cleanall_but;

    @FXML
    private TextField register_anthur;

    @FXML
    private TextArea register_bookintro;

    @FXML
    private TextField register_bookname;

    @FXML
    private DatePicker register_date;

    @FXML
    private TextArea register_imgadddress;

    @FXML
    private ChoiceBox<String> register_type;

    @FXML
    private Label root_name;

    @FXML
    private Button submit_but;

    @FXML
    void On_back(ActionEvent event) {
        SQLConnector.Book_register.close();
        SQLConnector.For_sys.show();
    }

    @FXML
    void On_cleanall(ActionEvent event) {
        register_type.getItems().clear();
        register_anthur.clear();
        register_bookintro.clear();
        register_bookname.clear();
        register_imgadddress.clear();
        register_date.getEditor().clear();
        if (pro_id.getOpacity() != 0){
            pro_id.setOpacity(0);
        }
    }

    @FXML
    void Load_type(MouseEvent event) {
        if(register_type.getItems().size() == 0){
            register_type.getItems().add("Romantic");
            register_type.getItems().add("Science Fiction");
            register_type.getItems().add("Philosophy");
        }

    }

    public boolean check_infor(){
        if(register_type.getSelectionModel().getSelectedItem().length() == 0){
            return false;
        } else if (register_bookname.getText().length() == 0) {
            return false;
        } else if(register_imgadddress.getText().length() == 0){
            return false;
        } else if (register_anthur.getText().length() == 0) {
            return false;
        } else if (register_bookintro.getText().length() == 0) {
            return false;
        } else if (register_date.getEditor().getText().length() == 0) {
            return false;
        }

        File file = new File(register_imgadddress.getText());
        if(!(file.exists())){
            System.out.println("img address not exists");
            return false;
        }
        return true;
    }
    @FXML
    void On_submit(ActionEvent event) {
        if(check_infor()){
            SQLConnector.Update_books(register_bookname.getText(),register_type.getSelectionModel().getSelectedItem(),register_date.getEditor().getText(),register_imgadddress.getText(),register_bookintro.getText(),register_anthur.getText());
            pro_id.setOpacity(1);
            pro_id.setProgress(1);
        }
    }

}

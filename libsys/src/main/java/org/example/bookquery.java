package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class bookquery {

    @FXML
    private Button back_id;

    @FXML
    private Button clear_id;

    @FXML
    private CheckBox mode_precise;

    @FXML
    private CheckBox mode_scope;

    @FXML
    private ListView<String> result_area;

    @FXML
    private TextField search_filed;

    @FXML
    private Button search_id;

    public int mode_code = 0;

    @FXML
    void Clicked_clean(ActionEvent event) {
        search_filed.clear();
        result_area.getItems().clear();
        result_area.setOpacity(0);
    }

    @FXML
    void Clicked_search(ActionEvent event) {
        if(result_area.getItems().size() != 0){
            result_area.getItems().clear();
        }
        if(search_filed.getText().length() != 0){
            List<String> m = SQLConnector.book_query(search_filed.getText(),mode_code);
            result_area.setOpacity(1);
            if(m != null){
                for (String s:m){
                    result_area.getItems().add(s);
                }
            }else{
                result_area.getItems().add("No such books");
            }
        }
    }

    @FXML
    void On_back(ActionEvent event) {
        SQLConnector.Book_query.close();
        SQLConnector.For_sys.show();
    }

    @FXML
    void On_precisemode(ActionEvent event) {
        mode_scope.setSelected(true);
        mode_precise.setSelected(false);
        mode_code = 1;
    }

    @FXML
    void On_scopemode(ActionEvent event) {
        mode_scope.setSelected(false);
        mode_precise.setSelected(true);
        mode_code = 0;
    }

}

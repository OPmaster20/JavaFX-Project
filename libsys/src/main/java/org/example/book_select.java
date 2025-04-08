package org.example;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
class Books{
    public String bookname;
    public String bookanthor;
    public String booktype;
    public String bookstatus;

    public String getBookname(){
        return bookname;
    }
    public String getBooktype(){
        return booktype;
    }
    public String getBookstatus(){
        return bookstatus;
    }
    public String getBookanthor(){
        return bookanthor;
    }

}

public class book_select {

    @FXML
    private ComboBox<String> bookstatus_id;

    @FXML
    private Label bookanthor_label;

    @FXML
    private Label booktype_label;

    @FXML
    private Label date_label;

    @FXML
    private Button but_backselect;

    @FXML
    private TreeTableColumn<Books, String> bn;

    @FXML
    private TreeTableColumn<Books, String> ba;

    @FXML
    private ImageView book_img;

    @FXML
    private TextArea book_intro;

    @FXML
    private TreeTableView<Books> table_view;

    @FXML
    private TreeTableColumn<Books, String> book_status;

    @FXML
    private TreeTableColumn<Books, String> bt;

    @FXML
    private Button but_back;

    @FXML
    private Button br;

    @FXML
    private Button but_borrow;

    @FXML
    private TabPane tab_panel;

    @FXML
    private Button but_next;

    @FXML
    private Button but_reload;

    @FXML
    private Pane but_return;

    @FXML
    private Tab tab_infor;

    @FXML
    private Tab tab_select;


    @FXML
    private TreeView<?> treebook;

    public List<Books> booklist = new ArrayList<>();
    public TreeItem root = new TreeItem<>("All books");
    public String Selected;

    @FXML
    void Clicked_back(ActionEvent event) {
        SQLConnector.For_sys.show();
        SQLConnector.Book_borrow.close();
    }

    @FXML
    void Clicked_backselect(ActionEvent event) {
        tab_infor.setDisable(true);
        tab_select.setDisable(false);
        tab_panel.getSelectionModel().select(tab_select);
        book_intro.clear();
        bookstatus_id.getItems().clear();
    }

    public void reload_infor(){
        for (Books b:booklist){
            if(b.bookname.equals(Selected)){
                tab_infor.setDisable(false);
                tab_select.setDisable(true);
                tab_panel.getSelectionModel().select(tab_infor);
                book_intro.setText(SQLConnector.get_bookintro(Selected));
                bookstatus_id.setDisable(false);
                bookstatus_id.getItems().add(b.bookstatus);
                bookstatus_id.setValue(b.bookstatus);
            }
        }
    }

    @FXML
    void Clicked_borrow(ActionEvent event) {
        if(but_borrow.isDisable()){
            but_borrow.setDisable(false);
            br.setDisable(true);
        }else{
            but_borrow.setDisable(true);
            br.setDisable(false);
        }

        SQLConnector.Update_bookstatus(0,Selected);
        update_data();
        bookstatus_id.getItems().clear();
        reload_infor();

    }

    @FXML
    void On_butcheck(MouseEvent event) {
        TreeItem<String> selectedItem = (TreeItem<String>) treebook.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Selected = selectedItem.getValue();
        }
    }

    @FXML
    void Clicked_nextpages(ActionEvent event) {
        for (Books b:booklist){
            if(b.bookname.equals(Selected)){
                tab_infor.setDisable(false);
                tab_select.setDisable(true);
                tab_panel.getSelectionModel().select(tab_infor);
                book_intro.setText(SQLConnector.get_bookintro(Selected));
                bookstatus_id.setDisable(false);
                bookstatus_id.getItems().add(b.bookstatus);
                bookstatus_id.setValue(b.bookstatus);
                bookanthor_label.setText(b.bookanthor);
                booktype_label.setText(b.booktype);
                date_label.setText(SQLConnector.get_bookdate(b.bookname));
                if(b.bookstatus.equals("Vacant")){
                    br.setDisable(true);
                    but_borrow.setDisable(false);
                }else {
                    br.setDisable(false);
                    but_borrow.setDisable(true);
                }

                Image image = new Image("file:" + SQLConnector.get_booksimageaddress(b.bookname));
                System.out.println(image.getUrl() + image.isError());
                book_img.setImage(image);
                break;

            }
        }
    }

    @FXML
    void Clicked_reload(ActionEvent event) {
        update_data();
    }

    @FXML
    void Clicked_return(ActionEvent event) {
        if(br.isDisable()){
            br.setDisable(false);
            but_borrow.setDisable(true);
        }else {
            br.setDisable(true);
            but_borrow.setDisable(false);
        }

        SQLConnector.Update_bookstatus(1,Selected);
        update_data();
        bookstatus_id.getItems().clear();
        reload_infor();

    }

    public void update_data(){
        SQLConnector.get_book_infor();
        if (booklist.size() != 0){
            booklist.clear();
        }
        if(treebook.getRoot() != null){
            treebook.getRoot().getChildren().clear();
        }
        for (int i = 0; i < SQLConnector.books.size(); ) {
            Books books = new Books();
            books.booktype = SQLConnector.books.get(i);
            books.bookname = SQLConnector.books.get(i+1);
            books.bookanthor = SQLConnector.books.get(i+2);
            books.bookstatus = SQLConnector.books.get(i+3);
            i += 4;
            booklist.add(books);
        }

        for (Books b: booklist){
            root.getChildren().add(new TreeItem<>(b.bookname));
        }
        treebook.setRoot(root);


    }

}

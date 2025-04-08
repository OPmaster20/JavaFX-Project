package org.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class libmg{

    @FXML
    private AnchorPane Ap;

    @FXML
    private BarChart<String, Number> Bar = new BarChart<>(axis,axis1);;

    @FXML
    private TableView<object_books> Datatable = new TableView<>();

    @FXML
    private PieChart Pie = new PieChart();

    @FXML
    private TitledPane Tp;

    @FXML
    private TableColumn<object_books, String> anthurcol = new TableColumn<>();

    @FXML
    private Button backhome;

    @FXML
    private Tab bookdatatab;

    @FXML
    private TableColumn<object_books, String> datecol = new TableColumn<>();

    @FXML
    private Button delectdata;

    @FXML
    private Tab delectdatatab;

    @FXML
    private TableColumn<object_books, String> idcol = new TableColumn<>();

    @FXML
    private Button load;

    @FXML
    private TableColumn<object_books, String> namecol = new TableColumn<>();

    @FXML
    private TableColumn<object_books, String> statuscol = new TableColumn<>();

    @FXML
    private TableColumn<object_books, String> timecol = new TableColumn<>();

    @FXML
    private TableColumn<object_books, String> typecol = new TableColumn<>();

    public static CategoryAxis axis = new CategoryAxis();
    public static NumberAxis axis1 = new NumberAxis();

    public static ObservableList<object_books> Ob = FXCollections.observableArrayList();

    @FXML
    void On_backhome(ActionEvent event) {
        SQLConnector.For_sys.show();
        SQLConnector.Lib_MA.close();
    }

    @FXML
    void On_delectdata(ActionEvent event) {
        object_books s = Datatable.getSelectionModel().getSelectedItem();
        if(s != null){
            SQLConnector.Delete_books(Integer.parseInt(s.getBook_id()));
            Datatable.getItems().remove(s);
        }
    }

    @FXML
    void On_loaddata(ActionEvent event) {

        if(Datatable.getItems().size() != 0){
            Datatable.getItems().clear();
        }
        if(Pie.getData().size() != 0){
            Pie.getData().clear();
        }
        if(Bar.getData().size() != 0){
            Bar.getData().clear();
        }

        axis.setLabel("Books name");
        axis1.setLabel("Borrowed time");

        String[][] data = SQLConnector.get_topbooks();

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(data[0][2]);
        series1.getData().add(new XYChart.Data<>(data[0][0],Integer.parseInt(data[0][1])));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(data[1][2]);
        series2.getData().add(new XYChart.Data<>(data[1][0],Integer.parseInt(data[1][1])));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName(data[2][2]);
        series3.getData().add(new XYChart.Data<>(data[2][0],Integer.parseInt(data[2][1])));

        Bar.getData().addAll(series1,series2,series3);

        PieChart.Data d1 = new PieChart.Data(data[0][2],Integer.parseInt(data[0][1]));
        PieChart.Data d2 = new PieChart.Data(data[1][2],Integer.parseInt(data[1][1]));
        PieChart.Data d3 = new PieChart.Data(data[2][2],Integer.parseInt(data[2][1]));

        Pie.getData().addAll(d1,d2,d3);


        List<String> infor = SQLConnector.get_allbooks();
        System.out.println(infor.size());

        anthurcol.setCellValueFactory(cellData -> cellData.getValue().book_anthurProperty());
        datecol.setCellValueFactory(cellData -> cellData.getValue().book_dateProperty());
        idcol.setCellValueFactory(cellData -> cellData.getValue().book_idProperty());
        namecol.setCellValueFactory(cellData -> cellData.getValue().book_nameProperty());
        statuscol.setCellValueFactory(cellData -> cellData.getValue().book_statusProperty());
        timecol.setCellValueFactory(cellData -> cellData.getValue().book_timeProperty());
        typecol.setCellValueFactory(cellData -> cellData.getValue().book_typeProperty());


        for (int i = 0; i < infor.size(); ) {
            object_books o = new object_books();
            o.setBook_id(infor.get(i));
            i++;
            o.setBook_name(infor.get(i));
            i++;
            o.setBook_anthur(infor.get(i));
            i++;
            o.setBook_date(infor.get(i));
            i++;
            o.setBook_status(infor.get(i));
            i++;
            o.setBook_time(infor.get(i));
            i++;
            o.setBook_type(infor.get(i));
            i++;
            Ob.add(o);
        }

        Datatable.setItems(Ob);

    }

    @FXML
    void On_sortdata(ActionEvent event) {

    }

}

class object_books {
    private StringProperty book_id = new SimpleStringProperty();
    private StringProperty book_name = new SimpleStringProperty();;
    private StringProperty book_anthur = new SimpleStringProperty();;
    private StringProperty book_date = new SimpleStringProperty();;
    private StringProperty book_status = new SimpleStringProperty();;
    private StringProperty book_time = new SimpleStringProperty();;
    private StringProperty book_type = new SimpleStringProperty();;

    public StringProperty book_idProperty() {
        return book_id;
    }

    public StringProperty book_anthurProperty() {
        return book_anthur;
    }

    public StringProperty book_dateProperty() {
        return book_date;
    }

    public StringProperty book_nameProperty() {
        return book_name;
    }

    public StringProperty book_statusProperty() {
        return book_status;
    }

    public StringProperty book_timeProperty() {
        return book_time;
    }

    public StringProperty book_typeProperty() {
        return book_type;
    }


    public void setBook_id(String s) {
        book_id.set(s);
    }

    public void setBook_name(String s) {
        book_name.set(s);
    }

    public void setBook_anthur(String s) {
        book_anthur.set(s);
    }

    public void setBook_date(String s) {
        book_date.set(s);
    }

    public void setBook_status(String s) {
        book_status.set(s);
    }

    public void setBook_time(String s) {
        book_time.set(s);
    }

    public void setBook_type(String s) {
        book_type.set(s);
    }

    public String getBook_id(){
        return book_id.get();
    }

    public String getBook_type(){
        return book_id.get();
    }

    public String getBook_name(){
        return book_id.get();
    }

    public String getBook_anthur(){
        return book_id.get();
    }
    public String getBook_time(){
        return book_id.get();
    }

    public String getBook_date(){
        return book_id.get();
    }

    public String getBook_status(){
        return book_id.get();
    }
}

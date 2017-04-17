package controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.org.apache.xpath.internal.operations.String;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import models.TableItemPayback;
import utils.Utils;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by Martín Ruíz on 4/14/2017.
 */
public class PaybackPeriod implements Initializable {

    @FXML private TableView<TableItemPayback> tableView;
    @FXML private AnchorPane root;
    @FXML private JFXButton buttonAddTabItem;
    @FXML private JFXButton buttonDeleteTabItem;
    @FXML private JFXTextField textFieldPrincipal;
    @FXML private JFXTextField textFieldInterestRate;
    @FXML private JFXComboBox<Integer> comboBoxPeriods;

    private ObservableList<TableItemPayback> data = FXCollections.observableArrayList(
        new TableItemPayback(0,0,0,0)
    );


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(tableView.toString());
        setupTable();
        setupComboBox();

    }

    private void setupComboBox() {
        for (int i = 1; i <= 100; i++) {
            comboBoxPeriods.getItems().add(i);
        }
        comboBoxPeriods.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                addPeriods(newValue);
            }
        });
    }
    private void addPeriods(int numPeriods){
        data.clear();
        for (int i = 0; i < numPeriods; i++) {
            data.add(new TableItemPayback( getDataIndex(),0,0,0) );
        }
    }

    @FXML
    void deleteTabItem(ActionEvent event) {
        TableItemPayback itemSelected = tableView.getSelectionModel().getSelectedItem();
        data.remove(itemSelected);
        reCalculatePeriods();
    }

    private void reCalculatePeriods() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setPeriod(i) ;
        }
    }

    public int getDataIndex (){
        if(data.size() <=0 ){
            return 0;
        }else{
            return data.get(data.size()-1).getPeriod() +1;
        }
    }

    @FXML
    void addTabITem(ActionEvent event) {
        data.add(new TableItemPayback( getDataIndex(),0,0,0));
    }


    public void setupTable(){
        //Creating columns of the table
        TableColumn colPeriod      = new TableColumn("Period");
        TableColumn colOutflow     = new TableColumn("Outflow");
        TableColumn colInflow      = new TableColumn("Inflow");
        TableColumn colCumulative = new TableColumn("Accumulative Cash Flow");
        //Setting Width of columns
        colOutflow.setPrefWidth(200);
        colInflow.setPrefWidth(200);
        colCumulative.setPrefWidth(200);
        //tableView.getColumns().setAll(colOutflow,colInflow,colAcumulative);
        //Adding columns to the table
        tableView.getColumns().addAll(colPeriod,colOutflow,colInflow,colCumulative);


        colPeriod.setCellValueFactory(new PropertyValueFactory<TableItemPayback,Integer>("period"));
        colOutflow.setCellValueFactory(new PropertyValueFactory<TableItemPayback,Float>("outflow"));
        colInflow.setCellValueFactory(new PropertyValueFactory<TableItemPayback,Float>("inflow"));
        colCumulative.setCellValueFactory(new PropertyValueFactory<TableItemPayback,Float>("cumulative"));

        /* Make the CellFactory so that can be editable */
        colOutflow.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colOutflow.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((TableItemPayback) event.getTableView().getItems().get(event.getTablePosition().getRow())).setOutflow((Float) event.getNewValue());

            }
        });
        colInflow.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colInflow.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((TableItemPayback) event.getTableView().getItems().get(event.getTablePosition().getRow())).setInflow((Float) event.getNewValue());
            }
        });



        tableView.setEditable(true);
        tableView.setItems(data);


    }










}

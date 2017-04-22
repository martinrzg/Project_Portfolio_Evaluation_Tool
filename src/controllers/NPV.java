package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.FloatStringConverter;
import models.TableNPV;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martín Ruíz on 4/19/2017.
 */
public class NPV implements Initializable {
    @FXML private JFXComboBox<Integer> comboBoxPeriods;
    @FXML private JFXTextField textFieldPrincipal;
    @FXML private JFXButton buttonAddTabItem;
    @FXML private AnchorPane root;
    @FXML private JFXTextField textFieldInterestRate;
    @FXML private JFXButton buttonDeleteTabItem;
    @FXML private TableView<TableNPV> tableView;
    @FXML private JFXTextField textFieldTaxRate;
    private ObservableList<TableNPV> data ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        setupTable();
        setupComboBox();



        data.add(new TableNPV(0,0,0,0,0));

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
            data.add(new TableNPV( getDataIndex(),0,0,0,0) );
        }
    }
    private void reCalculatePeriods() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setPeriod(i) ;
        }
    }

    private int getDataIndex (){
        if(data.size() <=0 ){
            return 0;
        }else{
            return data.get(data.size()-1).getPeriod() +1;
        }
    }

    private void setupTable(){
        //Creating columns of the table
        TableColumn colPeriod      = new TableColumn("Period");
        TableColumn colOutflow     = new TableColumn("Outflow");
        TableColumn colInflow      = new TableColumn("Inflow");
        TableColumn colNetCF       = new TableColumn("Net Cash Flow");
        TableColumn colCumulative  = new TableColumn("Accumulative Cash Flow");
        //Setting Width of columns
        colOutflow.setPrefWidth(200);
        colInflow.setPrefWidth(200);
        colNetCF.setPrefWidth(200);
        colCumulative.setPrefWidth(200);
        //tableView.getColumns().setAll(colOutflow,colInflow,colAcumulative);
        //Adding columns to the table
        tableView.getColumns().addAll(colPeriod,colOutflow,colInflow,colNetCF,colCumulative);
        //tableView.setOnMouseClicked(e-> );

        colPeriod.setCellValueFactory(new PropertyValueFactory<TableNPV,Integer>("period"));
        colOutflow.setCellValueFactory(new PropertyValueFactory<TableNPV,Float>("outflow"));
        colInflow.setCellValueFactory(new PropertyValueFactory<TableNPV,Float>("inflow"));
        colNetCF.setCellValueFactory(new PropertyValueFactory<TableNPV,Float>("ncf"));
        colCumulative.setCellValueFactory(new PropertyValueFactory<TableNPV,Float>("ccf"));


        /* Make the CellFactory so that can be editable */

        colOutflow.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colOutflow.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((TableNPV) event.getTableView().getItems().get(event.getTablePosition().getRow())).setOutflow((Float) event.getNewValue());
                //data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
            }

        });
        colInflow.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colInflow.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((TableNPV) event.getTableView().getItems().get(event.getTablePosition().getRow())).setInflow((Float) event.getNewValue());
                //data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
            }
        });

        tableView.setEditable(true);
        tableView.setItems(data);
    }
    @FXML
    void addTabITem(ActionEvent event) {
        data.add(new TableNPV( getDataIndex(),0,0,0,0));
    }

    @FXML
    void deleteTabItem(ActionEvent event) {
        TableNPV itemSelected = tableView.getSelectionModel().getSelectedItem();
        data.remove(itemSelected);
        reCalculatePeriods();
    }


}

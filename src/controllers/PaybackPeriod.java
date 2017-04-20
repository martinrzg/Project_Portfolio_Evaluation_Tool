package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.FloatStringConverter;
import models.TableItemPayback;
import operations.Projection;
import utils.Utils;

import java.net.URL;
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
    @FXML private BarChart<Number, Number> barChart;

    private ObservableList<TableItemPayback> data ;
    private XYChart.Data<Number, Number> dataChart;
    private XYChart.Series seriesChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(tableView.toString());
        data = FXCollections.observableArrayList();

        setupTable();
        setupComboBox();
        setUpValidator();

        /*  setupBarChart  */

        barChart.setTitle("Cash Flow Diagram");
        barChart.getXAxis().setLabel("Period");
        barChart.getYAxis().setLabel("Cash Flow");

        seriesChart = new XYChart.Series();
        /*data.addListener(new ListChangeListener<TableItemPayback>() {
            @Override
            public void onChanged(Change<? extends TableItemPayback> c) {
                System.out.println(c.getList().get(data.size()-1).getPeriod());
                seriesChart.getData().add(new XYChart.Data(data.get(data.size()-1).getPeriod()+"", data.get(data.size()-1).getCumulative()));
            }
        });*/
        data.add(new TableItemPayback(0,0,0,-5));
        data.add(new TableItemPayback(1,0,0,-2));
        data.add(new TableItemPayback(2,0,0,10));
        barChart.getData().add(seriesChart);
    }

    /*public void updateChart(){
        seriesChart.getData().removeAll();
        for (int i = 0; i < data.size(); i++) {
            dataChart =new XYChart.Data(i+"",data.get(i).getCumulative());
            seriesChart.getData().add(dataChart);
        }
    }*/



    private void setUpValidator() {
        NumberValidator principalValidator = new NumberValidator();
        NumberValidator interestRateValidator = new NumberValidator();

        textFieldPrincipal.getValidators().add(principalValidator);
        textFieldInterestRate.getValidators().add(interestRateValidator);

        principalValidator.setMessage("Must be numeric value.");
        interestRateValidator.setMessage("Number value 0-100 %");

        principalValidator.setIcon(new ImageView(Utils.geErrorIcon()));
        interestRateValidator.setIcon(new ImageView(Utils.geErrorIcon()));

        textFieldPrincipal.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldPrincipal.validate();
                }
            }
        });
        textFieldInterestRate.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldInterestRate.validate();
                    try{
                        float val = Float.parseFloat(textFieldInterestRate.getText());
                        System.out.println(val);
                        if((val >= 0) && (val <= 100)){
                            System.out.println("VALIDO");
                            textFieldInterestRate.getActiveValidator().setVisible(false);
                        }else {
                            System.out.println("INvalido!!!");
                            textFieldInterestRate.getActiveValidator().setVisible(true);
                        }
                    }catch (Exception e){

                    }
                }
            }
        });
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

    private int getDataIndex (){
        if(data.size() <=0 ){
            return 0;
        }else{
            return data.get(data.size()-1).getPeriod() +1;
        }
    }

    @FXML
    void addTabITem(ActionEvent event) {
        data.add(new TableItemPayback( getDataIndex(),0,0,0));
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
        //tableView.setOnMouseClicked(e-> );

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
                double principal, interestRate;
                try{
                    principal = Double.parseDouble(textFieldPrincipal.getText());
                }catch (Exception e){
                    principal = 0;
                }
                try {
                    interestRate = Double.parseDouble(textFieldInterestRate.getText());
                }catch (Exception e){
                    interestRate = 0;
                }
                data = Projection.calculatePayback(data,principal,interestRate);
            }

        });
        colInflow.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colInflow.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((TableItemPayback) event.getTableView().getItems().get(event.getTablePosition().getRow())).setInflow((Float) event.getNewValue());
                double principal, interestRate;
                try{
                    principal = Double.parseDouble(textFieldPrincipal.getText());
                }catch (Exception e){
                    principal = 0;
                }
                try {
                    interestRate = Double.parseDouble(textFieldInterestRate.getText());
                }catch (Exception e){
                    interestRate = 0;
                }
                data = Projection.calculatePayback(data,principal,interestRate);

                //updateChart();
            }
        });
        tableView.setEditable(true);
        tableView.setItems(data);
    }
}

package controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.DoubleTextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.NumberValidator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.PaybackRow;
import operations.Projection;
import utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Created by Martín Ruíz on 4/14/2017.
 */
public class PaybackPeriod implements Initializable {

    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    @FXML private AnchorPane root;
    @FXML private JFXTextField textFieldPrincipal;
    @FXML private JFXTextField textFieldInterestRate;
    @FXML private JFXComboBox<Integer> comboBoxPeriods;
    @FXML private BarChart<Number, Number> barChart;

    // New Skeleton
    @FXML private JFXTreeTableView<PaybackRow> treeTableView;
    @FXML private JFXButton treeTableViewAdd;
    @FXML private JFXButton treeTableViewRemove;
    @FXML private JFXTextField searchField;
    @FXML private JFXTreeTableColumn<PaybackRow, Integer> periodColumn;
    @FXML private JFXTreeTableColumn<PaybackRow, Double> outflowColumn;
    @FXML private JFXTreeTableColumn<PaybackRow, Double> inflowColumn;
    @FXML private JFXTreeTableColumn<PaybackRow, Double> netCashFlowColumn;
    @FXML private JFXTreeTableColumn<PaybackRow, Double> cumulativeColumn;
    @FXML private Label treeTableViewCount;

    private ObservableList<PaybackRow> data ;

    private XYChart.Data<Number, Number> dataChart;
    private XYChart.Series seriesChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(tableView.toString());
        data = FXCollections.observableArrayList();
        data.add(new PaybackRow(0,0,0,0,0));

        setupTable();
        setupComboBox();
        setUpValidator();

        /*  setupBarChart  */

        barChart.setTitle("Cash Flow Diagram");
        barChart.getXAxis().setLabel("Period");
        barChart.getYAxis().setLabel("Cash Flow");

        //updateChartData();
        /*data.addListener(new ListChangeListener<PaybackRow>() {
            @Override
            public void onChanged(Change<? extends PaybackRow> c) {
                System.out.println(c.getList().get(data.size()-1).getPeriod());
                seriesChart.getData().add(new XYChart.Data(data.get(data.size()-1).getPeriod()+"", data.get(data.size()-1).getNetCashFlow()));
            }
        });
        */
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
            data.add(new PaybackRow( getDataIndex(),0,0,0,0) );
        }
        //treeTableViewCount.setText("( "+String.valueOf(treeTableView.getCurrentItemsCount())+" )");
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
    private void printData(){
        for (int i = 0; i < data.size(); i++) {
            PaybackRow temp = data.get(i);
            System.out.println("p:"+temp.periodProperty().intValue()+" outf: "+temp.outflowProperty().doubleValue()
                    +" in: "+temp.inflowProperty().doubleValue()+" ncf:"+temp.netCashFlowProperty().doubleValue()
                    +" ccf:"+temp.cumulativeCashFlowProperty().doubleValue());
        }
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<PaybackRow, T> column, Function<PaybackRow, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<PaybackRow, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void setupTable(){
        setupCellValueFactory(periodColumn,  p->p.periodProperty().asObject());
        setupCellValueFactory(outflowColumn, p->p.outflowProperty().asObject());
        setupCellValueFactory(inflowColumn,  p->p.inflowProperty().asObject());
        setupCellValueFactory(netCashFlowColumn, p->p.netCashFlowProperty().asObject());
        setupCellValueFactory(cumulativeColumn,  p->p.cumulativeCashFlowProperty().asObject());

        outflowColumn.setCellFactory((TreeTableColumn<PaybackRow, Double> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new DoubleTextFieldEditorBuilder());
        });
        outflowColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<PaybackRow, Double> t) -> {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().outflowProperty().set(t.getNewValue());
            data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
            printData();

        });
        inflowColumn.setCellFactory((TreeTableColumn<PaybackRow, Double> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new DoubleTextFieldEditorBuilder());
        });
        inflowColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<PaybackRow, Double> t) -> {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().inflowProperty().set(t.getNewValue());
            data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
            printData();
        });

        treeTableViewAdd.setOnMouseClicked((e) -> {
            data.add(new PaybackRow(getDataIndex(),0,0,0,0));
            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() + 1);
            data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
        });
        treeTableViewRemove.setOnMouseClicked((e) -> {
            data.remove(treeTableView.getSelectionModel().selectedItemProperty().get().getValue());
            reCalculatePeriods();
            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() - 1);
            data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
        });

        treeTableView.setRoot(new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);
        treeTableView.setEditable(true);
        treeTableViewCount.textProperty().bind(Bindings.createStringBinding(()->PREFIX + treeTableView.getCurrentItemsCount()
                                                                             + POSTFIX, treeTableView.currentItemsCountProperty()));
        searchField.textProperty().addListener(setupSearchField(treeTableView));

    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<PaybackRow> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(paybackProp -> {
                    final PaybackRow temp = paybackProp.getValue();
                    return Integer.toString(temp.periodProperty().get()).contains(newVal)
                            || Double.toString(temp.outflowProperty().get()).contains(newVal)
                            || Double.toString(temp.inflowProperty().get()).contains(newVal)
                            || Double.toString(temp.netCashFlowProperty().get()).contains(newVal)
                            || Double.toString(temp.cumulativeCashFlowProperty().get()).contains(newVal) ;
                });
    }
}

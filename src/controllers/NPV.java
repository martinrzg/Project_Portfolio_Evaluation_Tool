package controllers;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martín Ruíz on 4/19/2017.
 */
public class NPV implements Initializable {
    @FXML private JFXComboBox<Integer> comboBoxPeriods;
    @FXML private AnchorPane root;
    @FXML private JFXTextField textFieldInterestRate;
    @FXML private JFXTextField textFieldTaxRate;
    @FXML private JFXButton treeTableViewAdd;
    @FXML private JFXTextField searchField;
    @FXML private BarChart<?, ?> barChart;
    @FXML private JFXTreeTableColumn<?, ?> periodColumn;
    @FXML private JFXTreeTableColumn<?, ?> outflowColumn;
    @FXML private JFXTreeTableColumn<?, ?> inflowColumn;
    @FXML private JFXTreeTableColumn<?, ?> netCashFlowColumn;
    @FXML private JFXTreeTableColumn<?, ?> NPVColumn;
    @FXML private JFXTreeTableColumn<?, ?> cumulativeNVPColumn;
    @FXML private JFXTreeTableView<?> treeTableView;
    @FXML private Label treeTableViewCount;
    @FXML private JFXButton treeTableViewRemove;
    @FXML private JFXButton buttonClear;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }




}

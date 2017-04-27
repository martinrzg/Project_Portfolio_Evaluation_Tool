package controllers;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.DoubleValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.DepreciationRow;
import operations.Projection;
import utils.PDFMaker;
import utils.Utils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Created by Martín Ruíz on 4/26/2017.
 */
public class Depreciation implements Initializable {
    @FXML private JFXTreeTableView<DepreciationRow> treeTableView;
    @FXML private JFXTreeTableColumn<DepreciationRow, Integer> yearColumn;
    @FXML private JFXTreeTableColumn<DepreciationRow, Double> annualDepreciationColumn;
    @FXML private JFXTreeTableColumn<DepreciationRow, Double> valueLedgersColumn;
    @FXML private JFXTreeTableColumn<DepreciationRow, Double> taxPerYearColumn;
    @FXML private JFXTreeTableColumn<DepreciationRow, Double> depreciationRateColumn;
    @FXML private JFXTreeTableColumn<DepreciationRow, Double> cumulativeDepreciationColumn;
    @FXML private JFXTextField searchField;
    @FXML private JFXButton buttonClear;
    @FXML private JFXButton buttonSavePDF;
    @FXML private JFXButton buttonCalculate;
    @FXML private JFXComboBox<Integer> comboBoxPeriods;
    @FXML private JFXComboBox<String> comboBoxCategory;
    @FXML private JFXComboBox<Integer> comboBoxStartingYear;
    @FXML private JFXComboBox<Integer> comboBoxSalvagePeriod;

    @FXML private AnchorPane root;
    @FXML private Label treeTableViewCount;
    @FXML private JFXTextField textFieldTaxRate;
    @FXML private JFXTextField textFieldSalvageValue;
    @FXML private JFXTextField textFieldPrincipal;
    @FXML private LineChart<String, Number> lineChart;

    private ObservableList<DepreciationRow> data;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        setupTable();
        setupComboBox();
        setupValidator();
        setupLineChart();

        buttonSavePDF.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    getChartImage();
                    PDFMaker.makePDFDepreciation("Depreciation",data.size(),Double.parseDouble(textFieldPrincipal.getText()),
                            Double.parseDouble(textFieldTaxRate.getText()),Double.parseDouble(textFieldSalvageValue.getText())
                            ,comboBoxCategory.getSelectionModel().getSelectedItem(),comboBoxSalvagePeriod.getSelectionModel().getSelectedIndex(),
                            data);
                    //TODO Popup sucess save file!
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonCalculate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Projection.calculateDepreciation(data,data.size(),Double.parseDouble(textFieldTaxRate.getText()),Double.parseDouble(textFieldPrincipal.getText()),
                                                Double.parseDouble(textFieldSalvageValue.getText()),getCategoryFromComboBox(),comboBoxStartingYear.getValue().intValue(),
                                                comboBoxSalvagePeriod.getValue().intValue());
                updateLinearChart();
            }
        });
        data.addListener(new ListChangeListener<DepreciationRow>() {
            @Override
            public void onChanged(Change<? extends DepreciationRow> c) {

                updateLinearChart();
            }
        });
    }

    private void getChartImage(){
        WritableImage image = lineChart.snapshot(new SnapshotParameters(),null);
        File file = new File("DepreciationChart.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    public int getCategoryFromComboBox(){
        int index = comboBoxCategory.getSelectionModel().getSelectedIndex();
        switch (index){
            case 0:
                return 0;
            case 1:
                return 3;
            case 2:
                return 5;
            case 3:
                return 7;
            case 4:
                return 10;
            case 5:
                return 15;
            case 6:
                return 20;
        }
        return 0;
    }


    private void updateLinearChart(){
        lineChart.setData(getChartData());
    }

    private void setupLineChart() {
        lineChart.getXAxis().setLabel("xLabel");
        lineChart.getYAxis().setLabel("yLabel");
        lineChart.setData(getChartData());
    }
    private ObservableList<XYChart.Series<String, Number>> getChartData(){
        ObservableList<XYChart.Series<String, Number>> newData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("series name");
        for (int i = 0; i < data.size(); i++) {
            DepreciationRow temp = data.get(i);
            final XYChart.Data<String, Number> dataForSerie = new XYChart.Data<>(Integer.toString(temp.yearProperty().intValue()),temp.taxPerYearProperty().doubleValue());
            /*dataForSerie.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newNode) {
                    if (newNode != null) {
                        if (dataForSerie.getYValue().intValue() >= 0 ) {
                            newNode.setStyle("-fx-bar-fill: #00C853;");
                        } else  {
                            newNode.setStyle("-fx-bar-fill: #F44336;");
                        }
                    }
                }
            });*/
            series.getData().add(dataForSerie);
            }
        newData.addAll(series);
        return newData;
    }

    private void setupValidator(){
        DoubleValidator principalValidator = new DoubleValidator();
        DoubleValidator salvageValidator = new DoubleValidator();
        DoubleValidator taxRateValidator = new DoubleValidator();

        textFieldPrincipal.getValidators().add(principalValidator);
        textFieldSalvageValue.getValidators().add(salvageValidator);
        textFieldTaxRate.getValidators().add(taxRateValidator);

        principalValidator.setMessage("Must be a number");
        salvageValidator.setMessage("Must be a number");
        taxRateValidator.setMessage("Must be number 1-100%");

        principalValidator.setIcon(new ImageView(Utils.geErrorIcon()));
        salvageValidator.setIcon(new ImageView(Utils.geErrorIcon()));
        taxRateValidator.setIcon(new ImageView(Utils.geErrorIcon()));

        textFieldPrincipal.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldPrincipal.validate();
                }
            }
        });
        textFieldSalvageValue.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldSalvageValue.validate();
                }
            }
        });
        textFieldTaxRate.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldTaxRate.validate();
                }
            }
        });
    }

    private void setupComboBox() {
        for (int i = 1; i <= 100; i++) {
            comboBoxPeriods.getItems().add(i);
        }
        for (int i = 1900; i < 2100 ; i++) {
            comboBoxStartingYear.getItems().add(i);
        }
        comboBoxCategory.getItems().add("Straight Line");
        comboBoxCategory.getItems().add("3 Years");
        comboBoxCategory.getItems().add("5 Years");
        comboBoxCategory.getItems().add("7 Years");
        comboBoxCategory.getItems().add("10 Years");
        comboBoxCategory.getItems().add("15 Years");
        comboBoxCategory.getItems().add("20 Years");

        comboBoxStartingYear.getSelectionModel().select(117);
        comboBoxPeriods.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                comboBoxSalvagePeriod.getItems().removeAll();
                addPeriods(newValue);
                for (int i = 0; i <= newValue; i++) {
                    comboBoxSalvagePeriod.getItems().add(i);
                }
            }
        });
    }
    private void addPeriods(int numPeriods) {
        data.clear();
        for (int i = 0; i <= numPeriods; i++) {
            data.add(new DepreciationRow( comboBoxStartingYear.getValue()+i,0,0,0,0,0) );
        }
        //updateBarChart();
    }
    private void setupTable() {
        setupCellValueFactory(yearColumn, d->d.yearProperty().asObject());
        setupCellValueFactory(depreciationRateColumn, d->d.depreciatonRateProperty().asObject());
        setupCellValueFactory(annualDepreciationColumn, d->d.annualDepreciationProperty().asObject());
        setupCellValueFactory(cumulativeDepreciationColumn, d->d.cumulativeDepreciationProperty().asObject());
        setupCellValueFactory(valueLedgersColumn, d->d.valueInLedgersProperty().asObject());
        setupCellValueFactory(taxPerYearColumn, d->d.taxPerYearProperty().asObject());

        treeTableView.setRoot(new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);
        treeTableView.setEditable(false);
        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<DepreciationRow> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(depreProp -> {
                    final DepreciationRow temp = depreProp.getValue();
                    return Integer.toString(temp.yearProperty().get()).contains(newVal)
                            || Double.toString(temp.depreciatonRateProperty().get()).contains(newVal)
                            || Double.toString(temp.annualDepreciationProperty().get()).contains(newVal)
                            || Double.toString(temp.cumulativeDepreciationProperty().get()).contains(newVal)
                            || Double.toString(temp.valueInLedgersProperty().get()).contains(newVal)
                            || Double.toString(temp.taxPerYearProperty().get()).contains(newVal) ;
                });
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<DepreciationRow, T> column, Function<DepreciationRow, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<DepreciationRow, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }
}

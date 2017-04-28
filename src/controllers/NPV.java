package controllers;

import com.itextpdf.text.DocumentException;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.NPVRow;
import models.PaybackRow;
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
 * Created by Martín Ruíz on 4/19/2017.
 */
public class NPV implements Initializable {
    @FXML private JFXComboBox<Integer> comboBoxPeriods;
    @FXML private AnchorPane root;
    @FXML private JFXTextField textFieldInterestRate;
    @FXML private JFXTextField textFieldTaxRate;
    @FXML private JFXButton treeTableViewAdd;
    @FXML private JFXTextField searchField;
    @FXML private BarChart<String , Number> barChart;

    @FXML private JFXTreeTableView<NPVRow> treeTableView;
    @FXML private JFXTreeTableColumn<NPVRow, Integer> periodColumn;
    @FXML private JFXTreeTableColumn<NPVRow, Double> outflowColumn;
    @FXML private JFXTreeTableColumn<NPVRow, Double> inflowColumn;
    @FXML private JFXTreeTableColumn<NPVRow, Double> netCashFlowColumn;
    @FXML private JFXTreeTableColumn<NPVRow, Double> NPVColumn;
    @FXML private JFXTreeTableColumn<NPVRow, Double> cumulativeNVPColumn;

    @FXML private JFXTextField textFieldResult;

    @FXML private Label treeTableViewCount;
    @FXML private JFXButton treeTableViewRemove;
    @FXML private JFXButton buttonClear;
    @FXML private JFXButton buttonSavePDF;


    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    private ObservableList<NPVRow> data;

    private void getChartImage(){
        WritableImage image = barChart.snapshot(new SnapshotParameters(),null);
        File file = new File("NPVChart.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        data.add(new NPVRow(0,0,0,0,0,0));

        setupTable();
        setupComboBox();
        setupValidator();
        setUpBarChart();

        buttonSavePDF.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    getChartImage();
                    PDFMaker.makePDFNPV("Net Present Value",getInterestRate(),data.size(),getTaxRate(),
                            data.get(data.size()-1).getCumulativeNPV(), data);
                    //TODO Popup sucess save file!
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        buttonClear.setOnMouseClicked(e->{
            data.clear();
            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
            currCountProp.set(data.size());
        });
    }

    private double getInterestRate(){
        if(textFieldInterestRate.getText()!= null && !textFieldInterestRate.getText().isEmpty()){
            try{
                double interestRate = Double.parseDouble(textFieldInterestRate.getText());
                return interestRate;
            }catch (Exception e){
                System.out.println("ERROR "+e);
                return 0;
            }
        }
        return 0;
    }
    private double getTaxRate(){
        if(textFieldInterestRate.getText()!= null && !textFieldInterestRate.getText().isEmpty()){
            try{
                double taxRate = Double.parseDouble(textFieldTaxRate.getText());
                return taxRate;
            }catch (Exception e){
                return 0;
            }
        }
        return 0;
    }
    private void setUpBarChart() {
        barChart.getXAxis().setLabel("Period");
        barChart.getXAxis().setLabel("USD");
        barChart.setData(getChartData());
    }

    private ObservableList<XYChart.Series<String, Number>> getChartData(){
        ObservableList<XYChart.Series<String, Number>> newData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Cash Flow");
        for (int i = 0; i < data.size(); i++) {
            NPVRow temp = data.get(i);
            final XYChart.Data<String, Number> dataForSerie = new XYChart.Data<>(Integer.toString(temp.periodProperty().intValue()),temp.netCashFlowProperty().doubleValue());
            dataForSerie.nodeProperty().addListener(new ChangeListener<Node>() {
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
            });
            series.getData().add(dataForSerie);
            //series.getData().add(new XYChart.Data(Integer.toString(temp.periodProperty().intValue()), temp.netCashFlowProperty().doubleValue()));
        }
        newData.addAll(series);
        return newData;
    }
    private void updateBarChart(){
        textFieldResult.setText(String.valueOf(data.get(data.size()-1).getCumulativeNPV()));
        barChart.setData(getChartData());
    }


    private void setupValidator() {
        NumberValidator interestRateValidator = new NumberValidator();
        NumberValidator taxRateValidator      = new NumberValidator();

        textFieldInterestRate.getValidators().add(interestRateValidator);
        textFieldTaxRate.getValidators().add(taxRateValidator);

        interestRateValidator.setMessage("Numeric value 0-100%");
        taxRateValidator.setMessage("Numeric value 0-100%");

        interestRateValidator.setIcon(new ImageView(Utils.geErrorIcon()));
        taxRateValidator.setIcon(new ImageView(Utils.geErrorIcon()));

        textFieldTaxRate.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldTaxRate.validate();
                }
            }
        });
        textFieldInterestRate.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldInterestRate.validate();
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

    private void printData(){
        for (int i = 0; i < data.size(); i++) {
            NPVRow temp = data.get(i);
            System.out.println("p:"+temp.periodProperty().intValue()+" outf: "+temp.outflowProperty().doubleValue()
                    +" in: "+temp.inflowProperty().doubleValue()+" ncf:"+temp.netCashFlowProperty().doubleValue()
                    +" npv:"+temp.netPresentValueProperty().doubleValue() +" Cnpv "+temp.cumulativeNPVProperty().doubleValue());
        }
    }

    private void addPeriods(int numPeriods) {
        data.clear();
        for (int i = 0; i <= numPeriods; i++) {
            data.add(new NPVRow( getDataIndex(),0,0,0,0,0) );
        }
        updateBarChart();
        final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
        currCountProp.set(data.size());
    }



    private void setupTable() {
        setupCellValueFactory(periodColumn,p->p.periodProperty().asObject());
        setupCellValueFactory(outflowColumn,p->p.outflowProperty().asObject());
        setupCellValueFactory(inflowColumn,p->p.inflowProperty().asObject());
        setupCellValueFactory(netCashFlowColumn,p->p.netCashFlowProperty().asObject());
        setupCellValueFactory(NPVColumn,p->p.netPresentValueProperty().asObject());
        setupCellValueFactory(cumulativeNVPColumn,p->p.cumulativeNPVProperty().asObject());

        outflowColumn.setCellFactory((TreeTableColumn<NPVRow, Double> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new DoubleTextFieldEditorBuilder());
        });
        outflowColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<NPVRow, Double> t) -> {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().outflowProperty().set(t.getNewValue());
            data = Projection.calculateNPV(data,getInterestRate(),getTaxRate());
            updateBarChart();
            //printData();

        });
        inflowColumn.setCellFactory((TreeTableColumn<NPVRow, Double> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new DoubleTextFieldEditorBuilder());
        });
        inflowColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<NPVRow, Double> t) -> {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().inflowProperty().set(t.getNewValue());
            //data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
            data = Projection.calculateNPV(data,getInterestRate(),getTaxRate());
            updateBarChart();
            //printData();
        });
        treeTableViewAdd.setOnMouseClicked((e) -> {
            data.add(new NPVRow(getDataIndex(),0,0,0,0,0));
            updateBarChart();
            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() + 1);
            //TODO make get textfield methods to make validation
            //data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
        });
        treeTableViewRemove.setOnMouseClicked((e) -> {
            data.remove(treeTableView.getSelectionModel().selectedItemProperty().get().getValue());
            reCalculatePeriods();
            updateBarChart();
            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() - 1);
            //TODO make get textfield methods to make validation
            //data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),Double.parseDouble(textFieldInterestRate.getText()));
        });
        treeTableView.setRoot(new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);
        treeTableView.setEditable(true);
        treeTableViewCount.textProperty().bind(Bindings.createStringBinding(()->PREFIX + treeTableView.getCurrentItemsCount()
                + POSTFIX, treeTableView.currentItemsCountProperty()));
        searchField.textProperty().addListener(setupSearchField(treeTableView));

    }
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<NPVRow> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(paybackProp -> {
                    final NPVRow temp = paybackProp.getValue();
                    return Integer.toString(temp.periodProperty().get()).contains(newVal)
                            || Double.toString(temp.outflowProperty().get()).contains(newVal)
                            || Double.toString(temp.inflowProperty().get()).contains(newVal)
                            || Double.toString(temp.netCashFlowProperty().get()).contains(newVal)
                            || Double.toString(temp.netCashFlowProperty().get()).contains(newVal)
                            || Double.toString(temp.cumulativeNPVProperty().get()).contains(newVal) ;
                });
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
    private <T> void setupCellValueFactory(JFXTreeTableColumn<NPVRow, T> column, Function<NPVRow, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<NPVRow, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }


}

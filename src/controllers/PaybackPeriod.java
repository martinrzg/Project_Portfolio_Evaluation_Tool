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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
 * Created by Martín Ruíz on 4/14/2017.
 */
public class PaybackPeriod implements Initializable {

    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    @FXML private AnchorPane root;
    @FXML private JFXTextField textFieldPrincipal;
    @FXML private JFXTextField textFieldInterestRate;
    @FXML private JFXComboBox<Integer> comboBoxPeriods;
    @FXML private BarChart<String, Number> barChart;

    // New Skeleton
    @FXML private JFXTreeTableView<PaybackRow> treeTableView;
    @FXML private JFXButton treeTableViewAdd;
    @FXML private JFXButton treeTableViewRemove;
    @FXML private JFXTextField searchField;
    @FXML private JFXTextField textFieldResult;
    @FXML private JFXTreeTableColumn<PaybackRow, Integer> periodColumn;
    @FXML private JFXTreeTableColumn<PaybackRow, Double> outflowColumn;
    @FXML private JFXTreeTableColumn<PaybackRow, Double> inflowColumn;
    @FXML private JFXTreeTableColumn<PaybackRow, Double> netCashFlowColumn;
    @FXML private JFXTreeTableColumn<PaybackRow, Double> cumulativeColumn;
    @FXML private Label treeTableViewCount;
    @FXML private JFXButton buttonClear;
    @FXML private JFXButton buttonSavePDF;


    private ObservableList<PaybackRow> data ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(tableView.toString());
        data = FXCollections.observableArrayList();
        data.add(new PaybackRow(0,0,0,0,0));


        setupTable();
        setupComboBox();
        setUpValidator();
        setUpBarChart();

        buttonSavePDF.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    getChartImage();
                    PDFMaker.makePDFPayback("Payback Period",getColumns(),getInterestRate(),data.size(),0,getTextPrincipal(),0,
                            Projection.paybackGetROIPeriod(data),data);
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
    private void getChartImage(){
        WritableImage image = barChart.snapshot(new SnapshotParameters(),null);
        File file = new File("PaybackChart.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    private String[] getColumns(){
        return new String[]{"Period", "Outflow", "Inflow","Net Cash Flow", "Cumulative"};
    }
    private void setUpBarChart(){
        barChart.getXAxis().setLabel("Period");
        barChart.getYAxis().setLabel("USD");
        barChart.setData(getChartData());
        //barChart.getData().get(0).getNode().setStyle("-fx-bar-fill:#F44336;");

    }

    private double getTextPrincipal(){
        if(textFieldPrincipal.getText()!= null && !textFieldPrincipal.getText().isEmpty()){
            try{
                double principal = Double.parseDouble(textFieldPrincipal.getText());
                return principal;
            }catch (Exception e){
                return 0;
            }
        }
        return 0;
    }


    private ObservableList<XYChart.Series<String, Number>> getChartData(){
        ObservableList<XYChart.Series<String, Number>> newData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.setName("Cash Flow");
        for (int i = 0; i < data.size(); i++) {
            PaybackRow temp = data.get(i);
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
        if(Projection.paybackGetROIPeriod(data) >=0){
           textFieldResult.setText(String.valueOf(Projection.paybackGetROIPeriod(data)));
        }else{
            textFieldResult.setText("N/A");
        }
        barChart.setData(getChartData());
    }

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
        for (int i = 0; i <= numPeriods; i++) {
            data.add(new PaybackRow( getDataIndex(),0,0,0,0) );
        }
        updateBarChart();
        final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
        currCountProp.set(data.size());
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
            data = Projection.calculatePayback(data,getTextPrincipal(),Double.parseDouble(textFieldInterestRate.getText()));
            updateBarChart();
            //printData();

        });
        inflowColumn.setCellFactory((TreeTableColumn<PaybackRow, Double> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new DoubleTextFieldEditorBuilder());
        });
        inflowColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<PaybackRow, Double> t) -> {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().inflowProperty().set(t.getNewValue());
            data = Projection.calculatePayback(data,getTextPrincipal(),Double.parseDouble(textFieldInterestRate.getText()));
            updateBarChart();
            //printData();
        });

        treeTableViewAdd.setOnMouseClicked((e) -> {
            data.add(new PaybackRow(getDataIndex(),0,0,0,0));
            updateBarChart();
            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() + 1);
            //TODO make get textfield methods to make validation
            data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),getInterestRate());
        });
        treeTableViewRemove.setOnMouseClicked((e) -> {
            data.remove(treeTableView.getSelectionModel().selectedItemProperty().get().getValue());
            reCalculatePeriods();
            updateBarChart();
            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() - 1);
            //TODO make get textfield methods to make validation
            data = Projection.calculatePayback(data,Double.parseDouble(textFieldPrincipal.getText()),getInterestRate());
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

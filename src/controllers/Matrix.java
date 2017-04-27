package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.*;
import models.MatrixRow;


/**
 * Created by gluqu on 23/04/2017.
 */
public class Matrix implements Initializable {

    @FXML
    private ComboBox cbp1, cbp2, cbp3, cbp4;
    @FXML
    private ComboBox cbs1, cbs2, cbs3, cbs4, cbs5, cbs6, cbs7, cbs8, cbs9, cbs10;
    @FXML
    private ComboBox cbo1, cbo2, cbo3, cbo4, cbo5, cbo6, cbo7, cbo8;
    @FXML
    private TextField tfp1, tfp2, tfp3, tfp4;
    @FXML
    private TextField tfs1, tfs2, tfs3, tfs4, tfs5, tfs6, tfs7, tfs8, tfs9, tfs10;
    @FXML
    private TextField tfo1, tfo2, tfo3, tfo4, tfo5, tfo6, tfo7, tfo8;
    @FXML
    private Text wvp1, wvp4, wvp3, wvp2;
    @FXML
    private Text wvs1, wvs2, wvs3, wvs4, wvs5, wvs6, wvs7, wvs8, wvs9, wvs10;
    @FXML
    private Text wvo1, wvo2, wvo3, wvo4, wvo5, wvo6, wvo7, wvo8;
    @FXML
    private Text wttp, wvttp;
    @FXML
    private Text wtts, wvtts;
    @FXML
    private Text wtto, wvtto;
    @FXML
    private Button btnCalc1;
    @FXML
    private Button btnCalc2, calcOther;
    @FXML
    private Button btnCalc3;
    @FXML
    private Button btnDecision;
    @FXML
    private Label wettp;
    @FXML
    private Label wetts;
    @FXML
    private Label wetto;

    @FXML
    private TextField pwt, swt, owt, wgt;
    @FXML
    private TextField pwvt, swvt, owvt, gtwv, wvd;
    @FXML
    private TextField pw, sw, ow;

    private ComboBox[] cbp = new ComboBox[4];
    private ComboBox[] cbs = new ComboBox[10];
    private ComboBox[] cbo = new ComboBox[8];

    private MatrixRow[] mrp = new MatrixRow[4];
    private MatrixRow[] mrs = new MatrixRow[10];
    private MatrixRow[] mro = new MatrixRow[8];

    private TextField[] tfp = new TextField[4];
    private TextField[] tfs = new TextField[10];
    private TextField[] tfo = new TextField[8];


    private Text[] tp = new Text[4];
    private Text[] ts = new Text[10];
    private Text[] to = new Text[8];

    public int ind = 0;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Declara opciones de combo box
        List<String> list = new ArrayList<String>();
        list.add("Low");
        list.add("Medium");
        list.add("High");
        ObservableList obList = FXCollections.observableList(list);

        //Accion de boton primario
        btnCalc1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calcWV1();
            }
        });

        //Accion de boton secundario
        btnCalc2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calcWV2();
            }
        });

        //Accion de boton secundario
        calcOther.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calcWV3();
            }
        });

        btnDecision.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                decision();
            }
        });

        //Agrega combo box primarios a arreglo
        cbp[0] = cbp1;        cbp[1] = cbp2;        cbp[2] = cbp3;        cbp[3] = cbp4;

        //Agrega text fields primarios a arreglo
        tfp[0] = tfp1;        tfp[1] = tfp2;        tfp[2] = tfp3;        tfp[3] = tfp4;

        //Agrega texto primario a arreglo
        tp[0] = wvp1;         tp[1] = wvp2;         tp[2] = wvp3;         tp[3] = wvp4;

        //Agrega combo box secundario a arreglo
        cbs[0] = cbs1;        cbs[1] = cbs2;        cbs[2] = cbs3;        cbs[3] = cbs4;
        cbs[4] = cbs5;        cbs[5] = cbs6;        cbs[6] = cbs7;        cbs[7] = cbs8;
        cbs[8] = cbs9;        cbs[9] = cbs10;

        //Agrega text fields secundario a arreglo
        tfs[0] = tfs1;        tfs[1] = tfs2;        tfs[2] = tfs3;        tfs[3] = tfs4;
        tfs[4] = tfs5;        tfs[5] = tfs6;        tfs[6] = tfs7;        tfs[7] = tfs8;
        tfs[8] = tfs9;        tfs[9] = tfs10;

        //Agrega texto secundario a arreglo
        ts[0] = wvs1;         ts[1] = wvs2;         ts[2] = wvs3;         ts[3] = wvs4;
        ts[4] = wvs5;         ts[5] = wvs6;         ts[6] = wvs7;         ts[7] = wvs8;
        ts[8] = wvs9;         ts[9] = wvs10;

        //Agrega combo box otros a arreglo
        cbo[0] = cbo1;        cbo[1] = cbo2;        cbo[2] = cbo3;        cbo[3] = cbo4;
        cbo[4] = cbo5;        cbo[5] = cbo6;        cbo[6] = cbo7;        cbo[7] = cbo8;

        //Agrega text fields other a arreglo
        tfo[0] = tfo1;        tfo[1] = tfo2;        tfo[2] = tfo3;        tfo[3] = tfo4;
        tfo[4] = tfo5;        tfo[5] = tfo6;        tfo[6] = tfo7;        tfo[7] = tfo8;

        //Agrega texto other a arreglo
        to[0] = wvo1;         to[1] = wvo2;         to[2] = wvo3;         to[3] = wvo4;
        to[4] = wvo5;         to[5] = wvo6;         to[6] = wvo7;         to[7] = wvo8;


        for(int i = 0; i < cbp.length; i++){
            initCB(cbp[i], obList, i);
        }

        for(int i = 0; i < cbs.length; i++){
            initCB(cbs[i], obList, i);
        }

        for(int i = 0; i < cbo.length; i++){
            initCB(cbo[i], obList, i);
        }

        for(int i = 0; i < mrp.length; i++){
            mrp[i] = new MatrixRow(i, 1, 1, 1, 0 );
        }

        for(int i = 0; i < mrs.length; i++){
            mrs[i] = new MatrixRow(i, 2, 1, 1, 0 );
        }

        for(int i = 0; i < mro.length; i++){
            mro[i] = new MatrixRow(i, 3, 1, 1, 0 );
        }

    }

    private void initCB(ComboBox cb, ObservableList ol, int index){
        cb.getItems().clear();
        cb.setItems(ol);
    }

    private void calcWV1(){
        getCBp();
        double wt = 0;
        double wvt = 0;
        double wet = 0;

        try {

            for (int i = 0; i < mrp.length; i++) {

                int rating = mrp[i].getRatind();

                mrp[i].setWeighting(Integer.parseInt(tfp[i].getText()));
                int weighting = mrp[i].getWeighting();

                int wv = (weighting / 5) * rating;

                wvt += wv;

                mrp[i].setWeighted(wv);
                tp[i].setText(wv + "");
            }

            for (int i = 0; i < mrp.length; i++) {
                wt += mrp[i].getWeighting();
            }

            if(wt == 100){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Total weighting grater than 100!");

                alert.showAndWait();
            }
            else {

                wet = (wvt / 100) * (Integer.parseInt(pw.getText()));

                wettp.setText(wet + "");
                wvttp.setText(wvt + "");
                wttp.setText(wt + "");
                pwvt.setText(wet + "");
                pwt.setText(pw.getText());
            }

        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Some fields might be empty!");

            alert.showAndWait();
        }
        catch (NumberFormatException e2){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Weighting field might be empty!");

            alert.showAndWait();
        }

        System.out.println("PUTAMADRE " + wet);
    }

    private void getCBp(){

        try {
            validateRatingp(cbp[0].getValue().toString(), 0);
            validateRatingp(cbp[1].getValue().toString(), 1);
            validateRatingp(cbp[2].getValue().toString(), 2);
            validateRatingp(cbp[3].getValue().toString(), 3);
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Some fields might be empty");

            alert.showAndWait();
        }
    }

    private void calcWV2(){
        getCBs();
        double wt = 0;
        double wvt = 0;
        double wet = 0;

        try {
            for (int i = 0; i < mrs.length; i++) {

                int rating = mrs[i].getRatind();

                mrs[i].setWeighting(Integer.parseInt(tfs[i].getText()));
                int weighting = mrs[i].getWeighting();

                int wv = (weighting / 5) * rating;

                wvt += wv;

                mrs[i].setWeighted(wv);
                ts[i].setText(wv + "");
            }

            for (int i = 0; i < mrs.length; i++) {
                wt += mrs[i].getWeighting();
            }

            if(wt == 100){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Total weighting grater than 100!");

                alert.showAndWait();
            }
            else{

                wet = (wvt / 100) * (Integer.parseInt(sw.getText()));


                wetts.setText((wet) + "");
                wvtts.setText(wvt + "");
                wtts.setText(wt + "");
                swvt.setText(wet + "");
                swt.setText(sw.getText());
            }

        }

        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Some fields might be empty!");

            alert.showAndWait();
        }
        catch (NumberFormatException e2){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Weighting field might be empty!");

            alert.showAndWait();
        }

        System.out.println("PUTAMADRE " + wet);
    }

    private void getCBs(){

        try {
            for (int i = 0; i < cbs.length; i++) {
                validateRatings(cbs[i].getValue().toString(), i);
            }
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Some fields might be empty");

            alert.showAndWait();
        }
    }

    private void calcWV3(){

        System.out.println("CALCWV3");

        getCBo();
        double wt = 0;
        double wvt = 0;
        double wet = 0;

        try {
            for (int i = 0; i < mro.length; i++) {

                int rating = mro[i].getRatind();
                System.out.println("PUTAMADRE " + rating);

                mro[i].setWeighting(Integer.parseInt(tfo[i].getText()));
                int weighting = mro[i].getWeighting();

                int wv = (weighting / 5) * rating;

                wvt += wv;

                mro[i].setWeighted(wv);
                to[i].setText(wv + "");
            }

            for (int i = 0; i < mro.length; i++) {
                wt += mro[i].getWeighting();
            }

            if(wt == 100){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Total weighting grater than 100!");

                alert.showAndWait();
            }
            else
            {
                wet = (wvt / 100) * (Integer.parseInt(ow.getText()));


                wetto.setText((wet) + "");
                wvtto.setText(wvt + "");
                wtto.setText(wt + "");
                owvt.setText((wet) + "");
                owt.setText(ow.getText());
            }

        }

        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Some fields might be empty!");

            alert.showAndWait();
        }
        catch (NumberFormatException e2){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Weighting field might be empty!");

            alert.showAndWait();
        }

    }

    private void getCBo(){

        try {
            for (int i = 0; i < cbo.length; i++) {
                validateRatingo(cbo[i].getValue().toString(), i);
            }
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Some fields might be empty");

            alert.showAndWait();
        }
    }

    private int validateRatingp(String newValue, int ind){

        try{
            switch (newValue){
                case "Low":
                    mrp[ind].setRatind(1);
                    return 1;
                case "Medium":
                    mrp[ind].setRatind(3);
                    return 3;
                case "High":
                    mrp[ind].setRatind(5);
                    return 5;
            }

        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ind " + ind);
        }
        return 0;
    }

    private int validateRatings(String newValue, int ind){

        try{
            switch (newValue){
                case "Low":
                    mrs[ind].setRatind(1);
                    return 1;
                case "Medium":
                    mrs[ind].setRatind(3);
                    return 3;
                case "High":
                    mrs[ind].setRatind(5);
                    return 5;
            }

        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ind " + ind);
        }
        return 0;
    }

    private int validateRatingo(String newValue, int ind){

        try{
            switch (newValue){
                case "Low":
                    mro[ind].setRatind(1);
                    return 1;
                case "Medium":
                    mro[ind].setRatind(3);
                    return 3;
                case "High":
                    mro[ind].setRatind(5);
                    return 5;
            }

        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ind " + ind);
        }
        return 0;
    }

    private void decision(){
        try{

            double pweighting = Double.parseDouble(pwt.getText());
            double sweighting = Double.parseDouble(swt.getText());
            double oweighting = Double.parseDouble(owt.getText());

            double pWeightV = Double.parseDouble(pwvt.getText());
            double sWeightV = Double.parseDouble(swvt.getText());
            double oWeightV = Double.parseDouble(owvt.getText());

            double gt = (pWeightV + sWeightV + oWeightV);

            if( gt >= 0 && gt < 50){
                wvd.setText("NO");
            }
            if( gt >= 50 && gt < 65){
                wvd.setText("CAN CONSIDER");
            }
            if( gt >= 65 && gt < 100){
                wvd.setText("YES");
            }
            System.out.println("gt " + gt);
            wgt.setText((pweighting + sweighting + oweighting) + "");
            gtwv.setText((pWeightV + sWeightV + oWeightV) + "");

        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You have to make all 3 Considerations");

            alert.showAndWait();
        }
    }
}

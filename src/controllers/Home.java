package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Session;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.jfoenix.controls.JFXDialog.DialogTransition.CENTER;

/**
 * Created by Martín Ruíz on 4/12/2017.
 */
public class Home implements Initializable
{
    @FXML private MenuItem menuItemExit;
    @FXML private MenuBar MenuBar;
    @FXML private AnchorPane tabContainer;
    @FXML private JFXTabPane tabPane;
    @FXML private JFXDialog dialog;
    @FXML private AnchorPane root;
    @FXML private StackPane mainStackPane;


    private Parent parent;
    private Stage stage;
    private Scene scene;

    public Home(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("..//fxml/Home.fxml"));
            fxmlLoader.setController(this);
            parent = (Parent) fxmlLoader.load();
            // set height and width here for this home scene
            scene = new Scene(parent, 1900  , 1000);
            final ObservableList<String> stylesheets = scene.getStylesheets();
            stylesheets.addAll(this.getClass().getResource("..//css/jfoenix-components.css").toExternalForm(),
                               this.getClass().getResource("..//css/jfoenix-design.css").toExternalForm(),
                               this.getClass().getResource("..//css/jfoenix-main-demo.css").toExternalForm(),
                               this.getClass().getResource("..//css/tab.css").toExternalForm()
            );

        } catch (IOException e) {
            System.out.println("Error loading resource "+e.toString());
            // manage the exception
        }
    }
    public void displayHomeScreen(Stage stage){
        this.stage = stage;
        stage.setTitle("Project Portfolio Evaluation Tool - Home");
        stage.setScene(scene);

        // Must write

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabManager(0);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tabManager(tabPane.getSelectionModel().getSelectedIndex());
        });
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            tabPane.getTabs().get(i).setTooltip(new Tooltip(Utils.tabMessages[i]));
        }
        root.getChildren().remove(dialog);
    }
    @FXML
    void showAbout(ActionEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Developers"));
        content.setBody(new Text(" Project Evaluation Portfolio \n" +
                                 "@Martín Ruíz Guadarrama A01361252\n "+
                                 "@Eduardo Francisco Llamas A01364075\n "+
                                 "@Gabriel Luque Miranda A01364318\n\n\n "+
                                 "Version Beta 0.6"));
        JFXDialog jfxDialog = new JFXDialog(mainStackPane,content, CENTER);

        JFXButton button = new JFXButton("Okay");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jfxDialog.close();
            }
        });
        content.setActions(button);
        jfxDialog.show();


    }

    @FXML
    void showInstructions(ActionEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Instructions"));
        content.setBody(new Text("Payback period \n" +
                "For the payback period module, the user is intended to fist enter the number of periods that will be used, the principal and the interest rate. \n" +
                "At this point a table should have been displayed for the user to enter both inflow and outflow for each period. The principal should be added manually\n" +
                " as the outflow of period 0. As the user enters the cash flows, the interface will show the respective cumulative cash flow numerically and graphically.\n" +
                " After generating the results, it is possible to create a PDF report\n" +
                "\n" +
                "NPV\n" +
                "For the Net present value |module, the user is intended to first enter the number of periods that will be used, the interest rate, \n" +
                "the tax rate. At this point a table should have been displayed for the user to enter both inflow and outflow for each period. If the \n" +
                "outflow on the period 0 is not displayed, it should be added manually. As the user enters the cash flows, the interface will show the \n" +
                "respective cumulative cash flow and the net cash flow. After generating the results, it is possible to create a PDF report.\n" +
                "\n" +
                "Project Screening matrix\n" +
                "For the Screening matrix module, the user will have to go through all the tabs shown entering the information required. The module will \n" +
                "not display information if some data is missing. At the end of entering the data on the first 3 tabs, the user should go to the results \n" +
                "tab and select the generate button, that will display the resultant decision considering the result on the previous tabs. The total \n" +
                "weighting on each tab should add up to 100, not more not less. Each category should be selecting considering the rubric shown on the \n" +
                "right side of each tab. After generating the results, it is possible to create a PDF report.\n" +
                "\n" +
                "Checklist\n" +
                "For the Checklist module, the user should write the answer to each question, and will be able to print the results to a pdf.\n" +
                "\n" +
                "Depreciation\n" +
                "For the depreciation module, the user is intended to first enter the number of periods that will be used, tax rate, year of start, \n" +
                "salvage value, category (method), salvage period and principal. Then the user must select the calculate option to generate the results \n" +
                "that will be shown both numerically and graphically. After generating the results, it is possible to create a PDF report.\n"));

        JFXDialog jfxDialog = new JFXDialog(mainStackPane,content, CENTER);

        JFXButton button = new JFXButton("Okay");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jfxDialog.close();
            }
        });
        content.setActions(button);
        jfxDialog.show();
    }



    private void tabManager(int selectedIndex) {

        String srcResource = "..//fxml/";

        switch (selectedIndex) {
            case 0:             /* PayBack Period */
                System.out.println("Payback Period");
                srcResource += "PaybackPeriod";
                break;
            case 1:             /*      NPV     */
                System.out.println("NPV");
                srcResource += "NPV";
                break;
            case 2:             /*   CheckList  */
                System.out.println("Checklist");
                srcResource += "Checklist";
                break;
            case 3:             /*    Matrix    */
                System.out.println("Matrix");
                srcResource += "Matrix";
                break;
            case 4:             /* Depreciation */
                System.out.println("Depreciation");
                srcResource += "Depreciation";
                break;
            default:
                break;
        }
        srcResource +=".fxml";
        try {

            AnchorPane newTabContent = FXMLLoader.load(getClass().getResource(srcResource));
            tabContainer.getChildren().setAll(newTabContent);
            AnchorPane.setTopAnchor(newTabContent, (double) 0);
            AnchorPane.setBottomAnchor(newTabContent, (double) 0);
            AnchorPane.setLeftAnchor(newTabContent, (double) 0);
            AnchorPane.setRightAnchor(newTabContent, (double) 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }


}


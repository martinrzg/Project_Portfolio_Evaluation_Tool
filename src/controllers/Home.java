package controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Session;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martín Ruíz on 4/12/2017.
 */
public class Home implements Initializable
{
    @FXML private MenuItem menuItemExit;
    @FXML private MenuBar MenuBar;
    @FXML private AnchorPane tabContainer;
    @FXML private JFXTabPane tabPane;

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


package controllers;
/**
 * Created by Martín Ruíz on 4/12/2017.
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Welcome implements Initializable{

    @FXML private JFXTextField textFieldEvaluatorName;
    @FXML private AnchorPane rootPane;
    @FXML private JFXButton buttonStart;
    @FXML private JFXTextField textFieldProyectID;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public Welcome() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("..//fxml/Welcome.fxml"));
        fxmlLoader.setController(this);
        try{
            parent =  (Parent) fxmlLoader.load();
            scene = new Scene(parent,800,600);
            final ObservableList<String> stylesheets = scene.getStylesheets();
            stylesheets.addAll(this.getClass().getResource("..//css/jfoenix-components.css").toExternalForm(),
                    this.getClass().getResource("..//css/jfoenix-design.css").toExternalForm(),
                    this.getClass().getResource("..//css/jfoenix-main-demo.css").toExternalForm());
        }catch (IOException e){
            System.out.println("Error loading windows" +e.toString());
        }
    }

    public void launchWelcomeScene(Stage stage) {
        this.stage = stage;
        stage.setTitle("Project Portfolio Evaluation Tool - Welcome");
        stage.setScene(scene);
        //Don't forget to add below code in every controller
        //stage.hide();
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println( Paths.get(".").toAbsolutePath().normalize().toString() );


        RequiredFieldValidator projectIDValidator = new RequiredFieldValidator();
        RequiredFieldValidator evaluatorNameValidator = new RequiredFieldValidator();

        textFieldProyectID.getValidators().add(projectIDValidator);
        textFieldEvaluatorName.getValidators().add(evaluatorNameValidator);

        projectIDValidator.setMessage("No project ID/name given");
        evaluatorNameValidator.setMessage("No evaluator name given");

        try {
            Image iconError = new Image(new FileInputStream("resources\\icons\\ic_error_black_24dp_1x.png"));
            projectIDValidator.setIcon(new ImageView(iconError));
            evaluatorNameValidator.setIcon(new ImageView(iconError));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        textFieldEvaluatorName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldEvaluatorName.validate();
                }
            }
        });

        textFieldProyectID.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    textFieldProyectID.validate();
                }
            }
        });
    }

    @FXML
    void makeStart(ActionEvent event) throws IOException {
        if(textFieldEvaluatorName.validate() && textFieldProyectID.validate()){
            Session.getInstance().setProjectID(textFieldProyectID.getText());
            Session.getInstance().setEvaluatorName(textFieldEvaluatorName.getText());

            new Home().displayHomeScreen(stage);
            //AnchorPane homePane = FXMLLoader.load(getClass().getResource("..//fxml/Home.fxml"));
            //rootPane.getChildren().setAll(homePane);
        }
    }



}

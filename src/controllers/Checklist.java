package controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DefaultStringConverter;
import models.ChecklistRow;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Created by Martín Ruíz on 4/25/2017.
 */
public class Checklist implements Initializable {

    @FXML private JFXTextField searchField;
    @FXML private AnchorPane root;
    @FXML private Label treeTableViewCount;
    @FXML private JFXTreeTableView<ChecklistRow> treeTableView;
    @FXML private JFXTreeTableColumn<ChecklistRow, String> questionColumn;
    @FXML private JFXTreeTableColumn<ChecklistRow, String> topicColumn;
    @FXML private JFXTreeTableColumn<ChecklistRow, String> answerColumn;

    private ObservableList<ChecklistRow> data;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        getTableData();
        setupTable();
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ChecklistRow, T> column, Function<ChecklistRow, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChecklistRow, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void setupTable() {
        setupCellValueFactory(topicColumn   , ChecklistRow::topicProperty);
        setupCellValueFactory(questionColumn, ChecklistRow::questionProperty);
        setupCellValueFactory(answerColumn  ,ChecklistRow::answerProperty);

        answerColumn.setCellFactory((TreeTableColumn<ChecklistRow, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        answerColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<ChecklistRow, String> t) -> {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().answerProperty().set(t.getNewValue());
        });

        treeTableView.setRoot(new RecursiveTreeItem<ChecklistRow>(data, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);
        treeTableView.setEditable(true);
        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<ChecklistRow> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(paybackProp -> {
                    final ChecklistRow temp = paybackProp.getValue();
                    return  temp.getTopic().contains(newVal)
                            || temp.getQuestion().contains(newVal)
                            || temp.getAnswer().contains(newVal);
                });
    }

    private void getTableData() {
        for (int i = 0; i < 20; i++) {
            data.add(new ChecklistRow("","",""));
        }
        data.get(0).setTopic("Strategy/alignment");
        data.get(0).setQuestion("What specific organization strategy does this project align with?");

        data.get(1).setTopic("Driver");
        data.get(1).setQuestion("What business problem does the project solve?");
        data.get(2).setTopic("Success metrics");
        data.get(2).setQuestion("How will measure success?");
        data.get(3).setTopic("Sponsorship");
        data.get(3).setQuestion("Who is the project sponsor?");
        data.get(4).setTopic("Risk");
        data.get(4).setQuestion("What is the impact of not doing this project?");
        data.get(5).setTopic("Risk");
        data.get(5).setQuestion("What is the project risk to our organization?");
        data.get(6).setTopic("Risk");
        data.get(6).setQuestion("Where does the proposed project fit in our risk profile?");
        data.get(7).setTopic("Benefits, value ");
        data.get(7).setQuestion("What is the value of the project organization?");
        data.get(8).setTopic("Benefits, value");
        data.get(8).setQuestion("When will the project shows result?");
        data.get(9).setTopic("Objectives");
        data.get(9).setQuestion("What are the project objectives?");
        data.get(10).setTopic("Organization Culture");
        data.get(10).setQuestion("Is our organization culture right for this type of project?");
        data.get(11).setTopic("Resources");
        data.get(11).setQuestion("Will internal resources be available for this project?");
        data.get(12).setTopic("Approach");
        data.get(12).setQuestion("Will we build or buy?");
        data.get(13).setTopic("Schedule");
        data.get(13).setQuestion("How long will this project take?");
        data.get(14).setTopic("Schedule");
        data.get(14).setQuestion("Is the timeline realistic?");
        data.get(15).setTopic("Training/resources");
        data.get(15).setQuestion("Will staff training be required?");
        data.get(16).setTopic("Finance/portfolio");
        data.get(16).setQuestion("What is the estimated cost of the project?");
        data.get(17).setTopic("Portfolio");
        data.get(17).setQuestion("Is this a new initiative or path of an existing initiative?");
        data.get(18).setTopic("Portfolio");
        data.get(18).setQuestion("How does this project interact with current projects?");
        data.get(19).setTopic("Technology");
        data.get(19).setQuestion("Is the technology available or new?");
    }
}

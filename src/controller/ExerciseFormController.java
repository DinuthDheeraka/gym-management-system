package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.NavigateUI;
import view.tm.ExerciseTM;
import view.tm.MemberTM;
import view.tm.SupplimentTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExerciseFormController implements Initializable {

    public TableView<ExerciseTM> exercisesTbl;
    public TableColumn colExerciseId;
    public TableColumn colExerciseName;
    public TableColumn colExerciseNote;
    public TextField txtSearchBar;

    private String exerciseId;
    private String description;
    private String note;

    private Parent parent;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colExerciseId.setCellValueFactory(new PropertyValueFactory<>("exerciseId"));
        colExerciseName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colExerciseNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        setExercisesToTable();

        exercisesTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setMemberData((ExerciseTM) newValue);
                });
    }

    private void setMemberData(ExerciseTM newValue) {
        exerciseId = newValue.getExerciseId();
        description = newValue.getDescription();
        note = newValue.getNote();
    }

    public void addExerciseOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddExercisesForm");
    }

    public void setExercisesToTable(){
        ObservableList<ExerciseTM> observableList = FXCollections.observableArrayList(
                ExerciseCrudController.getAllExercises()
        );
        exercisesTbl.setItems(observableList);
    }

    public void refreshCtmOnAction(ActionEvent actionEvent) {
        setExercisesToTable();
    }

    public void txtSearchBarOnAction(ActionEvent actionEvent) {
        searchExercise();
    }

    public void searhBtnOnAction(ActionEvent actionEvent) {
        searchExercise();
    }

    public void deleteCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            // delete
            ExerciseCrudController.deleteExercise(exerciseId);
        }
    }

    public void updateCtmOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddExercisesForm.fxml"));
        parent = fxmlLoader.load();

        AddExercisesFormController addExercisesFormController = fxmlLoader.getController();
        addExercisesFormController.setValuesForTextFields(
                exerciseId,description,note,
                "UPDATE","ADD","UPDATE EXERCISE"
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);

    }

    public void txtSearchBarOnKeyReleased(KeyEvent keyEvent) {
        searchExercise();
    }

    public void searchExercise(){
        if(txtSearchBar.getText().startsWith("EXE")){
            ObservableList<ExerciseTM> ob = FXCollections.observableArrayList(
                    ExerciseCrudController.searchExercisesById("%"+txtSearchBar.getText()+"%")
            );
            exercisesTbl.setItems(ob);
        }else{
            ObservableList<ExerciseTM> ob1 = FXCollections.observableArrayList(
                    ExerciseCrudController.searchExercisesByName("%"+txtSearchBar.getText()+"%")
            );

            ObservableList<ExerciseTM> ob2 = FXCollections.observableArrayList(
                    ExerciseCrudController.searchExercisesByNote("%"+txtSearchBar.getText()+"%")
            );

            for(ExerciseTM tm : ob2){
                if(!isExists(ob1,tm)){
                    ob1.add(tm);
                }
            }

            exercisesTbl.setItems(ob1);
        }
    }


    public boolean isExists(ObservableList<ExerciseTM> ob1, ExerciseTM tm){
        boolean isExists = false;
        for(ExerciseTM tm1 : ob1){
            if(tm1.equals(tm.getExerciseId())){
                isExists = true;
            }
        }
        return isExists;
    }
}

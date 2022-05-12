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
import view.tm.MealPlanTM;
import view.tm.TrainerTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MealPlansFormController implements Initializable {

    public TableView mealPlansTbl;
    public TableColumn colMealPlanId;
    public TableColumn colMealPlanDescription;
    public TextField txtSearchBar;

    private String mealPlanId;
    private String description;

    private Parent parent;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMealPlanId.setCellValueFactory(new PropertyValueFactory<>("mealPlanId"));
        colMealPlanDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        setMealPlansToTable();

        mealPlansTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setMealPlanData((MealPlanTM) newValue);
                });
    }

    private void setMealPlanData(MealPlanTM newValue) {
        mealPlanId = newValue.getMealPlanId();
        description = newValue.getDescription();
    }

    public void addMealPlanOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddMealPlanForm");
    }

    public void setMealPlansToTable(){
        ObservableList<MealPlanTM> observableList = FXCollections.observableArrayList(
                MealPlansCrudController.getAllMealPlans()
        );
        mealPlansTbl.setItems(observableList);
    }

    public void refreshTableCtmOnAction(ActionEvent actionEvent) {
        setMealPlansToTable();
    }

    public void txtSearchBarOnAction(ActionEvent actionEvent) {
        searchMealPlan();
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
        searchMealPlan();
    }

    public void updateTableCtmOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddMealPlanForm.fxml"));
        parent = fxmlLoader.load();

        AddMealPlanFormController addMealPlanFormController = fxmlLoader.getController();
        addMealPlanFormController.setValuesForTextFields(
                mealPlanId,description,"UPDATE"
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);
    }

    public void deleteTableCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            // delete
            MealPlansCrudController.deleteMealPlan(mealPlanId);
        }
    }

    public void txtSearchBarOnKeyReleased(KeyEvent keyEvent) {
        searchMealPlan();
    }

    public void searchMealPlan(){
        if(txtSearchBar.getText().startsWith("MLP-")){
            ObservableList<MealPlanTM> ob = FXCollections.observableArrayList(
                    MealPlansCrudController.searchMealPlansById("%"+txtSearchBar.getText()+"%")
            );
            mealPlansTbl.setItems(ob);
        }else{
            ObservableList<MealPlanTM> ob = FXCollections.observableArrayList(
                    MealPlansCrudController.searchMealPlansByDescription("%"+txtSearchBar.getText()+"%")
            );
            mealPlansTbl.setItems(ob);
        }
    }
}

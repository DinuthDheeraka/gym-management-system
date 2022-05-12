package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.CrudUtil;
import util.NavigateUI;
import util.ShowNotification;
import view.tm.SupplierTM;
import view.tm.TrainerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageTrainerFormController implements Initializable {
    public TableView<TrainerTM> trainerTable;
    public TableColumn colTrainerNic;
    public TableColumn colTrainerName;
    public TableColumn colTrainerAddress;
    public TableColumn colTrainerEmail;
    public TableColumn colTrainerTeleNo;
    public TableColumn colTrainerAge;

    private String ttrainerNic;
    private String ttrainerName;
    private String ttrainerAddress;
    private String ttrainerTele;
    private String ttrainerEmail;
    private int tage;

    public TextField txtTrainerSearchBar;

    private Parent parent;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colTrainerNic.setCellValueFactory(new PropertyValueFactory<>("trainerNic"));
        colTrainerName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
        colTrainerAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colTrainerAddress.setCellValueFactory(new PropertyValueFactory<>("trainerAddress"));
        colTrainerEmail.setCellValueFactory(new PropertyValueFactory<>("trainerEmail"));
        colTrainerTeleNo.setCellValueFactory(new PropertyValueFactory<>("trainerTele"));

        setDataToTrainerTable();

        trainerTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setTrainerData((TrainerTM) newValue);
                });

    }

    private void setTrainerData(TrainerTM newValue) {
        ttrainerNic = newValue.getTrainerNic();
        ttrainerAddress = newValue.getTrainerAddress();
        ttrainerEmail = newValue.getTrainerEmail();
        ttrainerName = newValue.getTrainerName();
        tage = newValue.getAge();
        ttrainerTele = newValue.getTrainerTele();
    }

    public void addTrainerOnAction(ActionEvent actionEvent) throws IOException {

        NavigateUI.navigator.setNewStage("AddTrainerForm");
    }

    public void setDataToTrainerTable(){
        ObservableList<TrainerTM> observableList = FXCollections.observableArrayList(
                TrainerCrudController.getAllTrainers()
        );
        trainerTable.setItems(observableList);
    }

    public void refreshCtmOnAction(ActionEvent actionEvent) {
        setDataToTrainerTable();
    }

    public void deleteCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            if(!isAssignedTrainer(ttrainerNic)){
                TrainerCrudController.deleteTrainer(ttrainerNic);
            }else{
                ShowNotification.show(new Image("asserts/x-button_ccexpress.png"),
                        "Trainer "+ttrainerNic+" Assigned for a Schedule!");
            }
        }
        setDataToTrainerTable();
    }

    public void UpdateCtmOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddTrainerForm.fxml"));
        parent = fxmlLoader.load();

        AddTrainerFormController addTrainerFormController = fxmlLoader.getController();
        addTrainerFormController.setValuesForTextFields(
                ttrainerNic,tage,ttrainerTele,ttrainerEmail,ttrainerAddress,ttrainerName,
                "ADD","UPDATE","UPDATE TRAINER"
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);

    }

    public void txtTrainerSearchBarKeyReleased(KeyEvent keyEvent) {

        trainerSearchBtnOnAction(new ActionEvent());
    }

    public void trainerSearchBtnOnAction(ActionEvent actionEvent) {
        if(txtTrainerSearchBar.getText().startsWith("TRA-")){
            ObservableList<TrainerTM> ob = FXCollections.observableArrayList(
                    TrainerCrudController.searchTrainerById("%"+txtTrainerSearchBar.getText()+"%")
            );
            trainerTable.setItems(ob);
        }else{
            ObservableList<TrainerTM> ob = FXCollections.observableArrayList(
                    TrainerCrudController.searchTrainerByName("%"+txtTrainerSearchBar.getText()+"%")
            );
            trainerTable.setItems(ob);
        }
    }

    public boolean isAssignedTrainer(String id){
        ArrayList<String> arrayList = ScheduleCrudController.getAssignedTrainersIds();
        boolean isAssign = false;
        for(String s : arrayList){
            if(s.equals(id)){
                isAssign = true;
            }
        }
        return  isAssign;
    }
}

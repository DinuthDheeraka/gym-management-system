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
import view.tm.EquipmentTM;
import view.tm.ScheduleTM;
import view.tm.SupplimentTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EquipmentsFormController implements Initializable {
    public TableView<EquipmentTM> equipmentTbl;
    public TableColumn equipmentTblColEqpId;
    public TableColumn equipmentTblColEqpType;
    public TableColumn equipmentTblColEqpDescription;
    public TableColumn equipmentTblColEqpQoh;
    public TextField txtSearchBar;

    private String id;
    private String type;
    private String description;
    private int qoh;

    private Parent parent;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        equipmentTblColEqpDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        equipmentTblColEqpQoh.setCellValueFactory(new PropertyValueFactory<>("qoh"));
        equipmentTblColEqpType.setCellValueFactory(new PropertyValueFactory<>("type"));
        equipmentTblColEqpId.setCellValueFactory(new PropertyValueFactory<>("id"));

        setDataToEqpTbl();

        equipmentTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setScheduleData((EquipmentTM) newValue);
                });
    }

    private void setScheduleData(EquipmentTM newValue) {
        id = newValue.getId();
        description = newValue.getDescription();
        qoh = newValue.getQoh();
        type = newValue.getType();
    }

    public void addEquipmentOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddEqipmentForm");
    }

    public void setDataToEqpTbl(){
        ObservableList<EquipmentTM> ob = FXCollections.observableArrayList(
                EquipmentCrudController.getAllEquipments()
        );
        equipmentTbl.setItems(ob);
    }

    public void refreshCtmOnAction(ActionEvent actionEvent) {
        setDataToEqpTbl();
    }

    public void updateCtmOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddEqipmentForm.fxml"));
        parent = fxmlLoader.load();

        AddEqipmentFormController addEqipmentFormController = fxmlLoader.getController();
        addEqipmentFormController.setValuesForTextFields(
                id,description,type,qoh
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);
    }

    public void deleteCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            // delete
            EquipmentCrudController.deleteEquipment(id);
        }
    }

    public void searchBarOnAction(ActionEvent actionEvent) {
        searchEqp();
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
        searchEqp();
    }

    public void txtSearchBarOnKeyReleased(KeyEvent keyEvent) {
        searchEqp();
    }

    public void searchEqp(){

        if(txtSearchBar.getText().startsWith("EQP-")){
            ObservableList<EquipmentTM> ob = FXCollections.observableArrayList(
                    EquipmentCrudController.searchEquipmentById(txtSearchBar.getText())
            );
            equipmentTbl.setItems(ob);
        }else{

            ObservableList<EquipmentTM> ob1 = FXCollections.observableArrayList(
                    EquipmentCrudController.searchEquipmentByName("%"+txtSearchBar.getText()+"%")
            );

            ObservableList<EquipmentTM> ob2 = FXCollections.observableArrayList(
                    EquipmentCrudController.searchEquipmentByType("%"+txtSearchBar.getText()+"%")
            );

            for(EquipmentTM tm : ob2){
                if(!isExists(ob1,tm)){
                    ob1.add(tm);
                }
            }

            equipmentTbl.setItems(ob1);
        }
    }



    public boolean isExists(ObservableList<EquipmentTM> ob1, EquipmentTM tm){
        boolean isExists = false;
        for(EquipmentTM tm1 : ob1){
            if(tm1.equals(tm.getId())){
                isExists = true;
            }
        }
        return isExists;
    }
}

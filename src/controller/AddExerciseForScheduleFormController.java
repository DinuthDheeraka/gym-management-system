package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddExerciseForScheduleFormController implements Initializable {
    public JFXComboBox cmbxScheduleIds;
    public JFXComboBox cmbxExerciseIds;
    public JFXComboBox cmbxAffectedAreas;
    public JFXTextArea txtInstructions;
    public JFXButton addBtn;

    public String tScheduleId;
    public String tExericseId;
    public String affect;
    public String instructions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addExercisesToCmbx();
        setAffectingAreasToCmbx();
        addBtn.setDisable(true);
        addScheduleToCmbx();
        if(txtInstructions.getText().isEmpty()){
            addBtn.setDisable(true);
        }
    }

    public void addExerciseToScheduleCloseBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void scheduleExerciseAddBtnOnAction(ActionEvent actionEvent) {
        if(addBtn.getText().equals("ADD")){
            ScheduleCrudController.addExercisesToSchedule(
                    getExerciseId(cmbxExerciseIds.getSelectionModel().getSelectedIndex()),
                    String.valueOf(cmbxScheduleIds.getSelectionModel().getSelectedItem()),
                    String.valueOf(cmbxAffectedAreas.getSelectionModel().getSelectedItem()),
                    txtInstructions.getText());
        }else{
            ScheduleCrudController.updateScheduleExercise(
                    getExerciseId(cmbxExerciseIds.getSelectionModel().getSelectedIndex()),
                    String.valueOf(cmbxAffectedAreas.getSelectionModel().getSelectedItem()),
                    txtInstructions.getText(),tScheduleId,tExericseId,affect,instructions
            );

            System.out.println(""+tScheduleId+"-"+tExericseId+"-"+affect+"-"+instructions);
        }

        //System.out.println(cmbxExerciseIds.getSelectionModel().getSelectedIndex());
    }

    public void addExercisesToCmbx(){
        ObservableList<String> observableList = FXCollections.observableArrayList(
                ExerciseCrudController.getExercisesIdsWithDescription()
        );

        cmbxExerciseIds.setItems(observableList);
    }

    public void addScheduleToCmbx(){
        ObservableList<String> observableList = FXCollections.observableArrayList(
                ScheduleCrudController.getSchedulesIds()
        );

        cmbxScheduleIds.setItems(observableList);
    }

    public void setAffectingAreasToCmbx(){
        String[] ars = {"ABS","Chest","Shoulders","Legs"};
        ArrayList<String> arrayList = new ArrayList();
        for(String x : ars){
            arrayList.add(x);
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(arrayList);
        cmbxAffectedAreas.setItems(observableList);

    }

    public String getExerciseId(int index){
        ArrayList<String> arrayList = ExerciseCrudController.getExercisesIds();
        return arrayList.get(index);
    }

    public void validate(KeyEvent keyEvent) {

        if(!txtInstructions.getText().isEmpty()){
            addBtn.setDisable(false);
        }else{
            addBtn.setDisable(true);
        }
    }

    public void setValuesForTextFields(Object... params){
        cmbxScheduleIds.getSelectionModel().clearAndSelect((Integer)params[0]);
        cmbxExerciseIds.getSelectionModel().clearAndSelect((Integer)params[1]);
        cmbxAffectedAreas.getSelectionModel().clearAndSelect((Integer)params[2]);
        txtInstructions.setText(String.valueOf(params[3]));
        this.tScheduleId = String.valueOf(params[4]);
        this.tExericseId = String.valueOf(params[5]);
        this.affect = String.valueOf(params[6]);
        this.instructions = String.valueOf(params[7]);
        addBtn.setText("UPDATE");
    }
}

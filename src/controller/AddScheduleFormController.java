package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddScheduleFormController implements Initializable {
    public JFXButton addScheduleAddBtn;
    public JFXButton addScheduleUpdateBtn;
    public Label addScheduleTitle;
    public JFXTextField txtScheduleId;
    public JFXTextField txtScheduleDPW;
    public JFXDatePicker txtScheduleStartDate;
    public JFXDatePicker txtScheduleEndDate;
    public JFXComboBox cmbxAssignTrainer;
    public JFXComboBox cmbxAssignMealPlan;

    public void setValuesForTextFields(Object... params) {
        txtScheduleId.setText(String.valueOf(params[0]));
        txtScheduleDPW.setText(String.valueOf(params[1]));
        cmbxAssignTrainer.getSelectionModel().clearAndSelect(Integer.valueOf((Integer) params[2]));
        cmbxAssignMealPlan.getSelectionModel().clearAndSelect(Integer.valueOf((Integer) params[3]));
        txtScheduleStartDate.setValue(LocalDate.parse(String.valueOf(params[4])));
        txtScheduleEndDate.setValue(LocalDate.parse(String.valueOf(params[5])));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataToTrainerIdCmbx();
        loadDataToMealPlanCmbx();
        txtScheduleId.setText(genarateId());
        txtScheduleId.setEditable(false);
        addScheduleAddBtn.setDisable(true);
    }

    public void addScheduleCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addScheduleAddBtnOnAction(ActionEvent actionEvent) {
        if(addScheduleAddBtn.getText().equals("ADD")){
            ScheduleCrudController.insertSchedule(txtScheduleId.getText(),txtScheduleStartDate.getValue(),
                    txtScheduleEndDate.getValue(),txtScheduleDPW.getText(),
                    cmbxAssignMealPlan.getSelectionModel().getSelectedItem(),
                    cmbxAssignTrainer.getSelectionModel().getSelectedItem());
            txtScheduleId.setText(genarateId());
        }else{
            ScheduleCrudController.updateSchedule(
                    txtScheduleId.getText(),txtScheduleStartDate.getValue(),txtScheduleEndDate.getValue(),
                    txtScheduleDPW.getText(),cmbxAssignMealPlan.getSelectionModel().getSelectedItem(),
                    cmbxAssignTrainer.getSelectionModel().getSelectedItem(),txtScheduleId.getText()
            );
        }
    }

    public void addScheduleUpdateBtnOnAction(ActionEvent actionEvent) {
        if(addScheduleAddBtn.getText().equals("ADD")){
            System.out.println(cmbxAssignMealPlan.getSelectionModel().getSelectedItem());
            addScheduleAddBtn.setText("UPDATE");
            addScheduleUpdateBtn.setText("ADD");
        }else{
            addScheduleAddBtn.setText("ADD");
            addScheduleUpdateBtn.setText("UPDATE");
        }
    }

    public void loadDataToTrainerIdCmbx(){
        ObservableList<String> observableList = FXCollections.observableArrayList(
                TrainerCrudController.getTrainersIds()
        );

        cmbxAssignTrainer.setItems(observableList);
    }

    public void loadDataToMealPlanCmbx(){
        ObservableList<String> observableList = FXCollections.observableArrayList(
                MealPlansCrudController.getMealPlansIds()
        );

        cmbxAssignMealPlan.setItems(observableList);
    }

    public void validate(KeyEvent keyEvent) {

        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(SCH-)[0-9]{3,6}$");
        Pattern dpwP = Pattern.compile("[1-7]{1}");

        map.put(txtScheduleId,idP);
        map.put(txtScheduleDPW,dpwP);

        boolean b = Validator.validate(map,addScheduleAddBtn);
        if(b==false){
            addScheduleAddBtn.setDisable(true);
        }else{
            addScheduleAddBtn.setDisable(false);
        }
    }

    public String genarateId(){
        return IdsGenerator.genarateId("SCH-",
                ScheduleCrudController.getScheduleLastId());
        //return IdsGenerator.genarate(Mods.SCHEDULE,
                //ScheduleCrudController.getScheduleLastId());
    }
}

package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;
import view.tm.ExerciseTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddMemberFormController implements Initializable {

    public JFXTextField txtMemberName;
    public JFXTextField txtMemberAddress;
    public JFXTextField txtMemberEmail;
    public JFXTextField txtMemberTele;
    public JFXTextField txtMemberWeight;
    public JFXTextField txtMemberHeight;
    public JFXComboBox cmbxSchedules;
    public JFXTextField txtMemberId;
    public JFXTextField txtMemberAge;
    public JFXDatePicker dpJoinedDate;
    public Label lblAddMember;
    public JFXButton addMemberUpDateBtn;
    public JFXButton addMemberAddBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setScheduleIdsToCmbx();
        txtMemberId.setText(genarateId());
        txtMemberId.setEditable(false);
        addMemberAddBtn.setDisable(true);
    }


    public void addMemberCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addmemberBtnOnAction(ActionEvent actionEvent) {
        if(addMemberAddBtn.getText().equals("UPDATE")){
            MemberCrudController.updateMember(
                    txtMemberName.getText(),
                    txtMemberAddress.getText(),txtMemberEmail.getText(),txtMemberTele.getText(),
                    txtMemberAge.getText(),String.valueOf(dpJoinedDate.getValue()),
                    txtMemberHeight.getText(),txtMemberWeight.getText(),
                    String.valueOf(cmbxSchedules.getSelectionModel().getSelectedItem()),
                    txtMemberId.getText()
            );
        }else{
            MemberCrudController.insertMember(txtMemberId.getText(),txtMemberName.getText(),
                    txtMemberAddress.getText(),txtMemberEmail.getText(),txtMemberTele.getText(),
                    txtMemberAge.getText(),String.valueOf(dpJoinedDate.getValue()),
                    txtMemberHeight.getText(),txtMemberWeight.getText(),
                    String.valueOf(cmbxSchedules.getSelectionModel().getSelectedItem()));
            txtMemberId.setText(genarateId());
        }
    }

    public void setScheduleIdsToCmbx(){
        ObservableList<String> observableList = FXCollections.observableArrayList(
            ScheduleCrudController.getSchedulesIds()
        );
        cmbxSchedules.setItems(observableList);
    }

    public void setValuesForTextFields(Object... params){
        txtMemberId.setText(String.valueOf(params[0]));
        txtMemberAge.setText(String.valueOf(params[1]));
        txtMemberAddress.setText(String.valueOf(params[2]));
        txtMemberEmail.setText(String.valueOf(params[3]));
        txtMemberHeight.setText(String.valueOf(params[4]));
        dpJoinedDate.setValue(LocalDate.parse(String.valueOf(params[5])));
        txtMemberName.setText(String.valueOf(params[6]));
        cmbxSchedules.getSelectionModel().clearAndSelect(Integer.valueOf((Integer) params[7]));
        txtMemberTele.setText(String.valueOf(params[8]));
        txtMemberWeight.setText(String.valueOf(params[9]));
        addMemberUpDateBtn.setText(String.valueOf(params[10]));
        addMemberAddBtn.setText(String.valueOf(params[11]));
        lblAddMember.setText(String.valueOf(params[12]));
    }

    public void addMemberUpDateBtnOnAction(ActionEvent actionEvent) {
        if(addMemberAddBtn.getText().equals("ADD")){
            addMemberAddBtn.setText("UPDATE");
            addMemberUpDateBtn.setText("ADD");
            lblAddMember.setText("UPDATE MEMBER");
        }else{
            addMemberAddBtn.setText("ADD");
            addMemberUpDateBtn.setText("UPDATE");
            lblAddMember.setText("ADD MEMBER");
        }
    }

    public void validate(KeyEvent keyEvent) {
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(MMB-)[0-9]{3,6}$");
        Pattern nameP = Pattern.compile("[A-Za-z .]{3,50}$");
        Pattern ageP = Pattern.compile("[0-9]{1,3}");
        Pattern addressP = Pattern.compile("[A-Za-z 0-9,/.]{3,100}$");
        Pattern teleP = Pattern.compile("[0-9]{10}$");
        Pattern emailP = Pattern.compile("[A-Za-z0-9]{1,}@(gmail|yahoo).com");
        Pattern weightP = Pattern.compile("^[1-9][0-9.]{1,}$");
        Pattern heightP = Pattern.compile("^[1-9][0-9.]{1,}$");

        map.put(txtMemberId,idP);
        map.put(txtMemberName,nameP);
        map.put(txtMemberAddress,addressP);
        map.put(txtMemberEmail,emailP);
        map.put(txtMemberTele,teleP);
        map.put(txtMemberWeight,weightP);
        map.put(txtMemberHeight,heightP);
        map.put(txtMemberAge,ageP);

        boolean b = Validator.validate(map,addMemberAddBtn);
        if(b==false){
            addMemberAddBtn.setDisable(true);
        }else{
            addMemberAddBtn.setDisable(false);
        }
    }

    public String genarateId(){
        return IdsGenerator.genarateId("MMB-",MemberCrudController.getLastMemberId());
        //return IdsGenerator.genarate(Mods.MEMBER,MemberCrudController.getLastMemberId());
    }
}

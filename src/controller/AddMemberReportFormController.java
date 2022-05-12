package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddMemberReportFormController implements Initializable {
    public JFXButton updateBtn;
    public JFXTextField txtReportId;
    public JFXComboBox cmbxMemberId;
    public JFXDatePicker date;
    public JFXTextField txtWeight;
    public JFXTextField txtHeight;
    public JFXButton addBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataToMemberCmbx();
        txtReportId.setText(genId());
        txtReportId.setEditable(false);
        addBtn.setDisable(true);
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
    }

    public void createReportBtnOnAction(ActionEvent actionEvent) {
        MemberFitnessReportsCrudController.addFitnessReport(txtReportId.getText(),
                String.valueOf(date.getValue()),
                txtWeight.getText(),txtHeight.getText(),
                String.valueOf(cmbxMemberId.getSelectionModel().getSelectedItem()));
        txtReportId.setText(genId());
    }

    public void canselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void setDataToMemberCmbx(){
        ObservableList<String> ob = FXCollections.observableArrayList(
            MemberCrudController.getMembersIds()
        );

        cmbxMemberId.setItems(ob);
    }

    public void validate(KeyEvent keyEvent) {

        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(MFR-)[0-9]{3,6}$");
        Pattern heightP = Pattern.compile("^[1-9][0-9.]{1,}");
        Pattern weightP = Pattern.compile("^[1-9][0-9.]{1,}");

        map.put(txtReportId,idP);
        map.put(txtWeight,weightP);
        map.put(txtHeight,heightP);

        boolean b =  Validator.validate(map,addBtn);
        if(b==false){
            addBtn.setDisable(true);
        }else{
            addBtn.setDisable(false);
        }
    }

    public String genId(){
        return IdsGenerator.genarateId("MFR-",
                MemberFitnessReportsCrudController.getFitnessReportLastId());
        //return IdsGenerator.genarate(Mods.MEMBER_FITNESS_REPORT,
                //MemberFitnessReportsCrudController.getFitnessReportLastId());
    }
}

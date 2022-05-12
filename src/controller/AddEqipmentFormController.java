package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.EmptyChecker;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddEqipmentFormController implements Initializable {
    public JFXTextField txtEqId;
    public JFXTextField txtEqType;
    public JFXTextField txtEqDesc;
    public JFXTextField txtEqQOH;
    public JFXButton updatebtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtEqId.setDisable(true);
    }

    public void setValuesForTextFields(Object... params) {
        txtEqId.setText(String.valueOf(params[0]));
        txtEqDesc.setText(String.valueOf(params[1]));
        txtEqType.setText(String.valueOf(params[2]));
        txtEqQOH.setText(String.valueOf(params[3]));
        updatebtn.setDisable(true);
    }

    public void addEquipmentCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }
    public void updateBtnOnAction(ActionEvent actionEvent) {
//        if(EmptyChecker.isEmpty(txtEqId,txtEqDesc,txtEqQOH,txtEqType)==false){
//            //System.out.println(1212121212);
//        }
        //else{
            EquipmentCrudController.updateEquipment(
                    txtEqId.getText(),txtEqDesc.getText(),txtEqQOH.getText(),txtEqType.getText(),
                    txtEqId.getText()
            );
        //}
    }

    public void validate(KeyEvent keyEvent) {
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(EQP-)[0-9]{3,6}$");
        Pattern descP = Pattern.compile("[A-Za-z0-9, ./]{1,50}");
        Pattern qohP = Pattern.compile("[0-9]{1,}");
        Pattern typeP = Pattern.compile("[A-Za-z0-9 ./]{1,}");

        map.put(txtEqId,idP);
        map.put(txtEqDesc,descP);
        map.put(txtEqQOH,qohP);
        map.put(txtEqType,typeP);

        boolean b =  Validator.validate(map,updatebtn);
        if(b==false){
            updatebtn.setDisable(true);
        }else{
            updatebtn.setDisable(false);
        }
    }
}

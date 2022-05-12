package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddSupplimentFormController implements Initializable {
    public JFXTextField txtId;
    public JFXTextField txtDescription;
    public JFXTextField txtType;
    public JFXTextField txtUPrice;
    public JFXTextField txtQOH;
    public JFXButton updateBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateBtn.setDisable(true);
        txtId.setDisable(true);
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        SupplimentCrudController.updateSuppliment(txtId.getText(),txtDescription.getText(),
                txtType.getText(),txtUPrice.getText(),txtQOH.getText(),txtId.getText());
    }

    public void canselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.minimizeStage(actionEvent);
    }

    public void setValuesForTextFields(Object... params) {
        txtId.setText(String.valueOf(params[0]));
        txtDescription.setText(String.valueOf(params[1]));
        txtType.setText(String.valueOf(params[2]));
        txtUPrice.setText(String.valueOf(params[3]));
        txtQOH.setText(String.valueOf(params[4]));
    }

    public void validate(KeyEvent keyEvent) {
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern nameP = Pattern.compile("[A-Za-z0-9 ,'/+()-]{1,}");
        Pattern sellP = Pattern.compile("^[1-9][0-9.]{2,}");
        Pattern typeP = Pattern.compile("[A-Za-z0-9 ()+,'/-]{1,}");
        Pattern qtyP = Pattern.compile("[0-9]{1,}");

        map.put(txtDescription,nameP);
        map.put(txtUPrice,sellP);
        map.put(txtType,typeP);
        map.put(txtQOH,qtyP);

        boolean b = Validator.validate(map,updateBtn);
        if(b==false){
            updateBtn.setDisable(true);
        }else{
            updateBtn.setDisable(false);
        }
    }
}

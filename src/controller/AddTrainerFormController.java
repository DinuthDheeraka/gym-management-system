package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddTrainerFormController implements Initializable {
    public JFXTextField txtNic;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtTelNo;
    public JFXTextField txtAge;
    public JFXButton addTrainerAddBtn;
    public JFXButton addTrainerUpDateBtn;
    public Label addTrainerLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtNic.setText(genarateId());
        txtNic.setEditable(false);
        addTrainerAddBtn.setDisable(true);
    }

    public void addTrainerCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addTrainerAddBtnOnAction(ActionEvent actionEvent) {
        if(addTrainerAddBtn.getText().equals("UPDATE")){
            TrainerCrudController.updateTrainer(
                    txtNic.getText(),txtName.getText(),txtAddress.getText(),
                    txtTelNo.getText(),txtEmail.getText(),txtAge.getText(),txtNic.getText()
            );
        }else{
            TrainerCrudController.insertTrainer(txtNic.getText(),txtName.getText(),txtAddress.getText(),
                    txtTelNo.getText(),txtEmail.getText(),txtAge.getText());

            txtNic.setText(genarateId());
        }
    }

    public void addTrainerUpDateBtnOnAction(ActionEvent actionEvent) {
        if(addTrainerAddBtn.getText().equals("ADD")){
            addTrainerAddBtn.setText("UPDATE");
            addTrainerUpDateBtn.setText("ADD");
            addTrainerLbl.setText("UPDATE MEMBER");
        }else{
            addTrainerAddBtn.setText("ADD");
            addTrainerUpDateBtn.setText("UPDATE");
            addTrainerLbl.setText("ADD MEMBER");
        }
    }

    public void setValuesForTextFields(Object... params) {
        txtNic.setText(String.valueOf(params[0]));
        txtAge.setText(String.valueOf(params[1]));
        txtTelNo.setText(String.valueOf(params[2]));
        txtEmail.setText(String.valueOf(params[3]));
        txtAddress.setText(String.valueOf(params[4]));
        txtName.setText(String.valueOf(params[5]));

        addTrainerUpDateBtn.setText(String.valueOf(params[6]));
        addTrainerAddBtn.setText(String.valueOf(params[7]));
        addTrainerLbl.setText(String.valueOf(params[8]));
    }

    public void validate(KeyEvent keyEvent) {
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(TRA-)[0-9]{3,6}$");
        Pattern nameP = Pattern.compile("[A-ba-z .]{3,50}$");
        Pattern ageP = Pattern.compile("[0-9]{1,3}");
        Pattern addressP = Pattern.compile("[A-Za-z 0-9,/.]{3,100}$");
        Pattern teleP = Pattern.compile("[0-9]{10}$");
        Pattern emailP = Pattern.compile("[A-Za-z0-9]{1,}@(gmail|yahoo).com");

        map.put(txtNic,idP);
        map.put(txtName,nameP);
        map.put(txtAddress,addressP);
        map.put(txtEmail,emailP);
        map.put(txtTelNo,teleP);
        map.put(txtAge,ageP);

        boolean b =  Validator.validate(map,addTrainerAddBtn);
        if(b==false){
            addTrainerAddBtn.setDisable(true);
        }else{
            addTrainerAddBtn.setDisable(false);
        }
    }

    public String genarateId(){
        return IdsGenerator.genarateId("TRA-",
                TrainerCrudController.getTrainersLastId());
        //return IdsGenerator.genarate(Mods.TRAINER,TrainerCrudController.getTrainersLastId());
    }
}

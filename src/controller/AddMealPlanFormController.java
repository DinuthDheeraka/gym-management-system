package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import view.tm.MealPlanTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddMealPlanFormController implements Initializable {
    public JFXTextArea txtMealPlanDescription;
    public JFXTextField txtMealPlanId;
    public JFXButton addBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtMealPlanId.setText(genId());
        txtMealPlanId.setEditable(false);
        addBtn.setDisable(true);
    }

    public void addMealPlanCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addMealPlanAddBtnOnAction(ActionEvent actionEvent) {
        if(addBtn.getText().equals("ADD")){
            MealPlansCrudController.insertMealPlan(txtMealPlanId.getText(),
                    txtMealPlanDescription.getText());
            txtMealPlanId.setText(genId());
        }else{
            MealPlansCrudController.updateMealPlan(txtMealPlanDescription.getText(),txtMealPlanId.getText());
        }
    }

    public void setValuesForTextFields(Object... params) {
        txtMealPlanId.setText(String.valueOf(params[0]));
        txtMealPlanDescription.setText(String.valueOf(params[1]));
        addBtn.setText(String.valueOf(params[2]));
    }

    public void validate(KeyEvent keyEvent) {
        LinkedHashMap<Object, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(MLP-)[0-9]{3,6}$");
        Pattern nameP = Pattern.compile("[A-Za-z0-9 ,'/+()-:%]{1,}");
        map.put(txtMealPlanId,idP);
        map.put(txtMealPlanDescription,nameP);
        for(Object ob : map.keySet()){
            if(ob instanceof TextField){
                TextField t = ((TextField) ob);
                if (!t.getText().isEmpty()) {
                    Pattern patternValue = map.get(ob);
                    if (!patternValue.matcher(t.getText()).matches()) {
                        t.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                        addBtn.setDisable(true);
                    }else{
                        t.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
                        addBtn.setDisable(false);
                    }
                }
            }else{
                TextArea textArea = ((TextArea) ob);
                if (!textArea.getText().isEmpty()) {
                    Pattern patternValue = map.get(ob);
                    if (!patternValue.matcher(textArea.getText()).matches()) {
                        textArea.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                        addBtn.setDisable(true);
                    }else{
                        textArea.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
                        addBtn.setDisable(false);
                    }
                }
            }
        }
    }

    public String genId(){
        return IdsGenerator.genarateId("MLP-",MealPlansCrudController.getMealPlanLastId());
        //return IdsGenerator.genarate(Mods.MEAL_PLAN,MealPlansCrudController.getMealPlanLastId());
    }
}

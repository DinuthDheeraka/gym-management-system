package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddExercisesFormController implements Initializable {
    public JFXTextField exerciseId;
    public JFXTextField exerciseDescription;
    public JFXTextField exerciseNote;
    public JFXButton exerciseUpdateBtn;
    public JFXButton exerciseAddBtn;
    public Label addExerciseLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exerciseId.setText(genarateId());
        exerciseId.setEditable(false);
        exerciseAddBtn.setDisable(true);
    }

    public void addExerciseCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addExerciseOnAction(ActionEvent actionEvent) {
        if(exerciseAddBtn.getText().equals("ADD")){
            ExerciseCrudController.insertExercise(exerciseId.getText(),exerciseDescription.getText(),
                    exerciseNote.getText());
            exerciseId.setText(genarateId());
        }else{
            ExerciseCrudController.updateExercise(exerciseDescription.getText(),exerciseNote.getText(),
                    exerciseId.getText());
        }
    }

    public void exerciseUpdateBtnOnAction(ActionEvent actionEvent) {
        if(exerciseAddBtn.getText().equals("ADD")){
            exerciseAddBtn.setText("UPDATE");
            exerciseUpdateBtn.setText("ADD");
            addExerciseLbl.setText("UPDATE");
        }else{
            exerciseAddBtn.setText("ADD");
            addExerciseLbl.setText("ADD");
            exerciseUpdateBtn.setText("UPDATE");
        }
    }

    public void setValuesForTextFields(Object... params) {
        exerciseId.setText(String.valueOf(params[0]));
        exerciseDescription.setText(String.valueOf(params[1]));
        exerciseNote.setText(String.valueOf(params[2]));
        exerciseAddBtn.setText(String.valueOf(params[3]));
        exerciseUpdateBtn.setText(String.valueOf(params[4]));
        addExerciseLbl.setText(String.valueOf(params[5]));
    }

    public void validate(KeyEvent keyEvent) {
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(EXE-)[0-9]{3,6}$");
        Pattern nameP = Pattern.compile("[A-Za-z0-9 ./]{1,}");
        Pattern noteP = Pattern.compile("[A-Za-z0-9 ./]{1,}");

        map.put(exerciseId,idP);
        map.put(exerciseDescription,nameP);
        map.put(exerciseNote,noteP);

        boolean b =  Validator.validate(map,exerciseAddBtn);
        if(b==false){
            exerciseAddBtn.setDisable(true);
        }else{
            exerciseAddBtn.setDisable(false);
        }
    }

    public String genarateId(){
        return IdsGenerator.genarateId("EXE-",ExerciseCrudController.getExerciseLastId());
        //return IdsGenerator.genarate(Mods.EXERCISE,ExerciseCrudController.getExerciseLastId());
    }
}

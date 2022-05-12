package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.NavigateUI;
import util.Validator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignupFormController implements Initializable {
    public JFXButton signupFormCloseBtn;
    public JFXButton signupFormMinBtn;
    public Circle circle2;
    public JFXButton signupFormLoginBtn;
    public JFXButton signupFormBackBtn;
    public AnchorPane signupFormContext;
    public TextField txtEmail;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public ImageView showPasswordIcon;
    public ImageView hidePasswordIcon;
    public TextField txtTempPassword;
    public Label passwarningLbl11;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setRotate(circle2,false,360,1000);
        signupFormLoginBtn.setDisable(true);
    }

    public void setRotate(Circle c, boolean reverse, int angle, int duration){
//        RotateTransition r1 = new RotateTransition(Duration.seconds(duration),c);
//        r1.setAutoReverse(reverse);
//
//        r1.setByAngle(angle);
//        r1.setDelay(Duration.seconds(0));
//        r1.setRate(1000);
//        r1.setCycleCount(400);
//        r1.play();
    }

    public void signupFormCloseBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void signupFormMinBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.minimizeStage(actionEvent);
    }

    public void signupFormBackBtnOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.addParentToCurrentStage("loginForm",signupFormContext);
    }

    public void validate(KeyEvent keyEvent) {
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern userNameP = Pattern.compile("[A-Za-z .0-9]{4,15}$");
        Pattern emailP = Pattern.compile("[A-Za-z0-9]{1,}@(gmail|yahoo).com");

        map.put(txtUserName,userNameP);
        map.put(txtEmail,emailP);

        boolean isExists = isPasswordExists(txtPassword.getText());

        boolean b =  Validator.validate(map,signupFormLoginBtn);

        if(isExists==false && b && txtPassword.getText().length()>4 && txtPassword.getText().length()<11){
            signupFormLoginBtn.setDisable(false);
        }else{
            signupFormLoginBtn.setDisable(true);
        }
    }

    public void signUpBtnOnAction(ActionEvent actionEvent) throws IOException {
        LoginFormCrudController.addUser(txtUserName.getText(),txtPassword.getText(),
                txtEmail.getText(),1);

        NavigateUI.navigator.closeCurrentStage(signupFormContext);
        NavigateUI.navigator.setNewStage("DashboardForm");
    }

    public void showPasswordOnClick(MouseEvent mouseEvent) {
        showPasswordIcon.setVisible(false);
        hidePasswordIcon.setVisible(true);
        txtPassword.setVisible(false);
        String tempPassword = txtPassword.getText();
        txtTempPassword.setVisible(true);
        txtTempPassword.setText(tempPassword);
    }

    public void hidePasswordOnClick(MouseEvent mouseEvent) {
        showPasswordIcon.setVisible(true);
        hidePasswordIcon.setVisible(false);
        txtTempPassword.setVisible(false);
        txtPassword.setVisible(true);
        txtPassword.setText(txtTempPassword.getText());
    }

    public boolean isPasswordExists(String password){
        boolean isExists = false;
        ArrayList<String> arrayList = LoginFormCrudController.selectPasswords();
        for(String s : arrayList){
            if(s.equals(password)){
                isExists = true;
            }
        }
        return isExists;
    }
}

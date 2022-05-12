package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.NavigateUI;
import util.ShowNotification;

import javax.management.Notification;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {


    public JFXTextField txtShowPassword;
    public ImageView showPasswordIcon;
    public ImageView hidePasswordIcon;
    public JFXPasswordField loginPassword;
    public AnchorPane loginFormContext;
    public Circle circle1;
    public TextField loginUserName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRotate(circle1,false,360,1000);
    }

    public void te(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("SignupForm",loginFormContext);
    }

    public void setRotate(Circle c, boolean reverse, int angle, int duration){
        RotateTransition r1 = new RotateTransition(Duration.seconds(duration),c);
        r1.setAutoReverse(reverse);

        r1.setByAngle(angle);
        r1.setDelay(Duration.seconds(0));
        r1.setRate(800);
        r1.setCycleCount(400);
        r1.play();
    }

    public void showPasswordIconOnClick(MouseEvent mouseEvent) {
        showPasswordIcon.setVisible(false);
        hidePasswordIcon.setVisible(true);
        loginPassword.setVisible(false);
        String tempPassword = loginPassword.getText();
        txtShowPassword.setVisible(true);
        txtShowPassword.setText(tempPassword);
    }

    public void hidePasswordIconOnClick(MouseEvent mouseEvent) {
        showPasswordIcon.setVisible(true);
        hidePasswordIcon.setVisible(false);
        txtShowPassword.setVisible(false);
        loginPassword.setVisible(true);
        loginPassword.setText(txtShowPassword.getText());
    }

    public void closeBtnOnAction(MouseEvent mouseEvent) {
        NavigateUI.navigator.closeStage(mouseEvent);
    }

    public void minBtnOnAction(MouseEvent mouseEvent) {
        NavigateUI.navigator.minimizeStage(mouseEvent);
    }

    public void loginFormloginBtnOnAction(ActionEvent actionEvent) throws IOException {
        if(LoginFormCrudController.findUser(loginUserName.getText(),loginPassword.getText())){

           // LoginFormCrudController.updateUser(1,loginUserName.getText(),txtShowPassword.getText());

            NavigateUI.navigator.closeCurrentStage(loginFormContext);
            NavigateUI.navigator.setNewStage("DashboardForm");
            ShowNotification.show(new Image("/asserts/tick-mark (1)_ccexpress.png"),
                    "Login Successfull");
        }else{
            ShowNotification.show(new Image("/asserts/x-button_ccexpress.png"),
                    "Invalid Username or Password!");
        }
    }

    public void validate(KeyEvent keyEvent) {
    }
}

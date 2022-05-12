package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MemberTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LoginFormCrudController {

    public static void addUser(Object... params){
            try {
                if (CrudUtil.execute("INSERT INTO System_User VALUES(?,?,?,?)", params)) {
                    ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                            "Signup Successfully.");
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }


    public static boolean findUser(String userName,String password) {

        boolean isValidUser = false;
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM System_User WHERE User_Name = ? && User_Password = ?",userName,password);
            if (result.next()) {
                isValidUser = true;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return isValidUser;
    }

    public static void updateUser(Object... params){
        try {
            if (CrudUtil.execute("UPDATE System_User SET Status = ? WHERE User_Name = ? && User_Password = ?", params)) {
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Signup Successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public static void updateUserFromDB(Object... params){
        try {
            if (CrudUtil.execute("UPDATE System_User SET Status = ? WHERE Status = 1", params)) {
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Signup Successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public static String findUser1() {
        String userName = "Error:(";
        boolean isValidUser = false;
        try {

            ResultSet result = CrudUtil.execute("SELECT User_Name FROM System_User WHERE Status = 1");
            if (result.next()) {
                userName = result.getString(1);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return userName;
    }




    public static ArrayList<String> selectPasswords() {
        ArrayList<String> passwords = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT User_Password FROM System_User;");
            if (result.next()) {
                passwords.add(result.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return passwords;
    }
}

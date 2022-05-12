package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MemberTM;
import view.tm.SupplimentTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SupplimentCrudController {

    public static void insertSuppliment(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO Suppliment VALUES(?,?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Suppliment "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<SupplimentTM> getAllSuppliments() {
        ArrayList<SupplimentTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Suppliment;");
            while(result.next()){
                arrayList.add(new SupplimentTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getDouble(4),
                        result.getInt(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<String> getAllSupplimentsIds() {
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT DISTINCT Suppliment_Id FROM Suppliment;");
            while(result.next()){
                System.out.println(result.getString(1));
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void updateSupplimentFromOrder(Object... params){
        try {
            if(CrudUtil.execute("UPDATE Suppliment SET Suppliment_Unit_Price = ?,qoh=qoh+? WHERE Suppliment_Id = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Suppliment "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static int getSupplimentCount(){
        int count = 0;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Suppliment_Id) FROM Suppliment");
            if(result.next()){
                count = result.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void updateSuppliment(Object... params){
        try {
            if(CrudUtil.execute("UPDATE Suppliment SET Suppliment_Id = ?,Suppliment_Description = ?,Suppliment_Type = ?,Suppliment_Unit_Price = ?,QOH = ?  WHERE Suppliment_Id = ? ",params)){

                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Suppliment "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static void deleteSuppliment(String id){
        try {
            if(CrudUtil.execute("DELETE FROM Suppliment WHERE Suppliment_Id = ?",id)){

                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Suppliment "+(id)+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getSupplimentLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Suppliment_Id FROM Suppliment ORDER BY Suppliment_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<SupplimentTM> searchSupplimentById(String id) {
        ArrayList<SupplimentTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Suppliment WHERE Suppliment_Id LIKE ?;",id);
            while(result.next()){
                arrayList.add(new SupplimentTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getDouble(4),
                        result.getInt(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }



    public static ArrayList<SupplimentTM> searchSupplimentByType(String type) {
        ArrayList<SupplimentTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Suppliment WHERE Suppliment_Type LIKE ?;",type);
            while(result.next()){
                arrayList.add(new SupplimentTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getDouble(4),
                        result.getInt(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<SupplimentTM> searchSupplimentByDesc(String type) {
        ArrayList<SupplimentTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Suppliment WHERE Suppliment_Description LIKE ?;",type);
            while(result.next()){
                arrayList.add(new SupplimentTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getDouble(4),
                        result.getInt(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<SupplimentTM> supplimentAutoFill(String id) {
        ArrayList<SupplimentTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Suppliment WHERE Suppliment_Id LIKE ? LIMIT 1;",id);
            while(result.next()){
                arrayList.add(new SupplimentTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getDouble(4),
                        result.getInt(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}

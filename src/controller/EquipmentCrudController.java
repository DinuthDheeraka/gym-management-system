package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.EquipmentTM;
import view.tm.MemberTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EquipmentCrudController {

    public static ArrayList<EquipmentTM> getAllEquipments() {
        ArrayList<EquipmentTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM equipment");
            while(result.next()){

                arrayList.add(new EquipmentTM(
                       result.getString(1),
                       result.getString(4),
                        result.getString(2),
                        result.getInt(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void insertEquipment(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO equipment VALUES(?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Equipment Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getAllEquipmentsIds() {
        ArrayList<String> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT Equipment_Id FROM equipment");
            while(result.next()){

                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void updateEquipmentByMyOrder(Object... params){

        try {
            if(CrudUtil.execute("UPDATE equipment SET qoh = qoh+?;",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Equipment Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static int getEquipmentCount(){
        int count = 0;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Equipment_Id) FROM Equipment");
            if(result.next()){
                count = result.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static ArrayList<EquipmentTM> searchEquipmentById(String id){

        ArrayList<EquipmentTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM equipment WHERE Equipment_Id LIKE ?",id);
            while(result.next()){

                arrayList.add(new EquipmentTM(
                        result.getString(1),
                        result.getString(4),
                        result.getString(2),
                        result.getInt(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void updateEquipment(Object... params){
        try {
            if(CrudUtil.execute("UPDATE equipment SET Equipment_Id=?,Equipment_Description=?,QOH=?,Type=? WHERE Equipment_Id=?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Equipment Updated Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static void deleteEquipment(String id){
        try {
            if(CrudUtil.execute("DELETE FROM equipment WHERE Equipment_Id=?",id)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Equipment Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getEqpLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Equipment_Id FROM Equipment ORDER BY Equipment_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }



    public static ArrayList<EquipmentTM> searchEquipmentByName(String name){

        ArrayList<EquipmentTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM equipment WHERE Equipment_Description LIKE ?",name);
            while(result.next()){

                arrayList.add(new EquipmentTM(
                        result.getString(1),
                        result.getString(4),
                        result.getString(2),
                        result.getInt(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }



    public static ArrayList<EquipmentTM> searchEquipmentByType(String type){

        ArrayList<EquipmentTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM equipment WHERE Type LIKE ?",type);
            while(result.next()){

                arrayList.add(new EquipmentTM(
                        result.getString(1),
                        result.getString(4),
                        result.getString(2),
                        result.getInt(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }



    public static ArrayList<EquipmentTM> eqpAutoFill(String id){

        ArrayList<EquipmentTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM equipment WHERE Equipment_Id LIKE ? LIMIT 1",id);
            while(result.next()){

                arrayList.add(new EquipmentTM(
                        result.getString(1),
                        result.getString(4),
                        result.getString(2),
                        result.getInt(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

}

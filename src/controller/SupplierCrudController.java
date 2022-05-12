package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.ScheduleTM;
import view.tm.SupplierTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierCrudController {
    public static ArrayList<SupplierTM> getAllSuppliers(){
        ArrayList<SupplierTM> arrayList = new ArrayList();

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Supplier");
            while(result.next()){
                System.out.println(result.getString(3));
                arrayList.add(new SupplierTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getString(5)
                ));

                System.out.println(arrayList.get(0).getEmail());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    public static void insertMember(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO Supplier VALUES(?,?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Supplier "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static void updateSupplier(Object... params){
        try {
            if(CrudUtil.execute("UPDATE Supplier SET Supplier_Id = ?,Company_Name = ?" +
                    ",Supplier_Email = ?,Supplier_Tele = ?,Supplier_Address = ? WHERE Supplier_Id = ?",params)){

                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Supplier "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static void deletetSupplier(Object... params){
        try {
            if(CrudUtil.execute("DELETE FROM Supplier WHERE Supplier_Id = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Supplier "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static int getSupplierCount(){
        int count = 0;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Supplier_Id) FROM Supplier");
            if(result.next()){
                count = result.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static ArrayList<String> getSupplierIds(){
        ArrayList<String> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT Supplier_Id FROM Supplier");
            while (result.next()){
                arrayList.add(result.getString(1));
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<String> getSupplierLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Supplier_Id FROM Supplier ORDER BY Supplier_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<SupplierTM> searchSupplierById(String id){
        ArrayList<SupplierTM> arrayList = new ArrayList();

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE Supplier_Id LIKE ?",id);
            while(result.next()){
                System.out.println(result.getString(3));
                arrayList.add(new SupplierTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getString(5)
                ));

                System.out.println(arrayList.get(0).getEmail());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }


    public static ArrayList<SupplierTM> searchSupplierCompanyName(String id){
        ArrayList<SupplierTM> arrayList = new ArrayList();

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE Company_Name LIKE ?",id);
            while(result.next()){
                System.out.println(result.getString(3));
                arrayList.add(new SupplierTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getString(5)
                ));

                System.out.println(arrayList.get(0).getEmail());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }
}

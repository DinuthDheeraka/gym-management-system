package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MyOrderProductTM;
import view.tm.MyOrdersTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyOrdersCrudController {

    public static ArrayList<MyOrdersTM> getAllMyOrders(){
        ArrayList<MyOrdersTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Owner_Order");
            while(result.next()){

                arrayList.add(new MyOrdersTM(
                   result.getString(1),result.getString(2),
                        result.getString(3),result.getDouble(4),
                        result.getString(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<MyOrderProductTM> getAllItemsFromMyOrdersByOrderId(String orderId){
        ArrayList<MyOrderProductTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Owner_Order_Detail WHERE Owner_Order_Id = ?",orderId);
            while(result.next()){

                arrayList.add(new MyOrderProductTM(
                        result.getString(2),result.getString(3),
                        result.getString(6),result.getDouble(4),
                        result.getInt(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void addMyOrderDetail(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO owner_order_detail VALUES(?,?,?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Item Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    public static ArrayList<String> getMyOrdersIds(){
        ArrayList<String> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT Owner_Order_Id FROM Owner_Order");
            while(result.next()){

                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void addMyOrder(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO owner_order VALUES(?,?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Order Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getMyOrderLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Owner_Order_Id FROM Owner_Order ORDER BY Owner_Order_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static double getExpensesByMonth(String monthLikeValue){
        double income = -1;
        try {
            ResultSet result = CrudUtil.execute("SELECT SUM(Total_Cost) FROM owner_order WHERE Date LIKE  ? ",monthLikeValue);
            while (result.next()){
                income = result.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return income;
    }


    public static ArrayList<MyOrdersTM> searchMyOrdersById(String id){
        ArrayList<MyOrdersTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Owner_Order WHERE Owner_Order_Id LIKE ?",id);
            while(result.next()){

                arrayList.add(new MyOrdersTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getDouble(4),
                        result.getString(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<MyOrdersTM> searchMyOrdersByDate(String date){
        ArrayList<MyOrdersTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Owner_Order WHERE Date LIKE ?",date);
            while(result.next()){

                arrayList.add(new MyOrdersTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getDouble(4),
                        result.getString(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MyOrderProductTM> searchOrderDetailsById(String orderId){
        ArrayList<MyOrderProductTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Owner_Order_Detail WHERE Owner_Order_Id LIKE ?",orderId);
            while(result.next()){

                arrayList.add(new MyOrderProductTM(
                        result.getString(2),result.getString(3),
                        result.getString(6),result.getDouble(4),
                        result.getInt(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static void updateMyOrder(Object... params){
        try {
            if(CrudUtil.execute("UPDATE owner_order SET Date=?,Time=?,Total_Cost=?,Supplier_Id=? WHERE Owner_Order_Id=?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Order Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}

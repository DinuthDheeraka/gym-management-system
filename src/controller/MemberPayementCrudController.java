package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MemberPayementStatusTM;
import view.tm.MemberPayementTM;
import view.tm.MemberTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberPayementCrudController {

    public static ArrayList<MemberPayementTM> getAllMemberPayements(){
        ArrayList<MemberPayementTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Payement ORDER BY Settle_Date DESC;");
            while(result.next()){
                arrayList.add(new MemberPayementTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getDouble(5),result.getString(6)
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<MemberPayementTM> getAllMemberPayementsByMonth(String month){
        ArrayList<MemberPayementTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Payement WHERE Month = ?",month);
            while(result.next()){
                arrayList.add(new MemberPayementTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getDouble(5),result.getString(6)
                ));
                System.out.println(result.getString(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<MemberPayementStatusTM> getAllUnPaidMembersByMonth(String month){
        ArrayList<MemberPayementStatusTM> arrayList = new ArrayList();
        ArrayList<MemberPayementStatusTM> arrayList1 = getAllPaidMembersByMonth(month);
        try {
            ResultSet result = CrudUtil.execute("SELECT DISTINCT Member_Id FROM Payement WHERE Month != ?",month);
            while(result.next()){
                boolean isIn = false;
                for(MemberPayementStatusTM tm : arrayList1){
                    if(result.getString(1).equals(tm.getMemberId())){
                        isIn = true;
                    }
                }
                if(isIn==false){
                    ArrayList<MemberTM> arrayList2 = MemberCrudController.selectMemberById(result.getString(1));
                    if(arrayList2.size()>0){
                        arrayList.add(new MemberPayementStatusTM(
                                arrayList2.get(0).getMemberId(),arrayList2.get(0).getMemberName(),
                                arrayList2.get(0).getMemberEmail(),arrayList2.get(0).getMemberAddress(),
                                arrayList2.get(0).getMemberTele(),arrayList2.get(0).getMemberJoinedDate()));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<MemberPayementStatusTM> getAllPaidMembersByMonth(String month){
        ArrayList<MemberPayementStatusTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT DISTINCT Member_Id FROM Payement WHERE Month = ?",month);
            while(result.next()){
                ArrayList<MemberTM> arrayList1 = MemberCrudController.selectMemberById(result.getString(1));
                if(arrayList1.size()>0){
                    arrayList.add(new MemberPayementStatusTM(
                            arrayList1.get(0).getMemberId(),arrayList1.get(0).getMemberName(),
                            arrayList1.get(0).getMemberEmail(),arrayList1.get(0).getMemberAddress(),
                            arrayList1.get(0).getMemberTele(),arrayList1.get(0).getMemberJoinedDate()));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void insertMemberPayement(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO Payement VALUES(?,?,?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Payement Successfull.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static int getAllPaidMembersCountByDate(String date){
        int count = -1;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Member_Id) FROM payement WHERE Settle_Date = ? ;",date);
            while (result.next()){
                count = result.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static double getIncomeFromMembersByDate(String date){
        double income = -1;
        try {
            ResultSet result = CrudUtil.execute("SELECT SUM(Amount) FROM payement WHERE Settle_Date = ? ;",date);
            while (result.next()){
                income = result.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return income;
    }

    public static double getIncomeFromMembersByMonth(String monthLikeValue){
        double income = -1;
        try {
            ResultSet result = CrudUtil.execute("SELECT SUM(Amount) FROM payement WHERE Settle_Date LIKE  ? ",monthLikeValue);
            while (result.next()){
                income = result.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return income;
    }

    public static ArrayList<MemberPayementTM> getAllMemberPayementsBySettledDate(String date){
        ArrayList<MemberPayementTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Payement WHERE Settle_Date = ?",date);
            while(result.next()){
                arrayList.add(new MemberPayementTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getDouble(5),result.getString(6)
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<MemberPayementTM> getAllMemberPayementsByMemberId(String id){
        ArrayList<MemberPayementTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Payement WHERE Member_Id = ?",id);
            while(result.next()){
                arrayList.add(new MemberPayementTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getDouble(5),result.getString(6)
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void deleteMemberPayement(String id){
        try {
            if(CrudUtil.execute("DELETE FROM Payement WHERE Member_Id = ?",id)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Cleared Member History.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> geMemberPayementLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Payement_Id FROM Payement ORDER BY Payement_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<MemberPayementStatusTM> geMemberNeverSettledPayement(){
        ArrayList<MemberPayementStatusTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE Member_Id NOT IN (SELECT Member_Id FROM Payement);");
            while(result.next()){
                arrayList.add(new MemberPayementStatusTM(
                        result.getString(1),result.getString(2),
                        result.getString(4),result.getString(3),
                        result.getString(5),result.getString(7)
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}

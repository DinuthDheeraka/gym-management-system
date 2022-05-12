package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.util.Duration;
import model.Member;
import model.MemberWeightFitnessReport;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MemberFitnessReportTM;
import view.tm.MemberTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MemberFitnessReportsCrudController {

    public static LinkedHashMap<String,Double> getMemberWeightReport(String memberId){
        LinkedHashMap<String,Double> hashMap = new LinkedHashMap();

        try {

            ResultSet result = CrudUtil.execute("SELECT Date,Weight FROM fitnessreport WHERE Member_Id = ? ORDER BY  Date ASC;",memberId);
            while(result.next()){
                hashMap.put(result.getString(1),result.getDouble(2));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return hashMap;
    }


    public static ArrayList<MemberWeightFitnessReport> getMemberWeightReportArrayL(String memberId){
        ArrayList<MemberWeightFitnessReport> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT Date,Weight FROM fitnessreport WHERE Member_Id = ? ORDER BY  Date ASC;",memberId);
            while(result.next()){
                arrayList.add(new MemberWeightFitnessReport(
                        result.getString(1),result.getDouble(2)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MemberFitnessReportTM> getAllFitnessReports(){
        ArrayList<MemberFitnessReportTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM fitnessreport");
            while(result.next()){
                arrayList.add(new MemberFitnessReportTM(
                        result.getString(1),result.getString(5),
                        result.getString(2),result.getDouble(3),
                        result.getDouble(4)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void addFitnessReport(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO fitnessreport VALUES(?,?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Created Fitness Report Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<MemberFitnessReportTM> getReportsByMemberId(String memberId){
        ArrayList<MemberFitnessReportTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM fitnessreport WHERE Member_Id = ? ORDER BY  Date DESC;",memberId);
            while(result.next()){
                arrayList.add(new MemberFitnessReportTM(
                        result.getString(1),result.getString(5),
                        result.getString(2),result.getDouble(3),
                        result.getDouble(4)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MemberFitnessReportTM> getReportsByReportId(String reportId){
        ArrayList<MemberFitnessReportTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM fitnessreport WHERE Report_Id = ?;",reportId);
            while(result.next()){
                arrayList.add(new MemberFitnessReportTM(
                        result.getString(1),result.getString(5),
                        result.getString(2),result.getDouble(3),
                        result.getDouble(4)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MemberFitnessReportTM> getLastReportByMemberId(String memberId){
        ArrayList<MemberFitnessReportTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM fitnessreport WHERE Member_Id = ? ORDER BY  Date DESC LIMIT 1;",memberId);
                while(result.next()){
                    arrayList.add(new MemberFitnessReportTM(
                            result.getString(1),result.getString(5),
                            result.getString(2),result.getDouble(3),
                            result.getDouble(4)
                    ));
                }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void deleteReport(Object... params){
        try {
            if(CrudUtil.execute("DELETE FROM fitnessreport WHERE Report_Id = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Report Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static void deleteReportByMemberId(Object... params){
        try {
            if(CrudUtil.execute("DELETE FROM fitnessreport WHERE Member_Id = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Report Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getFitnessReportLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Report_Id FROM FitnessReport ORDER BY Report_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }



    public static ArrayList<MemberFitnessReportTM> searchReportByReportId(String id){
        ArrayList<MemberFitnessReportTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM fitnessreport WHERE Report_Id LIKE ?",id);
            while(result.next()){
                arrayList.add(new MemberFitnessReportTM(
                        result.getString(1),result.getString(5),
                        result.getString(2),result.getDouble(3),
                        result.getDouble(4)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }



    public static ArrayList<MemberFitnessReportTM> searchReportByMemberId(String id){
        ArrayList<MemberFitnessReportTM> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM fitnessreport WHERE Member_Id LIKE ?",id);
            while(result.next()){
                arrayList.add(new MemberFitnessReportTM(
                        result.getString(1),result.getString(5),
                        result.getString(2),result.getDouble(3),
                        result.getDouble(4)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}

package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MemberTM;
import view.tm.ScheduleTM;
import view.tm.Schedule_Exercise_TM;
import view.tm.ViewScheduleJasperTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ScheduleCrudController {

    public static ArrayList<ScheduleTM> getAllSchedules(){
        ArrayList<ScheduleTM> arrayList = new ArrayList();

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Schedule");
            while(result.next()){
                arrayList.add(new ScheduleTM(
                        result.getString(1),result.getString(6),
                        result.getString(2),result.getString(3),
                        result.getInt(4),result.getString(5)
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    public static void insertSchedule(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO Schedule VALUES(?,?,?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Schedule "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getSchedulesIds(){
        ArrayList<String> arrayList = new ArrayList();

        try {
            ResultSet result = CrudUtil.execute("SELECT Schedule_Id FROM Schedule");
            while(result.next()){
                arrayList.add(result.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    public static void addExercisesToSchedule(String... params){
        try {
            if(CrudUtil.execute("INSERT INTO exercise_schedule VALUES(?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Exercise Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<Schedule_Exercise_TM> getScheduleExercises(String scheduleId){
        ArrayList<Schedule_Exercise_TM> arrayList = new ArrayList();
        LinkedHashMap<String,String> linkedHashMap = ExerciseCrudController.getIdsWithExerciseNames();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM exercise_schedule WHERE Schedule_Id = ?",scheduleId);
            while(result.next()){
                arrayList.add(new Schedule_Exercise_TM(
                        result.getString(1),linkedHashMap.get(result.getString(1)),
                        result.getString(3),result.getString(4)
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static LinkedHashMap<String,String> getScheduleIdWithTrainerId(){
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap();

        try {
            ResultSet result = CrudUtil.execute("SELECT Schedule_Id,Trainer_NIC FROM Schedule");
            while(result.next()){
                linkedHashMap.put(result.getString(1),result.getString(2));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return linkedHashMap;
    }

    public static void deleteSchedule(String... params){
        try {
            if(CrudUtil.execute("DELETE FROM Schedule WHERE Schedule_Id = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Schedule "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            ShowNotification.show(new Image("/asserts/x-button_ccexpress.png"),
                    "Schedule "+(params[0])+" in use,Can't Delete!");
        }
    }

    public static void deleteExerciseFromSchedule(String... params){
        try {
            if(CrudUtil.execute("DELETE FROM exercise_schedule WHERE Exercise_Id = ? AND Affecting_Area = ? AND Instructions = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Exercise From Schedule "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static void updateSchedule(Object... params){
        try {
            if(CrudUtil.execute("UPDATE Schedule SET Schedule_Id = ?,Start_Date = ?,End_Date = ?,Days_Per_Week = ?,Meal_Plan_Id = ?,Trainer_NIC = ? WHERE Schedule_Id = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Schedule "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getScheduleLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT Schedule_Id FROM Schedule ORDER BY Schedule_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<ViewScheduleJasperTM> getScheduleForJasper(String id){
        LinkedHashMap<String,String> a = ExerciseCrudController.getIdsWithExerciseNames();

        ArrayList<ViewScheduleJasperTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT Exercise_Id,Affecting_Area,Instructions FROM exercise_schedule WHERE Schedule_Id = ?",id);
            while(result.next()){
                arrayList.add(new ViewScheduleJasperTM(
                        a.get(result.getString(1)),
                        result.getString(2),
                        result.getString(3)
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }


    public static ArrayList<ScheduleTM> searchScheduleById(String id){
        ArrayList<ScheduleTM> arrayList = new ArrayList();

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Schedule WHERE Schedule_Id LIKE ?",id);
            while(result.next()){
                arrayList.add(new ScheduleTM(
                        result.getString(1),result.getString(6),
                        result.getString(2),result.getString(3),
                        result.getInt(4),result.getString(5)
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<ScheduleTM> searchScheduleByTrainerId(String id){
        ArrayList<ScheduleTM> arrayList = new ArrayList();

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Schedule WHERE Trainer_NIC LIKE ?",id);
            while(result.next()){
                arrayList.add(new ScheduleTM(
                        result.getString(1),result.getString(6),
                        result.getString(2),result.getString(3),
                        result.getInt(4),result.getString(5)
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }


    public static ArrayList<Schedule_Exercise_TM> getScheduleExercisesLike(String scheduleId){
        ArrayList<Schedule_Exercise_TM> arrayList = new ArrayList();
        LinkedHashMap<String,String> linkedHashMap = ExerciseCrudController.getIdsWithExerciseNames();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM exercise_schedule WHERE Schedule_Id LIKE ?",scheduleId);
            while(result.next()){
                arrayList.add(new Schedule_Exercise_TM(
                        result.getString(1),linkedHashMap.get(result.getString(1)),
                        result.getString(3),result.getString(4)
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static void updateScheduleExercise(Object... params){
        try {
            if(CrudUtil.execute("UPDATE exercise_schedule SET Exercise_Id=?,Affecting_Area=?,Instructions=? WHERE Schedule_Id=? && Exercise_Id=? && Affecting_Area=? && Instructions=?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Exercise "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getAssignedTrainersIds(){
        ArrayList<String> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT Trainer_NIC FROM schedule;");
            while(result.next()){
                arrayList.add(result.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}

package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.ExerciseTM;
import view.tm.MemberTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExerciseCrudController {

    public static ArrayList<ExerciseTM> getAllExercises() {
        ArrayList<ExerciseTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Exercise");
            while(result.next()){

                arrayList.add(new ExerciseTM(
                        result.getString(1),result.getString(2),
                        result.getString(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void insertExercise(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO Exercise VALUES(?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Exercise Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getExercisesIdsWithDescription(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Exercise");
            while(result.next()){

                arrayList.add(result.getString(1)+"-"+result.getString(2));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<String> getExercisesIds(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Exercise_Id FROM Exercise");
            while(result.next()){

                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static LinkedHashMap<String,String> getIdsWithExerciseNames(){
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap();

        try {

            ResultSet result = CrudUtil.execute("SELECT Exercise_Id,Description FROM Exercise");
            while(result.next()){

                linkedHashMap.put(result.getString(1),result.getString(2));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return linkedHashMap;
    }

    public static void deleteExercise(String id){
        try {
            if(CrudUtil.execute("DELETE FROM Exercise WHERE Exercise_Id = ?",id)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Exercise Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static void updateExercise(Object... params){
        try {
            if(CrudUtil.execute("UPDATE Exercise SET Description = ?,Note = ? WHERE Exercise_Id = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Exercise Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getExerciseLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Exercise_Id FROM Exercise ORDER BY Exercise_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<ExerciseTM> searchExercisesById(String id) {
        ArrayList<ExerciseTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Exercise WHERE Exercise_Id LIKE ?",id);
            while(result.next()){

                arrayList.add(new ExerciseTM(
                        result.getString(1),result.getString(2),
                        result.getString(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<ExerciseTM> searchExercisesByName(String name) {
        ArrayList<ExerciseTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Exercise WHERE Description LIKE ?",name);
            while(result.next()){

                arrayList.add(new ExerciseTM(
                        result.getString(1),result.getString(2),
                        result.getString(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }



    public static ArrayList<ExerciseTM> searchExercisesByNote(String note) {
        ArrayList<ExerciseTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Exercise WHERE Note LIKE ?",note);
            while(result.next()){

                arrayList.add(new ExerciseTM(
                        result.getString(1),result.getString(2),
                        result.getString(3)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}

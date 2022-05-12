package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MealPlanTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealPlansCrudController {

    public static ArrayList<MealPlanTM> getAllMealPlans(){
        ArrayList<MealPlanTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM meal_plan");

            while(result.next()){
                arrayList.add(new MealPlanTM(
                        result.getString(1),result.getString(2)
                ));
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void insertMealPlan(String... params){
        try {
            if(CrudUtil.execute("INSERT INTO meal_plan VALUES(?,?)",params[0],params[1])){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Meal Plan Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getMealPlansIds(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT DISTINCT Meal_Plan_Id FROM Meal_Plan");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void deleteMealPlan(String id){
        try {
            if(CrudUtil.execute("DELETE FROM Meal_Plan WHERE Meal_Plan_Id = ?",id)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Meal Plan Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static void updateMealPlan(Object... params){
        try {
            if(CrudUtil.execute("UPDATE Meal_Plan SET Description = ? WHERE Meal_Plan_Id = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getMealPlanLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Meal_Plan_Id FROM Meal_Plan ORDER BY Meal_Plan_Id DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<MealPlanTM> searchMealPlansById(String id){
        ArrayList<MealPlanTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM meal_plan WHERE Meal_Plan_Id LIKE ?",id);

            while(result.next()){
                arrayList.add(new MealPlanTM(
                        result.getString(1),result.getString(2)
                ));
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    public static ArrayList<MealPlanTM> searchMealPlansByDescription(String desc){
        ArrayList<MealPlanTM> arrayList = new ArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM meal_plan WHERE Description LIKE ?",desc);

            while(result.next()){
                arrayList.add(new MealPlanTM(
                        result.getString(1),result.getString(2)
                ));
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}

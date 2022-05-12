package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MemberTM;
import view.tm.TrainerTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrainerCrudController {

    public static ArrayList<TrainerTM> getAllTrainers(){
        ArrayList<TrainerTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Trainer");
            while(result.next()){
                arrayList.add(new TrainerTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getString(5),result.getInt(6)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void insertTrainer(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO Trainer VALUES(?,?,?,?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Added Trainer "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getTrainersIds(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT DISTINCT Trainer_NIC FROM Trainer");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static void deleteTrainer(Object... params){
        try {
            if(CrudUtil.execute("DELETE FROM Trainer WHERE Trainer_NIC = ?",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Deleted Trainer "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            ShowNotification.show(new Image("/asserts/x-button_ccexpress.png"),
                    "Trainer "+(params[0])+" belongs to one or more Schedules.");
        }
    }


    public static int getTrainerCount(){
        int count = 0;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Trainer_NIC) FROM Trainer");
            if(result.next()){
                count = result.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void updateTrainer(Object... params){
        try {
            if(CrudUtil.execute("UPDATE Trainer SET Trainer_NIC = ?,Trainer_Name = ?,Trainer_Address = ?,Trainer_Tele = ?,Trainer_Email = ?,Age = ? WHERE Trainer_Nic = ?",params)){

                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Trainer "+(params[0])+" Successfully.");
            }
        }
        catch (ClassNotFoundException|SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<String> getTrainersLastId(){
        ArrayList<String> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT Trainer_NIC FROM Trainer ORDER BY Trainer_NIC DESC LIMIT 1");
            while(result.next()){
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static String getTrainerName(String id){
        String name = null;
        try {
            ResultSet result = CrudUtil.execute("SELECT Trainer_Name FROM Trainer WHERE Trainer_NIC = ?",id);
            if(result.next()){
                name = result.getString(1);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return name;
    }


    public static ArrayList<TrainerTM> searchTrainerById(String id){
        ArrayList<TrainerTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Trainer WHERE Trainer_NIC LIKE ?",id);
            while(result.next()){
                arrayList.add(new TrainerTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getString(5),result.getInt(6)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<TrainerTM> searchTrainerByName(String name){
        ArrayList<TrainerTM> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Trainer WHERE Trainer_Name LIKE ?",name);
            while(result.next()){
                arrayList.add(new TrainerTM(
                        result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),
                        result.getString(5),result.getInt(6)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}

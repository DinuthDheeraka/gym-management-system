package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import model.Member;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MemberTM;
import view.tm.Member_Attendance_TM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AttendanceCrudController {

    public static void markAttendance(Object... params){
        try {
            if(CrudUtil.execute("INSERT INTO Attendance VALUES(?,?,?)",params)){
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Marked Attendance Successfully.");
            }
        }
        catch (ClassNotFoundException| SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public static ArrayList<Member_Attendance_TM> getAttendedMembers(String date){
        ArrayList<Member_Attendance_TM> arrayList = new ArrayList();
        LinkedHashMap<String,String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member INNER JOIN Attendance ON Member.Member_Id = Attendance.Member_Id WHERE Attendance.Date = ?",date);
            while(result.next()){

                arrayList.add(new Member_Attendance_TM(
                        result.getString(1),result.getString(2),
                        result.getString(3),
                        result.getString(4),result.getString(5),
                        result.getInt(6),
                        result.getString(7),result.getString(10),
                        linkedHashMap.get(result.getString(10)),
                        result.getInt(8),result.getInt(9)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<Member_Attendance_TM> getNotAttendedMembers(String date){
        ArrayList<Member_Attendance_TM> arrayList = new ArrayList();
        LinkedHashMap<String,String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();
        try {

            ResultSet result = CrudUtil.execute("SELECT DISTINCT(Member_Id) FROM Attendance WHERE Date != ?;",date);

            ArrayList<Member_Attendance_TM> arrayList1 = AttendanceCrudController.getAttendedMembers(date);
            boolean isIn = false;

            while(result.next()){
                for(Member_Attendance_TM tm : arrayList1){
                    if(tm.getMemberId().equals(result.getString(1))){
                        isIn = true;
                    }
                }

                if(!isIn){
                    Member member = MemberCrudController.getMemberDetails(result.getString(1));

                    arrayList.add(new Member_Attendance_TM(
                            member.getMember_Id(),member.getName(),member.getAddress(),member.getEmail(),member.getTele(),member.getAge(),
                            member.getJoinDate(),member.getScheduleId(),linkedHashMap.get(member.getScheduleId()),
                            member.getHeight(), member.getWeight()
                    ));
                }
                isIn = false;
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<Member_Attendance_TM> getMembersNotAttendsAllTime(){
        ArrayList<Member_Attendance_TM> arrayList = new ArrayList();
        LinkedHashMap<String,String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();
        try {

            ResultSet result = CrudUtil.execute("SELECT Member_Id FROM Member WHERE Member_Id NOT IN (SELECT Member_Id FROM Attendance);");

            while(result.next()){
                Member member = MemberCrudController.getMemberDetails(result.getString(1));

                arrayList.add(new Member_Attendance_TM(
                        member.getMember_Id(),member.getName(),member.getAddress(),member.getEmail(),member.getTele(),member.getAge(),
                        member.getJoinDate(),member.getScheduleId(),linkedHashMap.get(member.getScheduleId()),
                        member.getHeight(), member.getWeight()
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static int getAttendanceCountByMonth(String month){
        int count = 0;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Member_Id) FROM Attendance WHERE Date LIKE ?",month);
            if(result.next()){
                count = result.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int getAttendanceCountByDate(String date){
        int count = 0;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Member_Id) FROM Attendance WHERE Date = ?",date);
            if(result.next()){
                count = result.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Member;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ShowNotification;
import view.tm.MemberTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MemberCrudController {

    public static ArrayList<MemberTM> getAllMembers() {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member");
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static Member getMemberDetails(String memberId) {
        Member member = new Member();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE Member_Id = ?", memberId);
            LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

            if (result.next()) {
                return new Member(
                        result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getString(5), result.getString(10),
                        result.getInt(6), result.getDouble(9), result.getDouble(8),
                        result.getString(7)
                );
            } else {
                new Alert(Alert.AlertType.ERROR, "Could't find member " + memberId).show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return member;
    }

    public static void insertMember(Object... params) {
        try {
            if (CrudUtil.execute("INSERT INTO Member VALUES(?,?,?,?,?,?,?,?,?,?)", params)) {
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Adde Member Successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public static ArrayList<MemberTM> selectMemberById(String value) {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE Member_Id = ?", value);
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MemberTM> selectMemberByScheduleId(String value) {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE Schedule_Id = ?", value);
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MemberTM> selectMemberByName(String value) {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE Member_Name = ?", value);
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<String> getMembersIds() {
        ArrayList<String> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT Member_Id FROM Member");
            while (result.next()) {

                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MemberTM> selectMemberByTrainerId(String value) {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member INNER JOIN Schedule ON Member.Schedule_Id = Schedule.Schedule_Id WHERE Trainer_Nic = ?", value);
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MemberTM> getAllMembersLike(String name) {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE Member_Name LIKE ?", name);
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static int getMemberJoiningRateByMonth(String month) {
        int count = 0;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Member_Id) FROM Member WHERE Joined_Date LIKE ?", month);
            if (result.next()) {
                count = result.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public static void deletetMember(Object... params) {
        try {
            if (CrudUtil.execute("DELETE FROM Member WHERE Member_Id = ?", params)) {
                Notifications notifications = Notifications.create();
                ShowNotification.show(new Image("asserts/pngtree-purple-tick-image_2292529_ccexpress.jpeg"),
                        "Deleted Member " + (params[0]) + " Successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public static int getMemberCount() {
        int count = 0;
        try {
            ResultSet result = CrudUtil.execute("SELECT COUNT(Member_Id) FROM Member");
            if (result.next()) {
                count = result.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void updateMember(Object... params) {
        try {
            if (CrudUtil.execute("UPDATE Member SET Member_Name = ?,Member_Address = ?,Member_Email = ?,Member_Tele = ?,Member_Age = ?,Joined_Date = ?,Height = ?,Weight = ?,Schedule_Id = ? WHERE Member_Id = ? ", params)) {
                ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),
                        "Updated Member Successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public static ArrayList<String> getLastMemberId() {
        ArrayList<String> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT Member_Id FROM Member ORDER BY Member_Id DESC LIMIT 1");
            while (result.next()) {

                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<MemberTM> getAllMembersIdsLike(String id) {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE Member_Id LIKE ?", id);
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<MemberTM> getAllMembersSchLike(String id) {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE Schedule_Id LIKE ?", id);
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<MemberTM> getAllMembersTrainerLike(String id) {
        ArrayList<MemberTM> arrayList = new ArrayList();
        LinkedHashMap<String, String> linkedHashMap = ScheduleCrudController.getScheduleIdWithTrainerId();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Member INNER JOIN Schedule ON Member.Schedule_Id = Schedule.Schedule_Id WHERE Trainer_Nic LIKE  ?", id);
            while (result.next()) {

                arrayList.add(new MemberTM(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getInt(6), result.getString(7),
                        result.getString(10),
                        linkedHashMap.get(result.getString(10)), result.getDouble(8), result.getDouble(9)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static String getMemberNameBySchedule(String id) {

        String name = null;

        try {

            ResultSet result = CrudUtil.execute("SELECT Member_Name FROM Member WHERE Schedule_Id = ?", id);
            while (result.next()) {
                name = result.getString(1);
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return name;
    }


    public static int selectMemberCountJoinToday(String value) {
        int count = 0;
        try {

            ResultSet result = CrudUtil.execute("SELECT COUNT(Member_Id) FROM Member WHERE Joined_Date =  ? ", value);
            while (result.next()) {
                count = result.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return count;
    }

    public static ArrayList<String> getScheduleIds() {

        ArrayList<String> arrayList = new ArrayList<>();

        try {

            ResultSet result = CrudUtil.execute("SELECT Schedule_Id FROM Member;");
            while (result.next()) {
                arrayList.add(result.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }
}


package controller;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.Member_Attendance_TM;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class AttendanceFormController implements Initializable {
    public JFXTimePicker attendedTime;
    public JFXDatePicker attendedDate;
    public JFXTextField memberId;
    public JFXButton searchAttendanceBtn;
    public JFXDatePicker txtSearchByAttendDate;
    public TableView<Member_Attendance_TM> attendanceTable;
    public TableColumn attendanceTableColMemberId;
    public TableColumn attendanceTableColName;
    public TableColumn attendanceTableColAddress;
    public TableColumn attendanceTableColEmail;
    public TableColumn attendanceTableColTeleNo;
    public TableColumn attendanceTableColAge;
    public TableColumn attendanceTableColJoinDate;
    public TableColumn attendanceTableScheduleId;
    public TableColumn attendanceTableColTrainerId;
    public TableColumn attendanceTableColWeight;
    public TableColumn attendanceTableColHeight;
    public JFXToggleButton attendsToggleBtn;
    public TextField txtSerachAttendanceCount;
    public BarChart attendanceLineChart;
    public LineChart lineChartAttendance;
    public Label todayCount;
    public JFXComboBox memberCmbx;
    XYChart.Series s1 = new XYChart.Series();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attendanceTableColMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        attendanceTableColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        attendanceTableColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        attendanceTableColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        attendanceTableColTeleNo.setCellValueFactory(new PropertyValueFactory<>("teleNo"));
        attendanceTableColWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        attendanceTableColHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        attendanceTableColTrainerId.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
        attendanceTableColJoinDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        attendanceTableScheduleId.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
        attendanceTableColAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        setDataToMemberCmbxAttendance();

        lineChartAttendance.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        String year = String.valueOf(LocalDate.now());
        setDataToAttendanceLineChart(year.substring(0,4));
        setDataToToDayCount(String.valueOf(LocalDate.now()));

    }

    public void attendanceFormMarkAttendBtnOnAction(ActionEvent actionEvent) {
        AttendanceCrudController.markAttendance(String.valueOf(memberCmbx.getSelectionModel().getSelectedItem()),
                String.valueOf(attendedDate.getValue()),
                attendedTime.getValue());
        setDataToToDayCount(String.valueOf(LocalDate.now()));
    }

    public void searchAttendanceBtnOnAction(ActionEvent actionEvent) {
        attendanceTable.getItems().clear();
        if(attendsToggleBtn.isSelected()){
            ObservableList<Member_Attendance_TM> observableList = FXCollections.observableArrayList(
                    AttendanceCrudController.getNotAttendedMembers(String.valueOf(txtSearchByAttendDate.getValue()))
            );

            ArrayList<Member_Attendance_TM> arrayList = AttendanceCrudController.getMembersNotAttendsAllTime();

            for(Member_Attendance_TM tm : arrayList){
                observableList.add(tm);
            }
            attendanceTable.setItems(observableList);
        }else{
            ObservableList<Member_Attendance_TM> observableList = FXCollections.observableArrayList(
                    AttendanceCrudController.getAttendedMembers(String.valueOf(txtSearchByAttendDate.getValue()))
            );
            attendanceTable.setItems(observableList);
        }
    }

    public void getAttendanceCountToTblSearchBtnOnAction(ActionEvent actionEvent) {
        lineChartAttendance.getData().clear();
        lineChartAttendance.setTitle("ATTENDANCE DETAILS FOR YEAR OF "+txtSerachAttendanceCount.getText());
        setDataToAttendanceLineChart(txtSerachAttendanceCount.getText());
    }

    public String getvalidMonth(int month){
        return month<10? "0"+month : String.valueOf(month);
    }

    public void setDataToAttendanceLineChart(String year){
        lineChartAttendance.setTitle("ATTENDANCE DETAILS FOR YEAR OF "+year);
        XYChart.Series s = new XYChart.Series();

        String[] months = {"January","February","March","April","May","June","July","August","September",
                "October","November","December"};

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            int count = AttendanceCrudController.getAttendanceCountByMonth(likeValue);
            System.out.println(count);

            int finalI = i;
//            Thread thread = new Thread(()->{
//                for(int j = 0; j<4; j++){
//                    try {
//                        Thread.sleep(1000);
//                    }catch (Exception e){
//                        System.out.println(e);
//                    }
//                    Platform.runLater(()->{
                        s.getData().add(new XYChart.Data<>(months[finalI-1],count));
//                    });
//                }
//            });
//            thread.start();
        }

        lineChartAttendance.getData().add(s);
    }

    public void setDataToToDayCount(String date){
        int count = AttendanceCrudController.getAttendanceCountByDate(date);
        String temp ="";
        if(count<10){
            temp = "0"+count;
        }else{
            temp = String.valueOf(count);
        }
        todayCount.setText(temp);
    }

    public void setDataToMemberCmbxAttendance(){
        ObservableList<String> ob = FXCollections.observableArrayList(
                MemberCrudController.getMembersIds()
        );
        memberCmbx.setItems(ob);
    }
}

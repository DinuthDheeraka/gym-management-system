package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.NavigateUI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DashboardFormController{
    public AnchorPane anchor1;
    public AnchorPane pageLoadingAnchor;
    public Label as;
    public ImageView menuBarIamge;
    public AreaChart testTable;
    public LineChart testTable1;
    public LineChart chart2;
    public AreaChart area45;
    public PieChart piechart1;
    public Label time;
    public volatile boolean stop = false;
    public LineChart dashBoardMemberAttendanceChart;
    public String year;
    public Label lblDbMemberCount;
    public Label lblDbTrainerCount;
    public Label lblDbSupplierCount;
    public Label lblDbSupplimentCount;
    public Label lblDbEquipmentCount;
    public Label memCountToday1;


    public void initialize(){
        year = String.valueOf(LocalDate.now());
        memCountToday1.setText(String.valueOf(MemberCrudController.selectMemberCountJoinToday(String.valueOf(year))));
        loadDataToTestChart(year.substring(0,4));
        loadDataToTestChart1();
        setPieData();
        showTime();
        setCountDataToDashBoard();
        setDataToAttendanceChart(year.substring(0,4));
        dashBoardMemberAttendanceChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        //txtUserName1.setText(LoginFormCrudController.findUser1());
    }

    private void setCountDataToDashBoard() {
        int memberCount = MemberCrudController.getMemberCount();
        int supplierCount = SupplierCrudController.getSupplierCount();
        int trainerCount = TrainerCrudController.getTrainerCount();
        int supplimentCount = SupplimentCrudController.getSupplimentCount();
        int equipmentCount = EquipmentCrudController.getEquipmentCount();

        lblDbEquipmentCount.setText(getvalidMonth(equipmentCount));
        lblDbSupplimentCount.setText(getvalidMonth(supplimentCount));
        lblDbTrainerCount.setText(getvalidMonth(trainerCount));
        lblDbMemberCount.setText(getvalidMonth(memberCount));
        lblDbSupplierCount.setText(getvalidMonth(supplierCount));
    }

    public void minBtn(MouseEvent mouseEvent) {
        NavigateUI.navigator.minimizeStage(mouseEvent);
    }

    public void closeBtn(MouseEvent mouseEvent) {
        //NavigateUI.navigator.closeStage(mouseEvent);
        LoginFormCrudController.updateUserFromDB(0);
        System.exit(0);
    }

    public void manageMemberOnAction(ActionEvent actionEvent) throws IOException {
        //Ui.className = "manageM";
        NavigateUI.navigator.setNewParentToCurrentStage("ManageMemberForm",pageLoadingAnchor);
    }

    public void loadDataToTestChart(String year){
        testTable1.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        testTable1.setTitle("Member Joining Rate By Month "+year);
        XYChart.Series s = new XYChart.Series();
        s.setName("Member rate");

        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep",
                "Oct","Nov","Dec"};

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            int count = MemberCrudController.getMemberJoiningRateByMonth(likeValue);
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

        testTable1.getData().add(s);
    }

    public void loadDataToTestChart1(){
        area45.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");

        XYChart.Series s = new XYChart.Series();
        XYChart.Series s1 = new XYChart.Series();
        s.setName("This Year income");
        s1.setName("Last Year income");


        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep",
                "Oct","Nov","Dec"};

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year.substring(0,4)+"-"+month+"%";
            double income = MemberPayementCrudController.getIncomeFromMembersByMonth(likeValue);

            int finalI = i;
            s.getData().add(new XYChart.Data<>(months[finalI-1],income));

        }

        String lastYear = String.valueOf(Integer.valueOf(year.substring(0,4))-1);

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = lastYear.substring(0,4)+"-"+month+"%";
            double income = MemberPayementCrudController.getIncomeFromMembersByMonth(likeValue);

            int finalI = i;
            s1.getData().add(new XYChart.Data<>(months[finalI-1],income));

        }
        area45.getData().add(s1);
        area45.getData().add(s);
        area45.setTitle("Income From Members");
    }

    public void homeIcon(MouseEvent mouseEvent) throws IOException {
        NavigateUI.navigator.closeStage(mouseEvent);
        NavigateUI.navigator.setNewStage("DashboardForm");
    }

    public void setPieData(){
        ArrayList arrayList = MemberPayementCrudController.getAllUnPaidMembersByMonth(getLastMonth(year));
        ArrayList arrayList1 = MemberPayementCrudController.getAllPaidMembersByMonth(getLastMonth(year));
        ArrayList arrayList2 = MemberPayementCrudController.geMemberNeverSettledPayement();

        for(int i = 0; i<arrayList2.size(); i++){
            arrayList.add(arrayList2.get(i));
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(arrayList1.size()+" Paid Members", arrayList1.size()),
                new PieChart.Data(arrayList.size()+" Unpaid Members", arrayList.size()));

        piechart1.setTitle("Member's Payement Details For Last Month");
        piechart1.setData(pieChartData);
       // piechart1.setTitle("Members Payement Details For Last Month");
    }

    public void showTime(){
        Thread thread = new Thread(()->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            while(!stop){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String timeNow = simpleDateFormat.format(new Date());
                Platform.runLater(()->{
                    time.setText(timeNow);
                });
            }
        });
        thread.start();
    }

    public void trainersOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("ManageTrainerForm",pageLoadingAnchor);
    }

    public void scheduleOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("ManageScheduleForm",pageLoadingAnchor);
    }

    public void supplimentsOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("ManageSupplimentsForm",pageLoadingAnchor);
    }

    public void suppliersOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("ManageSuppliersForm",pageLoadingAnchor);
    }

    public void equipmentOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("EquipmentsForm",pageLoadingAnchor);
    }

    public void mealPlansOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("MealPlansForm",pageLoadingAnchor);
    }

    public void exercisesOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("ExerciseForm",pageLoadingAnchor);
    }

    public void attendanceOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("AttendanceForm",pageLoadingAnchor);
    }

    public void setDataToAttendanceChart(String year){
            dashBoardMemberAttendanceChart.setTitle("Members Attendance Details For year of "+year);
            XYChart.Series s = new XYChart.Series();

            String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep",
                    "Oct","Nov","Dec"};

            for(int i = 1; i<=12; i++){
                String month = getvalidMonth(i);
                String likeValue = year+"-"+month+"%";
                int count = AttendanceCrudController.getAttendanceCountByMonth(likeValue);
                System.out.println(count);

                int finalI = i;

                s.getData().add(new XYChart.Data<>(months[finalI-1],count));

            }
            s.setName("Member Attendance Count");

            dashBoardMemberAttendanceChart.getData().add(s);
    }

    public String getvalidMonth(int month){
        return month<10? "0"+month : String.valueOf(month);
    }

    public void payementOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("ManagePayementForm",pageLoadingAnchor);
    }

    public String getLastMonth(String month){
        int lastMonthIntVal = Integer.parseInt(month.substring(5,7))-1;
        String temp = "";
        if(lastMonthIntVal<10){
            temp = "0"+lastMonthIntVal;
        }else{
            temp = String.valueOf(lastMonthIntVal);
        }
        String lastMonth = month.substring(0,5)+temp;
        return  lastMonth;
    }

    public void reportsOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewParentToCurrentStage("IncomAndMemberReports",pageLoadingAnchor);
    }
}

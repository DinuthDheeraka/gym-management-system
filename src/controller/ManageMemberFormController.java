package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Member;
import model.MemberWeightFitnessReport;
import util.CrudUtil;
import util.NavigateUI;
import util.ShowNotification;
import view.tm.MemberFitnessReportTM;
import view.tm.MemberTM;
import view.tm.SupplierTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ManageMemberFormController implements Initializable {
    public AnchorPane addMemberAnchor;
    public LineChart memberAtandance;
    public TableView<MemberTM> memberTable;
    public TableColumn colMemberId;
    public TableColumn colMemberName;
    public TableColumn colMemberAddress;
    public TableColumn colMemberEMail;
    public TableColumn colMemberTelNo;
    public TableColumn colMemberAge;
    public TableColumn colMemberJDate;
    public TableColumn colMemberSchId;
    public TableColumn colMemberTraId;
    public TableColumn colMemberHeight;
    public TableColumn colMemberWeight;
    public LineChart<String,Double> memberFitnessChart;
    public ToggleGroup l;
    public JFXRadioButton memberIdRdoBtn;
    public JFXRadioButton memberNameRdoBtn;
    public JFXRadioButton memberScheduleIdRdoBtn;
    public JFXRadioButton memberTrainerIdRdoBtn;
    public TextField txtSearchMemberBar;
    public JFXComboBox cmbxMember1;
    public JFXComboBox cmbxMember2;
    public TableView<MemberFitnessReportTM> reportTbl;
    public TableColumn colReportId;
    public TableColumn colRMemberId;
    public TableColumn colDate;
    public TableColumn colHeight;
    public TableColumn colWeight;
    public JFXRadioButton reportIdRadioBtn;
    public JFXRadioButton memberIdRadioBtn;
    public TextField txtSearchReportBar;
    public Label memberFitnessLbl;
    public JFXTextField test1234;
    public Label member1FitDate;
    public Label member1FitValue;
    public Label member2FirDate;
    public Label member2FirValue;
    public JFXTextField member1Id;
    public JFXTextField member1Name;
    public JFXTextField member1JoinDate;
    public JFXTextField memberHeight2;
    public JFXTextField memberHeight;
    public JFXTextField member1Weight;
    public JFXTextField member1Weight2;
    public JFXTextField member2Id;
    public JFXTextField member2Name;
    public JFXTextField member2JoinDate;
    public JFXTextField member2Height;
    public JFXTextField member2Height2;
    public JFXTextField member2Weight;
    public JFXTextField member2Weight2;

    private double xOffset = 0;
    private double yOffset = 0;

    private String tmemberId;
    private String tmemberName;
    private String tmemberAddress;
    private String tmemberEmail;
    private String tMemberTele;
    private int tmemberAge;
    private String tmemberJoinedDate;
    private String tmemberScheduleId;
    private double tmemberHeight;
    private double tmemberWeight;

    String tempReportId;

    Parent parent;
    Scene scene;
    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colMemberAge.setCellValueFactory(new PropertyValueFactory<>("memberAge"));
        colMemberAddress.setCellValueFactory(new PropertyValueFactory<>("memberAddress"));
        colMemberTelNo.setCellValueFactory(new PropertyValueFactory<>("MemberTele"));
        colMemberTraId.setCellValueFactory(new PropertyValueFactory<>("memberTrainerId"));
        colMemberSchId.setCellValueFactory(new PropertyValueFactory<>("memberScheduleId"));
        colMemberJDate.setCellValueFactory(new PropertyValueFactory<>("memberJoinedDate"));
        colMemberHeight.setCellValueFactory(new PropertyValueFactory<>("memberHeight"));
        colMemberWeight.setCellValueFactory(new PropertyValueFactory<>("memberWeight"));
        colMemberEMail.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));

        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colRMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        setDataToMembersTable();
        setDataTocmbxMember();
        setDataToFitnessTbl();

        memberFitnessChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");


        cmbxMember1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setDataToMemberFitnessChart1((String)newValue);setDataToMember1Details((String)newValue);
        });

        cmbxMember2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setDataToMemberFitnessChart2((String)newValue);setDataToMember2Details((String)newValue);
        });

        memberTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setMemberData((MemberTM) newValue);
                });
        reportTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setReportData((MemberFitnessReportTM) newValue);
                });
    }

    private void setReportData(MemberFitnessReportTM newValue) {
        tempReportId = newValue.getReportId();
    }

    public void setMemberData(MemberTM memberData){
        tmemberId = memberData.getMemberId();
        tmemberName = memberData.getMemberName();
        tmemberAddress = memberData.getMemberAddress();
        tmemberEmail = memberData.getMemberEmail();
        tMemberTele = memberData.getMemberTele();
        tmemberAge = memberData.getMemberAge();
        tmemberJoinedDate = memberData.getMemberJoinedDate();
        tmemberScheduleId = memberData.getMemberScheduleId();
        tmemberHeight = memberData.getMemberHeight();
        tmemberWeight = memberData.getMemberWeight();
    }

    private void setDataToMember2Details(String newValue) {
        Member member = MemberCrudController.getMemberDetails(newValue);
        ArrayList<MemberFitnessReportTM> ar = MemberFitnessReportsCrudController.getLastReportByMemberId(newValue);
        member2Name.setText(member.getName());
        member2Id.setText(newValue);
        member2Height.setText(String.valueOf(member.getHeight()));
        member2Weight.setText(String.valueOf(member.getWeight()));
        member2JoinDate.setText(member.getJoinDate());
        if(ar.size()>0){
            member2Weight2.setText(String.valueOf(ar.get(0).getWeight()));
            member2Height2.setText(String.valueOf(ar.get(0).getHeight()));
        }
    }

    private void setDataToMember1Details(String newValue) {
        Member member = MemberCrudController.getMemberDetails(newValue);
        member1Name.setText(member.getName());
        ArrayList<MemberFitnessReportTM> ar = MemberFitnessReportsCrudController.getLastReportByMemberId(newValue);
        member1Id.setText(newValue);
        memberHeight.setText(String.valueOf(member.getHeight()));
        member1Weight.setText(String.valueOf(member.getWeight()));
        member1JoinDate.setText(member.getJoinDate());
        if(ar.size()>0){
            member1Weight2.setText(String.valueOf(ar.get(0).getWeight()));
            memberHeight2.setText(String.valueOf(ar.get(0).getHeight()));
        }
    }

    public void lll(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addMemberAnchor.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }


    public void addMemberOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddMemberForm");
    }

    public void setDataToMemberFitnessChart1(String member1){
        LinkedHashMap<String,Double> hashMap = MemberFitnessReportsCrudController.getMemberWeightReport(member1);
        if(hashMap.size()==0){
            ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),"Member "+member1+" Havent Created Reports!");
        }else{
            memberFitnessChart.getData().clear();
            XYChart.Series<String,Double> s = new XYChart.Series();
            s.setName("Member "+member1+" Weight");

            int i = 0;
            for(String key : hashMap.keySet()){

                s.getData().add(new XYChart.Data<>(key,hashMap.get(key)));

            }
            memberFitnessChart.getData().addAll(s);

            for(final XYChart.Data<String,Double> data: s.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        member1FitDate.setText(data.getXValue());
                        member1FitValue.setText(String.valueOf(data.getYValue()));
                    }
                });
            }
        }

    }

    public void setDataToMemberFitnessChart2(String member2){
        //memberFitnessChart.getData().clear();
        LinkedHashMap<String,Double> hashMap = MemberFitnessReportsCrudController.getMemberWeightReport(member2);
        XYChart.Series<String,Double> s2 = new XYChart.Series();
        s2.setName("Member "+member2+" Weight");

        if(hashMap.size()==0){
            ShowNotification.show(new Image("asserts/tick-mark (1)_ccexpress.png"),"Member "+member2+" Havent Created Reports!");
        }else{
            for(String key : hashMap.keySet()){

                s2.getData().add(new XYChart.Data<>(key,hashMap.get(key)));

            }
            memberFitnessChart.getData().add(s2);


            for(final XYChart.Data<String,Double> data: s2.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        member2FirDate.setText(data.getXValue());
                        member2FirValue.setText(String.valueOf(data.getYValue()));
                    }
                });
            }
        }
    }

    public void setDataToMembersTable() {
        ObservableList<MemberTM> members = FXCollections.observableArrayList(
                MemberCrudController.getAllMembers()
        );
        memberTable.setItems(members);
    }

    public void refreshContextOnAction(ActionEvent actionEvent) {
        setDataToMembersTable();
    }

    public void searchMemberOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(txtSearchMemberBar.getText().startsWith("MMB")){
            setFilteredDataToMemberTable("Member_Id",txtSearchMemberBar.getText());

        }else if(txtSearchMemberBar.getText().startsWith("TRA")){
            setFilteredDataToMemberTable("Trainer_Id",txtSearchMemberBar.getText());

        }else if(txtSearchMemberBar.getText().startsWith("SCH")){
            setFilteredDataToMemberTable("Schedule_Id",txtSearchMemberBar.getText());

        }else{
            setFilteredDataToMemberTable("Member_Name",txtSearchMemberBar.getText());
        }
    }

    public void setFilteredDataToMemberTable(String column,String value){
        switch (column){
            case "Member_Id" : ObservableList<MemberTM> obId = FXCollections.observableArrayList(
                                    MemberCrudController.selectMemberById(value)
                                );
                                 memberTable.setItems(obId);break;

            case "Member_Name" :  ObservableList<MemberTM> obName = FXCollections.observableArrayList(
                                    MemberCrudController.selectMemberByName(value)
                                );
                                 memberTable.setItems(obName);break;

            case "Schedule_Id" :  ObservableList<MemberTM> obSchedule = FXCollections.observableArrayList(
                                    MemberCrudController.selectMemberByScheduleId(value)
                                );
                                memberTable.setItems(obSchedule);break;

            case "Trainer_Id" :  ObservableList<MemberTM> obSTrainer = FXCollections.observableArrayList(
                                    MemberCrudController.selectMemberByTrainerId(value)
                                );
                                memberTable.setItems(obSTrainer);break;
        }
    }

    public void setDataTocmbxMember(){
        ObservableList<String> observableList = FXCollections.observableArrayList(
                MemberCrudController.getMembersIds()
        );
        cmbxMember1.setItems(observableList);

        ObservableList<String> observableList2 = FXCollections.observableArrayList(
                MemberCrudController.getMembersIds()
        );
        cmbxMember2.setItems(observableList);
    }


//    public void test(){
//        memberFitnessChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
//
//        XYChart.Series s = new XYChart.Series();
//        XYChart.Series s1 = new XYChart.Series();
//
//        for(memberAttandance memberAttandance : Database.arrayList){
//            s.getData().add(new XYChart.Data<>(memberAttandance.getMonth(),memberAttandance.getCount()));
//        }
//
//        for(memberAttandance memberAttandance : Database.arrayList1){
//            s1.getData().add(new XYChart.Data<>(memberAttandance.getMonth(),memberAttandance.getCount()));
//        }
//        s.setName("Last Year");
//        s1.setName("This Year");
//
//        memberFitnessChart.getData().add(s1);
//        memberFitnessChart.getData().add(s);
//    }

    public void searchReportOnAction(ActionEvent actionEvent) {
//        if(reportIdRadioBtn.isSelected()){
//            ObservableList<MemberFitnessReportTM> ob = FXCollections.observableArrayList(
//                    MemberFitnessReportsCrudController.getReportsByReportId(txtSearchReportBar.getText())
//            );
//            reportTbl.setItems(ob);
//        }else{
//            ObservableList<MemberFitnessReportTM> ob = FXCollections.observableArrayList(
//                    MemberFitnessReportsCrudController.getReportsByMemberId(txtSearchReportBar.getText())
//            );
//            reportTbl.setItems(ob);
//        }

        searchMemberReport();
    }

    public void makeReportOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddMemberReportForm");
    }

    public void setDataToFitnessTbl(){
        ObservableList<MemberFitnessReportTM> ob = FXCollections.observableArrayList(
                MemberFitnessReportsCrudController.getAllFitnessReports()
        );
        reportTbl.setItems(ob);
    }

    public void refreshTblCtmOnAction(ActionEvent actionEvent) {
        setDataToFitnessTbl();
    }

    public void memberFitnessReportsSearchBarKeyPressed(KeyEvent keyEvent) {

    }

    public void memberSearchBarKeyPressed(KeyEvent keyEvent) {
        if(txtSearchMemberBar.getText().startsWith("MMB")){
            FilterByMemberIdCounterTask counterTask = new FilterByMemberIdCounterTask(
                    txtSearchMemberBar.getText()
            );

            counterTask.valueProperty().addListener(new ChangeListener<ObservableList<MemberTM>>() {
                @Override
                public void changed(ObservableValue<? extends ObservableList<MemberTM>> observable, ObservableList<MemberTM> oldValue, ObservableList<MemberTM> newValue) {
                    memberTable.setItems(newValue);
                }
            });

            Thread thread = new Thread(counterTask);
            thread.setDaemon(true);
            thread.start();

        }else if(txtSearchMemberBar.getText().startsWith("SCH")){

            FilterMemberBySchCounterTask counterTask = new FilterMemberBySchCounterTask(txtSearchMemberBar.getText());
            counterTask.valueProperty().addListener(new ChangeListener<ObservableList<MemberTM>>() {
                @Override
                public void changed(ObservableValue<? extends ObservableList<MemberTM>> observable, ObservableList<MemberTM> oldValue, ObservableList<MemberTM> newValue) {
                    memberTable.setItems(newValue);
                }
            });

            Thread thread = new Thread(counterTask);
            thread.setDaemon(true);
            thread.start();


        }else if(txtSearchMemberBar.getText().startsWith("TRA")){

            FilterByTrainerCounterTask counterTask = new FilterByTrainerCounterTask(txtSearchMemberBar.getText());
            counterTask.valueProperty().addListener(new ChangeListener<ObservableList<MemberTM>>() {
                @Override
                public void changed(ObservableValue<? extends ObservableList<MemberTM>> observable, ObservableList<MemberTM> oldValue, ObservableList<MemberTM> newValue) {
                    memberTable.setItems(newValue);
                }
            });

            Thread thread = new Thread(counterTask);
            thread.setDaemon(true);
            thread.start();
        }else{
            FilterMemberByNameSearchCounterTask counterTask = new
                    FilterMemberByNameSearchCounterTask(txtSearchMemberBar.getText());
            counterTask.valueProperty().addListener(new ChangeListener<ObservableList<MemberTM>>() {
                @Override
                public void changed(ObservableValue<? extends ObservableList<MemberTM>> observable, ObservableList<MemberTM> oldValue, ObservableList<MemberTM> newValue) {
                    memberTable.setItems(newValue);
                }
            });

            Thread thread = new Thread(counterTask);
            thread.setDaemon(true);
            thread.start();
        }

    }

    public void updateMemberCtmOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddMemberForm.fxml"));
        parent = fxmlLoader.load();

        AddMemberFormController addMemberFormController = fxmlLoader.getController();
        addMemberFormController.setValuesForTextFields(
                tmemberId,tmemberAge,tmemberAddress,tmemberEmail,tmemberHeight,
                tmemberJoinedDate,tmemberName,getScheduleIndex(tmemberScheduleId),tMemberTele,
                tmemberWeight,"ADD","UPDATE","UPDATE MEMBER"
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);
    }

    public void deleteMemberCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            // delete
            //MemberPayementCrudController.deleteMemberPayement(tmemberId);
            MemberCrudController.deletetMember(tmemberId);
        }
        setDataToMembersTable();
    }

    public int getScheduleIndex(String scheduleId){
        int index = -1;
        ArrayList<String> arrayList =  ScheduleCrudController.getSchedulesIds();
        for(int  i = 0; i<arrayList.size(); i++){
            if(scheduleId.equals(arrayList.get(i))){
                index = i;
            }
        }
        return index;
    }

    public void deleteTblCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            // delete
            MemberFitnessReportsCrudController.deleteReport(tempReportId);
        }
    }

    public void memberFitnessReportsSearchBarKeyReleased(KeyEvent keyEvent) {
        searchMemberReport();
    }


    public void searchMemberReport(){
        if(txtSearchReportBar.getText().startsWith("MMB")){
            ObservableList<MemberFitnessReportTM> ob = FXCollections.observableArrayList(
                    MemberFitnessReportsCrudController.searchReportByMemberId(
                            "%"+txtSearchReportBar.getText()+"%"
                    )
            );
            reportTbl.setItems(ob);
        }
        else if(txtSearchReportBar.getText().startsWith("MFR")){
            ObservableList<MemberFitnessReportTM> ob = FXCollections.observableArrayList(
                    MemberFitnessReportsCrudController.searchReportByReportId(
                            "%"+txtSearchReportBar.getText()+"%"
                    )
            );
            reportTbl.setItems(ob);
        }
    }
}

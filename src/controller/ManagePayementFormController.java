package controller;

import com.jfoenix.controls.*;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.IdsGenerator;
import util.Validator;
import view.tm.MemberPayementStatusTM;
import view.tm.MemberPayementTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManagePayementFormController implements Initializable {
    public LineChart incomeFromMembersChart;
    public TableView<MemberPayementTM> memberPayementTbl;
    public TableColumn memberPayementTblPId;
    public TableColumn memberPayementTblSDate;
    public TableColumn memberPayementTblSTime;
    public TableColumn memberPayementTblMonth;
    public TableColumn memberPayementTblAmount;
    public TableColumn memberPayementTblMId;
    public TextField txtMemberPayementSearchBar;
    public JFXButton memberPayementSearchBtn;
    public TextField txtMemberPayementcStatusSearchBar;
    public TableView memberPayementStatusTbl;
    public TableColumn memberPayementStatusTblMId;
    public TableColumn memberPayementStatusTblMName;
    public TableColumn memberPayementStatusTblMmail;
    public TableColumn memberPayementStatusTblMAddress;
    public TableColumn memberPayementStatusTblMTele;
    public TableColumn memberPayementStatusTblMJDate;
    public JFXToggleButton unpaidMemberToggle;
    public JFXTextField txtPayementId;
    public JFXTextField txtPayingMonth;
    public JFXDatePicker txtPaidDate;
    public JFXTimePicker txtPaidTime;
    public JFXTextField txtPaidAmount;
    public JFXComboBox cmbxMemberList;
    public Label lblTotalMembersSettled;
    public Label lbltodayIncome;
    public Label lblLastMonthUnPaid;
    public TextField txtSearchInComeBar;
    public JFXToggleButton toggleMemberId;
    public JFXToggleButton toggleDate;
    public JFXToggleButton toggleMonth;
    public JFXButton payBtn;
    LocalDate localDate;
    String tempyear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localDate = LocalDate.now();
        tempyear = String.valueOf(localDate).substring(0,4);
        memberPayementTblAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        memberPayementTblSDate.setCellValueFactory(new PropertyValueFactory<>("settleDate"));
        memberPayementTblSTime.setCellValueFactory(new PropertyValueFactory<>("settleTime"));
        memberPayementTblMId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        memberPayementTblMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        memberPayementTblPId.setCellValueFactory(new PropertyValueFactory<>("payementId"));

        memberPayementStatusTblMAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        memberPayementStatusTblMName.setCellValueFactory(new PropertyValueFactory<>("membername"));
        memberPayementStatusTblMId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        memberPayementStatusTblMJDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        memberPayementStatusTblMTele.setCellValueFactory(new PropertyValueFactory<>("tele"));
        memberPayementStatusTblMmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        incomeFromMembersChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        setDataToMemberCmbx();
        setValuesForPayementForm(String.valueOf(localDate));
        setDataToIncomeChart(tempyear);
        setDataToMemberPayementTable();

        txtPayementId.setText(genId());
        txtPayementId.setEditable(false);
        payBtn.setDisable(true);
    }

    public void memberPayementSearchBtnOnAction(ActionEvent actionEvent) {
        if(toggleMonth.isSelected()){
            ObservableList<MemberPayementTM> ob = FXCollections.observableArrayList(
                    MemberPayementCrudController.getAllMemberPayementsByMonth(txtMemberPayementSearchBar.getText())
            );
            memberPayementTbl.setItems(ob);
        }
        if(toggleMemberId.isSelected()){
            ObservableList<MemberPayementTM> ob = FXCollections.observableArrayList(
                    MemberPayementCrudController.getAllMemberPayementsByMemberId(txtMemberPayementSearchBar.getText())
            );
            memberPayementTbl.setItems(ob);
        }
        if(toggleDate.isSelected()){
            ObservableList<MemberPayementTM> ob = FXCollections.observableArrayList(
                    MemberPayementCrudController.getAllMemberPayementsBySettledDate(txtMemberPayementSearchBar.getText())
            );
            memberPayementTbl.setItems(ob);
        }
    }

    public void memberPayementStatusSearchBtnOnAction(ActionEvent actionEvent) {
        if(unpaidMemberToggle.isSelected()){
            ObservableList<MemberPayementStatusTM> ob = FXCollections.observableArrayList(
                    MemberPayementCrudController.getAllUnPaidMembersByMonth(txtMemberPayementcStatusSearchBar.getText())
            );

            ArrayList<MemberPayementStatusTM> ar = MemberPayementCrudController.geMemberNeverSettledPayement();
            for(MemberPayementStatusTM tm : ar){
                ob.add(tm);
            }

            memberPayementStatusTbl.setItems(ob);
        }else{
            ObservableList<MemberPayementStatusTM> ob = FXCollections.observableArrayList(
                    MemberPayementCrudController.getAllPaidMembersByMonth(txtMemberPayementcStatusSearchBar.getText())
            );
            memberPayementStatusTbl.setItems(ob);
        }

    }

    public void markAsPaidBtnOnAction(ActionEvent actionEvent) {
        MemberPayementCrudController.insertMemberPayement(
                txtPayementId.getText(),txtPaidDate.getValue(),
                txtPaidTime.getValue(),txtPayingMonth.getText(),
                txtPaidAmount.getText(),
                String.valueOf(cmbxMemberList.getSelectionModel().getSelectedItem())
        );
        txtPayementId.setText(genId());
        setValuesForPayementForm(String.valueOf(localDate));
    }

    public void setDataToMemberCmbx(){
        ObservableList<String> ob = FXCollections.observableArrayList(
                MemberCrudController.getMembersIds()
        );
        cmbxMemberList.setItems(ob);
    }

    public void setValuesForPayementForm(String date){
        int count = MemberPayementCrudController.getAllPaidMembersCountByDate(date);
        int count2 = MemberPayementCrudController.geMemberNeverSettledPayement().size();
        double income = MemberPayementCrudController.getIncomeFromMembersByDate(date);
        ArrayList arrayList = MemberPayementCrudController.getAllUnPaidMembersByMonth(getLastMonth(date));
        if(count==-1){
            count = 0;
        }
        lblTotalMembersSettled.setText(count+" Members");
        lbltodayIncome.setText("LKR."+income);
        lblLastMonthUnPaid.setText(((arrayList.size())+count2)+" Members");

        //System.out.println(arrayList.size());
    }

    public String getLastMonth(String date){
        String lastMonth = "";
        String month = date.substring(5,7);
        int temp = Integer.valueOf(month)-1;
        if(temp<10){
            month = "0"+temp;
        }else{
            month = String.valueOf(temp);
        }
        lastMonth = date.substring(0,5)+month;
        return lastMonth;
    }

    public void setDataToIncomeChart(String year){
        incomeFromMembersChart.getData().clear();
        incomeFromMembersChart.setTitle("Income report from Members's Payements of "+year);
        XYChart.Series s = new XYChart.Series();

        String[] months = {"January","February","March","April","May","June","July","August","September",
                "October","November","December"};

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            double income = MemberPayementCrudController.getIncomeFromMembersByMonth(likeValue);

            int finalI = i;
            s.getData().add(new XYChart.Data<>(months[finalI-1],income));

        }

        incomeFromMembersChart.getData().add(s);
    }

    public String getvalidMonth(int month){
        return month<10? "0"+month : String.valueOf(month);
    }

    public void txtSearchInComeBtnOnAction(ActionEvent actionEvent) {
        setDataToIncomeChart(txtSearchInComeBar.getText());
    }

    public void setDataToMemberPayementTable(){
        ObservableList<MemberPayementTM> ob = FXCollections.observableArrayList(
                MemberPayementCrudController.getAllMemberPayements()
        );
        memberPayementTbl.setItems(ob);
    }

    public void txtMemberPayementSearchBarOnAction(ActionEvent actionEvent) {
        memberPayementSearchBtnOnAction(new ActionEvent());
    }

    public void payementHIstoryRefreshCtmOnAction(ActionEvent actionEvent) {
        setDataToMemberPayementTable();
    }

    public void validate(KeyEvent keyEvent) {
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern monthP = Pattern.compile("[0-9]{4}-[0-9]{2}$");
        Pattern amountP = Pattern.compile("[0-9.]{1,}$");

        map.put(txtPayingMonth,monthP);
        map.put(txtPaidAmount,amountP);
        Validator.validate(map,payBtn);
    }

    public String genId(){
        return IdsGenerator.genarateId("MMP-",MemberPayementCrudController.geMemberPayementLastId());
//        return IdsGenerator.genarate(Mods.MEMBER_MONTHLY_PAYEMENT,
//                MemberPayementCrudController.geMemberPayementLastId());
    }

    public void vireIncomeReport(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {

        HashMap hashMap = new HashMap();
        hashMap.put("year",txtSearchInComeBar.getText());
        hashMap.put("likeVal",txtSearchInComeBar.getText()+"%");
        Connection connection = DBConnection.getInstance().getConnection();

        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/IncomeFromPayementsEachMonth.jrxml"));

        JasperReport compile = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint print = JasperFillManager.fillReport(compile,hashMap,connection);

        JasperViewer.viewReport(print,false);
    }
}

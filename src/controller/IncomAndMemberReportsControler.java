package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class IncomAndMemberReportsControler implements Initializable {
    public LineChart incomeChart;
    public TextField txtIncomeSearch;
    public LineChart memberCountChart;
    public TextField txtCountSearch;
    public Label lblIncomeAmount;
    public Label lblExpensesMonth;
    public Label lblExpensesAmount;
    public Label lblIncomeMonth;
    public Label lblTotalExpences;
    public Label lblTotalRevenue;
    public Label lblTotalIncome;
    public Label lblSearchedYearMembers;
    public Label lblSearchedYearMembersCount;
    public Label reportDateMembers;
    public Label reportMonthMembers;
    public Label reportYearMembers;
    public Label txtYearName;
    String year;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        year = String.valueOf(LocalDate.now());
        setMemberCount(year.substring(0,4));
        loadDataToIncomeChart(year.substring(0,4));
        setInComeValues(year.substring(0,4));
        incomeChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        memberCountChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        setDataToMemberJoiningCountsThisYear(year);
        setYearMemberCount(year.substring(0,4));
    }

    public void setMemberCount(String year){
        memberCountChart.getData().clear();
        memberCountChart.setTitle("Member Joining Rate By Month "+year);
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

            s.getData().add(new XYChart.Data<>(months[finalI-1],count));

        }

        memberCountChart.getData().add(s);
    }

    public String getvalidMonth(int month){
        return month<10? "0"+month : String.valueOf(month);
    }

    public void searchCountOnAction(ActionEvent actionEvent) {
        setMemberCount(txtCountSearch.getText());
        setYearMemberCount(txtCountSearch.getText());
    }

    private void setYearMemberCount(String year) {
        txtYearName.setText(year);
        reportYearMembers.setText(
                String.valueOf(MemberCrudController.getMemberJoiningRateByMonth(year+"%")));
    }


    public void loadDataToIncomeChart(String year){
        incomeChart.getData().clear();

        XYChart.Series<String,Double> s = new XYChart.Series();
        XYChart.Series<String,Double> s1 = new XYChart.Series();
        s.setName("This Year income");
        s1.setName("This Year Expensec");


        String[] months = {"January","February","March","April","May","June","July","August","September",
                "October","November","December"};

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            double income = MemberPayementCrudController.getIncomeFromMembersByMonth(likeValue);

            int finalI = i;
            s.getData().add(new XYChart.Data<>(months[finalI-1],income));

        }

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            double expenses = MyOrdersCrudController.getExpensesByMonth(likeValue);

            int finalI = i;
            s1.getData().add(new XYChart.Data<>(months[finalI-1],expenses));

        }
        incomeChart.getData().add(s1);
        incomeChart.getData().add(s);

        for(final XYChart.Data<String,Double> data: s1.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblExpensesMonth.setText(data.getXValue());
                    lblExpensesAmount.setText(String.valueOf(data.getYValue()));
                }
            });
        }

        for(final XYChart.Data<String,Double> data: s.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lblIncomeMonth.setText(data.getXValue());
                    lblIncomeAmount.setText(String.valueOf(data.getYValue()));
                }
            });
        }

//        incomeChart.getData().add(s1);
//        incomeChart.getData().add(s);
        incomeChart.setTitle("Income & Expenses for Year "+ year);
    }

    public void incomeSearchBtnOnAction(ActionEvent actionEvent) {
        loadDataToIncomeChart(txtIncomeSearch.getText());
        setInComeValues(txtIncomeSearch.getText());
    }

    public void setInComeValues(String year){
        double income = 0;

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            income += MemberPayementCrudController.getIncomeFromMembersByMonth(likeValue);
        }

        double expenses = 0;

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            expenses += MyOrdersCrudController.getExpensesByMonth(likeValue);
        }

        lblTotalIncome.setText(String.valueOf(income));
        lblTotalExpences.setText(String.valueOf(expenses));
        lblTotalRevenue.setText(String.valueOf(income-expenses));
    }

    public void viewReportBtnOnAction(ActionEvent actionEvent) throws JRException {

        IncomeJasperCounterTask incomeJasperCounterTask = new IncomeJasperCounterTask("",txtIncomeSearch.getText());

        Thread thread = new Thread(incomeJasperCounterTask);
        thread.setDaemon(true);
        thread.start();

        //        HashMap hashMap = new HashMap();
//        hashMap.put("year","2022");
//        hashMap.put("income","200000");
//        hashMap.put("expenses2","100000");
//        hashMap.put("revenue","100000");
//
//        ArrayList<JasperRevenue> arrayList = new ArrayList();
//        arrayList.add(new JasperRevenue("Jan","10000","5000","5000"));
//
//
//
//
//        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/incomeReportjrxml.jrxml"));
//
//        JasperReport compile = JasperCompileManager.compileReport(jasperDesign);
//
//        JasperPrint print = JasperFillManager.fillReport(compile,hashMap,new JRBeanCollectionDataSource(arrayList));
//
//          JasperViewer.viewReport(print,false);
    }

    public double getIncome(){
        double income = 0;
        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            income += MemberPayementCrudController.getIncomeFromMembersByMonth(likeValue);
        }
        return income;
    }

    public double getExpenses(){
        double expenses = 0;
        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";
            expenses += MyOrdersCrudController.getExpensesByMonth(likeValue);
        }
        return expenses;
    }


    public void setDataToMemberJoiningCountsThisYear(String year){
        int monthlyCount = MemberCrudController.getMemberJoiningRateByMonth(year.substring(0,7)+"%");
        int yearCount = MemberCrudController.getMemberJoiningRateByMonth(year.substring(0,4)+"%");
        int todayCount = MemberCrudController.selectMemberCountJoinToday(year+"%");

        reportYearMembers.setText(String.valueOf(yearCount));
        reportMonthMembers.setText(String.valueOf(monthlyCount));
        reportDateMembers.setText(String.valueOf(todayCount));
    }
}

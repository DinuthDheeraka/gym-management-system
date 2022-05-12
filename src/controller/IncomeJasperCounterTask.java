package controller;

import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class IncomeJasperCounterTask extends Task<JasperPrint> {
    String year;
    String[] months = {"January","February","March","April","May","June","July","August","September",
    "October","November","December"};

    IncomeJasperCounterTask(String location,String year){
        this.year = year;
    }

    @Override
    protected JasperPrint call() throws Exception {

        LinkedHashMap hashMap = new LinkedHashMap();
        ArrayList<JasperRevenue> ar = new ArrayList();

//        hashMap.put("year","2022");
//        hashMap.put("income","200000");
//        hashMap.put("expenses2","100000");
//        hashMap.put("revenue","100000");

        hashMap.put("year",this.year);
        hashMap.put("income",String.valueOf(getIncome()));
        hashMap.put("expenses2",String.valueOf(getExpenses()));
        hashMap.put("revenue",String.valueOf(getIncome()-getExpenses()));

        for(int i = 1; i<=12; i++){
            String month = getvalidMonth(i);
            String likeValue = year+"-"+month+"%";

            double income = MemberPayementCrudController.getIncomeFromMembersByMonth(likeValue);
            double expenses = MyOrdersCrudController.getExpensesByMonth(likeValue);

            System.out.println(months[i-1]+" "+expenses);

            ar.add(new JasperRevenue(
                    months[i-1],String.valueOf(income),String.valueOf(expenses),
                    String.valueOf(income-expenses)
            ));
        }

        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/incomeReportjrxml.jrxml"));

        JasperReport compile = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint print = JasperFillManager.fillReport(compile,hashMap,new JRBeanCollectionDataSource(ar));

        JasperViewer.viewReport(print,false);

        return print;
    }

    public String getvalidMonth(int month){
        return month<10? "0"+month : String.valueOf(month);
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
}

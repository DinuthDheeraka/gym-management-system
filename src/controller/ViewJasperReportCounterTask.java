package controller;

import javafx.concurrent.Task;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import view.tm.ViewScheduleJasperTM;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewJasperReportCounterTask extends Task<JasperPrint> {

    String reportLocation;
    ArrayList<String> scheduleDetails;

    ViewJasperReportCounterTask(String location){
        this.reportLocation = location;
    }

    ViewJasperReportCounterTask(String location,ArrayList<String> a){
        this.scheduleDetails = a;
        this.reportLocation = location;
    }

    @Override
    protected JasperPrint call() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("memberName",
                MemberCrudController.getMemberNameBySchedule(scheduleDetails.get(0)));
        hashMap.put("daysPerWeek",scheduleDetails.get(4));
        hashMap.put("sDate",scheduleDetails.get(2));
        hashMap.put("eDate",scheduleDetails.get(3));
        hashMap.put("SCHEDULE NO",scheduleDetails.get(0));
        hashMap.put("Trainer",TrainerCrudController.getTrainerName(scheduleDetails.get(1)));

        ArrayList<ViewScheduleJasperTM> ar = ScheduleCrudController.getScheduleForJasper(
                scheduleDetails.get(0)
        );

        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/MemberSchedule.jrxml"));

        JasperReport compile = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint print = JasperFillManager.fillReport(compile,hashMap,new JRBeanCollectionDataSource(ar));

        return print;
    }

//    public ArrayList<ViewScheduleJasperTM> getSchedule(){
//
//    }
    public String getMemberName(){
        return null;
    }
}

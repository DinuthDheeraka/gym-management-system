package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.NavigateUI;
import view.tm.MemberFitnessReportTM;
import view.tm.MemberTM;
import view.tm.SupplimentTM;
import view.tm.ViewScheduleJasperTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageSupplimentsFormController implements Initializable {

    public TableView<SupplimentTM> supplimentTbl;
    public TableColumn suppTblColPId;
    public TableColumn suppTblColPDescription;
    public TableColumn suppTblColUPrice;
    public TableColumn suppTblColCategorie;
    public TableColumn suppTblColQty;
    private Parent parent;
    private Stage stage;
    private Scene scene;

    private String id;
    private String description;
    private String type;
    private double unitPrice;
    private int qty;

    public TextField txtSearchSuppliment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        suppTblColCategorie.setCellValueFactory(new PropertyValueFactory<>("type"));
        suppTblColPId.setCellValueFactory(new PropertyValueFactory<>("id"));
        suppTblColUPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        suppTblColPDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        suppTblColQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        setDataToSupplientTbl();

        supplimentTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setSupplimentData((SupplimentTM) newValue);
                });
    }

    private void setSupplimentData(SupplimentTM newValue) {
        id = newValue.getId();
        description = newValue.getDescription();
        qty = newValue.getQty();
        unitPrice = newValue.getUnitPrice();
        type = newValue.getType();
    }

    public void refreshCtmOnAction(ActionEvent actionEvent) {
        setDataToSupplientTbl();
    }

    public void setDataToSupplientTbl(){
        ObservableList<SupplimentTM> ob = FXCollections.observableArrayList(
                SupplimentCrudController.getAllSuppliments()
        );

        supplimentTbl.setItems(ob);
    }

    public void updateCtmOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/addSupplimentForm.fxml"));
        parent = fxmlLoader.load();

        AddSupplimentFormController loaderController = fxmlLoader.getController();
        loaderController.setValuesForTextFields(
                id,description,type,unitPrice,qty
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);

    }

    public void deleteCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            // delete
            SupplimentCrudController.deleteSuppliment(id);
        }
    }

    public void jasper(ActionEvent actionEvent) throws JRException {


//        ViewJasperReportCounterTask counterTask = new ViewJasperReportCounterTask("/view/reports/MemberSchedule.jrxml");
//        counterTask.valueProperty().addListener(new ChangeListener<JasperPrint>() {
//            @Override
//            public void changed(ObservableValue<? extends JasperPrint> observable, JasperPrint oldValue, JasperPrint newValue) {
//                JasperViewer.viewReport(newValue,false);
//            }
//        });
//
//        Thread thread = new Thread(counterTask);
//        thread.setDaemon(true);
//        thread.start();

//        HashMap hashMap = new HashMap();
//        hashMap.put("memberName","D.W.Dinuth Dheeraka Sethmal Fonseka");
//        hashMap.put("daysPerWeek","4");
//        hashMap.put("sDate","2020-01-01");
//        hashMap.put("eDate","2020-02-31");
//        hashMap.put("SCHEDULE NO","SCH-000");
//
//        ViewScheduleJasperTM[] tms = new ViewScheduleJasperTM[4];
//        tms[0] = new ViewScheduleJasperTM("Push Ups","Shoulders","3 - 15 x 3");
//        tms[1] = new ViewScheduleJasperTM("Russian Twist","ABS","4 - 20 x 4");
//        tms[2] = new ViewScheduleJasperTM("Sit Ups","ABS","3 - 50 x 3");
//        tms[3] = new ViewScheduleJasperTM("Leg Curl","Legs","3 - 6 x 3");
//
//        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/MemberSchedule.jrxml"));
//
//        JasperReport compile = JasperCompileManager.compileReport(jasperDesign);
//
//        JasperPrint print = JasperFillManager.fillReport(compile,hashMap,new JRBeanArrayDataSource(tms));
//
//        JasperViewer.viewReport(print,false);

    }

    public void txtSearchSupplimentOnKeyReleased(KeyEvent keyEvent) {
        searchSuppliment();
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
        searchSuppliment();
    }

    public void searchSuppliment(){
        if(txtSearchSuppliment.getText().startsWith("SUP-")){
            ObservableList<SupplimentTM> ob = FXCollections.observableArrayList(
                    SupplimentCrudController.searchSupplimentById("%"+txtSearchSuppliment.getText()+"%")
            );
            supplimentTbl.setItems(ob);
        }else if(txtSearchSuppliment.getText().length()>0){
            ObservableList<SupplimentTM> ob1 = FXCollections.observableArrayList(
                    SupplimentCrudController.searchSupplimentByType("%"+txtSearchSuppliment.getText()+"%")
            );

            ObservableList<SupplimentTM> ob2 = FXCollections.observableArrayList(
                    SupplimentCrudController.searchSupplimentByDesc("%"+txtSearchSuppliment.getText()+"%")
            );

            if(ob1.size()>0 || ob2.size()>0){
                for(SupplimentTM supplimentTM : ob2){
                    if(!isExists(ob1,supplimentTM)){
                        ob1.add(supplimentTM);
                    }
                }

                supplimentTbl.setItems(ob1);
            }
        }
    }

    public boolean isExists(ObservableList<SupplimentTM> ob1,SupplimentTM tm){
        boolean isExists = false;
        for(SupplimentTM tm1 : ob1){
            if(tm1.equals(tm.getId())){
                isExists = true;
            }
        }
        return isExists;
    }
}

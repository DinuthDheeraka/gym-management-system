package controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddOrderFormController implements Initializable {
    public JFXTextField txtOrderId;
    public JFXComboBox cmbxSuppliers;
    public JFXTextField txtTotalCost;
    public JFXDatePicker date;
    public JFXTimePicker time;
    public JFXButton addBtn;
    public Label addOrderLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataToSuppliercmbx();
        txtOrderId.setText(genId());
        txtOrderId.setEditable(false);
        addBtn.setDisable(true);
    }

    public void addOrderCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addOrderOnAction(ActionEvent actionEvent) {
        if(addBtn.getText().equals("ADD ORDER")){
            MyOrdersCrudController.addMyOrder(txtOrderId.getText(),date.getValue(),time.getValue(),
                    txtTotalCost.getText(),String.valueOf(cmbxSuppliers.getSelectionModel().getSelectedItem()));
            txtOrderId.setText(genId());
        }else{
            MyOrdersCrudController.updateMyOrder(
                    date.getValue(),time.getValue(),txtTotalCost.getText(),
                    String.valueOf(cmbxSuppliers.getSelectionModel().getSelectedItem()),
                    txtOrderId.getText()
            );
        }
    }

    public void setDataToSuppliercmbx(){
        ObservableList<String> ob = FXCollections.observableArrayList(
                SupplierCrudController.getSupplierIds()
        );

        cmbxSuppliers.setItems(ob);
    }

    public void validate(KeyEvent keyEvent) {

        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(MOR-)[0-9]{3,6}$");
        Pattern totalP = Pattern.compile("^[1-9][0-9.]{1,}");

        map.put(txtOrderId,idP);
        map.put(txtTotalCost,totalP);

        boolean b = Validator.validate(map,addBtn);
        if(b==false){
            addBtn.setDisable(true);
        }else{
            addBtn.setDisable(false);
        }
    }

    public String genId(){
        return IdsGenerator.genarateId("MOR-",
                MyOrdersCrudController.getMyOrderLastId());
        //return IdsGenerator.genarate(Mods.MY_ORDER,MyOrdersCrudController.getMyOrderLastId());
    }

    public void setValuesForTextFields(Object... params){
        txtOrderId.setText(String.valueOf(params[0]));
        cmbxSuppliers.getSelectionModel().clearAndSelect(Integer.valueOf((Integer) params[1]));
        System.out.println("Supp"+((Integer) params[1]));
        txtTotalCost.setText(String.valueOf(params[2]));
        date.setValue(LocalDate.parse(String.valueOf(params[3])));
        time.setValue(LocalTime.parse(String.valueOf(params[4])));
        addBtn.setText("Update");
    }
}

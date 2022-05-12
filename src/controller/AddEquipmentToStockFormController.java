package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import util.EmptyChecker;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;
import view.tm.EquipmentTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddEquipmentToStockFormController implements Initializable {
    public AnchorPane addEqipmentToStockContext;
    public JFXComboBox cmbxOrders;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtBPrice;
    public JFXTextField txtType;
    public JFXTextField txtQty;
    public JFXButton addUquipmentToStockBtnOnAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataToOrderCmbx();
        txtItemCode.setText(genId());
       // txtItemCode.setEditable(false);
    }

    public void addSupplimetsOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+"AddItemsToStockForm"+".fxml"));
        //targetLocation.getChildren().clear();
        addEqipmentToStockContext.getChildren().add(parent);
    }

    public void addEquipmentToStockCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addUquipmentToStockBtnOnAction(ActionEvent actionEvent) {
        //if(EmptyChecker.isEmpty(txtItemCode,txtDescription,txtBPrice,txtType,txtQty)==false){
       //     System.out.println(111111111);
       // }
      //  else{
            if(isExistsEquipment(txtItemCode.getText())){
                EquipmentCrudController.updateEquipmentByMyOrder(Integer.parseInt(txtQty.getText()));
            }else{
                EquipmentCrudController.insertEquipment(
                        txtItemCode.getText(),txtDescription.getText(),
                        txtQty.getText(),txtType.getText()
                );
                txtItemCode.setText(genId());
            }

            MyOrdersCrudController.addMyOrderDetail(
                    String.valueOf(cmbxOrders.getSelectionModel().getSelectedItem()),
                    txtItemCode.getText(),
                    "EQUIPMENT",Double.valueOf(txtBPrice.getText()),
                    Integer.parseInt(txtQty.getText()),txtDescription.getText()
            );
      //  }
    }

    public boolean isExistsEquipment(String id){
        ArrayList<String> arrayList = EquipmentCrudController.getAllEquipmentsIds();
        for(String s : arrayList){
            if(id.equals(s)){
                return true;
            }
        }
        return false;
    }

    public void setDataToOrderCmbx(){
        ObservableList<String> ob = FXCollections.observableArrayList(
                MyOrdersCrudController.getMyOrdersIds()
        );
        cmbxOrders.setItems(ob);
    }

    public void validate(KeyEvent keyEvent) {
        eqpAutoFill(txtItemCode.getText());
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern itemCodeP = Pattern.compile("^(EQP-)[0-9]{3,6}$");
        Pattern itemDescP = Pattern.compile("[A-ba-z0-9 /.]{1,50}");
        Pattern buyingP = Pattern.compile("[0-9.]{1,}");
        Pattern typeP = Pattern.compile("[A-Za-z 0-9,/.]{1,100}");
        Pattern qytP = Pattern.compile("[0-9]{1,}");

        map.put(txtItemCode,itemCodeP);
        map.put(txtDescription,itemDescP);
        map.put(txtBPrice,buyingP);
        map.put(txtType,typeP);
        map.put(txtQty,qytP);

        boolean b =  Validator.validate(map,addUquipmentToStockBtnOnAction);
        if(b==false){
            addUquipmentToStockBtnOnAction.setDisable(true);
        }else{
            addUquipmentToStockBtnOnAction.setDisable(false);
        }
    }

    public String genId(){
        return IdsGenerator.genarateId("EQP-",EquipmentCrudController.getEqpLastId());
       // return IdsGenerator.genarate(Mods.EQUIPMENT,EquipmentCrudController.getEqpLastId());
    }

    public void eqpAutoFill(String id){
        ArrayList<EquipmentTM> arrayList = EquipmentCrudController.eqpAutoFill("%"+id+"%");
        if(arrayList.size()>0){
            txtDescription.setText(arrayList.get(0).getDescription());
            txtType.setText(arrayList.get(0).getType());
        }
    }
}

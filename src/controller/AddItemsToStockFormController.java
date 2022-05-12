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
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;
import view.tm.SupplimentTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddItemsToStockFormController implements Initializable {
    public AnchorPane addItemsToStockContext;
    public JFXComboBox cmbxOrderList;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtBuyingPrice;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtSupplimentType;
    public JFXTextField txtQty;
    public JFXButton addBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataToOrderCmbx();
        txtItemCode.setText(genId());
        //txtItemCode.setEditable(false);
        addBtn.setDisable(true);
    }

    public void addEquipmentsOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+"AddEquipmentToStockForm"+".fxml"));
        //targetLocation.getChildren().clear();
        addItemsToStockContext.getChildren().add(parent);
    }

    public void addItemsToStockCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addToStockBtnOnAction(ActionEvent actionEvent) {
        if(isSupplimentExists(txtItemCode.getText())){
            SupplimentCrudController.updateSupplimentFromOrder(
                    txtSellingPrice.getText(),txtQty.getText(),
                    txtItemCode.getText()
            );
        }
        else{
            SupplimentCrudController.insertSuppliment(
                    txtItemCode.getText(),txtDescription.getText(),txtSupplimentType.getText(),
                    txtSellingPrice.getText(),txtQty.getText()
            );
            txtItemCode.setText(genId());
        }

        MyOrdersCrudController.addMyOrderDetail(
                String.valueOf(cmbxOrderList.getSelectionModel().getSelectedItem()),
                txtItemCode.getText(),"SUPPLIMENT",txtBuyingPrice.getText(),
                txtQty.getText(),txtDescription.getText()
        );
    }

    public void setDataToOrderCmbx(){
        ObservableList<String> ob = FXCollections.observableArrayList(
                MyOrdersCrudController.getMyOrdersIds()
        );
        cmbxOrderList.setItems(ob);
    }

    public boolean isSupplimentExists(String id){
        ArrayList<String> arrayList = SupplimentCrudController.getAllSupplimentsIds();
        for(String s : arrayList){
            if(s.equals(id)){
                return true;
            }
        }
        return false;
    }

    public void validate(KeyEvent keyEvent) {
        addSupplimentAutoFill(txtItemCode.getText());
        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(SUP-)[0-9]{3,6}$");
        Pattern nameP = Pattern.compile("[A-Za-z0-9 ,'/+()-]{1,}");
        Pattern buyP = Pattern.compile("^[1-9][0-9.]{2,}");
        Pattern sellP = Pattern.compile("^[1-9][0-9.]{2,}");
        Pattern typeP = Pattern.compile("[A-Za-z0-9 ()+,'/-]{1,}");
        Pattern qtyP = Pattern.compile("[0-9]{1,}");

        map.put(txtItemCode,idP);
        map.put(txtDescription,nameP);
        map.put(txtBuyingPrice,buyP);
        map.put(txtSellingPrice,sellP);
        map.put(txtSupplimentType,typeP);
        map.put(txtQty,qtyP);

        boolean b = Validator.validate(map,addBtn);
        if(b==false){
            addBtn.setDisable(true);
        }else{
            addBtn.setDisable(false);
        }
    }

    public String genId(){
        return IdsGenerator.genarateId("SUP-",SupplimentCrudController.getSupplimentLastId());
        //return IdsGenerator.genarate(Mods.SUPPLIMENT,SupplimentCrudController.getSupplimentLastId());
    }

    public void addSupplimentAutoFill(String id){
        ArrayList<SupplimentTM> arrayList = SupplimentCrudController.supplimentAutoFill("%"+id+"%");
        if(arrayList.size()>0){
            txtDescription.setText(arrayList.get(0).getDescription());
            txtSupplimentType.setText(arrayList.get(0).getType());
        }
    }
}

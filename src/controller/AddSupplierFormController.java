package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.IdsGenerator;
import util.NavigateUI;
import util.Validator;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddSupplierFormController implements Initializable {
    public JFXButton addSupplierUpdateBtn;
    public JFXTextField supplierName;
    public JFXTextField supplierEmail;
    public JFXTextField suppliertele;
    public JFXTextField supplierAddress;
    public JFXButton addSupplierAddBtn;
    public JFXTextField supplierId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierId.setText(genId());
        supplierId.setEditable(false);
        addSupplierAddBtn.setDisable(true);
    }

    public void addSupplierCanselBtnOnAction(ActionEvent actionEvent) {
        NavigateUI.navigator.closeStage(actionEvent);
    }

    public void addSupplierOnAction(ActionEvent actionEvent) {
        if(addSupplierAddBtn.getText().equals("ADD")){
            SupplierCrudController.insertMember(supplierId.getText(),supplierName.getText(),
                    supplierEmail.getText(),suppliertele.getText(),supplierAddress.getText());
            supplierId.setText(genId());
        }else{
            SupplierCrudController.updateSupplier(
                    supplierId.getText(),supplierName.getText(),
                    supplierEmail.getText(),suppliertele.getText(),supplierAddress.getText(),
                    supplierId.getText()
            );
        }
    }

    public void addSupplierUpdateBtnOnAction(ActionEvent actionEvent) {
        if(addSupplierAddBtn.getText().equals("ADD")){
            addSupplierAddBtn.setText("UPDATE");
            addSupplierUpdateBtn.setText("ADD");
        }else{
            addSupplierAddBtn.setText("ADD");
            addSupplierUpdateBtn.setText("UPDATE");
        }
    }

    public void setValuesForTextFields(String... params){
        JFXTextField[] textFields = {supplierId,supplierName,supplierEmail,suppliertele,supplierAddress};
        for(int i = 0; i<textFields.length; i++){
            textFields[i].setText(params[i]);
        }
    }

    public void validate(KeyEvent keyEvent) {

        LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
        Pattern idP = Pattern.compile("^(SPL-)[0-9]{3,6}$");
        Pattern nameP = Pattern.compile("[A-Za-z .0-9]{1,50}$");
        Pattern addressP = Pattern.compile("[A-Za-z 0-9,/.]{3,100}$");
        Pattern teleP = Pattern.compile("[0-9]{10}$");
        Pattern emailP = Pattern.compile("[A-Za-z0-9]{1,}@(gmail|yahoo).com");

        map.put(supplierId,idP);
        map.put(supplierName,nameP);
        map.put(supplierAddress,addressP);
        map.put(supplierEmail,emailP);
        map.put(suppliertele,teleP);

        boolean b = Validator.validate(map,addSupplierAddBtn);
        if(b==false){
            addSupplierAddBtn.setDisable(true);
        }else{
            addSupplierAddBtn.setDisable(false);
        }
    }

    public String genId(){
        return IdsGenerator.genarateId("SPL-",
                SupplierCrudController.getSupplierIds());
        //return IdsGenerator.genarate(Mods.SUPPLIER,SupplierCrudController.getSupplierLastId());
    }
}

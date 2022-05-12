package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.NavigateUI;
import view.tm.MyOrderProductTM;
import view.tm.MyOrdersTM;
import view.tm.SupplierTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageSuppliersFormController implements Initializable {

    public TableView supplierTbl;
    public TableColumn colSupplierId;
    public TableColumn colSupplierCname;
    public TableColumn colSupplierEmail;
    public TableColumn colSupplierTele;
    public TableColumn colSupplierAddress;

    public String temSupplierpId;
    public String tempSupplierName;
    public String tempSupplierEmail;
    public String tempSupplierTele;
    public String tempSupplierAddress;
    public TableView myOrdercsTbl;
    public TableColumn myOrdersTblColOrderId;
    public TableColumn myOrdersTblColDate;
    public TableColumn myOrdersTblColTime;
    public TableColumn myOrdersTblSupplierId;
    public TableColumn myOrdersTblTotalCost;
    public TableView myOrderItemTbl;
    public TableColumn myOrderItemTblColItmCode;
    public TableColumn myOrderItemTblColItmType;
    public TableColumn myOrderItemTblColDescription;
    public TableColumn myOrderItemTblColBuyingPrice;
    public TableColumn myOrderItemTblQty;
    public TextField txtMyOrdersItemSearchBar;

    public TextField txtSearchMyOrders;
    public TextField txtSearchSuppliers;

    private String tOrderId;
    private String tDate;
    private String tTime;
    private double tTotalCost;
    private String tSupplierId;

    Parent parent;
    Scene scene;
    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierCname.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSupplierTele.setCellValueFactory(new PropertyValueFactory<>("tele"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        myOrdersTblColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        myOrdersTblColTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        myOrdersTblSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        myOrdersTblColOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        myOrdersTblTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        myOrderItemTblColBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        myOrderItemTblColItmCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        myOrderItemTblColItmType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        myOrderItemTblQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        myOrderItemTblColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        setSuppliersToTbl();
        setDataToMyOrdersTbl();

        supplierTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setData((SupplierTM) newValue);
                });

        myOrdercsTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setDataMyOrders((MyOrdersTM) newValue);
                });
    }

    private void setDataMyOrders(MyOrdersTM newValue) {
        tSupplierId = newValue.getSupplierId();
        tOrderId = newValue.getOrderId();
        tDate = newValue.getDate();
        tTime = newValue.getTime();
        tTotalCost = newValue.getTotalCost();
    }

    private void setData(SupplierTM newValue) {
        temSupplierpId = newValue.getId();
        tempSupplierName = newValue.getCompanyName();
        tempSupplierEmail = newValue.getEmail();
        tempSupplierAddress = newValue.getAddress();
        tempSupplierTele = newValue.getTele();
    }

    public void addSupplierOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddSupplierForm");
    }

    public void addItemsOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddItemsToStockForm");
    }

    public void addOrderOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddOrderForm");
    }

    public void setSuppliersToTbl(){
        ObservableList<SupplierTM> observableList = FXCollections.observableArrayList(
                SupplierCrudController.getAllSuppliers()
        );
        supplierTbl.setItems(observableList);
    }

    public void refreshCtmOnAction(ActionEvent actionEvent) {
        setSuppliersToTbl();
    }

    public void updateCtmOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddSupplierForm.fxml"));
        parent = fxmlLoader.load();

        AddSupplierFormController addSupplierFormController = fxmlLoader.getController();
        addSupplierFormController.setValuesForTextFields(temSupplierpId,tempSupplierName,tempSupplierEmail,
                tempSupplierTele,tempSupplierAddress);

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);
    }

    public void setDataToMyOrdersTbl(){
        ObservableList<MyOrdersTM> ob = FXCollections.observableArrayList(
                MyOrdersCrudController.getAllMyOrders()
        );
        myOrdercsTbl.setItems(ob);
    }

    public void myOrdersSearchItemBtnOnAction(ActionEvent actionEvent) {
        setDataToSupplyinHistoryTbl();
    }

    public void setDataToSupplyinHistoryTbl(){
        ObservableList<MyOrderProductTM> ob = FXCollections.observableArrayList(
                MyOrdersCrudController.getAllItemsFromMyOrdersByOrderId(txtMyOrdersItemSearchBar.getText())
        );

        myOrderItemTbl.setItems(ob);
    }

    public void orderSearchBarOnAction(ActionEvent actionEvent) {
        searchMyOrders();
    }

    public void searchSupplierBarOnAction(ActionEvent actionEvent) {
        searchSupplier();
    }

    public void supplyingDetailsSearchBarOnAction(ActionEvent actionEvent) {
        setDataToSupplyinHistoryTbl();
    }

    public void deleteCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            // delete
            SupplierCrudController.deletetSupplier(temSupplierpId);
        }
    }

    public void txtSearchSuppliersKeyReleased(KeyEvent keyEvent) {
        searchSupplier();
    }

    public void txtSearchMyOrdersOnAction(ActionEvent actionEvent) {
        searchMyOrders();
    }

    public void txtMyOrdersItemSearchBarKeyReleades(KeyEvent keyEvent) {
        ObservableList<MyOrderProductTM> ob = FXCollections.observableArrayList(
                MyOrdersCrudController.searchOrderDetailsById("%"+txtMyOrdersItemSearchBar.getText()+"%")
        );

        myOrderItemTbl.setItems(ob);
    }

    public void txtSearchMyOrdersKeyReleased(KeyEvent keyEvent) {
        txtSearchMyOrdersOnAction(new ActionEvent());
    }

    public void searchSupplier(){
        if(txtSearchSuppliers.getText().startsWith("SPL-")){
            ObservableList<SupplierTM> ob = FXCollections.observableArrayList(
                    SupplierCrudController.searchSupplierById("%"+txtSearchSuppliers.getText()+"%")
            );

            supplierTbl.setItems(ob);
        }else{
            ObservableList<SupplierTM> ob = FXCollections.observableArrayList(
                    SupplierCrudController.searchSupplierCompanyName("%"+txtSearchSuppliers.getText()+"%")
            );

            supplierTbl.setItems(ob);
        }
    }

    public void searchMyOrders(){
        if(txtSearchMyOrders.getText().startsWith("MOR")){
            ObservableList<MyOrdersTM> ob = FXCollections.observableArrayList(
                    MyOrdersCrudController.searchMyOrdersById("%"+txtSearchMyOrders.getText()+"%")
            );

            myOrdercsTbl.setItems(ob);
        }else{
            ObservableList<MyOrdersTM> ob = FXCollections.observableArrayList(
                    MyOrdersCrudController.searchMyOrdersByDate("%"+txtSearchMyOrders.getText()+"%")
            );

            myOrdercsTbl.setItems(ob);
        }
    }

    public void updateMyOrderCtmOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddOrderForm.fxml"));
        parent = fxmlLoader.load();

        AddOrderFormController addOrderFormController = fxmlLoader.getController();
        addOrderFormController.setValuesForTextFields(
                tOrderId,getSupplierIdIndex(),tTotalCost,tDate,tTime,
                "ADD","UPDATE","UPDATE TRAINER"
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);
    }

    public void refreshMyOrderTblCtmOnAction(ActionEvent actionEvent) {
        setDataToMyOrdersTbl();
    }

    public void refreshOrderDetailsCtmOnAction(ActionEvent actionEvent) {
        if(!txtMyOrdersItemSearchBar.getText().isEmpty()){
            myOrdersSearchItemBtnOnAction(new ActionEvent());
        }
    }

    public int getSupplierIdIndex(){
        int index = -1;
        ArrayList<String> ar = SupplierCrudController.getSupplierIds();
        for(int i = 0; i<ar.size(); i++){
            if(ar.get(i).equals(tSupplierId)){
                index = i;
            }
        }
        return index;
    }
}

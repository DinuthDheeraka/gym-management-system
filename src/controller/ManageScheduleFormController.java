package controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import util.NavigateUI;
import util.ShowNotification;
import view.tm.ScheduleTM;
import view.tm.Schedule_Exercise_TM;
import view.tm.TrainerTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageScheduleFormController implements Initializable {

    public TableView scheduleTbl;
    public TableColumn colScheduleMealPlan;
    public TableColumn colScheduleIDPW;
    public TableColumn colScheduleIEndDate;
    public TableColumn colScheduleStartDate;
    public TableColumn colTrainerId;
    public TableColumn colScheduleId;
    public TableView<Schedule_Exercise_TM> scheduleExerciseTbl;
    public TableColumn scheduleExerciseTblColExerciseId;
    public TableColumn scheduleExerciseTblColExerciseName;
    public TableColumn scheduleExerciseTblColExerciseAffectArea;
    public TableColumn scheduleExerciseTblColExerciseINstructions;
    public TextField txtSearchScheduleId;
    public JFXButton exerciseDetailsSearchBtn;

    private String tscheduleId;
    private String ttrainerId;
    private String tstartDate;
    private String tendDate;
    private int tdaysPerWeek;
    private String tmealPlanId;

    private String texerciseId;
    private String tname;
    private String taffectedArea;
    private String tinstructions;

    public TextField txtSearchSchedule;

    Parent parent;
    Stage stage;
    Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colScheduleId.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
        colScheduleStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colTrainerId.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
        colScheduleIEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colScheduleMealPlan.setCellValueFactory(new PropertyValueFactory<>("mealPlanId"));
        colScheduleIDPW.setCellValueFactory(new PropertyValueFactory<>("daysPerWeek"));

        scheduleExerciseTblColExerciseAffectArea.setCellValueFactory(new PropertyValueFactory<>("affectedArea"));
        scheduleExerciseTblColExerciseINstructions.setCellValueFactory(new PropertyValueFactory<>("instructions"));
        scheduleExerciseTblColExerciseId.setCellValueFactory(new PropertyValueFactory<>("exerciseId"));
        scheduleExerciseTblColExerciseName.setCellValueFactory(new PropertyValueFactory<>("name"));

        setDataToScheduletable();

        scheduleTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setScheduleData((ScheduleTM) newValue);
                });

        scheduleExerciseTbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null)setScheduleExerciseData((Schedule_Exercise_TM) newValue);
                });
    }

    private void setScheduleExerciseData(Schedule_Exercise_TM newValue) {
        texerciseId = newValue.getExerciseId();
        tname = newValue.getName();
        taffectedArea = newValue.getAffectedArea();
        tinstructions = newValue.getInstructions();
    }

    private void setScheduleData(ScheduleTM newValue) {
        tscheduleId = newValue.getScheduleId();
        tendDate = newValue.getEndDate();
        tstartDate = newValue.getStartDate();
        tdaysPerWeek = newValue.getDaysPerWeek();
        ttrainerId = newValue.getTrainerId();
        tmealPlanId = newValue.getMealPlanId();
    }

    public void addScheduleOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddScheduleForm");
    }
    public void addExerciseOnAction(ActionEvent actionEvent) throws IOException {
        NavigateUI.navigator.setNewStage("AddExerciseForScheduleForm");
    }

    public void setDataToScheduletable(){
        ObservableList<ScheduleTM> observableList = FXCollections.observableArrayList(
                ScheduleCrudController.getAllSchedules()
        );
        scheduleTbl.setItems(observableList);
    }

    public void setDataToScheduleExerciseTbl(String scheduleId){
        ObservableList<Schedule_Exercise_TM> observableList = FXCollections.observableArrayList(
                ScheduleCrudController.getScheduleExercises(scheduleId)
        );

        scheduleExerciseTbl.setItems(observableList);

    }

    public void exerciseDetailsSearchBtnOnAction(ActionEvent actionEvent) {
        if(exerciseDetailsSearchBtn.getText().equals("SEARCH")){
            exerciseDetailsSearchBtn.setText("BACK");
            setDataToScheduleExerciseTbl(txtSearchScheduleId.getText());
        }else{
            scheduleExerciseTbl.getItems().clear();
            exerciseDetailsSearchBtn.setText("SEARCH");
        }

    }

    public void scheduleTableRefreshCtmOnAction(ActionEvent actionEvent) {
        setDataToScheduletable();
    }

    public void scheduleTableDeleteCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            if(!isUsingSchedule(tscheduleId)){
                ScheduleCrudController.deleteSchedule(tscheduleId);
            }else{
                ShowNotification.show(new Image("asserts/x-button_ccexpress.png"),
                        "Schedule "+tscheduleId+" in use!");
            }
        }
        setDataToScheduletable();
    }

    public void scheduleExerciseTblRefreshCtmOnAction(ActionEvent actionEvent) {
        setDataToScheduleExerciseTbl(txtSearchScheduleId.getText());
    }

    public void scheduleExerciseTblDeleteCtmOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            ScheduleCrudController.deleteExerciseFromSchedule(texerciseId,taffectedArea,tinstructions);
        }
    }

    public void scheduleTableUpdateCtmOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddScheduleForm.fxml"));
        parent = fxmlLoader.load();

        AddScheduleFormController addScheduleFormController = fxmlLoader.getController();
        addScheduleFormController.setValuesForTextFields(
                tscheduleId,tdaysPerWeek,getTrainerIndex(ttrainerId),getMealPlanIndex(tmealPlanId),
                tstartDate,tendDate
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);
    }

    public int getTrainerIndex(String id){
        int index = 0;
        ArrayList<String> arrayList = TrainerCrudController.getTrainersIds();
        for(int i = 0; i<arrayList.size(); i++){
            if(arrayList.get(i).equals(id)){
                index = i;
            }
        }
        return index;
    }

    public int getMealPlanIndex(String id){
        int index = 0;
        ArrayList<String> arrayList = MealPlansCrudController.getMealPlansIds();
        for(int i = 0; i<arrayList.size(); i++){
            if(arrayList.get(i).equals(id)){
                index = i;
            }
        }
        return index;
    }

    public void viewScheduleCtmOnAction(ActionEvent actionEvent) {
        ArrayList<String> ar = new ArrayList();
        ar.add(tscheduleId);
        ar.add(ttrainerId);
        ar.add(tstartDate);
        ar.add(tendDate);
        ar.add(String.valueOf(tdaysPerWeek));
        ar.add(tmealPlanId);
        ViewJasperReportCounterTask counterTask = new ViewJasperReportCounterTask("/view/reports/MemberSchedule.jrxml",ar);
        counterTask.valueProperty().addListener(new ChangeListener<JasperPrint>() {
            @Override
            public void changed(ObservableValue<? extends JasperPrint> observable, JasperPrint oldValue, JasperPrint newValue) {
                JasperViewer.viewReport(newValue,false);
            }
        });

        Thread thread = new Thread(counterTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void scheduleSearchBtnOnAction(ActionEvent actionEvent) {
        if(txtSearchSchedule.getText().startsWith("SCH-")){
            ObservableList<ScheduleTM> ob = FXCollections.observableArrayList(
                    ScheduleCrudController.searchScheduleById("%"+txtSearchSchedule.getText()+"%")
            );
            scheduleTbl.setItems(ob);
        }else if(txtSearchSchedule.getText().startsWith("TRA-")){
            ObservableList<ScheduleTM> ob = FXCollections.observableArrayList(
                    ScheduleCrudController.searchScheduleByTrainerId("%"+txtSearchSchedule.getText()+"%")
            );
            scheduleTbl.setItems(ob);
        }
    }

    public void searchScheduleBarOnKeyRelease(KeyEvent keyEvent) {
        scheduleSearchBtnOnAction(new ActionEvent());
    }

    public void searchExerciseDetailsKeyReleased(KeyEvent keyEvent) {
//        ObservableList<Schedule_Exercise_TM> observableList = FXCollections.observableArrayList(
//                ScheduleCrudController.getScheduleExercisesLike(
//                        "%"+txtSearchScheduleId.getText()+"%"
//        ));
//        scheduleExerciseTbl.setItems(observableList);
    }

    public boolean isUsingSchedule(String id){
        boolean isUsing = false;
        ArrayList<String> arrayList = ScheduleCrudController.getSchedulesIds();
        for (String s : arrayList){
            if(s.equals(id)){
                isUsing = true;
            }
        }
        return isUsing;
    }

    public void txtSearchScheduleIdOnAction(ActionEvent actionEvent) {
        setDataToScheduleExerciseTbl(txtSearchScheduleId.getText());
    }

    public void scheduleExerciseTblUpdateCtmOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddExerciseForScheduleForm.fxml"));
        parent = fxmlLoader.load();

        AddExerciseForScheduleFormController controller = fxmlLoader.getController();
        controller.setValuesForTextFields(
                getScheduleIndex(txtSearchScheduleId.getText()),getExericseIndex(texerciseId),
                getAfAreaIndex(taffectedArea),
                tinstructions,txtSearchScheduleId.getText(),texerciseId,taffectedArea,
                tinstructions
        );

        stage = new Stage();
        scene = new Scene(parent);
        stage.setScene(scene);
        NavigateUI.navigator.transparentUi(stage,scene);
    }

    public int getScheduleIndex(String id){
        ArrayList<String> arrayList = ScheduleCrudController.getSchedulesIds();
        for(int i = 0; i<arrayList.size(); i++){
            if(arrayList.get(i).equals(id)){
                return i;
            }
        }
        return -1;
    }

    public int getExericseIndex(String id){
        ArrayList<String> arrayList = ExerciseCrudController.getExercisesIds();
        for(int i = 0; i<arrayList.size(); i++){
            if(arrayList.get(i).equals(id)){
                return i;
            }
        }
        return -1;
    }

    public int getAfAreaIndex(String id){
        String[] ars = {"ABS","Chest","Shoulders","Legs"};
        for(int i = 0; i<ars.length; i++){
            if(ars[i].equals(id)){
                return i;
            }
        }
        return -1;
    }
}

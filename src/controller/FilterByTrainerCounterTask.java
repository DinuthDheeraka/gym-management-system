package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import view.tm.MemberTM;

public class FilterByTrainerCounterTask extends Task<ObservableList<MemberTM>> {

    String id;

    FilterByTrainerCounterTask(String id){
        this.id = id;
    }
    @Override
    protected ObservableList<MemberTM> call() throws Exception {
        ObservableList<MemberTM> ob = FXCollections.observableArrayList(
                MemberCrudController.getAllMembersTrainerLike("%"+id+"%")
        );
        updateValue(ob);
        return ob;
    }
}

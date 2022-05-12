package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import view.tm.MemberTM;

import java.util.ArrayList;

public class FilterMemberByNameSearchCounterTask extends Task<ObservableList<MemberTM>> {
    String name;

    FilterMemberByNameSearchCounterTask(String name){
        this.name = name;
    }
    @Override
    protected ObservableList<MemberTM> call() throws Exception {
        ObservableList<MemberTM> ob = FXCollections.observableArrayList(
                MemberCrudController.getAllMembersLike("%"+name+"%")
        );
        updateValue(ob);
        return ob;
    }
}

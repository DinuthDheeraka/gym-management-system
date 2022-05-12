package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import view.tm.MemberTM;

public class FilterMemberBySchCounterTask extends Task<ObservableList<MemberTM>> {

    String id;

    FilterMemberBySchCounterTask(String id){
        this.id = id;
    }
    @Override
    protected ObservableList<MemberTM> call() throws Exception {
        ObservableList<MemberTM> ob = FXCollections.observableArrayList(
                MemberCrudController.getAllMembersSchLike("%"+id+"%")
        );
        updateValue(ob);
        return ob;
    }
}

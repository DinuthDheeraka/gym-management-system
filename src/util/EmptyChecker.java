package util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Color;

public class EmptyChecker {

    public static boolean isEmpty(JFXTextField... params){
        boolean isEmpty = true;
        for(JFXTextField field : params){
            if(field.getText().isEmpty()){
                isEmpty&=false;
                field.setUnFocusColor(Color.RED);
            }else{
                //isEmpty&=true;
            }
        }
        return isEmpty;
    }
}

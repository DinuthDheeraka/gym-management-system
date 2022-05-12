package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validator {

    public static boolean validate(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        boolean isValid = true;
        for (TextField f : map.keySet()) {
            if (!f.getText().isEmpty()) {
                Pattern patternValue = map.get(f);
                if (!patternValue.matcher(f.getText()).matches()) {
                    f.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                    btn.setDisable(true);
                    isValid&=false;
                }else{
                    f.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
                    btn.setDisable(false);
                    isValid&=true;
                }
            }else{
                isValid&=false;
            }
        }
        return isValid;
    }
}

package controller;

import javafx.scene.layout.AnchorPane;

import java.util.LinkedHashMap;

public class Test {
    private static AnchorPane anchorPane;

    Test(AnchorPane anchorPane){
        this.anchorPane = anchorPane;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public static void main(String[] args) {

        LinkedHashMap<String,Integer> d = new LinkedHashMap<>();
        d.put("S",1);
        System.out.println(d.get("S"));
    }
}

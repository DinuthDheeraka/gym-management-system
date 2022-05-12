package util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ShowNotification {
    public static void show(Image image,String text){
        Notifications notifications = Notifications.create();
        notifications.title("GYM Management System");
        notifications.text(text);
        notifications.hideAfter(Duration.seconds(4));
        notifications.position(Pos.BOTTOM_RIGHT);
        notifications.graphic(new ImageView(image));
        notifications.darkStyle();
        notifications.show();
    }
}

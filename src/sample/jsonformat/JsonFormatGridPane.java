package sample.jsonformat;

import javafx.event.ActionEvent;
import sample.MyGridPane;
import sample.layoutParas.ViewSaxHandler;

/**
 * json 格式化 通用布局
 */
public class JsonFormatGridPane extends MyGridPane {

    public JsonFormatGridPane() {
        btn.setText("格式化数据");
        btn.setOnAction((ActionEvent e) -> {
            if (!notification.getText().trim().isEmpty()) {
                String text = ViewSaxHandler.start(notification.getText().trim());
                notification_out.setText(text);
            }
        });

        btn.setOnAction((ActionEvent e) -> {
            System.out.println(notification.getText());
            if (!notification.getText().trim().isEmpty()) {
                notification_out.setText(new JSONUtils(notification.getText().trim()).start());
            }
        });
    }

}
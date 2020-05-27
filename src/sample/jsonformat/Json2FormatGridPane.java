package sample.jsonformat;

import javafx.event.ActionEvent;
import sample.MyGridPane;
import sample.layoutParas.ViewSaxHandler;

/**
 * json 去除空格换行工具
 */
public class Json2FormatGridPane extends MyGridPane {

    public Json2FormatGridPane() {
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
                notification_out.setText(new JSONUtilsReplace(notification.getText().trim()).start());
            }
        });
    }

}
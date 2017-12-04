package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class MyGridPane extends GridPane {

        protected final Button btn;
        protected final TextArea notification_out;
        protected final TextArea notification;

        public MyGridPane() {
            this.setVgap(4);
            this.setHgap(10);
            this.setPadding(new Insets(5, 5, 5, 5));

            notification = new TextArea();
            notification.setText("Label");
            notification.clear();

            btn = new Button("开始格式化数据: ");

            notification_out = new TextArea();
            notification_out.setText("Label");
            notification_out.clear();

            this.add(new Label("请输入数据源: "), 0, 0);
            this.add(new Label("输出格式化数据: "), 0, 2);
            this.add(btn, 0, 1);
            this.add(notification, 1, 0);
            this.add(notification_out, 1, 2);
            btn.setOnAction((ActionEvent e) -> {
                System.out.println(notification.getText());
                if (!notification.getText().trim().isEmpty()) {
                    String text = TextController.format(notification.getText().trim());
                    notification_out.setText(text);
                }
            });
        }

    }
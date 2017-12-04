package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.jsonformat.JsonFormatGridPane;
import sample.layoutParas.ViewSaxHandler;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group(), 650, 450);
        Group root = (Group) scene.getRoot();
        primaryStage.setTitle("json格式化工具");
        //设置窗口的图标.
        primaryStage.getIcons().add(new Image("1.jpg"));
        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();
        String[] arrTitle = {"JSON(定制)格式化", "一键生成代码", "获取布局文件ID", "通用json格式化工具"};
        for (int i = 0; i < arrTitle.length; i++) {
            String navigation_bar_name = arrTitle[i];
            Tab tab = new Tab();
            tab.setText(navigation_bar_name);
            tab.closableProperty().set(false);
//            tab.selectedProperty().addListener(n);
            GridPane scrollPane = null;
            switch (i) {
                case 0:
                    scrollPane = new MyGridPane();
                    break;
                case 1:
                    scrollPane = new MyGridPane2();
                    break;
                case 2:
                    scrollPane = new MyGridPane3();
                    break;
                case 3:
                    scrollPane = new JsonFormatGridPane();
                    break;
                default:
                    break;
            }
            tab.setContent(scrollPane);
            tabPane.getTabs().add(tab);
        }

        borderPane.setCenter(tabPane);
        borderPane.getStyleClass().add("root");
        root.getChildren().add(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class MyGridPane2 extends GridPane {

        protected final Button btn;

        public MyGridPane2() {
            this.setVgap(4);
            this.setHgap(10);
            this.setPadding(new Insets(5, 5, 5, 5));

            TextArea notification = new TextArea();
            notification.setText("Label");
            notification.setMaxHeight(4);
            notification.clear();
            Label label_loading = new Label("  正在处理中... ");
            btn = new Button("开始生成文件");

            this.add(new Label("请输入生成文件名称: "), 0, 0);
            this.add(label_loading, 0, 2);
            this.add(btn, 0, 1);
            this.add(notification, 1, 0);
            btn.setOnAction((ActionEvent e) -> {
                System.out.println(notification.getText());
                if (!notification.getText().trim().isEmpty()) {
                    new Modify(notification.getText().trim(), label_loading).start();
                }
            });
        }

    }

    private class MyGridPane3 extends MyGridPane {
        public MyGridPane3() {
            super();
            btn.setText("获取布局Id列表");
            btn.setOnAction((ActionEvent e) -> {
                if (!notification.getText().trim().isEmpty()) {
                    String text = ViewSaxHandler.start(notification.getText().trim());
                    notification_out.setText(text);
                }
            });
        }
    }




}

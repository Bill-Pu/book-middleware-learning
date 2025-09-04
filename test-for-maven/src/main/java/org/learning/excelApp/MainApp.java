package org.learning.excelApp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.learning.excelApp.Config;
import org.learning.excelApp.ConfigTab;

import java.io.File;

public class MainApp extends Application {

    private TextField templateField;
    private TextField folderField;
    private TextField outputField;
    private TextArea logArea;

    // 全局配置对象
    private static Config config;

    @Override
    public void start(Stage stage) {
        // 1. 启动时加载 config.json
        config = Config.load();

        // 2. 任务执行界面
        VBox mainLayout = buildTaskUI(stage);

        // 3. 配置管理界面
        ConfigTab configTab = new ConfigTab(config);

        // Tab 切换
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("任务执行", mainLayout));
        tabPane.getTabs().add(configTab.createTab());

        Scene scene = new Scene(tabPane, 800, 500);
        stage.setTitle("POK Excel Updater");
        stage.setScene(scene);
        stage.show();
    }

    private VBox buildTaskUI(Stage stage) {
        // 模版 Excel
        Label templateLabel = new Label("模版 Excel:");
        templateField = new TextField();
        templateField.setPrefWidth(300);
        Button templateBtn = new Button("选择");
        templateBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("选择模版 Excel 文件");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel 文件 (*.xlsx)", "*.xlsx"));
            File file = chooser.showOpenDialog(stage);
            if (file != null) templateField.setText(file.getAbsolutePath());
        });
        HBox templateBox = new HBox(10, templateLabel, templateField, templateBtn);

        // 图片文件夹
        Label folderLabel = new Label("图片文件夹:");
        folderField = new TextField();
        folderField.setPrefWidth(300);
        Button folderBtn = new Button("选择");
        folderBtn.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("选择图片文件夹");
            File dir = chooser.showDialog(stage);
            if (dir != null) folderField.setText(dir.getAbsolutePath());
        });
        HBox folderBox = new HBox(10, folderLabel, folderField, folderBtn);

        // 输出 Excel
        Label outputLabel = new Label("导出 Excel:");
        outputField = new TextField();
        outputField.setPrefWidth(300);
        Button outputBtn = new Button("保存为");
        outputBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("保存 Excel 文件");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel 文件 (*.xlsx)", "*.xlsx"));
            File file = chooser.showSaveDialog(stage);
            if (file != null) outputField.setText(file.getAbsolutePath());
        });
        HBox outputBox = new HBox(10, outputLabel, outputField, outputBtn);

        // 执行按钮
        Button runBtn = new Button("开始执行");
        runBtn.setOnAction(e -> runUpdater());

        // 日志输出
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(200);

        VBox layout = new VBox(15, templateBox, folderBox, outputBox, runBtn, logArea);
        layout.setPadding(new Insets(20));
        return layout;
    }

    private void runUpdater() {
        String template = templateField.getText();
        String folder = folderField.getText();
        String output = outputField.getText();

        if (template.isEmpty() || folder.isEmpty() || output.isEmpty()) {
            log("⚠️ 请先选择所有路径！");
            return;
        }

        new Thread(() -> {
            try {
                log("▶️ 开始执行...");
                POKExcelUpdater.appendToTemplateExcel(template, folder, output, config.eColumnMap, config.fColumnMap);
                log("✅ 执行完成，文件已保存到: " + output);
            } catch (Exception ex) {
                log("❌ 出错: " + ex.getMessage());
                ex.printStackTrace();
            }
        }).start();
    }

    private void log(String message) {
        javafx.application.Platform.runLater(() -> logArea.appendText(message + "\n"));
    }

    public static void main(String[] args) {
        launch();
    }
}

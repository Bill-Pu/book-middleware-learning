package org.learning.excelApp;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import lombok.var;

public class ConfigTab {

    private TableView<ConfigItem> eTable;
    private TableView<ConfigItem> fTable;
    private Config config;

    public ConfigTab(Config config) {
        this.config = config;
    }

    public Tab createTab() {
        Tab tab = new Tab("配置管理");
        tab.setClosable(false);

        // E列配置
        eTable = createTable("分类", "价格");
        for (var entry : config.eColumnMap.entrySet()) {
            eTable.getItems().add(new ConfigItem(entry.getKey(), entry.getValue().toString()));
        }

        // F列配置
        fTable = createTable("分类", "数量");
        for (var entry : config.fColumnMap.entrySet()) {
            fTable.getItems().add(new ConfigItem(entry.getKey(), entry.getValue().toString()));
        }

        Button saveBtn = new Button("保存配置");
        saveBtn.setOnAction(e -> {
            config.eColumnMap.clear();
            config.fColumnMap.clear();

            for (ConfigItem item : eTable.getItems()) {
                try { config.eColumnMap.put(item.key.get(), Double.parseDouble(item.value.get())); } catch (Exception ignored) {}
            }
            for (ConfigItem item : fTable.getItems()) {
                try { config.fColumnMap.put(item.key.get(), Integer.parseInt(item.value.get())); } catch (Exception ignored) {}
            }

            config.save();
            new Alert(Alert.AlertType.INFORMATION, "配置已保存到 config.json").showAndWait();
        });

        VBox layout = new VBox(10, new Label("E列价格配置"), eTable,
                new Label("F列数量配置"), fTable,
                saveBtn);
        layout.setPadding(new javafx.geometry.Insets(10));
        tab.setContent(layout);

        return tab;
    }

    private TableView<ConfigItem> createTable(String col1, String col2) {
        TableView<ConfigItem> table = new TableView<>();
        table.setEditable(true);

        TableColumn<ConfigItem, String> keyCol = new TableColumn<>(col1);
        keyCol.setCellValueFactory(c -> c.getValue().key);
        keyCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<ConfigItem, String> valueCol = new TableColumn<>(col2);
        valueCol.setCellValueFactory(c -> c.getValue().value);
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn());

        table.getColumns().addAll(keyCol, valueCol);
        table.setItems(FXCollections.observableArrayList());

        return table;
    }

    public static class ConfigItem {
        StringProperty key = new SimpleStringProperty();
        StringProperty value = new SimpleStringProperty();
        ConfigItem(String k, String v) { key.set(k); value.set(v); }
    }
}

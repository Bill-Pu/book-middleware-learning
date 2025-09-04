package org.learning.excelApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public Map<String, Integer> fColumnMap = new HashMap<>();
    public Map<String, Double> eColumnMap = new HashMap<>();

    private static final String CONFIG_FILE = "config.json";

    // 加载配置（如果不存在则返回带默认值的配置）
    public static Config load() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            try {
                return mapper.readValue(file, Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 默认配置
        Config config = new Config();

        // ===== fColumnMap 默认值 =====
        config.fColumnMap.put("14x21大色纸", 3);
        config.fColumnMap.put("15元立牌", 3);
        config.fColumnMap.put("20元立牌", 3);
        config.fColumnMap.put("25元立牌", 3);
        config.fColumnMap.put("28元立牌", 3);
        config.fColumnMap.put("32元立牌", 3);
        config.fColumnMap.put("36元立牌", 3);
        config.fColumnMap.put("39元立牌", 3);
        config.fColumnMap.put("45元立牌", 3);
        config.fColumnMap.put("58双闪吧唧", 5);
        config.fColumnMap.put("75双闪吧唧", 5);
        config.fColumnMap.put("方吧唧", 5);
        config.fColumnMap.put("方卡", 6);
        config.fColumnMap.put("覆膜吧唧", 5);
        config.fColumnMap.put("捏捏吧唧", 10);
        config.fColumnMap.put("挂件", 5);
        config.fColumnMap.put("像素人挂件", 5);
        config.fColumnMap.put("镭射票", 10);
        config.fColumnMap.put("明信片", 10);
        config.fColumnMap.put("拍立得", 10);
        config.fColumnMap.put("色纸", 3);
        config.fColumnMap.put("椭圆吧唧", 5);
        config.fColumnMap.put("瓶盖吧唧", 4);
        config.fColumnMap.put("像素人立牌", 3);
        config.fColumnMap.put("亚克力棒棒糖", 10);
        config.fColumnMap.put("亚克力双闪色纸", 5);
        config.fColumnMap.put("亚克力透卡", 3);
        config.fColumnMap.put("陶瓷杯垫", 10);
        config.fColumnMap.put("亚克力圆盘", 6);
        config.fColumnMap.put("24元亚克力砖", 3);
        config.fColumnMap.put("32元亚克力砖", 1);

        // ===== eColumnMap 默认值 =====
        config.eColumnMap.put("14x21大色纸", 20.0);
        config.eColumnMap.put("陶瓷杯垫", 15.0);
        config.eColumnMap.put("15元立牌", 15.0);
        config.eColumnMap.put("20元立牌", 20.0);
        config.eColumnMap.put("25元立牌", 25.0);
        config.eColumnMap.put("28元立牌", 28.0);
        config.eColumnMap.put("32元立牌", 32.0);
        config.eColumnMap.put("36元立牌", 36.0);
        config.eColumnMap.put("39元立牌", 39.0);
        config.eColumnMap.put("45元立牌", 45.0);
        config.eColumnMap.put("58双闪吧唧", 15.0);
        config.eColumnMap.put("75双闪吧唧", 16.0);
        config.eColumnMap.put("方吧唧", 15.0);
        config.eColumnMap.put("方卡", 10.0);
        config.eColumnMap.put("覆膜吧唧", 10.0);
        config.eColumnMap.put("挂件", 15.0);
        config.eColumnMap.put("像素人挂件", 15.0);
        config.eColumnMap.put("镭射票", 0.0);
        config.eColumnMap.put("明信片", 4.0);
        config.eColumnMap.put("拍立得", 6.0);
        config.eColumnMap.put("色纸", 18.0);
        config.eColumnMap.put("椭圆吧唧", 18.0);
        config.eColumnMap.put("瓶盖吧唧", 6.9);
        config.eColumnMap.put("像素人立牌", 18.0);
        config.eColumnMap.put("亚克力棒棒糖", 18.0);
        config.eColumnMap.put("亚克力双闪色纸", 20.0);
        config.eColumnMap.put("亚克力透卡", 15.0);
        config.eColumnMap.put("亚克力圆盘", 0.0);
        config.eColumnMap.put("24元亚克力砖", 24.0);
        config.eColumnMap.put("32元亚克力砖", 32.0);

        return config;
    }

    // 保存配置
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_FILE), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

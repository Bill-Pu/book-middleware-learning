package org.learning;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReplaceCharacterNames {

    public static void main(String[] args) {
        String basePath = "/Users/Apple/Pictures/铺货模版文件夹4/";
        String recordPath = basePath + "铺货记录.xlsx";
        String aliasPath = basePath + "同人谷角色代称汇总.xlsx";
        String outputPath = basePath + "铺货记录_替换别称.xlsx";

        try {
            Map<String, String> aliasMap = loadAliasMapping(aliasPath);
            System.out.println("已加载角色映射数: " + aliasMap.size());

            replaceCharacterNames(recordPath, aliasMap, outputPath);

            System.out.println("替换完成，新文件已保存为：" + outputPath);

        } catch (IOException e) {
            System.err.println("处理失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Map<String, String> loadAliasMapping(String aliasExcelPath) throws IOException {
        Map<String, String> aliasMap = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(aliasExcelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell aliasCell = row.getCell(1); // B列
                Cell standardNameCell = row.getCell(2); // C列

                if (aliasCell != null && standardNameCell != null) {
                    String rawAlias = aliasCell.toString().trim();
                    String aliasKey = extractBeforeBracket(rawAlias);
                    String standardName = standardNameCell.toString().trim();

                    if (!aliasKey.isEmpty()) {
                        aliasMap.put(aliasKey, standardName);
                    }
                }
            }
        }
        return aliasMap;
    }

    private static String extractBeforeBracket(String input) {
        // 提取中文括号或英文括号前的内容，例如：愚人金（备注） 或 愚人金(备注)
        return input.replaceAll("[（(].*$", "").trim();
    }

    private static void replaceCharacterNames(String recordExcelPath, Map<String, String> aliasMap, String outputPath) throws IOException {
        FileInputStream fis = new FileInputStream(recordExcelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        fis.close();

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Cell characterCell = row.getCell(2); // C列
            if (characterCell == null) continue;

            String originalName = characterCell.toString().trim();
            String mappedName = aliasMap.get(originalName);

            if (mappedName != null) {
                characterCell.setCellValue(mappedName);
                System.out.println("替换: " + originalName + " → " + mappedName);
            }
        }

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            workbook.write(fos);
        }

        workbook.close();
    }
}

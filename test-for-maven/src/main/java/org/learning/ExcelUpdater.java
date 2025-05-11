package org.learning;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;

import java.io.*;
import java.util.*;

/*处理铺货文件专用代码*/
public class ExcelUpdater {
    private static final Map<String, Integer> fColumnMap = new HashMap<>();
    private static final Map<String, Integer> eColumnMap = new HashMap<>();
    public static void main(String[] args) {
        //数量
        fColumnMap.put("58吧唧", 5);
        fColumnMap.put("75吧唧", 5);
        fColumnMap.put("挂件", 5);
        fColumnMap.put("明信片", 5);
        fColumnMap.put("拍立得", 10);
        fColumnMap.put("大立牌", 3);
        fColumnMap.put("覆膜吧唧", 5);
        //价格
        eColumnMap.put("58吧唧", 15);
        eColumnMap.put("75吧唧", 18);
        eColumnMap.put("挂件", 15);
        eColumnMap.put("明信片", 4);
        eColumnMap.put("拍立得", 6);
        eColumnMap.put("覆膜吧唧", 12);

        String sourceExcelPath = "/Users/Apple/Pictures/石家庄-廊坊-南宁一次铺货/模版.xlsx"; // 原始Excel路径
        String newExcelPath = "/Users/Apple/Pictures/石家庄-廊坊-南宁一次铺货/新铺货记录.xlsx"; // 新的Excel路径
        String folderPath = "/Users/Apple/Pictures/石家庄-廊坊-南宁一次铺货"; // 目录路径

        createNewExcelWithHeaderAndUpdate(sourceExcelPath, folderPath, newExcelPath);
    }

    public static void createNewExcelWithHeaderAndUpdate(String sourceExcelPath, String folderPath, String newExcelPath) {
        try (FileInputStream fis = new FileInputStream(sourceExcelPath);
             Workbook sourceWorkbook = new XSSFWorkbook(fis);
             Workbook newWorkbook = new XSSFWorkbook()) {

            Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
            Sheet newSheet = newWorkbook.createSheet("Sheet1");

            // 复制表头
            Row sourceHeaderRow = sourceSheet.getRow(0);
            if (sourceHeaderRow == null) {
                System.out.println("源Excel表头为空！");
                return;
            }
            Row newHeaderRow = newSheet.createRow(0);
            for (int i = 0; i < sourceHeaderRow.getLastCellNum(); i++) {
                Cell sourceCell = sourceHeaderRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell newCell = newHeaderRow.createCell(i);
                newCell.setCellValue(sourceCell.toString());
            }

            // 获取所有子文件夹中的 JPG 图片
            List<File> imageFiles = getImageFilesInSubfolders(new File(folderPath));
            if (imageFiles.isEmpty()) {
                System.out.println("在目录中没有找到 JPG 图片文件。");
                return;
            }

            int rowIndex = 1; // 从第二行开始插入数据
            Drawing<?> drawing = newSheet.createDrawingPatriarch();

            // 设置图片的目标宽度和高度（以英寸为单位）
            double targetHeightInches = 1.22;  // 高度（英寸）
            double targetWidthInches = 1.0;    // 宽度（英寸）

            // 将英寸转换为点（1英寸 = 72点）
            double targetHeightInPoints = targetHeightInches * 72;
            double targetWidthInPoints = targetWidthInches * 72;

            // 设置 G 列宽度为13字符
            newSheet.setColumnWidth(6, 13 * 256); // G列宽度设为13字符，字符宽度乘以256

            // 设置所有行的行高为88磅
            setRowHeightForAllRows(newSheet, 88); // 88磅

            for (File file : imageFiles) {
                Row row = newSheet.createRow(rowIndex);

                // 使用 Java 8 初始化 E 列和 F 列的数值映射


                // 获取子文件夹名称
                String parentFolderName = file.getParentFile().getName();

                // 填充 D 列（子文件夹名称）
                row.createCell(3).setCellValue(parentFolderName);

                // 填充 E 列（数量）
                int eValue = eColumnMap.containsKey(parentFolderName) ? eColumnMap.get(parentFolderName) : 0;
                row.createCell(4).setCellValue(eValue);

                // 填充 F 列（数量）
                int fValue = fColumnMap.containsKey(parentFolderName) ? fColumnMap.get(parentFolderName) : 0;
                row.createCell(5).setCellValue(fValue);


                // 读取图片
                InputStream is = new FileInputStream(file);
                byte[] bytes = IOUtils.toByteArray(is);
                int pictureIdx = newWorkbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
                is.close();

                // 创建图片锚点
                XSSFClientAnchor anchor = new XSSFClientAnchor();
                anchor.setCol1(6); // G列（索引6）
                anchor.setRow1(rowIndex);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

                // 插入图片
                XSSFPicture picture = ((XSSFDrawing) drawing).createPicture(anchor, pictureIdx);

                // 获取图片的原始宽高（单位：像素）
                int originalWidth = picture.getImageDimension().width;
                int originalHeight = picture.getImageDimension().height;

                // 计算宽度和高度的缩放比例
                double widthScale = targetWidthInPoints / originalWidth;
                double heightScale = targetHeightInPoints / originalHeight;

                // 根据缩放比例调整图片
                picture.resize(widthScale, heightScale);

                rowIndex++;
            }

            // 保存新 Excel 文件
            try (FileOutputStream fos = new FileOutputStream(newExcelPath)) {
                newWorkbook.write(fos);
                System.out.println("新 Excel 文件创建成功，表头已复制，并插入了图片、文件夹名称和固定值！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 设置所有行的行高为指定的高度（磅数）
    private static void setRowHeightForAllRows(Sheet sheet, int targetHeightInPoints) {
        // 获取最后一行的行号
        int lastRowNum = sheet.getLastRowNum();

        // 设置每一行的行高
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            row.setHeightInPoints(targetHeightInPoints); // 设置行高
        }
    }

    // 获取指定目录及其子目录中的所有 JPG 图片文件
    private static List<File> getImageFilesInSubfolders(File folder) {
        List<File> imageFiles = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归进入子文件夹
                    imageFiles.addAll(getImageFilesInSubfolders(file));
                } else if (file.getName().toLowerCase().endsWith(".jpg")) {
                    // 仅添加 JPG 文件
                    imageFiles.add(file);
                }
            }
        }
        return imageFiles;
    }
}

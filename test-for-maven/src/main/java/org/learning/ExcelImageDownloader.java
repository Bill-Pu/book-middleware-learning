package org.learning;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExcelImageDownloader {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/Apple/Pictures/铺货/快手小店商品导出.xlsx";  // 输入 Excel 文件路径
        String outputFilePath = "/Users/Apple/Pictures/铺货/快手小店商品导出_filled.xlsx";  // 输出 Excel 文件路径
        int imageColumnIndex = 8; // I列（索引从0开始）

        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // 设置 I 列宽度为 1.3 英寸（1.3 * 256）
        sheet.setColumnWidth(8, (int) (1.3 * 256));  // 1.3英寸 * 256

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) { // 跳过第一行
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;

            Cell cell = row.getCell(imageColumnIndex);
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String imageUrl = cell.getStringCellValue();
                if (!imageUrl.isEmpty()) {
                    System.out.println("正在下载图片: " + imageUrl);
                    byte[] imageBytes = downloadImage(imageUrl);
                    if (imageBytes != null) {
                        System.out.println("图片下载成功，插入到 Excel...");
                        insertImageToCell(workbook, sheet, rowIndex, imageColumnIndex, imageBytes, cell);
                    } else {
                        System.err.println("图片下载失败或数据为空: " + imageUrl);
                    }
                }
            }
            row.setHeightInPoints(93.6f); // 设置足够的行高 (1.3 英寸)
        }

        fis.close();
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
        System.out.println("图片下载并插入完成，保存到: " + outputFilePath);
    }

    /**
     * 从 URL 下载图片为字节数组
     */
    private static byte[] downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(5000); // 5 秒连接超时
            connection.setReadTimeout(5000); // 5 秒读取超时

            try (InputStream in = connection.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = out.toByteArray();

                if (imageBytes.length == 0) {
                    System.err.println("警告: 图片数据为空！URL: " + imageUrl);
                    return null;
                }

                return imageBytes;
            }
        } catch (IOException e) {
            System.err.println("图片下载失败: " + imageUrl + " 错误: " + e.getMessage());
            return null;
        }
    }

    /**
     * 插入图片到 Excel 指定单元格，并删除原来的 URL
     */
    private static void insertImageToCell(Workbook workbook, Sheet sheet, int rowIndex, int colIndex, byte[] imageBytes, Cell cell) {
        int pictureIndex = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
        if (pictureIndex == -1) {
            System.err.println("图片添加失败，可能是格式问题");
            return;
        }

        // 设置目标图片大小为 1.3 英寸（宽高一致）
        double targetWidthInches = 1.3;  // 宽度为 1.3 英寸
        double targetHeightInches = 1.3; // 高度为 1.3 英寸

        // 将英寸转换为点（1英寸 = 72点）
        double targetWidthInPoints = targetWidthInches * 72;
        double targetHeightInPoints = targetHeightInches * 72;

        // 创建绘图对象
        CreationHelper helper = workbook.getCreationHelper();
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        // 创建图片锚点
        XSSFClientAnchor anchor = new XSSFClientAnchor();
        anchor.setCol1(colIndex); // I列（索引 8）
        anchor.setRow1(rowIndex);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

        // 插入图片
        XSSFPicture picture = ((XSSFDrawing) drawing).createPicture(anchor, pictureIndex);

        // 获取图片的原始尺寸
        int originalWidth = picture.getImageDimension().width;
        int originalHeight = picture.getImageDimension().height;

        // 如果原始宽高都不为 0，进行调整
        if (originalWidth > 0 && originalHeight > 0) {
            // 按比例调整图片的尺寸
            double ratio = Math.min(targetWidthInPoints / originalWidth, targetHeightInPoints / originalHeight);
            picture.resize(ratio);
        } else {
            System.err.println("图片原始尺寸无效，无法调整大小！");
        }

        // 设置列宽和行高
        sheet.setColumnWidth(colIndex, (int) (14 * 256));  // 1.3英寸转换为列宽
        Row row = sheet.getRow(rowIndex);
        row.setHeightInPoints((float) targetHeightInPoints);  // 设置行高

        // 删除 URL，只保留图片
        cell.setCellValue("");

        System.out.println("图片插入成功，URL 删除: 行 " + (rowIndex + 1) + ", 列 " + (colIndex + 1));
    }
}

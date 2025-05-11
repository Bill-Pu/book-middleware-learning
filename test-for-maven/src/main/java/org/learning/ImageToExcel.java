package org.learning;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
/*处理一级目录图片导出到excel*/
public class ImageToExcel {
    public static void main(String[] args) {
        String newExcelPath = "/Users/Apple/Pictures/退坑.xlsx"; // 生成的Excel路径
        String folderPath = "/Users/Apple/Pictures/退坑合集"; // PNG 图片目录

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Images");

            // 设置列宽（14个字符）
            sheet.setColumnWidth(0, 14 * 256);

            File folder = new File(folderPath);
            File[] imageFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

            if (imageFiles == null || imageFiles.length == 0) {
                System.out.println("No PNG images found in the folder.");
                return;
            }

            int rowIndex = 0;
            for (File imageFile : imageFiles) {
                Row row = sheet.createRow(rowIndex);
                row.setHeightInPoints(88); // 设置行高 88 磅

                // 压缩图片并获取字节数据
                byte[] compressedImage = compressImage(imageFile, 0.1); // 10% 的原始大小

                if (compressedImage != null) {
                    int pictureIdx = workbook.addPicture(compressedImage, Workbook.PICTURE_TYPE_PNG);
                    Drawing<?> drawing = sheet.createDrawingPatriarch();
                    CreationHelper helper = workbook.getCreationHelper();
                    ClientAnchor anchor = helper.createClientAnchor();
                    anchor.setCol1(0); // 第一列
                    anchor.setRow1(rowIndex); // 当前行
                    anchor.setRow2(rowIndex + 1); // 让图片占据整行
                    Picture picture = drawing.createPicture(anchor, pictureIdx);
                    picture.resize(); // 让图片适应单元格
                }
                rowIndex++;
            }

            // 保存 Excel 文件
            try (FileOutputStream fileOut = new FileOutputStream(newExcelPath)) {
                workbook.write(fileOut);
            }
            System.out.println("Excel file created successfully: " + newExcelPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片到指定的比例
     * @param imageFile 图片文件
     * @param scale 压缩比例（如 0.1 表示压缩到 10%）
     * @return 压缩后的字节数组
     */
    private static byte[] compressImage(File imageFile, double scale) {
        try {
            BufferedImage originalImage = ImageIO.read(imageFile);
            int newWidth = (int) (originalImage.getWidth() * Math.sqrt(scale));
            int newHeight = (int) (originalImage.getHeight() * Math.sqrt(scale));

            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

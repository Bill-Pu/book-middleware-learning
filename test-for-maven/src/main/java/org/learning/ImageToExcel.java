package org.learning;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageToExcel {
    public static void main(String[] args) {
        String newExcelPath = "/Users/Apple/Pictures/拿货图片.xlsx"; // 输出 Excel 文件路径
        String folderPath = "/Users/Apple/Pictures/拿货图片";        // 图片文件夹路径

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Images");
            sheet.setColumnWidth(0, 14 * 256); // 设置列宽

            File folder = new File(folderPath);
            // 支持常见图片格式
            File[] imageFiles = folder.listFiles((dir, name) -> {
                String lower = name.toLowerCase();
                return lower.endsWith(".png") || lower.endsWith(".jpg") ||
                        lower.endsWith(".jpeg") || lower.endsWith(".bmp") ||
                        lower.endsWith(".gif");
            });

            if (imageFiles == null || imageFiles.length == 0) {
                System.out.println("No supported image files found in the folder.");
                return;
            }

            int rowIndex = 0;
            for (File imageFile : imageFiles) {
                Row row = sheet.createRow(rowIndex);
                row.setHeightInPoints(88); // 设置行高

                // 压缩图像
                byte[] compressedImage = compressImage(imageFile, 1); // 10% 大小

                if (compressedImage != null) {
                    int pictureIdx = workbook.addPicture(compressedImage, Workbook.PICTURE_TYPE_PNG);
                    Drawing<?> drawing = sheet.createDrawingPatriarch();
                    CreationHelper helper = workbook.getCreationHelper();
                    ClientAnchor anchor = helper.createClientAnchor();
                    anchor.setCol1(0);
                    anchor.setRow1(rowIndex);
                    anchor.setRow2(rowIndex + 1);
                    Picture picture = drawing.createPicture(anchor, pictureIdx);
                    picture.resize();
                }

                rowIndex++;
            }

            try (FileOutputStream fileOut = new FileOutputStream(newExcelPath)) {
                workbook.write(fileOut);
            }

            System.out.println("Excel file created successfully: " + newExcelPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片
     */
    private static byte[] compressImage(File imageFile, double scale) {
        try {
            BufferedImage originalImage = ImageIO.read(imageFile);
            if (originalImage == null) {
                System.out.println("Unsupported image format or failed to read: " + imageFile.getName());
                return null;
            }

            int newWidth = (int) (originalImage.getWidth() * Math.sqrt(scale));
            int newHeight = (int) (originalImage.getHeight() * Math.sqrt(scale));

            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baos); // 始终转换成 PNG 格式存储
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

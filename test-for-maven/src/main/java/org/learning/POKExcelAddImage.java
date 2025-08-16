package org.learning;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class POKExcelAddImage {

    private static final Map<String, Integer> fColumnMap = new HashMap<>();
    private static final Map<String, Double> eColumnMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("程序开始执行...");

        fColumnMap.put("14x21大色纸", 5);
        fColumnMap.put("15元立牌", 5);
        fColumnMap.put("20元立牌", 5);
        fColumnMap.put("25元立牌", 5);
        fColumnMap.put("28元立牌", 5);
        fColumnMap.put("32元立牌", 5);
        fColumnMap.put("36元立牌", 5);
        fColumnMap.put("45元立牌", 5);
        fColumnMap.put("58双闪吧唧", 10);
        fColumnMap.put("75双闪吧唧", 10);
        fColumnMap.put("方吧唧", 10);
        fColumnMap.put("方卡", 6);
        fColumnMap.put("覆膜吧唧", 10);
        fColumnMap.put("捏捏吧唧", 10);
        fColumnMap.put("挂件", 10);
        fColumnMap.put("像素人挂件", 10);
        fColumnMap.put("镭射票", 10);
        fColumnMap.put("明信片", 10);
        fColumnMap.put("拍立得", 10);
        fColumnMap.put("色纸", 5);
        fColumnMap.put("椭圆吧唧", 10);
        fColumnMap.put("瓶盖吧唧", 4);
        fColumnMap.put("像素人立牌", 5);
        fColumnMap.put("亚克力棒棒糖", 10);
        fColumnMap.put("亚克力双闪色纸", 5);
        fColumnMap.put("亚克力透卡", 3);
        fColumnMap.put("陶瓷杯垫", 10);
        fColumnMap.put("亚克力圆盘", 6);
        fColumnMap.put("24元亚克力砖", 3);
        fColumnMap.put("32元亚克力砖", 1);

        eColumnMap.put("14x21大色纸", 20.0);
        eColumnMap.put("陶瓷杯垫", 15.0);
        eColumnMap.put("15元立牌", 15.0);
        eColumnMap.put("20元立牌", 20.0);
        eColumnMap.put("25元立牌", 25.0);
        eColumnMap.put("28元立牌", 28.0);
        eColumnMap.put("32元立牌", 32.0);
        eColumnMap.put("36元立牌", 36.0);
        eColumnMap.put("45元立牌", 45.0);
        eColumnMap.put("58双闪吧唧", 15.0);
        eColumnMap.put("75双闪吧唧", 16.0);
        eColumnMap.put("方吧唧", 15.0);
        eColumnMap.put("方卡", 10.0);
        eColumnMap.put("覆膜吧唧", 10.0);
        eColumnMap.put("挂件", 15.0);
        eColumnMap.put("像素人挂件", 15.0);
        eColumnMap.put("镭射票", 0.0);
        eColumnMap.put("明信片", 4.0);
        eColumnMap.put("拍立得", 6.0);
        eColumnMap.put("色纸", 18.0);
        eColumnMap.put("椭圆吧唧", 18.0);
        eColumnMap.put("瓶盖吧唧", 6.9);
        eColumnMap.put("像素人立牌", 18.0);
        eColumnMap.put("亚克力棒棒糖", 18.0);
        eColumnMap.put("亚克力双闪色纸", 20.0);
        eColumnMap.put("亚克力透卡", 15.0);
        eColumnMap.put("亚克力圆盘", 0.0);
        eColumnMap.put("24元亚克力砖", 24.0);
        eColumnMap.put("32元亚克力砖", 32.0);

        String sourceExcelPath = "/Users/Apple/Pictures/pokNewYAOYAO/pekopeko寄售表模版.xlsx";
        String newExcelPath = "/Users/Apple/Pictures/pokNewYAOYAO/铺货记录.xlsx";
        String folderPath = "/Users/Apple/Pictures/pokNewYAOYAO";

        createNewExcelWithHeaderAndUpdate(sourceExcelPath, folderPath, newExcelPath);

        System.out.println("程序执行结束。");
    }

    public static void createNewExcelWithHeaderAndUpdate(String sourceExcelPath, String folderPath, String newExcelPath) {
        try (FileInputStream fis = new FileInputStream(sourceExcelPath);
             Workbook sourceWorkbook = new XSSFWorkbook(fis);
             Workbook newWorkbook = new XSSFWorkbook()) {

            System.out.println("读取源 Excel 文件: " + sourceExcelPath);

            Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
            Sheet newSheet = newWorkbook.createSheet("Sheet1");

            Row sourceHeaderRow = sourceSheet.getRow(0);
            if (sourceHeaderRow == null) {
                System.out.println("源 Excel 表头为空！");
                return;
            }

            Row newHeaderRow = newSheet.createRow(0);
            for (int i = 0; i < sourceHeaderRow.getLastCellNum(); i++) {
                Cell sourceCell = sourceHeaderRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell newCell = newHeaderRow.createCell(i);
                newCell.setCellValue(sourceCell.toString());
            }

            List<File> imageFiles = getImageFilesInSubfolders(folderPath);
            if (imageFiles.isEmpty()) {
                System.out.println("在目录中没有找到支持格式的图片文件。");
                return;
            }

            System.out.println("找到图片数量: " + imageFiles.size());

            // 从第8行开始写
            int rowIndex = 7;
            Drawing<?> drawing = newSheet.createDrawingPatriarch();

            double targetHeightInPoints = 1.22 * 72;
            double targetWidthInPoints = 1.0 * 72;

            // 设置 A 列宽度
            newSheet.setColumnWidth(0, 13 * 256);

            for (File file : imageFiles) {
                Row row = newSheet.createRow(rowIndex);
                row.setHeightInPoints(88);

                String parentFolderName = file.getParentFile().getName().trim().replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9]", "");

                System.out.println("处理图片: " + file.getAbsolutePath());
                System.out.println("识别为分类: " + parentFolderName);

                Double eValue = eColumnMap.getOrDefault(parentFolderName, 0.0);
                int fValue = fColumnMap.getOrDefault(parentFolderName, 0);

                // D E F 列还是原来的 3、4、5
                row.createCell(3).setCellValue(parentFolderName);
                row.createCell(4).setCellValue(eValue);
                row.createCell(5).setCellValue(fValue);

                BufferedImage correctedImage = correctOrientation(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                String lowerName = file.getName().toLowerCase();
                if (lowerName.endsWith(".png")) {
                    ImageIO.write(correctedImage, "png", baos);
                } else {
                    ImageIO.write(correctedImage, "jpg", baos);
                }
                byte[] bytes = baos.toByteArray();

                int pictureType = getPictureType(file.getName());
                int pictureIdx = newWorkbook.addPicture(bytes, pictureType);

                XSSFClientAnchor anchor = new XSSFClientAnchor();
                anchor.setCol1(0); // 改到 A 列
                anchor.setRow1(rowIndex);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

                XSSFPicture picture = ((XSSFDrawing) drawing).createPicture(anchor, pictureIdx);

                double widthScale = targetWidthInPoints / correctedImage.getWidth();
                double heightScale = targetHeightInPoints / correctedImage.getHeight();

                picture.resize(widthScale, heightScale);

                System.out.println("插入图片成功，E列=" + eValue + "，F列=" + fValue);
                rowIndex++;
            }

            try (FileOutputStream fos = new FileOutputStream(newExcelPath)) {
                newWorkbook.write(fos);
                System.out.println("成功保存新 Excel 文件: " + newExcelPath);
            }

        } catch (IOException e) {
            System.err.println("发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int getPictureType(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".png")) return Workbook.PICTURE_TYPE_PNG;
        else return Workbook.PICTURE_TYPE_JPEG;
    }

    private static List<File> getImageFilesInSubfolders(String folderPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(file -> {
                        String name = file.getName().toLowerCase();
                        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("读取图片文件失败: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private static BufferedImage correctOrientation(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                return transformImage(image, orientation);
            }
        } catch (Exception e) {
            System.err.println("读取EXIF失败：" + e.getMessage());
        }
        return image;
    }

    private static BufferedImage transformImage(BufferedImage image, int orientation) {
        int width = image.getWidth();
        int height = image.getHeight();
        AffineTransform transform = new AffineTransform();

        switch (orientation) {
            case 6:
                transform.translate(height, 0);
                transform.rotate(Math.toRadians(90));
                break;
            case 3:
                transform.translate(width, height);
                transform.rotate(Math.toRadians(180));
                break;
            case 8:
                transform.translate(0, width);
                transform.rotate(Math.toRadians(270));
                break;
            default:
                return image;
        }

        BufferedImage newImage = new BufferedImage(
                orientation == 6 || orientation == 8 ? height : width,
                orientation == 6 || orientation == 8 ? width : height,
                image.getType()
        );

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, transform, null);
        g2d.dispose();
        return newImage;
    }
}

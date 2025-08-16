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

public class AddExcelPic {

    private static final Map<String, Integer> fColumnMap = new HashMap<>();
    private static final Map<String, Double> eColumnMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("程序开始执行...");

        // 分类对应数量
        fColumnMap.put("14x21大色纸", 3);
        fColumnMap.put("20元立牌", 3);
        fColumnMap.put("25元立牌", 3);
        fColumnMap.put("28元立牌", 3);
        fColumnMap.put("32元立牌", 3);
        fColumnMap.put("36元立牌", 3);
        fColumnMap.put("45元立牌", 3);
        fColumnMap.put("58双闪吧唧", 3);
        fColumnMap.put("75双闪吧唧", 3);
        fColumnMap.put("方吧唧", 3);
        fColumnMap.put("方卡", 3);
        fColumnMap.put("覆膜吧唧", 3);
        fColumnMap.put("挂件", 3);
        fColumnMap.put("小立牌", 3);
        fColumnMap.put("像素人挂件", 3);
        fColumnMap.put("镭射票", 3);
        fColumnMap.put("明信片", 20);
        fColumnMap.put("拍立得", 10);
        fColumnMap.put("色纸", 6);
        fColumnMap.put("椭圆吧唧", 2);
        fColumnMap.put("瓶盖吧唧", 4);
        fColumnMap.put("像素人立牌", 5);
        fColumnMap.put("亚克力棒棒糖", 6);
        fColumnMap.put("亚克力双闪色纸", 6);
        fColumnMap.put("亚克力透卡", 5);
        fColumnMap.put("陶瓷杯垫", 10);
        fColumnMap.put("亚克力圆盘", 6);

        // 分类对应单价
        eColumnMap.put("14x21大色纸", 20.0);
        eColumnMap.put("陶瓷杯垫", 15.0);
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
        eColumnMap.put("小立牌", 15.0);
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

        String excelPath = "/Users/Apple/Pictures/追加文件/模版.xlsx";
        String folderPath = "/Users/Apple/Pictures/追加文件";

        appendImagesToExistingExcel(excelPath, folderPath);

        System.out.println("程序执行结束。");
    }

    public static void appendImagesToExistingExcel(String excelPath, String folderPath) {
        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            System.out.println("读取 Excel 文件: " + excelPath);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            System.out.println("从第 " + (lastRowNum + 1) + " 行开始追加");

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            double targetHeightInPoints = 1.22 * 72;
            double targetWidthInPoints = 1.0 * 72;
            sheet.setColumnWidth(6, 13 * 256);

            List<File> imageFiles = getImageFilesInSubfolders(folderPath);
            if (imageFiles.isEmpty()) {
                System.out.println("在目录中没有找到支持格式的图片文件。");
                return;
            }

            int rowIndex = lastRowNum + 1;

            for (File file : imageFiles) {
                Row row = sheet.createRow(rowIndex);
                row.setHeightInPoints(88);

                String parentFolderName = file.getParentFile().getName().trim().replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9]", "");
                System.out.println("处理图片: " + file.getAbsolutePath());
                System.out.println("识别为分类: " + parentFolderName);

                Double eValue = eColumnMap.getOrDefault(parentFolderName, 0.0);
                int fValue = fColumnMap.getOrDefault(parentFolderName, 0);

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
                int pictureIdx = workbook.addPicture(bytes, pictureType);

                XSSFClientAnchor anchor = new XSSFClientAnchor();
                anchor.setCol1(6);
                anchor.setRow1(rowIndex);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

                XSSFPicture picture = ((XSSFDrawing) drawing).createPicture(anchor, pictureIdx);

                double widthScale = targetWidthInPoints / correctedImage.getWidth();
                double heightScale = targetHeightInPoints / correctedImage.getHeight();
                picture.resize(widthScale, heightScale);

                System.out.println("插入图片成功，E列=" + eValue + "，F列=" + fValue);
                rowIndex++;
            }

            try (FileOutputStream fos = new FileOutputStream(excelPath)) {
                workbook.write(fos);
                System.out.println("成功保存修改后的 Excel 文件: " + excelPath);
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
                (orientation == 6 || orientation == 8) ? height : width,
                (orientation == 6 || orientation == 8) ? width : height,
                image.getType()
        );

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, transform, null);
        g2d.dispose();
        return newImage;
    }
}

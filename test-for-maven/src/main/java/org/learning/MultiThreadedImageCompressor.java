package org.learning;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedImageCompressor {

    private static final long MAX_SIZE = 3 * 1024 * 1024; // 3MB
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    public static void main(String[] args) throws IOException {
        // 修改为你自己的输入和输出路径
        File inputFolder = new File("/Users/Apple/Pictures/糖果导出");
        File outputFolder = new File("/Users/Apple/Pictures/压缩输出");

        if (!inputFolder.exists()) {
            System.out.println("输入文件夹不存在！");
            return;
        }

        compressImagesInFolder(inputFolder, outputFolder);

        executor.shutdown();
    }

    public static void compressImagesInFolder(File inputFolder, File outputFolder) throws IOException {
        Files.walk(inputFolder.toPath())
                .filter(Files::isRegularFile)
                .filter(path -> isImageFile(path.toFile()))
                .forEach(path -> executor.submit(() -> {
                    try {
                        File inputFile = path.toFile();
                        Path relativePath = inputFolder.toPath().relativize(path);
                        File outputFile = new File(outputFolder, relativePath.toString());

                        // 确保输出目录存在
                        outputFile.getParentFile().mkdirs();

                        if (inputFile.length() <= MAX_SIZE) {
                            // 小于3MB，直接复制
                            Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("跳过压缩: " + inputFile.getName());
                        } else {
                            compressImageToTargetSize(inputFile, outputFile, MAX_SIZE);
                            System.out.println("压缩完成: " + inputFile.getName());
                        }
                    } catch (IOException e) {
                        System.err.println("处理文件失败: " + path + "，原因: " + e.getMessage());
                    }
                }));
    }

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
    }

    private static void compressImageToTargetSize(File inputFile, File outputFile, long targetSize) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        if (image == null) {
            System.out.println("无法读取图像: " + inputFile.getName());
            return;
        }

        String extension = getFileExtension(inputFile.getName());
        if (extension == null) {
            System.out.println("无法识别文件扩展名: " + inputFile.getName());
            return;
        }

        double quality = 1.0;
        File tempFile = File.createTempFile("compress_", "." + extension);

        while (quality > 0.1) {
            Thumbnails.of(inputFile)
                    .scale(1.0)
                    .outputQuality(quality)
                    .toFile(tempFile);

            if (tempFile.length() <= targetSize) {
                Files.move(tempFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return;
            }
            quality -= 0.05;
        }

        // 最终未压缩到目标大小，也保存最后版本
        Files.move(tempFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot == -1 || lastDot == fileName.length() - 1) {
            return null;
        }
        return fileName.substring(lastDot + 1).toLowerCase();
    }
}

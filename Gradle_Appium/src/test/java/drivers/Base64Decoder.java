package drivers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class Base64Decoder {
    public static void decodeBase64Image(String filePath, String outputImagePath) {
        try {
            // Читаем содержимое файла как строку
            String base64String = new String(Files.readAllBytes(new File(filePath).toPath()));
            // Декодируем строку Base64 в байты
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);
            // Сохраняем байты в файл изображения
            try (FileOutputStream fos = new FileOutputStream(outputImagePath)) {
                fos.write(decodedBytes);
            }

            System.out.println("Изображение успешно сохранено в " + outputImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\v.sultanov\\0_D_Disk\\Projects\\Appium\\Gradle_Appium\\src\\test\\resources\\base64Said.txt"; // Путь к файлу, содержащему Base64 код
        String outputImagePath = "C:\\Users\\v.sultanov\\0_D_Disk\\Projects\\Appium\\Gradle_Appium\\src\\test\\resources\\SaidBAse64Decoding.png"; // Путь для сохранения изображения

        decodeBase64Image(inputFilePath, outputImagePath);
    }
}


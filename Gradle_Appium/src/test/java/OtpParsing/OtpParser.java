package OtpParsing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.HashMap;
import java.util.Map;

public class OtpParser {

    // Метод для получения списка SMS через ADB
    public List<String> smsList() {
        StringBuilder output = new StringBuilder();

        try {
            // Команда для получения списка SMS через ADB
            String adbCommand = "adb shell content query --uri content://sms/inbox";

            // Запуск команды через ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd", "/c", adbCommand); // Для Windows замените "bash" на "cmd" и используйте "/c" вместо "-c"
            Process process = processBuilder.start();

            // Чтение результата выполнения команды
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Ожидание завершения процесса
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Список SMS получен успешно:");
            } else {
                System.err.println("Ошибка при выполнении команды ADB");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String a = output.toString();

        String[] rows = a.split("(?=Row:)");

        // Создаём список для хранения частей
        List<String> rowList = new ArrayList<>();
        for (String row : rows) {
            rowList.add(row.trim()); // Удаляем лишние пробелы
        }

        return rowList;
    }

    // Метод для парсинга строки в объект MessageDTO
    public SmsDTO parseMessage(String input) {
        // Регулярное выражение для извлечения ключ-значение
        Pattern pattern = Pattern.compile("(\\w+)=([^,]+|NULL)");
        Matcher matcher = pattern.matcher(input);

        Map<String, String> map = new HashMap<>();
        // Проходим по всем совпадениям и сохраняем в Map
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2).equals("NULL") ? null : matcher.group(2);
            map.put(key, value);
        }

        Pattern pattern2 = Pattern.compile("(body)=(.+, locked)");
        Matcher matcher2 = pattern2.matcher(input);

        Map<String, String> map2 = new HashMap<>();
        while (matcher2.find()) {
            String key = matcher2.group(1);
            String value = matcher2.group(2).equals("NULL") ? null : matcher2.group(2);
            map2.put(key, value.substring(0, value.length() - 8));
        }


        // Создание и заполнение объекта Message
        SmsDTO message = new SmsDTO();
        message.setRow(Integer.parseInt(map.getOrDefault("Row", "0")));
        message.set_id(Integer.parseInt(map.getOrDefault("_id", "0")));
        message.setThread_id(Integer.parseInt(map.getOrDefault("thread_id", "0")));
        message.setAddress(map.get("address"));
        message.setPerson(map.get("person"));
        message.setDate(Long.parseLong(map.getOrDefault("date", "0")));
        message.setDate_sent(Long.parseLong(map.getOrDefault("date_sent", "0")));
        message.setProtocol(Integer.parseInt(map.getOrDefault("protocol", "0")));
        message.setRead(Integer.parseInt(map.getOrDefault("read", "0")));
        message.setStatus(Integer.parseInt(map.getOrDefault("status", "0")));
        message.setType(Integer.parseInt(map.getOrDefault("type", "0")));
        message.setReply_path_present(Integer.parseInt(map.getOrDefault("reply_path_present", "0")));
        message.setSubject(map.get("subject"));
        message.setBody(map2.get("body"));
        message.setService_center(map.get("service_center"));
        message.setLocked(Integer.parseInt(map.getOrDefault("locked", "0")));
        message.setError_code(Integer.parseInt(map.getOrDefault("error_code", "0")));
        message.setSeen(Integer.parseInt(map.getOrDefault("seen", "0")));
        message.setTimed(Integer.parseInt(map.getOrDefault("timed", "0")));
        message.setDeleted(Integer.parseInt(map.getOrDefault("deleted", "0")));
        message.setSync_state(Integer.parseInt(map.getOrDefault("sync_state", "0")));
        message.setMarker(Integer.parseInt(map.getOrDefault("marker", "0")));
        message.setSource(map.get("source"));
        message.setBind_id(Integer.parseInt(map.getOrDefault("bind_id", "0")));
        message.setMx_status(Integer.parseInt(map.getOrDefault("mx_status", "0")));
        message.setMx_id(map.get("mx_id"));
        message.setOut_time(Integer.parseInt(map.getOrDefault("out_time", "0")));
        message.setAccount(map.get("account"));
        message.setSim_id(Integer.parseInt(map.getOrDefault("sim_id", "0")));
        message.setBlock_type(Integer.parseInt(map.getOrDefault("block_type", "0")));
        message.setAdvanced_seen(Integer.parseInt(map.getOrDefault("advanced_seen", "0")));
        message.setB2c_ttl(Integer.parseInt(map.getOrDefault("b2c_ttl", "0")));
        message.setB2c_numbers(map.get("b2c_numbers"));
        message.setFake_cell_type(Integer.parseInt(map.getOrDefault("fake_cell_type", "0")));
        message.setUrl_risky_type(Integer.parseInt(map.getOrDefault("url_risky_type", "0")));
        message.setCreator(map.get("creator"));
        message.setFavorite_date(Integer.parseInt(map.getOrDefault("favorite_date", "0")));
        message.setMx_id_v2(map.get("mx_id_v2"));
        message.setSub_id(Integer.parseInt(map.getOrDefault("sub_id", "-1")));

        return message;
    }


    //  Возвращает OTP из строки
    public String getOTP(String input, int otpLength) {

        // Проверяем на null или пустую строку
        if (input == null || input.trim().isEmpty()) {
            return "Строка пуста или null";
        }

        Pattern pattern = Pattern.compile("(?<!\\d)\\d{6}(?!\\d)");
        Matcher matcher = pattern.matcher(input);
        String otp = "";

        while (matcher.find()) {
            otp = matcher.group();
        }

        // Если OTP не найден, возвращаем сообщение
        if (otp.isEmpty()) {
            return "OTP не найден";
        }

        return otp;
    }

    // Возвращает список OTP из всех SMS от определенного отправителя
    public List<String> getOTPListBySender(String otpSenderName, List<SmsDTO> rowList) {

        List<String> allOtps = new ArrayList<>();

        // Проверяем, чтобы список сообщений не был пустым
        if (rowList == null || rowList.isEmpty()) {
            System.out.println("Список сообщений пуст или null.");
            return allOtps;
        }

        // Проверяем, чтобы имя отправителя было не null
        if (otpSenderName == null || otpSenderName.isEmpty()) {
            System.out.println("Имя отправителя пустое или null.");
            return allOtps;
        }

        // Проходим по каждому сообщению
        for (SmsDTO message : rowList) {

            if (message == null || message.getBody() == null || message.getAddress() == null) {
                continue; // Пропустить эту итерацию, перейти к следующему сообщению если сообщение или тело сообщения или адрес отправителя пустые
            }

            // Получаем текст сообщения
            String body = message.getBody();

            // Регулярное выражение для поиска шестизначных чисел
            Pattern pattern = Pattern.compile("(?<!\\d)\\d{6}(?!\\d)");
            Matcher matcher = pattern.matcher(body);

            // Ищем все подходящие совпадения
            while (matcher.find()) {
                // Проверяем, совпадает ли отправитель
                if (message.getAddress().equals(otpSenderName)) {
                    allOtps.add(matcher.group());
                }
            }
        }

        return allOtps;
    }

    public String getOTP2(String input, int otpLength, String customRegex) {

        // Проверяем на null или пустую строку
        if (input == null || input.trim().isEmpty()) {
            return "Строка пуста или null";
        }

        // Используем пользовательский regex или создаем стандартный
        String regex = (customRegex != null && !customRegex.isEmpty())
                ? customRegex
                : "(?<!\\d)\\d{" + otpLength + "}(?!\\d)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String otp = "";

        while (matcher.find()) {
            otp = matcher.group();
        }

        // Если OTP не найден, возвращаем сообщение
        if (otp.isEmpty()) {
            return "OTP не найден";
        }

        return otp;
    }


    public static void main(String[] args) {

        OtpParser messageParser = new OtpParser();

        List<String> sms = messageParser.smsList();

        List<SmsDTO> rowList = new ArrayList<>();
        for (String row : sms) {
            rowList.add(messageParser.parseMessage(row));
        }


        System.out.println("-------------------------------------------------");




    }
}


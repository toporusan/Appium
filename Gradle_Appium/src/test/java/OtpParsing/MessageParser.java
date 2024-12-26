package OtpParsing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.HashMap;
import java.util.Map;

public class MessageParser {

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

    public MessageDTO parseMessage(String input) {
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

        // Создание и заполнение объекта Message
        MessageDTO message = new MessageDTO();

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
        message.setBody(map.get("body"));
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

    public static void main(String[] args) {

        List <String> rowList = new MessageParser().smsList();
        String input = rowList.get(5);

        MessageDTO message = new MessageParser().parseMessage(input);
        System.out.println(message.getRow());
        System.out.println(message.get_id());
        System.out.println(message.getBody());
    }
}

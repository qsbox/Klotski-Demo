package rank;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class RecordBoard{

    public static List<Record> getRecords(int mission) throws IOException {
    String path = String.format("rank/%d.txt", mission);
    Path filePath = Paths.get(path);

    // 如果文件不存在，返回空列表
    if (!Files.exists(filePath)) {
        return new ArrayList<>();
    }

    // 读取文件所有行
    List<String> lines = Files.readAllLines(filePath);
    List<Record> records = new ArrayList<>();

    // 解析每行记录
    for (String line : lines) {
        if (!line.trim().isEmpty()) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                try {
                    String username = parts[0];
                    int timeUsed = Integer.parseInt(parts[1]);
                    records.add(new Record(username, timeUsed));
                } catch (NumberFormatException e) {
                    // 忽略格式错误的行
                    System.err.println("忽略格式错误的记录: " + line);
                }
            }
        }
    }


    // 按用时升序排序（用时越少排名越高）
    Collections.sort(records, Comparator.comparingInt(Record::getTimeUsed));

    return records;
    }
    public static void addRecord(int mission, String username, int timeUsed) throws IOException {
        Record record = new Record(username, timeUsed);

        Path dirPath = Paths.get("rank");
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        Path filePath = Paths.get("rank", mission + ".txt");

        Files.writeString(filePath,
                record + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

}
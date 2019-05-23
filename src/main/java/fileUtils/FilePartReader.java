package fileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FilePartReader {

    private String filePath;
    private Integer fromLine;
    private Integer toLine;

    public FilePartReader() {
        filePath = "/dummy/path/to/the/file.txt";
        fromLine = 1;
        toLine = 1;
    }

    public String getFilePath() {
        return filePath;
    }

    public Integer getFromLine() {
        return fromLine;
    }

    public Integer getToLine() {
        return toLine;
    }

    public void setup(String filePath, Integer fromLine, Integer toLine) throws IllegalArgumentException {

        if (toLine < fromLine || fromLine < 1) throw new IllegalArgumentException();

        this.filePath = filePath;
        this.fromLine = fromLine;
        this.toLine = toLine;
    }

    public String read() throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(s -> builder.append(s).append("\n"));
        }

        return builder.toString().replaceAll("\\s+$","");
    }

    public String readLines() throws IOException {
        List<String> lines = Arrays.asList(read().split("\n"));
        StringBuilder builder = new StringBuilder();

        IntStream
                .range(0, lines.size())
                .filter(i -> i + 1 >= fromLine && i + 1 <= toLine)
                .mapToObj(lines::get)
                .forEach(line -> builder.append(line).append("\n"));
        return builder.toString().replaceAll("\\s+$","");
    }

}

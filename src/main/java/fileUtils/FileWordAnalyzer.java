package fileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileWordAnalyzer {
    private FilePartReader filePartReader;

    public FileWordAnalyzer(FilePartReader filePartReader) {
        this.filePartReader = filePartReader;
    }

    public List<String> getWordsOrderedAlphabetically() throws IOException {

        return getStreamFromReader()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getWordsContainingSubstring(String subString) throws IOException {
        List<String> result = new ArrayList<>();

        getStreamFromReader()
                .filter(word -> word.contains(subString))
                .forEach(result::add);
        return result;
    }

    public List<String> getStringsWhichPalindromes() throws IOException {
        List<String> result = new ArrayList<>();

        getStreamFromReader()
                .filter(this::isPalindrome)
                .forEach(result::add);
        return result;
    }

    private boolean isPalindrome(String word) {
        char[] letters = word.toCharArray();
        int size = letters.length;
        for (int i = 0; i < size; i++) {
            if (letters[i] != letters[size - 1 - i])
                return false;
        }
        return true;
    }

    private Stream<String> getStreamFromReader() throws IOException{
        return Arrays.stream(filePartReader.readLines().split("\\s+"))
                .map(word-> word.replaceAll("^\\W+$|^\\W|\\W$", ""))
                .filter(word -> !word.isEmpty());
    }
}

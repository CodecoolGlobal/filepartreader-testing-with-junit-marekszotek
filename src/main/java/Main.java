import fileUtils.FilePartReader;
import fileUtils.FileWordAnalyzer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "/home/marek/Dokumenty/Web/SIprojects/filepartreader-testing-with-junit-marekszotek/src/main/resources/file.txt";
        Integer from = 1;
        Integer to = 7;


        FilePartReader filePartReader = new FilePartReader();
        filePartReader.setup(filePath, from, to);
        FileWordAnalyzer fileWordAnalyzer = new FileWordAnalyzer(filePartReader);

        try {
            System.out.println(fileWordAnalyzer.getWordsOrderedAlphabetically());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(fileWordAnalyzer.getWordsContainingSubstring("ac"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(fileWordAnalyzer.getStringsWhichPalindromes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

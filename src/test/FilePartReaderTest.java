import fileUtils.FilePartReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class FilePartReaderTest {

    private FilePartReader filePartReader;
    private static String filePath;

    @BeforeAll
    static void initAll(){
        filePath = "/home/marek/Dokumenty/Web/SIprojects/filepartreader-testing-with-junit-marekszotek/src/test/resources/testfile.txt";
    }

    @BeforeEach
    void init(){
        filePartReader = new FilePartReader();
    }

    @Test
    void testConstructorAssignedValues(){
        assertAll("parameters",
                () -> assertNotNull(filePartReader.getFilePath()),
                () -> assertNotNull(filePartReader.getFromLine()),
                () -> assertNotNull(filePartReader.getToLine()));
    }

    @Test
    void testToLineSmallerThanFromLine(){
        Integer toLine = 1;
        Integer fromLine = 2;

        assertThrows(IllegalArgumentException.class,
                () -> filePartReader.setup(filePath, fromLine, toLine));
    }

    @Test
    void testFromLineLessThanOne(){
        Integer toLine = 5;
        Integer fromLine = 0;

        assertThrows(IllegalArgumentException.class,
                () -> filePartReader.setup(filePath, fromLine, toLine));
    }

    @Test
    void testReadFileContentFromFilePath() throws IOException {
        String expectedFileContent =
                " first line of content\n" +
                        "second\n" +
                        "\n" +
                        "fourth line (after the space)";

        filePartReader.setup(filePath, 1, 100);

        assertEquals(expectedFileContent, filePartReader.read());
    }

    @Test
    void testSourceFileNotExist(){
        String notExistentFilePath = "i/am/not/here.txt";

        filePartReader.setup(notExistentFilePath, 1, 100);

        assertThrows(IOException.class, () -> filePartReader.read());
    }

    @Test
    void testReadlinesActivatesReadMethod() throws IOException{
        FilePartReader spy = spy(filePartReader);

        spy.setup(filePath, 1, 100);
        spy.readLines();

        verify(spy).read();
    }

    @Test
    void testReadOnlyFirstLine() throws IOException {
        String expected = " first line of content";

        filePartReader.setup(filePath, 1, 1);

        assertEquals(expected, filePartReader.readLines());
    }

    @Test
    void testReadLinesBeyondFile() throws IOException {
        String expected = "";

        filePartReader.setup(filePath, 100, 200);

        assertEquals(expected, filePartReader.readLines());
    }

    @Test
    void testReadLinesInsideFile() throws IOException {
        String expected = "second\n" +
                "\n" +
                "fourth line (after the space)";

        filePartReader.setup(filePath, 2, 4);

        assertEquals(expected, filePartReader.readLines());
    }


}
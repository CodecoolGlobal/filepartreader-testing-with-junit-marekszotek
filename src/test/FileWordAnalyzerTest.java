import fileUtils.FilePartReader;
import fileUtils.FileWordAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FileWordAnalyzerTest {
    private FilePartReader readerMock;
    private FileWordAnalyzer analyzer;

    @BeforeEach
    void init() {
        readerMock = Mockito.mock(FilePartReader.class);
        analyzer = new FileWordAnalyzer(readerMock);
    }

    @Test
    void testOrderedWordsCallReadlines() throws IOException {
        when(readerMock.readLines()).thenReturn("abc");

        analyzer.getWordsOrderedAlphabetically();

        verify(readerMock).readLines();
    }

    @Test
    void testOrderedWordsCorrectly() throws IOException {
        List<String> expected = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        when(readerMock.readLines()).thenReturn("ccc bbb ddd aaa");

        assertEquals(expected, analyzer.getWordsOrderedAlphabetically());
    }

    @Test
    void testOrderedWordsWithoutSpecialCharacters() throws IOException {
        List<String> expected = Arrays.asList("aaa", "bbb");
        when(readerMock.readLines()).thenReturn("bbb (aaa @#!)");

        assertEquals(expected, analyzer.getWordsOrderedAlphabetically());
    }

    @Test
    void testContainSubstringCallReadlines() throws IOException {
        String substring = "sub";
        when(readerMock.readLines()).thenReturn("abc");

        analyzer.getWordsContainingSubstring(substring);

        verify(readerMock).readLines();
    }

    @Test
    void testContainSubstringCorrectly() throws IOException{
        String substring = "abc";
        List<String> expected = Arrays.asList("babcia");
        when(readerMock.readLines()).thenReturn(
                "babcia dziadek abac cba ab cr ab#c"
        );

        assertEquals(expected, analyzer.getWordsContainingSubstring(substring));

    }

    @Test
    void testPalindromeCallReadlines() throws IOException {
        when(readerMock.readLines()).thenReturn("abc");

        analyzer.getStringsWhichPalindromes();

        verify(readerMock).readLines();
    }

    @Test
    void testPalindromeIdentified() throws IOException{
        List<String> expected = Arrays.asList("racecar");
        when(readerMock.readLines()).thenReturn("accb ba ab racecar pull up");

        assertEquals(expected, analyzer.getStringsWhichPalindromes());
    }
}

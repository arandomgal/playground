package andrews.ying.compare;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class ContentEqualIgnoreEOL {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file1 = new File(ContentEqualIgnoreEOL.class.getClassLoader().getResource("file1.txt").toURI());
        File file2 = new File(ContentEqualIgnoreEOL.class.getClassLoader().getResource("file2.txt").toURI());
        try (Reader targetReader = Files.newBufferedReader(file1.toPath());
             Reader testReader = Files.newBufferedReader(file2.toPath())) {
            System.out.println(IOUtils.contentEqualsIgnoreEOL(targetReader, testReader));
        }
    }
}

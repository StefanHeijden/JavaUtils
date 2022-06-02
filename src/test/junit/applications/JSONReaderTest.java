package applications;

import applications.jsonreader.JSONReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import utilities.Logger;

import java.io.IOException;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
class  JSONReaderTest {

    @org.junit.jupiter.api.Test
    void simpleJSONTest() {
        try {
            new JSONReader(Paths.get("C:\\Users\\stheijde\\Documents\\PRs\\test.json"));
        } catch (IOException e) {
            Logger.log(e);
            Assertions.assertTrue(false);
        }
        Assertions.assertTrue(true);
    }

}
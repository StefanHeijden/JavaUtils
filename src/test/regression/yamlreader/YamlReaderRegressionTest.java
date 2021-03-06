package yamlreader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utilities.StartClass;
import utilities.UserInterface;

@ExtendWith(MockitoExtension.class)
class YamlReaderRegressionTest {

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // TODO clear results
    }

    @org.junit.jupiter.api.Test
    void main() {
        boolean success;
        try {
            try (MockedStatic<UserInterface> utilities = Mockito.mockStatic(UserInterface.class)) {
                utilities.when(() -> UserInterface.getUserInput(ArgumentMatchers.anyString()))
                        .thenReturn("cd C:/Users/stheijde/Repositories/Java/java-hello-world-with-maven-master/src/test/data/yaml")
                        .thenReturn("yaml landen ./../../results")
                        .thenReturn("exit");
                StartClass.init();
                StartClass.start();
                success = true;
            }
        } catch (Exception e) {
            success = false;
        }
        // TODO check results
        Assertions.assertTrue(success);
    }
}
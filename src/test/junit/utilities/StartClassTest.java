package utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StartClassTest {

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void main() {
        try {
            try (MockedStatic<UserInterface> utilities = Mockito.mockStatic(UserInterface.class)) {
                utilities.when(() -> UserInterface.getUserInput(Mockito.anyString()))
                        .thenReturn("cd C:/Users/stheijde/Repositories/Java/java-hello-world-with-maven-master/src/test/data/yaml")
                        .thenReturn("yaml landen ./../../results")
                        .thenReturn("exit");
                StartClass.init();
                StartClass.start();
            }
        } catch (Exception e) {
            Assertions.fail();
        }
        Assertions.assertTrue(true);
    }
}
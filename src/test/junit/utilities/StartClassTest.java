package utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StartClassTest {

    @org.junit.jupiter.api.Test
    void startup() {
        boolean success = true;
        try {
            try (MockedStatic<UserInterface> utilities = Mockito.mockStatic(UserInterface.class)) {
                utilities.when(() -> UserInterface.getUserInput(ArgumentMatchers.anyString()))
                        .thenReturn("exit");
                StartClass.init();
                StartClass.start();
            }
        } catch (Exception e) {
            success = false;
        }
        Assertions.assertTrue(success);
    }

    @org.junit.jupiter.api.Test
    void printHelp() {
        boolean success = true;
        try {
            try (MockedStatic<UserInterface> utilities = Mockito.mockStatic(UserInterface.class)) {
                utilities.when(() -> UserInterface.getUserInput(ArgumentMatchers.anyString()))
                        .thenReturn("help")
                        .thenReturn("exit");
                StartClass.init();
                StartClass.start();
            }
        } catch (Exception e) {
            success = false;
        }
        //TODO check input screen whether help was printed
        Assertions.assertTrue(success);
    }
}
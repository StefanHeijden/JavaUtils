package test.junit.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import utilities.StartClass;
import utilities.UserInterface;

@ExtendWith(MockitoExtension.class)
class StartClassTest {

    @org.junit.jupiter.api.Test
    void startup() {
        try {
            try (MockedStatic<UserInterface> utilities = Mockito.mockStatic(UserInterface.class)) {
                utilities.when(() -> UserInterface.getUserInput(Mockito.anyString()))
                        .thenReturn("exit");
                StartClass.init();
                StartClass.start();
            }
        } catch (Exception e) {
            Assertions.fail();
        }
        Assertions.assertTrue(true);
    }

    @org.junit.jupiter.api.Test
    void printHelp() {
        try {
            try (MockedStatic<UserInterface> utilities = Mockito.mockStatic(UserInterface.class)) {
                utilities.when(() -> UserInterface.getUserInput(Mockito.anyString()))
                        .thenReturn("help")
                        .thenReturn("exit");
                StartClass.init();
                StartClass.start();
            }
        } catch (Exception e) {
            Assertions.fail();
        }
        //TODO check input screen whether help was printed
        Assertions.assertTrue(true);
    }
}
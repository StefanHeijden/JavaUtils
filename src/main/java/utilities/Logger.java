package utilities;

import configurators.Configurator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Logger {
    public static final String LOG_FILE_LOCATION = Configurator.APPLICATION_PATH + "/logs/test.txt";
    static {
        createNewFile(LOG_FILE_LOCATION);
    }
    public static final File LOG_FILE = new File(LOG_FILE_LOCATION);

    private Logger(){}

    public static void log(String text) {
        try(FileWriter writer = new FileWriter(LOG_FILE)) {
            writer.write(ZonedDateTime.now() + " ");
            writer.write(text + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void log(Throwable throwable) {
        try(FileWriter writer = new FileWriter(LOG_FILE)) {
            writer.write(ZonedDateTime.now() + " ");
            writer.write(throwable + System.lineSeparator());
            for (StackTraceElement element : Arrays.stream(throwable.getStackTrace()).collect(Collectors.toList())) {
                writer.write("  " + element.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createNewFile(String fileName) {
        try {
            File targetFile = new File(fileName);
            if (!targetFile.createNewFile()) {
                PrintWriter writer = new PrintWriter(targetFile);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            Logger.log(e);
        }
    }
}

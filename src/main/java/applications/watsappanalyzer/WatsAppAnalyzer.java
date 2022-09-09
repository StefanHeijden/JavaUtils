package applications.watsappanalyzer;

import utilities.UserInterface;
import utilities.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WatsAppAnalyzer {

    private String writePath;

    public WatsAppAnalyzer(Path filePath, Path targetPath) throws IllegalAccessException, IOException {
        writePath = targetPath.toString();
        List<String> lines = Files.readAllLines(filePath);
        List<WatsAppMessage> watsAppMassages = new ArrayList<>();
        for (String line : lines) {
            watsAppMassages.add(new WatsAppMessage(line));
        }
        UserInterface.printLine("Did find file");
        // extract conversations
    }

    public void printResults(List<WatsAppMessage> watsAppMassages) {
        try(FileWriter writer = new FileWriter(Paths.get(writePath +  "\\" + "result.txt").toFile())) {
            Iterator<WatsAppMessage> iterator = watsAppMassages.iterator();
            while(iterator.hasNext()) {
                WatsAppMessage watsAppMessage = iterator.next();
                    writer.write(watsAppMessage.toString());
            }
        } catch (IOException e) {
            Logger.log(e);
        }
    }
}
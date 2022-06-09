package programs.main;

import programs.Program;
import utilities.UserInputReader;
import utilities.UserInterface;

import java.io.File;
import java.util.Map;

public class ListDirectory implements Program {

    @Override
    public boolean work(Map<String, Object> input) {
        File[] files = UserInputReader.getCurrentPath().toFile().listFiles();
        StringBuilder fileString = new StringBuilder("");
        if(files != null){
            for (File file : files) {
                fileString.append(file.getName());
                if(file.isDirectory()) {
                    fileString.append("/");
                }
                fileString.append("  ");
            }
        }
        UserInterface.printLine(fileString);
        return true;
    }

}

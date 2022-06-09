package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Console extends JTextArea {

    public Console(int rows, int columns){
        super(rows, columns);
        setTabSize(0);
        setBorder(BorderFactory.createLineBorder(Color.black));
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // not implemented
            }

            @Override
            public void keyPressed(KeyEvent e) {
                setText(getText().replace("\t", ""));
                if (e.getKeyCode() == 10) {
                    UserInterface.startReadingInput();
                }
                if (e.getKeyCode() == 9) {
                    addNewFileWhenTab();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // not implemented
            }

        });
    }

    private void addNewFileWhenTab() {
        String[] commands = getText().split(" ");
        final String recentInput = commands.length > 0 ? commands[commands.length - 1] : "";
        List<File> files = Arrays.stream(Objects.requireNonNull(UserInputReader.getCurrentPath().toFile().listFiles()))
                .filter(f -> f.getName().startsWith(recentInput))
                .collect(Collectors.toList());
        String newCommandLine;
        if(!files.isEmpty()) {
            if(commands.length > 0) {
                commands[commands.length - 1] = files.get(0).getName();
                newCommandLine = String.join(" ", commands);
            } else {
                newCommandLine = files.get(0).getName();
            }
        } else {
            newCommandLine = getText();
        }
        setText(newCommandLine);
    }

    public void reset() {
        setText("");
        setEditable(true);
    }

    public String getTextAndStopReadingInput() {
        setEditable(false);
        String userInput = getText()
                .replace("\t", "")
                .replace("\n", "");
        setText("Loading");
        return userInput;
    }
}

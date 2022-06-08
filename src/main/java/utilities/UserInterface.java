package utilities;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import static java.lang.Thread.sleep;

public class UserInterface{
    private static final DisplayPanel display = new DisplayPanel(10, 10);
    private static final Console console = new Console(10, 10);
    private static final DisplayPanel history = new DisplayPanel(10, 10);
    private static boolean waitingForUserInput = false;

    private UserInterface() {

    }

    public static void init() {
        JFrame f = new JFrame("Java Console");
        JPanel p = new JPanel();

        p.setLayout(new BorderLayout());
        p.add(display, BorderLayout.CENTER);
        p.add(console, BorderLayout.SOUTH);
        p.add(history, BorderLayout.EAST);
        f.add(p);
        f.setSize(900, 600);
        f.setVisible(true);
    }


    public static String getUserInput(String message) {
        waitingForUserInput = true;
        try {
            printLine(message);
            console.reset();
            while(waitingForUserInput) {
                sleep(30);
            }
            String userInput = console.getTextAndStopReadingInput();
            printLine(history, userInput);
            return userInput;
        } catch (InterruptedException e) {
            Logger.log(e);
        }
        return "exit";
    }

    public static void printLine(Object message) {
        printLine(display, message);
    }

    public static void printLine(JTextArea textArea, Object message) {
        textArea.append(message.toString() + "\n");
        int end = 0;
        try {
            end = textArea.getLineEndOffset(0);
        } catch (BadLocationException e) {
            Logger.log(e);
        }
        textArea.replaceRange("", 0, end);
    }

    public static void startReadingInput(){
        waitingForUserInput = false;
    }

}

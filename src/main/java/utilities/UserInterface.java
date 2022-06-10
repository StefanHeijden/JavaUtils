package utilities;

import java.awt.*;
import javax.swing.*;

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

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(display, BorderLayout.SOUTH);
        JScrollPane displayScrollPane = new JScrollPane(displayPanel);

        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.add(history, BorderLayout.SOUTH);
        JScrollPane historyScrollPane = new JScrollPane(historyPanel);

        historyScrollPane.setMinimumSize(new Dimension(500, 250));
        p.add(displayScrollPane, BorderLayout.CENTER);
        p.add(console, BorderLayout.SOUTH);
        p.add(historyScrollPane, BorderLayout.EAST);
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
            history.appendToPane(userInput, Color.BLACK);
            return userInput;
        } catch (InterruptedException e) {
            Logger.log(e);
        }
        return "exit";
    }

    public static void printLine(Object message) {
        display.appendToPane(message, Color.BLACK);
    }

    public static void startReadingInput(){
        waitingForUserInput = false;
    }

}

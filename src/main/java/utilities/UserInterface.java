package utilities;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import static java.lang.Thread.sleep;

public class UserInterface{
    private static final JTextArea display = new JTextArea(10, 10);
    private static final JTextArea console = new JTextArea(10, 10);
    private static final JTextArea history = new JTextArea(10, 10);
    private static boolean waitingForUserInput = false;

    private UserInterface() {

    }

    public static void init() {
        JFrame f = new JFrame("Java Console");
        console.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel p = new JPanel();

        console.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // not implemented
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    waitingForUserInput = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // not implemented
            }

        });

        p.setLayout(new BorderLayout());
        p.add(display, BorderLayout.CENTER);
        p.add(console, BorderLayout.SOUTH);
        p.add(history, BorderLayout.EAST);
        f.add(p);
        f.setSize(900, 600);
        display.setEditable(false);
        history.setEditable(false);
        console.setTabSize(0);
        for(int i = 0; i < 25; i++) {
            display.append("\n");
            history.append("\n");
        }
        f.setVisible(true);
    }


    public static String getUserInput(String message) {
        waitingForUserInput = true;
        try {
            printLine(message);
            console.setText("");
            console.setEditable(true);
            while(waitingForUserInput) {
                sleep(30);
            }
            console.setEditable(false);
            String userInput = console.getText().replace("\n", "");
            printLine(history, userInput);
            console.setText("Loading");
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

}

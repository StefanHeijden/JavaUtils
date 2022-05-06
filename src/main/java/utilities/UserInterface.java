package utilities;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import static java.lang.Thread.sleep;

public class UserInterface{
    private final JTextArea display = new JTextArea(10, 10);
    private final JTextArea console = new JTextArea(10, 10);
    private final JTextArea history = new JTextArea(10, 10);
    private boolean waitingForUserInput = false;
    private StringBuilder userInput = new StringBuilder();

    public UserInterface(){
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
                if (Character.isLetterOrDigit(e.getKeyCode())) {
                    userInput.append(e.getKeyChar());
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
        for(int i = 0; i < 25; i++) {
            display.append("\n");
            history.append("\n");
        }
        f.setVisible(true);
    }


    public String getUserInput(String message) {
        waitingForUserInput = true;
        userInput = new StringBuilder();
        try {
            printLine(message);
            console.setText("");
            console.setEditable(true);
            while(waitingForUserInput) {
                sleep(30);
            }
            console.setEditable(false);
            console.setText("Loading");
            printLine(history, userInput.toString());
            return userInput.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "exit";
    }

    public void printWord(Object message) {
        display.append(message.toString());
    }

    public void printLine(Object message) {
        printLine(display, message);
    }

    public void printLine(JTextArea textArea, Object message) {
        textArea.append(message.toString() + "\n");
        int end = 0;
        try {
            end = textArea.getLineEndOffset(0);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        textArea.replaceRange("", 0, end);
    }

    public void printWithTab(String firstMessage, String secondMessage, int tabLength){
        printWord(firstMessage);
        for(int i = firstMessage.length(); i< tabLength; i++) {
            printWord(" ");
        }
        printWord(secondMessage);
    }

}
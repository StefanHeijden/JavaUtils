package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
                if (e.getKeyCode() == 10) {
                    UserInterface.startReadingInput();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // not implemented
            }

        });
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

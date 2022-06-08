package utilities;

import javax.swing.*;

public class DisplayPanel extends JTextArea {

    public static final int EFFECTIVE_ROWS = 25;

    public DisplayPanel(int rows, int columns) {
        super(rows, columns);
        setEditable(false);
        for(int i = 0; i < EFFECTIVE_ROWS; i++) {
            append("\n");
        }
    }
}

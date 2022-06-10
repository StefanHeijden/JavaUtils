package utilities;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class DisplayPanel extends JTextPane {

    public static final int EFFECTIVE_ROWS = 25;

    public DisplayPanel(int rows, int columns) {
        setEditable(false);
        Dimension d = getPreferredSize();
        setPreferredSize(d);
        setCaretPosition(0);
        for(int i = 0; i < EFFECTIVE_ROWS; i++) {
            appendToPane("\nThis is another test", Color.BLACK);
        }
    }

    public void appendToPane(Object msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = getDocument().getLength();
        setCaretPosition(len);
        setCharacterAttributes(aset, false);
        setText(getText() + msg.toString());
    }
}

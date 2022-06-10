package utilities;

import java.awt.*;

import javax.swing.*;

import javax.swing.border.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class TestClass extends JFrame
{
    private final int BORDER_WIDTH = 10;
    private JPanel topPanel;
    private JTextPane tPane;

    public TestClass()
    {
        setLayout(new BorderLayout());
        JTextPane jtp_help_center = new JTextPane();
        jtp_help_center.setEditable(false);
        Dimension d = jtp_help_center.getPreferredSize();
        jtp_help_center.setText("This is a test");
        jtp_help_center.setPreferredSize(d);
        jtp_help_center.setCaretPosition(0);
        JScrollPane jsp_center = new JScrollPane(jtp_help_center);
        add(jsp_center, BorderLayout.CENTER);
        setVisible(true);
        setSize(900, 600);
    }

    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new TestClass();
            }
        });
    }
}
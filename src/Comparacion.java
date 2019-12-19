import Controlador_ficheros.controlador_gestor_fitxer;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;


public class Comparacion extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextPane textArea1;
    private JTextPane textArea2;
    private String file1;
    private String file2;


    public Comparacion(String s1, String s2, boolean image) throws IOException {
        setContentPane(contentPane);
        setMaximumSize(new Dimension(500, 700));
        setSize(new Dimension(400, 600));
        setModal(false);
        getRootPane().setDefaultButton(buttonCancel);

        if (!image) {

            file1 = s1;
            file2 = s2;

            textArea1.setMaximumSize(new Dimension(300, 500));

            textArea2.setMaximumSize(new Dimension(300, 500));


            textArea1.setEditable(false);
            textArea2.setEditable(false);

            textArea1.setText(s1);
            textArea2.setText(s2);
        }

        else {
            controlador_gestor_fitxer cf = new controlador_gestor_fitxer();
            cf.create_img_aux1("temp1",s1);
            cf.create_img_aux1("temp2",s2);
            textArea1.insertIcon(new ImageIcon("temp1.png"));
            textArea2.insertIcon(new ImageIcon("temp2.png"));
        }


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }



    /*public static void main(String[] args) throws IOException {
        Comparacion dialog = new Comparacion();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/


    public class BatchDocument extends DefaultStyledDocument {
        /**
         * EOL tag that we re-use when creating ElementSpecs
         */
        private final char[] EOL_ARRAY = { '\n' };

        /**
         * Batched ElementSpecs
         */
        private ArrayList batch = null;

        public BatchDocument() {
            batch = new ArrayList();
        }

        /**
         * Adds a String (assumed to not contain linefeeds) for
         * later batch insertion.
         */
        public void appendBatchString(String str,
                                      AttributeSet a) {
            // We could synchronize this if multiple threads
            // would be in here. Since we're trying to boost speed,
            // we'll leave it off for now.

            // Make a copy of the attributes, since we will hang onto
            // them indefinitely and the caller might change them
            // before they are processed.
            a = a.copyAttributes();
            char[] chars = str.toCharArray();
            batch.add(new ElementSpec(
                    a, ElementSpec.ContentType, chars, 0, str.length()));
        }

        /**
         * Adds a linefeed for later batch processing
         */
        public void appendBatchLineFeed(AttributeSet a) {
            // See sync notes above. In the interest of speed, this
            // isn't synchronized.

            // Add a spec with the linefeed characters
            batch.add(new ElementSpec(
                    a, ElementSpec.ContentType, EOL_ARRAY, 0, 1));

            // Then add attributes for element start/end tags. Ideally
            // we'd get the attributes for the current position, but we
            // don't know what those are yet if we have unprocessed
            // batch inserts. Alternatives would be to get the last
            // paragraph element (instead of the first), or to process
            // any batch changes when a linefeed is inserted.
            Element paragraph = getParagraphElement(0);
            AttributeSet pattr = paragraph.getAttributes();
            batch.add(new ElementSpec(null, ElementSpec.EndTagType));
            batch.add(new ElementSpec(pattr, ElementSpec.StartTagType));
        }

        public void processBatchUpdates(int offs) throws
                BadLocationException {
            // As with insertBatchString, this could be synchronized if
            // there was a chance multiple threads would be in here.
            ElementSpec[] inserts = new ElementSpec[batch.size()];
            batch.toArray(inserts);

            // Process all of the inserts in bulk
            super.insert(offs, inserts);
        }
    }
}

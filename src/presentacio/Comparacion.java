package presentacio; /**
 * /file Comparacion.java
 * /author Daniel Cano Carrascosa
 * /title Comparació de imatges o textos
 */


import Controlador_ficheros.controlador_gestor_fitxer;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/** \brief Clase Comparacion
    \pre  Cert
    \post Definicio de la clase Comparacion
    \details Serveix per comparar dos texts o imatges .ppm
 */
public class Comparacion extends JDialog {


    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextPane textArea1;
    private JTextPane textArea2;
    private JTextField fitxerOriginalTextField;
    private JTextField fitxerComprimitTextField;
    private String file1;
    private String file2;
    private Task_IntegerUpdate tk1;
    private Task_IntegerUpdate tk2;
    private static  JTextField fit;

    public BufferedImage bufferImage(Image image, int type) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        return bufferedImage;
    }

    /**
     * \brief Compara dos fitxers o carpetes i els mostra per pantalla
     * \pre  Cert
     * \post Compara dos fitxers o carpetes i els mostra per pantalla
     * \details Les variables s1 y s2 contenen o bé el text en cas dels textos o be els paths de les imatges altrament
     */
    public Comparacion(String s1, String s2, boolean image, JProgressBar p, JTextField fite) throws IOException {
        setContentPane(contentPane);
        fit = fite;
        setMaximumSize(new Dimension(700, 500));
        setSize(new Dimension(600, 400));
        setModal(false);
        getRootPane().setDefaultButton(buttonCancel);
        fitxerOriginalTextField.setBorder(BorderFactory.createEmptyBorder());
        fitxerComprimitTextField.setBorder(BorderFactory.createEmptyBorder());

        if (!image && !s1.equals("-1")) {

            file1 = s1;
            file2 = s2;

            textArea1.setMaximumSize(new Dimension(500, 300));

            textArea2.setMaximumSize(new Dimension(500, 300));


            textArea1.setVisible(false);
            textArea2.setVisible(false);
            textArea1.setEditable(false);
            textArea2.setEditable(false);
            p.setMaximum(s1.length() + s2.length());
            p.setIndeterminate(false);
            tk1 = new Task_IntegerUpdate(p, textArea1, s1);
            tk1.execute();



            tk2 = new Task_IntegerUpdate(p, textArea2, s2);
            tk2.execute();


        } else if (!s1.equals("-1")) {
            p.setVisible(false);
            fite.setVisible(false);
            controlador_gestor_fitxer cf = new controlador_gestor_fitxer();
            cf.create_img_aux1("temp1", s1);
            cf.create_img_aux1("temp2", s2);

            textArea1.insertIcon(new ImageIcon("temp1.png"));
            textArea2.insertIcon(new ImageIcon("temp2.png"));
            cf.delete_file("temp1.png");
            cf.delete_file("temp2.png");
            cf.delete_file(s2);
        }


        /** \brief Tenca la vista
         \pre  Cert
         \post Tenca la vista
         \details Tenca la vista que esta mostrant o be dos textos o be dos imatges
         */
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

    private void onCancel() {
        // add your code here if necessary
        this.dispose();
    }

    public static  JTextField getFit () {return fit;}


    class Task_IntegerUpdate extends SwingWorker<Void, Integer> {

        String s;
        JTextPane label;
        JProgressBar p;

        public Task_IntegerUpdate(JProgressBar p, JTextPane label, String s1) {
            this.label = label;
            this.s = s1;
            this.p = p;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            int i = chunks.get(chunks.size() - 1);
            p.setValue(i); // The last value in this array is all we care about.
            //System.out.println(i);
            //label.setText("Loading " + i + " of " + max);
        }

        @Override
        protected Void doInBackground() throws Exception {
            Document doc = new DefaultStyledDocument();
            for (int i = 0; i < s.length(); ++i) {
                doc.insertString(doc.getLength(), String.valueOf(this.s.charAt(i)), null);
                publish(i+p.getValue());
            }
            if (p.getValue() >= s.length()*2 - 5) Comparacion.getFit().setText("Procesant informació per ser mostrada...");
            //this.label.setText(this.s);
            this.label.setDocument(doc);
            this.label.setVisible(true);
            return null;
        }

        @Override
        protected void done() {
            try {
                get();
                p.setVisible(false);
                Comparacion.getFit().setVisible(false);
                Comparacion.getFit().setText("Analitzant fitxer...");
                //JOptionPane.showMessageDialog(jpb.getParent(), "Success", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
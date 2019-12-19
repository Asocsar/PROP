/**
 * /file Comparacion.java
 * /author Daniel Cano Carrascosa
 * /title Comparació de imatges o textos
 */


import Controlador_ficheros.controlador_gestor_fitxer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

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


    /** \brief Compara dos fitxers o carpetes i els mostra per pantalla
     \pre  Cert
     \post Compara dos fitxers o carpetes i els mostra per pantalla
     \details Les variables s1 y s2 contenen o bé el text en cas dels textos o be els paths de les imatges altrament
     */
    public Comparacion(String s1, String s2, boolean image) throws IOException {
        setContentPane(contentPane);
        setMaximumSize(new Dimension(700, 500));
        setSize(new Dimension(600, 400));
        setModal(false);
        getRootPane().setDefaultButton(buttonCancel);
        fitxerOriginalTextField.setBorder(BorderFactory.createEmptyBorder());
        fitxerComprimitTextField.setBorder(BorderFactory.createEmptyBorder());

        if (!image) {

            file1 = s1;
            file2 = s2;

            textArea1.setMaximumSize(new Dimension(500, 300));

            textArea2.setMaximumSize(new Dimension(500, 300));


            textArea1.setEditable(false);
            textArea2.setEditable(false);

            textArea1.setText(s1);
            textArea2.setText(s2);
        } else {
            controlador_gestor_fitxer cf = new controlador_gestor_fitxer();
            cf.create_img_aux1("temp1", s1);
            cf.create_img_aux1("temp2", s2);
            textArea1.insertIcon(new ImageIcon("temp1.png"));
            textArea2.insertIcon(new ImageIcon("temp2.png"));
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
}
/**
 * /file Estadisticas.java
 * /author Daniel Cano Carrascosa
 * /title Mostrar les estadístiques tant globals com locals del algoritmes
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import  Estadístiques.*;


/** \brief Clase Estadisticas
 \pre  Cert
 \post Definicio de la clase Estadisticas
 \details Mostra les estadístiques globals i locals dels algoritmes
 */
public class Estadisticas extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JTextArea Quad2;
    private JTextArea Quad1;
    private JTextArea Quad4;
    private JTextArea Quad3;
    private int ini_y = 410;
    private int ini_x = 440;
    private int max_y = ini_y;
    private int max_x = ini_x;
    private int post_y = max_y + max_y/2 + max_y/4;
    private int post_x = max_x*2;
    private static Timer time = null;
    private JTextArea [] L = new JTextArea[] {Quad1, Quad2, Quad3, Quad4};


    /** \brief Estableix un tamany fixe
     \pre  Cert
     \post L'aplicació es manté a la mateixa mida
     \details Cada vegada que s'intenta que la vista sigui més gran o petita aquest canvi es veu ignorat i s'estableix el
     \seu tamany anterior
     */
    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            // Recalculate the variable you mentioned
            setSize(new Dimension(max_x,max_y));
        }
    }

    /** \brief Mostra i actualitza la vista de totes les estadístiques
     \pre  Cert
     \post La vista cambia de tamany cambia i es mostren totes les estadístiques
     \details
     */
    private void actual (Set<String> OPC, String last) {
        for (JTextArea A : L) {
            A.setVisible(true);
            A.setEnabled(true);
            A.setEditable(false);
        }
        String [] M = Estadistiques.nom_atr();
        int i = 0;
        for (String name : OPC) {
            if (name.equals(last)) {
                L[i].setBackground(Color.orange);
            }
            actual_alg(L[i],name, false);
            ++i;
        }
    }

    /** \brief Mostra i actualitza la vista de totes les estadístiques pero només d'un algoritme
     \pre  Cert
     \post La vista cambia de tamany cambia i es mostren totes les estadístiques d'un algoritme
     \details
     */
    public void actual_alg (JTextArea Q, String alg, boolean ultim) {
        String [] M = Estadistiques.nom_atr();
        String [] J = Estadistiques.getatrib();
        Map<String, List<Double>> MP = Estadistiques.getparam();
        List<Double> L = MP.get(alg);
        Q.setText("");
        Q.append("\n"+" "+alg+"\n");
        Q.append("\n");
        for (int i = 0; i < 9; ++i) {
            if (i == 0) Q.append(" Estadistiques més Recents\n\n");
            if (i == 4) Q.append("\n Estadístiques Globals\n\n");
            boolean mitad = false;
            Q.append(" " + M[i]);
            Q.append(" : ");
            DecimalFormat dc = null;
            if (M[i].startsWith("Número de")) {
                dc = new DecimalFormat("0");
                mitad = true;
            }
            else dc = new DecimalFormat("0.00");
            String num = null; //dc.format(L.get(i));
            if (mitad) num = dc.format(L.get(i)/2);
            else num = dc.format(L.get(i));
            Q.append(num);
            Q.append(" " + J[i]);
            Q.append(" \n");
        }
        if (ultim) {
            Q.append("\n Ultim Algoritme Utilitzat : ");
            Double d = MP.get("LAST ALGORITHM").get(0);
            int n = d.intValue();
            String[] lista = Estadistiques.asociacio_algoritmes();
            if (n != -1) Q.append(lista[n]);
            else Q.append("CAP\n");
        }
    }

    /** \brief Mostra la vista de Estadístiques
     \pre  Cert
     \post Definicio de la clase Comparacion
     \details Serveix per visualitzar les estadístiques
     */
    public Estadisticas (Set<String> OPC) {
        setContentPane(contentPane);
        setResizable(false);
        addComponentListener(new ResizeListener());
        setMinimumSize(new Dimension(max_x,max_y));
        setMaximumSize(new Dimension(max_x,max_y));
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonCancel);
        for (String s : OPC) comboBox1.addItem(s);
        comboBox1.addItem("Totes");
        buttonCancel.setForeground(Color.BLACK);
        for (JTextArea A : L) {
            A.setBorder(BorderFactory.createLoweredSoftBevelBorder());
            A.setEditable(false);
            A.setEnabled(true);
            A.setVisible(false);
            A.setBackground(Color.decode("#6BBCD1"));
            A.setBorder(BorderFactory.createRaisedBevelBorder());
            A.setMaximumSize(new Dimension(360,300));
            A.setMinimumSize(new Dimension(360,300));
        }


        Quad1.setVisible(true);
        Quad1.setForeground(Color.black);
        String[] M = Estadistiques.nom_atr();
        Map<String,List<Double>> MP = Estadistiques.getparam();
        List<Double> Ld = MP.get((String)comboBox1.getSelectedItem());

        Double d = MP.get("LAST ALGORITHM").get(0);
        int n = d.intValue();
        String [] lista = Estadistiques.asociacio_algoritmes();
        if (n != -1) {
            boolean finded = false;
            int i = 0;
            while (!finded) {
                if (comboBox1.getItemAt(i).equals(lista[n])) {
                    finded = true;
                    comboBox1.setSelectedItem(comboBox1.getItemAt(i));
                }
                else {
                    ++i;
                }
            }
        }

        actual_alg(Quad1, (String) comboBox1.getSelectedItem(), true);



        /** \brief Mostra les estadistiques de l'algorisme seleccionat
         \pre  Cert
         \post Cambia la vista a l'algorisme corresponent
         \details En cas de que la selecció sigui tots llavors es mostren els 4 algoritmes al mateix temps i el tamany
         \ de la vista cambia
         */
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comboBox1.getSelectedItem().equals("Totes")) {
                    max_x = post_x;
                    max_y = post_y;
                    setMinimumSize(new Dimension(max_x,max_y));
                    setMinimumSize(new Dimension(max_x,max_y));
                    setMaximumSize(new Dimension(max_x,max_y));
                    setMaximumSize(new Dimension(max_x,max_y));
                    actual(OPC, (n != -1) ? lista[n] : "CAP");
                }
                else {
                    if (max_x == post_x && max_y == post_y) {
                        max_x = ini_x;
                        max_y = ini_y;
                        setMinimumSize(new Dimension(max_x,max_y));
                        setMinimumSize(new Dimension(max_x,max_y));
                        setMaximumSize(new Dimension(max_x,max_y));
                        setMaximumSize(new Dimension(max_x,max_y));
                        setSize(new Dimension(max_x, max_y));
                        for (JTextArea A : L) {
                            A.setVisible(false);
                            A.setBackground(Color.decode("#6BBCD1"));
                        }
                    }
                    Quad1.setVisible(true);
                    actual_alg(Quad1, (String) comboBox1.getSelectedItem(), true);

                }

            }
        });


        /** \brief Tanca la vista de les Estadístiques
         \pre  Cert
         \post Cambia la vista a l'algorisme corresponent
         \details En cas de que la selecció sigui tots llavors es mostren els 4 algoritmes al mateix temps i el tamany
         \ de la vista cambia
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

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }

}


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import  Estadístiques.*;
import com.sun.org.apache.xpath.internal.operations.Number;
import javafx.scene.layout.Border;
import org.omg.CORBA.INTERNAL;


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
    private int post_y = max_y*2;
    private int post_x = max_x*2;
    private static Timer time = null;
    private JTextArea [] L = new JTextArea[] {Quad1, Quad2, Quad3, Quad4};



    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            // Recalculate the variable you mentioned
            setSize(new Dimension(max_x,max_y));
        }
    }

    public void actgen (JTextArea Q, String [] M, List<Double> L, String name) {
        Q.setText("");
        Q.append(name);
        Q.append("\n");
        for (int i = 0; i < 9; ++i) {
            if (i == 0) Q.append(" Estadistiques més Recents\n\n");
            if (i == 4) Q.append("\n Estadístiques Globals\n\n");
            Q.append(" " + M[i]);
            Q.append(" : ");
            DecimalFormat dc = new DecimalFormat("0.00");
            String num = dc.format(L.get(i));
            Q.append(num);
            Q.append(" \n");
        }
    }

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
            actual_alg(L[i],name);
            ++i;
        }
    }

    public void actual_alg (JTextArea Q, String alg) {
        String [] M = Estadistiques.nom_atr();
        Map<String, List<Double>> MP = Estadistiques.getparam();
        List<Double> L = MP.get(alg);
        Q.setText("");
        Q.append("\n"+" "+alg+"\n");
        Q.append("\n");
        for (int i = 0; i < 9; ++i) {
            if (i == 0) Q.append(" Estadistiques més Recents\n\n");
            if (i == 4) Q.append("\n Estadístiques Globals\n\n");
            Q.append(" " + M[i]);
            Q.append(" : ");
            DecimalFormat dc = new DecimalFormat("0.00");
            String num = dc.format(L.get(i));
            Q.append(num);
            Q.append(" \n");
        }
        Q.append("\n Ultim Algoritme Utilitzat : ");
        Double d = MP.get("LAST ALGORITHM").get(0);
        int n = d.intValue();
        String [] lista = Estadistiques.asociacio_algoritmes();
        if (n != -1) Q.append(lista[n]);
        else Q.append("CAP\n");
    }

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
            A.setBackground(Color.decode("#d0c4c4"));
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

        actual_alg(Quad1, (String) comboBox1.getSelectedItem());




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
                    actual(OPC, lista[n]);
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
                            A.setBackground(Color.decode("#d0c4c4"));
                        }
                    }
                    Quad1.setVisible(true);
                    actual_alg(Quad1, (String) comboBox1.getSelectedItem());

                }

            }
        });


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



    /*public static void main(String[] args) {
        Estadisticas dialog = new Estadisticas();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}

/*
                    time = new Timer(1, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int inc = 9;
                            if (getWidth() < post_x) {
                                max_x = getWidth()+inc;
                                setMinimumSize(new Dimension(getWidth()+inc,getHeight()));

                            }
                            if (getHeight() < post_y) {
                                max_y = getHeight() + inc;
                                setMaximumSize(new Dimension(getWidth(),getHeight()+inc));
                            }
                            if ( max_y >= post_y && max_x >= post_x) {
                                time.stop();
                                actual();
                            }
                        }
                    });
                    time.start();*/

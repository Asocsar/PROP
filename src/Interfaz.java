
import Controlador_Compressio_Descompressio.Cont_CD;
import Controlador_ficheros.controlador_gestor_fitxer;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Interfaz extends JFrame {

    public static class JFilePicker extends JPanel {
        private String textFieldLabel;
        private String buttonLabel;

        private JLabel label;
        private JTextField textField;
        private JButton button;

        private JFileChooser fileChooser;

        private int mode;
        public static final int MODE_OPEN = 1;
        public static final int MODE_SAVE = 2;
        private boolean directori = false;

        public JFilePicker(String textFieldLabel, String buttonLabel) {
            this.textFieldLabel = textFieldLabel;
            this.buttonLabel = buttonLabel;

            fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);

            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            // creates the GUI
            label = new JLabel(textFieldLabel);

            textField = new JTextField(20);
            textField.setEditable(false);
            button = new JButton(buttonLabel);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    buttonActionPerformed(evt);
                }
            });

            add(label);
            add(textField);
            add(button);

        }

        private void buttonActionPerformed(ActionEvent evt) {
            if (mode == MODE_OPEN) {
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            } else if (mode == MODE_SAVE) {
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    textField.setText(fileChooser.getSelectedFile().getAbsolutePath());

                }
            }
        }

        public void addFileTypeFilter(String[] e, String description) {
            FileTypeFilter filter = new FileTypeFilter(e, description);
            fileChooser.addChoosableFileFilter(filter);

        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public String getSelectedFilePath() {
            return textField.getText();
        }

        public JTextField gettextf () {return textField;}

        public JFileChooser getFileChooser() {
            return this.fileChooser;
        }

        public JFilePicker change (String s1, String s2) {
            return new JFilePicker(s1,s2);
        }

        public void removefilter () {
            fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
            textField.setText("");
        }

        public boolean getdir () {return directori;}
    }

    public static class FileTypeFilter extends FileFilter {

        private String[] extension;
        private String description;

        public FileTypeFilter(String[] extension, String description) {
            this.extension = extension;
            this.description = description;
        }

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            for (String s : extension) {
                if (file.getName().toLowerCase().endsWith(s)) return true;
            }
            return false;
        }

        public String getDescription() {
            return description + Arrays.toString(extension);
        }
    }

    public static class Boton extends JButton {
        public Boton() {
            this.setText("Accion");
        }
    }

    private JPanel Panel;
    private JButton Sortir;
    private JFilePicker Picker1;
    private JFilePicker Picker2;
    private JRadioButton LZW;
    private JRadioButton LZSS;
    private JRadioButton LZ78;
    private JRadioButton JPEG;
    private JButton Compare;
    private JButton Globales;
    private JButton Accion;
    private JSlider slider1;
    private JPanel Auxiliar;
    private static JFrame frame;
    private static int metodo = 0;
    private boolean directorio = false;
    private JRadioButton [] L = new JRadioButton[] {LZW, LZSS, LZ78, JPEG};
    HashMap<Integer, String> M = new HashMap<Integer, String>();

    private void createUIComponents () {
        Picker1 = new JFilePicker("Elemento a Comprimir/Descomprimir", "Busca");
        Picker1.setMode(JFilePicker.MODE_SAVE);
        Picker1.getFileChooser().setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        String [] ext = new String[] {".txt", ".ppm", ".fw", ".fs", ".f8", ".fg", ".FW", ".FS", ".F8"};
        Picker1.addFileTypeFilter(ext, "Archivo");

        Picker2 = new JFilePicker("Seleccione destino", "Busca");
        Picker2.setMode(JFilePicker.MODE_SAVE);
        Picker2.getFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    }

    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            // Recalculate the variable you mentioned
            Picker1.gettextf().setColumns(frame.getSize().width/20);
            Picker2.gettextf().setColumns(frame.getSize().width/20);
        }
    }


    public class Framepop extends JFrame {
        JFrame Frame = new JFrame();
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();

        public Framepop (String s1, String s2) {
            super("Comparacion");
            Frame.setEnabled(true);
            Frame.setVisible(true);
            Frame.setSize(300, 300);
            tf1.setEnabled(true);
            tf2.setEnabled(true);
            tf1.setEditable(false);
            tf2.setEditable(false);
            tf1.setVisible(true);
            tf2.setVisible(true);
            tf1.setText(s1);
            tf2.setText(s2);
            Frame.add(tf1);
            Frame.add(tf2);
            Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }


    public Interfaz() {
        frame.setSize(40, 40);
        frame.addComponentListener(new ResizeListener());
        Picker1.gettextf().setColumns(frame.getSize().width);
        Picker2.gettextf().setColumns(frame.getSize().width);
        slider1.setPaintTicks(true);
        slider1.setSnapToTicks(true);
        slider1.setVisible(false);
        slider1.setMaximumSize(new Dimension(0,0));
        Accion.setText("Comprimir/Descomprimir");
        M.put(0, "LZW");
        M.put(1, "LZSS");
        M.put(2, "LZ78");
        M.put(3, "JPEG");
        ButtonGroup group1 = new ButtonGroup();
        group1.add(LZ78);
        group1.add(LZW);
        group1.add(LZSS);
        group1.add(JPEG);
        for (JRadioButton b : L) b.setEnabled(false);
        Sortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        Picker1.textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                act();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //act();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                act();
            }

            public void act () {
                String p = Picker1.getSelectedFilePath();
                File f = new File(p);
                directorio = f.isDirectory();
                if (directorio) {
                    Accion.setText("Comprimir");
                    for (JRadioButton b : L) b.setEnabled(true);
                    L[metodo].doClick();
                    JPEG.setEnabled(false);
                    if (M.get(metodo).equals("JPEG")) {
                        metodo = 0;
                        L[metodo].doClick();
                    }
                }
                else {
                    if (p.substring(p.length()-4).equals(".txt") && !p.substring(p.length()-4).equals(".ppm")) {
                        Accion.setText("Comprimir");
                        for (JRadioButton b : L) b.setEnabled(true);
                        JPEG.setEnabled(false);
                        L[metodo].doClick();
                    }
                    else if (p.substring(p.length()-4).equals(".txt") || p.substring(p.length()-4).equals(".ppm")) {
                        Accion.setText("Comprimir");
                        for (JRadioButton b : L) b.setEnabled(true);
                        L[metodo].doClick();
                    }
                    else {
                        Accion.setText("Descomprimir");
                        group1.clearSelection();
                        for (JRadioButton b : L) b.setEnabled(false);
                    }
                }
            }
        });

        Accion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cont_CD C = new Cont_CD();
                if (Accion.getText().equals("Comprimir/Descomprimir")) {
                    JOptionPane.showMessageDialog(frame, "Escoge antes un Elemento a comprimir o descomprimir");
                } else {
                    try {
                        if (!directorio)
                            if (Accion.getText().equals("Comprimir"))
                                C.compressio_fitxer(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), M.get(metodo));
                            else
                                C.descompressio_fitxer(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath());
                        else if (Accion.getText().equals("Comprimir"))
                            C.compressio_carpeta(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), M.get(metodo));
                        else
                            C.descompressio_carpeta(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (controlador_gestor_fitxer.FicheroCompressionNoValido | controlador_gestor_fitxer.FicheroDescompressionNoValido ficheroCompressionNoValido) {
                        JOptionPane.showMessageDialog(frame, ficheroCompressionNoValido.getMessage());
                    }
                }
            }
        });

        Compare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cont_CD C = new Cont_CD();
                try {
                    String[] S = C.comparar();

                    Framepop p = new Framepop(S[0], S[1]);
                    p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    p.setSize(300, 300);

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (controlador_gestor_fitxer.FicheroCompressionNoValido | controlador_gestor_fitxer.FicheroDescompressionNoValido ficheroCompressionNoValido) {
                    JOptionPane.showMessageDialog(frame, ficheroCompressionNoValido.getMessage());
                }
            }
        });



        LZW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 0;
                slider1.setVisible(false);
                slider1.setMaximumSize(new Dimension(0,0));
            }
        });

        LZSS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 1;
                slider1.setVisible(false);
                slider1.setMaximumSize(new Dimension(0,0));
            }
        });

        LZ78.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 2;
                slider1.setVisible(false);
                slider1.setMaximumSize(new Dimension(0,0));
            }
        });

        JPEG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 3;
                slider1.setVisible(true);
                slider1.setMaximumSize(new Dimension(30,50));
            }
        });

    }

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setContentPane(new Interfaz().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 300);
        frame.setMaximumSize(new Dimension(900, 300));
        frame.setMinimumSize(new Dimension(900, 300));
        frame.setVisible(true);
    }


}

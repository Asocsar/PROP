
import Controlador_Compressio_Descompressio.Cont_CD;
import Controlador_ficheros.controlador_gestor_fitxer;
import com.sun.deploy.panel.JSmartTextArea;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.*;
import java.util.List;

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
    private JButton Compare;
    private JButton Globales;
    private JButton Accion;
    private JSlider slider1;
    private JComboBox comboBox1;
    private static JFrame frame;
    private static int metodo = 0;
    private boolean directorio = false;
    private static Cont_CD cont = new Cont_CD();
    private static controlador_gestor_fitxer cf = new controlador_gestor_fitxer();
    private static HashMap<Integer, String> M = new HashMap<Integer, String>();
    private static Map<String, List<String>> Asoc = cont.getAsoc();

    private void createUIComponents () {
        Picker1 = new JFilePicker("Elemento a Comprimir/Descomprimir", "Busca");
        Picker1.setMode(JFilePicker.MODE_SAVE);
        Picker1.getFileChooser().setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        List<String> S = new ArrayList<>();
        for (String Alg : Asoc.keySet()){
            for (String e : Asoc.get(Alg))
                if (!S.contains(e) && !e.equals("folder")) S.add(e);
        }
        String [] F = new String[S.size()];
        int i = 0;
        for (String p : S) F[i++] = p;
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

    public void LZW_option() {
        metodo = 0;
        slider1.setVisible(false);
        slider1.setMaximumSize(new Dimension(0,0));
    }

    public void LZSS_option() {
        metodo = 1;
        slider1.setVisible(false);
        slider1.setMaximumSize(new Dimension(0,0));
    }

    public void LZ78_option() {
        metodo = 2;
        slider1.setVisible(false);
        slider1.setMaximumSize(new Dimension(0,0));
    }

    public void JPEG_option() {
        metodo = 3;
        slider1.setVisible(true);
        slider1.setMaximumSize(new Dimension(30,50));
    }


    public Interfaz() {
        getContentPane().setBackground(Color.magenta);
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
                for (ActionListener a : comboBox1.getActionListeners()) comboBox1.removeActionListener(a);
                comboBox1.removeAllItems();
                comboBox1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String S = (String) comboBox1.getSelectedItem();
                        switch (S) {
                            case "LZW" : LZW_option();
                                break;
                            case "LZ78" : LZ78_option();
                                break;
                            case "LZSS" : LZSS_option();
                                break;
                            default: JPEG_option();
                        }

                    }
                });
                if (directorio) {
                    Accion.setText("Comprimir");
                    String substring = "folder";
                    for (String Alg : Asoc.keySet()) {
                        if (Asoc.get(Alg).contains(substring)) comboBox1.addItem(Alg);
                    }
                    comboBox1.setSelectedIndex(metodo);
                }
                else {
                    String substring = p.substring(p.length() - 4);
                    boolean comprimir = substring.equals(".txt") || substring.equals(".ppm");
                    if (comprimir) {
                        Accion.setText("Comprimir");
                        List<String> S = new ArrayList<>();

                        for (String Alg : Asoc.keySet()) {
                            if (Asoc.get(Alg).contains(substring)) {
                                comboBox1.addItem(Alg);
                            }
                        }
                        comboBox1.setSelectedIndex(0);
                    }
                    else {
                        Accion.setText("Descomprimir");
                        comboBox1.removeAllItems();
                        slider1.setVisible(false);
                        slider1.setMaximumSize(new Dimension(0,0));

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
                    Comparacion dialog = new Comparacion(S[0], S[1]);
                    dialog.pack();
                    dialog.setVisible(true);
                    dialog.setMaximumSize(new Dimension(500, 500));

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (controlador_gestor_fitxer.FicheroCompressionNoValido | controlador_gestor_fitxer.FicheroDescompressionNoValido ficheroCompressionNoValido) {
                    JOptionPane.showMessageDialog(frame, ficheroCompressionNoValido.getMessage());
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
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

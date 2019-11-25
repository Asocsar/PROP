import Controlador_ficheros.controlador_gestor_fitxer;
import Controlador_Compressio_Descompressio.Cont_CD;
import Estadístiques.Estadistiques;
import Controlador_Estadistiques.Cont_Est;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

        public JFilePicker(String textFieldLabel, String buttonLabel) {
            this.textFieldLabel = textFieldLabel;
            this.buttonLabel = buttonLabel;

            fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            //fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            //fileChooser.removeChoosableFileFilter();

            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            // creates the GUI
            label = new JLabel(textFieldLabel);

            textField = new JTextField(30);
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

        public void selectmode (int n) {
            if (n == 0) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            }

            else {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            }
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

        public void addFileTypeFilter(String extension, String description) {
            FileTypeFilter filter = new FileTypeFilter(extension, description);
            fileChooser.addChoosableFileFilter(filter);
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public String getSelectedFilePath() {
            return textField.getText();
        }

        public JFileChooser getFileChooser() {
            return this.fileChooser;
        }

        public JFilePicker change (String s1, String s2) {
            return new JFilePicker(s1,s2);
        }
    }

    public static class FileTypeFilter extends FileFilter {

        private String extension;
        private String description;

        public FileTypeFilter(String extension, String description) {
            this.extension = extension;
            this.description = description;
        }

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            return file.getName().toLowerCase().endsWith(extension);
        }

        public String getDescription() {
            return description + String.format(" (*%s)", extension);
        }
    }

    private JPanel Panel;
    private JButton Close_Button;
    private JTextPane textPane2;
    private JButton Sortir;
    private JTextPane textPane1;
    private JFilePicker Picker1;
    private JFilePicker Picker2;
    private JRadioButton LZW;
    private JRadioButton LZSS;
    private JRadioButton LZ78;
    private JRadioButton JPEG;
    private JRadioButton radioButton1;
    private static JFrame frame;
    /* 0 : LZW
    *  1 : LZSS
    *  2 : LZ78
    *  3 : JPEG*/
    private static int state = 0;
    /* 0 : File
    *  1 : Directory*/
    private static int metodo = 0;
    private static String pos = ".txt";
    private static String Sec = "TXT Files";
    private JRadioButton [] L = new JRadioButton[] {LZW, LZSS, LZ78, JPEG};

    private void createUIComponents () {
        Picker1 = new JFilePicker("Selecció", "Busca");
        Picker1.selectmode(state);
        Picker1.setMode(JFilePicker.MODE_SAVE);

        Picker2 = new JFilePicker("Selecció", "Busca");
        Picker2.selectmode(1);
        Picker2.setMode(JFilePicker.MODE_SAVE);

        if (state == 0)
            Picker1.addFileTypeFilter(pos, Sec);
    }

    public void changeFilter () {
        if (metodo == 3) {
            System.out.println("HOla");;
            pos = ".ppm";
            Sec = "PPM Files";
        } else {
            pos = ".txt";
            Sec = "TXT Files";
        }
        Picker1 = Picker1.change(pos, Sec);
    }

    public Interfaz() {
        ButtonGroup group = new ButtonGroup();
        group.add(LZ78);
        group.add(LZW);
        group.add(LZSS);
        group.add(JPEG);
        L[metodo].doClick();
        Sortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

            }
        });

        LZW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 0;
                changeFilter();
                frame.setContentPane(new Interfaz().Panel);
            }
        });

        LZSS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 1;
                changeFilter();
                frame.setContentPane(new Interfaz().Panel);
            }
        });

        LZ78.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 2;
                changeFilter();
                frame.setContentPane(new Interfaz().Panel);
            }
        });

        JPEG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 3;
                changeFilter();
                frame.setContentPane(new Interfaz().Panel);
            }
        });

    }

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setContentPane(new Interfaz().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}

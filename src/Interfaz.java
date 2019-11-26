
import Controlador_Compressio_Descompressio.Cont_CD;


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

        public void selectmode (int n) {
            if (n == 1) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            }
            else {
                if (metodo == 1 && state == 0) {
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                } else {
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                }
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

        public void removefilter () {
            fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
            textField.setText("");
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
    private JTextPane textPane2;
    private JButton Sortir;
    private JTextPane textPane1;
    private JFilePicker Picker1;
    private JFilePicker Picker2;
    private JRadioButton LZW;
    private JRadioButton LZSS;
    private JRadioButton LZ78;
    private JRadioButton JPEG;
    private JRadioButton Fitxer;
    private JRadioButton Carpeta;
    private JRadioButton Compress;
    private JRadioButton Descompress;
    private JButton Action;
    private JButton Compare;
    private static JFrame frame;
    /* 0 : LZW
    *  1 : LZSS
    *  2 : LZ78
    *  3 : JPEG*/
    private static int state = 0;
    /* 0 : File
    *  1 : Directory*/
    private static int metodo = 0;
    /* 0 : Compression
    *  1 : Descompression*/
    private static int compresion = 0;
    private static String pos = ".txt";
    private static String Sec = "TXT Files";
    private JRadioButton [] L = new JRadioButton[] {LZW, LZSS, LZ78, JPEG};
    private JRadioButton [] J = new JRadioButton[] {Fitxer, Carpeta};
    private JRadioButton [] K = new JRadioButton[] {Compress, Descompress};

    private void createUIComponents () {
        Picker1 = new JFilePicker("Selecció", "Busca");
        Picker1.selectmode(-1);
        Picker1.setMode(JFilePicker.MODE_SAVE);

        Picker2 = new JFilePicker("Selecció", "Busca");
        Picker2.selectmode(1);
        Picker2.setMode(JFilePicker.MODE_SAVE);

        if (state == 0)
            Picker1.addFileTypeFilter(pos, Sec);
    }

    public void changeFilter () {
        if (metodo == 3) {
            if (compresion == 0) {
                pos = ".ppm";
                Sec = "PPM Files";
            }
            else  {
                if (state == 0) {
                    pos = ".fG";
                    Sec = "PPM Compressed-Files";
                }
                else {
                    pos = ".FG";
                    Sec = "PPM Compressed-Folder";
                }
            }
        } else {
            if (compresion == 0) {
                pos = ".txt";
                Sec = "TXT Files";
            }
            else {
                if (state == 0) {
                    if (metodo == 0) pos = ".fW";
                    else if (metodo == 1) pos = ".fS";
                    else if (metodo == 2) pos = ".f8";
                    else pos = ".fG";
                    Sec = "TXT Compressed File";
                }
                else {
                    if (metodo == 0) pos = ".FW";
                    else if (metodo == 1) pos = ".FS";
                    else if (metodo == 2) pos = ".F8";
                    else pos = ".FG";
                    Sec = "TXT Compressed Folder";
                }
            }
        }
        Picker1.addFileTypeFilter(pos,Sec);
    }

    public void actPickers() {
        Picker1.selectmode(-1);
        Picker1.updateUI();
        Picker1.removefilter();
        changeFilter();
    }

    public Interfaz() {
        ButtonGroup group1 = new ButtonGroup();
        group1.add(LZ78);
        group1.add(LZW);
        group1.add(LZSS);
        group1.add(JPEG);
        ButtonGroup group2 = new ButtonGroup();
        group2.add(Compress);
        group2.add(Descompress);
        ButtonGroup group3 = new ButtonGroup();
        group3.add(Fitxer);
        group3.add(Carpeta);
        if (!L[metodo].isSelected()) L[metodo].doClick();
        if (!J[state].isSelected()) J[state].doClick();
        if (!K[compresion].isSelected()) K[compresion].doClick();
        Sortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        Action.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cont_CD C = new Cont_CD();
                C.compressio_descompressio(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), metodo, compresion == 0);
            }
        });

        Compare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cont_CD C = new Cont_CD();
                String[] S = C.comparar();
            }
        });

        Carpeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = 1;
                actPickers();
                //frame.setContentPane(new Interfaz().Panel);
            }
        });

        Fitxer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = 0;
                actPickers();
                //frame.setContentPane(new Interfaz().Panel);

            }
        });

        Descompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compresion = 1;
                actPickers();
            }
        });

        Compress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compresion = 0;
                actPickers();
            }
        });

        LZW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 0;
                actPickers();
            }
        });

        LZSS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 1;
                actPickers();
            }
        });

        LZ78.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 2;
                actPickers();
            }
        });

        JPEG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 3;
                actPickers();
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

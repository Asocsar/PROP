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
            //fileChooser.removeChoosableFileFilter();

            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            // creates the GUI
            label = new JLabel(textFieldLabel);

            textField = new JTextField(30);
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
    private static JFrame frame;
    /* 0 : LZW
    *  1 : LZSS
    *  2 : LZ78
    *  3 : JPEG*/
    private static int metodo;

    private void createUIComponents () {
        Picker1 = new JFilePicker("Selecció", "Busca");
        Picker1.setMode(JFilePicker.MODE_SAVE);
        Picker1.addFileTypeFilter(".ppm", "PPM Images");
        Picker1.addFileTypeFilter(".txt", "TXT Files");
        Picker2 = new JFilePicker("Selecció", "Busca");
        Picker2.setMode(JFilePicker.MODE_SAVE);
    }

    public Interfaz() {
        ButtonGroup group = new ButtonGroup();
        group.add(LZ78);
        group.add(LZW);
        group.add(LZSS);
        group.add(JPEG);
        LZW.doClick();
        metodo = 0;
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
            }
        });

        LZSS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 1;
            }
        });

        LZ78.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 2;
            }
        });

        JPEG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodo = 3;
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

/**
 * /file Interfaz.java
 * /author Daniel Cano Carrascosa
 * /title Algorisme LZSS
 */



import Controlador_Compressio_Descompressio.Cont_CD;
import Controlador_ficheros.controlador_gestor_fitxer;
import Estadístiques.Estadistiques;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class Interfaz extends JFrame  {



    /** \brief Clase JFilePicker
     \pre  Cert
     \post Definicio de la clase JFilePicker
     \details Personalització de un JPanel per ha escollir un fitxer o carpeta per a comprimir o descomprimir
     */
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

        /**
         * \brief Instanciació de JFilePicker
         * \pre  Cert
         * \post Crea una instancia de JFilePicker
         * \details S'inicialitzen tots els components necesàris
         */
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
            this.button.setBackground(Color.decode("#F0F6F7"));

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

        /**
         * \brief Estableix mode de selecció
         * \pre  Cert
         * \post S'obre una ventana per seleccionar el fitxer y desa el path seleccionat en un textField
         * \details Hi ha dos modes de seleccio.
         */
        private void buttonActionPerformed(ActionEvent evt) {
            if (mode == MODE_OPEN) {
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            } else if (mode == MODE_SAVE) {
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    String p = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!cf.path_valid(p)) {
                        JOptionPane.showMessageDialog(frame, "La direcció introduida ha de ser path exisistent o vàlid");
                    }
                    else
                        textField.setText(p);

                }
            }
        }

        /**
         * \brief Afegeix un filtre
         * \pre  Cert
         * \post El component FilePicker obté filtrés, per acceptar unicament uns fitxers en concret
         * \details
         */
        public void addFileTypeFilter(String[] e, String description) {
            FileTypeFilter filter = new FileTypeFilter(e, description);
            fileChooser.addChoosableFileFilter(filter);

        }

        /**
         * \brief Cambiem el mode de seleccio
         * \pre  Cert
         * \post Cambia la variable mode
         * \details Es pot cambiar entre el mode SAVE i OPEN
         */
        public void setMode(int mode) {
            this.mode = mode;
        }


        /**
         * \brief Obtenim el path seleccionat
         * \pre  Cert
         * \post Retorna el path seleccionat
         * \details
         */
        public String getSelectedFilePath() {
            return textField.getText();
        }


        /**
         * \brief Obtenim el TextField
         * \pre  Cert
         * \post Retorna el parametre JTextField
         * \details
         */
        public JTextField gettextf() {
            return textField;
        }

        /**
         * \brief Obtenim el filechooser
         * \pre  Cert
         * \post Retorna el parametre JfileChooser
         * \details
         */
        public JFileChooser getFileChooser() {
            return this.fileChooser;
        }
    }


    /** \brief Clase FileTypeFilter
     \pre  Cert
     \post Definicio de la clase FileTypeFIlter
     \details Creació de la clase FileTypeFIlter
     */
    public static class FileTypeFilter extends FileFilter {

        private String[] extension;
        private String description;


        /** \brief Instanciacio de la clase FileTypeFilter
         \pre  Cert
         \post S'inicializen els parametres de la clase
         \details El parametre extension conté els elements a acceptar y la descripció conté un missatge personalitzat
         */
        public FileTypeFilter(String[] extension, String description) {
            this.extension = extension;
            this.description = description;
        }

        /** \brief Filtre per acceptar o denegar elements
         \pre  Cert
         \post Retorna cert si l'arxiu es troba o no en el filtre
         \details El parametre local extension es que condiciona si el fitxer es  o no acceptat
         */

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

        /** \brief Descripció asociada a un filtre
         \pre  Cert
         \post Retorna la descripció asociada a un filtre concret
         \details
         */
        public String getDescription() {
            return description + Arrays.toString(extension);
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
    private JTextField qualitatDImatgeTextField;
    private JProgressBar progressBar1;
    private JTextField analitzantFitxerTextField;
    private static JFrame frame;
    private static int metodo = 0;
    private boolean directorio = false;
    private boolean image = false;
    private static Cont_CD cont = new Cont_CD();
    private static controlador_gestor_fitxer cf = new controlador_gestor_fitxer();
    private static HashMap<Integer, String> M = new HashMap<Integer, String>();
    private static Map<String, List<String>> Asoc = cont.getAsoc();
    private List<Comparacion> compL = new ArrayList<>();


    /** \brief Creació de components personalizats
     \pre  Cert
     \post Crea els components personalitzats en la Interficie principal
     \details
     */
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


    /** \brief Listener per al frame principal
     \pre  Cert
     \post Cada vegada que la mida del frame principal sigui modificada, la grandaria dels JFilePIcker s'adapta
     \details Creació de la clase FileTypeFIlter
     */
    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            // Recalculate the variable you mentioned
            Picker1.gettextf().setColumns(frame.getSize().width/20);
            Picker2.gettextf().setColumns(frame.getSize().width/20);
        }
    }


    /** \brief Oculta el slider de qualitat y estableix el algoritme de compressio
     \pre  Cert
     \post Cambio l'algorisme que s'utilitzarà  a LZW y oculta la barra
     \details
     */
    public void LZW_option() {
        metodo = 0;
        qualitatDImatgeTextField.setVisible(false);
        slider1.setVisible(false);
        slider1.setMaximumSize(new Dimension(0,0));
    }

    /** \brief Oculta el slider de qualitat y estableix el algoritme de compressio
     \pre  Cert
     \post Cambio l'algorisme que s'utilitzarà a LZSS y oculta la barra
     \details
     */
    public void LZSS_option() {
        metodo = 1;
        qualitatDImatgeTextField.setVisible(false);
        slider1.setVisible(false);
        slider1.setMaximumSize(new Dimension(0,0));
    }

    /** \brief Oculta el slider de qualitat y estableix el algoritme de compressio
     \pre  Cert
     \post Cambio l'algorisme que s'utilitzarà a LZ78 y oculta la barra
     \details
     */
    public void LZ78_option() {
        metodo = 2;
        qualitatDImatgeTextField.setVisible(false);
        slider1.setVisible(false);
        slider1.setMaximumSize(new Dimension(0,0));
    }

    /** \brief Mostra el slider de qualitat y estableix el algoritme de compressio
     \pre  Cert
     \post Cambio l'algorisme que s'utilitzarà a jPEG y mostra la barra
     \details
     */
    public void JPEG_option() {
        metodo = 3;
        qualitatDImatgeTextField.setVisible(true);
        slider1.setVisible(true);
        slider1.setMaximumSize(new Dimension(30,50));
    }

    /** \brief Instancia la ventana principal
     \pre  Cert
     \post S'inicialitzen totes les variables de control i components necesaris
     \details
     */
    public Interfaz()  {
        Estadistiques.inicialitzar();
        progressBar1.setVisible(false);
        analitzantFitxerTextField.setVisible(false);
        analitzantFitxerTextField.setBorder(BorderFactory.createEmptyBorder());
        qualitatDImatgeTextField.setBorder(BorderFactory.createEmptyBorder());
        qualitatDImatgeTextField.setVisible(false);
        Map<String, List<Double>> MP = Estadistiques.getparam();
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
        Compare.setBorder(BorderFactory.createEtchedBorder());
        Accion.setBorder(BorderFactory.createEtchedBorder());
        Sortir.setBorder(BorderFactory.createEtchedBorder());
        Globales.setBorder(BorderFactory.createEtchedBorder());

        /** \brief Implementa el comportament del boto de toncar l'aplicació
         \pre  No hi ha cap procés de compressio o descompressió actiu ni tampoc cap carrega de textos o imatges per comparar
         \post Totes les vistes de l'aplicació es tanquen i el programa finalitza
         \details
         */
        Sortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estadistiques.finalitza();
                for (Comparacion c : compL) c.dispose();
                frame.dispose();
            }
        });


        /** \brief Actualitza el botó de comprimir y l'algoritme utilitzat
         \pre  Cert
         \post El boto cambia d'estat i es selecciona l'algorisme
         \details Quan un fitxer es seleccionat, el boto pasa a tenir el text compressio o descompressio
         \ depenent de en quin fitxer haguem escollit i a part d'això s'actualitza els algotimes disponibles
         \ per comprimir i descomprimir i en cas del JPEG es mostra un slider que indica qualitat, altrament aquest slider
         \ s'amaga.
         */
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
                    directorio = !cf.dir_or_arch(p);
                    for (ActionListener a : comboBox1.getActionListeners()) comboBox1.removeActionListener(a);
                    comboBox1.removeAllItems();
                    comboBox1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String S = (String) comboBox1.getSelectedItem();
                            switch (S) {
                                case "LZW":
                                    LZW_option();
                                    break;
                                case "LZ78":
                                    LZ78_option();
                                    break;
                                case "LZSS":
                                    LZSS_option();
                                    break;
                                default:
                                    JPEG_option();
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
                    } else {
                        boolean comprimir = cf.a_comprimir(p);
                        String substring = cf.get_ext_file(p);
                        if (comprimir) {
                            Accion.setText("Comprimir");
                            List<String> S = new ArrayList<>();

                            for (String Alg : Asoc.keySet()) {
                                if (Asoc.get(Alg).contains(substring)) {
                                    comboBox1.addItem(Alg);
                                }
                            }
                            comboBox1.setSelectedIndex(0);
                        } else {
                            Accion.setText("Descomprimir");
                            comboBox1.removeAllItems();
                            qualitatDImatgeTextField.setVisible(false);
                            slider1.setVisible(false);
                            slider1.setMaximumSize(new Dimension(0, 0));

                        }
                    }
                }
        });

        /** \brief Comprimeix o Descomprimex el fitxer o carpeta seleccionat
         \pre  Cert
         \post Comprimeix o Descomprimex el fitxer o carpeta seleccionat
         \details En cas de comprimir una carpeta amb fitxers no compatibles s'enviara un avís
         \demanant a l'usuari que confirmi la seva elecció
         */
        Accion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cont_CD C = new Cont_CD();
                if (Accion.getText().equals("Comprimir/Descomprimir")) {
                    JOptionPane.showMessageDialog(frame, "Escoge antes un Elemento a comprimir o descomprimir");

                } else {
                    try {
                        if (!directorio && !cf.carpeta_des(Picker1.getSelectedFilePath()))
                            if (Accion.getText().equals("Comprimir")) {
                                int n = C.compressio_fitxer(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), M.get(metodo), false);
                                if (n != -1) image = (n == 1);
                                else {
                                    String message = "Hi ha un fitxer comprimit amb el mateix nom en aquest directori, si vols continuar aquest fitxer es sobreescriurà\n\n Vols continuar ?";
                                    int i = 0;
                                    String title = "Avís";
                                    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                                    if (reply == JOptionPane.YES_OPTION) {
                                        C.compressio_fitxer(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), M.get(metodo), true);
                                    }
                                }
                            }
                            else {
                                int n = C.descompressio_fitxer(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), false);
                                if (n == -1) {
                                    String message = "Ja existeix un fitxer amb aquest nom en el directori actual de continuar el seu contingut serà sobreescrit\n\n Vols continuar ?";
                                    int i = 0;
                                    String title = "Avís";
                                    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                                    if (reply == JOptionPane.YES_OPTION) {
                                        C.descompressio_fitxer(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), true);
                                    }
                                }
                            }
                        else if (Accion.getText().equals("Comprimir")) {
                            boolean force1 = false;
                            boolean force2 = false;
                            int auxiliar = -111;
                            List<String> N = C.compressio_carpeta(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), M.get(metodo), force1, force2);
                            if (N.size() >= 1 && N.get(0).equals("-1")) {
                                String message = "S'ha trobat un fitxer amb el mateix nom que la carpeta, si continua amb el procés el contingut del fitxer serà substituit\n\n Vol continuar ?";
                                String title = "Avís";
                                int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                                auxiliar = reply;
                                if (reply == JOptionPane.YES_OPTION) {
                                    force1 = true;
                                    N = C.compressio_carpeta(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), M.get(metodo), force1,force2);
                                }
                            }
                            if (N.size() >= 1 && !N.get(0).equals("0") && auxiliar == JOptionPane.YES_OPTION) {
                                String message = "Si continues amb la compressió els següents arxius seran ignorats\n\n";
                                int i = 0;
                                for (; (i < N.size()) && (i < 15); ++i) message += N.get(i) + "\n";
                                if (i == 15 && N.size() > 15) message += "I uns altres " + (N.size()-15) + " fitxers";
                                String title = "Avís";
                                int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                                if (reply == JOptionPane.YES_OPTION) {
                                    force2 = true;
                                    force1 = true;
                                    C.compressio_carpeta(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), M.get(metodo), force1,force2);
                                }
                            }
                        }
                        else {
                            if (! C.descompressio_carpeta(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), false)) {
                                String message = "Hi ha una carpeta amb el mateix nom, si vol continuar la carpeta serà eliminada y substituida per la que s'esta descomprimint\n\n Vol continuar?";
                                String title = "Avís";
                                int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                                if (reply == JOptionPane.YES_OPTION) {
                                    C.descompressio_carpeta(Picker1.getSelectedFilePath(), Picker2.getSelectedFilePath(), true);
                                }
                            }
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error inesperado, por favor vuelva a intentar");
                    } catch (controlador_gestor_fitxer.FicheroCompressionNoValido | controlador_gestor_fitxer.FicheroDescompressionNoValido | Cont_CD.NoFiles ficheroCompressionNoValido) {
                        JOptionPane.showMessageDialog(frame, ficheroCompressionNoValido.getMessage());
                    }
                }
            }
        });

        /** \brief Compara la última compressió executada
         \pre  Cert
         \post Compara la última compressió executada
         \details Compara la ultima compressió realitzada i en cas de que aquesta sigui un fitxer
         \ es mostrara el text original i el comprimit (descomprimint aquest de forma temporal per a que així l'usuari)
         \ pugui veure el resultat de la seva compressió, el mateix amb la imatge
         */
        Compare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cont_CD C = new Cont_CD();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String[] S = C.comparar();
                            analitzantFitxerTextField.setVisible(true);
                            progressBar1.setVisible(true);
                            Comparacion dialog = new Comparacion(S[0], S[1], Cont_CD.getlastjpeg(), progressBar1, analitzantFitxerTextField);
                            compL.add(dialog);
                            //dialog.pack();
                            dialog.setVisible(true);
                            dialog.setMaximumSize(new Dimension(500, 500));
                        } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error imprevist a l'hora de comparar, siusplau torni a intentar-ho");
                        } catch (controlador_gestor_fitxer.FicheroDescompressionNoValido | controlador_gestor_fitxer.FicheroCompressionNoValido | Cont_CD.NoCompress ficheroDescompressionNoValido) {
                            JOptionPane.showMessageDialog(frame, ficheroDescompressionNoValido.getMessage());
                        }
                    }
                });
            }
        });


        /** \brief Mostra les estadístiques globals
         \pre  Cert
         \post Mostra les estadístiques globals
         \details Mostra les estadístiques globals de tots els algoritmes, en cas de que
         \ es vulguin veure totes de forma simultanea l'últim algoritme utilitzat apareixerà
         \ amb un color diferent a la resta per indicar visualment quin es aquest
         */
        Globales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estadisticas E = new Estadisticas(Asoc.keySet());
                E.pack();
                E.setVisible(true);
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

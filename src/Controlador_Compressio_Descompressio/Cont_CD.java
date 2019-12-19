package Controlador_Compressio_Descompressio;


import Algoritmes.*;
import Controlador_ficheros.controlador_gestor_fitxer;
import Estadístiques.Estadistiques;
import javafx.scene.shape.Path;
//import javafx.beans.property.StringPropertyBase;


import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Cont_CD {

    private static String path1 = "";
    private static String path2 = "";
    private static double time;
    private static double rate;
    private static String id;
    private Map<String, List<String>> Asoc = new HashMap<String,List<String>>();
    private List<String> Alg = new ArrayList<>();
    private static boolean jpeg = false;

    public class NoCompress extends Exception {
        public NoCompress (String message) {super(message);}
    }

    public class NoFiles extends Exception {
        public NoFiles (String message) {super (message);}
    }
    /** \brief Creadora
     \pre Cert
     \post S'ha creat una instancia inicialitzada del Controlador CD y s'han asociat els algoritmes que aquesta implementa
     \amb les extensions obtenides de la capa de persistència
     */

    public Cont_CD () {
        controlador_gestor_fitxer c = new controlador_gestor_fitxer();
        Alg.add("LZW");
        Alg.add("LZ78");
        Alg.add("LZSS");
        Alg.add("JPEG");
        List<String> Ext = Arrays.asList(c.extensiones_validas());
        for (String Ex : Alg) {
            if (!(Ex.charAt(1) == 'f')) {
                String ch = Ex.charAt(Ex.length() - 1) + "";
                Predicate<String> a = String -> String.contains(ch.toUpperCase());
                List<String> fil = new ArrayList<>();
                if (!Ex.equals("JPEG")) fil.add(".txt");
                if (Ex.equals("JPEG")) fil.add(".ppm");
                else fil.add("folder");
                fil.addAll(Ext.stream().filter(a).collect(Collectors.toList()));
                Asoc.put(Ex, fil);
            }
        }
    }

    /** \brief Asociació path-algorismes
     \pre Cert
     \post S'han associat els paths amb els corresponents algorismes
     */

    public Map<String,List<String >> getAsoc () {return this.Asoc;}

    /** \brief Temps
     \pre Cert
     \post S'ha retornat el temps de l'última execució
     */

    public double getTime () {return this.time;}

    /** \brief Ratio de compressió
     \pre Cert
     \post S'ha retornat el ratio de compressió de l'última execució
     */

    public double getRate () {return this.rate;}


    /** \brief Si l'últim algorisme utilitzat ha sigut el jpeg
     \pre Cert
     \post Es retorna un bolea que indica si l'últim algorisme executat ha sigut el jpeg
     */
    public static boolean getlastjpeg () {return jpeg;}


    /**\brief Acció
     \pre cert
     \post Retorna el resultat d'aplicar compressió o descompressió d'algun algorisme
     */
    private  byte[] action (String path_o, String id, boolean comprimir, controlador_gestor_fitxer I, int bytes) throws controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido, IOException {
        byte[] L = null;
        byte[] b = null;
        Estadistiques E = new Estadistiques();
        if (bytes != -1)
            b = I.read_file_compressed(bytes, path_o);
        else
            b = I.get_buffer(path_o, comprimir, id);
        switch (id) {
            // Es decideix quin algorisme utilitzar i quina accio pendre
            case "LZ78":
                LZ78 L8 = new LZ78();
                if (comprimir) {
                    System.out.println("LZ78 compression ejecutado");
                    L = L8.compress(b);
                    //s'actualitzen les estadístiques i es guarda temps i rati
                    time = L8.get_Time();
                    rate = L8.get_Rate();
                    E.act8(time, rate, (time != 0) ? b.length/time : 0, true);
                } else {
                    System.out.println("LZ78 descompression ejecutado");
                    L = L8.descompress(b);
                    time = L8.get_Time();
                    E.act8(time, -1, (time != 0) ? b.length/time : 0, false);
                }

                break;
            case "LZSS":
                LZSS LS = new LZSS();
                if (comprimir) {
                    System.out.println("LZSS compression ejecutado");
                    L = LS.compress(b);
                    time = LS.getTime();
                    rate = LS.getRate();
                    E.actS(time, rate, (time != 0) ? b.length/time : 0, true);
                } else {
                    System.out.println("LZSS descompression ejecutado");
                    L = LS.descompress(b);
                    time = LS.getTime();
                    E.actS(time, -1, (time != 0) ? b.length/time : 0, false);
                }
                break;
            case "LZW":
                LZW LW = new LZW();
                if (comprimir) {
                    System.out.println("LZW compression ejecutado");
                    L = LW.compress(b);
                    time = LW.getTime();
                    rate = LW.getRate();
                    E.actW(time, rate, (time != 0) ? b.length/time : 0, true);

                } else {
                    System.out.println("LZW descompression ejecutado");
                    L = LW.descompress(b);
                    time = LW.getTime();
                    E.actW(time, -1, (time != 0) ? b.length/time : 0, false);

                }

                break;
            default:
/*
                JPEG JG = new JPEG();
                if (comprimir) {
                    System.out.println("JPEG compression ejecutado");
                    L = JG.compress(I.get_buffer(path_o, comprimir, id));
                    time = JG.;
                    rate = JG.getRate();
                    E.actG(time,rate);
                }
                else {
                    System.out.println("JPEG descompression ejecutado");
                    L = JG.descomprimir(I.get_buffer(path_o, comprimir, id));
                    time = JG.getTime();
                }
                break;*/
        }
        return  L;
    }

    /**\brief Compressió de fitxers
     \pre path_o ha de ser vàlid
     \post Comprimeix el fitxer situat al path_o i el desa al path_d
     */
    public int compressio_fitxer (String path_o, String path_d, String algoritme, boolean force) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        id = algoritme;
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        byte[] L = action(path_o, id, true, I, -1);
        if (algoritme.equals("JPEG")) jpeg = true;
        Path p = new Path();
        boolean seguir = I.writeFile(L, path_d, force);
        if (!seguir && !force) return -1;
        path1 = path_o;
        path2 = I.getPath_absoluto();
        if (I.is_jpeg(path_o)) return  1;
        else return  0;
    }

    /**\brief Compressió de carpetes
     \pre path_o ha de ser vàlid
     \post Comprimeix la carpeta situat al path_o i el desa al path_d
     * @return
     */
    public List<String> compressio_carpeta (String path_o, String path_d, String id, boolean force1, boolean force2) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido, NoFiles {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        List<String> a = I.get_paths_carpeta(path_o, path_d, id, force1);
        if (a.size() == 0) throw new NoFiles("No hi ha arxius, per tant s'avorta el procés de compressió");
        if (a.get(0).equals("-1") && !force1) return a;
        List<String> basura = new ArrayList<String>(1);
        basura.add("0");
        if (I.getPaths_no_valids().size() > 0 && !force2) return I.getPaths_no_valids();
        for (String s : a) {
            boolean jpeg = I.is_jpeg(s);
            String algoritmo_utilizado = id;
            if (jpeg) algoritmo_utilizado = "JPEG";
            byte[] aux = action(s, algoritmo_utilizado, true, I, -1);
            I.write_c_folder(s, aux);
        }
        return basura;
    }


        /**\brief Descompressió de carpetes
         \pre path_o ha de ser vàlid
         \post Descomprimeix la carpeta situat al path_o i el desa al path_d
         */
        public boolean descompressio_carpeta (String path_o, String path_d, boolean force) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
            controlador_gestor_fitxer I = new controlador_gestor_fitxer();
            I.reset_bytes_llegits();
            String algoritmo_usado = I.getAlgoritme(path_o);
            String path_fitxer_carpeta_comprimida = I.read_path(path_o);
            String path_destino_carpeta = I.path_dest_carpeta(path_o, path_d,force);
            if (path_destino_carpeta == null && !force) return false;
            int numerodeficheros = I.read_tamany(path_o);
            for (int i = 0; i < numerodeficheros; ++i) {
                String pathdelfichero = I.read_path(path_o);
                if (I.is_jpeg(pathdelfichero)) algoritmo_usado = "JPEG";
                int bytesfichero = I.read_tamany(path_o);
                byte[] fdescomprimit = action(path_o, algoritmo_usado, false, I, bytesfichero);
                I.write_fitxer_carpeta_desc(path_fitxer_carpeta_comprimida, path_destino_carpeta, pathdelfichero, fdescomprimit);
            }
            return true;
        }

        /**\brief Descompressió
         \pre path_o ha de ser vàlid
         \post Descomprimeix el fitxer situat al path_o i el desa al path_d
         */
        public int descompressio_fitxer (String path_o, String path_d, boolean force) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
            controlador_gestor_fitxer I = new controlador_gestor_fitxer();
            id = I.getAlgoritme(path_o);
            Object L = action(path_o, id, false, I, -1);
            boolean a = I.writeFile(L, path_d, force);
            if (!a && !force) return -1;
            else return 0;
        }




        /** \brief Comparar
         \pre : Hi ha hagut com a mínim una compressió des de que s'ha iniciat el programa
         \post: Mostra el contingut del fitxer original i el resultat després d'haver comprimit aquest
         */
        public String [] comparar() throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido, NoCompress {
            String [] K = new String[2];
            if (path1.equals("")) {
                // AQUI TENGO QUE PONER UNA EXCEPCION QUE UN NO HE HECHO
                throw new NoCompress("Debe realizar una compressión antes de intentar comparar");
            }
            else {
                if (jpeg) {
                    K[0] = path1;
                    K[1] = path2;
                }
                else {
                    controlador_gestor_fitxer I = new controlador_gestor_fitxer();
                    //llegim el contingut del fitxer original
                    String S = I.obtenir_fitxer(path1);
                    K[0] = S;
                    //descomprimir el contingut del fitxer comprimit i el mostrem
                    byte[] L = (byte[]) action(path2, id, false, I, -1);
                    K[1] = I.compare(L, id);
                }
            }
            return  K;
        }
    }
package Controlador_Compressio_Descompressio;


import Algoritmes.*;
import Controlador_ficheros.controlador_gestor_fitxer;
import Estadístiques.Estadistiques;
import javafx.beans.property.StringPropertyBase;


import javax.swing.text.StringContent;
import java.awt.datatransfer.StringSelection;
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

    public Cont_CD () {
        controlador_gestor_fitxer c = new controlador_gestor_fitxer();
        Alg.add("LZW");
        Alg.add("LZ78");
        Alg.add("LZSS");
        Alg.add("JPEG");
        List<String> Ext = Arrays.asList(c.extensiones_validas());
        for (String Ex : Alg) {
            String ch = Ex.charAt(Ex.length()-1)+"";
            Predicate<String> a = String -> String.contains(ch.toUpperCase());
            List<String> fil = new ArrayList<>();
            fil.add(".txt");
            if (!Ex.equals("JPEG")) fil.add(".ppm");
            fil.addAll(Ext.stream().filter(a).collect(Collectors.toList()));
            Asoc.put(Ex, fil);
        }
    }

    public Map<String,List<String >> getAsoc () {return this.Asoc;}
    public double getTime () {return this.time;}
    public double getRate () {return this.rate;}


    // Pre : Cert
    // Post: Retorna el resultat d'aplicar compressió o descompressió d'algun algorisme
    private  byte[] action (String path_o, String id, boolean comprimir, controlador_gestor_fitxer I) throws controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido, IOException {
        byte[] L = null;
            Estadistiques E = new Estadistiques();
            byte [] b = I.get_buffer(path_o, comprimir, id);
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
                        E.act8(time, rate);
                    } else {
                        System.out.println("LZ78 descompression ejecutado");
                        L = L8.descompress(b);
                        time = L8.get_Time();
                    }

                    break;
                case "LZSS":
                    LZSS LS = new LZSS();
                    if (comprimir) {
                        System.out.println("LZSS compression ejecutado");
                        L = LS.compress(b);
                        time = LS.getTime();
                        rate = LS.getRate();
                        E.actS(time, rate);
                    } else {
                        System.out.println("LZSS descompression ejecutado");
                        L = LS.descompress(b);
                        time = LS.getTime();
                        E.actS(time, -1);
                    }
                    break;
                case "LZW":
                    LZW LW = new LZW();
                    if (comprimir) {
                        System.out.println("LZW compression ejecutado");
                        L = LW.compress(b);
                        time = LW.getTime();
                        rate = LW.getRate();
                        E.actW(time, rate);

                    } else {
                        System.out.println("LZW descompression ejecutado");
                        L = LW.descompress(b);
                        time = LW.getTime();

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

    // Pre: Cert
    // Post: Comprimeix el fitxer situat al path_o i el desa al path_d
    public void compressio_fitxer (String path_o, String path_d, String algoritme) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        id = algoritme;
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        byte[] L = action(path_o, id, true, I);
        path1 = path_o;
        path2 = path1.substring(0, path1.length()-4) + ".fW";
        I.writeFile(L, path_o, path_d);
    }

    // Pre: Cert
    // Post: Comprimeix el fitxer situat al path_o i el desa al path_d
    public void compressio_carpeta (String path_o, String path_d, String algoritme) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        id = algoritme;
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        I.folder(path_o);
        while (I.resten()) {
            Object L = action(path_o, id, true, I);
            I.writeFile(L, path_o, path_d);
        }
    }


    // Pre: Cert
    // Post: Descomprimeix el fitxer situat al path_o i el desa al path_d
    public void descompressio_fitxer (String path_o, String path_d) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        id = I.getAlgoritme(path_o);
        Object L = action(path_o, id, false, I);
        I.writeFile(L, path_o, path_d);
    }

    // Pre: Cert
    // Post: Descomprimeix el fitxer situat al path_o i el desa al path_d
    public void descompressio_carpeta (String path_o, String path_d) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        id = I.getAlgoritme(path_o);
        Object L = action(path_o, id, false, I);
        I.writeFile(L, path_o, path_d);
    }




    // Pre : Hi ha hagut com a mínim una compressió des de que s'ha iniciat el programa
    // Post: Mostra el contingut del fitxer original i el resultat després d'haver comprimit aquest
    public String [] comparar() throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        String [] K = new String[2];
        if (path1.equals("")) {
            // AQUI TENGO QUE PONER UNA EXCEPCION QUE UN NO HE HECHO
        }
        else {
            controlador_gestor_fitxer I = new controlador_gestor_fitxer();
            //llegim el contingut del fitxer original
            String S = I.obtenir_fitxer(path1);
            K[0] = S;
            //descomprimir el contingut del fitxer comprimit i el mostrem
            byte[] L = (byte[]) action(path2, id, false, I);
            K[1] = I.compare(L, id);
        }
        return  K;
    }
}

package Estadístiques;


import Controlador_Estadistiques.Cont_Est;

import java.io.IOException;
import java.util.*;

public class Estadistiques {

    private static String[] noms_est = {"Temps de l'última compressió", "Temps de l'última descompressió" ,"Ratio de l'última compressió", "Velocitat de compressió",
            "Temps de compressió global", "Temps de descompressió global",  "Ratio de compressió global", "Número de compressions" ,
            "Número de descompressions"};
    /**< Noms de les estadístiques : Les 9 posicions es corresponen a :  0/ Temps de l'última compressió. 1/Temps de l'última descompressió.
     * 2/Ratio de l'última compressió.. 3/Velocitat de compressió.  4/Temps de compressió global. 5/ Temps de descompressió global
     * 6/Ratio de compressió global. 7/Número de compressions. 8/Número de descompressions. */

    private static Map<String,List<Double>> est = new HashMap<String, List<Double>>(); /**<Estadístiques Globals : les estadistiques de cada algorisme es troben contenides en una List<Double>, identificat amb al seu nom en un Map.
     * El nom de l'estadística en la posició i-èssima es correspon amb el nom contingut en la posició i-èssima de l'String[] "noms_est". També conté com a clau l'últim algorisme usat, acopmanyat de l'id d'aquest*/

    public static String[] nom_atr () {return noms_est;}
    public static  String [] asociacio_algoritmes () {return new String[] {"LZW", "LZ78", "LZSS", "JPEG"};}

    public static Map<String, List<Double>> getparam () {return est;}

    public static void inicialitzar ()  {
        try {
            Cont_Est C = new Cont_Est();
            C.GetStats();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void finalitza ()  {
        try {
            Cont_Est C = new Cont_Est();
            C.Stats_Update();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***************************
     ********** LZW ************
     ***************************/

    /** \var LLista amb les estadístiques d'execució amb LZW*/
    private static List<Double> lzw  = new ArrayList<Double>() {{
        for (int i = 0; i < 9; ++i) add(0.0);

    }};

    /** \brief Obtenció d'estadístiques LZW
     \pre  Cert
     \post Es retornen totes les estadístiques de LZW
     */

    public static List<Double> getstatsLZW(){
        List<Double> tmpstats = new ArrayList<>();
        tmpstats.add(lzw.get(0));
        tmpstats.add(lzw.get(1));
        tmpstats.add(lzw.get(2));
        tmpstats.add(lzw.get(3));
        if (lzw.get(7) != 0) tmpstats.add(lzw.get(4)/lzw.get(7)); else tmpstats.add(0.0);
        if (lzw.get(8) != 0) tmpstats.add(lzw.get(5)/lzw.get(8)); else tmpstats.add(0.0);
        if (lzw.get(7) != 0) tmpstats.add(lzw.get(6)/lzw.get(7)); else tmpstats.add(0.0);
        tmpstats.add(lzw.get(7));
        tmpstats.add(lzw.get(8));
        return tmpstats;
    }

    /** \brief Recuperacio estadistiques
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de LZW
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */

    public static void setLZW(List<Double> estlzw){
        for (int i = 0; i < 9; ++i) lzw.set(i,estlzw.get(i));
        est.put("LZW",lzw);
    }



    /***************************
     ********** LZ78 ************
     ***************************/

    /** \var LLista amb les estadístiques d'execució amb LZ78*/
    private static List<Double> lz78 = new ArrayList<Double>() {{
        for (int i = 0; i < 9; ++i) add(0.0);

    }};

    /** \brief Obtenció d'estadístiques LZ78
     \pre  Cert
     \post Es retornen totes les estadístiques de LZ78
     */

    public static List<Double> getstatsLZ78(){
        List<Double> tmpstats = new ArrayList<>();
        tmpstats.add(lz78.get(0));
        tmpstats.add(lz78.get(1));
        tmpstats.add(lz78.get(2));
        tmpstats.add(lz78.get(3));
        if (lz78.get(7) != 0) tmpstats.add(lz78.get(4)/lz78.get(7)); else tmpstats.add(0.0);
        if (lz78.get(8) != 0) tmpstats.add(lz78.get(5)/lz78.get(8)); else tmpstats.add(0.0);
        if (lz78.get(7) != 0) tmpstats.add(lz78.get(6)/lz78.get(7)); else tmpstats.add(0.0);
        tmpstats.add(lz78.get(7));
        tmpstats.add(lz78.get(8));
        return tmpstats;
    }

    /** \brief Recuperacio estadistiques
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de LZ78
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */

    public static void setLZ78(List<Double> estlz78){
        for (int i = 0; i < 9; ++i) lz78.set(i,estlz78.get(i));
        est.put("LZ78",lz78);
    }



    /***************************
     ********** LZSS ***********
     **************************/

    /** \var LLista amb les estadístiques d'execució amb LZSS*/
    private static List<Double> lzss = new ArrayList<Double>() {{
        for (int i = 0; i < 9; ++i) add(0.0);

    }};

    /** \brief Obtenció d'estadístiques LZSS
     \pre  Cert
     \post Es retornen totes les estadístiques de LZSS
     */

    public static List<Double> getstatsLZSS(){
        List<Double> tmpstats = new ArrayList<>();
        tmpstats.add(lzss.get(0));
        tmpstats.add(lzss.get(1));
        tmpstats.add(lzss.get(2));
        tmpstats.add(lzss.get(3));
        if (lzss.get(7) != 0) tmpstats.add(lzss.get(4)/lzss.get(7)); else tmpstats.add(0.0);
        if (lzss.get(8) != 0) tmpstats.add(lzss.get(5)/lzss.get(8)); else tmpstats.add(0.0);
        if (lzss.get(7) != 0) tmpstats.add(lzss.get(6)/lzss.get(7)); else tmpstats.add(0.0);
        tmpstats.add(lzss.get(7));
        tmpstats.add(lzss.get(8));
        return tmpstats;
    }



    /** \brief Recuperacio estadistiques LZSS
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de LZSS
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */

    public static void setLZSS(List<Double> estlzss){
        for (int i = 0; i < 9; ++i) lzss.set(i,estlzss.get(i));
        est.put("LZSS",lzss);
    }



    /***************************
     ********** JPEG ***********
     **************************/


    /** \var LLista amb les estadístiques d'execució amb JPEG*/
    private static List<Double> jpeg = new ArrayList<Double>() {{
        for (int i = 0; i < 9; ++i) add(0.0);

    }};

    /** \brief Obtenció d'estadístiques LZSS
     \pre  Cert
     \post Es retornen totes les estadístiques de LZSS
     */

    public static List<Double> getstatsJPEG(){
        List<Double> tmpstats = new ArrayList<>();
        tmpstats.add(jpeg.get(0));
        tmpstats.add(jpeg.get(1));
        tmpstats.add(jpeg.get(2));
        tmpstats.add(jpeg.get(3));
        if (jpeg.get(7) != 0) tmpstats.add(jpeg.get(4)/jpeg.get(7)); else tmpstats.add(0.0);
        if (jpeg.get(8) != 0) tmpstats.add(jpeg.get(5)/jpeg.get(8)); else tmpstats.add(0.0);
        if (jpeg.get(7) != 0) tmpstats.add(jpeg.get(6)/jpeg.get(7)); else tmpstats.add(0.0);
        tmpstats.add(jpeg.get(7));
        tmpstats.add(jpeg.get(8));
        return tmpstats;
    }


    /** \brief Recuperacio estadistiques JPEG
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de JPEG
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */

    public static void setJPEG(List<Double> estjpeg){
        for (int i = 0; i < 9; ++i) jpeg.set(i,estjpeg.get(i));
        est.put("JPEG",jpeg);
    }




    /***************************
     ****LAST ALGORITHM USED****
     **************************/

    /** \var LLista d'un element que conté l'id de l'últim algorisme usat
     *  \details 0: LZW | 1: LZ78 | 2: LZSS | 3: JPEG*/
    private static List<Double> LastAlg = new ArrayList<Double>() {{
        add(-1.0);
    }};

    /** \brief Últim algorisme
     \pre  Cert
     \post Retorna l'últim algorisme usat
     */

    public static String getLastAlg() {
        double lAlg = -1.0;
        if (est.get("LAST ALGORITHM") == null) {
            est.put("LAST ALGORITHM", Collections.singletonList(-1.0));
        }
        else lAlg = est.get("LAST ALGORITHM").get(0);
        String alg = "Cap";
        switch ((int) lAlg) {
            case 0:
                alg = "LZW";
                break;
            case 1:
                alg = "LZ78";
                break;
            case 2:
                alg = "LZSS";
                break;
            case 3:
                alg = "JPEG";
                break;
            default:
                break;
        }
        return alg;
    }

    /** \brief Update últim algorisme
     \pre  Cert
     \post S'ha actualitzat l'últim algorisme usat
     */

    public static void setLastAlg(String lAlg) {
        switch (lAlg) {
            case "LZW":
                LastAlg.set(0,0.0);
                break;
            case "LZ78":
                LastAlg.set(0,1.0);
                break;
            case "LZSS":
                LastAlg.set(0,2.0);
                break;
            case "JPEG":
                LastAlg.set(0,3.0);
                break;
            default:
                LastAlg.set(0,-1.0);
                break;
        }
        est.put("LAST ALGORITHM", LastAlg);
    }

    /***************************
     **UPDATE ALGORITHMS STATS**
     **************************/

    /** \brief Update estadistiques LZW
     \pre  ti > 0 & gr > 0
     \post S'han actualitzat les estadistiques globals de l'algorisme LZW
     */

    public static void actW(double ti, double gr, double vel, boolean comp) {
        if (comp) {
            System.out.println(lzw.size());
            lzw.set(0,ti);
            lzw.set(4,lzw.get(4)+ti);
            lzw.set(2,gr);
            lzw.set(6,lzw.get(6)+gr);
            lzw.set(3,vel);
            lzw.set(7,lzw.get(7)+1);
        }
        else{
            lzw.set(1,ti);
            lzw.set(5,lzw.get(5)+ti);
            lzw.set(8,lzw.get(8)+1);
        }
        setLastAlg("LZW");
    }

    /** \brief Update estadistiques LZSS
     \pre  ti > 0 i gr > 0
     \post S'han actualitzat les estadistiques globals de l'algorisme LZSS
     */
    public static void actS(double ti, double gr, double vel, boolean comp) {
        if (comp) {
            lzss.set(0,ti);
            lzss.set(4,lzss.get(4)+ti);
            lzss.set(2,gr);
            lzss.set(6,lzss.get(6)+gr);
            lzss.set(3,vel);
            lzss.set(7,lzss.get(7)+1);
        }
        else{
            lzss.set(1,ti);
            lzss.set(5,lzss.get(5)+ti);
            lzss.set(8,lzss.get(8)+1);
        }
        setLastAlg("LZSS");
    }


    /** \brief Update estadistiques LZ78
     \pre  ti > 0 i gr > 0
     \post S'han actualitzat les estadistiques globals de l'algorisme LZ78
     */
    public static void act8(double ti, double gr, double vel, boolean comp) {
        if (comp) {
            lz78.set(0,ti);
            lz78.set(4,lz78.get(4)+ti);
            lz78.set(2,gr);
            lz78.set(6,lz78.get(6)+gr);
            lz78.set(3,vel);
            lz78.set(7,lz78.get(7)+1);
        }
        else{
            lz78.set(1,ti);
            lz78.set(5,lz78.get(5)+ti);
            lz78.set(8,lz78.get(8)+1);
        }
        setLastAlg("LZ78");
    }


    /** \brief Update estadistiques JPEG
     \pre  ti > 0 i gr > 0
     \post S'han actualitzat les estadistiques globals de l'algorisme JPEG
     */

    public static void actG(double ti, double gr, double vel, boolean comp) {
        if (comp) {
            jpeg.set(0,ti);
            jpeg.set(4,jpeg.get(4)+ti);
            jpeg.set(2,gr);
            jpeg.set(6,jpeg.get(6)+gr);
            jpeg.set(3,vel);
            jpeg.set(7,jpeg.get(7)+1);
        }
        else{
            jpeg.set(1,ti);
            jpeg.set(5,jpeg.get(5)+ti);
            jpeg.set(8,jpeg.get(8)+1);
        }
        setLastAlg("JPEG");
    }

    /***************************
     *****MÈTODES AUXILIARS*****
     **************************/


    /** \brief Unificador d'estadistiques
     \pre  (tots els paràmetres) >= 0
     \post Es retorna una llista amb els valors passats per paràmetre
     */

    public static List<Double> act (double tcl, double tcg, double tdl, double tdg, double rl, double rg, double vel, double numc, double numd){
        List<Double> actalg = new ArrayList<>(Arrays.asList(tcl,tdl,rl,vel,tcg,tdg,rg,numc,numd));
        return actalg;
    }


}
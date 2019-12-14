package Estadístiques;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estadistiques {

    private static String[] noms_est = {"Temps de l'última compressió", "Temps de compressió global" ,"Temps de l'última descompressió", "Temps de descompressió global",
            "Ratio de l'última compressió", "Ratio de compressió global",  "Velocitat de compressió", "Número de compressions" ,
            "Número de descompressions"};
    /**< Noms de les estadístiques : Les 9 posicions es corresponen a :  0/ Temps de l'última compressió. 1/Temps de compressió global.
     * 2/Temps de l'última descompressió. 3/Temps de descompressió global. 4/Ratio de l'última compressió. 5/ Ratio de compressió global.
     * 6/Velocitat de compressió. 7/Número de compressions. 8/Número de descompressions. */

    private static Map<String,List<Double>> est = new HashMap<String, List<Double>>(); /**<Estadístiques Globals : les estadistiques de cada algorisme es troben contenides en una List<Double>, identificat amb al seu nom en un Map.
     * El nom de l'estadística en la posició i-èssima es correspon amb el nom contingut en la posició i-èssima de l'String[] "noms_est". També conté com a clau l'últim algorisme usat, acopmanyat de l'id d'aquest*/


    /***************************
     ********** LZW ************
     ***************************/

    /** \var LLista amb les estadístiques d'execució amb LZW*/
    private static List<Double> lzw  = new ArrayList<>(9);


    /** \brief Recuperacio estadistiques
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de LZW
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */

    public static void setLZW(List<Double> estlzw){
        for (int i = 0; i < 9; ++i) lzw.set(i,estlzw.get(i));
        est.put("LZW",lzw);
    }


    /** \brief Last compress time LZW
     \pre  Cert
     \post Retorna el temps de l'útlima execució amb LZW
     */

    public static double getTimeLZW() {
        return lzw.get(0);
    }


    /** \brief Temps de compressió Global LZW
     \pre  Cert
     \post Retorna la mitjana de temps d'execució amb LZW
     */

    public static double getGlobTimeLZW() {
        return (lzw.get(7) != 0) ? lzw.get(1)/lzw.get(7): 0;
    }


    /** \brief Last decompress time LZW
     \pre  Cert
     \post Retorna el temps de l'útlima descompressió amb LZW
     */

    public static double getDTimeLZW() {
        return lzw.get(2);
    }


    /** \brief Temps de descompressió Global LZW
     \pre  Cert
     \post Retorna la mitjana de temps de descompressió amb LZW
     */

    public static double getDGlobTimeLZW() {
        return (lzw.get(8) != 0) ? lzw.get(3)/lzw.get(8): 0;
    }


    /** \brief Last compress ratio LZW
     \pre  Cert
     \post Retorna el ratio de compressió de l'útlima execució amb LZW
     */

    public static double getRatioLZW() {
        return lzw.get(4);
    }


    /** \brief Ratio global LZW
     \pre  Cert
     \post Retorna el ratio de compressió global amb LZW
     */

    public static double getGlobRatioLZW() {
        return (lzw.get(7) != 0) ? lzw.get(5)/lzw.get(7) : 0;
    }


    /** \brief Velocitat de compressio LZW
     \pre  Cert
     \post Retorna la mitjana de la velocitat de compressió de LZW
     */

    public static double getVelLZW(){
        return lzw.get(6);
    }


    /** \brief Quantitat de compressions LZW
     \pre  Cert
     \post Retorna el número d'usos en la compressió de LZW
     */

    public static double getQuantLZW() {
        return lzw.get(7);
    }


    /** \brief Quantitat de Descompressions LZW
     \pre  Cert
     \post Retorna el número d'usos en la descompressió amb LZW
     */

    public static double getDQuantLZW() {
        return lzw.get(8);
    }


    /***************************
    ********** LZ78 ************
    ***************************/

    /** \var LLista amb les estadístiques d'execució amb LZ78*/
    private static List<Double> lz78 = new ArrayList<>(9);


    /** \brief Recuperacio estadistiques
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de LZ78
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */
    public static void setLZ78(List<Double> estlz78){
        for (int i = 0; i < 9; ++i) lz78.set(i,estlz78.get(i));
        est.put("LZ78",lz78);
    }


    /** \brief Last compress time LZ78
     \pre  Cert
     \post Retorna el temps de l'útlima execució amb LZ78
     */

    public static double getTimeLZ78() {
        return lz78.get(0);
    }


    /** \brief Temps Global LZ78
     \pre  Cert
     \post Retorna la mitjana de temps d'execució amb LZ78
     */

    public static double getGlobTimeLZ78() {
        return (lz78.get(7) != 0) ? lz78.get(1)/lz78.get(7): 0;
    }


    /** \brief Last decompress time LZ78
     \pre  Cert
     \post Retorna el temps de l'útlima descompressió amb LZ78
     */

    public static double getDTimeLZ78() {
        return lz78.get(2);
    }


    /** \brief Temps de descompressió Global LZ78
     \pre  Cert
     \post Retorna la mitjana de temps de descompressió amb LZ78
     */

    public static double getDGlobTimeLZ78() {
        return (lz78.get(8) != 0) ? lz78.get(3)/lz78.get(8): 0;
    }


    /** \brief Last ratio LZ78
     \pre  Cert
     \post Retorna el ratio de compressió de l'útlima execució amb LZ78
     */

    public static double getRatioLZ78() {
        return lz78.get(4);
    }

    /** \brief Ratio global LZ78
     \pre  Cert
     \post Retorna la mitjana de ratios de compressió global amb LZ78
     */

    public static double getGlobRatioLZ78() {
        return (lz78.get(7) != 0) ? lz78.get(5)/lz78.get(7) : 0;
    }


    /** \brief Velocitat de compressio LZ78
     \pre  Cert
     \post Retorna la mitjana de la velocitat de compressió de LZ78
     */

    public static double getVelLZ78(){
        return lz78.get(6);
    }


    /** \brief Quantitat d'usos LZ78
     \pre  Cert
     \post Retorna el número d'usos de LZ78
     */

    public static double getQuantLZ78() {
        return lz78.get(7);
    }


    /** \brief Quantitat de Descompressions LZ78
     \pre  Cert
     \post Retorna el número d'usos en la descompressió amb LZ78
     */

    public static double getDQuantLZ78() {
        return lz78.get(8);
    }


    /***************************
     ********** LZSS ***********
     **************************/

    /** \var LLista amb les estadístiques d'execució amb LZSS*/
    private static List<Double> lzss = new ArrayList<>(9);


    /** \brief Recuperacio estadistiques LZSS
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de LZSS
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */

    public static void setLZSS(List<Double> estlzss){
        for (int i = 0; i < 9; ++i) lzss.set(i,estlzss.get(i));
        est.put("LZSS",lzss);
    }


    /** \brief Last compress time LZSS
     \pre  Cert
     \post Retorna el temps de l'útlima execució amb LZSS
     */

    public static double getTimeLZSS() {
        return lzss.get(0);
    }

    /** \brief Temps Global LZSS
     \pre  Cert
     \post Retorna la mitjana de temps d'execució amb LZSS
     */

    public static double getGlobTimeLZSS() {
        return (lzss.get(7) != 0) ? lzss.get(1)/lzss.get(7): 0;
    }


    /** \brief Last decompress time LZSS
     \pre  Cert
     \post Retorna el temps de l'útlima descompressió amb LZSS
     */

    public static double getDTimeLZSS() {
        return lzss.get(2);
    }


    /** \brief Temps de descompressió Global LZSS
     \pre  Cert
     \post Retorna la mitjana de temps de descompressió amb LZSS
     */

    public static double getDGlobTimeLZSS() {
        return (lzss.get(8) != 0) ? lzss.get(3)/lzss.get(8): 0;
    }


    /** \brief Last ratio LZSS
     \pre  Cert
     \post Retorna el ratio de compressió de l'útlima execució amb LZSS
     */

    public static double getRatioLZSS() {
        return lzss.get(4);
    }


    /** \brief Ratio global LZSS
     \pre  Cert
     \post Retorna el ratio de compressió global amb LZSS
     */

    public static double getGlobRatioLZSS() {
        return (lzss.get(7) != 0) ? lzss.get(5)/lzss.get(7) : 0;
    }


    /** \brief Velocitat de compressio LZSS
     \pre  Cert
     \post Retorna la mitjana de la velocitat de compressió de LZSS
     */

    public static double getVelLZSS(){
        return lzss.get(6);
    }


    /** \brief Quantitat d'usos LZSS
     \pre  Cert
     \post Retorna el número d'usos de LZSS
     */

    public static double getQuantLZSS() {
        return lzss.get(7);
    }


    /** \brief Quantitat de Descompressions LZSS
     \pre  Cert
     \post Retorna el número d'usos en la descompressió amb LZSS
     */

    public static double getDQuantLZSS() {
        return lzss.get(7);
    }



    /***************************
     ********** JPEG ***********
     **************************/


    /** \var LLista amb les estadístiques d'execució amb JPEG*/
    private static List<Double> jpeg = new ArrayList<>(9);


    /** \brief Recuperacio estadistiques JPEG
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de JPEG
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */

    public static void setJPEG(List<Double> estjpeg){
        for (int i = 0; i < 9; ++i) jpeg.set(i,estjpeg.get(i));
        est.put("JPEG",jpeg);
    }


    /** \brief Last compress time JPEG
     \pre  Cert
     \post Retorna el temps de l'útlima execució amb JPEG
     */

    public static double getTimeJPEG() {
        return jpeg.get(0);
    }


    /** \brief Temps Global JPEG
     \pre  Cert
     \post Retorna la mitjana de temps d'execució amb JPEG
     */

    public static double getGlobTimeJPEG() {
        return (jpeg.get(7) != 0) ? jpeg.get(1)/jpeg.get(7): 0;
    }


    /** \brief Last decompress time JPEG
     \pre  Cert
     \post Retorna el temps de l'útlima descompressió amb JPEG
     */

    public static double getDTimeJPEG() {
        return jpeg.get(2);
    }


    /** \brief Temps de descompressió Global JPEG
     \pre  Cert
     \post Retorna la mitjana de temps de descompressió amb JPEG
     */

    public static double getDGlobTimeJPEG() {
        return (jpeg.get(8) != 0) ? jpeg.get(3)/jpeg.get(8): 0;
    }


    /** \brief Last ratio JPEG
     \pre  Cert
     \post Retorna el ratio de compressió de l'útlima execució amb JPEG
     */

    public static double getRatioJPEG() {
        return jpeg.get(4);
    }


    /** \brief Ratio global JPEG
     \pre  Cert
     \post Retorna el ratio de compressió global amb JPEG
     */

    public static double getGlobRatioJPEG() {
        return (jpeg.get(7) != 0) ? jpeg.get(5)/jpeg.get(7) : 0;
    }


    /** \brief Velocitat de compressio JPEG
     \pre  Cert
     \post Retorna la mitjana de la velocitat de compressió de JPEG
     */

    public static double getVelJPEG(){
        return jpeg.get(6);
    }


    /** \brief Quantitat d'usos JPEG
     \pre  Cert
     \post Retorna el número d'usos de JPEG
     */

    public static double getQuantJPEG() {
        return jpeg.get(7);
    }


    /** \brief Quantitat de Descompressions JPEG
     \pre  Cert
     \post Retorna el número d'usos en la descompressió amb JPEG
     */

    public static double getDQuantJPEG() {
        return jpeg.get(7);
    }


    /***************************
     ****LAST ALGORITHM USED****
     **************************/

    /** \var LLista d'un element que conté l'id de l'últim algorisme usat
     *  \details 0: LZW | 1: LZ78 | 2: LZSS | 3: JPEG*/
    private static List<Double> LastAlg = new ArrayList<>(1);

    /** \brief Últim algorisme
     \pre  Cert
     \post Retorna l'últim algorisme usat
     */

    public static String getLastAlg() {
        double lAlg = est.get("LAST ALGORITHM").get(0);
        String alg = "";
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
    public static void actW(double ti, double gr, boolean C) {
        timeLZW = ti;
        GtimeLZW += ti;
        ratioLZW = gr;
        GratioLZW += gr;
        QuantLZW++;
        setLastAlg("LZW");
    }

    /** \brief Update estadistiques LZSS
     \pre  ti > 0 i gr > 0
     \post S'han actualitzat les estadistiques globals de l'algorisme LZSS
     */
    public static void actS(double ti, double gr) {
        timeLZSS = ti;
        GtimeLZSS += ti;
        ratioLZSS = gr;
        GratioLZSS += gr;
        QuantLZSS++;
        setLastAlg("LZSS");
    }

    /** \brief Update estadistiques LZ78
     \pre  ti > 0 i gr > 0
     \post S'han actualitzat les estadistiques globals de l'algorisme LZ78
     */
    public static void act8(double ti, double gr) {
        timeLZ78 = ti;
        GtimeLZ78 += ti;
        ratioLZ78 = gr;
        GratioLZ78 += gr;
        QuantLZ78++;
        setLastAlg("LZ78");
    }

    /** \brief Update estadistiques JPEG
     \pre  ti > 0 i gr > 0
     \post S'han actualitzat les estadistiques globals de l'algorisme JPEG
     */

    public static void actG(double ti, double gr) {
        timeJPEG = ti;
        GtimeJPEG += ti;
        ratioJPEG = gr;
        GratioJPEG += gr;
        QuantJPEG++;
        setLastAlg("JPEG");
    }


}
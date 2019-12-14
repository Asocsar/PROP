package Estadístiques;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estadistiques {

    private static String[] noms_est = {"Temps de l'última compressió", "Temps de compressió global" ,"Temps de l'última descompressió", "Temps de descompressió global",
            "Ratio de l'última compressió", "Ratio de compressió global",  "Velocitat de compressió", "Número de compressions" ,
            "Número de descompressions"};
    /**< Noms de les estadístiques : Les 9 posicions es corresponen a :  1/ Temps de l'última compressió. 2/Temps de compressió global.
     * 3/Temps de l'última descompressió. 4/Temps de descompressió global. 5/Ratio de l'última compressió. 6/ Ratio de compressió global.
     * 7/Velocitat de compressió. 8/Número de compressions. 9/Número de descompressions. */

    private static Map<String,List<Double>> est = new HashMap<String, List<Double>>(); /**<Estadístiques Globals : les estadistiques de cada algorisme es troben contenides en una List<Double>, identificat amb al seu nom en un Map.
     * El nom de l'estadística en la posició i-èssima es correspon amb el nom contingut en la posició i-èssima de l'String[] "noms_est" */


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
        return (lzw.get(8) != 0) ? lzw.get(1)/lzw.get(8): 0;
    }

    /** \brief Last decompress time LZW
     \pre  Cert
     \post Retorna el temps de l'útlima descompressió amb LZW
     */

    public static double getDTimeLZW() {
        return lzw.get(3);
    }

    /** \brief Temps de descompressió Global LZW
     \pre  Cert
     \post Retorna la mitjana de temps de descompressió amb LZW
     */

    public static double getDGlobTimeLZW() {
        return (lzw.get(9) != 0) ? lzw.get(4)/lzw.get(9): 0;
    }


    /** \brief Last compress ratio LZW
     \pre  Cert
     \post Retorna el ratio de compressió de l'útlima execució amb LZW
     */

    public static double getRatioLZW() {
        return lzw.get(5);
    }

    /** \brief Ratio global LZW
     \pre  Cert
     \post Retorna el ratio de compressió global amb LZW
     */

    public static double getGlobRatioLZW() {
        return (lzw.get(8) != 0) ? lzw.get(6)/lzw.get(8) : 0;
    }

    /** \brief Velocitat de compressio LZW
     \pre  Cert
     \post Retorna la mitjana de la velocitat de compressió de LZW
     */
    public static double getVelLZW(){
        return lzw.get(7);
    }

    /** \brief Quantitat de compressions LZW
     \pre  Cert
     \post Retorna el número d'usos en la compressió de LZW
     */
    public static double getQuantLZW() {
        return lzw.get(8);
    }

    /** \brief Quantitat de Descompressions LZW
     \pre  Cert
     \post Retorna el número d'usos en la descompressió amb LZW
     */

    public static double getDQuantLZW() {
        return lzw.get(9);
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


    /** \brief Last time LZ78
     \pre  Cert
     \post Retorna el temps de l'útlima execució amb LZ78
     */
    public static double getTimeLZ78() {
        return timeLZ78;
    }

    /** \brief Temps Global LZ78
     \pre  Cert
     \post Retorna la mitjana de temps d'execució amb LZ78
     */
    public static double getGlobTimeLZ78() {
        return (QuantLZ78 != 0) ?  GtimeLZ78/QuantLZ78 : 0;
    }

    /** \brief Last ratio LZ78
     \pre  Cert
     \post Retorna el ratio de compressió de l'útlima execució amb LZ78
     */
    public static double getRatioLZ78() {
        return ratioLZ78;
    }

    /** \brief Ratio global LZ78
     \pre  Cert
     \post Retorna el ratio de compressió global amb LZ78
     */
    public static double getGlobRatioLZ78() {
        return (QuantLZ78 != 0) ?  GratioLZ78/QuantLZ78 : 0;
    }

    /** \brief Quantitat d'usos JPEG
     \pre  Cert
     \post Retorna el número d'usos de JPEG
     */
    public static double getQuantLZ78() {
        return QuantLZ78;
    }



    /***************************
     ********** LZSS ***********
     **************************/

    /** \var LLista amb les estadístiques d'execució amb LZSS*/
    private static List<Double> lzss = new ArrayList<>(9);

    private static double timeLZSS;
    private static double GtimeLZSS;
    private static double ratioLZSS;
    private static double GratioLZSS;
    private static double QuantLZSS;

    /** \brief Recuperacio estadistiques LZSS
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de LZSS
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */
    public static void setLZSS(double tl, double tg, double rl, double rg, double quant) {
        timeLZSS = tl;
        GtimeLZSS = tg;
        ratioLZSS = rl;
        GratioLZSS = rg;
        QuantLZSS = quant;
    }

    /** \brief Last time LZSS
     \pre  Cert
     \post Retorna el temps de l'útlima execució amb LZSS
     */
    public static double getTimeLZSS() {
        return timeLZSS;
    }

    /** \brief Temps Global LZSS
     \pre  Cert
     \post Retorna la mitjana de temps d'execució amb LZSS
     */
    public static double getGlobTimeLZSS() {
        return (QuantLZSS != 0) ? GtimeLZSS/QuantLZSS : 0;
    }

    /** \brief Last ratio LZSS
     \pre  Cert
     \post Retorna el ratio de compressió de l'útlima execució amb LZSS
     */
    public static double getRatioLZSS() {
        return ratioLZSS;
    }

    /** \brief Ratio global LZSS
     \pre  Cert
     \post Retorna el ratio de compressió global amb LZSS
     */
    public static double getGlobRatioLZSS() {
        return (QuantLZSS != 0) ? GratioLZSS/QuantLZSS : 0;
    }

    /** \brief Quantitat d'usos JPEG
     \pre  Cert
     \post Retorna el número d'usos de JPEG
     */
    public static double getQuantLZSS() {
        return QuantLZSS;
    }



    /***************************
     ********** JPEG ***********
     **************************/


    /** \var LLista amb les estadístiques d'execució amb JPEG*/
    private static List<Double> jpeg = new ArrayList<>(9);


    private static double timeJPEG;
    private static double GtimeJPEG;
    private static double ratioJPEG;
    private static double GratioJPEG;
    private static double QuantJPEG;


    /** \brief Recuperacio estadistiques JPEG
     \pre  Cert
     \post S'han assignat els valors de les estadistiques de JPEG
     \details A l'iniciar de nou el programa, recuperem les estadistiques fins al moment de l'execució actual, i les hi assignem a les variables de cada algorisme
     */
    public static void setJPEG(double tl, double tg, double rl, double rg, double quant) {
        timeJPEG = tl;
        GtimeJPEG = tg;
        ratioJPEG = rl;
        GratioJPEG = rg;
        QuantJPEG = quant;
    }

    /** \brief Last time JPEG
     \pre  Cert
     \post Retorna el temps de l'útlima execució amb JPEG
     */
    public static double getTimeJPEG() {
        return timeJPEG;
    }

    /** \brief Temps Global JPEG
     \pre  Cert
     \post Retorna la mitjana de temps d'execució amb JPEG
     */
    public static double getGlobTimeJPEG() {
        return (QuantJPEG != 0) ? GtimeJPEG/QuantJPEG : 0;
    }

    /** \brief Last ratio JPEG
     \pre  Cert
     \post Retorna el ratio de compressió de l'útlima execució amb JPEG
     */
    public static double getRatioJPEG() {
        return ratioJPEG;
    }

    /** \brief Ratio global JPEG
     \pre  Cert
     \post Retorna el ratio de compressió global amb JPEG
     */
    public static double getGlobRatioJPEG() {
        return (QuantJPEG != 0) ? GratioJPEG/QuantJPEG : 0;
    }

    /** \brief Quantitat d'usos JPEG
     \pre  Cert
     \post Retorna el número d'usos de JPEG
     */
    public static double getQuantJPEG() {
        return QuantJPEG;
    }

    //LAST ALGORITHM USED

    public static String LastAlg = "";

    /** \brief Últim algorisme
     \pre  Cert
     \post Retorna l'últim algorisme usat
     */at
    public static String getLastAlg() {
        return LastAlg;
    }

    /** \brief Update últim algorisme
     \pre  Cert
     \post S'ha actualitzat l'últim algorisme usat
     */
    public static void setLastAlg(String lAlg) {
        LastAlg = lAlg;
    }

    //ALGORITHM STATS UPDATE

    /** \brief Update estadistiques LZW
     \pre  ti > 0 i gr > 0
     \post S'han actualitzat les estadistiques globals de l'algorisme LZW
     */
    public static void actW(double ti, double gr) {
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

    /** \brief Visualització d'estadístíques globals
     \pre  Cert
     \post Mostra totes les estadístiques
     */
    public static void visualglob() {
        System.out.println("Tiempos de ultimas compresiones");
        System.out.println("LZ78 " + timeLZ78);
        System.out.println("LZSS " + timeLZSS);
        System.out.println("LZW " + timeLZW);
        System.out.println("JPEG " + timeJPEG);
        System.out.println("Ratios de ultimas compresiones");
        System.out.println("LZ78 " + ratioLZ78);
        System.out.println("LZSS " + ratioLZSS);
        System.out.println("LZW " + ratioLZW);
        System.out.println("JPEG " + ratioJPEG);
        System.out.println("Tiempos globales por cada algoritmo");
        System.out.println("LZ78 " + ((QuantLZ78 != 0) ? GtimeLZ78/QuantLZ78 : 0));
        System.out.println("LZSS " + ((QuantLZSS != 0) ? GtimeLZSS/QuantLZSS : 0));
        System.out.println("LZW " + ((QuantLZW != 0) ? GtimeLZW/QuantLZW : 0));
        System.out.println("JPEG " + ((QuantJPEG != 0) ? GtimeJPEG/QuantJPEG : 0));
        System.out.println("Ratios globales por cada algoritmo");
        System.out.println("LZ78 " + ((QuantLZ78 != 0) ? GratioLZ78/QuantLZ78 : 0));
        System.out.println("LZSS " + ((QuantLZSS != 0) ? GratioLZSS/QuantLZSS : 0));
        System.out.println("LZW " + ((QuantLZW != 0) ? GratioLZW/QuantLZW : 0));
        System.out.println("JPEG " + ((QuantJPEG != 0) ? GratioJPEG/QuantJPEG : 0));
        System.out.println("Numero de veces de ejecución");
        System.out.println("LZ78 " + QuantLZ78);
        System.out.println("LZSS " + QuantLZSS);
        System.out.println("LZW " + QuantLZW);
        System.out.println("JPEG " + QuantJPEG);
        System.out.println("Ultimo algoritmo utilizado");
        System.out.println(getLastAlg());
    }
}
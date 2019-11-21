package Estadístiques;


public class Estadistiques {


    // LZW
    // Variables LZW
    private static double timeLZW;
    private static double GtimeLZW;
    private static double ratioLZW;
    private static double GratioLZW;
    private static double QuantLZW;

    // Aqui estbim uns valors per a las variables de temps i rati de l'algorisme LZW
    // Pre : Cert
    // Post: Variables LZW inicialitzades
    public static void setLZW(double tl, double tg, double rl, double rg, double quant) {
        timeLZW = tl;
        GtimeLZW = tg;
        ratioLZW = rl;
        GratioLZW = rg;
        QuantLZW = quant;
    }

    // Pre : Cert
    // Post: retorna el temps trigat de l'ultima compressio
    public static double getTimeLZW() {
        return timeLZW;
    }

    // Pre : Cert
    // Post: Retorna el temps global de totes les compressions
    public static double getGlobTimeLZW() {
        return (QuantLZW != 0) ? GtimeLZW/QuantLZW : 0;
    }

    // Pre : Cert
    // Post: Retorna grau de compressio assolit en la ultima execucio
    public static double getRatioLZW() {
        return ratioLZW;
    }

    // Pre : Cert
    // Post: Retorna grau de compressió global
    public static double getGlobRatioLZW() {
        return (QuantLZW != 0) ? GratioLZW/QuantLZW : 0;
    }

    // Pre : Cert
    // Post: Retorna quantitat de vegades executat LZW
    public static double getQuantLZW() {
        return QuantLZW;
    }


    //LZ78

    private static double timeLZ78;
    private static double GtimeLZ78;
    private static double ratioLZ78;
    private static double GratioLZ78;
    private static double QuantLZ78;

    // Aqui estbim uns valors per a las variables de temps i rati de l'algorisme LZ78
    // Pre : Cert
    // Post: Variables LZ78 inicialitzades
    public static void setLZ78(double tl, double tg, double rl, double rg, double quant) {
        timeLZ78 = tl;
        GtimeLZ78 = tg;
        ratioLZ78 = rl;
        GratioLZ78 = rg;
        QuantLZ78 = quant;
    }

    // Pre : Cert
    // Post: retorna el temps trigat de l'ultima compressio
    public static double getTimeLZ78() {
        return timeLZ78;
    }

    // Pre : Cert
    // Post: Retorna el temps global de totes les compressions
    public static double getGlobTimeLZ78() {
        return (QuantLZ78 != 0) ?  GtimeLZ78/QuantLZ78 : 0;
    }

    // Pre : Cert
    // Post: Retorna grau de compressio assolit en la ultima execucio
    public static double getRatioLZ78() {
        return ratioLZ78;
    }

    // Pre : Cert
    // Post: Retorna grau de compressió global
    public static double getGlobRatioLZ78() {
        return (QuantLZ78 != 0) ?  GratioLZ78/QuantLZ78 : 0;
    }

    // Pre : Cert
    // Post: Retorna quantitat de vegades executat LZ78
    public static double getQuantLZ78() {
        return QuantLZ78;
    }


    //LZSS

    private static double timeLZSS;
    private static double GtimeLZSS;
    private static double ratioLZSS;
    private static double GratioLZSS;
    private static double QuantLZSS;

    // Aqui estbim uns valors per a las variables de temps i rati de l'algorisme LZ78
    // Pre : Cert
    // Post: Variables LZSS inicialitzades
    public static void setLZSS(double tl, double tg, double rl, double rg, double quant) {
        timeLZSS = tl;
        GtimeLZSS = tg;
        ratioLZSS = rl;
        GratioLZSS = rg;
        QuantLZSS = quant;
    }

    // Pre : Cert
    // Post: retorna el temps trigat de l'ultima compressio
    public static double getTimeLZSS() {
        return timeLZSS;
    }

    // Pre : Cert
    // Post: Retorna el temps global de totes les compressions
    public static double getGlobTimeLZSS() {
        return (QuantLZSS != 0) ? GtimeLZSS/QuantLZSS : 0;
    }

    // Pre : Cert
    // Post: Retorna grau de compressio assolit en la ultima execucio
    public static double getRatioLZSS() {
        return ratioLZSS;
    }

    // Pre : Cert
    // Post: Retorna grau de compressió global
    public static double getGlobRatioLZSS() {
        return (QuantLZSS != 0) ? GratioLZSS/QuantLZSS : 0;
    }

    // Pre : Cert
    // Post: Retorna quantitat de vegades executat LZss
    public static double getQuantLZSS() {
        return QuantLZSS;
    }


    //JPEG

    private static double timeJPEG;
    private static double GtimeJPEG;
    private static double ratioJPEG;
    private static double GratioJPEG;
    private static double QuantJPEG;


    // Aqui estbim uns valors per a las variables de temps i rati de l'algorisme JPEG
    // Pre : Cert
    // Post: Variables JPEG inicialitzades
    public static void setJPEG(double tl, double tg, double rl, double rg, double quant) {
        timeJPEG = tl;
        GtimeJPEG = tg;
        ratioJPEG = rl;
        GratioJPEG = rg;
        QuantJPEG = quant;
    }

    // Pre : Cert
    // Post: retorna el temps trigat de l'ultima compressio
    public static double getTimeJPEG() {
        return timeJPEG;
    }

    // Pre : Cert
    // Post: Retorna el temps global de totes les compressions
    public static double getGlobTimeJPEG() {
        return (QuantJPEG != 0) ? GtimeJPEG/QuantJPEG : 0;
    }

    // Pre : Cert
    // Post: Retorna grau de compressio assolit en la ultima execucio
    public static double getRatioJPEG() {
        return ratioJPEG;
    }

    // Pre : Cert
    // Post: Retorna grau de compressió global
    public static double getGlobRatioJPEG() {
        return (QuantJPEG != 0) ? GratioJPEG/QuantJPEG : 0;
    }

    // Pre : Cert
    // Post: Retorna quantitat de vegades executat JPEG
    public static double getQuantJPEG() {
        return QuantJPEG;
    }

    //LAST ALGORITHM USED

    public static String LastAlg = "";

    // Pre: Cert
    // Post: retorna l'últim algoritme utilitzat
    public static String getLastAlg() {
        return LastAlg;
    }

    // Pre : Cert
    // Post: estableix l'últim algoritme utilitzat
    public static void setLastAlg(String lAlg) {
        LastAlg = lAlg;
    }

    //ALGORITHM STATS UPDATE

    // Pre :  ti > 0 && gr > 0
    // Post: variables LZW actualitzades
    public static void actW(double ti, double gr) {
        timeLZW = ti;
        GtimeLZW += ti;
        ratioLZW = gr;
        GratioLZW += gr;
        QuantLZW++;
        setLastAlg("LZW");
    }

    // Pre :  ti > 0 && gr > 0
    // Post: variables LZW actualitzades
    public static void actS(double ti, double gr) {
        timeLZSS = ti;
        GtimeLZSS += ti;
        ratioLZSS = gr;
        GratioLZSS += gr;
        QuantLZSS++;
        setLastAlg("LZSS");
    }

    // Pre :  ti > 0 && gr > 0
    // Post: variables LZW actualitzades
    public static void act8(double ti, double gr) {
        timeLZ78 = ti;
        GtimeLZ78 += ti;
        ratioLZ78 = gr;
        GratioLZ78 += gr;
        QuantLZ78++;
        setLastAlg("LZ78");
    }

    // Pre :  ti > 0 && gr > 0
    // Post: variables LZW actualitzades
    public static void actG(double ti, double gr) {
        timeJPEG = ti;
        GtimeJPEG += ti;
        ratioJPEG = gr;
        GratioJPEG += gr;
        QuantJPEG++;
        setLastAlg("JPEG");
    }

    // Pre : Cert
    // Post: Mostra totes les estadístiques
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

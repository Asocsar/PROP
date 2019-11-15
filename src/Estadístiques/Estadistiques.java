package Estadístiques;

public class Estadistiques {


    //LZW

    private static double timeLZW;
    private static double GtimeLZW;
    private static double ratioLZW;
    private static double GratioLZW;
    private static double QuantLZW;

    public static void setLZW(double tl, double tg, double rl, double rg, double quant) {
        timeLZW = tl;
        GtimeLZW = tg;
        ratioLZW = rl;
        GratioLZW = rg;
        QuantLZW = quant;
    }

    public static double getTimeLZW() {
        return timeLZW;
    }

    public static double getGlobTimeLZW() {
        return GtimeLZW;
    }

    public static double getRatioLZW() {
        return ratioLZW;
    }

    public static double getGlobRatioLZW() {
        return GratioLZW;
    }

    public static double getQuantLZW() {
        return QuantLZW;
    }


    //LZ78

    private static double timeLZ78;
    private static double GtimeLZ78;
    private static double ratioLZ78;
    private static double GratioLZ78;
    private static double QuantLZ78;

    public static void setLZ78(double tl, double tg, double rl, double rg, double quant) {
        timeLZ78 = tl;
        GtimeLZ78 = tg;
        ratioLZ78 = rl;
        GratioLZ78 = rg;
        QuantLZ78 = quant;
    }

    public static double getTimeLZ78() {
        return timeLZ78;
    }

    public static double getGlobTimeLZ78() {
        return GtimeLZ78;
    }

    public static double getRatioLZ78() {
        return ratioLZ78;
    }

    public static double getGlobRatioLZ78() {
        return GratioLZ78;
    }

    public static double getQuantLZ78() {
        return QuantLZ78;
    }


    //LZSS

    private static double timeLZSS;
    private static double GtimeLZSS;
    private static double ratioLZSS;
    private static double GratioLZSS;
    private static double QuantLZSS;

    public static void setLZSS(double tl, double tg, double rl, double rg, double quant) {
        timeLZSS = tl;
        GtimeLZSS = tg;
        ratioLZSS = rl;
        GratioLZSS = rg;
        QuantLZSS = quant;
    }

    public static double getTimeLZSS() {
        return timeLZSS;
    }

    public static double getGlobTimeLZSS() {
        return GtimeLZSS;
    }

    public static double getRatioLZSS() {
        return ratioLZSS;
    }

    public static double getGlobRatioLZSS() {
        return GratioLZSS;
    }

    public static double getQuantLZSS() {
        return QuantLZSS;
    }


    //JPEG

    private static double timeJPEG;
    private static double GtimeJPEG;
    private static double ratioJPEG;
    private static double GratioJPEG;
    private static double QuantJPEG;


    public static void setJPEG(double tl, double tg, double rl, double rg, double quant) {
        timeJPEG = tl;
        GtimeJPEG = tg;
        ratioJPEG = rl;
        GratioJPEG = rg;
        QuantJPEG = quant;
    }

    public static double getTimeJPEG() {
        return timeJPEG;
    }

    public static double getGlobTimeJPEG() {
        return GtimeJPEG;
    }

    public static double getRatioJPEG() {
        return ratioJPEG;
    }

    public static double getGlobRatioJPEG() {
        return GratioJPEG;
    }

    public static double getQuantJPEG() {
        return QuantJPEG;
    }

    //LAST ALGORITHM USED

    public static String LastAlg = "LZ78";

    public static String getLastAlg() {
        return LastAlg;
    }

    public static void setLastAlg(String lAlg) {
        LastAlg = lAlg;
    }

    //ALGORITHM STATS UPDATE

    public static void actW(double ti, double gr) {
        timeLZW = ti;
        GtimeLZW += ti;
        ratioLZW = gr;
        GratioLZW += gr;
        QuantLZW++;
    }

    public static void actS(double ti, double gr) {
        timeLZSS = ti;
        GtimeLZSS += ti;
        ratioLZSS = gr;
        GratioLZSS += gr;
        QuantLZSS++;
    }

    public static void act8(double ti, double gr) {
        timeLZ78 = ti;
        GtimeLZ78 += ti;
        ratioLZ78 = gr;
        GratioLZ78 += gr;
        QuantLZ78++;
    }

    public static void actG(double ti, double gr) {
        timeJPEG = ti;
        GtimeJPEG += ti;
        ratioJPEG = gr;
        GratioJPEG += gr;
        QuantJPEG++;
    }
}





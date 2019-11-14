package Estadístiques;

public class Estadistiques {
    private static double gradeW = 0;
    private static double timeW = 0;
    private static double grade8 = 0;
    private static double time8 = 0;
    private static double gradeS = 0;
    private static double timeS = 0;
    private static double gradeG = 0;
    private static double timeG = 0;
    private static double gradeWT = 0;
    private static double timeWT = 0;
    private static double grade8T = 0;
    private static double time8T = 0;
    private static double gradeST = 0;
    private static double timeST = 0;
    private static double gradeGT = 0;
    private static double timeGT = 0;
    private static int numW = 0;
    private static int numS = 0;
    private static int num8 = 0;
    private static int numG = 0;
    private static String last = "";

    public  Estadistiques () {}

    public int getQuantLZW () {
        return numW;
    }
    public int getQuantLZSS () {
        return numS;
    }
    public int getQuantLZ78 () {
        return num8;
    }
    public int getQuantJPEG () {
        return numG;
    }

    public void setLZW (double gr, double grT, double ti, double tiT, double q) {
        gradeW = gr;
        gradeWT = grT;
        timeW = ti;
        timeWT = tiT;
        numW = (int)q;
    }

    public void setLZSS (double gr, double grT, double ti, double tiT, double q) {
        gradeS = gr;
        gradeST = grT;
        timeS = ti;
        timeST = tiT;
        numS = (int)q;
    }

    public void setLZ78 (double gr, double grT, double ti, double tiT, double q) {
        grade8 = gr;
        grade8T = grT;
        time8 = ti;
        time8T = tiT;
        num8 = (int)q;
    }

    public void setJPEG  (double gr, double grT, double ti, double tiT, double q) {
        gradeG = gr;
        gradeGT = grT;
        timeG = ti;
        timeGT = tiT;
        numG = (int)q;
    }


    public double getRatioLZW () {return gradeW;}
    public double getRatioLZSS () {return grade8;}
    public double getRatioLZ78 () {return gradeS;}
    public double getRatioJPEG () {return gradeG;}


    public double getTimeLZW () {return timeW;}
    public double getTimeLZSS () {return time8;}
    public double getTimeLZ78 () {return timeS;}
    public double getTimeJPEG () {return timeG;}

    public double getGlobRatioLZW () {return gradeWT;}
    public double getGlobRatioLZSS () {return grade8T;}
    public double getGlobRatioLZ78 () {return gradeST;}
    public double getGlobRatioJPEG () {return gradeGT;}


    public double getGlobTimeLZW () {return timeWT;}
    public double getGlobTimeLZSS () {return time8T;}
    public double getGlobTimeLZ78 () {return timeST;}
    public double getGlobTimeJPEG () {return timeGT;}


    public void actW (double ti, double gr) {
        timeW = ti;
        timeWT += ti;
        gradeW = gr;
        gradeWT += gr;
        numW += 1;
        last = "0";
    }

    public void actS (double ti, double gr) {
        timeS = ti;
        timeST += ti;
        gradeS = gr;
        gradeST += gr;
        numS += 1;
        last = "1";
    }

    public void act8 (double ti, double gr) {
        time8 = ti;
        time8T += ti;
        grade8 = gr;
        grade8T += gr;
        num8 += 1;
        last = "2";
    }

    public void actG (double ti, double gr) {
        timeG = ti;
        timeGT += ti;
        gradeG = gr;
        gradeGT += gr;
        numG += 1;
        last = "3";
    }

    public String getLastAlg () {return  last; }
    public void setLastAlg (String id) {last = id;}

    public void visualglob() {
        System.out.println("Tiempos de ultimas compresiones");
        System.out.println("LZ78 " + timeW);
        System.out.println("LZSS " + timeS);
        System.out.println("LZW " + timeW);
        System.out.println("JPEG " + timeG);
        System.out.println("Ratios de ultimas compresiones");
        System.out.println("LZ78 " + grade8);
        System.out.println("LZSS " + gradeS);
        System.out.println("LZW " + gradeW);
        System.out.println("JPEG " + gradeG);
        System.out.println("Tiempos globales por cada algoritmo");
        System.out.println("LZ78 " + timeWT/num8);
        System.out.println("LZSS " + timeST/numS);
        System.out.println("LZW " + timeWT/numW);
        System.out.println("JPEG " + timeGT/numG);
        System.out.println("Ratios globales por cada algoritmo");
        System.out.println("LZ78 " + grade8T/num8);
        System.out.println("LZSS " + gradeST/numS);
        System.out.println("LZW " + gradeWT/numW);
        System.out.println("JPEG " + gradeGT/numG);
        System.out.println("Numero de veces de ejecución");
        System.out.println("LZ78 " + num8);
        System.out.println("LZSS " + numS);
        System.out.println("LZW " + numW);
        System.out.println("JPEG " + numG);
    }




}

package Estadístiques;

public class Estadistiques {
    private static double gradeW;
    private static double timeW;
    private static double grade8;
    private static double time8;
    private static double gradeS;
    private static double timeS;
    private static double gradeG;
    private static double timeG;
    private static double gradeWT;
    private static double timeWT;
    private static double grade8T;
    private static double time8T;
    private static double gradeST;
    private static double timeST;
    private static double gradeGT;
    private static double timeGT;
    private static int numW;
    private static int numS;
    private static int num8;
    private static int numG;
    private static int last;

    public  Estadistiques () {}

    public void setGradeW (double gr, double grT) {gradeW = gr;gradeWT = grT;}
    public void setGrade8 (double gr, double grT) {grade8 = gr;gradeWT = grT;}
    public void setGradeS (double gr, double grT) {gradeS = gr;gradeWT = grT;}
    public void setGradeG (double gr, double grT) {gradeG = gr;gradeWT = grT;}


    public void setTimeW (double ti, double tiT) {timeW = ti;timeWT = tiT;}
    public void setTime8 (double ti, double tiT) {time8 = ti;timeWT = tiT;}
    public void setTimeS (double ti, double tiT) {timeS = ti;timeWT = tiT;}
    public void setTimeG (double ti, double tiT) {timeG = ti;timeWT = tiT;}


    public double getGradeW () {return gradeW;}
    public double getGrade8 () {return grade8;}
    public double getGradeS () {return gradeS;}
    public double getGradeG () {return gradeG;}


    public double getTimeW () {return timeW;}
    public double getTime8 () {return time8;}
    public double getTimeS () {return timeS;}
    public double getTimeG () {return timeG;}

    public double getGradeWT () {return gradeWT;}
    public double getGrade8T () {return grade8T;}
    public double getGradeST () {return gradeST;}
    public double getGradeGT () {return gradeGT;}


    public double getTimeWT () {return timeWT;}
    public double getTime8T () {return time8T;}
    public double getTimeST () {return timeST;}
    public double getTimeGT () {return timeGT;}


    public void actW (double ti, double gr) {
        timeW = ti;
        timeWT += ti;
        gradeW = gr;
        gradeWT += gr;
        numW += 1;
        last = 0;
    }

    public void actS (double ti, double gr) {
        timeS = ti;
        timeST += ti;
        gradeS = gr;
        gradeST += gr;
        numS += 1;
        last = 1;
    }

    public void act8 (double ti, double gr) {
        time8 = ti;
        time8T += ti;
        grade8 = gr;
        grade8T += gr;
        num8 += 1;
        last = 2;
    }

    public void actG (double ti, double gr) {
        timeG = ti;
        timeGT += ti;
        gradeG = gr;
        gradeGT += gr;
        numG += 1;
        last = 3;
    }

    public int getLastAlg () {return  last; }
    public void setLastAlg (int id) {last = id;}

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
        System.out.println("LZ78 " + timeWT);
        System.out.println("LZSS " + timeST);
        System.out.println("LZW " + timeWT);
        System.out.println("JPEG " + timeGT);
        System.out.println("Ratios globales por cada algoritmo");
        System.out.println("LZ78 " + grade8T);
        System.out.println("LZSS " + gradeST);
        System.out.println("LZW " + gradeWT);
        System.out.println("JPEG " + gradeGT);
    }




}

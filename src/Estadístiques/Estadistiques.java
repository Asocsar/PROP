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

    public  Estadistiques () {}

    public static void setGradeW (double gr, double grT) {gradeW = gr;gradeWT = grT;}
    public static void setGrade8 (double gr, double grT) {grade8 = gr;gradeWT = grT;}
    public static void setGradeS (double gr, double grT) {gradeS = gr;gradeWT = grT;}
    public static void setGradeG (double gr, double grT) {gradeG = gr;gradeWT = grT;}


    public static void setTimeW (double ti, double tiT) {timeW = ti;timeWT = tiT;}
    public static void setTime8 (double ti, double tiT) {time8 = ti;timeWT = tiT;}
    public static void setTimeS (double ti, double tiT) {timeS = ti;timeWT = tiT;}
    public static void setTimeG (double ti, double tiT) {timeG = ti;timeWT = tiT;}


    public static double getGradeW () {return gradeW;}
    public static double getGrade8 () {return grade8;}
    public static double getGradeS () {return gradeS;}
    public static double getGradeG () {return gradeG;}


    public static double getTimeW () {return timeW;}
    public static double getTime8 () {return time8;}
    public static double getTimeS () {return timeS;}
    public static double getTimeG () {return timeG;}

    public static double getGradeWT () {return gradeWT;}
    public static double getGrade8T () {return grade8T;}
    public static double getGradeST () {return gradeST;}
    public static double getGradeGT () {return gradeGT;}


    public static double getTimeWT () {return timeWT;}
    public static double getTime8T () {return time8T;}
    public static double getTimeST () {return timeST;}
    public static double getTimeGT () {return timeGT;}


    public static void actW (double ti, double gr) {
        timeW = ti;
        timeWT += ti;
        gradeW = gr;
        gradeWT += gr;
        numW += 1;
    }

    public static void actS (double ti, double gr) {
        timeS = ti;
        timeST += ti;
        gradeS = gr;
        gradeST += gr;
        numS += 1;
    }

    public static void act8 (double ti, double gr) {
        time8 = ti;
        time8T += ti;
        grade8 = gr;
        grade8T += gr;
        num8 += 1;
    }

    public static void actG (double ti, double gr) {
        timeG = ti;
        timeGT += ti;
        gradeG = gr;
        gradeGT += gr;
        numG += 1;
    }




}

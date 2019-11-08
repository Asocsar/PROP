package Controlador_Estadistiques;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public  class Stats_Stub{ //Stats Stub

    //LZW

    private static double timeLZW = 0;
    private static double GtimeLZW = 0;
    private static double ratioLZW = 0;
    private static double GratioLZW = 0;
    private static double QuantLZW = 0;

    public static void setLZW(double tl,double tg,double rl,double rg, double quant){}
    public static double getTimeLZW(){ return 4.0;}
    public static double getGlobTimeLZW(){ return 4.0;}
    public static double getRatioLZW(){ return 4.0;}
    public static double getGlobRatioLZW() { return 3.0;}
    public static double getQuantLZW(){ return 4.0;}


    //LZ78

    private static double timeLZ78 = 0;
    private static double GtimeLZ78 = 0;
    private static double ratioLZ78 = 0;
    private static double GratioLZ78 = 0;
    private static double QuantLZ78 = 0;

    public static void setLZSS(double tl,double tg,double rl,double rg, double quant){}
    public static double getTimeLZSS(){ return 4.0;}
    public static double getGlobTimeLZSS(){ return 4.0;}
    public static double getRatioLZSS(){ return 4.0;}
    public static double getGlobRatioLZSS() { return 3.0;}
    public static double getQuantLZSS(){ return 4.0;}


    //LZSS

    private static double timeLZSS = 0;
    private static double GtimeLZSS = 0;
    private static double ratioLZSS = 0;
    private static double GratioLZSS = 0;
    private static double QuantLZSS = 0;

    public static void setLZ78(double tl,double tg,double rl,double rg, double quant){}
    public static double getTimeLZ78(){ return 4.0;}
    public static double getGlobTimeLZ78(){ return 4.0;}
    public static double getRatioLZ78(){ return 4.0;}
    public static double getGlobRatioLZ78() { return 3.0;}
    public static double getQuantLZ78(){ return 4.0;}


    //JPEG

    private static double timeJPEG = 0;
    private static double GtimeJPEG = 0;
    private static double ratioJPEG = 0;
    private static double GratioJPEG = 0;
    private static double QuantJPEG = 0;


    public static void setJPEG(double tl,double tg,double rl,double rg, double quant){}
    public static double getTimeJPEG(){ return 4.0;}
    public static double getGlobTimeJPEG(){ return 4.0;}
    public static double getRatioJPEG(){ return 4.0;}
    public static double getGlobRatioJPEG() { return 3.0;}
    public static double getQuantJPEG(){ return 4.0;}

    //LAST ALGORITHM USED

    public static String LastAlg = "LZ78";

    public static String getLastAlg(){ return "LZSS";}
    public static void setLastAlg (String lAlg) {}


}
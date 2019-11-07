package Controlador_Estadisticas;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class Cont_Est {

    public static class Estadístiques{ //Stats Stub

        //LZW

        private static double timeLZW;
        private static double GtimeLZW;
        private static double ratioLZW;
        private static double GratioLZW;
        private static double QuantLZW;

        public static void setLZW(double tl,double tg,double rl,double rg, double quant){}
        public static double getTimeLZW(){ return 4.0;}
        public static double getGlobTimeLZW(){ return 4.0;}
        public static double getRatioLZW(){ return 4.0;}
        public static double getGlobRatioLZW() { return 3.0;}
        public static double getQuantLZW(){ return 4.0;}



        //LZ78

        private static double timeLZ78;
        private static double GtimeLZ78;
        private static double ratioLZ78;
        private static double GratioLZ78;
        private static double QuantLZ78;

        public static void setLZSS(double tl,double tg,double rl,double rg, double quant){}
        public static double getTimeLZSS(){ return 4.0;}
        public static double getGlobTimeLZSS(){ return 4.0;}
        public static double getRatioLZSS(){ return 4.0;}
        public static double getGlobRatioLZSS() { return 3.0;}
        public static double getQuantLZSS(){ return 4.0;}



        //LZSS

        private static double timeLZSS;
        private static double GtimeLZSS;
        private static double ratioLZSS;
        private static double GratioLZSS;
        private static double QuantLZSS;

        public static void setLZ78(double tl,double tg,double rl,double rg, double quant){}
        public static double getTimeLZ78(){ return 4.0;}
        public static double getGlobTimeLZ78(){ return 4.0;}
        public static double getRatioLZ78(){ return 4.0;}
        public static double getGlobRatioLZ78() { return 3.0;}
        public static double getQuantLZ78(){ return 4.0;}


        //JPEG

        private static double timeJPEG;
        private static double GtimeJPEG;
        private static double ratioJPEG;
        private static double GratioJPEG;
        private static double QuantJPEG;


        public static void setJPEG(double tl,double tg,double rl,double rg, double quant){}
        public static double getTimeJPEG(){ return 4.0;}
        public static double getGlobTimeJPEG(){ return 4.0;}
        public static double getRatioJPEG(){ return 4.0;}
        public static double getGlobRatioJPEG() { return 3.0;}
        public static double getQuantJPEG(){ return 4.0;}

        //LAST ALGORITHM USED

        public static String getLastAlg(){ return "LZSS";}
        public static void setLastAlg (String lAlg) {}


    }



    public static void main() throws  IOException{
     Stats_Update();
     GetStats();
    }


    public static void Stats_Update() throws  IOException {

        //Update stats from last compression

        Estadístiques E = new Estadístiques();
        File out = new File("/home/clums/Escriptori/Estadisticas.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(out));

        // Save Stats LZW

        Estadístiques.timeLZW = E.getTimeLZW();
        Estadístiques.GtimeLZW = E.getGlobTimeLZW();
        Estadístiques.ratioLZW = E.getRatioLZW();
        Estadístiques.GratioLZW = E.getGlobRatioLZW();
        Estadístiques.QuantLZW = E.getQuantLZW();

        pw.print(0+Estadístiques.timeLZW+Estadístiques.GtimeLZW+Estadístiques.ratioLZW+Estadístiques.GratioLZW+Estadístiques.QuantLZW);

        // Save Stats LZSS

        Estadístiques.timeLZSS = E.getTimeLZSS();
        Estadístiques.GtimeLZSS = E.getGlobTimeLZSS();
        Estadístiques.ratioLZSS = E.getRatioLZSS();
        Estadístiques.GratioLZSS = E.getGlobRatioLZSS();
        Estadístiques.QuantLZSS = E.getQuantLZSS();

        pw.print(1+Estadístiques.timeLZSS+Estadístiques.GtimeLZSS+Estadístiques.ratioLZSS+Estadístiques.GratioLZSS+Estadístiques.QuantLZSS);

        // Save Stats LZ78

        Estadístiques.timeLZ78 = E.getTimeLZ78();
        Estadístiques.GtimeLZ78 = E.getGlobTimeLZ78();
        Estadístiques.ratioLZ78 = E.getRatioLZ78();
        Estadístiques.GratioLZ78 = E.getGlobRatioLZ78();
        Estadístiques.QuantLZ78 = E.getQuantLZ78();

        pw.print(2+Estadístiques.timeLZ78+Estadístiques.GtimeLZ78+Estadístiques.ratioLZ78+Estadístiques.GratioLZ78+Estadístiques.QuantLZ78);


        // Save Stats JPEG

        Estadístiques.timeJPEG = E.getTimeJPEG();
        Estadístiques.GtimeJPEG = E.getGlobTimeJPEG();
        Estadístiques.ratioJPEG = E.getRatioJPEG();
        Estadístiques.GratioJPEG = E.getGlobRatioJPEG();
        Estadístiques.QuantJPEG = E.getQuantJPEG();

        pw.print(3+Estadístiques.timeJPEG+Estadístiques.GtimeJPEG+Estadístiques.ratioJPEG+Estadístiques.GratioJPEG+Estadístiques.QuantJPEG);

    }

        public static void GetStats () throws IOException{ //Set stats from file to classes


            File OldStats = new File("/home/clums/Escriptori/Estadisticas.txt");
            BufferedReader file = new BufferedReader(new FileReader(OldStats));

            Estadístiques oldE = new Estadístiques();
            int act;
            String line = "";
            int flag;
            while ((act = file.read()) != -1) {

                if (act == 0 | act == 1 | act == 2 | act == 3 | act == 4) {
                    flag = act;
                    line = file.readLine();

                // Get numeric values from file : timelast,timeglob,ratelast,rateglob,quant

                     List<Double> values = new ArrayList<>(5);
                        for (int i = 0; i < line.length(); ++i) {
                        String actnumber = "";
                            while (line.charAt(i) != ',') {
                                actnumber += line.charAt(i);
                            }
                        values.add(Double.parseDouble(actnumber));

                            // Set stats to algorithms
                        switch (flag) {
                            case 0: //LZW

                                oldE.setLZW(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
                                break;

                            case 1: //LZ78

                                oldE.setLZ78(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
                                break;

                            case 2: //LZSS

                                oldE.setLZSS(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
                                break;

                            case 3: //JPEG

                                oldE.setJPEG(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
                                break;

                            case 4: //LastAlgorithmUsed

                                oldE.setLastAlg(line);

                            default:
                                break;
                    }
                }
            }
        }
    }
}

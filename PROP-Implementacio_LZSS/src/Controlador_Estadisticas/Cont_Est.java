package Controlador_Estadisticas;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class Cont_Est {

    public static class Estadístiques{ //Stats Stub

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



    public static void main() throws  IOException{

        Stats_Update();

        System.out.println(Estadístiques.timeJPEG+Estadístiques.GratioJPEG+Estadístiques.GtimeJPEG);

        GetStats();

        System.out.println(Estadístiques.timeJPEG+Estadístiques.GratioJPEG+Estadístiques.GtimeJPEG);



    }


    public static void Stats_Update() throws  IOException {

        //Update stats from last compression

        Estadístiques E = new Estadístiques();
        File out = new File("/home/clums/Escriptori/UpdateEstadisticas.txt");
        if (!out.exists()) out.createNewFile();
        PrintWriter pw = new PrintWriter(new FileWriter(out));

        // Save Stats LZW

        Estadístiques.timeLZW = E.getTimeLZW();
        Estadístiques.GtimeLZW = E.getGlobTimeLZW();
        Estadístiques.ratioLZW = E.getRatioLZW();
        Estadístiques.GratioLZW = E.getGlobRatioLZW();
        Estadístiques.QuantLZW = E.getQuantLZW();

        pw.print(0+Estadístiques.timeLZW+','+Estadístiques.GtimeLZW+','+Estadístiques.ratioLZW+','+Estadístiques.GratioLZW+','+Estadístiques.QuantLZW+"\n");

        // Save Stats LZSS

        Estadístiques.timeLZSS = E.getTimeLZSS();
        Estadístiques.GtimeLZSS = E.getGlobTimeLZSS();
        Estadístiques.ratioLZSS = E.getRatioLZSS();
        Estadístiques.GratioLZSS = E.getGlobRatioLZSS();
        Estadístiques.QuantLZSS = E.getQuantLZSS();

        pw.print(1+Estadístiques.timeLZSS+','+Estadístiques.GtimeLZSS+','+Estadístiques.ratioLZSS+','+Estadístiques.GratioLZSS+','+Estadístiques.QuantLZSS+"\n");

        // Save Stats LZ78

        Estadístiques.timeLZ78 = E.getTimeLZ78();
        Estadístiques.GtimeLZ78 = E.getGlobTimeLZ78();
        Estadístiques.ratioLZ78 = E.getRatioLZ78();
        Estadístiques.GratioLZ78 = E.getGlobRatioLZ78();
        Estadístiques.QuantLZ78 = E.getQuantLZ78();

        pw.print(2+Estadístiques.timeLZ78+','+Estadístiques.GtimeLZ78+','+Estadístiques.ratioLZ78+','+Estadístiques.GratioLZ78+','+Estadístiques.QuantLZ78+"\n");


        // Save Stats JPEG

        Estadístiques.timeJPEG = E.getTimeJPEG();
        Estadístiques.GtimeJPEG = E.getGlobTimeJPEG();
        Estadístiques.ratioJPEG = E.getRatioJPEG();
        Estadístiques.GratioJPEG = E.getGlobRatioJPEG();
        Estadístiques.QuantJPEG = E.getQuantJPEG();

        pw.print(3+Estadístiques.timeJPEG+','+Estadístiques.GtimeJPEG+','+Estadístiques.ratioJPEG+','+Estadístiques.GratioJPEG+','+Estadístiques.QuantJPEG+ "\n" );

        //Save Last Algorithm

        Estadístiques.LastAlg = E.getLastAlg();

        pw.print(4+Estadístiques.LastAlg);

    }

        public static void GetStats () throws IOException{ //Set stats from file to classes


            File OldStats = new File("/home/clums/Escriptori/NuevasEstadisticas.txt");

            //Creation of the file if needed

            if (!OldStats.exists()) {
                try {
                    if (OldStats.createNewFile()) {
                        PrintWriter pw = new PrintWriter(new FileWriter(OldStats));
                        pw.print('0' + ',' + '0' + ',' + '0' + ',' + '0' + ',' + '0' + '0' + "\n"); //LZW
                        pw.print('1' + ',' + '0' + ',' + '0' + ',' + '0' + ',' + '0' + '0' + "\n"); //LZSS
                        pw.print('2' + ',' + '0' + ',' + '0' + ',' + '0' + ',' + '0' + '0' + "\n"); //LZ78
                        pw.print('3' + ',' + '0' + ',' + '0' + ',' + '0' + ',' + '0' + '0' + "\n"); //JPEG
                        pw.print('4' + ',' + "null");                               //LAST ALGORITHM USED
                    }
                }
                catch (Exception FileAlreadyExists){
                    System.err.print(FileAlreadyExists);
                }
            }

            BufferedReader file = new BufferedReader(new FileReader(OldStats));

            Estadístiques oldE = new Estadístiques();
            int act;
            String line = "";
            int id;
            while ((act = file.read()) != -1) {

                if (act == 0 | act == 1 | act == 2 | act == 3 | act == 4) {
                    id = act;
                    line = file.readLine();

                // Get numeric values from file : timelast,timeglob,ratelast,rateglob,quant

                     List<Double> values = new ArrayList<>(5);
                        for (int i = 0; i < line.length(); ++i) {
                            String actnumber = "";
                            while (line.charAt(i) != ',') {
                                actnumber += line.charAt(i);
                                ++i;
                            }
                            values.add(Double.parseDouble(actnumber));
                        }

                // Set stats to algorithms
                        switch (id) {
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

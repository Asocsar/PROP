package Controlador_Estadistiques;
import Estadístiques.Estadistiques;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class Cont_Est {

    public void Stats_Update() throws  IOException {

        //Update stats from last compression

        Estadistiques E = new Estadistiques();
        File out = new File("/home/clums/Escriptori/UpdateEstadisticas.txt");
        if (!out.exists()) out.createNewFile();
        PrintWriter pw = new PrintWriter(new FileWriter(out));

        // Save Stats LZW

        Estadistiques.timeLZW = E.getTimeLZW();
        Estadistiques.GtimeLZW = E.getGlobTimeLZW();
        Estadistiques.ratioLZW = E.getRatioLZW();
        Estadistiques.GratioLZW = E.getGlobRatioLZW();
        Estadistiques.QuantLZW = E.getQuantLZW();

        pw.print(0+Estadistiques.timeLZW+','+Estadistiques.GtimeLZW+','+Estadistiques.ratioLZW+','+Estadistiques.GratioLZW+','+Estadistiques.QuantLZW+"\n");

        // Save Stats LZSS

        Estadistiques.timeLZSS = E.getTimeLZSS();
        Estadistiques.GtimeLZSS = E.getGlobTimeLZSS();
        Estadistiques.ratioLZSS = E.getRatioLZSS();
        Estadistiques.GratioLZSS = E.getGlobRatioLZSS();
        Estadistiques.QuantLZSS = E.getQuantLZSS();

        pw.print(1+Estadistiques.timeLZSS+','+Estadistiques.GtimeLZSS+','+Estadistiques.ratioLZSS+','+Estadistiques.GratioLZSS+','+Estadistiques.QuantLZSS+"\n");

        // Save Stats LZ78

        Estadistiques.timeLZ78 = E.getTimeLZ78();
        Estadistiques.GtimeLZ78 = E.getGlobTimeLZ78();
        Estadistiques.ratioLZ78 = E.getRatioLZ78();
        Estadistiques.GratioLZ78 = E.getGlobRatioLZ78();
        Estadistiques.QuantLZ78 = E.getQuantLZ78();

        pw.print(2+Estadistiques.timeLZ78+','+Estadistiques.GtimeLZ78+','+Estadistiques.ratioLZ78+','+Estadistiques.GratioLZ78+','+Estadistiques.QuantLZ78+"\n");


        // Save Stats JPEG

        Estadistiques.timeJPEG = E.getTimeJPEG();
        Estadistiques.GtimeJPEG = E.getGlobTimeJPEG();
        Estadistiques.ratioJPEG = E.getRatioJPEG();
        Estadistiques.GratioJPEG = E.getGlobRatioJPEG();
        Estadistiques.QuantJPEG = E.getQuantJPEG();

        pw.print(3+Estadistiques.timeJPEG+','+Estadistiques.GtimeJPEG+','+Estadistiques.ratioJPEG+','+Estadistiques.GratioJPEG+','+Estadistiques.QuantJPEG+ "\n" );

        //Save Last Algorithm

        Estadistiques.LastAlg = E.getLastAlg();

        pw.print(4+Estadistiques.LastAlg);

    }

    public void GetStats () throws IOException{ //Set stats from file to classes


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

        Estadistiques oldE = new Estadistiques();
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

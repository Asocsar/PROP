package Controlador_Estadistiques;
import Estadístiques.Estadistiques;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;



public class Cont_Est {




   /* public static void main() throws  IOException{
        Stats_Update();
        System.out.println(Estadístiques.timeJPEG+Estadístiques.GratioJPEG+Estadístiques.GtimeJPEG);
        GetStats();
        System.out.println(Estadístiques.timeJPEG+Estadístiques.GratioJPEG+Estadístiques.GtimeJPEG);
    }*/

    //DESCRIPCIÓ DEL MÈTODE : S'actualitza l'arxiu de text d'estadístiques amb les últimes generades.
    //PRE: Cert
    //POST: L'arxiu d'stats ha estat actualitzat
    //EXCEPCIONS: IOException

    public static void Stats_Update() throws  IOException {

        //Update stats from last compression

        Estadistiques E = new Estadistiques();
        File out = new File("Estadisticas.txt");
        if (!out.exists()) out.createNewFile();
        PrintWriter pw = new PrintWriter(new FileWriter(out));

        // Save Stats LZW

        E.setLZW(E.getTimeLZW(),E.getGlobTimeLZW(),E.getRatioLZW(),E.getGlobRatioLZW(),E.getQuantLZW());


        pw.print(0+E.getTimeLZW()+","+E.getGlobTimeLZW()+","+E.getRatioLZW()+","+E.getGlobRatioLZW()+","+E.getQuantLZW()+"\n");

        // Save Stats LZSS

        E.setLZSS(E.getTimeLZSS(),E.getGlobTimeLZSS(),E.getRatioLZSS(),E.getGlobRatioLZSS(),E.getQuantLZSS());


        pw.print(1+E.getTimeLZSS()+","+E.getGlobTimeLZSS()+","+E.getRatioLZSS()+","+E.getGlobRatioLZSS()+","+E.getQuantLZSS()+"\n");


        // Save Stats LZ78

        E.setLZ78(E.getTimeLZ78(),E.getGlobTimeLZ78(),E.getRatioLZ78(),E.getGlobRatioLZ78(),E.getQuantLZ78());

        pw.print(2+E.getTimeLZ78()+","+E.getGlobTimeLZ78()+","+E.getRatioLZ78()+","+E.getGlobRatioLZ78()+","+E.getQuantLZ78()+"\n");


        // Save Stats JPEG

        E.setJPEG(E.getTimeJPEG(),E.getGlobTimeJPEG(),E.getRatioJPEG(),E.getGlobRatioJPEG(),E.getQuantJPEG());

        pw.print(3+E.getTimeJPEG()+","+E.getGlobTimeJPEG()+","+E.getRatioJPEG()+","+E.getGlobRatioJPEG()+","+E.getQuantJPEG()+"\n");

        //Save Last Algorithm

        E.setLastAlg(E.getLastAlg());

        pw.print(4+E.getLastAlg());

    }


    //DESCRIPCIÓ DEL MÈTODE : Obtenim les estadístiques de l'arxiu i inicialitzem els valors d'aquestes en la classe Estadistiques
    //PRE: Cert
    //POST: La classe estadístiques ha estat inicialitzada amb els valors que ja teniem, si no en teníem, s'ha creat l'arxiu inicialitzat tot a "null"
    //EXCEPCIONS: IOException i FileAlreadyExists(no s'ha pogut crear perquè ja existia)

    public static void GetStats () throws IOException{ //Set stats from file to classes


        File OldStats = new File("Estadisticas.txt");

        //Creation of the file if needed

        if (!OldStats.exists()) {
            try {
                if (OldStats.createNewFile()) {
                    PrintWriter pw = new PrintWriter(new FileWriter(OldStats));
                    pw.print("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "\n");   //LZW
                    pw.print("1" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "\n");   //LZSS
                    pw.print("2" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "\n");   //LZ78
                    pw.print('3' + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "\n");   //JPEG
                    pw.print('4' + "," + "null");                                                       //LAST ALGORITHM USED
                }
            }
            catch (Exception FileAlreadyExists){
                throw FileAlreadyExists;
            }
        }

        BufferedReader file = new BufferedReader(new FileReader(OldStats));

        Estadistiques oldE = new Estadistiques();
        double act;
        String line = "";
        int id;
        while ((act = file.read()) != -1) {

            if (act == 0 | act == 1 | act == 2 | act == 3 | act == 4) {
                id = (int)act;
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
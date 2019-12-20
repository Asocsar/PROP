package Controlador_Estadistiques;
import Estadístiques.Estadistiques;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cont_Est {

    /*public static void main (String[] args) throws IOException {
        GetStats();
        Stats_Update();
        Map<String,List<Double>> temp = new HashMap<>();
        temp = Estadistiques.getparam();
        System.out.print(temp.values());
    }*/

    /** \brief Actualitza l'arxiu d'estadístiques
     \pre Cert
     \post L'arxiu d'stats ha estat actualitzat amb les últimes generades
     \throw IOException
     */

    public static void Stats_Update() throws IOException {

        //Update stats from last compression
         Estadistiques E = new Estadistiques();
         File out = new File("Estadisticas.txt");
         if (!out.exists()) out.createNewFile();
         PrintWriter pw = new PrintWriter(new FileWriter(out));
         pw.print("");
         pw.close();
         pw = new PrintWriter(new FileWriter(out));

         List<Double> actalg = new ArrayList<>();

         // Save Stats LZW

         actalg = Estadistiques.getstatsLZW();//act(E.getTimeLZW(), E.getGlobTimeLZW(), E.getDTimeLZW(), E.getDGlobTimeLZW(), E.getRatioLZW(), E.getGlobRatioLZW(), E.getVelLZW(), E.getQuantLZW(), E.getDQuantLZW());
         E.setLZW(actalg);


         pw.print(0 + "," + actalg.get(0) + "," + actalg.get(1) + "," + actalg.get(2) + "," + actalg.get(3) + "," + actalg.get(4) + "," + actalg.get(5) + "," + actalg.get(6) + "," + actalg.get(7) + "," + actalg.get(8) + "\n");

         // Save Stats LZSS

         actalg = Estadistiques.getstatsLZSS();//act(E.getTimeLZSS(), E.getGlobTimeLZSS(), E.getDTimeLZSS(), E.getDGlobTimeLZSS(), E.getRatioLZSS(), E.getGlobRatioLZSS(), E.getVelLZSS(), E.getQuantLZSS(), E.getDQuantLZSS());
         E.setLZSS(actalg);


         pw.print(1 + "," + actalg.get(0) + "," + actalg.get(1) + "," + actalg.get(2) + "," + actalg.get(3) + "," + actalg.get(4) + "," + actalg.get(5) + "," + actalg.get(6) + "," + actalg.get(7) + "," + actalg.get(8) + "\n");


         // Save Stats LZ78

         actalg = Estadistiques.getstatsLZ78();//act(E.getTimeLZ78(), E.getGlobTimeLZ78(), E.getDTimeLZ78(), E.getDGlobTimeLZ78(), E.getRatioLZ78(), E.getGlobRatioLZ78(), E.getVelLZ78(), E.getQuantLZ78(), E.getDQuantLZ78());
         E.setLZ78(actalg);

         pw.print(2 + "," + actalg.get(0) + "," + actalg.get(1) + "," + actalg.get(2) + "," + actalg.get(3) + "," + actalg.get(4) + "," + actalg.get(5) + "," + actalg.get(6) + "," + actalg.get(7) + "," + actalg.get(8) + "\n");


         // Save Stats JPEG

         actalg = Estadistiques.getstatsJPEG();//act(E.getTimeJPEG(), E.getGlobTimeJPEG(), E.getDTimeJPEG(), E.getDGlobTimeJPEG(), E.getRatioJPEG(), E.getGlobRatioJPEG(), E.getVelJPEG(), E.getQuantJPEG(), E.getDQuantJPEG());
         E.setJPEG(actalg);

         pw.print(3 + "," + actalg.get(0) + "," + actalg.get(1) + "," + actalg.get(2) + "," + actalg.get(3) + "," + actalg.get(4) + "," + actalg.get(5) + "," + actalg.get(6) + "," + actalg.get(7) + "," + actalg.get(8) + "\n");

         //Save Last Algorithm

         E.setLastAlg(E.getLastAlg());

         pw.print(4 + "," + E.getLastAlg());

         pw.close();


    }


    /** \brief Obtenció d'estadístiques de l'arxiu
     \pre Cert
     \post La classe estadístiques ha estat inicialitzada amb els valors que ja teniem, si no en teníem, s'ha creat l'arxiu inicialitzat tot a "null"
     \throw IOException i FileAlreadyExists(no s'ha pogut crear perquè ja existia)
     */


    public static void GetStats() throws IOException{ //Set stats from file to classes


        File OldStats = new File("Estadisticas.txt");

        //Creation of the file if needed

        if (!OldStats.exists()) {
            try {
                if (OldStats.createNewFile()) {
                    PrintWriter pw = new PrintWriter(new FileWriter(OldStats));
                    pw.print("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "\n");   //LZW
                    pw.print("1" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "\n");   //LZSS
                    pw.print("2" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "\n");   //LZ78
                    pw.print("3" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "\n");   //JPEG
                    pw.print("4" + "," + "Cap");                                                                                                       //LAST ALGORITHM USED
                    pw.close();
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
            act = Double.parseDouble(Character.toString((char) act));
            if (act == 0 | act == 1 | act == 2 | act == 3 | act == 4) {
                id = (int)act;
                line = file.readLine();

                // Get numeric values from file : timelast,timeglob,ratelast,rateglob,quant

                List<Double> values = new ArrayList<>(9);
                String cas_especial = "";
                for (int i = 0; i < line.length(); ++i) {
                    String actnumber = "";
                    while (i < line.length() && line.charAt(i) != ',') {
                        actnumber += line.charAt(i);
                        ++i;
                        //System.out.println(i);
                    }
                    if (actnumber.equals("Cap")) actnumber = "-1";
                    if (id == 4) cas_especial = actnumber;
                    else if (actnumber != "") values.add(Double.parseDouble(actnumber));
                }

                // Set stats to algorithms
                switch (id) {
                    case 0: //LZW

                        oldE.setLZW(values);
                        break;

                    case 1: //LZ78

                        oldE.setLZ78(values);
                        break;

                    case 2: //LZSS

                        oldE.setLZSS(values);
                        break;

                    case 3: //JPEG

                        oldE.setJPEG(values);
                        break;

                    case 4: //LastAlgorithmUsed

                        oldE.setLastAlg(cas_especial);

                    default:
                        break;
                }
            }
        }
    }
}
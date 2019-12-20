package Controlador_Estadistiques;
import Estadístiques.Estadistiques;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cont_Est {



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

         actalg = Estadistiques.getstatsLZW();
         Estadistiques.setLZW(actalg);


         pw.print(0 + "," + actalg.get(0) + "," + actalg.get(1) + "," + actalg.get(2) + "," + actalg.get(3) + "," + actalg.get(4) + "," + actalg.get(5) + "," + actalg.get(6) + "," + actalg.get(7) + "," + actalg.get(8) + "\n");

         // Save Stats LZSS

         actalg = Estadistiques.getstatsLZSS();
         Estadistiques.setLZSS(actalg);


         pw.print(1 + "," + actalg.get(0) + "," + actalg.get(1) + "," + actalg.get(2) + "," + actalg.get(3) + "," + actalg.get(4) + "," + actalg.get(5) + "," + actalg.get(6) + "," + actalg.get(7) + "," + actalg.get(8) + "\n");


         // Save Stats LZ78

         actalg = Estadistiques.getstatsLZ78();
         Estadistiques.setLZ78(actalg);

         pw.print(2 + "," + actalg.get(0) + "," + actalg.get(1) + "," + actalg.get(2) + "," + actalg.get(3) + "," + actalg.get(4) + "," + actalg.get(5) + "," + actalg.get(6) + "," + actalg.get(7) + "," + actalg.get(8) + "\n");


         // Save Stats JPEG

         actalg = Estadistiques.getstatsJPEG();
         Estadistiques.setJPEG(actalg);

         pw.print(3 + "," + actalg.get(0) + "," + actalg.get(1) + "," + actalg.get(2) + "," + actalg.get(3) + "," + actalg.get(4) + "," + actalg.get(5) + "," + actalg.get(6) + "," + actalg.get(7) + "," + actalg.get(8) + "\n");

         //Save Last Algorithm

         Estadistiques.setLastAlg(Estadistiques.getLastAlg());

         pw.print(4 + "," + Estadistiques.getLastAlg());

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

                        Estadistiques.setLZW(values);
                        break;

                    case 1: //LZ78

                        Estadistiques.setLZ78(values);
                        break;

                    case 2: //LZSS

                        Estadistiques.setLZSS(values);
                        break;

                    case 3: //JPEG

                        Estadistiques.setJPEG(values);
                        break;

                    case 4: //LastAlgorithmUsed

                        Estadistiques.setLastAlg(cas_especial);

                    default:
                        break;
                }
            }
        }
    }
}
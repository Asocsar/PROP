package Algoritmes.JPEG;


import com.sun.org.apache.bcel.internal.generic.IFLE;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Driver_JPEG {

    /*
    El driver de l'algorisme JPEG prova la funcionalitat de l'algorisme de compressió i descompressió.
    Et demana 3 paràmetres:
        1. El path d'origen del fitxer PPM
        2. El path destí del fitxer comprimit .fG, sense extensió.
        3. El path destí del fitxer descomprimit PPM, per comparar-lo amb l'original.

     */
    public static void main(String[] args) throws IOException {
        try {
            /*
            //Provar compress per fer l'exemple d wikipedia'
            JPEG J = new JPEG(50);


            int[][] m = {
                    {52, 55, 61, 66, 70, 61, 64, 73},
                    {63, 59, 55, 90, 109, 85, 69, 72},
                    {62, 59, 68, 113, 144, 104, 66, 73},
                    {63, 58, 71, 122, 154, 106, 70, 69},
                    {67, 61, 68, 104, 126, 88, 68, 70},
                    {79, 65, 60, 70, 77, 68, 58, 75},
                    {85, 71, 64, 59, 55, 61, 65, 83},
                    {87, 79, 69, 68, 65, 76, 78, 94}};

            byte[] b = J.compress8(m);
            System.out.println("Length: "+ b.length);
            System.out.println(new String(b));







            //System.out.println(s);
            int[][] m2 = J.decompress8(J.compress8(m));

            for(int i = 0; i < 8; ++i){
                for(int j = 0; j < 8; ++j){
                    if(m[i][j] != m2[i][j]) System.out.println("Diferència m: "+m[i][j] + " || " + m2[i][j]);
                }
                System.out.println();
            }
            

            */

            Scanner S = new Scanner(System.in);
            /*
            System.out.println("Introdueix el path del fitxer a comprimir");
            System.out.println("Exemple: /home/usr/fitxer");
            String path = S.next();
            */
            String path = "/home/maller/Downloads/PROP/codi/JPEG/Jocsdeprova/internet/compressed/vuit.ppm";

            File infile = new File(path);

            System.out.println("Introdueix la qualitat de compressió (0-100)");
            int quality =  Integer.parseInt(S.next());
            JPEG J  = new JPEG(quality);


            byte [] buffin = Files.readAllBytes(infile.toPath());
            //Crida a compress
            byte[] buffout = J.compress(buffin);

            //Agafar estadístiques
            System.out.println("Ratio " + J.getRate());
            System.out.println("Temps " + J.getTime());

            //Escriptura a fitxer .fG
            System.out.println("Introdueix path de destí del fitxer comprimit (fitxer sense extensió)");
            path = S.next();
            File f2 = new File (path + ".fg");
            FileOutputStream fo = new FileOutputStream (f2);
            fo.write(buffout);
            fo.close();

            infile = new File(path + ".fg");
            buffin = Files.readAllBytes(infile.toPath());
            buffout = J.descompress(buffin);

            System.out.println("Introdueix path de destí del fitxer descomprimit (fitxer sense extensió)");

            String path2 = S.next();
            f2 = new File (path2 + ".ppm");
            fo = new FileOutputStream (f2);
            fo.write(buffout);
            fo.close();


            System.out.println("JPEG acabat amb èxit.");


        } catch (FileNotFoundException e) {
            System.out.println("L'adreça no ha estat trobada");

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error en la Entrada - Sortida");


        } catch (JPEG.JPEGException e) {
            e.printStackTrace();
        }

             /*

            String outfile = "/home/maller/Downloads/PROP/codi/JPEG/Jocsdeprova/internet/compressed/vuit.ppm";
            FileOutputStream fo = new FileOutputStream(outfile);
            ByteArrayOutputStream out;
            StringBuilder sb  = new StringBuilder();
            out = new ByteArrayOutputStream();

            sb.append("P6\n");
            sb.append(8).append(" ").append(8).append("\n");
            sb.append("255\n");
            out.write(sb.toString().getBytes());

            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    for(int a = 0; a < 3; ++a) {
                        out.write(m[i][j]);
                    }
                }
            }

            fo.write(out.toByteArray());
            fo.close();

            */



    }
}
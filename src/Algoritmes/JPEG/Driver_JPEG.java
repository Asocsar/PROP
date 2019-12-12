package Algoritmes.JPEG;


import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;


public class Driver_JPEG {

    /*
    El driver de l'algorisme JPEG prova la funcionalitat de l'algorisme de compressió i descompressió.
    Et demana 3 paràmetres:
        1. El path d'origen del fitxer PPM
        2. El path destí del fitxer comprimit .fG, sense extensió.
        3. El path destí del fitxer descomprimit PPM, per comparar-lo amb l'original.

     */
    public static void main(String[] args) {
        try {
            Scanner S = new Scanner(System.in);
            System.out.println("Introdueix el path del fitxer a comprimir");
            System.out.println("Exemple: /home/usr/fitxer");
            String path = S.next();
            File infile = new File(path);

            System.out.println("Introdueix la qualitat de compressió (0-100)");
            int quality =  Integer.parseInt(S.next());
            JPEG J = new JPEG(quality);


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
    }
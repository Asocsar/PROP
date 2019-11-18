package Algoritmes.LZW;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Driver_LZW {
    public static void main(String[] args) throws Exception {
        //Escollim el fitxer a comprimir
        //Scanner S = new Scanner(System.in);
        //String name = "juego2";
        String camino = /*"Unextended" */"Extended ASCII";
        File file_aux = new File("/home/asocar/Desktop/pruebas/" + camino);
        String[] directories = file_aux.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        for (String name : directories) {
            System.out.println("Fitxer " + name + " comprimit i descomprimit");
            //System.out.println("Introdueixi el path del fitxer a comprimir");
            //String path = S.next();
            String path = "/home/asocar/Desktop/pruebas/" + camino + "/" + name + "/" + name + ".txt";
            File file = new File(path);
            //Llegim tots els bytes del fitxer
            byte[] b = Files.readAllBytes(file.toPath());
            LZW A = new LZW();
            long r = 3;
            //Comprimim el contingut
            List<Integer> s = A.compress(b);
            //Mostrem el rati de compressió assolit
            //System.out.println("Ratio " + A.getRate());
            //System.out.println("Tiempo " + A.getTime());
            // escribim el contingut en un fitxer codificat
            //System.out.println("Introdueixi path de destí i nom del fitxer (no fa falta especificar la extensio)");
            //System.out.println("Exemple \\home\\usr\\fitxer");
            //path = S.next();
            //path = path + ".fW";
            path = "/home/asocar/Desktop/pruebas/" + camino + "/" + name + "/" + name + ".fW";
            FileWriter file_o = new FileWriter(path);
            for (int i = 0; i < s.size(); ++i) {
                if (s.get(i) != null) {
                    int n = s.get(i);
                    file_o.write(n);
                }
            }
            file_o.close();
            //Llegim el contingut d'aquest fitxer
            FileReader file_r = new FileReader(path);
            List<Integer> g = new ArrayList<>();
            int n = 0;
            while ((n = file_r.read()) != -1) {
                g.add(n);
            }
            file_r.close();
            // descomprimir el contingut del fitxer
            byte[] out = A.descomprimir(g);
            /*System.out.println("Tiempo " + A.getTime());
            System.out.println("Introdueixi el path desti del fitxer descomprimit (no fa falta afegir extensió)");
            System.out.println("Exemple \\home\\usr\\nombre");*/
            //path = S.next();
            //path = path + ".txt";
            path = "/home/asocar/Desktop/pruebas/" + camino + "/" + name + "/" + name + "S" + ".txt";
            ;
            File file2 = new File(path);
            //tornem a escriure el seu contingut
            Files.write(file2.toPath(), out);
        }
    }
}


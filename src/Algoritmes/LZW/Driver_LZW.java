package Algoritmes.LZW;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver_LZW {
    public static void main(String[] args) throws Exception {
        //Escollim el fitxer a comprimir
        Scanner S = new Scanner(System.in);
        System.out.println("Introdueixi el path del fitxer a comprimir");
        String path = S.next();
        File file = new File(path);
        //Llegim tots els bytes del fitxer
        byte[] b = Files.readAllBytes(file.toPath());
        LZW A = new LZW();
        long r = 3;
        //Comprimim el contingut
        byte[] s = A.compress(b);
        //Mostrem el rati de compressió assolit
        System.out.println("Ratio " + A.getRate());
        System.out.println("Tiempo " + A.getTime());
        // escribim el contingut en un fitxer codificat
        System.out.println("Introdueixi path de destí i nom del fitxer (no fa falta especificar la extensio)");
        System.out.println("Exemple \\home\\usr\\fitxer");
        path = S.next();
        path = path + ".fW";
        File file_o = new File(path);
        Files.write(file_o.toPath(), s);
        //Llegim el contingut d'aquest fitxer
        byte[] g = Files.readAllBytes(file_o.toPath());
        // descomprimir el contingut del fitxer
        byte[] out = A.descomprimir(g);
        System.out.println("Tiempo " + A.getTime());
        System.out.println("Introdueixi el path desti del fitxer descomprimit (no fa falta afegir extensió)");
        System.out.println("Exemple \\home\\usr\\nombre");
        path = S.next();
        path = path + ".txt";
        File file2 = new File(path);
        //tornem a escriure el seu contingut
        Files.write(file2.toPath(), out);
    }
}


package Algoritmes.LZW;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Driver_LZW {
    public static void main(String[] args) throws Exception {
        //Leemos el fichero a comprimir
        File file = new File("C:\\Users\\Asocs\\Desktop\\Juegos de prueba\\quijote_entero.txt");
        //Leemos los bytes del fichero
        byte[] b = Files.readAllBytes(file.toPath());
        LZW A = new LZW();
        long r = 3;
        //Comprimimos el fichero objetivo
        List<Integer> s = A.compress(b);
        //Mostramos el ratio de compresión y tiempo tardado
        System.out.println("Ratio " + A.getRate());
        System.out.println("Tiempo " + A.getTime());
        FileWriter file_o = new FileWriter("Salida.LZW");
        for (int i = 0; i < s.size(); ++i) {
            if (s.get(i) != null) {
                int n = s.get(i);
                file_o.write(n);
            }
        }
        file_o.close();
        FileReader file_r = new FileReader("Salida.LZW");
        List<Integer> g = new ArrayList<>();
        int n = 0;
        while ((n = file_r.read()) != -1) {
            g.add(n);
        }
        file_r.close();
        byte[] out = A.descomprimir(g);
        System.out.println("Tiempo " + A.getTime());
        File file2 = new File("salida_test.txt");
        Files.write(file2.toPath(), out);
    }
}


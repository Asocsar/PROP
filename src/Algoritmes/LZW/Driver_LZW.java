package Algoritmes.LZW;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver_LZW {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\Asocs\\Desktop\\Juegos de prueba\\Quijote.txt");
        byte[] b = Files.readAllBytes(file.toPath());
        //System.out.println(Arrays.toString(b).substring(b.length-50,b.length));
        /*-------*/
        LZW A = new LZW();
        List<Integer> s = A.compress(b);
        //System.out.println(s.subList(s.size()-50,s.size()));
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
        File file2 = new File("test_salida.txt");
        Files.write(file2.toPath(), out);
    }
}


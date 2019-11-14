package Algoritmes.LZW;

import java.io.*;
import java.nio.file.Files;
import java.lang.Object;
import java.util.ArrayList;
import java.util.List;

public class Driver_LZW {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\Asocs\\Desktop\\entrada.txt");
        byte[] b = Files.readAllBytes(file.toPath());
        /*EXTRA*/
        File extra = new File("C:\\Users\\Asocs\\Desktop\\extra.txt");
        Files.write(extra.toPath(),b);
        /*-------*/
        LZW A = new LZW();
        List<Integer> s = A.compress(b);
        FileWriter file_o = new FileWriter("C:\\Users\\Asocs\\Desktop\\test.LZW");
        for (int i = 0; i < s.size(); ++i) {
            int n = s.get(i);
            file_o.write(n);
        }
        file_o.close();

        FileReader file_r = new FileReader("C:\\Users\\Asocs\\Desktop\\test.LZW");
        List<Integer> g = new ArrayList<>();
        int n = 0;
        while ((n = file_r.read()) != -1) {
            g.add(n);
        }
        file_r.close();
        byte[] out = A.descomprimir(g);
        File file2 = new File("C:\\Users\\Asocs\\Desktop\\test_salida.txt");
        Files.write(file2.toPath(), out);
    }
}


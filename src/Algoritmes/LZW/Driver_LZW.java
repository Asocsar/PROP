package Algoritmes.LZW;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Driver_LZW {
    public static void main(String[] args) throws Exception {
        //Escollim el fitxer a comprimir
        File file = new File("C:\\Users\\Asocs\\Desktop\\Juegos de prueba\\dos_letras.txt");
        //Llegim tots els bytes del fitxer
        byte[] b = Files.readAllBytes(file.toPath());
        LZW A = new LZW();
        long r = 3;
        //Comprimim el contingut
        List<Integer> s = A.compress(b);
        //Mostrem el rati de compressió assolit
        System.out.println("Ratio " + A.getRate());
        System.out.println("Tiempo " + A.getTime());
        // escribim el contingut en un fitxer codificat
        FileWriter file_o = new FileWriter("Salida.LZW");
        for (int i = 0; i < s.size(); ++i) {
            if (s.get(i) != null) {
                int n = s.get(i);
                file_o.write(n);
            }
        }
        file_o.close();
        //Llegim el contingut d'aquest fitxer
        FileReader file_r = new FileReader("Salida.LZW");
        List<Integer> g = new ArrayList<>();
        int n = 0;
        while ((n = file_r.read()) != -1) {
            g.add(n);
        }
        file_r.close();
        // descomprimir el contingut del fitxer
        byte[] out = A.descomprimir(g);
        System.out.println("Tiempo " + A.getTime());
        File file2 = new File("salida_test.txt");
        //tornem a escriure el seu contingut
        Files.write(file2.toPath(), out);
    }
}


package Algoritmes;

import Algoritmes.LZSS;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Driver_LZSS {
    public static void main(String[] args) throws Exception {
        //Leemos el fichero a comprimir
        String dir = "Unextended";
        File file_aux = new File("/home/clums/Escriptori/JOCS_DE_PROVES/" + dir);
        String[] tests = file_aux.list((current, name) -> new File(current, name).isFile());

        //Leemos los bytes del fichero

        assert tests != null;
        for (String test : tests) {
            System.out.println(test + "es comprimirà i descodificarà");
            String path = "/home/clums/Escriptori/JOCS_DE_PROVES/" + "/" + dir + "/" + test + ".txt";
            File file = new File(path);
            byte[] c = Files.readAllBytes(file.toPath());

            LZSS A = new LZSS();
            //Comprimimos el fichero objetivo
            Byte[] compressed = A.compress(c);
            System.out.println("La compressió s'ha donat amb èxit");
            //Mostramos el ratio de compresión y tiempo tardado
            System.out.println("El ratio de compressió ha estat" + ": " + A.getRate() + "\n");
            System.out.println("El temps de compressió ha estat" + ": " + A.getTime() + "\n");

            path = "/home/clums/Escriptori/JOCS_DE_PROVES/" + "/" + dir + "/" + test + ".fS";
            FileWriter file_out = new FileWriter(path);

            for (Byte b : compressed) {
                file_out.write(b + ',');
            }
            file_out.close();

            File filed = new File(path);
            byte[] d = Files.readAllBytes(filed.toPath());

            Byte[] todecod = new Byte[d.length];
            int decodindex = 0;
            for (byte b : d) {
                todecod[decodindex++] = b;
            }

            path = "/home/clums/Escriptori/JOCS_DE_PROVES/" + "/" + dir + "/" + test + ".txt";
            StringBuilder decompressed = A.decompress(todecod);

            File file2 = new File(path);
            Files.write(file2.toPath(), Collections.singleton(decompressed));
        }
    }
}
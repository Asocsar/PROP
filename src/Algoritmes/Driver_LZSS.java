package Algoritmes;

import Algoritmes.LZSS;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Driver_LZSS {
    public static void main(String[] args) throws Exception {
        //Leemos el fichero a comprimir
        int carpetes = 0;
        while (carpetes < 2) {
            Scanner directori = new Scanner(System.in);
            System.out.println("Introdueixi directori: \n 1 : Unextended ASCII tests \n 2: Extended ASCII tests");
            int value = directori.nextInt();
            String dir;
            if (value == 1) dir = "Unextended";
            else dir = "Extended";
            File file_aux = new File("/home/clums/Escriptori/JOCS_DE_PROVA/" + dir);
            String[] tests = file_aux.list((current, name) -> new File(current, name).isFile());

            //Leemos los bytes del fichero

            assert tests != null;
            for (String test : tests) {
                System.out.println(test + " es comprimirà i descodificarà");
                String path = "/home/clums/Escriptori/JOCS_DE_PROVA/" + "/" + dir + "/" + test;
                File file = new File(path);
                byte[] c = Files.readAllBytes(file.toPath());

                //LINIES DE TESTEO DE JOCS: IMPRIMIM EL FITXER QUE ES COMPRIMIRA
                //for(byte b : c) System.out.print(b+',');
                //System.out.print("\n");

                LZSS A = new LZSS();
                //Comprimimos el fichero objetivo
                List<Byte> compressed = A.compress(c);
                System.out.println("La compressió s'ha donat amb èxit");
                //Mostramos el ratio de compresión y tiempo tardado
                System.out.println("El ratio de compressió ha estat" + ": " + A.getRate() + "\n");
                System.out.println("El temps de compressió ha estat" + ": " + A.getTime() + "\n");

                //LINIES DE TESTEO DE JOCS: IMPRIMIM EL FITXER COMPRIMIT

                //for(Byte B : compressed) System.out.print(B+',');

                path = "/home/clums/Escriptori/JOCS_DE_PROVA/" + "/" + dir + "/Compressed/" + test.substring(0, test.length() - 4) + ".fS";
                File file_out = new File(path);

                byte[] towrite = new byte[compressed.size()];
                int writeind = 0;
                for (Byte comp : compressed) {
                    towrite[writeind++] = comp;
                }

                Files.write(file_out.toPath(), towrite);

                File filed = new File(path);
                byte[] d = Files.readAllBytes(filed.toPath());

                List<Byte> todecod = new ArrayList<>();
                for (byte b : d) {
                    todecod.add(b);
                }

                path = "/home/clums/Escriptori/JOCS_DE_PROVA/" + "/" + dir + "/" + test.substring(0, test.length() - 4) + "S" + ".txt";
                List<Byte> decompressed = A.decompress(todecod);

                //for (int i = 0; i<decompressed.length(); ++i) System.out.print(decompressed.charAt(i));


                File file2 = new File(path);
                OutputStream out = new FileOutputStream(file2.toPath().toString());
                byte[] tofile = new byte[decompressed.size()];
                int tofind = 0;
                for(Byte l : decompressed){
                    tofile[tofind++] = l;
                }
                out.write(tofile);
            }
            carpetes++;
        }
    }
}
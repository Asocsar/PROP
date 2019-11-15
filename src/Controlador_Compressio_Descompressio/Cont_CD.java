package Controlador_Compressio_Descompressio;
import Controlador_Compressio_Descompressio.Algoritmes_Stub.*;
import Controlador_Compressio_Descompressio.Controlador_fitxers_stub.Item;

import java.io.*;
import java.util.*;

public class Cont_CD {

    private static String path1 = "";
    private static String path2 = "";

    public Cont_CD (){
    }


    public static void compressio_descompressio(String path, int id, String path_dest, boolean comprimir) throws IOException {
        Item I = new Item(path);
        if (path_dest == "") path_dest = path;
        if (id == 0) {
            I.setPath(path);
            I.analize();
            id = I.best_metod;
        }
        List<Integer> L = new ArrayList<>();
        String S = "";
        double time = 0;
        double rate = 0;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        switch (id) {
            case 1:
                LZ78 L8 = new LZ78();
                if (comprimir) {
                    L = L8.compress(br);
                    time = L8.getTime();
                    rate = L8.getRate();
                }
                else {
                    S = L8.descomprimir(I.getcode());
                    time = L8.getTime();
                    rate = L8.getRate();
                }

                break;
            case 2:
                LZSS LS = new LZSS();
                if (comprimir) {
                    L = LS.compress(br);
                    time = LS.getTime();
                    rate = LS.getRate();
                }
                else {
                    S = LS.descomprimir(I.getcode());
                    time = LS.getTime();
                    rate = LS.getRate();
                }
                break;
            case 3:
                LZW LW = new LZW();
                if (comprimir) {
                    L = LW.compress(br);
                    time = LW.getTime();
                    rate = LW.getRate();

                }
                else {
                    S = LW.descomprimir(I.getcode());
                    time = LW.getTime();
                    rate = LW.getRate();

                }
                break;
            case 4:
                JPEG JG = new JPEG();
                if (comprimir) {
                    L = JG.compress(br);
                    time = JG.getTime();
                    rate = JG.getRate();
                }
                else {
                    S = JG.descomprimir(I.getcode());
                    time = JG.getTime();
                    rate = JG.getRate();
                }
                break;
            }

        /*BufferedWriter F = new BufferedWriter(new FileWriter(path_dest));
        if (comprimir) {
            for (int i = 0; i < L.size(); ++i) {
                F.write(L.get(i) + ",");
            }
        }
        else
            F.write(S);

        F.flush();
        F.close();*/

        if (comprimir) {
            path1 = path;
            path2 = path_dest;
        }

        System.out.println("Tiempo " + time);
        System.out.println("Ratio " + rate);

    }

    public static void comparar() throws IOException {
        System.out.println(path1);
        File file1 = new File(path1);
        BufferedReader br = new BufferedReader(new FileReader(file1));
        int n = br.read();
        String S = "";
        boolean one_more = true;
        while (n != -1) {
            S = S + (char)n;
            n = br.read();
        }
        System.out.println(S);
        System.out.println();
        File file2 = new File(path2);
        BufferedReader gr = new BufferedReader(new FileReader(file2));
        n = gr.read();
        S = "";
        while (n != -1) {
            S = S + (char)n;
            n = gr.read();
        }
        System.out.println(S);
    }
}

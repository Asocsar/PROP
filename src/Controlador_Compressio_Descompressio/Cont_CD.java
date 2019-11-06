package Controlador_Compressio_Descompressio;

import Algoritmes.LZ78;
import Algoritmes.LZSS;
import Algoritmes.LZW;
import Estadístiques.Estadistiques;

import java.io.*;

public class Cont_CD {

    private static String path1 = "";
    private static String path2 = "";

    public Cont_CD (){
    }


    public static void compressio_descompressio(String path, int id, String path_dest, boolean comprimir) throws IOException {
        Item I = new Item();
        I.getid();
        Object L = null;
        double time = 0;
        double rate = 0;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        switch (id) {
            case 1:
                LZ78 L8 = new LZ78();
                if (comprimir) {
                    L = L8.compresio(br);
                    //time = L8.getTime();
                    //rate = L8.getRate();
                }
                else {
                    //L = L8.descompresio(I.getcode());
                    //time = L8.getTime();
                    //rate = L8.getRate();
                }

                break;
            case 2:
                LZSS LS = new LZSS();
                if (comprimir) {
                    L = LS.compress_mine2(br);
                    time = LS.getTime();
                    rate = LS.getRate();
                }
                else {
                    L = LS.decompress(I.getcode());
                    time = LS.getTime();
                    rate = LS.getRate();
                }
                break;
            case 3:
                LZW LW = new LZW();
                if (comprimir) {
                    L = LW.compress(br);
                    //time = LW.getTime();
                    //rate = LW.getRate();

                }
                else {
                    L = LW.descomprimir(I.getcode());
                    //time = LW.getTime();
                    //rate = LW.getRate();

                }
                break;
            /*case 4:
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
                break;*/
            }



        if (comprimir) {
            path1 = path;
            path2 = path_dest;
        }

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

    public static void main(String[] args) {
        try {
            compressio_descompressio("/home/asocar/Desktop/Ejemygplo.txt", 3, "/home/asocar/Desktop/Ejemplo_salida.LZW", true);
            comparar();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

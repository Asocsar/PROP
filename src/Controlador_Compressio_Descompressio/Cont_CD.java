package Controlador_Compressio_Descompressio;

import Algoritmes.LZ78;
import Algoritmes.LZSS;
import Algoritmes.LZW;
import Controlador_ficheros.classe_fichero;
import Estadístiques.Estadistiques;

import java.io.*;

public class Cont_CD {

    private static String path1 = "";
    private static String path2 = "";

    public Cont_CD (){
    }


    private  Object action (int id, boolean comprimir, classe_fichero I) {
        Object L = null;
        double time = 0;
        double rate = 0;
        Estadistiques E = new Estadistiques();
        switch (id) {
            case 1:
                LZ78 L8 = new LZ78();
                if (comprimir) {
                    L = L8.compresio(I.getBuffer());
                    time = L8.get_time();
                    rate = L8.get_time();
                    E.act8(time,rate);
                }
                else {
                    L = L8.descompresio(I.getinfo());
                }

                break;
            case 2:
                LZSS LS = new LZSS();
                if (comprimir) {
                    L = LS.compress_mine2(I.getBuffer());
                    time = LS.getTime();
                    rate = LS.getRate();
                    E.actS(time,rate);
                }
                else {
                    L = LS.decompress(I.getinfo());
                }
                break;
            case 3:
                LZW LW = new LZW();
                if (comprimir) {
                    L = LW.compress(I.getBuffer());
                    time = LW.getTime();
                    rate = LW.getRatio();
                    E.actW(time,rate);

                }
                else {
                    L = LW.descomprimir(I.getinfo());

                }
                break;
            default:
                JPEG JG = new JPEG();
                if (comprimir) {
                    L = JG.compress(I.getBuffer());
                    time = JG.getTime();
                    rate = JG.getRate();
                }
                else {
                    S = JG.descomprimir(I.getinfo());
                    time = JG.getTime();
                    rate = JG.getRate();
                }
                break;
        }
        System.out.println("Time " + time);
        System.out.println("Rate " + rate);
        return  L;
    }

    public void compressio_descompressio(classe_fichero I) throws IOException {
        Object L = null;
        double time = 0;
        double rate = 0;
        Estadistiques E = new Estadistiques();
        int id = I.getid();
        boolean comprimir = I.compress();
        L = action(id, comprimir, I);
        if (comprimir) {
            path1 = I.getpath();
            path2 = I.getpat_dest();
        }
        I.writeFile(L);
    }

    public void comparar() throws IOException {
        classe_fichero I = new classe_fichero(path2, "", false, 0);
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
        int id = I.getid();
        Object L = action(id, false, I);
        System.out.println((String)L);
    }
}

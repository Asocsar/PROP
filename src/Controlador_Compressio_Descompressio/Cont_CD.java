package Controlador_Compressio_Descompressio;

import Algoritmes.*;
import Controlador_ficheros.controlador_gestor_fitxer;
import Estadístiques.Estadistiques;

import java.io.*;

public class Cont_CD {

    private static String path1 = "";
    private static String path2 = "";

    public Cont_CD (){
    }


    private  Object action (int id, boolean comprimir, controlador_gestor_fitxer I) throws controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido, IOException {
        Object L = null;
        double time = 0;
        double rate = 0;
        Estadistiques E = new Estadistiques();
        switch (id) {
            case 1:
                LZ78 L8 = new LZ78();
                if (comprimir) {
                    L = L8.compresio((BufferedReader) I.get_buffer());
                    time = L8.get_time();
                    rate = L8.get_time();
                    E.act8(time,rate);
                }
                else {
                    L = L8.descompresio((BufferedReader) I.get_buffer());
                }

                break;
            case 2:
                LZSS LS = new LZSS();
                if (comprimir) {
                    L = LS.compress_mine2((BufferedReader) I.get_buffer());
                    time = LS.getTime();
                    rate = LS.getRate();
                    E.actS(time,rate);
                }
                else {
                    L = LS.decompress(I.get_buffer());
                }
                break;
            case 3:
                LZW LW = new LZW();
                if (comprimir) {
                    L = LW.compress(I.get_buffer());
                    time = LW.getTime();
                    rate = LW.getRate();
                    E.actW(time,rate);

                }
                else {
                    L = LW.descomprimir(I.get_buffer());

                }
                break;
            default:
                System.out.println("JPEG en etapa de desarrollo");
                /*
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
                }*/
                break;
        }
        System.out.println("Time " + time);
        System.out.println("Rate " + rate);
        return  L;
    }

    public void compressio_descompressio(controlador_gestor_fitxer I) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        Object L = null;
        Estadistiques E = new Estadistiques();
        int id = I.getid();
        boolean comprimir = I.getcompress();
        L = action(id, comprimir, I);
        if (comprimir) {
            path1 = I.getPath_original();
            path2 = I.getPath_desti();
        }
        I.writeFile(L);
    }

    public void comparar() throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer(path2, "", 3, false);
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

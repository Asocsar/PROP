package Controlador_Compressio_Descompressio;

import Controlador_Compressio_Descompressio.Algoritmes_Stub.*;
import Controlador_Compressio_Descompressio.Controlador_fitxers_stub.controlador_gestor_fitxer;
import Controlador_Compressio_Descompressio.Estadisticas.Estadisticas_Stub;

import java.io.*;

public class Cont_CD {

    private static String path1 = "";
    private static String path2 = "";
    private  double time;
    private  double rate;

    public Cont_CD (){
    }


    private  Object action (int id, boolean comprimir, controlador_gestor_fitxer I)  {
        Object L = null;
        double time = 0;
        double rate = 0;
        Estadisticas_Stub E = new Estadisticas_Stub();
        switch (id) {
            case 1:
                LZ78 L8 = new LZ78();
                if (comprimir) {
                    L = L8.compress(I.get_buffer());
                    time = L8.getTime();
                    rate = L8.getRate();
                    E.act8(time,rate);
                }
                else {
                    L = L8.descomprimir( I.get_buffer());
                }

                break;
            case 2:
                LZSS LS = new LZSS();
                if (comprimir) {
                    L = LS.compress(I.get_buffer());
                    time = LS.getTime();
                    rate = LS.getRate();
                    E.actS(time,rate);
                }
                else {
                    L = LS.descomprimir(I.get_buffer());
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

                JPEG JG = new JPEG();
                if (comprimir) {
                    L = JG.compress(I.get_buffer());
                    time = JG.getTime();
                    rate = JG.getRate();
                    E.actG(time,rate);
                }
                else {
                    L = JG.descomprimir(I.get_buffer());
                    time = JG.getTime();
                }
                break;
        }
        this.time = time;
        this.rate = rate;
        return  L;
    }

    public void compressio_descompressio(controlador_gestor_fitxer I) {
        Object L = null;
        Estadisticas_Stub E = new Estadisticas_Stub();
        int id = I.getid();
        boolean comprimir = I.getcompress();
        L = action(id, comprimir, I);
        System.out.println("Time " + this.time);
        System.out.println("Rate " + this.rate);
        if (comprimir) {
            path1 = I.getPath_original();
            path2 = I.getPath_desti();
        }
        I.writeFile(L);
    }

    public void comparar() throws IOException {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer(path2, "", 3, false);
        System.out.println(path1);
        String S = I.Read1();
        System.out.println(S);
        System.out.println();
        int id = I.getid();
        Object L = action(id, false, I);
        System.out.println(path2);
        System.out.println((String)L);
    }
}
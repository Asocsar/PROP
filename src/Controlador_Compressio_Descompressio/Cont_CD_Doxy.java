package Controlador_Compressio_Descompressio;

import Controlador_Compressio_Descompressio.Algoritmes_Stub.*;
import Controlador_Compressio_Descompressio.Controlador_fitxers_stub.controlador_gestor_fitxer;
import Controlador_Compressio_Descompressio.Estadisticas.Estadisticas_Stub;
import java.io.*;

public class Cont_CD {

    private static String path1 = "";
    private static String path2 = "";
    private static double time;
    private static double rate;
    private static int id;

    public Cont_CD (){
    }


    /**\brief Acció
        \pre cert
        \post Retorna el resultat d'aplicar compressió o descompressió d'algun algorisme 
    */
    private  Object action (String path_o, int id, boolean comprimir, controlador_gestor_fitxer I)  {
        Object L = null;
        Estadisticas_Stub E = new Estadisticas_Stub();
        switch (id) {
            // Es decideix quin algorisme utilitzar i quina accio pendre
            case 1:
                LZ78 L8 = new LZ78();
                if (comprimir) {
                    System.out.println("LZ78 compression ejecutado");
                    L = L8.compress(I.get_buffer(path_o, comprimir, id));
                    //s'actualitzen les estadístiques i es guarda temps i rati
                    time = L8.getTime();
                    rate = L8.getRate();
                    E.act8(time,rate);
                }
                else {
                    System.out.println("LZ78 descompression ejecutado");
                    L = L8.descomprimir(I.get_buffer(path_o, comprimir, id));
                    time = L8.getTime();
                }

                break;
            case 2:
                LZSS LS = new LZSS();
                if (comprimir) {
                    System.out.println("LZSS compression ejecutado");
                    L = LS.compress(I.get_buffer(path_o, comprimir, id));
                    time = LS.getTime();
                    rate = LS.getRate();
                    E.actS(time,rate);
                }
                else {
                    System.out.println("LZSS descompression ejecutado");
                    L = LS.descomprimir(I.get_buffer(path_o, comprimir, id));
                    time = LS.getTime();
                }
                break;
            case 3:
                LZW LW = new LZW();
                if (comprimir) {
                    System.out.println("LZW compression ejecutado");
                    L = LW.compress(I.get_buffer(path_o, comprimir, id));
                    time = LW.getTime();
                    rate = LW.getRate();
                    E.actW(time,rate);

                }
                else {
                    System.out.println("LZW descompression ejecutado");
                    L = LW.descomprimir(I.get_buffer(path_o, comprimir, id));
                    time = LW.getTime();

                }

                break;
            default:

                JPEG JG = new JPEG();
                if (comprimir) {
                    System.out.println("JPEG compression ejecutado");
                    L = JG.compress(I.get_buffer(path_o, comprimir, id));
                    time = JG.getTime();
                    rate = JG.getRate();
                    E.actG(time,rate);
                }
                else {
                    System.out.println("JPEG descompression ejecutado");
                    L = JG.descomprimir(I.get_buffer(path_o, comprimir, id));
                    time = JG.getTime();
                }
                break;
        }
        return  L;
    }

    /**\brief Compressió/Descompressió
        \pre path_o ha de ser vàlid
        \post Comprimeix o descomprimeix el fitxer situat al path_o i el desa al path_d
    */
    public void compressio_descompressio(String path_o, String path_d, int ide, boolean compress) {
        Object L = null;
        id = ide;
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        // es fa l'accio demanada
        L = action(path_o, id, compress, I);
        System.out.println("Time " + time);
        if (compress) System.out.println("Rate " + rate);
        // en cas de compressio guardem els path
        if (compress) {
            path1 = path_o;
            path2 = path_d;
        }
        I.writeFile(L, path_d);
    }
    /** \brief Comparar
        \pre : Hi ha hagut com a mínim una compressió des de que s'ha iniciat el programa
        \post: Mostra el contingut del fitxer original i el resultat després d'haver comprimit aquest
    */
    public void comparar() throws IOException {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        //llegim el contingut del fitxer original
        System.out.println("path origen a comparar -> " + path1);
        String S = I.Read1(path1);
        System.out.println(S);
        System.out.println();
        //descomprimir el contingut del fitxer comprimit i el mostrem
        Object L = action(path2, id, false, I);
        System.out.println("path desti a comparar -> " + path2);
        System.out.println((String)L);
    }
}
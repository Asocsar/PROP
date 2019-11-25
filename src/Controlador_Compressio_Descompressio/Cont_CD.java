package Controlador_Compressio_Descompressio;


import Algoritmes.*;
import Controlador_ficheros.controlador_gestor_fitxer;
import Estadístiques.Estadistiques;

import java.io.*;
import java.util.List;

public class Cont_CD {

    private static String path1 = "";
    private static String path2 = "";
    private static double time;
    private static double rate;
    private static int id;

    public Cont_CD (){
    }


    // Pre : Cert
    // Post: Retorna el resultat d'aplicar compressió o descompressió d'algun algorisme
    private  Object action (String path_o, int id, boolean comprimir, controlador_gestor_fitxer I)  {
        Object L = null;
        try {
            Estadistiques E = new Estadistiques();
            switch (id) {
                // Es decideix quin algorisme utilitzar i quina accio pendre
                case 2:
                    LZ78 L8 = new LZ78();
                    if (comprimir) {
                        System.out.println("LZ78 compression ejecutado");
                        L = L8.compresio((byte[]) I.get_buffer(path_o, comprimir, id));
                        //s'actualitzen les estadístiques i es guarda temps i rati
                        time = L8.get_time();
                        rate = L8.get_ratio_c();
                        E.act8(time, rate);
                    } else {
                        System.out.println("LZ78 descompression ejecutado");
                        L = L8.descompresio((byte[]) I.get_buffer(path_o, comprimir, id));
                        time = L8.get_time();
                    }

                    break;
                case 1:
                    LZSS LS = new LZSS();
                    if (comprimir) {
                        System.out.println("LZSS compression ejecutado");
                        L = LS.compress((byte[]) I.get_buffer(path_o, comprimir, id));
                        time = LS.getTime();
                        rate = LS.getRate();
                        E.actS(time, rate);
                    } else {
                        System.out.println("LZSS descompression ejecutado");
                        L = LS.decompress((Byte[]) I.get_buffer(path_o, comprimir, id));
                        time = LS.getTime();
                    }
                    break;
                case 0:
                    LZW LW = new LZW();
                    if (comprimir) {
                        System.out.println("LZW compression ejecutado");
                        L = LW.compress((byte[]) I.get_buffer(path_o, comprimir, id));
                        time = LW.getTime();
                        rate = LW.getRate();
                        E.actW(time, rate);

                    } else {
                        System.out.println("LZW descompression ejecutado");
                        L = LW.descomprimir((List<Integer>) I.get_buffer(path_o, comprimir, id));
                        time = LW.getTime();

                    }

                    break;
                default:
/*
                JPEG JG = new JPEG();
                if (comprimir) {
                    System.out.println("JPEG compression ejecutado");
                    L = JG.compress(I.get_buffer(path_o, comprimir, id));
                    time = JG.;
                    rate = JG.getRate();
                    E.actG(time,rate);
                }
                else {
                    System.out.println("JPEG descompression ejecutado");
                    L = JG.descomprimir(I.get_buffer(path_o, comprimir, id));
                    time = JG.getTime();
                }
                break;*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (controlador_gestor_fitxer.FicheroCompressionNoValido ficheroCompressionNoValido) {
            System.out.println("Muy mal con intentar comprimir un fichero no valido cacho cabron");
        } catch (controlador_gestor_fitxer.FicheroDescompressionNoValido ficheroDescompressionNoValido) {
            System.out.println("Bastante mal con intentar descomprimir un fichero no valido cacho perro");
        }
        return  L;
    }

    // Pre: Cert
    // Post: Comprimeix o descomprimeix el fitxer situat al path_o i el desa al path_d
    public void compressio_descompressio(String path_o, String path_d, int ide, boolean compress) {
        Object L = null;
        id = ide;
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        // es fa l'accio demanada
        L = action(path_o, id, compress, I);
        if (L != null) {
            System.out.println("Time " + time);
            if (compress) System.out.println("Rate " + rate);
            // en cas de compressio guardem els path
            if (compress) {
                path1 = path_o;
                path2 = path_d;
            }
            try {
                I.writeFile(L, path_d, path_o);
            } catch (IOException e) {
                System.out.println("Fallo en la escritura");
            }
        }
    }

    // Pre : Hi ha hagut com a mínim una compressió des de que s'ha iniciat el programa
    // Post: Mostra el contingut del fitxer original i el resultat després d'haver comprimit aquest
    public void comparar() {
        if (path1.equals("")) {
            System.out.println("Por favor ejecute una compressión antes de comparar");
        }
        else {
            try {
                controlador_gestor_fitxer I = new controlador_gestor_fitxer();
                //llegim el contingut del fitxer original
                System.out.println("path origen a comparar -> " + path1);
                String S = I.obtenir_fitxer(path1);
                System.out.println(S);
                System.out.println();
                //descomprimir el contingut del fitxer comprimit i el mostrem
                Object L = action(path2, id, false, I);
                System.out.println("path desti a comparar -> " + path2);
                System.out.println((String) L);
            } catch (IOException e) {
                System.out.println("El archivo a comparar ha sido desplazado en el sistema");
            }
        }
    }
}
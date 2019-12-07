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
    private static String id;

    public Cont_CD (){
    }


    // Pre : Cert
    // Post: Retorna el resultat d'aplicar compressió o descompressió d'algun algorisme
    private  Object action (String path_o, String id, boolean comprimir, controlador_gestor_fitxer I)  {
        Object L = null;
        try {
            Estadistiques E = new Estadistiques();
            switch (id) {
                // Es decideix quin algorisme utilitzar i quina accio pendre
                case "LZ78":
                    LZ78 L8 = new LZ78();
                    if (comprimir) {
                        System.out.println("LZ78 compression ejecutado");
                        L = L8.compresio(I.get_buffer(path_o, comprimir, id));
                        //s'actualitzen les estadístiques i es guarda temps i rati
                        time = L8.get_time();
                        rate = L8.get_ratio_c();
                        E.act8(time, rate);
                    } else {
                        System.out.println("LZ78 descompression ejecutado");
                        L = L8.descompresio(I.get_buffer(path_o, comprimir, id));
                        time = L8.get_time();
                    }

                    break;
                case "LZSS":
                    LZSS LS = new LZSS();
                    if (comprimir) {
                        System.out.println("LZSS compression ejecutado");
                        L = LS.compress(I.get_buffer(path_o, comprimir, id));
                        time = LS.getTime();
                        rate = LS.getRate();
                        E.actS(time, rate);
                    } else {
                        System.out.println("LZSS descompression ejecutado");
                        L = LS.decompress(I.get_buffer(path_o, comprimir, id));
                        time = LS.getTime();
                    }
                    break;
                case "LZW":
                    LZW LW = new LZW();
                    if (comprimir) {
                        System.out.println("LZW compression ejecutado");
                        L = LW.compress(I.get_buffer(path_o, comprimir, id));
                        time = LW.getTime();
                        rate = LW.getRate();
                        E.actW(time, rate);

                    } else {
                        System.out.println("LZW descompression ejecutado");
                        L = LW.descompress(I.get_buffer(path_o, comprimir, id));
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
    // Post: Comprimeix el fitxer situat al path_o i el desa al path_d
    public void compressio_fitxer (String path_o, String path_d, String algoritme) throws IOException {
        id = algoritme;
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        Object L = action(path_o, id, true, I);
        path1 = path_o;
        path2 = path_d;
        I.writeFile(L, path_o, path_d);
    }

    // Pre: Cert
    // Post: Comprimeix el fitxer situat al path_o i el desa al path_d
    public void compressio_carpeta (String path_o, String path_d, String algoritme) throws IOException {
        id = algoritme;
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        I.folder(path_o);
        while (I.resten()) {
            Object L = action(path_o, id, true, I);
            I.writeFile(L, path_o, path_d);
        }
    }


    // Pre: Cert
    // Post: Descomprimeix el fitxer situat al path_o i el desa al path_d
    public void descompressio_fitxer (String path_o, String path_d) throws IOException {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        id = I.getAlgoritme(path_o);
        Object L = action(path_o, id, false, I);
        I.writeFile(L, path_o, path_d);
    }

    // Pre: Cert
    // Post: Descomprimeix el fitxer situat al path_o i el desa al path_d
    public void descompressio_carpeta (String path_o, String path_d) throws IOException {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer();
        id = I.getAlgoritme(path_o);
        Object L = action(path_o, id, false, I);
        I.writeFile(L, path_o, path_d);
    }




    // Pre : Hi ha hagut com a mínim una compressió des de que s'ha iniciat el programa
    // Post: Mostra el contingut del fitxer original i el resultat després d'haver comprimit aquest
    public String [] comparar() throws IOException {
        String [] K = new String[2];
        if (path1.equals("")) {
            // AQUI TENGO QUE PONER UNA EXCEPCION QUE UN NO HE HECHO
        }
        else {
            controlador_gestor_fitxer I = new controlador_gestor_fitxer();
            //llegim el contingut del fitxer original
            String S = I.obtenir_fitxer(path1);
            K[0] = S;
            //descomprimir el contingut del fitxer comprimit i el mostrem
            Object L = action(path2, id, false, I);
            K[1] = I.compare(L);
        }
        return  K;
    }
}
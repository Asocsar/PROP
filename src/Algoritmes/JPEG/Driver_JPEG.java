package Algoritmes.JPEG;


import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;


public class Driver_JPEG {

    /*
    El driver de l'algorisme JPEG prova la funcionalitat de l'algorisme de compressió i descompressió.
    Et demana 3 paràmetres:
        1. El path d'origen del fitxer PPM
        2. El path destí del fitxer comprimit .fG, sense extensió.
        3. El path destí del fitxer descomprimit PPM, per comparar-lo amb l'original.

     */
    public static void main(String[] args) {
        try {
            Scanner S = new Scanner(System.in);
            System.out.println("Introdueix el path del fitxer a comprimir");
            System.out.println("Exemple: /home/usr/fitxer");
            String path = S.next();
            File infile = new File(path);

            FileInputStream fis = new FileInputStream(infile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            JPEG J = new JPEG();


            byte[] bb = new byte[50];
            char c;
            int width = 0, height = 0;

            /*
            bis.read(bb, 0, 3);
            if((char)bb[0] == 'P' && (char)bb[1] != '6') System.out.println("Fitxer diferent de P6");
            StringBuilder str = new StringBuilder();
            c = (char) bis.read();
            while(c != '\n'){
                if(c == ' ') {
                    width = Integer.parseInt(str.toString());
                    str = new StringBuilder();
                }
                else str.append(c);
                c = (char) bis.read();
            }

            height = Integer.parseInt(str.toString());
            System.out.println("Width: " + width);
            System.out.println("Height: " + height);

            bis.read(bb, 0, 4);

            System.out.println("Depth: " + (char) bb[0] + (char) bb[1] + (char) bb[2]);


            int[][] Y = new int[height][width];
            int[][] Cb = new int[height][width]; //height/factor_downsampling and width/FD
            int[][] Cr = new int[height][width]; //H/fd and w/fd ??

            //Read ints
            int r, g, b;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    r = bis.read();
                    g = bis.read();
                    b = bis.read();

                    Y[i][j] = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                    Cb[i][j] = (int) (128 - 0.1687 * r - 0.3313 * g + 0.5 * b);
                    Cr[i][j] = (int) (128 + 0.5 * r - 0.4187 * g - 0.0813 * b);

                }
            }
            bis.close();
            System.out.println("Finished reading");
            int[][][] aux = new int[][][] {Y, Cb, Cr};
            */

            System.out.println("Introdueix la qualitat de compressió (0-100)");
            int quality =  Integer.parseInt(S.next());

            byte [] buff = Files.readAllBytes(infile.toPath());
            int[][][] aux = J.byteAtoMat(buff);
            //Crida a compress
            aux = J.compress(aux, quality);

            //Agafar estadístiques
            System.out.println("Ratio " + J.getRate());
            System.out.println("Temps " + J.getTime());

            //Escriptura a fitxer .fG
            System.out.println("Introdueix path de destí del fitxer comprimit (fitxer sense extensió)");

            path = S.next();
            path = path + ".fg";

            FileOutputStream fosc = new FileOutputStream(path);
            BufferedOutputStream bosc = new BufferedOutputStream(fosc);
            bosc.write(width);
            bosc.write(height);
            bosc.write(quality);

            int length = aux[0].length;
            //Escriure bytes
            for(int a = 0; a < 3; ++a) {
                //escriure byte 'A'
                for (int i = 0; i < length; ++i) {
                    //escriure byte 'B'
                    bosc.write(aux[a][i].length); //escriure mida array comprimit
                    for (int j = 0; j < aux[a][i].length; ++j) {
                        bosc.write((byte) aux[a][i][j]);
                    }
                }
            }
            bosc.close();


            /*
            FileWriter file_o = new FileWriter(path);
            file_o.write(width);
            file_o.write(height);
            file_o.write(quality);
            int length = aux[0].length;


            //Escriure bytes
            for(int a = 0; a < 3; ++a) {
                //escriure byte 'A'
                for (int i = 0; i < length; ++i) {
                    //escriure byte 'B'
                    file_o.write((byte) aux[a][i].length); //escriure mida array comprimit
                    for (int j = 0; j < aux[a][i].length; ++j) {
                        file_o.write((byte) aux[a][i][j]);
                    }
                }
            }
            file_o.close();

             */

            /*
            for(int a = 0; a < 3; ++a) {
                System.out.println("Nou color");
                for (int i = 0; i < length; ++i) {
                    for (int j = 0; j < aux[a][i].length; ++j) {
                        System.out.printf("%d ",aux[a][i][j]);
                    }
                    System.out.println();
                }
                System.out.println();
            }
            */


            //Reading comprimit i escriptura descomprimit
            FileInputStream fisc = new FileInputStream(path);
            BufferedInputStream bisc = new BufferedInputStream(fisc);
            int midafila;

            width = bisc.read();
            height = bisc.read();
            quality = bisc.read();

            System.out.println("w: " + width);
            System.out.println("h: " + height);
            System.out.println("q: " + quality);

            int Bheight = (height % 8 == 0) ? height / 8 : height / 8 + 1;
            int Bwidth = (width % 8 == 0) ? width / 8 : width / 8 + 1;
            length = Bwidth*Bheight;

            int[][][] aux2 = new int[3][length][];
            for(int a = 0; a < 3; ++a) {
                for (int i = 0; i < length; ++i) {
                    midafila = bisc.read();
                    //System.out.println("i: "+ i);
                    aux2[a][i] = new int[midafila];
                    for (int j = 0; j < midafila; ++j) {
                        aux2[a][i][j] = bisc.read();
                    }

                }
            }
            bisc.close();

            //Diferència entre aux i aux2
            for(int a = 0; a < 3; ++a) {
                if(aux[a].length != aux2[a].length) System.out.println("Mides diferents de blocs: " + aux[a].length + " | " + aux2[a].length);
                for (int i = 0; i < aux[0].length; ++i) {
                    if (aux[a][i].length != aux2[a][i].length){
                        //System.out.println("--------------------------------------------------------");
                        System.out.println( "Mides diferents, escrita: "+ aux[a][i].length + " | llegida: " + aux2[a][i].length);
                        System.out.println("Color: " + a +", Bloc: " + i);
                       // System.out.println("--------------------------------------------------------");

                    }else{
                        for (int j = 0; j < aux[a][i].length; ++j){
                            if(aux[a][i][j] != aux2[a][i][j]) {
                                //System.out.println("--------------------------------------------------------");
                                System.out.println("Diferència de valor, escrit: " + aux[a][i][j] + " | llegit: "+ aux2[a][i][j]);
                                System.out.println("Color: " + a + ", Bloc: "+ i+ ", PosNúmero: " + j);
                                //System.out.println("--------------------------------------------------------");
                            }
                        }
                    }
                }
            }


            int[][][] YCbCr = J.decompress(aux, height, width, quality);


            System.out.println("Introdueix el path desti del fitxer descomprimit (amb extensió)");
            path = S.next();
            File outfile = new File(path);
            FileOutputStream fos = new FileOutputStream(outfile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            bos.write(("P6" + "\n").getBytes());
            bos.write((width + " " + height + "\n").getBytes());
            bos.write(("255\n").getBytes());

            int r,g,b;
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    //YCbCr to RGB and write

                    double y, cb, cr;


                    y = (YCbCr[0][i][j]*298.082) / 256;
                    cb = YCbCr[1][i][j];
                    cr = YCbCr[2][i][j];

                    r = (int) (y + 1.40200 * (cr - 0x80));
                    g = (int) (y - 0.34414 * (cb - 0x80) - 0.71414 * (cr - 0x80));;
                    b = (int) (y + 1.77200 * (cb - 0x80));

                    r = Math.max(Math.min(r, 255), 0);
                    g = Math.max(Math.min(g, 255), 0);
                    b = Math.max(Math.min(b, 255), 0);


                    bos.write(r);
                    bos.write(g);
                    bos.write(b);

                }
            }
            bos.close();
            System.out.println("JPEG acabat amb èxit.");

        } catch (FileNotFoundException e) {
            System.out.println("L'adreça no ha estat trobada");

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error en la Entrada - Sortida");
        }

    }
}
package Algoritmes.JPEG;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver_JPEG {

    public static void main(String[] args) {
        try {
            Scanner S = new Scanner(System.in);
            System.out.println("Introdueix el path del fitxer a comprimir");
            String path = S.next();
            File infile = new File(path);

            FileInputStream fis = new FileInputStream(infile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            JPEG J = new JPEG();

            byte[] bb = new byte[50];
            char c;
            int width = 0, height;

            bis.read(bb, 0, 3);
            if((char)bb[0] == 'P' && (char)bb[1] == '6') System.out.println("Lectura de fitxer P6");
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
            aux = J.compress(aux);

            System.out.println("Ratio " + J.getRate());
            System.out.println("Temps " + J.getTime());
            //Escriptura a fitxer .fG
            System.out.println("Introdueix path de destí i nom del fitxer (no fa falta especificar la extensio)");
            System.out.println("Exemple \\home\\usr\\fitxer");

            path = S.next();
            path = path + ".fg";
            FileWriter file_o = new FileWriter(path);
            for(int a = 0; a < 3; ++a) {
                for (int i = 0; i < height; ++i) {
                    for (int j = 0; j < aux[a][i].length; ++j) {
                        file_o.write(aux[a][i][j]);
                    }
                }
            }
            file_o.close();


            int[][][] YCbCr = J.decompress(aux, height, width);


            System.out.println("Introdueix el path desti del fitxer descomprimit");
            File outfile = new File(path);
            FileOutputStream fos = new FileOutputStream(outfile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            bos.write(("P6" + "\n").getBytes());
            bos.write((width + " " + height + "\n").getBytes());
            bos.write(("255\n").getBytes());


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
            System.out.println("El fitxer no ha estat trobat");

        } catch (IOException e) {
            System.out.println("Error en la Entrada - Sortida");
        }

    }
}

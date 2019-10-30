package Algoritmes;
import com.sun.org.apache.xml.internal.utils.SystemIDResolver;

import java.awt.*;
import java.io.*;
import java.sql.SQLOutput;
import java.util.Scanner;




public class JPEG {




    public static double[][] dct (int[][] m){

        double[][] dct = new double[8][8];
        double au, av, sum;
        int u, v, x, y, val;

        for (u = 0; u < 8; ++u) {
            for (v = 0; v < 8; ++v) {

                if (u == 0) au = 1 / Math.sqrt(2);
                else au = 1;
                if (v == 0) av = 1 / Math.sqrt(2);
                else av = 1;

                sum = 0;
                for (x = 0; x < 8; ++x) {
                    for (y = 0; y < 8; ++y) {
                        val = m[x][y] - 128;
                        sum += val * Math.cos(((2 * x + 1) * u * Math.PI) / 16) * Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                    }
                }

                dct[u][v] = 0.25 * au * av * sum;
            }
        }

        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++)
                System.out.printf("%f\t", dct[x][y]);
            System.out.println();
        }
        return dct;
    }

    public static int[] compress8 ( int[][] m, boolean chroma){

        //DCT Transform
        double[][] D = dct(m);

        //Quantitzation
        int[][] Q = {{16,11,12,16,24,40,51,61},
                {12,12,14,19,26,58,60,55},
                {14,13,16,24,40,57,69,56},
                {14,17,22,29,51,87,80,62},
                {18,22,37,56,68,109,103,77},
                {24,35,55,64,81,104,113,92},
                {49,64,78,87,103,121,120,101},
                {72,92,95,98,112,110,103,99}};

        int[][] QC =   {{17,18,24,47,99,99,99,99},
                {18,21,26,66,99,99,99,99},
                {24,26,56,99,99,99,99,99},
                {47,66,99,99,99,99,99,99},
                {99,99,99,99,99,99,99,99},
                {99,99,99,99,99,99,99,99},
                {99,99,99,99,99,99,99,99},
                {99,99,99,99,99,99,99,99}};

        if (chroma) Q = QC; //Si el boolean chroma es true aplicar el quantitzation de chrominance

        System.out.println();
        int[][] B = new int[8][8];
        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                B[i][j] = (int) Math.round(D[i][j] / Q[i][j]);
                System.out.printf("%d\t", B[i][j]);
            }
            System.out.println();
        }

        //Encoding ZigZag (RLE)
        int[][] ZigZag = {
                {0,0},
                {0,1},{1,0},
                {2,0},{1,1},{0,2},
                {0,3},{1,2},{2,1},{3,0},
                {4,0},{3,1},{2,2},{1,3},{0,4},
                {0,5},{1,4},{2,3},{3,2},{4,1},{5,0},
                {6,0},{5,1},{4,2},{3,3},{2,4},{1,5},{0,6},
                {0,7},{1,6},{2,5},{3,4},{4,3},{5,2},{6,1},{7,0},
                {7,1},{6,2},{5,3},{4,4},{3,5},{2,6},{1,7},
                {2,7},{3,6},{4,5},{5,4},{6,3},{7,2},
                {7,3},{6,4},{5,5},{4,6},{3,7},
                {4,7},{5,6},{6,5},{7,4},
                {7,5},{6,6},{5,7},
                {6,7},{7,6},
                {7,7}};


        System.out.println();
        int[] buff = new int[64];
        for(int i = 0; i < 64; ++i){
            buff[i] = B[ZigZag[i][0]][ZigZag[i][1]];
            System.out.printf("%d\t", buff[i]);
        }
        System.out.println();

        return buff;

        //We have to add the DC coefficient and the 63 other values
        //With RLE and Hufmann (RUNLENGTH, SIZE) (AMPLITUDE)
    }

    public static int[][] idct (int[][] m){

        double[][] idct = new double[8][8];
        double au, av, sum;
        int u, v, x, y, val;

        for (u = 0; u < 8; ++u) {
            for (v = 0; v < 8; ++v) {

                if (u == 0) au = 1 / Math.sqrt(2);
                else au = 1;
                if (v == 0) av = 1 / Math.sqrt(2);
                else av = 1;

                sum = 0;
                for (x = 0; x < 8; ++x) {
                    for (y = 0; y < 8; ++y) {
                        sum += au * av * Math.cos(((2 * x + 1) * u * Math.PI) / 16) * Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                    }
                }

                idct[u][v] =  0.25 * sum ;
            }
        }

        int[][] intdct = new int[8][8];
        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++){
                intdct[x][y] = Math.min(Math.max(dct[x][y] + 128, 0), 255);
            }
        }
        return intdct;
    }


    public static int[][] decompress8 (int[] buff){

        //Decoding ZigZag (RLE)
        int[][] ZigZag = {
                {0,0},
                {0,1},{1,0},
                {2,0},{1,1},{0,2},
                {0,3},{1,2},{2,1},{3,0},
                {4,0},{3,1},{2,2},{1,3},{0,4},
                {0,5},{1,4},{2,3},{3,2},{4,1},{5,0},
                {6,0},{5,1},{4,2},{3,3},{2,4},{1,5},{0,6},
                {0,7},{1,6},{2,5},{3,4},{4,3},{5,2},{6,1},{7,0},
                {7,1},{6,2},{5,3},{4,4},{3,5},{2,6},{1,7},
                {2,7},{3,6},{4,5},{5,4},{6,3},{7,2},
                {7,3},{6,4},{5,5},{4,6},{3,7},
                {4,7},{5,6},{6,5},{7,4},
                {7,5},{6,6},{5,7},
                {6,7},{7,6},
                {7,7}};

        System.out.println("Comença la descompressió de 8x8");
        int[][] B = new int[8][8];
        for(int i = 0; i < 64; ++i){
            B[ZigZag[i][0]][ZigZag[i][1]] = buff[i];
        }
        System.out.println();

        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                System.out.printf("%d\t", B[i][j]);
            }
            System.out.println();
        }
        //Quantitzation
        int[][] Q = {{16,11,12,16,24,40,51,61},
                {12,12,14,19,26,58,60,55},
                {14,13,16,24,40,57,69,56},
                {14,17,22,29,51,87,80,62},
                {18,22,37,56,68,109,103,77},
                {24,35,55,64,81,104,113,92},
                {49,64,78,87,103,121,120,101},
                {72,92,95,98,112,110,103,99}};

        int[][] QC =
                {{17,18,24,47,99,99,99,99},
                        {18,21,26,66,99,99,99,99},
                        {24,26,56,99,99,99,99,99},
                        {47,66,99,99,99,99,99,99},
                        {99,99,99,99,99,99,99,99},
                        {99,99,99,99,99,99,99,99},
                        {99,99,99,99,99,99,99,99},
                        {99,99,99,99,99,99,99,99}};

        //if (chroma) Q = QC; //Si el boolean chroma es true aplicar el quantitzation de chrominance
        int[][] D = new int[8][8];
        for(int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                D[i][j] = B[i][j] * Q[i][j];
                System.out.printf("%d\t", D[i][j]);
            }
            System.out.println();
        }


        //DCT Transform
        int[][] m = idct(D);

        //Shift values by 128 and apply Math.min and Math.max
        return m;
    }

    public static int[] compress(int[] buff, int  height, int width){

        /*
        int height = buff.length;
        int width = m[0].length;

        //Convert RGB to YCbCr color space

        int [][] Y = new int[height][width];
        int [][] Cb = new int[height][width]; //height/factor_downsampling and width/FD
        int [][] Cr = new int[height][width]; //H/fd and w/fd ??

        int r,g,b;
        while (i != )
        for(int x = 0; x < height; ++x){
            for(int y = 0; y < width; ++y){

                r = buff[i];
                g = buffer[i + 1];
                b = buffer[i + 2];

                Y[x][y] = (int) (0.299*r + 0.587*g + 0.114*b);
                Cb[x][y] = (int) (128 - 0.1687*r - 0.3313*g + 0.5*b);
                Cr[x][y] = (int) (128 + 0.5*r - 0.4187*g - 0.0813*b);
            }
        }
        return new int[][][] {Y,Cb,Cr};

         */
        int[][] m = {
                { 52, 55, 61, 66, 70, 61, 64, 73},
                { 63, 59, 55, 90, 109, 85, 69, 72 },
                { 62, 59, 68, 113, 144, 104, 66, 73 },
                { 63, 58, 71, 122, 154, 106, 70, 69 },
                { 67, 61, 68, 104, 126, 88, 68, 70 },
                { 79, 65, 60, 70, 77, 68, 58, 75 },
                { 85, 71, 64, 59, 55, 61, 65, 83 },
                { 87, 79, 69, 68, 65, 76, 78, 94 }};


        int[][]m2 = decompress8(compress8(m, false));  //Returns array of ints of Y

        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                System.out.printf("%d\t", m2[i][j]);
            }
            System.out.println();
        }



        }

    }



    public static void decompress (double[][] m) { }

    public static void main(String[] args) {

        //try {

            /*
            File infile = new File("/home/maller/Downloads/tiny.ppm");
            File outfile = new File("/home/maller/Downloads/tinessed.ppm");


            long size = infile.length(); //Tamany del fitxer per posar-lo al buffer
            int[] buffer = new int[(int) size];

            FileInputStream fis = new FileInputStream(infile);

            System.out.println("Inici fitxer: ");

            System.out.println((char)buffer[0] + " " + (char)buffer[1]); //Know if is P3 or P6
            Then the second line is for knowing width and height


            for(int i = 0; i+2 < (int) size; i += 3){
                System.out.println((char) buffer[i] + " " +  (char)buffer[i+1] + " " +(char) buffer[i+2]);
            }
            //System.out.println(Arrays.toString(buffer));


            //Comprimir a buffer
            int[] compressedbuffer = compress(buffer, height, width);

            System.out.println("Compressed result: ");
            //System.out.println(Arrays.toString(compressedbuffer));

            //Write to file .fg


            //Descomprimir a buffer
            byte[] decompressedbuffer = decompress(compressedbuffer);

            System.out.println("Decompressed result: ");
            System.out.println(Arrays.toString(decompressedbuffer));


            FileOutputStream fos = new FileOutputStream(outfile);
            fos.write(decompressedbuffer);

            fis.close();
            fos.close();
            */
            System.out.println("JPEG acabat amb èxit.");

            /*
        }

        catch (FileNotFoundException e) {
            System.out.println("El fichero no ha sido encontrado");

        } catch (IOException e) {
            System.out.println("Error en la entrada salida");
        }
        */

    }
}

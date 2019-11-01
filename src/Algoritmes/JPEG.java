package Algoritmes;


import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class JPEG {



    public static double[][] dct (int[][] m){

        double[][] dct = new double[8][8];
        double au, av, sum;
        int u, v, x, y, val;

        for (u = 0; u < 8; ++u) {
            for (v = 0; v < 8; ++v) {

                au = (u==0) ?  1/Math.sqrt(2) : 1;
                av = (v==0) ?  1/Math.sqrt(2) : 1;

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

        int[][] idct = new int[8][8];
        double au, av, sum;
        int u, v, x, y;

        for (x = 0; x < 8; ++x) {
            for (y = 0; y < 8; ++y) {

                sum = 0;

                for (u = 0; u < 8; ++u) {
                    for (v = 0; v < 8; ++v) {
                        au = (u==0) ?  1/Math.sqrt(2) : 1;
                        av = (v==0) ?  1/Math.sqrt(2) : 1;
                        sum += m[u][v] * au * av * Math.cos(((2 * x + 1) * u * Math.PI) / 16) * Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                    }

                }

                idct[x][y] = (int) Math.round(0.25 * sum);
            }
        }

        System.out.println();
        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++){
                System.out.printf("%d\t", idct[x][y]);
                if(idct[x][y] > 127) idct[x][y] = 255;
                else idct[x][y] += 128;
            }
            System.out.println();
        }
        System.out.println();

        return idct;
    }


    public static int[][] decompress8 (int[] buff, boolean chroma){

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

        if (chroma) Q = QC; //Si el boolean chroma es true aplicar el quantitzation de chrominance
        int[][] D = new int[8][8];

        System.out.println();
        for(int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                D[i][j] = B[i][j] * Q[i][j];
                System.out.printf("%d\t", D[i][j]);
            }
            System.out.println();
        }


        //DCT Transform
        return idct(D);
    }



    public static int[][] compress(int[][] Y, int[][] Cb, int[][] Cr){

        int height = Y.length;
        int width = Y[0].length;
        int length = height*width /64;
        int[][] buff = new int[length][];

        for(int i = 0; i < length; ++i){
            int[][] m = new int[8][8];
            for(int x = 0; x < 8; ++x){
                for(int y =0; y < 8; ++y){
                    m[x][y] = Y[i/length * 8][i%width *8];
                }
            }
            buff[i] = compress8(m,false);
        }
        return buff;
    }


    public static int[][] decompress(int[][] buff, int height, int width) {


        int length =  height*width /64;
        int[][] m = new int[8][8];
        int[][] Y = new int[height][width];
        for(int i = 0; i < length; ++i){
            m = decompress8(buff[i],false);
            for(int x = 0; x < 8; ++x){
                for(int y =0; y < 8; ++y){
                    Y[i/length * 8][i%width *8] = m[x][y];
                }
            }

        }

        return buff;

    }


    public static void main(String[] args) {
        /*

        int[][] m = {
                { 52, 55, 61, 66, 70, 61, 64, 73},
                { 63, 59, 55, 90, 109, 85, 69, 72 },
                { 62, 59, 68, 113, 144, 104, 66, 73 },
                { 63, 58, 71, 122, 154, 106, 70, 69 },
                { 67, 61, 68, 104, 126, 88, 68, 70 },
                { 79, 65, 60, 70, 77, 68, 58, 75 },
                { 85, 71, 64, 59, 55, 61, 65, 83 },
                { 87, 79, 69, 68, 65, 76, 78, 94 }};


        int[][]m2 = decompress8(compress8(m, false), false);  //Returns array of ints of Y


        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                System.out.printf("%d\t", m2[i][j]);
            }
            System.out.println();
        }
        */

        try {

            File infile = new File("/home/maller/Downloads/west_1.ppm");
            File outfile = new File("/home/maller/Downloads/wested.ppm");

            Scanner in = new Scanner(infile);
            String filetype = infile.nextLine();
            String comments = infile.nextLine();
            /*

            FileInputStream fis = new FileInputStream(infile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            DataInputStream dis = new DataInputStream(fis);
            */
            int width, height;

            height = in.nextInt();
            width = in.nextInt();

            ArrayList<String> header = new ArrayList<>();

            String line = br.readLine();
            System.out.println(line);
            header.add(line); //ADD P6


            while ((line = br.readLine()) != null && line.charAt(0) == '#') {
                header.add(line);
                System.out.println(line);
            }

            //Read width and height
            System.out.println(line);
            StringTokenizer st = new StringTokenizer(line);
            width = Integer.parseInt(st.nextToken());
            height = Integer.parseInt(st.nextToken());

            int[][] Y = new int[height][width];
            int[][] Cb = new int[height][width]; //height/factor_downsampling and width/FD
            int[][] Cr = new int[height][width]; //H/fd and w/fd ??

            br.readLine(); //Skip 255 -> depth


            line = br.readLine();

            st = new StringTokenizer(line);
            for (int i = 0; i < 3; ++i) Integer.parseInt(st.nextToken());


            int r, g, b;
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    r = dis.readInt();
                   // System.out.println("r: " + r);
                    g = dis.readInt();
                    //System.out.println("g: " + g);
                    b = dis.readInt();
                   // System.out.println("b: " + b);

                    Y[i][j] = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                    Cb[i][j] = (int) (128 - 0.1687 * r - 0.3313 * g + 0.5 * b);
                    Cr[i][j] = (int) (128 + 0.5 * r - 0.4187 * g - 0.0813 * b);

                }
            }

            System.out.println("Finished reading");

            decompress(compress(Y, Cb, Cr), height, width);


            FileOutputStream fos = new FileOutputStream(outfile);

            for (String s : header) fos.write((s + "\n").getBytes());
            fos.write((width + " " + height + "\n").getBytes());
            fos.write(("255\n").getBytes());

            //YCbCr to RGB and write
            int y, cb, cr;

            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {

                    y = (int) (1.16438 * (Y[i][j] - 16));
                    cb = Cb[i][j];
                    cr = Cr[i][j];
                    r = (int) (-222.921 + y + (408.583 * cr) / 256);
                    g = (int) (135.576 + y - (100.291 * cb) / 256 - (208.120 * cr) / 256);
                    b = (int) (-276.836 + y + (516.412 * cb) / 256);


                    fos.write((r + " " + g + " " + b + " ").getBytes());
                }
                fos.write(("\n").getBytes());
            }

            fis.close();
            fos.close();

            System.out.println("JPEG acabat amb èxit.");

        }

        catch (FileNotFoundException e) {
            System.out.println("El fichero no ha sido encontrado");

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error en la entrada salida");
        }

    }
}

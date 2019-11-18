package Algoritmes.JPEG;


import java.io.*;
import java.util.ArrayList;


public class JPEG {

    //temps de l'ultima compressió / descompressió
    private double time;
    //rati de compressió assolit en la última compressió
    private double rate;

    //PRE: Cert
    //POST: Crea una instància de classe
    public JPEG(){
        this.time = 0.0;
        this.rate = 0.0;
    }
    // Pre : Cert
    // Post: Retorna el temps de l'última compressió/descompressió
    public double getTime () {return  this.time;}
    // Pre : Certs
    // Post: Retorna el rati assolit de l'última compressió
    public double getRate () {return  this.rate;}

    private int[][] Q = {{16, 11, 12, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 110, 103, 99}};


    private int[][] ZigZag = {
            {0, 0},
            {0, 1}, {1, 0},
            {2, 0}, {1, 1}, {0, 2},
            {0, 3}, {1, 2}, {2, 1}, {3, 0},
            {4, 0}, {3, 1}, {2, 2}, {1, 3}, {0, 4},
            {0, 5}, {1, 4}, {2, 3}, {3, 2}, {4, 1}, {5, 0},
            {6, 0}, {5, 1}, {4, 2}, {3, 3}, {2, 4}, {1, 5}, {0, 6},
            {0, 7}, {1, 6}, {2, 5}, {3, 4}, {4, 3}, {5, 2}, {6, 1}, {7, 0},
            {7, 1}, {6, 2}, {5, 3}, {4, 4}, {3, 5}, {2, 6}, {1, 7},
            {2, 7}, {3, 6}, {4, 5}, {5, 4}, {6, 3}, {7, 2},
            {7, 3}, {6, 4}, {5, 5}, {4, 6}, {3, 7},
            {4, 7}, {5, 6}, {6, 5}, {7, 4},
            {7, 5}, {6, 6}, {5, 7},
            {6, 7}, {7, 6},
            {7, 7}};



    private double[][] dct(int[][] m) {

        double[][] dct = new double[8][8];
        double au, av, sum;
        int u, v, x, y, val;

        for (u = 0; u < 8; ++u) {
            for (v = 0; v < 8; ++v) {

                au = (u == 0) ? 1 / Math.sqrt(2) : 1;
                av = (v == 0) ? 1 / Math.sqrt(2) : 1;

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
        /*
        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++)
                System.out.printf("%f\t", dct[x][y]);+ compress
            System.out.println();
        }
        */
        return dct;
    }

    private int[][] idct(int[][] m) {

        int[][] idct = new int[8][8];
        double au, av, sum;
        int u, v, x, y;

        for (x = 0; x < 8; ++x) {
            for (y = 0; y < 8; ++y) {

                sum = 0;

                for (u = 0; u < 8; ++u) {
                    for (v = 0; v < 8; ++v) {
                        au = (u == 0) ? 1 / Math.sqrt(2) : 1;
                        av = (v == 0) ? 1 / Math.sqrt(2) : 1;
                        sum += m[u][v] * au * av * Math.cos(((2 * x + 1) * u * Math.PI) / 16) * Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                    }

                }

                idct[x][y] = (int) Math.round(0.25 * sum);
            }
        }

        //System.out.println();
        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++) {
                //System.out.printf("%d\t", idct[x][y]);
                if (idct[x][y] > 127) idct[x][y] = 255;
                else idct[x][y] += 128;
            }
            //System.out.println();
        }
        //System.out.println();

        return idct;
    }




    private int[] compress8(int[][] m) {


        //DCT Transform
        double[][] D = this.dct(m);

        //Quantitzation

        //Si el boolean chroma es true aplicar el quantitzation de chrominance
        //System.out.println();
        int[][] B = new int[8][8];

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                B[i][j] = (int) Math.round(D[i][j] / Q[i][j]);
                //System.out.printf("%d\t", B[i][j]);
            }
            //System.out.println();
        }


        //Encoding ZigZag (RLE)
        //System.out.println();

        ArrayList<Integer> list = new ArrayList<Integer>();
        int last = B[0][0];
        int count = 1, curr;
        for (int i = 1; i < 64; i++) {
            curr = B[ZigZag[i][0]][ZigZag[i][1]];
            if (curr == last) count++;
            else {
                list.add(count);
                list.add(last);
                count = 1;
                last = curr;

            }
        }
        return list.stream().mapToInt(i->i).toArray();

        //We have to add the DC coefficient and the 63 other values
        //With RLE and Hufmann (RUNLENGTH, SIZE) (AMPLITUDE)
    }


    private int[][] decompress8(int[] buff) {

        //Decoding ZigZag (RLE)
        //System.out.println("Comença la descompressió de 8x8");
        int[][] B = new int[8][8];
        int c = 0, curr, count;

        for (int i = 0; i < buff.length; i+=2) {
            count = buff[i];
            curr = buff[i+1];
            for(int j = 0; j < count; ++j) {
                B[ZigZag[c + j][0]][ZigZag[c + j][1]] = curr;
            }
            c+=count;
        }
        //System.out.println();

        //System.out.println();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                //System.out.printf("%d\t", B[i][j]);
            }
            //System.out.println();
        }

        int[][] D = new int[8][8];
        //System.out.println();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                D[i][j] = B[i][j] * Q[i][j];
                //System.out.printf("%d\t", D[i][j]);
            }
            //System.out.println();
        }

        //DCT Transform
        return idct(D);

    }


    public int[][][] compress(int[][][] YCbCr) {

        int height = 0, width = 0, Bheight, Bwidth, length, posx, posy;
        int[][][] buff = new int[3][][];
        //Buffer with 3dimensions
        /*
        b[1] = NBlocks compressed which every on contains
        an array of Ints(RLE) or a Bitset(Hufmann)
         */
        long start = System.currentTimeMillis();
        System.out.println("Comença la compressió");
        long midafinal = 0;
        for (int a = 0; a < 3; ++a) {

            height = YCbCr[a].length;
            width = YCbCr[a][0].length;
            Bheight = (height % 8 == 0) ? height / 8 : height / 8 + 1;
            Bwidth = (width % 8 == 0) ? width / 8 : width / 8 + 1;
            buff[a] = new int[Bheight * Bwidth][];
            //System.out.println( "Blocs d'alçada: " + Bheight);
            //System.out.println( "Blocs d'amplada: " + Bwidth);
            midafinal = 3;

            for (int i = 0; i < Bheight; ++i) {
                for (int j = 0; j < Bwidth; ++j) {
                    int[][] m = new int[8][8];
                    for (int y = 0; y < 8; ++y) {
                        for (int x = 0; x < 8; ++x) {

                            posx = j * 8 + x;
                            posy = i * 8 + y;
                            if (posx >= width) m[y][x] = m[y][x - 1];
                            else if (posy >= height) m[y][x] = m[y - 1][x];
                            else m[y][x] = YCbCr[a][posy][posx];
                        }

                    }
                    buff[a][i * Bwidth + j] = compress8(m);
                    midafinal = midafinal + buff[a][i * Bwidth + j].length;

                }
            }

        }
        long end = System.currentTimeMillis();
        this.time = (end - start) / 1000F;
        this.rate = 1 - midafinal / (height * width + 3);
        return buff;
    }


    public int[][][] decompress(int[][][] buff, int height, int width) {

        System.out.println("Comença la descompressió");
        int[][][] YCbCr = new int[3][height][width];
        int Bheight = (height % 8 == 0) ? height / 8 : height / 8 + 1;
        int Bwidth = (width % 8 == 0) ? width / 8 : width / 8 + 1;
        int posx, posy;
        int[][] m;
        for(int a = 0; a < 3; a++) {
            for (int i = 0; i < Bheight; ++i) {
                for(int j = 0; j < Bwidth; ++j) {

                    m = decompress8(buff[a][i * Bwidth + j]);
                    for (int y = 0; y < 8; ++y) {
                        for (int x = 0; x < 8; ++x) {
                            posx = j * 8 + x;
                            posy = i * 8 + y;
                            if (posx < width && posy < height) YCbCr[a][posy][posx] = m[y][x];
                        }
                    }
                }
            }
        }

        /*
        for(int a = 0; a < 3; ++a) {
            System.out.println();
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    System.out.printf("%d\t", YCbCr[a][i][j]);
                }
                System.out.println();
            }
        }
         */

        return YCbCr;

    }

}

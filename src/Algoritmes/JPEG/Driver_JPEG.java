package Algoritmes.JPEG;


import java.io.*;


public class Driver_JPEG {

    private static int[][] Q = {{16, 11, 12, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 110, 103, 99}};

    private static int[][] QC = {{17, 18, 24, 47, 99, 99, 99, 99},
            {18, 21, 26, 66, 99, 99, 99, 99},
            {24, 26, 56, 99, 99, 99, 99, 99},
            {47, 66, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}};

    private static int[][] ZigZag = {
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


    private static double[][] dct(int[][] m) {

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

    private static int[][] idct(int[][] m) {

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




    private static int[] compress8(int[][] m, boolean chroma) {

        //DCT Transform
        double[][] D = dct(m);

        //Quantitzation

        //Si el boolean chroma es true aplicar el quantitzation de chrominance
        //System.out.println();
        int[][] B = new int[8][8];
        if(chroma) {
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    B[i][j] = (int) Math.round(D[i][j] / QC[i][j]);
                    //System.out.printf("%d\t", B[i][j]);
                }
                //System.out.println();
            }
        }else{
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    B[i][j] = (int) Math.round(D[i][j] / Q[i][j]);
                    //System.out.printf("%d\t", B[i][j]);
                }
                //System.out.println();
            }
        }

        //Encoding ZigZag (RLE)
        //System.out.println();
        int[] buff = new int[64];
        for (int i = 0; i < 64; ++i) {
            buff[i] = B[ZigZag[i][0]][ZigZag[i][1]];
            //System.out.printf("%d\t", buff[i]);
        }
        //System.out.println();

        return buff;

        //We have to add the DC coefficient and the 63 other values
        //With RLE and Hufmann (RUNLENGTH, SIZE) (AMPLITUDE)
    }




   private static int[][] decompress8(int[] buff, boolean chroma) {

        //Decoding ZigZag (RLE)
        //System.out.println("Comença la descompressió de 8x8");
        int[][] B = new int[8][8];
        for (int i = 0; i < 64; ++i) {
            B[ZigZag[i][0]][ZigZag[i][1]] = buff[i];
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
        if (chroma) {


            //System.out.println();
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    D[i][j] = B[i][j] * QC[i][j];
                    //System.out.printf("%d\t", D[i][j]);
                }
                //System.out.println();
            }
        }else{
            //System.out.println();
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    D[i][j] = B[i][j] * Q[i][j];
                    //System.out.printf("%d\t", D[i][j]);
                }
                //System.out.println();
            }
        }

        //DCT Transform
        return idct(D);
    }


    public static int[][][] compress(int[][][] YCbCr) {

        int height, width, Bheight, Bwidth, length, posx, posy;
        int[][][] buff = new int[3][1242][];
        //Buffer with 3dimensions
        /*
        b[0] = NBlocks compressed which every on contains
        a String(RLE) or a Bitset(Hufmann)
         */

        for(int a = 0; a < 3; ++a) {


            height = YCbCr[a].length;
            width = YCbCr[a][0].length;
            Bheight = (height % 8 == 0) ? height / 8 : height / 8 + 1;
            Bwidth = (width % 8 == 0) ? width / 8 : width / 8 + 1;
            System.out.println( "Blocs d'alçada: " + Bheight);
            System.out.println( "Blocs d'amplada: " + Bwidth);

            for (int i = 0; i < Bheight; ++i) {
                System.out.println( "Bi: " + i);
                for (int j = 0; j < Bwidth; ++j) {
                    System.out.println("Bj: "  + j);
                    int[][] m = new int[8][8];
                    for (int y = 0; y < 8; ++y) {
                        for (int x = 0; x < 8; ++x) {

                            posx = j*8 + x;
                            posy = i*8 + y;
                            if (posx >= width) m[y][x] = m[y][x-1];

                            else if(posy >= height) m[y][x] = m[y-1][x];
                            else m[y][x] = YCbCr[a][posy][posx];
                        }

                    }
                    buff[a][i*Bwidth +j] = compress8(m, a==0);
                }
            }

        }
        return buff;
    }


    public static int[][][] decompress(int[][][] buff, int height, int width) {


        int[][][] YCbCr = new int[3][height][width];
        int Bheight = (height % 8 == 0) ? height / 8 : height / 8 + 1;
        int Bwidth = (width % 8 == 0) ? width / 8 : width / 8 + 1;
        int posx, posy;
        int[][] m;
        for(int a = 0; a < 3; a++) {
            for (int i = 0; i < Bheight; ++i) {
                for(int j = 0; j < Bwidth; ++j) {

                    m = decompress8(buff[a][i * Bwidth + j], false);
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

        for(int a = 0; a < 3; ++a) {
            System.out.println();
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {

                    System.out.printf("%d\t", YCbCr[a][i][j]);
                }
                System.out.println();
            }
        }

        return YCbCr;

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


        int[][]m2 = this.decompress8(this.compress8(m, false), false);  //Returns array of ints of Y


        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                System.out.printf("%d\t", m2[i][j]);
            }
            System.out.println();
        }
        */

        try {

            File infile = new File("/home/maller/Downloads/west_2.ppm");
            File outfile = new File("/home/maller/Downloads/wested2.ppm");

            FileInputStream fis = new FileInputStream(infile);
            BufferedInputStream bis = new BufferedInputStream(fis);

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

            /*
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {

                    System.out.printf("%d\t", Y[i][j]);
                }
                System.out.println();
            }
            */

            int[][][] AUX = new int[][][] {Y, Cb, Cr};
            int[][][] YCbCr = decompress(compress(AUX), height, width);


            System.out.println("Start writing");
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
            System.out.println("El fichero no ha sido encontrado");

        } catch (IOException e) {
            System.out.println("Error en la entrada salida");
        }

    }
}

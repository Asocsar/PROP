package Algoritmes;
import java.io.*;
import java.util.Scanner;


public class JPEG {

    public static double[][][] RGBtoYCbCr(double [][][] RGB){

        double[][] Y = new double[8][8];
        double[][] Cb = new double[8][8];
        double[][] Cr = new double[8][8];

        double r,g,b;
        for(int x = 0; x < 8; ++x){
            for(int y = 0; y < 8; ++y){

                r = RGB[0][x][y];
                g = RGB[1][x][y];
                b = RGB[2][x][y];

                Y[x][y] = 0.299*r + 0.587*g + 0.114*b;
                Cb[x][y] = 128 - 0.1687*r - 0.3313*g + 0.5*b;
                Cr[x][y] = 128 + 0.5*r - 0.4187*g - 0.0813*b;
            }
        }

        return new double[][][] {Y,Cb,Cr};
    }

    public static double[][] compress8(double[][] m ){}

    public static double[][] compress(double[][] m){

        double[][][] subRGB = new double[3][8][8];
        for(int x = 0; x < m.length; ++x) {
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    subRGB[0][i][j] = m[];

                }
            }

            compress8(subRGB);
        }

        //Convert RGB to YCbCr color space
        double[][][] YCbCr = RGBtoYCbCr(RGB);

        //Downsampling Cb and Cr components every 2x2 pixels


        //DCT Transform

        for(int x = 0; x < 8; ++x){
            for(int y = 0; y < 8; ++y){
                //Sumatoris
                m[x][y] * cos((2*x + 1)*u*PI
            }
        }


        //Quantitzation
        double Q[][] = {{16,11,12,16,24,40,51,61},
                        {12,12,14,19,26,58,60,55},
                        {14,13,16,24,40,57,69,56},
                        {14,17,22,29,51,87,80,62},
                        {18,22,37,56,68,109,103,77},
                        {24,35,55,64,81,104,113,92},
                        {49,64,78,87,103,121,120,101},
                        {72,92,95,98,112,110,103,99}};

        //Encoding ZigZag (RLE)


        return buffer;
    }

    public static byte[] decompress (double[][] m){
        return buffer;
    }

    public static void main(String[] args) {

        try {
            System.out.println("Enter input matrix");
            BufferedReader obj = new BufferedReader (new InputStreamReader(System.in));
            double[][] m = new double[8][8];
            for(int i=0;i<8;i++) for(int j=0;j<8;j++) m[i][j] = Integer.parseInt(obj.readLine());
            compress()
            
            /*
            File infile = new File("/home/maller/Downloads/tiny.ppm");
            File outfile = new File("/home/maller/Downloads/tinessed.ppm");

            Scanner scan = new Scanner(infile);
            while(scan.hasNext()){
                scan.nextByte();
            }

            long size = infile.length(); //Tamany del fitxer per posar-lo al buffer
            byte[] buffer = new byte[(int) size];

            FileInputStream fis = new FileInputStream(infile);

            System.out.println("Inici fitxer: ");
            //System.out.println((char)buffer[0] + " " + (char)buffer[1]); //Know if is P3 or P6
            for(int i = 0; i+2 < (int) size; i += 3){
                System.out.println((char) buffer[i] + " " +  (char)buffer[i+1] + " " +(char) buffer[i+2]);
            }
            //System.out.println(Arrays.toString(buffer));


            //Comprimir a buffer
            byte[] compressedbuffer = compress(buffer);

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
        }

        catch (FileNotFoundException e) {
            System.out.println("El fichero no ha sido encontrado");

        } catch (IOException e) {
            System.out.println("Error en la entrada salida");
        }
    }
}

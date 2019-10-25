package Algoritmes;

import java.io.*;
import java.util.Arrays;


public class JPEG {



    public static byte[] compress(byte[] buffer){
        if((char) buffer[1] == '6'){
            System.out.println("ASCII compression P6");

            //Convert RGB to YCbCr color space

            //Downsampling

            //DCT Transform

            //Encoding (Huffmann and RLE -> ZigZag)

            //Add header

        }
        return buffer;
    }

    public static byte[] decompress (byte[] buffer){
        return buffer;
    }

    public static void main(String[] args) {

        try {

            File infile = new File("/home/maller/Downloads/cluet.ppm");
            File outfile = new File("/home/maller/Downloads/compresset.ppm");

            long size = infile.length(); //Tamany del fitxer per posar-lo al buffer
            byte[] buffer = new byte[(int) size];

            FileInputStream fis = new FileInputStream(infile);
            fis.read(buffer);
            System.out.println("Inici fitxer: ");
            System.out.println((char)buffer[0] + " " + (char)buffer[1]); //Know if is P3 or P6
            System.out.println(Arrays.toString(buffer));


            //Comprimir a buffer
            byte[] compressedbuffer = compress(buffer);

            System.out.println("Compressed result: ");
            System.out.println(Arrays.toString(compressedbuffer));
            /*
            //Write to file .fg


            //Descomprimir a buffer
            byte[] decompressedbuffer = decompress(compressedbuffer);

            System.out.println("Decompressed result: ");
            System.out.println(Arrays.toString(decompressedbuffer));
            */

            FileOutputStream fos = new FileOutputStream(outfile);
            fos.write(/*decompressed*/buffer);

            fis.close();
            fos.close();

            System.out.println("JPEG acabat amb èxit.");
        }

        catch (FileNotFoundException e) {
            System.out.println("El fichero no ha sido encontrado");

        } catch (IOException e) {
            System.out.println("Error en la entrada salida");
        }
    }
}

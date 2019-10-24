package Algoritmes;

import java.io.*;


public class JPEG {
    public static void main(String[] args) {

        try {

            File infile = new File("/home/maller/Downloads/cluet.ppm");
            File outfile = new File("/home/maller/Downloads/compresset.ppm");

            long size = infile.length(); //Tamany del fitxer per posar-lo al buffer
            byte[] buffer = new byte[(int) size];

            InputStream is = new FileInputStream(infile);
            is.read(buffer, 0, buffer.length);
            is.close();

            //Comprimir a buffer

            //Descomprimir a buffer

            OutputStream os = new FileOutputStream(outfile);
            os.write(buffer, 0, buffer.length);
            os.close();

            System.out.println("JPEG acabat amb èxit.");
        }

        catch (FileNotFoundException e) {
            System.out.println("El fichero no ha sido encontrado");
        } catch (IOException e) {
            System.out.println("Error en la entrada saldia");
        }
    }
}

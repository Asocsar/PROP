package Algoritmes;
import javax.naming.InsufficientResourcesException;
import javax.print.DocFlavor;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.*;
import java.lang.*;

public class LZSS {

    public LZSS() { }


    public static double filesize;

    public static double CompressTime;

    public static double CompressRatio;

    public static int search_buffer;

    //DESCRIPCIÓ DEL MÈTODE : Obtenim el ratio de compressió
    //PRE: Cert
    //POST: Es retorna el ratio de compressió.
    //EXCEPCIONS:

    public static double getRate() {
        return CompressRatio;
    }


    //DESCRIPCIÓ DEL MÈTODE : Obtenim el temps de compressió
    //PRE: Cert
    //POST: Es retorna el temps de compressió.
    //EXCEPCIONS:

    public static double getTime() {
        return CompressTime;
    }

    //DESCRIPCIÓ DEL MÈTODE : Càlcul del ratio de compressió
    //PRE: srcsize > 0
    //POST: Es retorna el quocient entre la mida de l'arxiu comprimit i la mida de l'arxiu original, en bytes
    //EXCEPCIONS: La mida de l'arxiu font és 0 -> quocient indeterminat


    public static double CalcCompressRatio(double srcsize, double compressedsize) {
        try {
            if (srcsize <= 0) throw new ArithmeticException();

        } catch (ArithmeticException a) {
            System.out.print("Mida de l'arxiu font invàlida");
        }
        return compressedsize/srcsize;
    }

    //DESCRIPCIÓ DEL MÈTODE : Codifiació de l'arxiu font en un array de bytes amb l'algorisme LZSS
    //PRE: file.size() > 3
    //POST: Es retorna un array de bytes amb els elements codificats
    //EXCEPCIONS: file.size() <= 3


    public static Byte[] compress(byte[] file) throws IOException {
        int match, offset, SB, LAB,LABini;
        match = 0;
        offset = 0;
        SB = 0;
        LAB = 1;
        LABini = 1;
        boolean found = false;
        String mask = "0";

        List<Byte> result = new ArrayList<>(); //Llista temporal
        List<Byte> encoded = new ArrayList<>(); // Llista definitiva
        result.add(file[0]);


        if (filesize < 4095) search_buffer = 0;
        else search_buffer = 4095;
        //Recorrem l'arxiu
        while ( LAB < file.length) {
            //Si la mascara te mida 7, codifiquem en la llista definitiva mask+llista temporal
            if (mask.length() == 7 ) {
                encoded.addAll((encode(mask, result)));
                mask = "";
                result.clear();
            }
            //Generem una parella <match,offset> si : hem trobat el match màxim, o els elements actuals ja no coincideixen i el match > 3
            if (found && (match == 127 || ((SB == LABini || file[LAB] != file[SB]) && match > 3))) {

                byte binmatch = (byte) (match);
                byte binoffsetl = ((byte) (offset));  // 3<offset<4096 --> s'ha de codificar en 2 bytes
                byte binoffseth = (byte) (offset >> 8);

                result.add(binmatch);
                result.add(binoffseth);
                result.add(binoffsetl);

                mask += '1'; // Com hem generat una parella, l'element següent de la màscara és 1

                match = 0;
                offset = 0;
                found = false;

                if (LAB >= 4095) SB = LAB - search_buffer;
                else SB = 0;

            }
            //Si anem trobant elements coincidents augmentem el match
            else if (file[LAB] == file[SB] && SB < LABini) {
                if (!found) {
                    offset = LAB - SB; // Si és el primer element coincident (trobat == false), guardem la diferència entre ambdós (offset)
                    found = true;
                }
                match++;
                SB++;
                LAB++;
            }
            //Si no hem trobat cap element coincident, o bé hem trobat el primer que no ho és i el match actual és < 4, codifiquem com a caràcter
            else if ((found && match < 4 && (file[LAB] != file[SB] || SB == LABini ))  || (!found && SB == LAB - 1)) {
                byte notmatched;
                if (found) {
                    notmatched = file[LAB - match]; //Si hi havia coincidència hem de restar match de l'element que ens trobem per situar-nos en l'actual
                    found = false;
                    LAB -= match;
                } else notmatched = file[LAB]; //Sino, l'actual és el que toca codificar

                result.add(notmatched);

                mask += '0'; //Hem afegit un caràcter, per tant el següent bit de la mascara és 0.

                LAB++;
                LABini = LAB;
                offset = 0;
                match = 0;

                if (LAB >= 4095) SB = LAB - search_buffer;
                else SB = 0;
            }
            else ++SB;
        }
        Byte[] byteencoding = encoded.toArray(new Byte[encoded.size()]); //Convertim la llista a un byte[}
        return byteencoding;
    }

    //DESCRIPCIÓ DEL MÈTODE : Codificació en una sola llista d'una màscara i els elements corresponents als bits d'aquesta
    //PRE: Cert
    //POST: Es retorna una llista composta per un byte de màscara i els elements als qui fa referència
    //EXCEPCIONS:

    public static List<Byte> encode(String mask, List<Byte> current) {
        //Codifiquem en una sola llista la màscara actual i els elements corresponents a aquesta màscara
        List<Byte> encoded = new ArrayList<>();
        Byte Bmask = Byte.parseByte(mask,2);
        encoded.add(Bmask);
        for (byte bin : current) {

            encoded.add(bin);
        }
        return encoded;
    }







    //DESCRIPCIÓ DEL MÈTODE: Descodifiquem en un string l'array de bytes de l'arxiu comprimit
    //PRE: Bencoded és un arxiu codificat segons LZSS
    //POST: És retorna la cadena de caracters corresponent a la descodificació de Bencoded
    //EXCEPCIONES:

    public static StringBuilder decompress (Byte[] Bencoded)  {
        List<Byte> encoded = Arrays.asList(Bencoded);
        StringBuilder result = new StringBuilder();
        int resultindex = 0;
        //Recorrem els bytes codificats
        for (int i = 0; i < encoded.size();) {

            //Obtenim la màscara i la posem en 7 bits

            String binmask = Integer.toBinaryString(((int) encoded.get(i++)) & 0xFF);
            while (binmask.length() < 7) binmask = "0" + binmask;

            //Recorrem la màscara de bits per identificar què són caràcters (0) i què són parelles <match,offset> (1)

            for (int maskind = 0; maskind < binmask.length(); ++maskind) {
                //Obtenim caràcters
                if (binmask.charAt(maskind) == '0') {
                    result.append((char) (encoded.get(i++)).intValue());
                    resultindex++;
                }
                //Decsodifiquem parelles
                else {
                    String offset,offsetl,offseth;

                    offsetl = Integer.toBinaryString(encoded.get(i+2).intValue()&0xFF);
                    offseth = Integer.toBinaryString(encoded.get(i+1).intValue()&0xFF);
                    offset = offseth+offsetl;

                    int offsetint = Integer.parseInt(offset,2);

                    for (int match = 0; match < encoded.get(i).intValue(); ++match) {
                        result.append((result.charAt(resultindex - offsetint)));
                        resultindex++;
                    }
                    i += 3;
                }
            }
        }
        return result;
        }





    public static void print_status(Byte[] compressed, StringBuilder decompressed) {
        for (int i = 0; i < compressed.length; ++i) {
                System.out.print(compressed[i]);
                System.out.print(",");
            }
        System.out.println("\n");
        //for (int f = 0; f < flags.length(); f++) if (flags.get(f)) System.out.println(f);
        System.out.println("\n");
        for (int d = 0; d < decompressed.length();++d) System.out.print(decompressed.charAt(d));
        System.out.println("\n");
        System.out.println(CompressRatio);
        System.out.println("\n");
        System.out.println(CompressTime);
    }

    public static void WriteatFile (StringBuilder decompressed) throws IOException{
        File out = new File("/home/clums/Escriptori/Uncompressed.fS");
        FileWriter write = new FileWriter(out);
        for (int i = 0; i < decompressed.length(); ++i) {
            write.write(decompressed.charAt(i));
        }
    }




    public static void main(String[] args) throws IOException {

        double startTime = System.currentTimeMillis();

        File file = new File("/home/clums/Escriptori/Ejemplo.txt");
        byte[] bytefile = Files.readAllBytes(file.toPath());
        filesize = bytefile.length;


        Byte[] compressed = compress(bytefile);
        Object oc = compressed;


        double endTime = System.currentTimeMillis();
        CompressTime = (endTime - startTime);

        StringBuilder decompressed = decompress(compressed);
        CompressRatio = CalcCompressRatio(filesize, compressed.length);
        //WriteatFile(decompressed);
        print_status(compressed,decompressed);
    }
}


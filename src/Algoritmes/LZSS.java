package Algoritmes;
import javax.naming.InsufficientResourcesException;
import javax.print.DocFlavor;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.*;
import java.lang.*;

public class LZSS {

    public LZSS() {
    }

    ;

    public static int flags = 0b00000000;

    public static int flagindex = 1;

    //public static int uconst = 128;

    public static double filesize;

    public static double CompressTime;

    public static double CompressRatio;

    public static int search_buffer;


    public static double getRate() {
        return CompressRatio;
    }

    public static double getTime() {
        return CompressTime;
    }

    public static double CalcCompressRatio(double srcsize, double compressedsize) {

        return compressedsize / srcsize;
    }




    private static int getMatchedLen(CharSequence src, int i1, int i2, int end) {
        int n = Math.min(i2 - i1, end - i2);
        for (int i = 0; i < n; i++) {
            if (src.charAt(i1++) != src.charAt(i2++)) return i;
        }
        return 0;
    }




    public static Byte[] compress(byte[] file) throws IOException {
        int match, offset, SB, LAB,LABini;
        match = 0;
        offset = 0;
        SB = 0;
        LAB = 1;
        LABini = 1;
        boolean found = false;
        String mask = "0";

        List<Byte> result = new ArrayList<>();
        List<Byte> encoded = new ArrayList<>();
        result.add(file[0]);


        if (filesize < 4095) search_buffer = 0;
        else search_buffer = 4095;
        while (SB < LAB & LAB < file.length) {

            if (mask.length() == 7) {
                encoded.addAll((encode(mask, result)));
                mask = "";
                result.clear();
            }

            if (found && (match == 127 || ((SB == LABini - 1 || file[LAB] != file[SB]) && match > 2))) {

                byte binmatch = (byte) (match);
                byte binoffsetl = (byte) (offset);
                byte binoffseth = (byte) (offset >> 8);

                result.add(binmatch);
                result.add(binoffseth);
                result.add(binoffsetl);

                mask += '1';

                match = 0;
                offset = 0;
                found = false;

                if (LAB >= 4095) SB = LAB - search_buffer;
                else SB = 0;

            }
            else if (file[LAB] == file[SB] && SB < LABini) {
                if (!found) {
                    offset = LAB - SB;
                    found = true;
                }
                match++;
                SB++;
                LAB++;
            }
            else if ((found && match < 3 && (file[LAB] != file[SB] || SB == LABini))  || (!found && SB == LAB - 1)) {
                byte notmatched;
                if (found) {
                    notmatched = file[LAB - match];
                    found = false;
                    LAB -= match;
                } else notmatched = file[LAB];

                result.add(notmatched);

                mask += '0';

                LAB++;
                LABini = LAB;
                offset = 0;
                match = 0;

                if (LAB >= 4095) SB = LAB - search_buffer;
                else SB = 0;
            }
            else ++SB;
        }
        Byte[] byteencoding = encoded.toArray(new Byte[encoded.size()]);
        return byteencoding;
    }


    public static List<Byte> encode(String mask, List<Byte> current) {

        List<Byte> encoded = new ArrayList<>();
        Byte Bmask = Byte.parseByte(mask,2);
        encoded.add(Bmask);
        for (byte bin : current) {

            encoded.add(bin);
        }
        return encoded;
    }







    //DESCRIPCIÓ DEL MÈTODE
    //PRE:
    //POST:
    //EXCEPCIONES

    public static StringBuilder decompress (Byte[] Bencoded) {
        List<Byte> encoded = Arrays.asList(Bencoded);
        StringBuilder result = new StringBuilder();
        int resultindex = 0;
        for (int i = 0; i < encoded.size();) {
            String binmask = Integer.toBinaryString(((int) encoded.get(i++)) & 0xFF);
            while (binmask.length() < 7) binmask = "0" + binmask;
            for (int maskind = 0; maskind < binmask.length(); ++maskind) {
                if (binmask.charAt(maskind) == '0') {
                    result.append((char) (encoded.get(i++)).intValue());
                    resultindex++;
                }
                else {
                    int offset;
                    offset = (encoded.get(i + 2).intValue() + (encoded.get(i + 1).intValue() >> 8));
                    for (int match = 0; match < encoded.get(i).intValue(); ++match) {
                        result.append((result.charAt(resultindex - offset)));
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
        for (int d = 0; d < decompressed.length();++d) System.out.println(decompressed.charAt(d));
        System.out.println("\n");
        System.out.println(CompressRatio);
        System.out.println("\n");
        System.out.println(CompressTime);
    }

    public static void WriteatFile (StringBuilder decompressed) throws IOException{
        File out = new File("/home/clums/Escriptori/Uncompressed.fS");
        FileWriter write = new FileWriter(out);
        PrintWriter pw = new PrintWriter(write);
        for (int i = 0; i < decompressed.length(); ++i) {
            pw.println(decompressed.charAt(i));
        }
    }




    public static void main(String[] args) throws IOException {

        double startTime = System.currentTimeMillis();

        File file = new File("/home/clums/Escriptori/JOCS_DE_PROVA/Non_Extended_ASCII/UnMundoFeliz-Non_extended.txt");
        byte[] bytefile = Files.readAllBytes(file.toPath());
        filesize = bytefile.length*8;


        Byte[] compressed = compress(bytefile);
        Object oc = compressed;


        double endTime = System.currentTimeMillis();
        CompressTime = (endTime - startTime);

        // Byte[] voidl = {(byte) 10, (byte) 112,(byte) 97,(byte) 97,(byte) 112,(byte) 2,(byte) 0,(byte) 4,(byte) 112,(byte) 3,(byte) 0,(byte) 4,(byte) 98};
        //Object o = voidl;
        StringBuilder decompressed = decompress(compressed);
        CompressRatio = CalcCompressRatio(filesize, compressed.length*8);
        //WriteatFile(decompressed);
        print_status(compressed,decompressed);
    }
}


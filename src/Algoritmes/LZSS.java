package Algoritmes;
import javax.naming.InsufficientResourcesException;
import javax.print.DocFlavor;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.*;
import java.lang.*;

public class LZSS {

    public LZSS() {};

    public static int flags = 0b00000000;

    public static int flagindex = 1;

    //public static int uconst = 128;

    public static double filesize;

    public static double CompressTime;

    public static double CompressRatio;

    public static int search_buffer;


    public static double getRate(){
        return CompressRatio;
    }

    public static double getTime(){
        return CompressTime;
    }

    public static double CalcCompressRatio (double srcsize,double compressedsize){

        return compressedsize/srcsize;
    }


    /*public static Integer[] conversion (List<Byte> encoded){
        Integer[] aux = new Integer[encoded.size()/8];
        String stringencoded = encoded.toString();
        for (int i = 0; i < encoded.size();){
            if (stringencoded.charAt(i) == 0) {
                aux[i++] = 0;
                aux[i] = Integer.parseInt((stringencoded.substring(i,i+8)),2);
                i += 8;
            }
            else {
                aux[i++] = 1;
                aux[i] = Integer.parseInt((stringencoded.substring(i,i+4)),2);
                i += 4;
                aux[i] = Integer.parseInt((stringencoded.substring(i,i+12)),2);
                i += 12;
            }
        }
        return aux;
    }



    private static int getMatchedLen(CharSequence src, int i1, int i2, int end) {
        int n = Math.min(i2 - i1, end - i2);
        for (int i = 0; i < n; i++) {
            if (src.charAt(i1++) != src.charAt(i2++)) return i;
        }
        return 0;
    }



    public static List<BitSet> initialize_searchbuff (String sequence){
        List<BitSet> ini = new ArrayList<>();
        if (sequence.length() > 0){
            int init = sequence.charAt(0);
            ini.add(convert(Integer.toBinaryString(init),false));
        }
        return ini;
    }*/

    public static Byte encode_mask (int vflagindex){
        flags = (flags & 0x1) << vflagindex;
        Byte mask = (byte) flags;
        return mask;
    }

    public static Byte[] compress_mine2 (byte[] file) throws IOException {
        int match, offset, SB, LAB;
        match = 0;
        offset = 0;
        SB = 0;
        LAB = 1;
        boolean found = false;
        Byte mask = 0b00000000;

        List<Byte> result = new ArrayList<>();
        List<Byte> encoded = new ArrayList<>();
        result.add(file[0]);


        //flags.set(flagindex++,false);

        if (filesize < 4095) search_buffer = 0;
        else search_buffer = 4095;
        while (SB < LAB & LAB < file.length) {

            if (flagindex == 8) {

                flagindex = 0;
                encoded.addAll((encode(mask, result)));
                result.clear();

            }

            if (found && (match == 127 || ((file[LAB]) != file[SB] & match > 2))) {

                int offsetl = offset & 0xFF;
                int offseth = offset & 0x0F00;
                //if (match > 127) match = (0 - (match-128));
                if (offsetl > 127)  offsetl = (0 - (offset-128));

                byte binmatch = (byte) (match);
                byte binoffsetl = (byte) (offsetl);
                byte binoffseth = (byte) (offseth);

                result.add(binmatch);
                result.add(binoffseth);
                result.add(binoffsetl);

                mask = encode_mask(flagindex++);

                match = 0;
                offset = 0;
                found = false;

                if (LAB >= 4095) SB = LAB - search_buffer;
                else SB = 0;
            }
            else if (file[LAB] == file[SB]) {
                if (!found) {
                    offset = LAB - SB;
                    found = true;
                }
                match++;
                SB++;
                LAB++;
            }

            else if (file[LAB] != file[SB] & found & match < 3 || SB == LAB - 1 & !found) {
                byte notmatched;
                if (found) {
                    notmatched = file[LAB - match];
                    found = false;
                    LAB -= match;
                }
                else notmatched = file[LAB];

                result.add(notmatched);
                flagindex++;


                LAB++;
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


    public static List<Byte> encode (Byte mask, List<Byte> current) {
        List<Byte> encoded = new ArrayList<>();
        encoded.add(mask);
        for(byte bin : current){
            encoded.add(bin);
        }
        flags = 0b00000000;
        return encoded;
    }



    /*public static BitSet current_mask(Byte Bmask){
        BitSet bitmask = new BitSet(8);
        String binmask =  Integer.toBinaryString(((int) Bmask) & 0xFF);
        boolean act = false;
        for(int i = 7; i >= 0; --i){
            if( i >= binmask.length() || binmask.charAt(i) == '0') act = false;
            else if ( binmask.charAt(i) == '1' ) act = true;
            bitmask.set((7-i),act);
        }
        return bitmask;
    }*/


    public static StringBuilder decompress (Object o) {
        Byte[] byteencoded = (Byte[]) o;
        List<Byte> encoded = Arrays.asList(byteencoded);
        StringBuilder result = new StringBuilder();
        int resultindex = 0;
        for (int i = 0; i < encoded.size();) {
            String mask = Integer.toBinaryString((int)encoded.get(i++));
            for (int maskind = 7; maskind > 0; --maskind) {
                if ((maskind >= mask.length()) || mask.charAt(maskind) == '0') {
                    result.append((char) (encoded.get(i++)).intValue());
                    resultindex++;
                }
                else if(mask.charAt(maskind) == '1') {
                    int offset;
                    offset = (encoded.get(i + 2).intValue() + (encoded.get(i + 1).intValue() << 8));
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

        File file = new File("/home/clums/Escriptori/Ejemplo.txt");
        byte[] bytefile = Files.readAllBytes(file.toPath());
        filesize = bytefile.length*8;
        Object in;
        //BufferedReader br = new BufferedReader(new FileReader(file));

        Byte[] compressed = compress_mine2(bytefile);
        Object oc = compressed;

        /*for(int i = 0; i< compressed.length; ++i) {
            System.out.print(compressed[i]);
            System.out.print(',');
        }*/

        double endTime = System.currentTimeMillis();
        CompressTime = (endTime - startTime);

        Byte[] voidl = {(byte) 10, (byte) 112,(byte) -23,(byte) 97,(byte) 112,(byte) 2,(byte) 0,(byte) 3,(byte) 112,(byte) 3,(byte) 0,(byte) 4,(byte) 98};
        Object o = voidl;
        StringBuilder decompressed = decompress(o);
        CompressRatio = CalcCompressRatio(filesize, compressed.length*8);
        //WriteatFile(decompressed);
        print_status(compressed,decompressed);
    }
}


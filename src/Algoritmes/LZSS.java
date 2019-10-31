package Algoritmes;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

    public class LZSS {

        public LZSS() {};

        public static BitSet flags = new BitSet();

        public static double CompressRatio (String src, List<Integer> encoded){
            return encoded.size()/src.length();
        }


        private static int getMatchedLen(CharSequence src, int i1, int i2, int end) {
            int n = Math.min(i2 - i1, end - i2);
            for (int i = 0; i < n; i++) {
                if (src.charAt(i1++) != src.charAt(i2++)) return i;
            }
            return 0;
        }

        private static String getString(BufferedReader file) throws IOException {
            String src = "";
            int act;
            while ((act = file.read()) != -1) {
                src += (char) act;
            }
            return src;


        }


        public static List<Integer> initialize_searchbuff (String sequence){
            List<Integer> ini = new ArrayList<>();
            for (int i = 0; i < sequence.length(); i++){
                int init = sequence.charAt(i);
                ini.add(init);
            }
            return ini;
        }

        public static List<List<Integer>> compress_mine2 (BufferedReader file) throws IOException {
            String sequence = getString(file);
            flags.set(0, sequence.length(), false);
            int match, offset;
            match = 0;
            offset = 0;
            boolean found = false;
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            String init;
            int ini;
            if(sequence.length() > 6){
                init = sequence.substring(0,6);
                ini = 6;
            }
            else {
                init = sequence;
                ini = 1;
            }
            result.add(initialize_searchbuff(init));
            for (int rec = ini; rec < sequence.length();) {
                for (int LA = rec - 6; LA < rec  & rec < sequence.length();) {
                    if (sequence.charAt(rec) == sequence.charAt(LA) & !found) {
                        offset = rec-LA;
                        //flags.set(rec);
                        found = true;
                        match++;
                        LA++;
                        rec++;
                    }
                    else if (sequence.charAt(rec) == sequence.charAt(LA) & found) {
                        match++;
                        LA++;
                        rec++;
                    }
                    else if (sequence.charAt(rec) != sequence.charAt(LA) & found) {
                        List<Integer> matched = new ArrayList<>(2);
                        matched.add(match);
                        matched.add(offset);
                        result.add(matched);
                        //flags.set(rec-match-1);
                        match = 0;
                        offset = 0;
                        found = false;
                        //rec++;
                        LA = rec-6;
                    }
                    else if (LA == rec-1 & !found){
                        List<Integer> character = new ArrayList<>(1);
                        int notmatched = sequence.charAt(rec);
                        character.add(notmatched);
                        result.add(character);
                        rec++;
                        LA = rec-6;
                    }
                    else ++LA;
                }
            }
            return result;
        }

        public static List<Integer> encode (List<List<Integer>> compressed){
            List<Integer> encoded = new ArrayList<>();
            int flag = 0;
            for (List<Integer> act : compressed){
                if (act.size() == 2) {
                    flags.set(flag+5);
                    flag +=2;
                }
                else ++flag;
                for(Integer acti : act){
                    encoded.add(acti);
                }
            }
            return encoded;
        }

       public static StringBuilder decompress (List<Integer> encoded, BitSet flags) {
           StringBuilder result = new StringBuilder();
           int resultindex = 0;
           for (int i = 0; i < encoded.size();/*i++*/) {
               if (!flags.get(i)) {
                   result.append((char) (encoded.get(i).intValue()));
                   ++i;
                   resultindex++;
               }
               else {
                   int offset = encoded.get(i + 1);
                   for (int match = 0; match < encoded.get(i) ; ++match ) {
                       result.append((result.charAt(resultindex-offset)));
                       resultindex++;
                       offset--;
                       }
                       i += 2;
               }
           }
           return result;
       }



        public static void print_compress (List<List<Integer>> compressed, List<Integer> encoded, StringBuilder decompressed) {
            for (int i = 0; i < compressed.size(); ++i) {
                System.out.print("/");
                for (int j = 0; j < compressed.get(i).size(); ++j) {
                    System.out.print(compressed.get(i).get(j));
                    System.out.print("~");
                }
            }
            System.out.println("\n");
            for (int f = 0; f < flags.length(); f++) System.out.println(flags.get(f));
            System.out.println("\n");
            for (int s = 0; s < encoded.size(); ++s) System.out.println(encoded.get(s));
            System.out.println("\n");
            for (int d = 0; d < decompressed.length();++d) System.out.println(decompressed.charAt(d));
        }

        public static void WriteatFile (StringBuilder decompressed) throws IOException{
                    File out = new File("/home/clums/Escriptori/Uncompressed.txt");
                    FileWriter write = new FileWriter(out);
                    PrintWriter pw = new PrintWriter(write);
                    for (int i = 0; i < decompressed.length(); ++i) {
                        pw.println(decompressed.charAt(i));
                    }
            }


        public static void main(String[] args) throws IOException {
            File file = new File("/home/clums/Escriptori/Ejemplo.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<List<Integer>> compressed = compress_mine2(br);
            List<Integer> encoded = encode(compressed);
            StringBuilder decompressed = decompress(encoded,flags);
            WriteatFile(decompressed);
            print_compress(compressed,encoded,decompressed);
        }
    }



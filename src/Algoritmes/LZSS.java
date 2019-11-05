package Algoritmes;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

    public class LZSS {

        public LZSS() {};

        public static BitSet flags = new BitSet();

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
                filesize++;
            }
            return src;
        }


        public static List<Integer> initialize_searchbuff (String sequence){
            List<Integer> ini = new ArrayList<>();
            if (sequence.length() > 0){
                int init = sequence.charAt(0);
                ini.add(init);
            }
            return ini;
        }

        public static List<List<Integer>> compress_mine2 (BufferedReader file) throws IOException {
            String sequence = getString(file);
            flags.set(0, sequence.length(), false);
            int match, offset, LA, rec;
            match = 0;
            offset = 0;
            LA = 0;
            rec = 1;
            boolean found = false;

            List<List<Integer>> result = new ArrayList<>();
            result.add(initialize_searchbuff(sequence.substring(0,1)));

            if (filesize < 4096) search_buffer = 0;
            else search_buffer = 4096;
                while (LA < rec & rec < sequence.length()) {
                    if ((match == 32 & found) | (sequence.charAt(rec) != sequence.charAt(LA) & found & match > 2) ) {
                        List<Integer> matched = new ArrayList<>(2);
                        matched.add(match);
                        matched.add(offset);
                        result.add(matched);
                        match = 0;
                        offset = 0;
                        found = false;
                        LA = search_buffer;
                    }
                    else if (sequence.charAt(rec) == sequence.charAt(LA) ){
                        if (!found) {
                            offset = rec - LA;
                            found = true;
                        }
                            match++;
                            LA++;
                            rec++;
                    }

                    else if (sequence.charAt(rec) != sequence.charAt(LA) & found & match < 3 | LA == rec -1)  {
                        List<Integer> character = new ArrayList<>(1);
                        int notmatched;
                        if (found) {
                            notmatched = sequence.charAt(rec-match);
                            found = false;
                            rec -= (match+1);
                        }
                        else{
                            notmatched = sequence.charAt(rec);
                            rec++;
                        }
                        character.add(notmatched);
                        result.add(character);
                        LA = search_buffer;
                        offset = 0;
                        match = 0;
                    }
                else  ++LA;
                }
            return result;
        }

        public static List<Integer> encode (List<List<Integer>> compressed) {
            List<Integer> encoded = new ArrayList<>();
            for (List<Integer> act : compressed){
                if (act.size() == 2) {
                    encoded.add(-1);
                    //flags.set(flag);
                    //flag +=2;
                }
                //else ++flag;
                for(Integer acti : act){
                    encoded.add(acti);
                    //if( last < compressed.size()) encoded.add((int) ',');
                }

            }
            return encoded;
        }


        public static Integer[] conversion (List<String> encoded){
            Integer[] aux = new Integer[encoded.size()];
            String [] Arrayencoded = encoded.toArray(new String[0]);
            for (int i = 0; i < encoded.size(); ++i){
                aux[i] = Integer.parseInt((Arrayencoded[i]));
            }
            return aux;
        }


       public static StringBuilder decompress (Object o/*List<String> encoded, BitSet flags*/) {
           List <String> encoded = (List<String>) o;
           StringBuilder result = new StringBuilder();
           int resultindex = 0;
           //String[] aux = new String[encoded.size()];
           //aux = encoded.toArray(aux);
           Integer[] aux = conversion(encoded);
           for (int i = 0; i < encoded.size();) {
                   if (aux[i] != -1) {
                       result.append(Character.toString(aux[i]));
                       ++i;
                       resultindex++;
                   } else {
                       int offset = aux[i+2];
                       for (int match = 0; match < aux[i+1]; ++match) {
                           result.append((result.charAt(resultindex - offset)));
                           resultindex++;
                       }
                       i += 3;
                   }
               }
           return result;
       }



        public static void print_status(List<List<Integer>> compressed, List<Integer> encoded, StringBuilder decompressed) {
            for (int i = 0; i < compressed.size(); ++i) {
                for (int j = 0; j < compressed.get(i).size(); ++j) {
                    System.out.print(compressed.get(i).get(j));
                    System.out.print(",");
                }
            }
            System.out.println("\n");
            for (int f = 0; f < flags.length(); f++) if (flags.get(f)) System.out.println(f);
            System.out.println("\n");
            for (Integer i : encoded) System.out.print(i + ",");
            System.out.println("\n");
            for (int d = 0; d < decompressed.length();++d) System.out.println(decompressed.charAt(d));
            System.out.println("\n");
            System.out.println(CompressRatio);
            System.out.println("\n");
            System.out.println(CompressTime);
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

            double startTime = System.currentTimeMillis();

            File file = new File("/home/clums/Escriptori/Ejemplo.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<List<Integer>> compressed = compress_mine2(br);
            List<Integer> encoded = encode(compressed);


            double endTime = System.currentTimeMillis();
            CompressTime = (endTime - startTime);

           List<String> voidl = Arrays.asList("118","105","115","116","97","32","-1","1","2","-1","4","5","99","-1","5","6","109","-1","3","6","114","101","32","112","97","115","116","-1","1","3","-1","1","6","108","-1","5","6","-1","2","5","100","-1","2","6","10");
           Object o = voidl;
           StringBuilder decompressed = decompress(o/*,flags*/);
            CompressRatio = CalcCompressRatio(filesize, encoded.size());
            //WriteatFile(decompressed);
            print_status(compressed,encoded,decompressed);
        }
    }



package Algoritmes;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

    public class LZSS {

        public LZSS() {};

        public BitSet flags = new BitSet();

        public double filesize;

        public double CompressTime;

        public double CompressRatio;

        public int search_buffer;


        public double getRate(){
            return CompressRatio;
        }

        public double getTime(){
            return CompressTime;
        }

        public double CalcCompressRatio (double srcsize,double compressedsize){

            return compressedsize/srcsize;
        }


        private int getMatchedLen(CharSequence src, int i1, int i2, int end) {
            int n = Math.min(i2 - i1, end - i2);
            for (int i = 0; i < n; i++) {
                if (src.charAt(i1++) != src.charAt(i2++)) return i;
            }
            return 0;
        }

        private String getString(BufferedReader file) throws IOException {
            String src = "";
            int act;
            while ((act = file.read()) != -1) {
                src += (char) act;
                filesize++;
            }
            return src;
        }


        public List<Integer> initialize_searchbuff (String sequence){
            List<Integer> ini = new ArrayList<>();
            if (sequence.length() > 0){
                int init = sequence.charAt(0);
                ini.add(init);
            }
            return ini;
        }

        public List<List<Integer>> compress_mine2 (BufferedReader file) throws IOException {
            String sequence = getString(file);
            flags.set(0, sequence.length(), false);
            int match, offset, LA, rec;
            match = 0;
            offset = 0;
            LA = 0;
            rec = 1;
            boolean found = false;

            List<List<Integer>> result = new ArrayList<>();
            result.add(initialize_searchbuff(sequence));

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
                        if(rec >= 4096) LA = rec - search_buffer;
                        else LA = 0;
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

                    else if (sequence.charAt(rec) != sequence.charAt(LA) & found & match < 3 | LA == rec -1 & !found)  {
                        List<Integer> character = new ArrayList<>(1);
                        int notmatched;
                        if (found) {
                            notmatched = sequence.charAt(rec-match);
                            found = false;
                            rec -= match;
                        }
                        else{
                            notmatched = sequence.charAt(rec);
                        }
                        rec++;
                        character.add(notmatched);
                        result.add(character);
                        offset = 0;
                        match = 0;
                        if(rec >= 4096) LA = rec - search_buffer;
                        else LA = 0;
                    }
                else  ++LA;
                }
            return result;
        }

        public List<Integer> encode (List<List<Integer>> compressed) {
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


        public Integer[] conversion (List<String> encoded){
            Integer[] aux = new Integer[encoded.size()];
            String [] Arrayencoded = encoded.toArray(new String[0]);
            for (int i = 0; i < encoded.size(); ++i){
                aux[i] = Integer.parseInt((Arrayencoded[i]));
            }
            return aux;
        }


       public StringBuilder decompress (Object o/*List<String> encoded, BitSet flags*/) {
           List <String> encoded = (List<String>) o;
           StringBuilder result = new StringBuilder();
           int resultindex = 0;
           //String[] aux = new String[encoded.size()];
           //aux = encoded.toArray(aux);
           Integer[] aux = conversion(encoded);
           for (int i = 0; i < encoded.size();) {
                   if (aux[i] != -1) {
                       result.append(/*Character.toString*/(aux[i]));
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

    }



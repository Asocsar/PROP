package Algoritmes;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class LZSS {
    public static List<List<Integer>> compress(BufferedReader file) throws IOException {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        StringBuffer SearchBuffer = new StringBuffer();
        String currentMatch = "";
        int nextC;
        char c;
        int lenght;
        int matchIndex = 0, tempIndex = 0;
        while ((nextC=file.read()) != -1) {
            tempIndex = SearchBuffer.indexOf(currentMatch + (char) nextC);
            //Si hi ha match, l'afegim al match actual i actualitzem index
            if (tempIndex != -1) {
                currentMatch += (char) nextC;
                matchIndex = tempIndex;
            }
            else {
                // S'ha acabat el match, el codifiquem
                List<Integer> act = new ArrayList<>();
                c = (char) nextC;
                act.add(matchIndex);
                act.add(currentMatch.length());
                act.add((int) c);
                String union = currentMatch + (char) nextC;
                if (act.size() <= union.length()) {
                    result.add(act);
                    SearchBuffer.append(union);
                    currentMatch = "";
                    matchIndex = 0;
                }
                else {
                    currentMatch = union;
                    matchIndex = -1;
                    while (currentMatch.length() > 1 && matchIndex == -1) {
                        SearchBuffer.append(currentMatch.charAt(0));
                        currentMatch = currentMatch.substring(1, currentMatch.length());
                        matchIndex = SearchBuffer.indexOf(currentMatch);
                    }
                }
            }
        }
        return result;
    }


    /*public static String descomprimir (List<Integer> s) {return result;}*/

    public static void main(String[] args) throws IOException {
        File file = new File ("./Ejemplo.txt");
        BufferedReader br = new BufferedReader(new FileReader (file));
        List<List<Integer>> s = compress(br);
        System.out.println(s);
        //System.out.println(descomprimir(s));
    }
}


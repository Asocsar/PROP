package Algoritmes.JPEG.Huffman;
import java.sql.SQLOutput;
import java.util.Scanner;

public class HuffmanTables {

    public static void main(String[] args){
        int i, mask ;
        Scanner s = new Scanner(System.in);
        while(s.hasNext()){

            i = Integer.parseInt(s.next());
            String st = Integer.toBinaryString(Math.abs(i));
            mask = st.length();
            if(i < 0) st = Integer.toBinaryString(--i).substring(32 -mask);
            System.out.println(st + " " + mask);
        }

    }
}

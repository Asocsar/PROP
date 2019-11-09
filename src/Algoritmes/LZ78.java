package Algoritmes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class LZ78 {

    public long elapsed_time;
    public long length_n;
    public long length_c;

    public long get_time(){

        return elapsed_time;
    }


    private String ajuntar_llistes(List<Integer> a, List<Character> b){
        int n= a.size();
        String aux="";
        int aux_num;
        for (int i= 0; i < n; ++i){
            aux_num= a.get(i);
            aux = aux + aux_num + "/" + b.get(i);
        }
        return aux;
    }

    public String compresio(BufferedReader file) throws IOException {
        long startTime = System.currentTimeMillis();
        length_n=0;
        List<Integer> Indexs = new ArrayList<>();
        List<Character> Coded_text = new ArrayList<>();
        List<String> Caracters = new ArrayList<>();
        Caracters.add(null);
        int lletra = file.read();
        String aux_s = "";
        int index = 0;
        while (lletra != -1) {
            aux_s = aux_s + (char) lletra;
            if (!Caracters.contains(aux_s)) {
                Caracters.add(aux_s);
                Coded_text.add((char) lletra);
                Indexs.add(index);
                aux_s = "";
                index = 0;
            }
            else {
                index=  Caracters.indexOf(aux_s);
            }
            ++length_n;
            lletra = file.read();
        }
        String Coded= ajuntar_llistes(Indexs,Coded_text);
        long endTime = System.currentTimeMillis();
        elapsed_time= (endTime - startTime);
        length_c= Coded.length();
        return Coded;
    }

    public String escriure_llistes(Object b){
        List<String> a= (List<String>)b;
        int n= a.size();
        String aux="";
        for (int i= 1; i < n; ++i){
            String r= a.get(i);
            aux = aux + r.substring(4);

        }
        return aux;
    }

    public String descompresio(BufferedReader file) throws IOException {
        long startTime = System.currentTimeMillis();
        List<String> Caracters = new ArrayList<>();
        Caracters.add(null);
        int lletra = file.read();
        char aux_lletra = 0;
        int aux_in = 0;
        String aux_c = "";
        boolean trobat = false;
        while (lletra != -1) {
            if ((char) lletra == '/' && aux_lletra == '/') {
                aux_c = Caracters.get(aux_in) + (char) lletra;
                Caracters.add(aux_c);
                aux_lletra = 0;
                aux_c = "";
                lletra= file.read();
                aux_in=0;
            }

            else {
                if (aux_lletra == '/') {
                    aux_c = Caracters.get(aux_in) + (char) lletra;
                    Caracters.add(aux_c);
                    aux_lletra = 0;
                    aux_c="";
                    lletra=file.read();
                    aux_in=0;
                }
                else {
                    trobat=false;
                    while (lletra != -1 && !trobat){
                        if ((char)lletra == '/') {
                            trobat = true;
                            aux_lletra = '/';
                            lletra= file.read();
                        }
                        else {
                            aux_in= aux_in*10 + Character.getNumericValue((char)lletra);
                            lletra= file.read();
                        }
                    }

                }
            }
        }
        String r= escriure_llistes(Caracters);
        long endTime = System.currentTimeMillis();
        elapsed_time= (endTime - startTime);
        return r;

    }

    public Long get_ratio_c(){
        return length_n/length_c;
    }



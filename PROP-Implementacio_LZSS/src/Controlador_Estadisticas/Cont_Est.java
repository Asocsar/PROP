package Controlador_Estadisticas;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


//carga estadistiques primer metodo llamado /*lees archivo que tiene todas las estadisticas y actualizas los metodos con el archivo set estadistiques lzw



public class Cont_Est {

    public class Estadístiques{

    private static double timeLZW;
    private static double timeLZ78;
    private static double timeLZSS;
    private static double timeJPEG;

    private static double ratioLZW;
    private static double ratioLZ78;
    private static double ratioLZSS;
    private static double ratioJPEG;

    }




    public static List<Double> getValues (String line){
        List<Double> values = new ArrayList<>(5);
        for (int i = 0; i < line.length();++i){
            String actnumber = "";
            while (line.charAt(i) != ','){
                actnumber += line.charAt(i);
            }
            values.add(Double.parseDouble(actnumber));
        }
    }

    public static void setEstadistiques(BufferedReader file, Estadistiques E) throws IOException {
        String src = "";
        int act;
        String line = "";
        int flag;
        while ((act = file.read()) != -1) {
            //src += (char) act;
            if (act == 0 | act == 1 | act == 2 | act == 3) {
                flag = act;
                line = file.readLine();
                List<Double> values = getValues(line); //timelast,timeglob,ratelast,rateglob,cant
                switch (flag) {
                    case 0:
                        E.setLZW(values.get(0),values.get(1),values.get(2),values.get(3),values.get(4));
                        break;
                    case 1:
                        E.setLZ78(values.get(0),values.get(1),values.get(2),values.get(3),values.get(4));
                        break;
                    case 2:
                        E.setLZSS(values.get(0),values.get(1),values.get(2),values.get(3),values.get(4));
                        break;
                    case 3:
                        E.setJPEG(values.get(0),values.get(1),values.get(2),values.get(3),values.get(4));
                        break;
                    case 4:
                        E.lastAlg(line);
                    default:
                        break;
                }
            }

        }
        //return src;
    }


    public static String Update() {
        //String src = "";
        //int act;
        Estadistiques E = new Estadistiques();
        File out = new File("/home/clums/Escriptori/Estadisticas.txt");
        FileWriter write = new FileWriter(out);
        PrintWriter pw = new PrintWriter(write);


    }

    public static void main() {
        File file = new File("/home/clums/Escriptori/Estadisticas.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Estadistiques E = new Estadistiques();
        String s = Update(br, E);
        //Estadistiques E = new Estadistiques();
    }
}/*
    String S = leeer
    Estadisticas E = neeeeeew Estadisttttttticas
        E.setLZW(tiempoLZW, grrrrrrrrrrrrradoLZW
        )
    LZW
            time_last
    rate_last
            time_glob
    rate_glo
            LZSS
    LZ78

    Last algorithm

    cant

    }

}*/

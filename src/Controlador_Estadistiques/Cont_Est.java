package Controlador_Estadistiques;
import Estadístiques.Estadistiques;
import java.io.*;



//carga estadistiques primer metodo llamado /*lees archivo que tiene todas las estadisticas y actualizas los metodos con el archivo set estadistiques lzw



public class Cont_Est {

    public static double timeLZW;
    public static double timeLZ78;
    public static double timeLZSS;
    public static double timeJPEG;

    public static double ratioLZW;
    public static double ratioLZ78;
    public static double ratioLZSS;
    public static double ratioJPEG;


    private static String Update(BufferedReader file,Estadistiques E) throws IOException {
        String src = "";
        int act;
        int flag;
        while ((act = file.read()) != -1) {
            src += (char) act;
            if (act == 0 | act == 1 | act == 2 | act == 3) {
                flag = act;
                act = file.read();
                switch (flag) {
                    case 0:
                        E.setLZW(file.read(), file.read());
                        break;
                    case 1:
                        E.setLZ78(file.read(), file.read());
                        break;
                    case 2:
                        E.setLZSS(file.read(), file.read());
                        break;
                    case 3:
                        E.setJPEG(file.read(), file.read());
                        break;
                    default:
                        break;
                }
            }

        }
        return src;
    }

    public static String Get (BufferedReader file){
        String src = "";
        int act;
        while ()
    }

    public static void Update_Estadistiques{
        File file = new File("/home/clums/Escriptori/Estadisticas.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Estadistiques E = new Estadistiques();
        String s = Update(br,E);
        //Estadistiques E = new Estadistiques();


    }
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

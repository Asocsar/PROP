import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.lang.Exception;
import java.lang.String;

public class classe_fichero {

    //VARIABLES DE LA CLASSE
    private static int id_desc;
    private static int id_comp;
    private static Object file_dec;
    private static String path_dest;
    private Integer comp_desc;   //Controlador
    private static BufferedReader fitxer;
    private static String extensio;

    //EXCEPCIONS
    public static class FicheroCompressionNoValido extends Exception {

        public FicheroCompressionNoValido(String message) {
            super(message);
        }
    }

    public static class FicheroDescompressionNoValido extends Exception {

        public FicheroDescompressionNoValido(String message) {
            super(message);
        }
    }



    //FUNCIONS DE RETORN DE VARIABLES DE LA CLASSE (PUBLIQUES)

    //Retorna el fitxer convertit (FUNCIONA)
    public static Object get_file_dec(){
        return file_dec;
    }


    //COMPRESSIO: BUSCAR, LLEGIR ARXIU. (PRIVADES)

    //Transforma una imatge en un bufferedreader (CREAR EXCEPCIO FILENOTFOUND A DINS)

    private static BufferedReader ppm_convert (String path_og){
        fitxer= null;
        return fitxer;
    }

    //Busca l'arxiu i retorna aquest en un BufferedReader, i excepcio de file no trobat (FUNCIONA)
    private static BufferedReader buscar_leer_archivo(String path_og){
        try{
            File file = new File (path_og);
            if(path_og.substring(path_og.length()-3).equals("ppm")) fitxer= ppm_convert(path_og);
            else fitxer = new BufferedReader(new FileReader (file));
        }

        catch (FileNotFoundException e){
            System.out.println("Fichero no encontrado.");
        }

        return fitxer;

    }


    //FUNCIONS DE CREACIO I ESCRIPTURA (PUBLIQUES)

    //Crea un fitxer per escriptura
    public static void crear_fichero (String path_dest){
        
    }


    //Escriu en un fitxer
    public static void escribir_archivo (Object escript, int id_imatge, String path_dest){
        try {
            Path path = Paths.get(path_dest);
        }
        catch (InvalidPathException ex){
            System.out.println("El directorio destino no és valido");
        }

        if(id_imatge == 0){

        }

        else if(id_imatge == 1){

        }
    }

    //FUNCIONS DE TRANSFORMACIO DE FITXERS (PUBLIQUES)

    //Funcio d'excepcions compressió i transformacio(si es necessari)
    /*public static BufferedReader get_f_compressio(String path_og, String path_desti Integer id){
         
        extension(id);
        BufferedReader br= buscar_leer_archivo(path_og);


    }*/

    //DESC: Converteix un fitxer en un objecte que entenguin els diversos algorismes (FUNCIONA; FALTA JPEG)
    public static void conversio_fitxer_desc(String path_og) throws IOException {
        //id_algorismo(path_og);
        BufferedReader br = buscar_leer_archivo(path_og);
        if(id_desc == 1 | id_desc == 3){
            String aux = "";
            int n= br.read();
            List<String> a = new ArrayList<>();
            while(n!= -1){
                if((char)n != ',') aux= aux + (char)n;

                else{
                    a.add(aux);
                    aux="";
                }


                n= br.read();
            }
            a.add(aux);
            file_dec= a;
        }

        else if (id_desc == 2){
            String aux= br.readLine();
            String a= "";
            while (aux != null){
                a= a +aux;
                aux= br.readLine();
            }
            file_dec= a;
        }

        else if (id_desc == 4){


        }


    }

    //FUNCIONS AUXILIARS (PRIVADES)

    //Identifica l'algorisme amb el que ha estat comprimit l'arxiu del path_og i canvia el id de descompresio (FUNCIONA)
    private static void id_algorismo(String path_og){
        String aux= path_og.substring(path_og.length()-2);
        if(aux.equals("fW")) id_desc = 1;
        else if (aux.equals("f8"))id_desc= 2;
        else if (aux.equals("fS")) id_desc = 3;
        else if (aux.equals("fG")) id_desc= 4;

    }

    private static void extension(Integer id){
        if(id== 1) extensio= "fW";
        else if(id==2)  extensio= "f8";
        else if(id==3)  extensio= "fS";
        else if(id==4)  extensio= "fG";
    }

    /*public static void main(String[] args) throws IOException {
        String a= "D:\\Mis documentos\\PAU\\UPC\\19-20 QT\\PROP\\Projecte\\proves\\trydescompressio.txt";
        id_desc= 1;
        conversio_fitxer_desc(a);
        Object o = get_file_dec();
        System.out.println(o);
    }*/



}

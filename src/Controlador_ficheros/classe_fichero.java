import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.lang.String;





public class gestor_fitxers {
    //VARIABLES DE LA CLASSE
    private  String path_dest;
    private Integer id_desc;
    private static String extensio;
    private static String nom_fitxer;


    public gestor_fitxers(){

    }

    //COMPRESSIO: BUSCAR, LLEGIR ARXIU. (PRIVADES)


    //Busca l'arxiu i retorna aquest en un objecte, i excepcio de file no trobat (FUNCIONA)
    private Object buscar_leer_archivo(String path_og, int id){
        try{
            File file = new File (path_og);
            Object fitxer= new Object();
            //per imatges
            //if(id== 4) {
                //JPEG
            //}
            //else{
                byte[] aux1 = Files.readAllBytes(file.toPath());
                fitxer = aux1;
            //}
            return fitxer;
        }

        catch (FileNotFoundException e){
            System.out.println("Fichero no encontrado.");
        } catch (IOException e) {
            System.out.print("Error inesperado de lectura o escritura");
            e.printStackTrace();
        }
        return null;
    }


    //FUNCIONS DE CREACIO I ESCRIPTURA (PUBLIQUES)

    //Crea un fitxer per escriptura i retorna el fitxer creat, treu la excepcio de que el fitxer ja existeix en el cas de que passi.
    public void c_e_fichero_descomp (String path_desti, Object encoded, Integer id) throws IOException {
        try {
            Path path = Paths.get(path_desti);
            extension(id);
            path_dest = path + nom_fitxer + extensio;
            File file = new File(path_dest);
            if(file.createNewFile()) {
                if(id== 1) {
                    Files.write(file.toPath(), (byte[]) encoded);
                }
                else if(id==2 | id==3){
                    FileWriter writer = new FileWriter(path_dest);
                    writer.write((String)encoded);
                }
                /*else if(extensio.equals(".fG")){
                    //JPEG
                }*/
            }

        }
        catch(FileAlreadyExistsException e) {
            System.out.println("El fichero ya existe.");
        }
        catch (InvalidPathException ex){
            System.out.println("El directorio destino no és valido");
        }
    }


    //Crea un fitxer per escriptura i retorna el fitxer creat, treu la excepcio de que el fitxer ja existeix en el cas de que passi.
    public void c_e_fichero_comp (String path_desti, Object aux, Integer id) throws IOException {
        try {
            Path path = Paths.get(path_desti);
            extension(id);
            path_dest = path_desti + nom_fitxer + extensio;
            File file = new File(path_dest);
            if(file.createNewFile()) {
                if(extensio.equals(".fS")|extensio.equals(".f8")) {
                    Files.write(file.toPath(), (byte[]) aux);
                }
                else if(extensio.equals(".fW")){
                    FileWriter writer = new FileWriter(path_dest);
                    for(Integer x: (List<Integer>)aux) {
                        writer.write( x );
                    }
                    writer.close();
                }

                /*else if(extensio.equals(".fG")){
                    //JPEG
                }*/
            }
        }
        catch(FileAlreadyExistsException e) {
            System.out.println("El fichero ya existe.");
        }
        catch (InvalidPathException ex){
            System.out.println("El directorio destino no és valido");
        }
    }


    //FUNCIONS DE TRANSFORMACIO DE FITXERS (PUBLIQUES)

    //Funcio d'excepcions compressió i transformacio(si es necessari)
    public Object get_f_compressio(String path_og, Integer id){
        String[] parts = path_og.split("/");
        nom_fitxer= "/" + parts[parts.length-1];
        extension(id);
        return buscar_leer_archivo(path_og, id);
    }

    //DESC: Converteix un fitxer en un objecte que entenguin els diversos algorismes (FUNCIONA; FALTA JPEG)
    public Object conversio_fitxer_desc(String path_og) throws IOException {
        id_algorismo(path_og);

        /*if (id_desc == 4){
            //JPEG
        }*/
        return buscar_leer_archivo(path_og,0);
    }

    //FUNCIONS AUXILIARS (PRIVADES)

    //Identifica l'algorisme amb el que ha estat comprimit l'arxiu del path_og i canvia el id de descompresio (FUNCIONA)
    private void id_algorismo(String path_og){
        String aux= path_og.substring(path_og.length()-2);
        if(aux.equals("f8")) id_desc = 1;
        else if (aux.equals("fS"))id_desc= 2;
        else if (aux.equals("fW")) id_desc = 3;
        else if (aux.equals("fG")) id_desc= 4;

    }

    private void extension(Integer id){
        if(id== 1) extensio= ".f8";
        else if(id==2)  extensio= ".fS";
        else if(id==3)  extensio= ".fW";
        else if(id==4)  extensio= ".fG";
    }

}

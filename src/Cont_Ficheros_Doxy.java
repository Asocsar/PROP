package Gestor_fitxeros;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.lang.String;





public class gestor_fitxers {
    //VARIABLES DE LA CLASSE
    private  String path_dest;
    private Integer id_desc;
    private  String extensio;
    private boolean c_p; //true=compressio false=descompresio


    /** \brief Creadora
        \pre Cert
        \post Es crea una instància de la classe gestor_fitxers
    */
    public gestor_fitxers(){

    }

    //COMPRESSIO: BUSCAR, LLEGIR ARXIU. (PRIVADES)


     /** \brief Buscar i llegir arxiu
        \pre Cert
        \post Retorna un objecte que representa la estructura de dades necessària depenent de l’algorisme utilitzat i de si es fa una compressió o descompressió.
     */   
    private Object buscar_leer_archivo(String path_og, int id){
        try{
            File file = new File (path_og);
            Object fitxer= new Object();
            //per imatges
            if(id== 4) {
                System.out.print("Buscar i llegir imatge");
            }

            else if(id == 3 & !c_p){
                byte[] aux1 = Files.readAllBytes(file.toPath());
                List<Integer> x= new ArrayList<>();
                for(byte a: aux1){
                    x.add((int)a);
                }
            }

            else{
                byte[] aux1 = Files.readAllBytes(file.toPath());
                fitxer = aux1;
            }
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

     /** \brief Creadora del fitxer destí
        \pre path_og és vàlid
        \post Crea un fitxer en el path_desti i hi escriu el resultat de la descompressió.
    */
    
    public void c_e_fichero_descomp (String path_og, String path_desti, Object encoded) throws IOException {
        try {
            Path path = Paths.get(path_desti);
            id_algorismo(path_og);
            path_dest = path + extensio;
            File file = new File(path_dest);
            if(file.createNewFile()) {
                if(id_desc== 3) {
                    Files.write(file.toPath(), (byte[]) encoded);
                }
                else if(id_desc==2 | id_desc==1){
                    FileWriter writer = new FileWriter(path_dest);
                    writer.write((String)encoded);
                }
                else if(id_desc==4){
                    System.out.print("Escriure imatge descomprimida.");
                }
            }

        }
        catch(FileAlreadyExistsException e) {
            System.out.println("El fichero ya existe.");
        }
        catch (InvalidPathException ex){
            System.out.println("El directorio destino no és valido");
        }
    }


     /** \brief Creadora
        \pre 0 < id < 4
        \post Crea un fitxer i hi escriu el resultat de la compressió.
    */
    public void c_e_fichero_comp (String path_desti, Object aux, Integer id) throws IOException {
        try {
            Path path = Paths.get(path_desti);
            extension(id);
            path_dest = path + extensio;
            File file = new File(path_dest);
            if(file.createNewFile()) {
                if(extensio.equals(".fS")|extensio.equals(".f8")) {
                    Files.write(file.toPath(), (byte[]) aux);
                }
                else if(extensio.equals(".fW")){
                    FileWriter writer = new FileWriter(path_dest);
                    for(Integer x: (List<Integer>)aux) {
                        writer.write(x);
                    }
                    writer.close();
                }

                else if(extensio.equals(".fG")){
                    System.out.print("Escric imatge.");
                }
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

     /**\brief Gestio fitxer a comprimir
        \pre path_og és vàlid, 0 < id < 4
        \post Retorna un objecte amb l’estructura de dades necessària per la compressió de l’arxiu demanat per l’algorisme seleccionat.
    */
    public Object get_f_compressio(String path_og, Integer id){
        c_p = true;
        /*String[] parts = path_og.split("/");
        String[] parts2 = parts[parts.length-1].split(".");
        String a = "";
        for(int i=0; i < parts2.length-2; ++i){
            a= a+ parts2[i];
        }*/
        return buscar_leer_archivo(path_og, id);
    }

    /**\brief Gestio fitxer a descomprimir
        \pre path_og és vàlid
        \post Retorna un objecte amb l’estructura de dades necessària per la descompressió de l’arxiu demanat per l’algorisme seleccionat.
    */
    public Object conversio_fitxer_desc(String path_og) throws IOException {
        id_algorismo(path_og);
        c_p = false;
        if (id_desc == 4){
            System.out.print("Retorno imatge convertida");
            return null;
        }
        else return buscar_leer_archivo(path_og,0);
    }

    /**\brief Lectura fitxer
        \pre cert
        \post S'ha guardat en un string el fitxer del path "path"
    */ 
    public String read_file(String path) throws IOException {
        try {
            Path path1 = Paths.get(path);
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String aux = "";
            String aux2;
            while ((aux2 = br.readLine()) != null) {
                aux = aux + aux2 + "/n";
            }
            return aux;
        }
        catch (InvalidPathException ex){
            System.out.println("El directorio destino no és valido");
        }
        return null;
    }

    //FUNCIONS AUXILIARS (PRIVADES)

     /**\brief Extensio en l''arxiu
        \pre path_og és vàlid 
        \post La variable id_desc i extensio de l'arxiu han estat actualitzades segons el path_og
    */
    
    private void id_algorismo(String path_og){
        String aux= path_og.substring(path_og.length()-2);
        if(aux.equals("f8")) {
            id_desc = 1;
            extensio= ".txt";
        }
        else if (aux.equals("fS")){
            id_desc= 2;
            extensio = ".txt";
        }
        else if (aux.equals("fW")){
            id_desc = 3;
            extensio = ".txt";
        }
        else if (aux.equals("fG")){
            id_desc= 4;
            extensio = ".ppm";
        }

    }
    
    /** \brief Update extensio
        \pre 0 < id < 4 
        \post La variable extensió ha estat actualitzada
    */
    
    private void extension(Integer id){
        if(id== 1) extensio= ".f8";
        else if(id==2)  extensio= ".fS";
        else if(id==3)  extensio= ".fW";
        else if(id==4)  extensio= ".fG";
    }

}

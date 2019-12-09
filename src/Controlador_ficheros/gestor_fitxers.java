package Controlador_ficheros;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.lang.String;





public class gestor_fitxers {
    //VARIABLES DE LA CLASSE
    private String nom_fitxer;
    private Integer id_desc;
    private String extensio;


    //PRE: Cert
    //POST: Crea una instancia de la classe gestor_fitxers
    public gestor_fitxers(){

    }

    public void folder(){

    }

    //COMPRESSIO:

    //PRE: Id ha de ser un id d’algorisme vàlid. El path_og ha de ser vàlid.
    //POST: Retorna un objecte amb l’estructura de dades necessària per la compressió de l’arxiu demanat per l’algorisme seleccionat.
    public byte[] get_f_compressio(String path_og, String id_a) throws IOException {
        nombre_fichero(path_og);
        ex_comp(id_a);
        return buscar_leer_archivo(path_og);
    }

    //PRE: Id ha de ser un id d’algorisme vàlid
    //POST: Crea un fitxer i hi escriu el resultat de la compressió.
    public void c_e_fichero_comp (String path_desti, Object aux) throws IOException {
        Path path_dest= Paths.get(path_desti,nom_fitxer);
        String nom_ex= path_desti.substring(0, path_desti.length()-4) + extensio;
        File file = new File(nom_ex);
        if (file.createNewFile()) {
            FileOutputStream fop= new FileOutputStream(file);
            fop.write((byte[])aux);
            fop.flush();
            fop.close();
        }
    }

    //PRE: Id ha de ser un id d’algorisme vàlid
    //POST: La variable global extensió queda actualitzada.
    private void ex_comp(String id_s){
        switch(id_s) {
            case"LZ78":
                extensio=".f8";
                break;
            case"LZSS":
                extensio=".fS";
                break;
            case"LZW":
                extensio=".fW";
                break;

            case"JPEG":
                extensio=".fG";
                break;

            default:
        }
    }


    //FI FUNCIONS COMPRESIO


    //DESCOMPRESIO:

    //PRE: El path_og ha de ser vàlid.
    //POST: Retorna un objecte amb l’estructura de dades necessària per la descompressió de l’arxiu demanat.
    public byte[] conversio_fitxer_desc(String path_og) throws IOException {
        id_ex_desc(path_og);
        nombre_fichero(path_og);
        return buscar_leer_archivo(path_og);
    }

    //PRE: path_og és un path vàlid.
    //POST: Crea un fitxer en el path_desti i hi escriu el resultat de la descompressió.
    public void c_e_fichero_descomp (String path_og, String path_desti, Object encoded) throws IOException {
        String nom_ex= nom_fitxer + extensio;
        Path path_dest= Paths.get(path_desti,nom_ex);
        System.out.println(path_dest);
        if(id_desc==4){
            System.out.print("Escriure imatge descomprimida.");
        }
        else {
            File file = new File(String.valueOf(path_dest));
            if(file.createNewFile()) {
                FileOutputStream fop = new FileOutputStream(file);
                fop.write((byte[])encoded);
                fop.flush();
                fop.close();
            }
        }

    }

    //PRE: path_og és un path vàlid
    //POST: Actualitza les variables id_desc i extensió depenent del path_og
    private void id_ex_desc(String path_og){
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

    //FI FUNCIONS DESCOMPRESIO

    //PRE:El path “path_og” és vàlid
    //POST: Retorna un objecte que representa la estructura de dades necessària depenent de l’algorisme utilitzat i de si es fa una compressió o descompressió.
    private byte[] buscar_leer_archivo(String path_og) throws IOException {
        File file = new File (path_og);
        byte[] aux1 = Files.readAllBytes(file.toPath());
        return aux1;
    }

    //AGAFA EL NOM DEL FITXER ORIGINAL
    private void nombre_fichero(String Path_og){
        Path p = Paths.get(Path_og);
        String aux = p.getFileName().toString();
        int pos = aux.lastIndexOf(".");
        nom_fitxer=aux.substring(0, pos);
    }

    //PRE: Cert
    //POST: Retorna el contingut del fitxer en el path "path" en un string
    public String read_file(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String aux = "";
        String aux2;
        while ((aux2 = br.readLine()) != null) {
            aux = aux + aux2 + "/n";
        }
        return aux;
    }

    public String compare_g(byte[] aux, String id) throws IOException {
        File file = new File("temp.txt");
        if(file.createNewFile()) {
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(aux);
            fop.flush();
            fop.close();
            //}
            byte[] encoded = Files.readAllBytes(Paths.get("temp.txt"));
            file.delete();
            return new String(encoded, Charset.defaultCharset());
        }
        return null;
    }

}
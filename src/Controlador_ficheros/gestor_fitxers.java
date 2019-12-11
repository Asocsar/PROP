package Controlador_ficheros;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.lang.String;
import java.util.List;





public class gestor_fitxers {
    //VARIABLES DE LA CLASSE
    private String nom_fitxer;
    private String extensio;
    private String Path_original;


    //PRE: Cert
    //POST: Crea una instancia de la classe gestor_fitxers
    public gestor_fitxers(){

    }
    
     public String path_if_null(){
        return Path_original;
    }

    public void folder(){

    }
    
     //COMPRESSIO CARPETES:
    public List<String> get_paths_folder(String Path_o, List<String> paths_fitxers){
        File[] files = new File(Path_o).listFiles();
        for(File a: files) {
            if (a.isDirectory()) {
                get_paths_folder(a.getPath(),paths_fitxers);
            } else paths_fitxers.add(a.getPath());
        }
        return paths_fitxers;
    }

    public void create_dir(String Path_dir) throws IOException {
        File dir = new File(Path_dir);
        dir.mkdir();
        Path path_cap = Paths.get(Path_dir,"Capçalera.txt");
        File file = new File(path_cap.toString());
        file.createNewFile();
    }

    //COMPRESSIO:

    //PRE: Id ha de ser un id d’algorisme vàlid. El path_og ha de ser vàlid.
    //POST: Retorna un objecte amb l’estructura de dades necessària per la compressió de l’arxiu demanat per l’algorisme seleccionat.
    public byte[] get_f_compressio(String path_og, String id_a) throws IOException {
        int pos= path_og.lastIndexOf('/');
        Path_original= path_og.substring(0,pos);
        nombre_fichero(path_og);
        ex_comp(id_a);
        return buscar_leer_archivo(path_og);
    }

    //PRE: Id ha de ser un id d’algorisme vàlid
    //POST: Crea un fitxer i hi escriu el resultat de la compressió.
    public void c_e_fichero_comp (String path_desti, Object aux) throws IOException {
        if (path_desti.equals("")) path_desti=Path_original;
        Path path_dest= Paths.get(path_desti,nom_fitxer);
        String nom_ex= path_dest + extensio;

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
        int pos= path_og.lastIndexOf('/');
        Path_original= path_og.substring(0,pos);
        id_ex_desc(path_og);
        nombre_fichero(path_og);
        return buscar_leer_archivo(path_og);
    }

    //PRE: path_og és un path vàlid.
    //POST: Crea un fitxer en el path_desti i hi escriu el resultat de la descompressió.
    public void c_e_fichero_descomp (String path_og, String path_desti, Object encoded) throws IOException {
        if (path_desti.equals("")) path_desti=Path_original;
        String nom_ex= nom_fitxer + extensio;
        Path path_dest= Paths.get(path_desti,nom_ex);
        File file = new File(String.valueOf(path_dest));
        if(file.createNewFile()) {
            FileOutputStream fop = new FileOutputStream(file);
            fop.write((byte[])encoded);
            fop.flush();
            fop.close();
        }
    }

    //PRE: path_og és un path vàlid
    //POST: Actualitza les variables id_desc i extensió depenent del path_og
      private void id_ex_desc(String path_og){
        String aux= path_og.substring(path_og.length()-2);
        if (aux.equals("fG")) extensio = ".ppm";
        else extensio = ".txt";

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
            byte[] encoded = Files.readAllBytes(Paths.get("temp.txt"));
            file.delete();
            return new String(encoded, Charset.defaultCharset());
        }
        return null;
    }

}

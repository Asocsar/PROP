package Gestor_fitxeros;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.lang.String;





public class gestor_fitxers {
    //VARIABLES DE LA CLASSE
    private  String path_dest;
    private Integer id;
    private Integer id_desc;
    private  String extensio;
    private boolean c_p; //true=compressio false=descompresio


    //PRE: Cert
    //POST: Crea una instancia de la classe gestor_fitxers
    public gestor_fitxers(){

    }

    public void folder(){

    }

    //COMPRESSIO: BUSCAR, LLEGIR ARXIU. (PRIVADES)


    //PRE:El path “path_og” és vàlid
    //POST: Retorna un objecte que representa la estructura de dades necessària depenent de l’algorisme utilitzat i de si es fa una compressió o descompressió.
    private byte[] buscar_leer_archivo(String path_og) throws IOException {
            if(id== 4) {
                File imgPath = new File(path_og);
                BufferedImage bufferedImage = ImageIO.read(imgPath);
                WritableRaster raster = bufferedImage .getRaster();
                DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
                return ( data.getData() );
            }
            else{
                File file = new File (path_og);
                byte[] aux1 = Files.readAllBytes(file.toPath());
                return aux1;

            }
    }


    //FUNCIONS DE CREACIO I ESCRIPTURA (PUBLIQUES)

    //PRE: path_og és un path vàlid.
    //POST: Crea un fitxer en el path_desti i hi escriu el resultat de la descompressió.
    public void c_e_fichero_descomp (String path_og, String path_desti, Object encoded) throws IOException {
            Path path = Paths.get(path_desti);
            id_algorismo(path_og);
            path_dest = path + extensio;
            if(id_desc==4){
                    System.out.print("Escriure imatge descomprimida.");
            }
            else {
                File file = new File(path_dest);
                if(file.createNewFile()) {
                    FileOutputStream fop = new FileOutputStream(file);
                    fop.write((byte[])encoded);
                    fop.flush();
                    fop.close();
                }
            }

    }


    //PRE: Id ha de ser un id d’algorisme vàlid
    //POST: Crea un fitxer i hi escriu el resultat de la compressió.
    public void c_e_fichero_comp (String path_desti, Object aux, String id) throws IOException {
        Path path = Paths.get(path_desti);
        extension(id);
        path_dest = path + extensio;
        File file = new File(path_dest);
        if (file.createNewFile()) {
            FileOutputStream fop= new FileOutputStream(file);
            fop.write((byte[])aux);
            fop.flush();
            fop.close();
        }
    }

    //FUNCIONS DE TRANSFORMACIO DE FITXERS (PUBLIQUES)

    //PRE: Id ha de ser un id d’algorisme vàlid. El path_og ha de ser vàlid.
    //POST: Retorna un objecte amb l’estructura de dades necessària per la compressió de l’arxiu demanat per l’algorisme seleccionat.
    public byte[] get_f_compressio(String path_og) throws IOException {
        c_p = true;
        //ARREGLAR EXTENSIO
        extension(path_og);
        return buscar_leer_archivo(path_og);
    }

    //PRE: El path_og ha de ser vàlid.
    //POST: Retorna un objecte amb l’estructura de dades necessària per la descompressió de l’arxiu demanat.
    public Object conversio_fitxer_desc(String path_og) throws IOException {
        id_algorismo(path_og);
        c_p = false;
        id=0;
        return buscar_leer_archivo(path_og);
    }

    //PRE: Cert
    //POST: Retorna el contingut del fitxer en el path "path" en un string
    public String read_file(String path) throws IOException {
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

    //FUNCIONS AUXILIARS (PRIVADES)

    //PRE: path_og és un path vàlid
    //POST: Actualitza les variables id_desc i extensió depenent del path_og
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

    //PRE: Id ha de ser un id d’algorisme vàlid
    //POST: La variable global extensió queda actualitzada.
    private void extension(String id_s){
        if(id_s.equals("LZ78")) {
            extensio= ".f8";
            id= 1;
        }
        else if(id.equals("LZSS")) {
            extensio= ".fS";
            id=2;
        }
        else if(id.equals("LZW")) {
            extensio= ".fW";
            id=3;
        }
        else if(id.equals("JEPG")){
            extensio= ".fG";
            id=4;
        }
    }

}

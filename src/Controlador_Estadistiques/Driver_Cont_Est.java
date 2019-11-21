package Controlador_Estadistiques;
import Controlador_Estadistiques.Cont_Est;
import Controlador_Estadistiques.Stats_Stub;
import Estadístiques.Estadistiques;

import java.io.*;

public class Driver_Cont_Est {

    public void main() throws IOException {
        Stats_Stub E = new Stats_Stub();

        System.out.println("Actualitzem el fitxer d'estadistiques amb les estadistiques de l'última compressió");

        E.Stats_Update();

        System.out.println(E.timeJPEG + E.GratioJPEG + E.GtimeJPEG);

        System.out.println("Obtenim les estadistiques de l'arxiu" )

        GetStats();

        System.out.println(Estadístiques.timeJPEG + Estadístiques.GratioJPEG + Estadístiques.GtimeJPEG);

    }
}
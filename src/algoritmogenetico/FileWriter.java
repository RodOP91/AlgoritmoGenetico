/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico;

import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author benji
 */
public class FileWriter {

    private static StringBuilder sb = new StringBuilder();

    public static void agregarContenido(String cadena) {
        sb.append(cadena);
        sb.append('\n');
    }

    public static void guardarArchivo(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new File(nombreArchivo))) {
            writer.print(sb.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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

    private StringBuilder sb = new StringBuilder();
    private String nombreArchivo;
    
    public FileWriter(String nombreArchivo){
        this.nombreArchivo = nombreArchivo;
    }
    public void agregarContenido(String cadena) {
        sb.append(cadena);
        sb.append('\n');
    }

    public void guardarArchivo() {
        try (PrintWriter writer = new PrintWriter(new File(nombreArchivo))) {
            writer.print(sb.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * Proyecto creado con propósitos educativos en la Facultad de  
 *  Estadística e Informática de la Universidad Veracruzana.
 * Código libre.
 */
package algoritmogenetico;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author J. Rodrigo Ordóñez Pacheco
 */
public class Individuo {
    private ArrayList<Double> valores = new ArrayList(20);
    private boolean aprobado = true;
    private double evaluacion; 

    public ArrayList<Double> getValores() {
        return valores;
    }

    public void setValores(ArrayList<Double> valores) {
        this.valores = valores;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }
    
    
}

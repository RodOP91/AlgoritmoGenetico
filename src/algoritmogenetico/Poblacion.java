/*
 * Proyecto creado con propósitos educativos en la Facultad de  
 *  Estadística e Informática de la Universidad Veracruzana.
 * Código libre.
 */
package algoritmogenetico;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author J. Rodrigo Ordóñez Pacheco
 */
public class Poblacion {
    private List<Individuo> individuos = new ArrayList(200);

    public Poblacion(){
        for(int x = 0; x<200; x++){
            Individuo ind = new Individuo();
            this.individuos.add(ind);
        }
    }
    public ArrayList<Individuo> getPoblacion() {
        return (ArrayList<Individuo>) individuos;
    }

    public void setPoblacion(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }
    
    public void ordenarPoblacion(){
        individuos.sort(Comparator.comparingDouble(Individuo::getEvaluacion));
    }
}



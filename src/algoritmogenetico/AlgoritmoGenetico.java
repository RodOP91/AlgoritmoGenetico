/*
 */
package algoritmogenetico;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import algoritmogenetico.Formula;
import java.util.List;

/**
 *
 * @author J. Rodrigo Ordóñez Pacheco
 */
public class AlgoritmoGenetico {

    private static final int POBLACION = 10;
    private static final int N = 20;
    private static final int POSICION_CRUZA = 10;
    private static final double PROBAB_CRUZA = 0.8;
    private static Formula objeto_evaluador = new Formula();
    private static Restricciones objeto_restricciones = new Restricciones();
    private static Poblacion poblacion = new Poblacion();
    //private static Poblacion padres = new Poblacion();
    
    private static List<Individuo> padresSelect;
    private static List<Individuo> hijos;

    private static void generarPrimeraGeneracion() {
        for (int x = 0; x < POBLACION; x++) {
            ArrayList<Double> valores = new ArrayList();
            for (int y = 0; y < N; y++) {
                double temp = Math.random() * 10;
                //System.out.println(temp);
                valores.add(temp);
            }
            poblacion.getPoblacion().get(x).setValores(valores);
            for (int z = 0; z < N; z++) {
                //System.out.println(poblacion.getPoblacion().get(x).getValores().get(z));

            }
            //System.out.println("FIN");
        }
    }

    private static void evaluarPoblacion() {
        for (int x = 0; x < POBLACION; x++) {
            //Individuo individuo = poblacion.getPoblacion().get(x);
            int resultadoEvaluacion = evaluacionIndividuo(poblacion.getPoblacion().get(x));
            if (resultadoEvaluacion == 0) {
                poblacion.getPoblacion().get(x).setEvaluacion(2000000);
                poblacion.getPoblacion().get(x).setAprobado(false);
            } else if (resultadoEvaluacion == 1) {
                poblacion.getPoblacion().get(x).setEvaluacion(1000000);
                poblacion.getPoblacion().get(x).setAprobado(false);
            } else if (resultadoEvaluacion == 2) {
                asignarAptitud(poblacion.getPoblacion().get(x));
                System.out.println( x + " fue aprobado");
            } else {
                System.out.println("Error en asignación de aptitud");
            }
        }
    }

    private static int evaluacionIndividuo(Individuo individuo) {
        int respuesta = 0;
        if (objeto_restricciones.primeraRestriccion(individuo)) {
            respuesta++;
            if (objeto_restricciones.segundaRestriccion(individuo)) {
                respuesta++;
            }
        }
        return respuesta;
    }

    private static void asignarAptitud(Individuo individuo) {
        double primeraSuma = objeto_evaluador.primerTermino(individuo.getValores());
        double multipOrdenada = objeto_evaluador.segundoTermino(individuo.getValores());
        double raiz = objeto_evaluador.tercerTermino(individuo.getValores());
        double resta = primeraSuma - (2) * multipOrdenada;
        double resultado = objeto_evaluador.divisionYValorAbsoluto(resta, raiz);
        individuo.setEvaluacion(resultado);
    }
    /*
    private static void seleccionDePadres() {
        double promedio = 0.0;
        int temp = 0;
        for (int x = 0; x < POBLACION; x++) {
            if (poblacion.getPoblacion().get(x).isAprobado()) {
                padres.getPoblacion().add(poblacion.getPoblacion().get(x));

            }
        }

        for (int x = 0; x < padres.getPoblacion().size(); x++) {
            promedio += padres.getPoblacion().get(x).getEvaluacion();
        }
        promedio = promedio / padres.getPoblacion().size();

        for (int x = 0; x < padres.getPoblacion().size(); x++) {
            if (padres.getPoblacion().get(x).getEvaluacion() >= promedio) {
                temp++;
                System.out.println(temp);
            }
        }
        System.out.println("Numero de padres: " + temp);

    }*/

    private static List<Individuo> seleccionPadresRuleta() {
        padresSelect = new ArrayList<>();
        double valorEsperado = 0.0;
        for (int x = 0; x < poblacion.getPoblacion().size(); x++) {
            double aptitud = poblacion.getPoblacion().get(x).getEvaluacion();
            valorEsperado += aptitud;
        }
        for (int x = 0; x < POBLACION; x++) {
            double numeroGenerado = Math.random() * valorEsperado;
            double sumaNumeros = 0.0;
            for (int y = 0; y < poblacion.getPoblacion().size(); y++) {
                sumaNumeros += poblacion.getPoblacion().get(y).getEvaluacion();
                if (sumaNumeros > numeroGenerado) {
                    padresSelect.add(poblacion.getPoblacion().get(y));
                }
            }
        }
        return padresSelect;
    }

    private static List<Individuo> cruzar(List<Individuo> padresSelect) {
        hijos = new ArrayList<>();
        for (int x = 0; x < padresSelect.size(); x = x + 2) {
            ArrayList<Double> valoresPadre1 = padresSelect.get(x).getValores();
            ArrayList<Double> valoresPadre2 = padresSelect.get(x + 1).getValores();
            Individuo hijo1 = new Individuo();
            Individuo hijo2 = new Individuo();
            for (int y = 0; y < 20; y++) {
                if (y >= POSICION_CRUZA) {
                    hijo1.agregarValor(valoresPadre2.get(y) * PROBAB_CRUZA);
                    hijo2.agregarValor(valoresPadre1.get(y)* PROBAB_CRUZA);
                } else {
                    hijo1.agregarValor(valoresPadre1.get(y));
                    hijo2.agregarValor(valoresPadre2.get(y));
                }
            }
            hijos.add(hijo1);
            hijos.add(hijo2);
        }
        return hijos;
    }
    
    private static double media(List<Individuo> poblacionAux){
        double promedio = 0.0;
        for(int x = 0; x< poblacionAux.size(); x++){
            promedio += poblacionAux.get(x).getEvaluacion();
        }
        promedio /= poblacionAux.size();
        return promedio;
    }
    
    private static double mediana(List<Individuo>poblacionAux){
        return poblacionAux.get(poblacionAux.size()/2).getEvaluacion();
    }
    
    private static double varianza(List<Individuo>poblacionAux){
        double varianza = 0.0;
        double promedio = media(poblacionAux);
        for(int x = 0; x < poblacionAux.size(); x++){
            double rango;
            rango = Math.pow(poblacionAux.get(x).getEvaluacion()-promedio, 2);
            varianza += rango;
        }
        varianza /= poblacionAux.size();
        return varianza;
    }
    
    private static double desviacionEstandar(List<Individuo>poblacionAux){
        return Math.sqrt(varianza(poblacionAux));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nombreArchivo = "Primerarchivo";
        generarPrimeraGeneracion();
        evaluarPoblacion();
        poblacion.ordenarPoblacion();
        int contador = 0;
        for (int x = 0; x < POBLACION; x++) {
            //System.out.println(x);
            if (poblacion.getPoblacion().get(x).isAprobado()) {
                contador++;
            }
        }
        System.out.println("Numero de aprobados: " + contador);
        padresSelect = seleccionPadresRuleta();
        System.out.println("Numero de padres: " + padresSelect.size());
        
        
        for(int x=0; x<POBLACION; x++){
            System.out.println("Evaluación: " + poblacion.getPoblacion().get(x).getEvaluacion());
        }
        //FileWriter.guardarArchivo(nombreArchivo);
    }

}

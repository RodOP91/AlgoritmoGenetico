/*
 */
package algoritmogenetico;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import algoritmogenetico.Formula;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author J. Rodrigo Ordóñez Pacheco
 */
public class AlgoritmoGenetico {

    private static final int POBLACION = 20;
    private static final int N = 20;
    private static final int NUM_INDIVIDUOS = 200;
    private static final int POSICION_CRUZA = 10;
    private static final double PROBAB_CRUZA = 0.8;
    private static Formula objeto_evaluador = new Formula();
    private static Restricciones objeto_restricciones = new Restricciones();
    private static Poblacion poblacion = new Poblacion();
    private static Poblacion padres = new Poblacion();
    private static int hijosEvaluados = 0;

    private static void generarPrimeraGeneracion() {
        for (int x = 0; x < NUM_INDIVIDUOS; x++) {
            ArrayList<Double> valores = new ArrayList();
            for (int y = 0; y < N; y++) {
                double temp = Math.random() * 10;
                //System.out.println(temp);
                valores.add(temp);
            }
            poblacion.getPoblacion().get(x).setValores(valores);
            //System.out.println("FIN");
        }
    }

    private static void evaluarPoblacion() {
        for (int x = 0; x < NUM_INDIVIDUOS; x++) {
            Individuo individuo = poblacion.getPoblacion().get(x);
            int resultadoEvaluacion = evaluacionIndividuo(individuo);
            if (resultadoEvaluacion == 0) {
                individuo.setEvaluacion(2000000);
            } else if (resultadoEvaluacion == 1) {
                individuo.setEvaluacion(1000000);
            } else if (resultadoEvaluacion == 2) {
                asignarAptitud(individuo);
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

    private static List<Individuo> seleccionPadresRuleta() {
        List<Individuo> padresSelect = new ArrayList<>();
        double valorEsperado = 0.0;
        for (int x = 0; x < poblacion.getPoblacion().size(); x++) {
            double aptitud = poblacion.getPoblacion().get(x).getEvaluacion();
            valorEsperado += aptitud;
        }
        for (int x = 0; x < 51; x++) {
            double numeroGenerado = Math.random() * valorEsperado;
            double sumaNumeros = 0.0;
            for (int y = 0; y < poblacion.getPoblacion().size(); y++) {
                sumaNumeros += poblacion.getPoblacion().get(y).getEvaluacion();
                if (Math.abs(sumaNumeros) > Math.abs(numeroGenerado)) {
                    padresSelect.add(poblacion.getPoblacion().get(y));
                    break;
                }
            }
        }
        return padresSelect;
    }

    private static List<Individuo> cruzar(List<Individuo> padres) {
        List<Individuo> hijos = new ArrayList<>();
        for (int x = 0; x < 50; x = x + 2) {
            ArrayList<Double> valoresPadre1 = padres.get(x).getValores();
            ArrayList<Double> valoresPadre2 = padres.get(x + 1).getValores();
            Individuo hijo = new Individuo();
            Individuo hijo2 = new Individuo();
            for (int y = 0; y < 20; y++) {
                if (y >= POSICION_CRUZA) {
                    hijo.agregarValor(valoresPadre2.get(y) * PROBAB_CRUZA);
                    hijo2.agregarValor(valoresPadre1.get(y) * PROBAB_CRUZA);
                } else {
                    hijo.agregarValor(valoresPadre1.get(y));
                    hijo2.agregarValor(valoresPadre2.get(y));
                }
            }
            hijos.add(hijo);
            hijos.add(hijo2);
        }
        return hijos;
    }

    private static List<Individuo> mutarHijos(List<Individuo> hijos) {
        List<Individuo> hijosMutados = new ArrayList<>();
        Random rand = new Random();
        int num_aleatorio = rand.nextInt(20);
        for (int x = 0; x < hijos.size(); x++) {
            ArrayList<Double> valores_mutados = hijos.get(x).getValores();
            valores_mutados.set(num_aleatorio, (rand.nextDouble() * (11 - 0) + 0));
            hijos.get(x).setValores(valores_mutados);
            hijosMutados.add(hijos.get(x));
        }
        return hijosMutados;
    }

    private static void seleccionNatural(List<Individuo> hijosMutados) {
        for (int x = 0; x < hijosMutados.size(); x++) {
            Individuo individuo = hijosMutados.get(x);
            int resultadoEvaluacion = evaluacionIndividuo(individuo);
            if (resultadoEvaluacion == 0) {
                individuo.setEvaluacion(2000000);
            } else if (resultadoEvaluacion == 1) {
                individuo.setEvaluacion(1000000);
            } else if (resultadoEvaluacion == 2) {
                asignarAptitud(individuo);
            } else {
                System.out.println("Error en asignación de aptitud");
            }
        }
        int numeroHijos = poblacion.getPoblacion().size() - hijosMutados.size();
        int contador = 0;
        for (int x = poblacion.getPoblacion().size() - 1; x >= numeroHijos; x--) {
            if (hijosMutados.get(contador).getEvaluacion() < 1000000) {
                poblacion.getPoblacion().set(x, hijosMutados.get(contador));
                hijosEvaluados++;
            }
            contador++;
        }
    }

    private static double media(List<Individuo> poblacionAux) {
        double promedio = 0.0;
        for (int x = 0; x < poblacionAux.size(); x++) {
            promedio += poblacionAux.get(x).getEvaluacion();
        }
        promedio /= poblacionAux.size();
        return promedio;
    }

    private static double mediana(List<Individuo> poblacionAux) {
        return poblacionAux.get(poblacionAux.size() / 2).getEvaluacion();
    }

    private static double varianza(List<Individuo> poblacionAux) {
        double varianza = 0.0;
        double promedio = media(poblacionAux);
        for (int x = 0; x < poblacionAux.size(); x++) {
            double rango;
            rango = Math.pow(poblacionAux.get(x).getEvaluacion() - promedio, 2);
            varianza += rango;
        }
        varianza /= poblacionAux.size();
        return varianza;
    }

    private static double desviacionEstandar(List<Individuo> poblacionAux) {
        return Math.sqrt(varianza(poblacionAux));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nombreArchivo = "evaluaciones/" + args[0];
        String nombreEstadisticas = "estadisticas/" + args[0];
        FileWriter escritorArchivo = new FileWriter(nombreArchivo);
        FileWriter escritorEstadisticas = new FileWriter(nombreEstadisticas);
        int generaciones = 1;
        int evaluaciones = 50;
        generarPrimeraGeneracion();
        evaluarPoblacion();
        poblacion.ordenarPoblacion();
        Collections.reverse(poblacion.getPoblacion());
        double mejor = poblacion.getPoblacion().get(0).getEvaluacion();
        double peor = poblacion.getPoblacion().get(poblacion.getPoblacion().size() - 1).getEvaluacion();
        System.out.println("Mejor por generación");
        System.out.println("Generación: " + generaciones);
        System.out.println("Mejor: " + mejor);
        escritorArchivo.agregarContenido("Mejor por generación");
        escritorArchivo.agregarContenido("Generación: " + generaciones);
        escritorArchivo.agregarContenido("Mejor: " + mejor);
        escritorArchivo.agregarContenido("Peor: " + peor);
        escritorArchivo.agregarContenido("");
        escritorEstadisticas.agregarContenido("Mejor por generación");
        escritorEstadisticas.agregarContenido("Media: " + media(poblacion.getPoblacion()));
        escritorEstadisticas.agregarContenido("Mediana: " + mediana(poblacion.getPoblacion()));
        escritorEstadisticas.agregarContenido("DE: " + desviacionEstandar(poblacion.getPoblacion()));
        escritorEstadisticas.agregarContenido("");
        do {
            hijosEvaluados = 0;
            generaciones++;
            List<Individuo> padres = seleccionPadresRuleta();
            List<Individuo> hijos = cruzar(padres);
            List<Individuo> hijosMutados = mutarHijos(hijos);
            seleccionNatural(hijosMutados);
            evaluarPoblacion();
            poblacion.ordenarPoblacion();
            Collections.reverse(poblacion.getPoblacion());
            mejor = poblacion.getPoblacion().get(0).getEvaluacion();
            System.out.println("Mejor por generación");
            System.out.println("Generación: " + generaciones);
            System.out.println("Mejor: " + mejor);
            evaluaciones += hijosMutados.size();
            escritorArchivo.agregarContenido("Mejor por generación");
            escritorArchivo.agregarContenido("Generación: " + generaciones);
            escritorArchivo.agregarContenido("Mejor: " + mejor);
            escritorArchivo.agregarContenido("Peor: " + peor);
            escritorArchivo.agregarContenido("");
            escritorEstadisticas.agregarContenido("Mejor por generación");
            escritorEstadisticas.agregarContenido("Media: " + media(poblacion.getPoblacion()));
            escritorEstadisticas.agregarContenido("Mediana: " + mediana(poblacion.getPoblacion()));
            escritorEstadisticas.agregarContenido("DE: " + desviacionEstandar(poblacion.getPoblacion()));
            escritorEstadisticas.agregarContenido("");

        } while (mejor != 0 && evaluaciones < 200);
        
        escritorArchivo.guardarArchivo();
        escritorEstadisticas.guardarArchivo();
    }

}

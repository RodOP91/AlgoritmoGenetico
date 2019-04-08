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
    private static final int POBLACION = 20;
    private static final int N = 20;
    private static Formula objeto_evaluador = new Formula();
    private static Restricciones objeto_restricciones = new Restricciones();
    private static Poblacion poblacion = new Poblacion();
    private static Poblacion padres = new Poblacion();
    
    
    
    private static void generarPrimeraGeneracion(){
        for(int x = 0; x<POBLACION ; x++){
            ArrayList<Double> valores = new ArrayList();
            for(int y= 0; y<N; y++){                
                int temp = ThreadLocalRandom.current().nextInt(0, 11);
                //System.out.println(temp);
                valores.add((double) temp);              
            }
            poblacion.getPoblacion().get(x).setValores(valores);
            for(int z=0; z<N ; z++){
                //System.out.println(poblacion.getPoblacion().get(x).getValores().get(z));
                
            }
            //System.out.println("FIN");
        }
    }
    
    private static void evaluarPoblacion(){
        for(int x =0; x<POBLACION ; x++){
            Individuo individuo = poblacion.getPoblacion().get(x);
            int resultadoEvaluacion = evaluacionIndividuo(individuo);
            if(resultadoEvaluacion == 0){
                individuo.setEvaluacion(2000000);
            } else if ( resultadoEvaluacion == 1){
                individuo.setEvaluacion(2000000);
            } else if( resultadoEvaluacion == 2){
                asignarAptitud(individuo);
            }else{
                System.out.println("Error en asignación de aptitud");
            }
        }
    }
    

    private static int evaluacionIndividuo(Individuo individuo){
        int respuesta = 0;
        if(objeto_restricciones.primeraRestriccion(individuo)){
            respuesta++;
            if(objeto_restricciones.segundaRestriccion(individuo)){
                respuesta++;
            }
        }
        return respuesta;
    }
    
    private static void asignarAptitud(Individuo individuo){
        double primeraSuma = objeto_evaluador.primerTermino(individuo.getValores());
        double multipOrdenada = objeto_evaluador.segundoTermino(individuo.getValores());
        double raiz = objeto_evaluador.tercerTermino(individuo.getValores());
        double resta = primeraSuma - (2)*multipOrdenada;
        double resultado = objeto_evaluador.divisionYValorAbsoluto(resta, raiz);
        individuo.setEvaluacion(resultado);
    }
    
    private static void seleccionDePadres(){
        double promedio =0.0;
        int temp =0;
        for(int x=0; x<POBLACION ; x++){
            if(poblacion.getPoblacion().get(x).isAprobado()){
                padres.getPoblacion().add(poblacion.getPoblacion().get(x));
               
            }
        }
        
        for(int x=0; x<padres.getPoblacion().size(); x++){         
             promedio += padres.getPoblacion().get(x).getEvaluacion();
        }
        promedio = promedio / padres.getPoblacion().size();
        
        for(int x=0; x<padres.getPoblacion().size(); x++){
             if(padres.getPoblacion().get(x).getEvaluacion()>=promedio){
                 temp++;
                 System.out.println(temp);
             }
        }
        System.out.println("Numero de padres: " + temp);
                
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       generarPrimeraGeneracion();
       evaluarPoblacion();
       int contador=0;
       for(int x =0; x<POBLACION; x++){
           //System.out.println(x);
           if(poblacion.getPoblacion().get(x).isAprobado()){
               contador++;
           }
       }
       System.out.println("Numero de aprobados: " + contador);
       seleccionDePadres();
       
       
    }
    
    
}

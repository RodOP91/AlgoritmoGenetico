/*
 */
package algoritmogenetico;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import algoritmogenetico.Formula;

/**
 *
 * @author J. Rodrigo Ordóñez Pacheco
 */
public class AlgoritmoGenetico {
    public static final int POBLACION = 20;
    public static final int N = 20;
    private static Formula objeto_evaluador = new Formula();
    private static Restricciones objeto_restricciones = new Restricciones();
    public static Poblacion poblacion = new Poblacion();
    public static Poblacion padres = new Poblacion();
    
    
    
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
            double resultado_primer_termino = objeto_evaluador.primerTermino(poblacion.getPoblacion().get(x).getValores());
            //System.out.println(resultado_primer_termino);
            double resultado_segundo_termino = objeto_evaluador.segundoTermino(poblacion.getPoblacion().get(x).getValores());
            //System.out.println(resultado_segundo_termino);
            double resultado_resta= objeto_evaluador.restaPrimeroMenosSegundo(resultado_primer_termino, resultado_segundo_termino);
            //System.out.println(resultado_resta);
            double resultado_tercer_termino = objeto_evaluador.tercerTermino(poblacion.getPoblacion().get(x).getValores());
            //System.out.println(resultado_tercer_termino);
            double resultado_general = objeto_evaluador.divisionYValorAbsoluto(resultado_resta, resultado_tercer_termino);
            System.out.println( x + "--> " +resultado_general);
            
            poblacion.getPoblacion().get(x).setEvaluacion(resultado_general);
        }
    }
    
    private static void evaluarRestriccionesEnPoblacion(){
        for(int x = 0; x<POBLACION ; x++){
            
            boolean aprobado1 = objeto_restricciones.primeraRestriccion(poblacion.getPoblacion().get(x));
            poblacion.getPoblacion().get(x).setAprobado(aprobado1);
            if(aprobado1 == true){
                boolean aprobado2 = objeto_restricciones.segundaRestriccion(poblacion.getPoblacion().get(x));
                poblacion.getPoblacion().get(x).setAprobado(aprobado2);
                System.out.println("-->" + x);
            }else{
                
            }
            
            
        }
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
       evaluarRestriccionesEnPoblacion();
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

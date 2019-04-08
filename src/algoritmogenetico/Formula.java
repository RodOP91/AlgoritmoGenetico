/*
 * Proyecto creado con propósitos educativos en la Facultad de  
 *  Estadística e Informática de la Universidad Veracruzana.
 * Código libre.
 */
package algoritmogenetico;

import java.util.ArrayList;
import java.math.*;

/**
 *
 * @author J. Rodrigo Ordóñez Pacheco
 */
public class Formula {
    
    /**
     * La suma total del coseno elevado a la 4ta potencia de 'n' valores.
     * 
     * @param valores lista de los valores del individuo a evaluar
     * @return resultado_primer_termino resultado de la evaluación del primer término de los valores 
     * otorgados en la lista 'valores' 
     */
    public double primerTermino(ArrayList<Double> valores){
        double resultado_primer_termino=0.0;
        for(int x =0; x<20; x++){
            double res = Math.pow(valores.get(x), 4);
            resultado_primer_termino += res;
        }
        return resultado_primer_termino;
    }
    /**
     * Dos veces el productorio del coseno elevado al cuadrado de 'n' valores
     * 
     * @param valores lista de los valores del individuo a evaluar
     * @return resultado_segundo_termino resultado de la evaluación del segundo término de los valores 
     */
    public double segundoTermino(ArrayList<Double> valores){
        double resultado_segundo_termino=1.0;
        for(int x = 0; x<20; x++){
            double res = 2*(Math.pow(Math.cos(valores.get(x)), 2));
            resultado_segundo_termino *= res;
        }
        return resultado_segundo_termino;
    }
    
    /**
     * Resultado del primero término menos el resultado del segundo término
     * 
     * @param primero resultado de la evaluación del primer término
     * @param segundo resultado de la evaluación del segundo término
     * @return resultado_resta
     */
    public double restaPrimeroMenosSegundo(Double primero, Double segundo){
        double resultado_resta= primero - segundo;
        return resultado_resta;
    }
    
    /**
     * La raíz cuadrada de la suma total del producto de 'i' por 'n' al cuadrado
     * de 'n' valores.
     * 
     * @param valores lista de los valores del individuo a evaluar.
     * @return resultado_tercer_termino
     */
    public double tercerTermino(ArrayList<Double> valores){
        double resultado_tercer_termino=0.0;
        for(int x = 0; x<20 ; x++){
            double res = x*(Math.pow(valores.get(x), 2));
            resultado_tercer_termino += res;
            resultado_tercer_termino = Math.sqrt(resultado_tercer_termino);
        }
        
        return resultado_tercer_termino;
    }
    /**
     * Valor negativo del valor absoluto del resultado de la resta entre el resultado
     * del tercer término
     * 
     * @param resultado_resta
     * @param resultado_tercer_termino
     * @return resultado_general
     */
    public double divisionYValorAbsoluto(Double resultado_resta, Double resultado_tercer_termino){
        double resultado_general;
        
        resultado_general =  -1 * (Math.abs(resultado_resta/resultado_tercer_termino));
        
        return resultado_general;
    }
    
}

/*
 * Proyecto creado con propósitos educativos en la Facultad de  
 *  Estadística e Informática de la Universidad Veracruzana.
 * Código libre.
 */
package algoritmogenetico;

/**
 *
 * @author J. Rodrigo Ordóñez Pacheco
 */
public class Restricciones {
    
    /**
     * Método que evalúa la primera restricción --> g1(x)
     * 
     * @param individuo de la población a ser evaluado por la restriccion
     * @return ind objeto evaluado por la restriccion
     */
    public boolean primeraRestriccion(Individuo individuo){
        Individuo ind = individuo;
        double res=1.0;
        boolean aprobado= true;
        
        for(int x = 0; x< 20; x++){
            res *= ind.getValores().get(x);
        }
        double restriccion = 0.75 - (res);
        System.out.println("Primera rest: " + restriccion);
        if(restriccion <=0){
            
        }else{
            aprobado = false;
        }
        
        return aprobado;
    }
    
    /**
     * Método que evalúa la segunda restricción --> g2(x)
     * 
     * @param individuo de la población a ser evaluado por la restriccion
     * @return objeto evaluado por la restriccion
     */
    public boolean segundaRestriccion(Individuo individuo){
        Individuo ind = individuo;
        boolean aprobado = true;
        double res =0.0;
        
        for(int x=0; x<20; x++){
            res += ind.getValores().get(x);
        }
        
        double restriccion = (res)-(7.5 * 20);
        System.out.println("Segunda rest: " + restriccion);
        if(restriccion<= 0){
            
        }else{
            aprobado=false;
        }
        
        return aprobado;
    }
}

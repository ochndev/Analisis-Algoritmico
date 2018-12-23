/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amc_practica1;

import amc_practica1.Grafo.Arista;
import amc_practica1.Grafo.Grafo;
import java.util.ArrayList;

/**
 *
 * @author Oscar Chaves Navarro <ochndev at github.com>
 */
public class Algoritmos {
    
    private ArrayList<Punto> elementos;
    
    public Algoritmos(ArrayList<Punto> elementos){
        
        this.elementos = elementos;       
        
    }
    
    public void setElementos(ArrayList<Punto> elementos){
        this.elementos = elementos;
    }
    
    public Triangulo metodoExhaustivo (ArrayList<Punto> vector){
               
        double distancia_actual, distancia_minima = 100000;
        Triangulo tri =  new Triangulo(new Punto(0,0),new Punto(0,0), new Punto(0,0));
        
        for (int i = 0; i < vector.size(); i++) {            
            for (int j = 0; j < vector.size(); j++) {
                for (int k = 0; k < vector.size(); k++) {
                    
                    if((i != j) && (j != k) && (i != k)){
                    
                        distancia_actual = distanciaTresPuntos(vector.get(i),vector.get(j),vector.get(k));
                    
                        if(distancia_actual < distancia_minima){
                            distancia_minima = distancia_actual;
                            tri.setA(vector.get(i));
                            tri.setB(vector.get(j));
                            tri.setC(vector.get(k));
                        
                        }
                    }
                }
            }
        }
        
        tri.setDistancia(distancia_minima);
        return tri;
    } 
    
    private double distanciaTresPuntos(Punto a, Punto b, Punto c){
        
        double distancia =  Math.sqrt((Math.pow(Math.abs(a.getX()-b.getX()),2)) + (Math.pow(Math.abs(a.getY()-b.getY()),2)))+
                            Math.sqrt((Math.pow(Math.abs(b.getX()-c.getX()),2)) + (Math.pow(Math.abs(b.getY()-c.getY()),2)))+
                            Math.sqrt((Math.pow(Math.abs(a.getX()-c.getX()),2)) + (Math.pow(Math.abs(a.getY()-c.getY()),2)));
        return distancia;
    }
    
    /**
     * 
     * Calcula la desviacion típica, devuelve 1 si se debe ordenar por X y 2 si se
     * debe ordenar por Y;
     * 
     * @param elem
     * @return 
     */
    
    private int DesviacionTipica(ArrayList<Punto> elem){
        
        double desviacion_y = 0, desviacion_x = 0;
        double media_y = 0, media_x = 0;
        
        for(int i = 0 ; i < elem.size() ; i++)
        {
            media_x += elem.get(i).getX();
            media_y += elem.get(i).getY();
        }
        
        media_x = media_x / elem.size();
        media_y = media_y / elem.size();
        
        for(int j = 0; j < elem.size() ; j++)
        {
            /*POR HACER TODAVIA - FORMULA DE LA DESVIACION TIPICA*/
        }
        
        if(desviacion_y > desviacion_x)
        {
            return 1;
        }else{
            return 2;
        }
        
    }
    
    
    public Triangulo metodoDyV(ArrayList<Punto> V){
        
        int fin, inicio;
        Triangulo resultado_final = new Triangulo();
        
        inicio = 0;
        fin = V.size();
        
        heap_sort(V);
        

        resultado_final = metodoDyV(V , 0 , V.get(fin-1).getX()+1);
        

        return resultado_final;
        
    }
    
    public Triangulo metodoDyV(ArrayList<Punto> V, double inicio, double fin){
        
        //pRmin -> perimetro minimo derecho
        //pLmin -> perimetro minimo izquierdo
        //pLRmin -> perimetro minimo izquierdo y derecho
        //distancia_minima -> perimetro minimo izquierdo y derecho partido entre dos
        double mitad;
        Triangulo pRmin, pLmin, pLRmin;
        double distancia_minima;
        Triangulo minimo = new Triangulo();
        
        if(V.size() > 6)
        {
            mitad = ((fin-inicio)/2)+inicio;
            //////////////////////////////////////////////////////////////
            pLmin = metodoDyV(Particion(V , inicio , mitad), inicio , mitad);
            ///////////////////////////////////////////////////////////////
            pRmin = metodoDyV(Particion(V, mitad , fin), mitad, fin);
            ///////////////////////////////////////////////////////////////
            if(pLmin.getDistancia()<pRmin.getDistancia())
                pLRmin = pLmin;
            else
                pLRmin = pRmin;        
            ///////////////////////////////////////////////////////////////
            distancia_minima = pLRmin.getDistancia() / 2;
            minimo = metodoExhaustivo( Particion ( V , mitad - distancia_minima , mitad + distancia_minima)); 
            
            if(pLRmin.getDistancia() < minimo.getDistancia())
                return pLRmin;
            else
                return minimo;
            
        }
        else if(V.size() <= 6 && V.size() >= 3){
            return metodoExhaustivo(V);
        }
        else{
            minimo.setDistancia(1000000);
            return minimo;
        }
        
    }
    
    /**
     * 
     * La funcion particion, extrae una particion de un vector desde inicio hasta fin
     * Inicio y Fin son valores minimos y maximos de X
     * 
     * @param V
     * @param inicio
     * @param fin
     * @return 
     */
    
    public ArrayList<Punto> Particion (ArrayList<Punto> V, double inicio, double fin){
        
        ArrayList<Punto> aux = new ArrayList<>();
        
        for(int i = 0 ; i < V.size() ; i++)
            if(V.get(i).getX() >= inicio && V.get(i).getX() < fin)
                aux.add(V.get(i));
        
        return aux;
    }
    
    
    
    
    /**
     * Ordenamiento HeapSort para vectores de puntos
     * 
     */
    
    void heap_sort(ArrayList<Punto> T){
        
        Punto aux;
                
        int n = T.size();
        build_heap(T);
        for(int i = n-1 ; i >= 0 ; --i){
            
            aux = T.get(i);
            T.set(i,T.get(0));
            T.set(0, aux);            
            
            heapify(T,i,0);
        }
        
    }    
    
    private void build_heap(ArrayList<Punto> T) {
        int n = T.size();
        for( int i = n/2-1 ; i >= 0 ; i--){
            heapify(T,n,i);
        }
    }
    
    private void heapify(ArrayList<Punto> T, int n, int i) {
        Punto x = T.get(i);
        int c = 2*i+1;
        while(c < n){
            if((c+1 < n) && (T.get(c).getX() < T.get(c+1).getX()))
                c++;
            if(x.getX() >= T.get(c).getX()) break;
            T.set(i,T.get(c));
            i = c;
            c = 2*i+1;
        }
        T.set(i, x);        
    }
    
    
    
    
    
    
    
    
    /**
     * 
     * Ordenamiento HeapSort para vectores de aristas
     * 
     * @param T 
     */
    
    
    void heap_sort_2(ArrayList<Arista> T){
        
        Arista aux;
                
        int n = T.size();
        build_heap_2(T);
        for(int i = n-1 ; i >= 0 ; --i){
            
            aux = T.get(i);
            T.set(i,T.get(0));
            T.set(0, aux);
            
            heapify_2(T,i,0);
        }
        
    }
        
    private void build_heap_2(ArrayList<Arista> T) {
        int n = T.size();
        for( int i = n/2-1 ; i >= 0 ; i--){
            heapify_2(T,n,i);
        }
    }

    private void heapify_2(ArrayList<Arista> T, int n, int i) {
        Arista x = T.get(i);
        int c = 2*i+1;
        while(c < n){
            if((c+1 < n) && (T.get(c).getDistancia() < T.get(c+1).getDistancia()))
                c++;
            if(x.getDistancia() >= T.get(c).getDistancia()) break;
            T.set(i,T.get(c));
            i = c;
            c = 2*i+1;
        }
        T.set(i, x);        
    }


    
    
    /**
     * ALGORITMO DE PRIM - Dado un grafo g, devuelve el conjunto solucion
     * de las aristas que forman el camino mínimo
     * 
     * @param g
     * @return 
     */
    
    public ArrayList<Arista> AlgoritmoPrim(Grafo g){
        
        // DECLARACION DE VARIABLES E INICIALIZACION        
        double matriz_adyacencia[][] = g.getMatrizDistancias();        
        int n = matriz_adyacencia.length;
        ArrayList<Arista> solucion = new ArrayList<>();
        
        int k = 0;
        double min;
        int mas_proximo[] = new int[n];
        double distancia_minima[] = new double[n];            
        ////////////////////////////////////////////////        
        distancia_minima[0] = -1;
        
        for(int i = 0 ; i < n ; i++){
            mas_proximo[i] = 1;
            distancia_minima[i] = matriz_adyacencia[i][1];
        }        
        // BUCLE VORAZ   
        for(int i = 0 ; i < n ; i++){
            
            min = 10000000;
            
            for(int j = 0 ; j < n ; j++){
                if (distancia_minima[j] >= 0 && distancia_minima[j] < min){
                    min = distancia_minima[j];
                    k = j;
                }
            }
            
            solucion.add(new Arista(g.getVertices().get(mas_proximo[k]),
                    g.getVertices().get(k),
                    matriz_adyacencia[mas_proximo[k]][k]));
            distancia_minima[k] = -1;
                        
            for(int j = 0 ; j < n ; j++){
                if(matriz_adyacencia[j][k] < distancia_minima[j]){
                    distancia_minima[j] = matriz_adyacencia[j][k];
                    mas_proximo[j] = k;
                }
            }
        }        
        return solucion;
    }
    
    
    /**
     * 
     * ALGORITMO DE KRUSKAL
     * 
     * @param g
     * @return 
     */
    
    /////////////// ALGORITMO DE KRUSKAL /////////////////////////////////////
    
    
    public ArrayList<Arista> AlgoritmoKruskal(Grafo g){
        
        ArrayList<Arista> ConjuntoCandidatos;
        ArrayList<Arista> ConjuntoSolucion;
        
        int n = g.getNumelementos();

        ConjuntoSolucion = new ArrayList<>();
        ConjuntoCandidatos = g.getAristas();
        //Ordenar A segun coordenadas crecientes        
        heap_sort_2(ConjuntoCandidatos);                
        //Inicializar, creamos un conjunto de conjuntos disjuntos de vertices            
        ArrayList<ArrayList> ConjuntoConjuntoVertice = new ArrayList<>();
            
        for(int z = 0 ; z < g.getVertices().size() ; z++){
            ArrayList<Punto> ConjuntoVertices = new ArrayList<>();
            ConjuntoVertices.add(g.getVertices().get(z));
            ConjuntoConjuntoVertice.add(ConjuntoVertices);
        }            
        //Bucle voraz        
        while(ConjuntoSolucion.size() < n-1){
            
            //Extraemos la arista mas corta eliminandola de la cola.
            Arista aux = ConjuntoCandidatos.get(0);
            ConjuntoCandidatos.remove(0);

            int i = 0 , j = 0;
            
            //Comprobamos que los vertices de la arista pertenecena distintos
            //conjuntos disjuntos.            
            while(!ConjuntoConjuntoVertice.get(i).contains(aux.getA()))
                i++;
            
            while(!ConjuntoConjuntoVertice.get(j).contains(aux.getB()))
                j++;
            
            if(i != j){
                Fusionar(ConjuntoConjuntoVertice,i,j);
                ConjuntoSolucion.add(aux);
            }
        }        
        return ConjuntoSolucion;
    }
    
    
    public void Fusionar(ArrayList <ArrayList> CCV, int i, int j){
        
        int x = 0;
        
        for(x = 0; x < CCV.get(i).size(); x++)
            CCV.get(j).add(CCV.get(i).get(x));
                
        CCV.remove(i);
    }
    
}

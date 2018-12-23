/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amc_practica1;

import amc_practica1.Grafo.Arista;
import amc_practica1.Grafo.Grafo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Oscar Chaves Navarro <ochndev at github.com>
 */
public class LogicaDeLaAplicacion {
    
    ArrayList<Punto> elementos;
    int cantidad_puntos;
    ArrayList<Arista> conjunto_solucion;
    Triangulo trianguloClase;
    public static final int EXHAUSTIVO = 0;
    public static final int DYV = 1;
    public static final int PRIM = 2;
    public static final int KRUSKAL = 3;
    
    
    
    public LogicaDeLaAplicacion(){
        conjunto_solucion = new ArrayList<>();
        cantidad_puntos = 0;
        elementos = null;
    }
    
    
    
    /**
     * 
     * Carga el nombre, comentarios y dimension, así como los puntos contenidos
     * en un archivo TSP
     * 
     * @param ruta
     * @param parametros
     * @return
     * @throws IOException 
     */
    
    public ArrayList<Punto> cargarArchivoTSP (String ruta, String [] parametros) throws IOException{
                
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        
        archivo = new File(ruta);
        fr = new FileReader(archivo);
        br = new BufferedReader(fr);

        String linea;
        
        elementos = new ArrayList<>();
        
        
        while(!"EOF".equals(linea = br.readLine()) && !linea.contains("1 ")){
            
            System.out.println(linea);
            
            if(linea.contains("NAME")){
                linea = linea.replace("NAME : ", "");
                linea = linea.replace("NAME: ", "");
                parametros[0] = linea;
            }            
            if(linea.contains("COMMENT")){
                linea = linea.replace("COMMENT : ", "");
                linea = linea.replace("COMMENT: ", "");
                parametros[1] = linea;
            }
            if(linea.contains("DIMENSION")){                
                linea = linea.replace("DIMENSION : ", "");
                linea = linea.replace("DIMENSION: ", "");
                parametros[2] = linea;
                cantidad_puntos = Integer.parseInt(parametros[2]);
            }
           
        }
        
        do{
        
            Punto punto_aux;
            String [] parts = linea.split(" ");
            
            punto_aux = new Punto(Integer.parseInt(parts[0]) , Double.parseDouble(parts[1]),Double.parseDouble(parts[2]));
            
            elementos.add(punto_aux);
            
        }while(!"EOF".equals(linea = br.readLine()));
        
        br.close();
        fr.close();
        
        return elementos;
        
    }

    
    /**
     * 
     * Metodo que escribe un archivo TSP con los datos del vector de ciudades
     * 
     * @param ruta
     * @param nombre
     * @param solucion
     * @throws IOException 
     */
    
    public void GuardarArchivoTSP(String ruta , String nombre, String solucion) throws IOException{
        
        String nombre_archivo = ruta+"\\"+nombre+".tour";
        
        System.out.println("nombre: "+nombre);
        System.out.println("ruta: "+ruta);
        System.out.println("solucion: "+solucion);
        System.out.println("nombre de archivo: "+nombre_archivo);
        
        try (FileWriter fw = new FileWriter(nombre_archivo); 
                BufferedWriter bw = new BufferedWriter(fw)) {
            
            bw.write("NAME: " + nombre);
            bw.newLine();
            bw.write("TYPE: tour");
            bw.newLine();
            bw.write("DIMENSION: " + conjunto_solucion.size());
            bw.newLine();
            bw.write("SOLUTION: " + solucion);
            bw.newLine();
            bw.write("TOUR_SECTION");
            bw.newLine();
            
            for(int i = 0 ; i < conjunto_solucion.size(); i++){
                bw.write(conjunto_solucion.get(i).getA().getNombre() + "," + conjunto_solucion.get(i).getB().getNombre());
                bw.newLine();
            }
            bw.write("-1");
            bw.newLine();
            bw.write("EOF");
            
            bw.close();
            fw.close();
            
        }
        
    }
    
    
    
    
    /**
     * 
     * Dado un numero tamaño determinado, un minimo y un maximo pasados por parametro
     * genera un ArrayList de puntos aleatorios con esas caracteristicas
     * 
     * @param tamanyo
     * @param minimo
     * @param maximo
     * @return 
     */
    
    public ArrayList<Punto> generarPuntosAleatorios(int tamanyo){
        
        elementos = new ArrayList<>(tamanyo);
        double coordenadaX = 0, coordenadaY = 0;
        Random rnd = new Random();
        
        rnd.setSeed(System.currentTimeMillis());
        
        for(int i = 0 ; i < tamanyo ; i++){ 
                
            coordenadaX = rnd.nextDouble() * (1000 - 1) + 1;
            coordenadaX = Math.round(coordenadaX*1000d)/1000d;
            coordenadaY = rnd.nextDouble() * (1000 - 1) + 1;
            coordenadaY = Math.round(coordenadaY*1000d)/1000d;
            
            elementos.add(new Punto( i+1, coordenadaX, coordenadaY));
            
        }
        cantidad_puntos = elementos.size();
        
        return elementos;
    }
    
    /**
     * 
     * @param tamanyo
     * @param minimo
     * @param maximo
     * @return 
     */
    
    
    public ArrayList<Punto> generarPuntosAleatoriosPeor(int tamanyo){
        
        elementos = new ArrayList<>(tamanyo);
        double coordenadaX = 0, coordenadaY = 0;
        Random rnd = new Random();        
        rnd.setSeed(System.currentTimeMillis());       
        
        double mitad = (1000 - 1)/2 + 1;
        double distancia_central = (1000 - 1)/50;
        System.out.println("mitad: "+mitad);
        System.out.println("distancia_central: "+distancia_central);
        
        double cotamin = (mitad - distancia_central) + 1;
        double cotamax = (mitad + distancia_central) + 1;
        
        System.out.println("cotamin: "+cotamin);
        System.out.println("cotamax: "+cotamax);
        
        //Generamos el punto de inicio
        
        coordenadaX = 1;                
        coordenadaY = mitad;
        elementos.add(new Punto(1,coordenadaX, coordenadaY));       
        
        //generamos los puntos centrales
        for(int i = 1 ; i < tamanyo-1 ; i++){ 
                
            coordenadaX = rnd.nextDouble() * (cotamax - cotamin)+cotamin;
            coordenadaX = Math.round(coordenadaX*1000d)/1000d;
            coordenadaY = rnd.nextDouble() * (1000 - 1) + 1;
            coordenadaY = Math.round(coordenadaY*1000d)/1000d;
            
            elementos.add(new Punto( i+1, coordenadaX, coordenadaY));
            
        }
        
        //generamos el punto final
        coordenadaX = 1000;
        coordenadaY = mitad;
        elementos.add(new Punto(tamanyo,coordenadaX,coordenadaY));
        
        cantidad_puntos = elementos.size();
        
        return elementos;
    }
    
    
    /**
     * 
     * Ejecuta el algoritmo pasado por parametro METODO, generando distintos 
     * vectores aleatorios de puntos indicados en TALLAS y mide el tiempo de ejecucion
     * 
     * @param tallas
     * @param repeticiones
     * @param metodo
     * @return 
     */
    
    public long [] medirTiempoAlgoritmo(int [] tallas , int repeticiones , int metodo, boolean caso_peor){      
        
        long tiempo_final = 0, tiempo_inicial = 0, tiempo_total = 0;
        long [] tiempo_medio;
        tiempo_medio = new long [tallas.length];
        Triangulo auxiliar = new Triangulo();
        
        double distancia_minima;
        
        Algoritmos alg;
        
        for(int i = 0; i < tallas.length; i++){
            
            if(caso_peor)
                alg = new Algoritmos(generarPuntosAleatoriosPeor(tallas[i]));
            else
                alg = new Algoritmos(generarPuntosAleatorios(tallas[i]));
            
            tiempo_final = 0; tiempo_inicial = 0; tiempo_total = 0;
            
            for(int j = 0 ; j < repeticiones ; j++){
        
                tiempo_inicial = System.currentTimeMillis();                
                
                switch(metodo){
                    
                    case EXHAUSTIVO:
                        auxiliar = alg.metodoExhaustivo(elementos);
                    break;
                    
                    case DYV:
                        auxiliar = alg.metodoDyV(elementos);
                        System.out.println("distancia: "+auxiliar.getDistancia());
                        System.out.println("punto A: ["+auxiliar.getA().getX()+" - "+auxiliar.getA().getY()+"]");
                        System.out.println("punto B: ["+auxiliar.getB().getX()+" - "+auxiliar.getB().getY()+"]");
                        System.out.println("punto C: ["+auxiliar.getC().getX()+" - "+auxiliar.getC().getY()+"]");
                    break;
                    
                    case PRIM:
                        conjunto_solucion = new ArrayList<>();
                        Grafo graf1 = new Grafo(elementos);
                        conjunto_solucion = alg.AlgoritmoPrim(graf1);
                        distancia_minima = calcularDistanciaAristas(conjunto_solucion);
                        representarAristas(conjunto_solucion);
                        
                    break;
                    
                    case KRUSKAL:
                        conjunto_solucion = new ArrayList<>();
                        Grafo graf2 = new Grafo(elementos);
                        conjunto_solucion = alg.AlgoritmoKruskal(graf2);
                        distancia_minima = calcularDistanciaAristas(conjunto_solucion);
                        representarAristas(conjunto_solucion);
                    break;
                    
                    default:
                    break;
                    
                }
                
                tiempo_final = System.currentTimeMillis() - tiempo_inicial;
                tiempo_total += tiempo_final;
            }
            
            tiempo_medio[i] = tiempo_total / repeticiones;
            
        }
        
        return tiempo_medio;
        
    }
      
    
    public Triangulo probarMetodo(int metodo){
        
        Triangulo tri = new Triangulo();
        double distancia_minima = 0;
        Algoritmos alg = new Algoritmos(elementos);        
        
        switch (metodo){
            
            case EXHAUSTIVO: 
                tri = alg.metodoExhaustivo(elementos);
            break;
            case DYV:                
                tri = alg.metodoDyV(elementos);
                break;
            
            case PRIM:
                conjunto_solucion = new ArrayList<>();
                Grafo graf1 = new Grafo(elementos);
                conjunto_solucion = alg.AlgoritmoPrim(graf1);
                distancia_minima = calcularDistanciaAristas(conjunto_solucion);
                tri.setDistancia(distancia_minima);
                representarAristas(conjunto_solucion);
            break;
            
            case KRUSKAL:
                conjunto_solucion = new ArrayList<>();
                Grafo graf2 = new Grafo(elementos);
                conjunto_solucion = alg.AlgoritmoKruskal(graf2);
                distancia_minima = calcularDistanciaAristas(conjunto_solucion);
                tri.setDistancia(distancia_minima);
                representarAristas(conjunto_solucion);
                
            break;
        
        }
        
        return tri;
                
    }
  

    /**
     * 
     * Retorna un Array bidimensional para representacion en pantalla
     * con la primera fila con CoordX y la segunda fila con CoordY
     * 
     * @param puntos
     * @param resX
     * @param resY
     * @return 
     */
    
    public int[][] ConvertirCoordenadasPantalla(ArrayList<Punto> puntos, int resX, int resY){
        

        
        double maxY = 0, maxX = 0, minX = 100000, minY = 100000;
        
        int [][] resultado = new int [2][puntos.size()];
        
        //Obtenemos el maximo
        
        for(int i=0; i<elementos.size(); i++){
          
          if(elementos.get(i).getX() > maxX)
              maxX = elementos.get(i).getX();
          
          if(elementos.get(i).getY() > maxY)
              maxY = elementos.get(i).getY();
        }
        
        //Obtenemos el minimo
        
        for(int j = 0 ; j < elementos.size() ; j++){
            
            if(elementos.get(j).getX() < minX)
                minX = elementos.get(j).getX();
            
            if(elementos.get(j).getY() < minY)
                minY = elementos.get(j).getY();
            
        }
        
        //Redondea por exceso
        
        maxY = Math.ceil(maxY);
        maxX = Math.ceil(maxX);
        
        //Redondea por defecto
        
        minX = Math.floor(minX);
        minY = Math.floor(minY);
        
        //La primera columna son las coordenadas X y la segunda las Y
        
        for(int i = 0; i<puntos.size(); i++){
            
            resultado[0][i] = (int) Math.round((((puntos.get(i).getX())/maxX) * resX));
            resultado[1][i] = resY - (int) Math.round(((puntos.get(i).getY()/maxY) * resY));
            
        }
        
        return resultado;
        
    }
    
    
    /**
     * 
     * Convierte las coordenadas de tiempos pasadas en coordenadas
     * graficas en el canvas segun su resolucion
     * 
     * @param tiempos
     * @param tallas
     * @param resX
     * @param resY
     * @return 
     */
    
    public int[][] ConvertirCoordenadasPantalla(long [] tiempos , int [] tallas , int resX, int resY , long maxY){
        
        double maxX = 0, minX = 100000, minY = 100000;
        
        int [][] resultado = new int [tallas.length][tiempos.length];
        
        //Obtenemos el maximo
        
        for(int i=0; i<tallas.length; i++){
          
          if(tallas[i] > maxX)
              maxX = tallas[i];
          
        //  if(tiempos[i] > maxY)
        //      maxY = tiempos[i];
        }
        
        //Obtenemos el minimo
        
        for(int j = 0 ; j < tallas.length ; j++){
            
            if(tallas[j] < minX)
                minX = tallas[j];
            
            if(tiempos[j] < minY)
                minY = tiempos[j];
            
        }
        
        //Redondea por exceso
        
        //maxY = Math.ceil(maxY);
        maxX = Math.ceil(maxX);
        
        //Redondea por defecto
        
        minX = Math.floor(minX);
        minY = Math.floor(minY);
        
        //La primera fila son las coordenadas X y la segunda las Y
        
        for(int i = 0; i<tiempos.length; i++){
            
            resultado[0][i] = (int) Math.round(((( tallas[i] - minX) / maxX) * resX))+15;
            resultado[1][i] = resY - (int) Math.round(((( tiempos[i] - minY ) / maxY) * resY))+15;
        }
        
        return resultado;
        
    }

    private double calcularDistanciaAristas(ArrayList<Arista> ConjuntoSolucion) {
        
        double distancia_total = 0;
        for(int i = 0 ; i < ConjuntoSolucion.size() ; i++){
            distancia_total += ConjuntoSolucion.get(i).getDistancia();
        }
        return distancia_total;
        
    }

    private void representarAristas(ArrayList<Arista> ConjuntoSolucion) {
        
        for(int i = 0 ; i < ConjuntoSolucion.size() ; i++)
            ConjuntoSolucion.get(i).representarArista();
        
    }

    int[][] ConvertirCoordenadasPantallaGrafo(ArrayList<Arista> conjunto_solucion, int resX, int resY) {
        
        double maxY = 0, maxX = 0, minX = 100000, minY = 100000;
        
        int [][] resultado = new int [4][conjunto_solucion.size()];
        
        //Obtenemos el maximo
        
        for(int i=0; i<elementos.size(); i++){
          
          if(elementos.get(i).getX() > maxX)
              maxX = elementos.get(i).getX();
          
          if(elementos.get(i).getY() > maxY)
              maxY = elementos.get(i).getY();
        }
        
        //Obtenemos el minimo
        
        for(int j = 0 ; j < elementos.size() ; j++){
            
            if(elementos.get(j).getX() < minX)
                minX = elementos.get(j).getX();
            
            if(elementos.get(j).getY() < minY)
                minY = elementos.get(j).getY();
            
        }
        
        //Redondea por exceso
        
        maxY = Math.ceil(maxY);
        maxX = Math.ceil(maxX);
        
        //Redondea por defecto
        
        minX = Math.floor(minX);
        minY = Math.floor(minY);
        
        //La primera columna son las coordenadas X y la segunda las Y
        
        System.out.println("Cardinalidad conjunto_solucion: "+conjunto_solucion.size());
        
        for(int i = 0; i < conjunto_solucion.size(); i++){
            
            resultado[0][i] = (int) Math.round((((conjunto_solucion.get(i).getA().getX())/maxX) * resX));
            resultado[1][i] = resY - (int) Math.round(((conjunto_solucion.get(i).getA().getY()/maxY) * resY));
            resultado[2][i] = (int) Math.round((((conjunto_solucion.get(i).getB().getX())/maxX) * resX));
            resultado[3][i] = resY - (int) Math.round(((conjunto_solucion.get(i).getB().getY()/maxY) * resY));
            
        }
        
        return resultado;
        
    }

    
    
    
    
}

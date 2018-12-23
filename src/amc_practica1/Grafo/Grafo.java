/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amc_practica1.Grafo;

import amc_practica1.Punto;
import java.util.ArrayList;

/**
 *
 * @author Oscar Chaves Navarro <ochndev at github.com>
 */
public class Grafo {
    
    private ArrayList<Punto> vertices;
    private ArrayList<Arista> aristas;
    private int numelementos;
    private double [][] matriz_distancias;
    
    public Grafo(ArrayList<Punto> vertices){
        
        this.vertices = vertices;
        this.numelementos = vertices.size();
        this.aristas = new ArrayList();
        this.matriz_distancias = new double [numelementos][numelementos];
        
        //Generamos la matriz de distancias y creamos el conjunto de aristas
        
        for(int i= 0; i<numelementos; i++){
            for(int j = 0; j<numelementos; j++){
                this.matriz_distancias[i][j] = CalcularDistancia2Puntos(vertices.get(i),vertices.get(j));
                if(j != i && matriz_distancias[i][j] != 0)
                    this.aristas.add(new Arista(vertices.get(i),vertices.get(j),matriz_distancias[i][j]));
            }
        }
    }

    public ArrayList<Punto> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Punto> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Arista> getAristas() {
        return aristas;
    }

    public void setAristas(ArrayList<Arista> aristas) {
        this.aristas = aristas;
    }

    public int getNumelementos() {
        return numelementos;
    }

    public void setNumelementos(int numelementos) {
        this.numelementos = numelementos;
    }
    
    private double CalcularDistancia2Puntos(Punto A, Punto B){
        double distancia = Math.sqrt(Math.pow(A.getX()-B.getX(),2)+Math.pow(A.getY()-B.getY(),2));
        return distancia;
    }
    
    public double [][] getMatrizDistancias(){
        return this.matriz_distancias;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amc_practica1.Grafo;

import amc_practica1.Punto;

/**
 * 
 * @author Oscar Chaves Navarro <ochndev at github.com>
 */

public class Arista {
    
    Punto a;
    Punto b;
    double distancia;

    public Arista(Punto a, Punto b, double distancia) {
        this.a = a;
        this.b = b;
        this.distancia = distancia;
    }
    
    public Punto getA() {
        return a;
    }

    public void setA(Punto a) {
        this.a = a;
    }

    public Punto getB() {
        return b;
    }

    public void setB(Punto b) {
        this.b = b;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    
    public void representarArista(){
        System.out.println("a: "+a.getX()+" - b: "+b.getY());
    }
    
    
}

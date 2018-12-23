/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amc_practica1;

/**
 *
 * @author usuario
 */
public class Triangulo {
    
    private Punto a;
    private Punto b;
    private Punto c;
    private double distancia;

    public Triangulo() {
    }

    public Triangulo(Punto a, Punto b, Punto c) {
        this.a = a;
        this.b = b;
        this.c = c;
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

    public Punto getC() {
        return c;
    }

    public void setC(Punto c) {
        this.c = c;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    
    
    
    
}

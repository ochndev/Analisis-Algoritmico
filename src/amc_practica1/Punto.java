/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amc_practica1;

/**
 *
 * @author Oscar Chaves Navarro <ochndev at github.com>
 */
public class Punto {
    
    private double x, y;
    private int nombre;
    
    Punto(double x, double y){
        this.x = x;
        this.y = y;
    }

    Punto(int nombre, double x, double y) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }   
    
}

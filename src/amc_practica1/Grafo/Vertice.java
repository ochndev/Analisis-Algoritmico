/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amc_practica1.Grafo;

/**
 *
 * @author Oscar Chaves Navarro <ochndev at github.com>
 */
public class Vertice {
    
    private double coordX;
    private double coordY;
    int nombre;

    public Vertice(double coordX, double coordY, int nombre) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.nombre = nombre;
    }

    public double getX() {
        return coordX;
    }

    public void setX(int coordX) {
        this.coordX = coordX;
    }

    public double getY() {
        return coordY;
    }

    public void setY(int coordY) {
        this.coordY = coordY;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
    
}

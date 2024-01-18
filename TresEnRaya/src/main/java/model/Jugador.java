/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Alvarez Orellana Moises
 * @author Flores González Carlos
 * @author Maldonado Jaramillo Paulette
 */
public class Jugador implements Serializable {
    private String item;
    private String nombre;
    private boolean turno;
    private boolean modoAuto;

    public Jugador(String item, String nombre, boolean turno) {
        this.item = item;
        this.nombre = nombre;
        this.turno = turno;
        modoAuto = false;
    }

    public boolean isModoAuto() {
        return modoAuto;
    }

    public void setModoAuto(boolean modoAuto) {
        this.modoAuto = modoAuto;
    }
    

    public Jugador(String nombre, boolean turno) {
        this.item = null;
        this.nombre = nombre;
        this.turno = turno;
    }
        
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Jugador{" + "item=" + item + ", nombre=" + nombre + ", turno=" + turno + '}';
    }
}

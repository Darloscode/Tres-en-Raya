/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.mycompany.tresenraya.SecondaryController;
import java.util.EventListener;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author ander
 */
public class Cell extends Button{       
    
    private String signo;
    private int fila;
    private int columna;    
    
    public Cell(int fila, int columna) {
        this.signo = null;
        this.fila = fila;
        this.columna = columna;        
        setMinSize(100, 100);
        setOnAction(e -> {
            if (!SecondaryController.tableroCompleto()) {
                manejarClic();
                SecondaryController.turno = 0;
                SecondaryController.computadora();
            }
        });        
    }    

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo;
    }
    
    public void manejarClic() {    
        if (this.signo == null) {
            System.out.println(this.toString());
            //"file:imagenes\\x.png"
            Image imagen = new Image(SecondaryController.jugada());        
            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(90);
            imageView.setFitHeight(90);        
            this.setGraphic(imageView);
            this.setSigno(SecondaryController.jugada());
            System.out.println(this.toString());
        }
    }
    
    @Override
    public String toString() {
        return "Cell{" +
                "signo='" + signo + '\'' +
                ", fila=" + fila +
                ", columna=" + columna +
                '}';
    }
    

}

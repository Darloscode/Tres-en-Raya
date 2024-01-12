/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.mycompany.tresenraya.SecondaryController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 *
 * @author Alvarez Orellana Moises
 * @author Flores González Carlos
 * @author Maldonado Jaramillo Paulette
 */
public class Cell extends Button{
    private String signo;
    private int fila;
    private int columna;    
    
    public Cell(String signo) {
        this.signo = signo;
        this.fila = -1;
        this.columna = -1;
    }
    
    public Cell(int fila, int columna) {
        this.signo = null;
        this.fila = fila;
        this.columna = columna;
        setMinSize(100, 100);
        setOnAction(e -> {
            if (SecondaryController.modo.equals("solo")) {
                
                if (SecondaryController.jug1.isTurno()) {
                    manejarClic();
                    SecondaryController.maquin.setTurno(true);
                    SecondaryController.jug1.setTurno(false);
                    SecondaryController.hayGanador();
                    SecondaryController.computadora();
                }
                
            } else if (SecondaryController.modo.equals("coop")) {
                
                if (SecondaryController.jug1.isTurno()) {
                    manejarClic();
                    SecondaryController.jug2.setTurno(true);
                    SecondaryController.jug1.setTurno(false);
                    SecondaryController.hayGanador();
                } else {
                    manejarClic();
                    SecondaryController.jug2.setTurno(false);
                    SecondaryController.jug1.setTurno(true);
                    SecondaryController.hayGanador();
                }
                
            } else if (SecondaryController.modo.equals("auto")) {
                
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
            Image imagen = new Image(SecondaryController.jugada());
            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(90);
            imageView.setFitHeight(90);
            this.setGraphic(imageView);
            this.setSigno(SecondaryController.jugada());
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

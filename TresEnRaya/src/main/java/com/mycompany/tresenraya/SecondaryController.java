package com.mycompany.tresenraya;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Cell;

public class SecondaryController implements Initializable{
    @FXML
    private Label titulo;    
    @FXML
    private VBox boxPrincipal;
    
    GridPane tablero = new GridPane();
    public static int turno = 0;
    public static String maquina = "";
    public static String jugador1 = "";
    public static String jugador2 = "";
    public static boolean contraMaquina = false;    
    
    public static Cell[][] celdas = new Cell[3][3];
        
    public static String jugada() {
        if (SecondaryController.turno == 0) {
            return maquina;
        } else {
            return jugador1;
        }        
    }
    
    @FXML
    private void salir() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tablero.setPrefSize(270, 270);
        boxPrincipal.getChildren().add(tablero);
        
        tablero.setOnMouseClicked(eh->{
            System.out.println("Holaaaaaaaaaaaaaaaaa");
        });
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                celdas[i][j] = new Cell(i, j);
                tablero.add(celdas[i][j], j, i);
            }        
        }                                   
        if (this.turno == 0 ) {
            this.computadora();
        }
        /*
        while (!this.tableroCompleto()) {
            if (this.turno == 0) {
                this.computadora();
            }            
        }
        */
    }
    
    public static void computadora() {
        boolean salida = true;
        while (salida) {
            Random r = new Random();
            int fila = r.nextInt((2-0+1)+0);
            System.out.println(fila);

            int columna = r.nextInt((2-0+1)+0);
            System.out.println(columna);
            if (celdas[fila][columna].getSigno() == null) {
                celdas[fila][columna].manejarClic();
                SecondaryController.turno = 1;
                salida = false;
            }
            if (tableroCompleto()) {
                salida = false;
                SecondaryController.alerta();
            }
        }

    }
    
    public static boolean tableroCompleto() {        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {                
                if (celdas[i][j].getSigno() == null) {
                    return false;
                }
            }
        }
        return true;
    }         
    
    public static void alerta() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia!!");
        alert.setHeaderText(null);
        alert.setContentText("Juego terminado");
        alert.showAndWait();
    }       
}
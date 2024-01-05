package com.mycompany.tresenraya;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Cell;
import model.Jugador;

public class SecondaryController implements Initializable{
    @FXML
    private Label titulo;    
    @FXML
    private VBox boxPrincipal;
    
    GridPane tablero = new GridPane();
    
    public static Jugador maquin;
    public static Jugador jug1;
    public static Jugador jug2;
    public static String modo;
    
    public static Cell[][] celdas = new Cell[3][3];
        
    public static String jugada() {
        if (jug1.isTurno()) {
            return jug1.getItem();
        } else {
            return maquin.getItem();
        }        
    }
    
    @FXML
    private void salir() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tablero.setPrefSize(270, 270);
        tablero.setStyle("-fx-background-color: white;");
        boxPrincipal.getChildren().add(tablero);        
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                celdas[i][j] = new Cell(i, j);
                tablero.add(celdas[i][j], j, i);
            }        
        }
        if (modo.equals("solo")) {
            SecondaryController.computadora();
        } else if (modo.equals("coop")) {
            //Código o metodo para un juego de dos jugadores
        } else if (modo.equals("auto")) {
            //Código para que la computadora juegue contra si misma
        }
    }    
        
    public static void computadora() {
        if (maquin.isTurno()){
            boolean salida = true;
            while (salida) {
                Random r = new Random();

                int fila = r.nextInt((2-0+1)+0);                

                int columna = r.nextInt((2-0+1)+0);                

                if (celdas[fila][columna].getSigno() == null) {
                    celdas[fila][columna].manejarClic();
                    jug1.setTurno(true);
                    maquin.setTurno(false);
                    SecondaryController.hayGanador();
                    salida = false;
                }            
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

    public static void hayGanador() {
        String ganador = "";
        
        if (tableroCompleto()){
            ganador = "Empate";
        }
        
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (celdas[i][0].getSigno() != null &&
                celdas[i][0].getSigno().equals(celdas[i][1].getSigno()) &&
                celdas[i][1].getSigno().equals(celdas[i][2].getSigno())) {
                ganador = celdas[i][0].getSigno();
            }         
        }

        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (celdas[0][j].getSigno() != null &&
                celdas[0][j].getSigno().equals(celdas[1][j].getSigno()) &&
                celdas[1][j].getSigno().equals(celdas[2][j].getSigno())) {
                ganador = celdas[0][j].getSigno();
            }
        }

        // Verificar diagonales
        if (celdas[0][0].getSigno() != null &&
            celdas[0][0].getSigno().equals(celdas[1][1].getSigno()) &&
            celdas[1][1].getSigno().equals(celdas[2][2].getSigno())) {
            ganador = celdas[0][0].getSigno();
        }      

        if (celdas[0][2].getSigno() != null &&
            celdas[0][2].getSigno().equals(celdas[1][1].getSigno()) &&
            celdas[1][1].getSigno().equals(celdas[2][0].getSigno())) {
            ganador = celdas[0][2].getSigno();
        }                 
        
        if (ganador.equals("Empate")){
            SecondaryController.alerta("Es un empate!!");
            SecondaryController.alerta("Juego terminado");
            jug1.setTurno(false);
            maquin.setTurno(false);
            try {
                App.setRoot("primary");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            if (ganador.equals(jug1.getItem())) {
                SecondaryController.alerta("Ha ganado "+jug1.getNombre());                             
                SecondaryController.alerta("Juego terminado");   
                jug1.setTurno(false);
                maquin.setTurno(false);                
                try {
                    App.setRoot("primary");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (ganador.equals(maquin.getItem())) {
                SecondaryController.alerta("Ha ganado "+maquin.getNombre());
                SecondaryController.alerta("Juego terminado");
                jug1.setTurno(false);
                maquin.setTurno(false);                
                try {
                    App.setRoot("primary");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }        
    }
        
    public static void alerta(String mensaje) {        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información!!");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();        
    }    
}
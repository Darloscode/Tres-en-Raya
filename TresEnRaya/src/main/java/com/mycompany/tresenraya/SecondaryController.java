package com.mycompany.tresenraya;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import tree.Tree;
import tree.TreeNode;

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
            this.prueba();
            
            //SecondaryController.computadora();
        } else if (modo.equals("coop")) {
            //Código o metodo para un juego de dos jugadores
        } else if (modo.equals("auto")) {
            //Código para que la computadora juegue contra si misma
        }
    }    
        
    public static void computadora() {
        SecondaryController.prueba();
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
    
    public static void prueba() {        
        Tree<Cell[][]> arbol = new Tree<>(SecondaryController.copy(celdas));                
        
        List<Tree<Cell[][]>> lista1 = new ArrayList<Tree<Cell[][]>>();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {                
                if (celdas[i][j].getSigno() == null) {
                    Cell[][] nuevo = SecondaryController.copy(celdas);
                    nuevo[i][j].setSigno(maquin.getItem());
                    
                    Tree<Cell[][]> subArbol = new Tree<>(SecondaryController.copy(nuevo));
                                        
                    List<Tree<Cell[][]>> lista2 = new ArrayList<Tree<Cell[][]>>();
                                        
                    
                    for (int m=0; m<3; m++) {
                        for (int n=0; n<3; n++) {
                            if (nuevo[m][n].getSigno() == null) {
                                Cell[][] segunda = SecondaryController.copy(nuevo);
                                segunda[m][n].setSigno(jug1.getItem());
                                Tree<Cell[][]> sigArbol = new Tree<>(SecondaryController.copy(segunda));
                                lista2.add(sigArbol);
                            }
                        }
                    } 
                    subArbol.getRootNode().setChildren(lista2);                    
                    lista1.add(subArbol);                                                            
                }
            }
        }        
        arbol.getRootNode().setChildren(lista1);                        
        printNodesAtLevel(arbol.getRootNode(), 2, 1);
    }        
    
    private static void printNodesAtLevel(TreeNode<Cell[][]> node, int targetLevel, int currentLevel) {
        if (node == null) {
            return;
        }

        if (currentLevel == targetLevel) {
            System.out.println("*******************************");
            imprimirMatriz(node.getContent());           
            System.out.println("*******************************");
        } else {
            for (Tree<Cell[][]> childTree : node.getChildren()) {
                printNodesAtLevel(childTree.getRootNode(), targetLevel, currentLevel + 1);
            }
        }
    }    
    
    public static Cell[][] copy(Cell[][] matriz) {        
        Cell[][] copia = new Cell[3][3];        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell a = new Cell (matriz[i][j].getSigno());
                copia[i][j] = a;
            }
        }
        return copia;
    }    
    
    public static void imprimirMatriz(Cell[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].getSigno() == null) {
                    System.out.print(matriz[i][j].getSigno() + "\t");
                } else {
                    if (matriz[i][j].getSigno().equals("file:imagenes\\x.png")){
                        System.out.print("XXX" + "\t");
                    } else {
                        System.out.print("OOO" + "\t");
                    }
                }
                
            }
            System.out.println();
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

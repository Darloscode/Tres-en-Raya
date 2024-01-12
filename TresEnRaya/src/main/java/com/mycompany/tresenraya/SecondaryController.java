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
        if (modo.equals("solo")) {        
            if (jug1.isTurno()) {
                return jug1.getItem();
            } else {
                return maquin.getItem();
            }        
        } else if (modo.equals("coop")) {
            if (jug1.isTurno()) {
                return jug1.getItem();
            } else {
                return jug2.getItem();
            }                         
        }
        return "";        
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
            System.out.println(jug1);
            System.out.println(jug2);            
        } else if (modo.equals("auto")) {
            //Código para que la computadora juegue contra si misma
        }
    }    
        
    public static void computadora() {
        System.out.println("------------------------------------------------------------------");        
        if (maquin.isTurno()){
            SecondaryController.minimax();
            boolean salida = true;
            while (salida) {
                Random r = new Random();

                int fila = r.nextInt((2-0+1)+0);                

                int columna = r.nextInt((2-0+1)+0);                

                if (celdas[fila][columna].getSigno() == null) {
                    celdas[fila][columna].manejarClic();
                    jug1.setTurno(true);
                    maquin.setTurno(false);
                    System.out.println(SecondaryController.hayGanador());
                    salida = false;
                }            
            }
        }
    }
    
    public static void minimax() {        
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
        //printNodesAtLevel(arbol.getRootNode(), 2, 1);

        int mejorValor = Integer.MIN_VALUE;
        Tree<Cell[][]> mejorJugada = null;
        
        List<Tree<Cell[][]>> nodosHijos = arbol.getRootNode().getChildren();
        
        for (Tree<Cell[][]> hijo : nodosHijos) {            
            int valor = utilidadHojas(hijo, false);                                    
            if (valor > mejorValor) {
                mejorValor = valor;
                mejorJugada = hijo;
                System.out.println("HAY");
            }
        }
        
        imprimirMatriz(mejorJugada.getRoot());
        
    }        

    public static int evaluarTablero(Cell[][] tablero) {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0].getSigno() != null &&
                tablero[i][0].getSigno().equals(tablero[i][1].getSigno()) &&
                tablero[i][0].getSigno().equals(tablero[i][2].getSigno())) {
                return tablero[i][0].getSigno().equals(maquin.getItem()) ? 1 : -1;
            }
        }

        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (tablero[0][j].getSigno() != null &&
                tablero[0][j].getSigno().equals(tablero[1][j].getSigno()) &&
                tablero[0][j].getSigno().equals(tablero[2][j].getSigno())) {
                return tablero[0][j].getSigno().equals(maquin.getItem()) ? 1 : -1;
            }
        }

        // Verificar diagonales
        if (tablero[0][0].getSigno() != null &&
            tablero[0][0].getSigno().equals(tablero[1][1].getSigno()) &&
            tablero[0][0].getSigno().equals(tablero[2][2].getSigno())) {
            return tablero[0][0].getSigno().equals(maquin.getItem()) ? 1 : -1;
        }

        if (tablero[0][2].getSigno() != null &&
            tablero[0][2].getSigno().equals(tablero[1][1].getSigno()) &&
            tablero[0][2].getSigno().equals(tablero[2][0].getSigno())) {
            return tablero[0][2].getSigno().equals(maquin.getItem()) ? 1 : -1;
        }

        // Si no hay ganador, se considera empate
        return 0;
    }    
    
    public static int utilidadHojas(Tree<Cell[][]> nodo, boolean esMaximizador) {
        
        if (nodo.isLeaf()) {
            return evaluarTablero(nodo.getRoot());
        }

        List<Tree<Cell[][]>> hijos = nodo.getRootNode().getChildren();
        int mejorValor;

        if (esMaximizador) {
            mejorValor = Integer.MIN_VALUE;

            for (Tree<Cell[][]> hijo : hijos) {
                int valor = utilidadHojas(hijo, false);
                mejorValor = Math.max(mejorValor, valor);
            }
        } else {
            mejorValor = Integer.MAX_VALUE;

            for (Tree<Cell[][]> hijo : hijos) {
                int valor = utilidadHojas(hijo, true);
                mejorValor = Math.min(mejorValor, valor);
            }
        }
        return mejorValor;
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
        if (matriz!=null){
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

    public static boolean hayGanador() {
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
            
            if (modo.equals("solo")) {
                jug1.setTurno(false);
                maquin.setTurno(false);                
            } else if (modo.equals("coop")) {
                jug1.setTurno(false);
                jug2.setTurno(false);
            } else {
                
            }            
            try {
                App.setRoot("primary");
                return false;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            
            if (modo.equals("solo")) {

                if (ganador.equals(jug1.getItem())) {
                    SecondaryController.alerta("Ha ganado "+jug1.getNombre());                             
                    SecondaryController.alerta("Juego terminado");   
                    jug1.setTurno(false);
                    maquin.setTurno(false);                
                    try {
                        App.setRoot("primary");
                        return true;
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
                        return true;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                
            } else if (modo.equals("coop")) {
                
                if (ganador.equals(jug1.getItem())) {
                    SecondaryController.alerta("Ha ganado "+jug1.getNombre());                             
                    SecondaryController.alerta("Juego terminado");   
                    jug1.setTurno(false);
                    jug2.setTurno(false);                
                    try {
                        App.setRoot("primary");
                        return true;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (ganador.equals(jug2.getItem())) {
                    SecondaryController.alerta("Ha ganado "+jug2.getNombre());
                    SecondaryController.alerta("Juego terminado");
                    jug1.setTurno(false);
                    jug2.setTurno(false);                
                    try {
                        App.setRoot("primary");
                        return true;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }                
                
            } else {
                
            }                       
        }        
        return false;
    }
        
    public static void alerta(String mensaje) {        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información!!");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();        
    }    
}
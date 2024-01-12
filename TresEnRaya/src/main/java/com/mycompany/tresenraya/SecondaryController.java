package com.mycompany.tresenraya;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Cell;
import model.Jugador;
import tree.Tree;
import tree.TreeNode;

/**
 *
 * @author Alvarez Orellana Moises
 * @author Flores González Carlos
 * @author Maldonado Jaramillo Paulette
 */
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
            //
        }
    }    

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
        } else if (modo.equals("auto")) {
            //
        }
        return "";
    }
    
    public static void computadora() {              
        if (maquin.isTurno()){
            Cell[][] jugada = SecondaryController.minimax();
            int[] coordenadas = obtenerCoordenadas(celdas,jugada);
            celdas[coordenadas[0]][coordenadas[1]].manejarClic();
            jug1.setTurno(true);
            maquin.setTurno(false);
            SecondaryController.hayGanador();
        }
    }
    
    public static Cell[][] minimax() {        
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
        
        int mejorValor = Integer.MIN_VALUE;
        Tree<Cell[][]> mejorJugada = null;
        
        List<Tree<Cell[][]>> nodosHijos = arbol.getRootNode().getChildren();
        
        for (Tree<Cell[][]> hijo : nodosHijos) {            
            if (evaluarTablero(hijo.getRoot())==1) {
                mejorJugada = hijo; 
                break;
            }
            
            int valor = 0;
            
            int peorValor = Integer.MAX_VALUE;
            List<Tree<Cell[][]>> nodos3 = hijo.getRootNode().getChildren();
                        
            for (Tree<Cell[][]> subHijo : nodos3) {
                int valor2 = utilidadMínima(subHijo.getRoot());
                
                if (valor2 < peorValor) {
                    peorValor = valor2;
                    valor = valor2;
                }
            }
            
            if (valor > mejorValor) {
                mejorValor = valor;
                mejorJugada = hijo;                
            }
        }                
        return mejorJugada.getRoot();
    }        

    public static int[] obtenerCoordenadas(Cell[][] actual, Cell[][] nuevo) {
        int[] coordenadas = new int[2];        
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (nuevo[i][j].getSigno() != null) {
                    if (actual[i][j].getSigno() == null) {                        
                        coordenadas[0] = i;
                        coordenadas[1] = j;                        
                    }                    
                }
            }
        }
        return coordenadas;
    }
    
    public static int utilidadMínima(Cell[][] matriz) {
        String item = maquin.getItem();
        int countFilas = 0;
        int countColumnas = 0;
        int countDiagonales = 0;
        int countDI = 0;
        int countDD = 0;        
        int c = 2;
        
        for (int i=0; i<3; i++) {
            int countF = 0;
            int countC = 0;
                        
            for (int j=0; j<3; j++) {                
                if (matriz[i][j].getSigno() == null || matriz[i][j].getSigno().equals(item)) {
                    countF++;
                }                
                if (matriz[j][i].getSigno() == null || matriz[j][i].getSigno().equals(item)) {
                    countC++;
                }          
            }
            
            if (countF == 3) {
                countFilas++;
            }
            
            if (countC == 3) {
                countColumnas++;
            }
            
            if (matriz[i][i].getSigno() == null || matriz[i][i].getSigno().equals(item)) {                
                countDI++;
            }
            
            if (matriz[i][c].getSigno() == null || matriz[i][c].getSigno().equals(item)) {                
                countDD++;
            }
            
            if (countDI == 3) {
                countDiagonales++;
            }
            if (countDD == 3) {
                countDiagonales++;
            }
            c--;
        }                
        return countFilas+countColumnas+countDiagonales;
    }         
    
    private static void printNivel(TreeNode<Cell[][]> node, int targetLevel, int currentLevel) {
        if (node == null) {
            return;
        }

        if (currentLevel == targetLevel) {
            System.out.println("*******************************");
            imprimirMatriz(node.getContent());
        } else {
            for (Tree<Cell[][]> childTree : node.getChildren()) {
                printNivel(childTree.getRootNode(), targetLevel, currentLevel + 1);
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

    public static int evaluarTablero(Cell[][] tablero) {
        String ganador = "";
        
        if (tableroCompleto()){
            ganador = "Empate";
        }
        
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0].getSigno() != null &&
                tablero[i][0].getSigno().equals(tablero[i][1].getSigno()) &&
                tablero[i][1].getSigno().equals(tablero[i][2].getSigno())) {
                ganador = tablero[i][0].getSigno();
            }
        }

        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (tablero[0][j].getSigno() != null &&
                tablero[0][j].getSigno().equals(tablero[1][j].getSigno()) &&
                tablero[1][j].getSigno().equals(tablero[2][j].getSigno())) {
                ganador = tablero[0][j].getSigno();
            }
        }

        // Verificar diagonales
        if (tablero[0][0].getSigno() != null &&
            tablero[0][0].getSigno().equals(tablero[1][1].getSigno()) &&
            tablero[1][1].getSigno().equals(tablero[2][2].getSigno())) {
            ganador = tablero[0][0].getSigno();
        }

        if (tablero[0][2].getSigno() != null &&
            tablero[0][2].getSigno().equals(tablero[1][1].getSigno()) &&
            tablero[1][1].getSigno().equals(tablero[2][0].getSigno())) {
            ganador = tablero[0][2].getSigno();
        }
        
        if (ganador.equals("Empate")){
            return 0;
        } else {
            if (ganador.equals(jug1.getItem())) {
                return -1;
            } else if (ganador.equals(maquin.getItem())){
                return 1;
            } else {
                return -2;
            }
        }
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
            } else if (modo.equals("auto")){
                
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
            } else if (modo.equals("auto")) {
                
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
package com.mycompany.tresenraya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import model.Cell;
import tree.Tree;
import tree.TreeNode;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 659, 417);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();        
        /*
        Cell[][] celdas = new Cell[3][3];

        // Crear un árbol de enteros con 4 niveles
        Tree<Integer> arbol = new Tree<>();

        TreeNode<Integer> nd1 = new TreeNode<>(1);
        arbol.setRootNode(nd1);

        List<Tree<Integer>> lista1 = new LinkedList<Tree<Integer>>();
        Tree<Integer> s1= new Tree<>(2);
        Tree<Integer> s2= new Tree<>(3);
        Tree<Integer> s3= new Tree<>(4);
        Tree<Integer> s4= new Tree<>(5);
        lista1.add(s1);
        lista1.add(s2);
        lista1.add(s3);
        lista1.add(s4);        
        nd1.setChildren(lista1);

        List<Tree<Integer>> lista2 = new LinkedList<Tree<Integer>>();
        Tree<Integer> s5= new Tree<>(6);
        Tree<Integer> s6= new Tree<>(7);
        Tree<Integer> s7= new Tree<>(8);
        Tree<Integer> s8= new Tree<>(9);
        lista2.add(s5);
        lista2.add(s6);
        lista2.add(s7);
        lista2.add(s8);
        s1.getRootNode().setChildren(lista2);

        List<Tree<Integer>> lista3 = new LinkedList<Tree<Integer>>();
        Tree<Integer> s9 = new Tree<>(10);
        Tree<Integer> s10 = new Tree<>(11);
        Tree<Integer> s11 = new Tree<>(12);
        Tree<Integer> s12 = new Tree<>(13);        
        lista3.add(s9);
        lista3.add(s10);
        lista3.add(s11);
        lista3.add(s12);
        s2.getRootNode().setChildren(lista3);
                        
        for (int i=1; i<4; i++) {
            System.out.print("Nodos en el nivel "+i+": ");
            arbol.printNodesAtLevel(i);
            System.out.println("");
        }        */
    }
}
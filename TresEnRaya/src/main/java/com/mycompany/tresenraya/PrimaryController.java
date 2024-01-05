package com.mycompany.tresenraya;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Jugador;

public class PrimaryController {
    @FXML
    private Button jugarSolo;
    @FXML
    private Button juegoCoop;
    @FXML
    private Button autoJuego;
    @FXML
    private Button salir;
    @FXML
    private VBox boxPrincipal;
    
    @FXML
    private void jugarSolo() throws IOException {
        System.out.println("Jugando solo");
        SecondaryController.modo = "solo";
        this.opcionesSolo();
    }

    @FXML
    private void juegoCoop() throws IOException {
        System.out.println("Jugando con amigo");
        SecondaryController.modo = "coop";
        this.opcionesCoop();
    }

    @FXML
    private void autoJuego() throws IOException {
        System.out.println("Espectador de juego");
    }    
    
    @FXML
    private void salir() throws IOException {
        System.exit(0);
    }
    
    public void opcionesSolo() {
        boxPrincipal.getChildren().clear();
        HBox jugador = new HBox();
        HBox signo = new HBox();
        
        Label label1 = new Label("¿Quiere jugar primero?: ");
        Label label2 = new Label("¿Con cuál signo quiere jugar?: ");
        
        CheckBox check1 = new CheckBox();
        
        VBox xBox = new VBox();
        VBox circuloBox = new VBox();
        xBox.setAlignment(Pos.CENTER);
        circuloBox.setAlignment(Pos.CENTER);
        CheckBox x = new CheckBox();
        CheckBox circulo = new CheckBox();
        
        xBox.getChildren().addAll(new Label("X"), x);
        circuloBox.getChildren().addAll(new Label("Circulo"), circulo);
        
        jugador.getChildren().addAll(label1, check1);
        jugador.setSpacing(10);
        signo.getChildren().addAll(label2, xBox, circuloBox);
        signo.setSpacing(20);
        jugador.setAlignment(Pos.CENTER);
        signo.setAlignment(Pos.CENTER);
        Button aceptar = new Button("Aceptar");
        boxPrincipal.getChildren().addAll(jugador, signo, aceptar);
        boxPrincipal.setAlignment(Pos.CENTER);
        
        aceptar.setOnAction(eh -> {           
            if (x.isSelected() && circulo.isSelected()) {
                this.alerta();
            } else {                
                try {                    
                    if (check1.isSelected()) {
                        SecondaryController.jug1 = new Jugador("Jugador", true);
                        SecondaryController.maquin = new Jugador("Maquina", false);
                        this.seleccionSignos(x.isSelected());
                    } else {                        
                        SecondaryController.jug1 = new Jugador("Jugador", false);
                        SecondaryController.maquin = new Jugador("Maquina", true);                        
                        this.seleccionSignos(x.isSelected());
                    }
                    App.setRoot("secondary");                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });        
    }

    public void seleccionSignos (Boolean isSelected) {
        if (isSelected) {
            SecondaryController.jug1.setItem("file:imagenes\\x.png");
            SecondaryController.maquin.setItem("file:imagenes\\c.png");
        } else {
            SecondaryController.jug1.setItem("file:imagenes\\c.png");
            SecondaryController.maquin.setItem("file:imagenes\\x.png");
        }        
    }
        
    public void opcionesCoop() {
        //
    }
    
    private void alerta() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia!!");
        alert.setHeaderText(null);
        alert.setContentText("Solo debe seleccionar un signo");
        alert.showAndWait();
    }    
}

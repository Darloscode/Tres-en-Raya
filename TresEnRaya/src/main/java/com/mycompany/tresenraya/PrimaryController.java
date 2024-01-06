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
    
    public static void seleccionSignosPersonas (Boolean isSelected) {
        if (isSelected) {
            SecondaryController.jug1.setItem("file:imagenes\\x.png");
            SecondaryController.jug2.setItem("file:imagenes\\c.png");
        } else {
            SecondaryController.jug1.setItem("file:imagenes\\c.png");
            SecondaryController.jug2.setItem("file:imagenes\\x.png");
        }        
    }
        
    public void opcionesCoop() {
        boxPrincipal.getChildren().clear();
        
        HBox p1 = new HBox();
        HBox jugador1 = new HBox();
        HBox jugador2 = new HBox();
        HBox p2 = new HBox();
        HBox signo = new HBox();
        
        Label text0 = new Label("Escoja la ficha con la que desee jugar");
        
        Label text1 = new Label("Jugador 1:");
        VBox xBox = new VBox();
        VBox oBox = new VBox();
        xBox.setAlignment(Pos.CENTER);
        oBox.setAlignment(Pos.CENTER);
        CheckBox x = new CheckBox();
        CheckBox o = new CheckBox();
        xBox.getChildren().addAll(new Label("X"), x);
        oBox.getChildren().addAll(new Label("O"), o);
        
        Label text2 = new Label("Jugador 2:");
        VBox xBox2 = new VBox();
        VBox oBox2 = new VBox();
        xBox2.setAlignment(Pos.CENTER);
        oBox2.setAlignment(Pos.CENTER);
        CheckBox x2 = new CheckBox();
        CheckBox o2 = new CheckBox();
        xBox2.getChildren().addAll(new Label("X"), x2);
        oBox2.getChildren().addAll(new Label("O"), o2);
        
        Label text3 = new Label("¿Cuál signo quiere jugar primero?: ");
        
        VBox xBox3 = new VBox();
        VBox oBox3 = new VBox();
        xBox3.setAlignment(Pos.CENTER);
        oBox3.setAlignment(Pos.CENTER);
        CheckBox x3 = new CheckBox();
        CheckBox o3 = new CheckBox();
        xBox3.getChildren().addAll(new Label("X"), x3);
        oBox3.getChildren().addAll(new Label("O"), o3);
        
        p1.getChildren().add(text0);
        jugador1.getChildren().addAll(text1,xBox, oBox);
        jugador1.setSpacing(10);
        
        jugador2.getChildren().addAll(text2, xBox2, oBox2);
        jugador2.setSpacing(10);
        p2.getChildren().add(text3);
        signo.getChildren().addAll(xBox3, oBox3);
        signo.setSpacing(20);
        
        p1.setAlignment(Pos.CENTER);
        p2.setAlignment(Pos.CENTER);
        jugador1.setAlignment(Pos.CENTER);
        jugador2.setAlignment(Pos.CENTER);
        signo.setAlignment(Pos.CENTER);
        
        Button aceptar = new Button("Aceptar");
        boxPrincipal.getChildren().addAll(p1,jugador1, jugador2,p2,signo, aceptar);
        boxPrincipal.setAlignment(Pos.CENTER);
        
        aceptar.setOnAction(eh -> {
            if ((x.isSelected() && o.isSelected()) || (x2.isSelected() && o2.isSelected())) {
                this.alertaEntrada(2);
            } else if ((!x.isSelected() && !o.isSelected()) || (!x2.isSelected() && !o2.isSelected())) {
                this.alertaEntrada(0);
            } else {
                try {
                    if (x3.isSelected()) {
                        SecondaryController.jug1 = new Jugador("Jugador 1", true);
                        SecondaryController.jug2 = new Jugador("Jugador 2", false);
                        seleccionSignosPersonas(true);
                    } else {
                        SecondaryController.jug1 = new Jugador("Jugador 1", false);
                        SecondaryController.jug2 = new Jugador("Jugador 2", true);
                        seleccionSignosPersonas(false);
                    }
                    App.setRoot("secondary");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });        
    }
    
    private void alertaEntrada(int n) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia!");
        alert.setHeaderText(null);
        if(n==2){
            alert.setContentText("Solo debe seleccionar un signo por participante");
        }else if(n==0){
            alert.setContentText("Seleecione al menos un signo por participante");
        }
        alert.showAndWait();
    }    
    
    private void alerta() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia!!");
        alert.setHeaderText(null);
        alert.setContentText("Solo debe seleccionar un signo");
        alert.showAndWait();
    }    
}

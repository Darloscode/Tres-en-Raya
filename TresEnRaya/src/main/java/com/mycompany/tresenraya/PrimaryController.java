package com.mycompany.tresenraya;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Cell;
import model.Jugador;

/**
 *
 * @author Alvarez Orellana Moises
 * @author Flores González Carlos
 * @author Maldonado Jaramillo Paulette
 */
public class PrimaryController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File archivo = new File("partidas\\Partida_Guardada.ser");
        if (archivo.exists()) {
            Button cargarPartida = new Button("Continuar Partida");
            cargarPartida.setPrefHeight(30);
            cargarPartida.setPrefWidth(200);
            boxPrincipal.getChildren().add(2, cargarPartida);
            cargarPartida.setOnAction(eh -> {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("partidas\\Partida_Guardada.ser"))) {
                    SecondaryController.maquin = (Jugador) ois.readObject();
                    SecondaryController.jug1 = (Jugador) ois.readObject();
                    SecondaryController.jug2 = (Jugador) ois.readObject();
                    SecondaryController.modo = (String) ois.readObject();
                    SecondaryController.celdas = (Cell[][]) ois.readObject();
                    System.out.println("Objetos deserializados");
                    SecondaryController.guardado = true;
                    App.setRoot("secondary");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                archivo.delete();
            });
        } else {
            System.out.println("El archivo " + archivo + " no existe");
        }
    }

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

//

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
        circuloBox.getChildren().addAll(new Label("O"), circulo);

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
                this.alerta("Solo debe seleccionar un signo");
            } else if (!x.isSelected() && !circulo.isSelected()) {
                this.alerta("Debe seleccionar con que signo quiere jugar");
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
                    SecondaryController.celdas = new Cell[3][3];
                    App.setRoot("secondary");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void opcionesCoop() {
        boxPrincipal.getChildren().clear();

        HBox p1 = new HBox();
        HBox jugador1 = new HBox();
        HBox jugador2 = new HBox();
        HBox p2 = new HBox();
        HBox signo = new HBox();

        Label text0 = new Label("Escoja la ficha con la que desean jugar");

        Label text1 = new Label("Jugador 1:");
        VBox xBox = new VBox();
        xBox.setAlignment(Pos.CENTER);
        ComboBox<String> j1 = new ComboBox();
        j1.getItems().add("X");
        j1.getItems().add("O");
        xBox.getChildren().addAll(j1);

        Label text2 = new Label("Jugador 2:");
        VBox xBox2 = new VBox();
        xBox2.setAlignment(Pos.CENTER);
        ComboBox j2 = new ComboBox();
        j2.getItems().add("X");
        j2.getItems().add("O");
        xBox2.getChildren().addAll(j2);

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

        jugador1.getChildren().addAll(text1, xBox);
        jugador1.setSpacing(10);

        jugador2.getChildren().addAll(text2, xBox2);
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
        boxPrincipal.getChildren().addAll(p1, jugador1, jugador2, p2, signo, aceptar);
        boxPrincipal.setAlignment(Pos.CENTER);

        aceptar.setOnAction(eh -> {
            if (j1.getValue() == null) {
                this.alerta("Debe seleccionar un signo para el Jugador 1");
            } else if (j2.getValue() == null) {
                this.alerta("Debe seleccionar un signo para el Jugador 2");
            } else if (j1.getValue().equals(j2.getValue())) {
                this.alerta("Debe seleccionar signos diferentes para cada jugador");
            } else if (x3.isSelected() && o3.isSelected()) {
                this.alerta("Debe seleccionar solo un signo para que jugue primero");
            } else if (!x3.isSelected() && !o3.isSelected()) {
                this.alerta("Debe seleccionar un signo para que juegue primero");
            } else {
                try {
                    if (x3.isSelected()) {
                        seleccionSignosPersonas(j1.getValue());
                        if (SecondaryController.jug1.getItem().equals("file:imagenes\\x.png")) {
                            SecondaryController.jug1.setTurno(true);
                            SecondaryController.jug2.setTurno(false);
                        } else {
                            SecondaryController.jug1.setTurno(false);
                            SecondaryController.jug2.setTurno(true);
                        }
                    } else if (o3.isSelected()) {
                        seleccionSignosPersonas(j1.getValue());
                        if (SecondaryController.jug1.getItem().equals("file:imagenes\\c.png")) {
                            SecondaryController.jug1.setTurno(true);
                            SecondaryController.jug2.setTurno(false);
                        } else {
                            SecondaryController.jug1.setTurno(false);
                            SecondaryController.jug2.setTurno(true);
                        }
                    }
                    App.setRoot("secondary");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void seleccionSignos(Boolean isSelected) {
        if (isSelected) {
            SecondaryController.jug1.setItem("file:imagenes\\x.png");
            SecondaryController.maquin.setItem("file:imagenes\\c.png");
        } else {
            SecondaryController.jug1.setItem("file:imagenes\\c.png");
            SecondaryController.maquin.setItem("file:imagenes\\x.png");
        }
    }

    public static void seleccionSignosPersonas(String seleccion) {
        if (seleccion.equals("X")) {
            SecondaryController.jug1 = new Jugador("file:imagenes\\x.png", "Jugador 1", false);
            SecondaryController.jug2 = new Jugador("file:imagenes\\c.png", "Jugador 2", false);
        } else {
            SecondaryController.jug1 = new Jugador("file:imagenes\\c.png", "Jugador 1", false);
            SecondaryController.jug2 = new Jugador("file:imagenes\\x.png", "Jugador 2", false);
        }
    }

    private void alerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia!!");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

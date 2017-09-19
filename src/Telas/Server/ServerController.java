/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas.Server;

import RMIConection.ServerConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class ServerController implements Initializable {

    @FXML
    private TextField inputPorta;

    @FXML
    private TextField inputSalas;

    @FXML
    private TextField inputUsuariosPorSala;

    @FXML
    private Button buttonConnect;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputPorta.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputPorta.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        inputSalas.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputSalas.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        inputUsuariosPorSala.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputUsuariosPorSala.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    void onButtonConectarAction(ActionEvent event) throws IOException {
        try {
            
            //Abrir tela 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerMainScreen.fxml"));

            Parent parent = loader.load();

            ServerMainScreenController controller = loader.<ServerMainScreenController>getController();
            controller.setPorta(Integer.parseInt(this.inputPorta.getText()));
            controller.setNumSalas(Integer.parseInt(this.inputSalas.getText()));
            controller.setUsuariosPorSalas(Integer.parseInt(this.inputUsuariosPorSala.getText()));
            controller.iniciarVariaveis();

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);

            appStage.setScene(scene);
            appStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

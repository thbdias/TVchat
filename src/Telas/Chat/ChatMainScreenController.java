/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas.Chat;

import Models.Room;
import Models.User;
import RMIConection.ClientConnection;
import com.sun.deploy.util.FXLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class ChatMainScreenController implements Initializable {

    @FXML
    private TextField inputIp;

    @FXML
    private TextField inputNick;

    @FXML
    private Button buttonConnect;

    @FXML
    private ListView<Room> listaSalas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void onButtonConectarAction(ActionEvent event) throws IOException, Exception {

        ClientConnection.getInstance().setIp(this.inputIp.getText());
        ClientConnection.getInstance().setMainUser(new User(this.inputNick.getText(), 1, 0));
        ClientConnection.getInstance().conectar();
        
    }

    @FXML
    void onButtonEntrarAction(ActionEvent event) {
        
        Room r = listaSalas.getItems().get(listaSalas.getSelectionModel().getSelectedIndex());
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
            
            Parent parent = loader.load();
            
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            
            appStage.setScene(scene);
            appStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatMainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

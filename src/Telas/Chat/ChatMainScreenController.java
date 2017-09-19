/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas.Chat;

import Models.User;
import RMIConection.ClientConnection;
import com.sun.deploy.util.FXLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class ChatMainScreenController implements Initializable {

    @FXML
    private TextField inputIp;

    @FXML
    private TextField inputPorta;

    @FXML
    private TextField inputNick;

    @FXML
    private Button buttonConnect;

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
        ClientConnection.getInstance().setPorta(Integer.parseInt(this.inputPorta.getText()));
        ClientConnection.getInstance().setMainUser(new User(this.inputNick.getText(), "algumid", 0));
        ClientConnection.getInstance().conectar();
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        
        Parent parent = loader.load();
        
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        
        appStage.setScene(scene);
        appStage.show();
        
    }

}

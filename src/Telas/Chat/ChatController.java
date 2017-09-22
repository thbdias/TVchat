/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas.Chat;

import Models.Mensagem;
import Models.User;
import RMIConection.ClientConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author 912313
 */
public class ChatController implements Initializable {

    public ListView<User> list_clients;
    ObservableList<User> listUsers;
    public ListView<Mensagem> list_chat;
    ObservableList<Mensagem> listMessages;
    public Button button_send;
    public TextArea text_message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listUsers = FXCollections.observableArrayList();
        list_clients.setItems(listUsers);

        listMessages = FXCollections.observableArrayList();
        list_chat.setItems(listMessages);

        button_send.setOnAction((ActionEvent e) -> {
            enviarMensagem();
        });

        text_message.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                enviarMensagem();
                event.consume();
            }
        });

        // Seta listeners com a classe conection para atualizar a interface
        ClientConnection.getInstance().addUserAddedListener((User user) -> {
            // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
            Platform.runLater(
                    () -> {
                        // Update UI here.
                        this.listUsers.add(user);
                    }
            );
        });

        ClientConnection.getInstance().addUserRemovedListener((user) -> {
            // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
            Platform.runLater(
                    () -> {
                        // Update UI here.
                        this.listUsers.remove(user);
                    }
            );
        });

        ClientConnection.getInstance().addMessageRecievedListener((mensagem) -> {
            // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
            Platform.runLater(
                    () -> {
                        // Update UI here.
                        this.listMessages.add(mensagem);
                        list_chat.scrollTo(listMessages.size() - 1);
                    }
            );
        });
        
        ClientConnection.getInstance().lerMensagens();

    }

    public void setarMensagemDireta(User user) {
        this.text_message.setText("@" + user.getNick() + ": ");
        this.text_message.setFocusTraversable(true);
    }

    private void enviarMensagem() {
        if (text_message.getText().isEmpty()) {
            return;
        }
        Mensagem mensagem = new Mensagem(text_message.getText(), ClientConnection.getInstance().getMainUser());
        text_message.setText("");
        //listMessages.add(mensagem);
        list_chat.scrollTo(listMessages.size() - 1);
        //TODO enviar
        ClientConnection.getInstance().enviarMsg(mensagem);
    }

}

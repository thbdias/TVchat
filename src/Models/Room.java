/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vinic
 */
public class Room implements Serializable {

    int id;
    ObservableList<User> usuarios;
    Map <Integer,Queue<Mensagem>> mensagens;


    public Room(int id) {
        this.id = id;
        this.usuarios = FXCollections.observableArrayList();
        this.mensagens = new HashMap<>();
        this.usuarios = FXCollections.observableArrayList();
    }

    public Map<Integer, Queue<Mensagem>> getMensagens() {
        return mensagens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObservableList<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ObservableList<User> usuarios) {
        this.usuarios = usuarios;
    }

}

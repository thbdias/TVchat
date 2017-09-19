/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vinic
 */
public class Room implements Serializable {

    int id;
    ObservableList<User> usuarios;

    public Room(int id) {
        this.id = id;
        this.usuarios = FXCollections.observableArrayList();
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

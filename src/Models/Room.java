/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.collections.ObservableList;

/**
 *
 * @author vinic
 */
public class Room {
    int id;
    ObservableList<User> usuarios;

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

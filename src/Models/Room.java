/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author vinic
 */
public class Room implements Serializable {

    int id;
    transient List<User> usuarios;
    transient Map <Integer,Queue<Mensagem>> mensagens;


    public Room(int id) {
        this.id = id;
        this.usuarios = new ArrayList<>();
        this.mensagens = new HashMap<>();
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

    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }

}

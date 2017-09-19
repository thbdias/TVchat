/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIConection;

import Models.Room;
import Models.User;
import RMIConection.Interfaces.Chat;
import java.rmi.Naming;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vinic
 */
public class ServerConnection extends SuperConnection {

    private static ServerConnection INSTANCE;

    int numSalas = 0;
    int numPorta = 0;
    int numUsuariosPorSalas = 0;
    boolean prontoParaIniciar;

    ObservableList<Room> salas = FXCollections.observableArrayList();

    ;

    private ServerConnection() {
    }

    public static ServerConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerConnection();
        }
        return INSTANCE;
    }

    public ObservableList<Room> getSalas() {
        return salas;
    }

    public ServerConnection setNumSalas(int numSalas) {
        this.numSalas = numSalas;
        for (int i = 0; i < this.numSalas; i++) {
            this.salas.add(new Room(i));
//            this.salas.get(i).getUsuarios().add(
//                    new User("teste " + i, "teste", "teste", i)
//            );

        }
        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        return INSTANCE;
    }

    public ServerConnection setNumPorta(int numPorta) {
        this.numPorta = numPorta;
        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        return INSTANCE;
    }

    public ServerConnection setNumUsuariosPorSalas(int numUsuariosPorSalas) {
        this.numUsuariosPorSalas = numUsuariosPorSalas;
        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        return INSTANCE;
    }

    public void start() throws Exception {
        if (prontoParaIniciar) {
            try {
                java.rmi.registry.LocateRegistry.createRegistry(this.numPorta);
                System.out.println("RMI registry ready.");

                Chat server = new ChatImpl();
                Naming.rebind("rmi://localhost/chat", server);
                System.out.println("============ Servidor Conectado ============");
                
            } catch (Exception e) {
                System.out.println("Exception starting RMI registry:");
                e.printStackTrace();
            }
        } else {
            throw new Exception("Não foram informadas todas informações para iniciar");
        }
    }

}

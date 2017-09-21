/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIConection;

import Models.Mensagem;
import Models.Room;
import Models.User;
import RMIConection.Interfaces.Chat;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vinic
 */
public class ServerConnection extends SuperConnection{

    private static ServerConnection INSTANCE;

    int numSalas = 0;
    int numPorta = 0;
    int numUsuariosPorSalas = 0;
    boolean prontoParaIniciar;
    Registry registry;

    private Map<Integer, Room> salas;
    private ArrayList<Mensagem> mensagens;

    private ServerConnection() {
        super();
        mensagens = new ArrayList<>();
        salas = new HashMap<>();
        logging("Instanciado classe de ChatImpl");
    }

    public static ServerConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerConnection();
        }
        return INSTANCE;
    }

    public ServerConnection setNumSalas(int numSalas) {
        this.numSalas = numSalas;
        for (int i = 0; i < this.numSalas; i++) {
            this.salas.put(i, new Room(i));
//            this.salas.get(i).getUsuarios().add(
//                    new User("teste " + i, "teste", "teste", i)
//            );
        }
        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        logging("Setado salas: "+this.numSalas);
        return INSTANCE;
    }
    
    public ServerConnection setNumPorta(int numPorta) {
        this.numPorta = numPorta;
        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        logging("Setado PORTA: "+numPorta);
        return INSTANCE;
    }

    public ServerConnection setNumUsuariosPorSalas(int numUsuariosPorSalas) {
        this.numUsuariosPorSalas = numUsuariosPorSalas;
        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        logging("Setado Númer MAX de usuários: "+this.numUsuariosPorSalas);
        return INSTANCE;
    }

    public Registry getRegistry() {
        return registry;
    }
    
    public int getNumUsuariosPorSalas() {
        return numUsuariosPorSalas;
    }
    
    public List<Room> getRooms(){
        return new ArrayList<>(salas.values());
    }
    
    public void start() throws Exception {
        if (prontoParaIniciar) {
            try {
               this.registry = LocateRegistry.createRegistry(this.numPorta);

                Chat server = new ChatImpl();
                Naming.rebind("rmi://localhost/chat", server);
                logging("============ Servidor Conectado ============");
                
            } catch (Exception e) {
                System.out.println("Exception starting RMI registry:");
                e.printStackTrace();
            }
        } else {
            throw new Exception("Não foram informadas todas informações para iniciar");
        }
    }

}

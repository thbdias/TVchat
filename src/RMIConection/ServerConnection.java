/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIConection;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
    Registry registry;
    ChatImpl serverChat;

    private ServerConnection() {
        super();
    }

    public static ServerConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerConnection();
        }
        return INSTANCE;
    }

    public ServerConnection setNumSalas(int numSalas) {
        this.numSalas = numSalas;

        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        logging("Setado salas: " + this.numSalas);
        return INSTANCE;
    }

    public ServerConnection setNumPorta(int numPorta) {
        this.numPorta = numPorta;
        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        logging("Setado PORTA: " + numPorta);
        return INSTANCE;
    }

    public ServerConnection setNumUsuariosPorSalas(int numUsuariosPorSalas) {
        this.numUsuariosPorSalas = numUsuariosPorSalas;
        this.prontoParaIniciar = !((numSalas == 0) || (numPorta == 0) || (numUsuariosPorSalas == 0));
        logging("Setado Númer MAX de usuários: " + this.numUsuariosPorSalas);
        return INSTANCE;
    }

    public Registry getRegistry() {
        return registry;
    }

    public int getNumUsuariosPorSalas() {
        return numUsuariosPorSalas;
    }

    public void start() throws Exception {
        if (prontoParaIniciar) {
            try {
                registry = LocateRegistry.createRegistry(this.numPorta);

                serverChat = new ChatImpl(numSalas,numUsuariosPorSalas);
                registry.rebind("chat", serverChat);
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

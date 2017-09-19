/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIConection;

import Models.Menssagem;
import Models.Room;
import Models.User;
import RMIConection.Interfaces.Chat;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 912313
 */
public class ClientConnection extends SuperConnection {

    private static ClientConnection INSTANCE;

    private User mainUser;
    private Room usersList;
    private Chat chat;

    private String ip;
    private int porta = 0;
    private boolean prontoParaIniciar;

    private ClientConnection() {
    }

    public static ClientConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientConnection();
        }
        return INSTANCE;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
        this.prontoParaIniciar = !((this.ip == null) || (this.porta == 0) || (this.mainUser == null));
    }

    public User getMainUser() {
        return mainUser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
        this.prontoParaIniciar = !((this.ip == null) || (this.porta == 0) || (this.mainUser == null));
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
        this.prontoParaIniciar = !((this.ip == null) || (this.porta == 0) || (this.mainUser == null));
    }

    public void conectar() throws Exception {

        if (prontoParaIniciar) {
            try {
                chat = (Chat) Naming.lookup("rmi://" + ip + "/chat");
                this.connectionListenerListenerlist.forEach(action -> action.onConectedAdded(mainUser));
                receberMsg();
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new Exception("Não foram informadas todas informações para iniciar");
        }

    }

    /**
     * metodo que fica em loop lendo as msg do cliente e enviando-as ao servidor
     * para sair do loop usa-se a string 'exit'
     *
     * @param msg
     */
    public void enviarMsg(Menssagem msg) {
        try {
            chat.sendMensagem(msg); //enviando msg para servidor
            //this.messageRecievedListenerlist.forEach(action -> action.onMessageRecieved(msg));
        } catch (Exception e) {
            System.out.println("Erro: " + e);
            e.printStackTrace();
        }
    }//end enviar Msg

    /**
     * Este metodo cria uma nova Thread e fica em loop infinido -> lendo as msgs
     * do servidor e apresentando na tela do cliente
     */
    public void receberMsg() {
        new Thread() {
            @Override
            public void run() {
                try {
                    int cont = chat.lerMensagem().size();
                    while (true) {
                        if (chat.lerMensagem().size() > cont) {
                            List<Menssagem> mensagens = chat.lerMensagem();//lendo todas msg disponiveis no servidor
                            Menssagem show_msg = mensagens.get(mensagens.size() - 1); //ultima msg

                            //impedir de msg do emissor apareça na tela do emissor
                            if (!(mainUser.getNick().equals(show_msg.getRemetente().getNick()))) {
                                messageRecievedListenerlist.forEach(action -> action.onMessageRecieved(show_msg));
                            }

                            cont++;
                        }
                    }//end while
                } catch (Exception e) {
                    System.out.println("Erro Thread: " + e);
                }
            }//end run

            private String obterNickFromMsg(String msg) {
                String resp = "";

                for (int i = 1; i < msg.length(); i++) { //msg[0] == '('
                    if (msg.charAt(i) != ')') {
                        resp += msg.charAt(i);
                    } else {
                        i = msg.length();
                    }
                }

                return resp;
            }//end obterNick
        }.start();
    }//end receberMsg

    public void desconectar() {
        // TODO
        this.connectionListenerListenerlist.forEach(action -> action.onDesconectedAdded(mainUser));
    }

    private void addUser(User user) {
        this.usersList.getUsuarios().add(user);
        this.userAddedListenerlist.forEach(action -> action.onUserAdded(user));
    }

    private void removeUser(User user) {
        this.usersList.getUsuarios().remove(user);
        this.userRemovedListenerlist.forEach(action -> action.onUserRemoved(user));
    }

    private void recieveMessage(Menssagem message) {
        this.messageRecievedListenerlist.forEach(action -> action.onMessageRecieved(message));
    }

}

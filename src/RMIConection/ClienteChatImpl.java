/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIConection;

import Models.User;
import RMIConection.Interfaces.ClienteChat;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ClienteChatImpl extends UnicastRemoteObject implements ClienteChat {

    public ClienteChatImpl() throws RemoteException {
    }

    @Override
    public void alertarUsuarioConectado(User user) throws RemoteException {
        ClientConnection.getInstance().addUser(user);
    }

    @Override
    public void alertarUsuarioDesonectado(User user) throws RemoteException {
        ClientConnection.getInstance().removeUser(user);
    }
    
}

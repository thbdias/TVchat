/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIConection.Interfaces;

import Models.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author p805924
 */
public interface ClienteChat extends Remote{
    public void alertarUsuarioConectado(User user) throws RemoteException;
    public void alertarUsuarioDesonectado(User user) throws RemoteException;
}

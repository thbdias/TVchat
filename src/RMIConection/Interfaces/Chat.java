package RMIConection.Interfaces;

import Models.Menssagem;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
    Esta classe(interface) fica no lado/uso do servidor
 */
public interface Chat extends Remote{
    public void sendMensagem(Menssagem msg) throws RemoteException;
    public ArrayList<Menssagem> lerMensagem() throws RemoteException;
}

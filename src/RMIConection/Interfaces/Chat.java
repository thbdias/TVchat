package RMIConection.Interfaces;

import Models.Mensagem;
import Models.Room;
import Models.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
    Esta classe(interface) fica no lado/uso do servidor
 */
public interface Chat extends Remote{
    public void sendMensagem(Mensagem msg) throws RemoteException;
    public ArrayList<Mensagem> lerMensagem() throws RemoteException;
    public boolean temNovaMensagem(User user) throws RemoteException;
    public Mensagem receberMensagem(User user) throws RemoteException;
    public ArrayList<Room> getRooms(User user) throws RemoteException;
    public List<User> getUsuariosDaSala(User user) throws RemoteException;
    public int conectarNaSala(User user, ClienteChat cliente) throws Exception, RemoteException;
    public void desconectar(User user) throws RemoteException;
    
}

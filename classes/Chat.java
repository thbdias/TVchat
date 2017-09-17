import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
    Esta classe(interface) fica no lado/uso do servidor
 */
public interface Chat extends Remote{
    public void sendMensagem(String msg) throws RemoteException;
    public ArrayList<String> lerMensagem() throws RemoteException;
}

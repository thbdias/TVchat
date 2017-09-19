package RMIConection;


import Models.Menssagem;
import RMIConection.Interfaces.Chat;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
    Esta classe roda no lado/uso do servidor
*/
public class ChatImpl extends java.rmi.server.UnicastRemoteObject implements Chat{
    
    private ArrayList<Menssagem> mensagens;
    
    public ChatImpl() throws RemoteException{
        super();
        mensagens = new ArrayList<>();
    }//end construtor

    
    @Override
    public void sendMensagem(Menssagem msg) throws RemoteException {
        mensagens.add(msg);
        System.out.println(msg);
    }

    @Override
    public ArrayList<Menssagem> lerMensagem() throws RemoteException {
        return mensagens;
    }
    
}


import java.rmi.RemoteException;
import java.util.ArrayList;

/**
    Esta classe roda no lado/uso do servidor
*/
public class ChatImpl extends java.rmi.server.UnicastRemoteObject implements Chat{
    
    private ArrayList<String> mensagens;
    
    public ChatImpl() throws RemoteException{
        super();
        mensagens = new ArrayList<String>();
    }//end construtor

    
    @Override
    public void sendMensagem(String msg) throws RemoteException {
        mensagens.add(msg);
        System.out.println(msg);
    }

    @Override
    public ArrayList<String> lerMensagem() throws RemoteException {
        return mensagens;
    }
    
}

package RMIConection;

import Models.Mensagem;
import Models.Room;
import Models.User;
import RMIConection.Interfaces.Chat;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.application.Platform;

/**
 * Esta classe roda no lado/uso do servidor
 */
public class ChatImpl extends java.rmi.server.UnicastRemoteObject implements Chat {

    private Map<Integer, Room> salas;

    private ArrayList<Mensagem> mensagens;
    
    ChatImpl(Map<Integer, Room> salas) throws RemoteException {
        super();
        this.mensagens = new ArrayList<>();
        this.salas = salas;
        ServerConnection.getInstance().logging("Instanciado classe de ChatImpl");
    }

    @Override
    public void sendMensagem(Mensagem msg) throws RemoteException {
        mensagens.add(msg);
        salas.get(msg.getRemetente().getRoomId())
                .getMensagens()
                .entrySet()
                .forEach(action -> {
                    action.getValue().add(msg);
                });
        ServerConnection.getInstance().logging(msg.getRemetente().getNick() + " envia mensagem: \"" + msg.getMensagem() + "\", para: " + (msg.getDestinatario() != null ? msg.getDestinatario().getNick() : " TODOS"));
    }

    @Override
    public boolean temNovaMensagem(User user) throws RemoteException {
        Queue<Mensagem> dd = salas.get(user.getRoomId()).getMensagens().get(user.getId());
        return dd.size()>0;
    }

    @Override
    public Mensagem receberMensagem(User user) throws RemoteException {
        return salas.get(user.getRoomId()).getMensagens().get(user.getId()).remove();
    }

    @Override
    public ArrayList<Room> getRooms() throws RemoteException {
        return ServerConnection.getInstance().getRooms();
    }

    @Override
    public int conectarNaSala(User user) throws Exception, RemoteException {
        if (salas.get(user.getRoomId()).getUsuarios().size() <= ServerConnection.getInstance().getNumUsuariosPorSalas()) {
            user.setId(salas.get(user.getRoomId()).getUsuarios().size()+1);
            salas.get(user.getRoomId()).getUsuarios().add(user);
            salas.get(user.getRoomId()).getMensagens().put(user.getId(), new ConcurrentLinkedQueue<>());
            
            Platform.runLater( () -> ServerConnection.getInstance().userAddedListenerlist.forEach(action -> action.onUserAdded(user)));
                    
            return user.getId();
        }
        else{
            throw new Exception("Sala Cheia");
        }
    }

    @Override
    public void desconectar(User user) throws RemoteException {
        salas.get(user.getRoomId()).getUsuarios().remove(user);
        ServerConnection.getInstance().userRemovedListenerlist.forEach(action -> action.onUserRemoved(user));
    }
    
//-------------------------------------------------------
    @Override
    public ArrayList<Mensagem> lerMensagem() throws RemoteException {
        return mensagens;
    }
    
}

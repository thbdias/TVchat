package RMIConection;

import Models.Mensagem;
import Models.Room;
import Models.User;
import RMIConection.Interfaces.Chat;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta classe roda no lado/uso do servidor
 */
public class ChatImpl extends java.rmi.server.UnicastRemoteObject implements Chat {

    private Map<Integer, Room> salas;

    private ArrayList<Mensagem> mensagens;

    public ChatImpl() throws RemoteException {
        super();
        mensagens = new ArrayList<>();
        salas = new HashMap<>();
        ServerConnection.getInstance().logging("Instanciado classe de ChatImpl");
    }//end construtor

    @Override
    public void sendMensagem(Mensagem msg) throws RemoteException {
        mensagens.add(msg);
        ServerConnection.getInstance().logging(msg.getRemetente().getNick() + " envia mensagem: \"" + msg.getMensagem() + "\", para: " + (msg.getDestinatario() != null ? msg.getDestinatario().getNick() : " TODOS"));
    }

    @Override
    public ArrayList<Mensagem> lerMensagem() throws RemoteException {
        return mensagens;
    }

    @Override
    public boolean temNovaMensagem(User user) throws RemoteException {
        return salas.get(user.getRoomId()).getMensagens().size() > 0;
    }

    @Override
    public Mensagem receberMensagem(User user) throws RemoteException {
        return salas.get(user.getRoomId()).getMensagens().get(user.getId()).remove();
    }

    @Override
    public List<Room> getRooms() throws RemoteException {
        return new ArrayList<>(ServerConnection.getInstance().getRooms());
    }

    @Override
    public void conectarNaSala(User user) throws Exception, RemoteException {
        if (salas.get(user.getRoomId()).getUsuarios().size() <= ServerConnection.getInstance().getNumUsuariosPorSalas()) {
            salas.get(user.getRoomId()).getUsuarios().add(user);
        }
        else{
            throw new Exception("Sala Cheia");
        }
    }

    @Override
    public void desconectar(User user) throws RemoteException {
        salas.get(user.getRoomId()).getUsuarios().remove(user);
    }

}

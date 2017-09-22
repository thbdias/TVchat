package RMIConection;

import Models.Mensagem;
import Models.Room;
import Models.User;
import RMIConection.Interfaces.Chat;
import RMIConection.Interfaces.ClienteChat;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 * Esta classe roda no lado/uso do servidor
 */
public class ChatImpl extends java.rmi.server.UnicastRemoteObject implements Chat {

    int numSalas = 0;
    int numUsuariosPorSalas = 0;

    private Map<Integer, Room> salas;
    private ArrayList<Mensagem> mensagens;

    ChatImpl(int numSalas, int numUsuariosPorSalas) throws RemoteException {
        super();
        this.numSalas = numSalas;
        this.numUsuariosPorSalas = numUsuariosPorSalas;
        this.mensagens = new ArrayList<>();
        this.salas = new HashMap<>();
        Platform.runLater(() -> {
            ServerConnection.getInstance().logging("Instanciado classe de ChatImpl");
        });
        for (int i = 0; i < numSalas; i++) {
            this.salas.put(i, new Room(i));
//            this.salas.get(i).getUsuarios().add(
//                    new User("teste " + i, "teste", "teste", i)
//            );
        }
        Platform.runLater(() -> {
            ServerConnection.getInstance().logging("Instanciado " + numSalas + " salas.");
        });
    }

    public int getNumSalas() {
        return numSalas;
    }

    public void setNumSalas(int numSalas) {
        this.numSalas = numSalas;
    }

    public int getNumUsuariosPorSalas() {
        return numUsuariosPorSalas;
    }

    public void setNumUsuariosPorSalas(int numUsuariosPorSalas) {
        this.numUsuariosPorSalas = numUsuariosPorSalas;
    }

    @Override
    public void sendMensagem(Mensagem msg) throws RemoteException {
        mensagens.add(msg);
        salas.get(msg.getRemetente().getRoomId())
                .getMensagens()
                .entrySet()
                .forEach(action -> {
                    if (msg.getDestinatario() == null || action.getKey() == msg.getDestinatario().getId()) // verifica se é mensgagem direta
                    {
                        action.getValue().add(msg);
                    }
                });
        Platform.runLater(() -> {
            ServerConnection.getInstance().logging(msg.getRemetente().getNick() + " envia mensagem: \"" + msg.getMensagem() + "\", para: " + (msg.getDestinatario() != null ? msg.getDestinatario().getNick() : " TODOS"));
        });
    }

    @Override
    public boolean temNovaMensagem(User user) throws RemoteException {
        Queue<Mensagem> dd = salas.get(user.getRoomId()).getMensagens().get(user.getId());
        return dd.size() > 0; // retorna se há mensagens na fila
    }

    @Override
    public Mensagem receberMensagem(User user) throws RemoteException {
        return salas.get(user.getRoomId()).getMensagens().get(user.getId()).remove(); // retorna mensagem da fila
    }

    @Override
    public ArrayList<Room> getRooms(User user) throws RemoteException {
        Platform.runLater(() -> {
            ServerConnection.getInstance().logging(user.getNick() + " requisitou salas.");
        });
        return new ArrayList<>(salas.values()); // retorna salas
    }

    @Override
    public List<User> getUsuariosDaSala(User user) throws RemoteException {
        return salas.get(user.getRoomId()).getUsuarios(); // retorna usuarios da sala
    }

    @Override
    public int conectarNaSala(User user, ClienteChat cliente) throws Exception, RemoteException {
        if (salas.get(user.getRoomId()).getUsuarios().size() <= ServerConnection.getInstance().getNumUsuariosPorSalas()) {
            
            user.setId(salas.get(user.getRoomId()).getUsuariosConectados()); // atribui um id ao usuairo
            
            salas.get(user.getRoomId()).getUsuarios().add(user); // adiciona usuario a lista de usuarios
            salas.get(user.getRoomId()).getMensagens().put(user.getId(), new ConcurrentLinkedQueue<>()); // cria uma fila de mensagens para o usuario
            salas.get(user.getRoomId()).getClients().add(cliente);

            salas.get(user.getRoomId()).atualizarNumUsuarios();

            salas.get(user.getRoomId()).getClients().forEach(cli -> {
                try {
                    cli.alertarUsuarioConectado(user);
                } catch (RemoteException ex) {
                    Platform.runLater(() -> {
                        ServerConnection.getInstance().logging("Avisando usuarios da sala: " + user.getRoomId() + " sobre novo Usurio.");
                    });
                }
            });

            Platform.runLater(() -> {
                ServerConnection.getInstance().userAddedListenerlist.forEach(action -> action.onUserAdded(user));
                ServerConnection.getInstance().logging(user.getNick() + " entrou na sala: " + user.getRoomId());
            });

            return user.getId(); // retorna novo id do usuario
        } else {
            throw new Exception("Sala Cheia");
        }
    }

    @Override
    public void desconectar(User user) throws RemoteException {
       
        salas.get(user.getRoomId()).getUsuarios().remove(user); // remove usuario da lista de usuarios
        salas.get(user.getRoomId()).getMensagens().remove(user.getId()); // remove lista de mensagens do usuario
        salas.get(user.getRoomId()).getClients().remove(user.getId());
        
        salas.get(user.getRoomId()).atualizarNumUsuarios();
        
        salas.get(user.getRoomId()).getClients().forEach(cli -> {
            try {
                cli.alertarUsuarioDesonectado(user);
            } catch (RemoteException ex) {
                Platform.runLater(() -> {
                    ServerConnection.getInstance().logging("Avisando usuarios da sala: " + user.getRoomId() + " sobre novo Usurio.");
                });
            }
        });

        Platform.runLater(() -> {
            ServerConnection.getInstance().logging(user.getNick() + " Desconectou.");
            ServerConnection.getInstance().userRemovedListenerlist.forEach(action -> action.onUserRemoved(user));
        });
    }

//-------------------------------------------------------
    @Override
    public ArrayList<Mensagem> lerMensagem() throws RemoteException {
        return mensagens;
    }

}

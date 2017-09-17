import java.rmi.Naming;

public class Servidor {    
    
    public Servidor(){
        try{
            Chat server = new ChatImpl();
            Naming.rebind("rmi://localhost/chat", server);
            System.out.println("============ Servidor Conectado ============");
        }
        catch(Exception e){
            System.out.println("Erro Servidor: " + e);
        }
    }//end construtor    
    
    
    public static void main(String args[]){
        new Servidor();
    }
    
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.ArrayList;
import java.lang.*;


public class Cliente {
   
    private static String nick;
    private static int id;
    private static String msg;
    private static ArrayList<String> mensagens;
    private static BufferedReader in;
    private static Chat chat;

    /**
        metodo que fica em loop lendo as msg do cliente e enviando-as ao servidor
        para sair do loop usa-se a string 'exit'
     */
    private static void enviarMsg(){
        try{
            //System.out.print("msg: ");
            msg = in.readLine();

            while(!(msg.equals("exit"))){                
                chat.sendMensagem("("+nick + ") diz: " + msg); //enviando msg para servidor
                //mensagens = chat.lerMensagem(); //lendo todas msg disponiveis no servidor
                //System.out.println(mensagens.get(mensagens.size()-1)); //apresentando a ultima msg no servidor
                //System.out.print("msg: ");
                msg = in.readLine();
            }      
            chat.sendMensagem("("+nick+") DESCONECTADO!"); //enviando msg para servidor      
        }
        catch(Exception e){
            System.out.println("Erro: " + e);
        }
    }//end enviar Msg


    /**
        Este metodo cria uma nova Thread e 
        fica em loop infinido -> lendo as msgs do servidor e apresentando na tela do cliente
     */
    private static void receberMsg(){        
        new Thread (){
            @Override
            public void run(){
                try{
                    int cont = chat.lerMensagem().size();
                    String show_msg = ""; //mensagem a ser apresentada na tela
                    while(true){
                        if (chat.lerMensagem().size() > cont){
                            mensagens = chat.lerMensagem();//lendo todas msg disponiveis no servidor
                            show_msg = mensagens.get(mensagens.size()-1); //ultima msg

                            //impedir de msg do emissor apareça na tela do emissor
                            if (!(nick.equals(obterNickFromMsg(show_msg)))){ 
                                System.out.println(show_msg);//apresentando a ultima msg
                            }

                            cont++;
                        }
                    }//end while
                }
                catch(Exception e){
                    System.out.println("Erro Thread: " + e);
                }   
            }//end run


            private String obterNickFromMsg(String msg){
                String resp = "";

                for(int i = 1; i < msg.length(); i++){ //msg[0] == '('
                    if (msg.charAt(i) != ')')    
                        resp += msg.charAt(i);
                    else
                        i = msg.length();
                }

                return resp;
            }//end obterNick
        }.start();             
    }//end receberMsg


    

    
    public static void main (String args []){
        try{            
            chat = (Chat) Naming.lookup("rmi://localhost/chat");
            in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("nick: ");
            nick = in.readLine();
            
            receberMsg();
            enviarMsg();              
            System.exit(0);         
        }
        catch(Exception e){
            System.out.println("Erro Cliente: " + e);
        }
    }//end main
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author 912313
 */
public class Menssagem implements Serializable {

    private String mensagem;
    private User remetente;
    private User destinatário;

    public Menssagem(String mensagem, User remetente) {
        this.mensagem = mensagem;
        this.remetente = remetente;
    }
    public Menssagem(String mensagem, User remetente, User destinatario) {
        this.mensagem = mensagem;
        this.remetente = remetente;
        this.destinatário = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public User getRemetente() {
        return remetente;
    }

    public User getDestinatário() {
        return destinatário;
    }
    
    @Override
    public String toString() {
        return String.format("@%s: %s", this.remetente.getNick(), this.mensagem);
    }
}

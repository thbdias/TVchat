/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIConection.Interfaces;

import Models.Mensagem;

/**
 *
 * @author 939247
 */
public interface MessageRecievedListener {

    public void onMessageRecieved(Mensagem message);
    
}

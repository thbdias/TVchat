/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIConection.Interfaces;

import Models.User;

/**
 *
 * @author 939247
 */
public interface ConnectionListener {

    public void onConectedAdded(User user);

    public void onDesconectedAdded(User user);
    
}

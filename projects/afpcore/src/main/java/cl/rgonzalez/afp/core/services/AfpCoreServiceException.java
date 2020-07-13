/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core.services;

/**
 *
 * @author rgonzalez
 */
public class AfpCoreServiceException extends Exception {

    public AfpCoreServiceException(String string) {
        super(string);
    }

    public AfpCoreServiceException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
}

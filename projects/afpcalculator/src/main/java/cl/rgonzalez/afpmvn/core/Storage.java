/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.core;

import cl.rgonzalez.afpmvn.core.Database;
import cl.rgonzalez.afpmvn.run.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author administrador
 */
public class Storage {

//    private File dir = new File("storage");
//    private File file = new File("afp.ser");
    public Storage() {
//        if (!dir.exists()) {
//            dir.mkdir();
//        }
    }

    public void persist(File dir, Database db) {
        ObjectOutputStream stream = null;
        try {
            stream = new ObjectOutputStream(new FileOutputStream(new File(dir, "afp.ser")));
            stream.writeObject(db);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    
    public Database restore() {
        return restore(Utils.DB);
    }

    public Database restore(File dir) {
        ObjectInputStream stream = null;
        try {
            stream = new ObjectInputStream(new FileInputStream(new File(dir, "afp.ser")));
            return (Database) stream.readObject();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}

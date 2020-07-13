/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiFrameMainActionClose extends AbstractAction {

    @Autowired
    AfpUiFrameMain frame;

    public AfpUiFrameMainActionClose() {
        putValue(NAME, "Cerrar");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
    }

}

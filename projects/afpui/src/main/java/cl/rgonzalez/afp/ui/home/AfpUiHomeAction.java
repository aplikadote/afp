/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.home;

import cl.rgonzalez.afp.ui.AfpUiFrameMain;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiHomeAction extends AbstractAction {

    @Autowired
    AfpUiFrameMain frame;
    @Autowired
    AfpUiHomePanelModel model;

    public AfpUiHomeAction() {
        putValue(NAME, "Inicio");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.updateView(model.init());
    }

}

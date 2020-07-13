/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui.simulacion;

import cl.rgonzalez.afp.ui.AfpUiFrameMain;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiSimulacionAction extends AbstractAction {

    @Autowired
    AfpUiFrameMain frame;
    @Autowired
    AfpUiSimulacionPanelModel model;

    public AfpUiSimulacionAction() {
        putValue(NAME, "Simulacion");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.updateView(model.init());
    }

}

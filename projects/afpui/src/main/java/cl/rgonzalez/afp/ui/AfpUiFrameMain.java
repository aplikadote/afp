/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import cl.rgonzalez.afp.ui.home.AfpUiHomeAction;
import cl.rgonzalez.afp.ui.simulacion.AfpUiSimulacionAction;
import cl.rgonzalez.afp.ui.rentabilidad.AfpUiRentabilidadAction;
import com.alee.laf.toolbar.WebToolBar;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfpUiFrameMain extends JFrame {

    @Autowired
    AfpUiFrameMainActionClose actionClose;
    @Autowired
    AfpUiHomeAction actionHome;
    @Autowired
    AfpUiRentabilidadAction actionRentabilidad;
    @Autowired
    AfpUiSimulacionAction actionSimulacion;
    //
    private JPanel panelToolbarArea;
    private JPanel panelCenter;

    public AfpUiFrameMain() {
        super("MainFrame");
    }

    public void init() {
        JMenuBar menubar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        menubar.add(menuArchivo);
        menuArchivo.add(new JMenuItem(actionHome));
        menuArchivo.add(new JMenuItem(actionClose));

        JMenu menuReportes = new JMenu("Reportes");
        menuReportes.add(new JMenuItem(actionRentabilidad));
        menuReportes.add(new JMenuItem(actionSimulacion));
        menubar.add(menuReportes);

        this.setJMenuBar(menubar);

        this.panelToolbarArea = new JPanel();
        this.panelToolbarArea.setLayout(new BoxLayout(panelToolbarArea, BoxLayout.LINE_AXIS));
        
        WebToolBar webToolBar = new WebToolBar();
        webToolBar.add(actionHome);
        webToolBar.add(actionRentabilidad);
        webToolBar.add(actionSimulacion);
        this.panelToolbarArea.add(webToolBar);

        this.panelCenter = new JPanel();
        this.panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));

        this.setLayout(new BorderLayout());
        this.add(panelToolbarArea, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        actionHome.actionPerformed(null);
    }

    public void updateView(JPanel panel) {
        panelCenter.removeAll();
        panelCenter.add(panel);
        panelCenter.updateUI();
    }

    public void showMe() {
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

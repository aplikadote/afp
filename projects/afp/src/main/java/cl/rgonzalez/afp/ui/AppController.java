/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    
    @RequestMapping("/")
    public String analisisPorFondo() {
        return "analisisPorFondo";
    }
    
    @RequestMapping("/analisisPorAFP")
    public String analisisPorAFP() {
        return "analisisPorAFP";
    }
}

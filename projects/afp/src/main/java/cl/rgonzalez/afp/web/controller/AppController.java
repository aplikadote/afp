/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String home() {
//        return "rentabilidad";
//        return "simulacion";
        return "home";
    }

//    @GetMapping("/graficos/{webpage}")
//    public String analisisMensualFondo(@PathVariable(name = "webpage") String webpage) {
//        return "graficos/" + webpage;
//    }
//
//    @GetMapping("/database/{webpage}")
//    public String data(@PathVariable(name = "webpage") String webpage) {
//        return "database/" + webpage;
//    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author aplik
 */
public class BrowserRunner {

    public void run() {
        run("http://localhost:8081");
    }

    public void run(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            String[] command;

            String operatingSystemName = System.getProperty("os.name").toLowerCase();
            if (operatingSystemName.indexOf("nix") >= 0 || operatingSystemName.indexOf("nux") >= 0) {
                String[] browsers = {"opera", "google-chrome", "epiphany", "firefox", "mozilla", "konqueror", "netscape", "links", "lynx"};
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 0; i < browsers.length; i++) {
                    if (i == 0) {
                        stringBuffer.append(String.format("%s \"%s\"", browsers[i], url));
                    } else {
                        stringBuffer.append(String.format(" || %s \"%s\"", browsers[i], url));
                    }
                }
                command = new String[]{"sh", "-c", stringBuffer.toString()};
            } else if (operatingSystemName.indexOf("win") >= 0) {
                command = new String[]{"rundll32 url.dll,FileProtocolHandler " + url};

            } else if (operatingSystemName.indexOf("mac") >= 0) {
                command = new String[]{"open " + url};
            } else {
                System.out.println("an unknown operating system!!");
                return;
            }

            try {
                if (command.length > 1) {
                    runtime.exec(command); // linux
                } else {
                    runtime.exec(command[0]); // windows or mac
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.web.old;

import java.util.Objects;

/**
 *
 * @author aplik
 */
public class AppAfp implements Comparable<AppAfp> {

    public static final AppAfp EMPTY = new AppAfp();
    private int id;
    private String name;

    public AppAfp() {
        this.id = -1;
        this.name = "";
    }

    public AppAfp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AppYear)) {
            return false;
        }

        return id == ((AppYear) obj).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(AppAfp o) {
        return id - o.id;
    }
}

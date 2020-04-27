/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.ui;

import java.util.Objects;

/**
 *
 * @author aplik
 */
public class AppYear implements Comparable<AppYear> {

    public static final AppYear EMPTY = new AppYear();
    //
    private int id;
    private String name;

    public AppYear() {
        this.id = -1;
        this.name = "";
    }
    
    public AppYear(int id) {
       this.id= id;
       this.name = Integer.toString(id);
    }

    public AppYear(int id, String name) {
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
    public int compareTo(AppYear o) {
        return id - o.id;
    }

}

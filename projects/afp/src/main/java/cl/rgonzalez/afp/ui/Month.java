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
public class Month implements Comparable<Month> {

    public static final Month EMPTY = new Month();
    private int id;
    private String name;

    public Month() {
        this.id = -1;
        this.name = "";
    }

    public Month(int id, String name) {
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
        if (!(obj instanceof Year)) {
            return false;
        }

        return id == ((Year) obj).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(Month o) {
        return id - o.id;
    }
}

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
public class Year implements Comparable<Year> {

    public static final Year EMPTY = new Year();
    //
    private int id;
    private String name;

    public Year() {
        this.id = -1;
        this.name = "";
    }
    
    public Year(int id) {
       this.id= id;
       this.name = Integer.toString(id);
    }

    public Year(int id, String name) {
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
    public int compareTo(Year o) {
        return id - o.id;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author dmt1
 */
public class DTOhistory {
    private int id;
    private String arguments;

    public DTOhistory(History history) {
        this.id = history.getId();
        this.arguments = history.getArguments();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "DTOhistory{" + "id=" + id + ", arguments=" + arguments + '}';
    }
    
}

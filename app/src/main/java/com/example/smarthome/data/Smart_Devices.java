package com.example.smarthome.data;

import java.io.Serializable;

public class Smart_Devices implements Serializable {

    private String name;
    private int ID;
    private String description;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int type;

    public Smart_Devices() {
    }

    public Smart_Devices(String name, String description, int type) {
        this.description = description;
        this.name = name;
        this.type = type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
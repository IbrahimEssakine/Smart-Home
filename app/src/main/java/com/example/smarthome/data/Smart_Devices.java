package com.example.smarthome.data;

import android.media.Image;

import java.io.Serializable;

public class Smart_Devices implements Serializable {

    private String name;
    private int ID;
    private int iconOff;
    private int iconOn;

    private String description;
    private int type;
    private int signal=3;
    private int state;
    private int port;



    public Smart_Devices() {
    }

    public Smart_Devices(String name, int ID, int type, int signal) {
        this.name = name;
        this.ID = ID;
        this.type = type;
        this.signal = signal;
    }

    public Smart_Devices(String name, int ID, String description, int type, int signal, int port,int state) {
        this.name = name;
        this.ID = ID;
        this.description = description;
        this.type = type;
        this.signal = signal;
        this.port = port;
        this.state = state;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIconOff() {return iconOff;}

    public void setIconOff(int iconOff) {this.iconOff = iconOff;}

    public int getIconOn() {return iconOn;}
    public void setIconOn(int iconOn) {this.iconOn = iconOn;}
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getSignal() {
        return signal;
    }
    public void setSignal(int signal) {
        this.signal = signal;
    }
    public int getState() {return state;}
    public void setState(int state) {
        this.state = state;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
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
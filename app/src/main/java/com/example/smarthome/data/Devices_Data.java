package com.example.smarthome.data;
import android.graphics.drawable.Drawable;

import com.example.smarthome.R;

import java.util.ArrayList;
import java.util.List;

public class Devices_Data {
    List<Smart_Devices> devicesList = new ArrayList<>();
    public Devices_Data(){

    }
    public void addDevice(Smart_Devices smart_devices){
        devicesList.add(smart_devices);
    }
    public List<Smart_Devices> getDevicesList() {

        Smart_Devices Led = new Smart_Devices();
        Led.setID(0);
        Led.setName("Led");
        Led.setType(0);
        Led.setSignal(0);
        Led.setIconOff(R.drawable.outline_light_);
        Led.setIconOn(R.drawable.device_11);
        devicesList.add(0,Led);

        Smart_Devices GazSensor = new Smart_Devices();
        GazSensor.setID(1);
        GazSensor.setName("GazSensor");
        GazSensor.setSignal(1);
        GazSensor.setType(0);
        GazSensor.setIconOff(R.drawable.baseline_smartphone_24);
        devicesList.add(1,GazSensor);

        Smart_Devices SmokeSensor = new Smart_Devices();
        SmokeSensor.setID(2);
        SmokeSensor.setName("SmokeSensor");
        SmokeSensor.setType(0);
        SmokeSensor.setSignal(1);
        SmokeSensor.setIconOff(R.drawable.baseline_smartphone_24);
        devicesList.add(2,SmokeSensor);

        Smart_Devices PresenceSensor = new Smart_Devices();
        PresenceSensor.setName("PresenceSensor");
        PresenceSensor.setID(3);
        PresenceSensor.setType(0);
        PresenceSensor.setSignal(0);
        PresenceSensor.setIconOff(R.drawable.baseline_smartphone_24);
        devicesList.add(3,PresenceSensor);

        Smart_Devices ServoMotor = new Smart_Devices();
        ServoMotor.setID(4);
        ServoMotor.setName("ServoMotor");
        ServoMotor.setType(1);
        ServoMotor.setSignal(0);
        ServoMotor.setIconOff(R.drawable.baseline_smartphone_24);
        devicesList.add(4,ServoMotor);

        Smart_Devices Other = new Smart_Devices();
        Other.setID(5);
        Other.setName("Other");
        Other.setType(3);
        Other.setIconOff(R.drawable.google_icon);
        devicesList.add(5,Other);
        return devicesList;
    }

}


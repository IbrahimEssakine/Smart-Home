package com.example.smarthome.data;
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
        devicesList.add(0,Led);

        Smart_Devices GazSensor = new Smart_Devices();
        GazSensor.setID(1);
        GazSensor.setName("GazSensor");
        GazSensor.setType(0);
        devicesList.add(1,GazSensor);

        Smart_Devices SmokeSensor = new Smart_Devices();
        SmokeSensor.setID(2);
        SmokeSensor.setName("SmokeSensor");
        SmokeSensor.setType(0);
        devicesList.add(2,SmokeSensor);

        Smart_Devices PresenceSensor = new Smart_Devices();
        PresenceSensor.setName("PresenceSensor");
        PresenceSensor.setID(3);
        PresenceSensor.setType(0);
        devicesList.add(3,PresenceSensor);

        Smart_Devices ServoMotor = new Smart_Devices();
        ServoMotor.setID(4);
        ServoMotor.setName("ServoMotor");
        ServoMotor.setType(1);
        devicesList.add(4,ServoMotor);
        return devicesList;
    }

}


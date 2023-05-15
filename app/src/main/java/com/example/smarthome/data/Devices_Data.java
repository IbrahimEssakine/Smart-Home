package com.example.smarthome.data;
import java.util.ArrayList;
import java.util.List;

public class Devices_Data {

    public static List<Smart_Devices> getDevicesList() {
        List<Smart_Devices> devicesList = new ArrayList<>();
        Smart_Devices Led = new Smart_Devices();
        Led.setID(1);
        Led.setName("Led");
        Led.setType(1);
        devicesList.add(Led);

        Smart_Devices GazSensor = new Smart_Devices();
        GazSensor.setID(2);
        GazSensor.setName("GazSensor");
        GazSensor.setType(0);
        devicesList.add(GazSensor);

        Smart_Devices SmokeSensor = new Smart_Devices();
        SmokeSensor.setID(3);
        SmokeSensor.setName("SmokeSensor");
        SmokeSensor.setType(0);
        devicesList.add(SmokeSensor);

        Smart_Devices PresenceSensor = new Smart_Devices();
        Led.setName("PresenceSensor");
        PresenceSensor.setID(4);
        Led.setType(0);
        devicesList.add(Led);

        Smart_Devices ServoMotor = new Smart_Devices();
        ServoMotor.setID(5);
        Led.setName("ServoMotor");
        Led.setType(1);
        devicesList.add(ServoMotor);

        return devicesList;
    }

}


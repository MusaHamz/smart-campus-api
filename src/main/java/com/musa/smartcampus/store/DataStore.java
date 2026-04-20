package com.musa.smartcampus.store;

import com.musa.smartcampus.model.Room;
import com.musa.smartcampus.model.Sensor;
import com.musa.smartcampus.model.SensorReading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataStore {

    public static final Map<String, Room> ROOMS =
            Collections.synchronizedMap(new HashMap<>());

    public static final Map<String, Sensor> SENSORS =
            Collections.synchronizedMap(new HashMap<>());

    public static final Map<String, List<SensorReading>> READINGS =
            Collections.synchronizedMap(new HashMap<>());

    private DataStore() {
    }

    static {
        Room room = new Room("LIB-301", "Library Quiet Study", 40);
        ROOMS.put(room.getId(), room);

        Sensor sensor = new Sensor("TEMP-001", "Temperature", "ACTIVE", 21.5, room.getId());
        SENSORS.put(sensor.getId(), sensor);

        room.getSensorIds().add(sensor.getId());

        List<SensorReading> sensorReadings = Collections.synchronizedList(new ArrayList<>());
        sensorReadings.add(new SensorReading("READ-001", System.currentTimeMillis(), 21.5));
        READINGS.put(sensor.getId(), sensorReadings);
    }
}
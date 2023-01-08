package ru.itis.engine;

import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
public class Event {
    public int type;
    //1-move
    // Если событие пришло с сервера - id объекта, к которому применить событие
    // Если событие отправляется на сервев - id объекта источника события
    public int objectId;
    public double[] data;
    public static int END = 999;

    public Event(int type, int objectId, double[] data) {
        this.type = type;
        this.objectId = objectId;
        this.data = data;
    }

    public static Event readEvent(DataInputStream dataInputStream) throws IOException {
        int type = dataInputStream.readInt();
        if(type == Event.END) { return null; }
        int objectId = dataInputStream.readInt();
        double[] buffer = new double[10];
        for(int i = 0; i < 10; i++) {
            buffer[i] = dataInputStream.readDouble();
        }
        return new Event(type, objectId, buffer);
    }

    public static void writeEvent(Event event, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(event.type);
        dataOutputStream.writeInt(event.objectId);
        for(int i = 0; i < 10; i++) {
            dataOutputStream.writeDouble(event.data[i]);
        }
    }

}

package org.example;

import java.util.Date;

public class Avaliation {
    private String type;
    private int ponderation; // Percentage
    private Date dateHour;
    private Room room;

    public Avaliation(String type, int ponderation, Date dateHour, Room room) {
        this.type = type;
        this.ponderation = ponderation;
        this.dateHour = dateHour;
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public int getPonderation() {
        return ponderation;
    }

    public Date getDateHour() {
        return dateHour;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Avaliation{" +
                "type='" + type + '\'' +
                ", ponderation=" + ponderation +
                ", dateHour=" + dateHour +
                ", room='" + room + '\'' +
                '}';
    }
}

package com.example.centre_formation.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Event")
public class Event implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idEvent;

    @ColumnInfo(name = "eventName")
    private String eventName;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "numberOfPeople")
    private String numberOfPeople;
    @ColumnInfo(name = "address")
    private String address;

    public Event() {
    }

    public Event(String eventName, String description, String numberOfPeople, String address) {
        this.eventName = eventName;
        this.description = description;
        this.numberOfPeople = numberOfPeople;
        this.address = address;
    }
    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Event"+ idEvent +
                ": eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", numberOfPeople='" + numberOfPeople + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

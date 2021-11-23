package edu.model.order;

import lombok.Value;


@Value
public class Path {

    Integer origin;

    Integer destination;

    public static String toCSVHeader() {
        return "Origin,Destination";
    }

    public String toCSV() {
        return String.format("%s,%s", origin, destination);
    }
}



package edu.model.order;

import lombok.Value;
import lombok.With;


@Value
@With
public class Path {

    Integer origin;

    Integer destination;

    @Override
    public String toString() {
        return "Path(" + origin + "->" + destination + ')';
    }
}



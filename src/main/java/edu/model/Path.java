package edu.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Embeddable
public class Path {

    public Integer origin;

    public Integer destination;

    public Path(Integer origin, Integer destination) {
        this.origin = origin;
        this.destination = destination;
    }

    @Deprecated
    protected Path() {
    }

    @Override
    public String toString() {
        return "Path{" +
                "origin=" + origin +
                ", destination=" + destination +
                '}';
    }

    @Transient
    public boolean isRightWay() {
        return origin < destination;
    }
}



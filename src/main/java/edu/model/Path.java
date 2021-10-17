package edu.model;

import javax.persistence.Embeddable;


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
}



package edu.model.delay;

public final class BlurLogarithmic implements Blur {

    @Override
    public Double blur(Double x) {
        return -Math.log(Math.random()) * x;
    }
}

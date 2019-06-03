package cz.intercity.smellsphishy.ai.utils;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixRandom implements MathFunction {

    @Override
    public double getNumResult(double arg) {
        //Just get something stupid, it will (eventually) start working...
        return (ThreadLocalRandom.current().nextDouble() % 10000 + 1)/10000-0.5;
    }
}

package cz.intercity.smellsphishy.ai.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixRandom implements MathFunction {

    @Override
    public double getNumResult(double arg)  {
        //Just get something stupid, it will (eventually) start working...
        Logger log = LoggerFactory.getLogger(MatrixRandom.class);
        double result = (ThreadLocalRandom.current().nextDouble() % 10000 + 1)/10000-0.5;
        log.debug("Random number generated: "+result);
        return result;
    }
}

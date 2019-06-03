package cz.intercity.smellsphishy.ai.utils;

import static java.lang.Math.*;

public class MatrixSigmoidDerivative implements MathFunction {
    @Override
    public double getNumResult(double arg) {
        return(exp(-arg)/(pow(1+exp(-arg), 2)));
    }
}

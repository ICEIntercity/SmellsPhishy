package cz.intercity.smellsphishy.ai.utils;

import static java.lang.Math.exp;

public class MatrixSigmoid implements MathFunction {
    @Override
    public double getNumResult(double arg) {
        return 1/(1+exp(-arg));
    }
}

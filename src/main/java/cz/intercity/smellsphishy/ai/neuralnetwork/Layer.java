package cz.intercity.smellsphishy.ai.neuralnetwork;

import cz.intercity.smellsphishy.ai.utils.Matrix;
import cz.intercity.smellsphishy.ai.utils.MatrixRandom;

public class Layer {

    public Layer(int inputs, int outputs){
        this.weights = new Matrix(inputs, outputs);
        this.bias = new Matrix(1, outputs);

        //Introduce some entropy
        weights.applyFunction(new MatrixRandom());
    }

    public Matrix weights;
    public Matrix bias;
}

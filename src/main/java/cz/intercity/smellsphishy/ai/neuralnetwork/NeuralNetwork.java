package cz.intercity.smellsphishy.ai.neuralnetwork;

import cz.intercity.smellsphishy.ai.training.TrainingCase;
import cz.intercity.smellsphishy.ai.training.TrainingData;
import cz.intercity.smellsphishy.ai.utils.Matrix;
import cz.intercity.smellsphishy.ai.utils.MatrixSigmoid;
import cz.intercity.smellsphishy.ai.utils.MatrixSigmoidDerivative;

//Probably complete bullshit
//TODO: Prepare training data (somehow)
public class NeuralNetwork implements java.io.Serializable {


    public NeuralNetwork(int inputNeurons, int hiddenNeurons, int outputNeurons, double learningRate){
        this.sampleCount = 0;
        this.learningRate = learningRate;

        //Init layers + provide some entropy
        this.hiddenLayer = new Layer(inputNeurons, hiddenNeurons);
        this.outputLayer = new Layer(hiddenNeurons, outputNeurons);

        //No need to init x, h and y (hopefully)
    }

    //Operating with two layers for now.
    private Layer hiddenLayer;
    private Layer outputLayer;

    /*
    I wasn't exactly sure what I was doing at this point - These are sort of global variables, but the results
    of each output computation must be preserved for backpropagation
    */
    private Matrix x;
    private Matrix h;
    private Matrix y;

    private double learningRate;
    private int sampleCount;

    public Matrix computeOutput(Matrix input){
        x = input;
        h = input.dot(hiddenLayer.weights).add(hiddenLayer.bias).applyFunction(new MatrixSigmoid());
        y = h.dot(outputLayer.weights).add(outputLayer.bias).applyFunction(new MatrixSigmoid());

        return y;
    }

    public void learn(Matrix expectedOutput){
        
        //Compute cost function result - using sigmoid and d/dx sigmoid
        Matrix bias_hiddenLayer = y.subtract(expectedOutput).multiply(h.dot(outputLayer.weights).add(outputLayer.bias)
                .applyFunction(new MatrixSigmoidDerivative()));

        Matrix bias_inputLayer = bias_hiddenLayer.dot(outputLayer.weights.transpose()).multiply(
                x.dot(hiddenLayer.weights).add(hiddenLayer.bias).applyFunction(new MatrixSigmoidDerivative()));

        Matrix weight_hiddenLayer = h.transpose().dot(bias_hiddenLayer);
        Matrix weight_inputLayer = x.transpose().dot(bias_inputLayer);

        //Update coefficients
        hiddenLayer.weights = hiddenLayer.weights.subtract(weight_inputLayer.multiply(learningRate));
        hiddenLayer.bias = hiddenLayer.bias.subtract(bias_hiddenLayer.multiply(learningRate));

        outputLayer.weights = outputLayer.weights.subtract(weight_hiddenLayer.multiply(learningRate));
        outputLayer.bias = outputLayer.bias.subtract(bias_hiddenLayer.multiply(learningRate));
    }

    public void train(TrainingData data){
        for(TrainingCase trainingCase : data.getTrainingCases()){

            //load training data
            Matrix input = new Matrix(trainingCase.input.getAsArray());
            Matrix desiredOutput = new Matrix(trainingCase.desiredResult);

            Matrix output = this.computeOutput(input);
            this.learn(desiredOutput);
        }
    }

}

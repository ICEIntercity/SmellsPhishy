package cz.intercity.smellsphishy.ai.training;

import cz.intercity.smellsphishy.ai.ui.Input;

public class TrainingCase implements java.io.Serializable {

    private Input input;
    private double[] desiredResult;

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public double[] getDesiredResult() {
        return desiredResult;
    }

    public void setDesiredResult(double[] desiredResult) {
        this.desiredResult = desiredResult;
    }
}

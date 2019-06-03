package cz.intercity.smellsphishy.ai;

import cz.intercity.smellsphishy.ai.utils.Matrix;

public class Input {
    public Input(int grammar, int orgReputation, int urgency, int threat, int personalisation, int tech) {
        this.grammar = grammar;
        this.orgReputation = orgReputation;
        this.urgency = urgency;
        this.threat = threat;
        this.personalisation = personalisation;
        this.tech = tech;
    }

    public double[] getAsArray() {
        double result[] = new double[6];
        result[0] = grammar;
        result[1] = orgReputation;
        result[2] = urgency;
        result[3] = threat;
        result[4] = personalisation;
        result[5] = tech;

        return result;
    }

    private double grammar;
    private double orgReputation;
    private double urgency;
    private double threat;
    private double personalisation;
    private double tech;

    public double getGrammar() {
        return grammar;
    }

    public double getOrgReputation() {
        return orgReputation;
    }

    public double getUrgency() {
        return urgency;
    }

    public double getThreat() {
        return threat;
    }

    public double getPersonalisation() {
        return personalisation;
    }

    public double getTech() {
        return tech;
    }
}

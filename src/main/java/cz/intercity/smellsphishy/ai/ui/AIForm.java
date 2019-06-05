package cz.intercity.smellsphishy.ai.ui;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AIForm {

    @NotNull
    @Min(0)
    @Max(10)
    private int grammar;

    @NotNull
    @Min(0)
    @Max(10)
    private int orgReputation;

    @NotNull
    @Min(0)
    @Max(10)
    private int urgency;

    @NotNull
    @Min(0)
    @Max(10)
    private int threat;

    @NotNull
    @Min(0)
    @Max(10)
    private int personalisation;

    @NotNull
    @Min(0)
    @Max(10)
    private int tech;

    public int getGrammar() {
        return grammar;
    }

    public void setGrammar(int grammar) {
        this.grammar = grammar;
    }

    public int getOrgReputation() {
        return orgReputation;
    }

    public void setOrgReputation(int orgReputation) {
        this.orgReputation = orgReputation;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public int getThreat() {
        return threat;
    }

    public void setThreat(int threat) {
        this.threat = threat;
    }

    public int getPersonalisation() {
        return personalisation;
    }

    public void setPersonalisation(int personalisation) {
        this.personalisation = personalisation;
    }

    public int getTech() {
        return tech;
    }

    public void setTech(int tech) {
        this.tech = tech;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AIForm: {")
                .append(grammar).append(", ")
                .append(orgReputation).append(", ")
                .append(urgency).append(", ")
                .append(threat).append(", ")
                .append(personalisation).append(", ")
                .append(tech).append("}");

        return sb.toString();
    }
}

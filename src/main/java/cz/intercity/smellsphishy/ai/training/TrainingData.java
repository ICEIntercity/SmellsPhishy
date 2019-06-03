package cz.intercity.smellsphishy.ai.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingData implements java.io.Serializable {

    public TrainingData(){
        trainingCases = new ArrayList<>();
    }

    public void serialize(String targetFile) throws Exception {
        Logger log = LoggerFactory.getLogger(TrainingData.class);
        try{
            log.debug("Starting serialization of training data...");
            FileOutputStream fileOut = new FileOutputStream(targetFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            log.info("Training data saved to file: " + targetFile);
        }
        catch(Exception e){
            log.error("Exception while serializing training data:" + e.getMessage());
            throw(e);
        }
    }

    //Wanted to call this "Deserialize", but that name is already taken
    public TrainingData loadFromFile(String file) throws Exception{

        TrainingData td = null;
        Logger log = LoggerFactory.getLogger(TrainingData.class);

        try{
            log.debug("Starting serialization of training data...");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            td = (TrainingData) in.readObject();
            in.close();
            fileIn.close();
            log.info("Successfully loaded training data (Source file: " + file + ")");
        }
        catch(Exception e){
            log.error("Exception while deserializing training data: " + e.getMessage());
            throw(e);
        }

        return td;
    }

    public List<TrainingCase> getTrainingCases() {
        return trainingCases;
    }

    void addTrainingCase(TrainingCase trainingCase){
        this.trainingCases.add(trainingCase);
    }

    public void setTrainingCases(List<TrainingCase> trainingCases) {
        this.trainingCases = trainingCases;
    }

    private List<TrainingCase> trainingCases;
}

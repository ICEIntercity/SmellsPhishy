package cz.intercity.smellsphishy.ai.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingData implements java.io.Serializable {

    private static TrainingData trainingData_instance = null;
    private static final String persistenceFile = "persistence/trainingData.ser";
    private static final Logger log = LoggerFactory.getLogger(TrainingData.class);

    private TrainingData(){

        trainingCases = new ArrayList<>();

    }

    public static TrainingData getInstance() throws Exception{
        if(trainingData_instance == null){
            File f = new File(persistenceFile);
            if(f.exists()){
                log.debug("Persistence file found, loading data...");
                try {
                    trainingData_instance = loadFromFile();
                    log.debug("Data load successful: " + trainingData_instance.toString());
                }
                catch(Exception e){
                    log.error("Persistence file loading failed.");
                    throw(e);
                }
            }
            else {
                trainingData_instance = new TrainingData();
            }
        }

        return trainingData_instance;
    }

    public void serialize() throws IOException {

        try{
            log.debug("Starting serialization of training data...");
            FileOutputStream fileOut = new FileOutputStream(persistenceFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            log.info("Training data saved to file: " + persistenceFile);
            log.info("Current status: " + this.toString());
        }
        catch(IOException e){
            log.error("Exception while serializing training data:" + e.getMessage());
            throw(e);
        }
    }

    //Wanted to call this "Deserialize", but that name is already taken
    public static TrainingData loadFromFile() throws Exception{

        TrainingData td = null;
        Logger log = LoggerFactory.getLogger(TrainingData.class);

        try{
            log.debug("Starting deserialization of training data...");
            FileInputStream fileIn = new FileInputStream(persistenceFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            td = (TrainingData) in.readObject();
            in.close();
            fileIn.close();
            log.info("Successfully loaded training data (Source file: " + persistenceFile + ")");
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

    public void addTrainingCase(TrainingCase trainingCase){
        this.trainingCases.add(trainingCase);
    }

    public void setTrainingCases(List<TrainingCase> trainingCases) {
        this.trainingCases = trainingCases;
    }

    @Override
    public String toString(){
        return "TrainingData (" + trainingCases.size() + ((trainingCases.size() == 1) ? " entry)" : " entries)");
    }

    private List<TrainingCase> trainingCases;
}

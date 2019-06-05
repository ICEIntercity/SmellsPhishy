package cz.intercity.smellsphishy.controller;

import cz.intercity.smellsphishy.ai.training.TrainingCase;
import cz.intercity.smellsphishy.ai.training.TrainingData;
import cz.intercity.smellsphishy.ai.ui.AIForm;
import cz.intercity.smellsphishy.ai.neuralnetwork.NeuralNetwork;
import cz.intercity.smellsphishy.ai.ui.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


//Decision engine controller (TODO: Give it a purpose)
@Controller
public class AIController {

    private static Logger log = LoggerFactory.getLogger(AIController.class);

    @GetMapping("/aitest")
    public String phishyNN(Model model) {



        log.trace("AI Form invoked.");
        NeuralNetwork nn = new NeuralNetwork(6, 3, 1, 0.7); // checking if the init works at least
        model.addAttribute("form", new AIForm());
        return "aiform";
    }

    @PostMapping("/aitest")
    public String phishyNNResult(Model model, @Valid @ModelAttribute("form") AIForm form, BindingResult bindingResult, @RequestParam String decision){
        if(bindingResult.hasErrors()){
            return "aiform";
        }

        //Convert to AI digestible form
        Input input = new Input(form);
        TrainingCase trainingCase = new TrainingCase();
        trainingCase.setInput(input);
        double[] result = new double[1];

        if(decision.equals("Malicious!")){
            result[0] = 1;

        }
        else{
            result[0] = 0;
        }

        trainingCase.setDesiredResult(result);
        TrainingData trainingData;

        try {
            trainingData = TrainingData.getInstance();
        }
        catch(Exception e){
            e.printStackTrace();
            return "error";
        }

        trainingData.addTrainingCase(trainingCase);
        try {
            trainingData.serialize();
        }
        catch(Exception e){
            e.printStackTrace();
            log.error("Training data serialization failed: " + e.getMessage());
        }

        return "successPlaceholder";
    }
}

package cz.intercity.smellsphishy.controller;

import cz.intercity.smellsphishy.ai.neuralnetwork.NeuralNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


//Decision engine controller (TODO: Give it a purpose)
@Controller
public class AIController{
    Logger log = LoggerFactory.getLogger(AIController.class);

    @GetMapping("/aitest")
    public String phishyNN(Model model){
        NeuralNetwork nn = new NeuralNetwork(6,3,1,0.7);
        return "index";
    }
}

package cz.intercity.smellsphishy.controller;

import cz.intercity.smellsphishy.analysis.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

public class TicketController{

    public String generateTicket(@ModelAttribute("message") Message message, Model model, SessionStatus status){
        return "ticket";
    }

}

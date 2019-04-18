package cz.intercity.smellsphishy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    Logger log = LoggerFactory.getLogger(DefaultController.class);

    @GetMapping("/")
    public String landingPage(Model model){
        log.trace("Landing Page Invoked");
        return "index";
    }

    @GetMapping("/error")
    public String error(Model model){
        log.trace("Error Page Invoked");
        return "error";
    }
}

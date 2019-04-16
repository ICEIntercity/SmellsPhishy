package cz.intercity.smellsphishy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String landingPage(Model model){
        return "index";
    }

    @GetMapping("/error")
    public String error(Model model){ return "error"; }
}

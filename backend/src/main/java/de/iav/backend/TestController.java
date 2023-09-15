package de.iav.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String itsWorking() {
        return "Fuck it´s running no go back to work";
    }
}

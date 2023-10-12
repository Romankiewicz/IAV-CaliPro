package de.iav.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String itsWorking() {
        return "Fuck it´s running now go back to work";
    }

    @GetMapping("/test1")
    public String isItStillWorking() {
        return "is it still working?";
    }
}

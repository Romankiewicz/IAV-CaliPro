package de.iav.backend.controller;

import de.iav.backend.service.MetrologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrology")
@RequiredArgsConstructor
public class MetrologyController {

    private final MetrologyService metrologyService;


}

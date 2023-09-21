package de.iav.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdService {

    public String generateId(){
        return UUID.randomUUID().toString();
    }
}

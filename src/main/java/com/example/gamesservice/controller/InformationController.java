package com.example.gamesservice.controller;

import com.example.gamesservice.util.SessionValidator;
import com.example.gamesservice.util.counter.VisitCounter;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class InformationController {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private final VisitCounter visitCounter;
    private final SessionValidator sessionValidator;

    public InformationController(VisitCounter visitCounter, SessionValidator sessionValidator) {
        this.visitCounter = visitCounter;
        this.sessionValidator = sessionValidator;
    }

    @GetMapping("/visits")
    public ResponseEntity<Object> getVisitsCounter(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, userAccount -> ResponseEntity.ok(visitCounter.count()));
    }

    @GetMapping("/date")
    public ResponseEntity<Object> getServerDate(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, userAccount -> ResponseEntity.ok(
                DATE_FORMAT.format(new Date(System.currentTimeMillis()))
        ));
    }

}

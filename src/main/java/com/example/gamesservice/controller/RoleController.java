package com.example.gamesservice.controller;

import com.example.gamesservice.util.ApplicationConstants;
import com.example.gamesservice.util.ResponseMessages;
import com.example.gamesservice.util.SessionValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleController {

    private final SessionValidator sessionValidator;

    public RoleController(SessionValidator sessionValidator) {
        this.sessionValidator = sessionValidator;
    }

    @Parameters({
            @Parameter(name = "test", description = "test desc", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",  content = {
                    @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "[\"user\", \"moder\", \"admin\"]", description = "Array of roles")
                    )
            })
    })
    @GetMapping("/roles")
    public ResponseEntity<Object> allRoles(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, userAccount -> {
            if(!userAccount.getRole().equals("admin"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);

            return ResponseEntity.ok(ApplicationConstants.ROLES);
        });
    }

}

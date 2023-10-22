package com.example.gamesservice.controller;

import com.example.gamesservice.util.ResponseMessages;
import com.example.gamesservice.util.SessionValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final SessionValidator sessionValidator;

    public AvatarController(SessionValidator sessionValidator) {
        this.sessionValidator = sessionValidator;
    }

    @PostMapping
    public ResponseEntity<Object> uploadFile(HttpSession httpSession, @RequestBody MultipartFile file) {
        return sessionValidator.validate(httpSession, user -> {
            try {
                file.transferTo(new File("C:/images/avatar/" + user.getId() + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(user.getId());
        });
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> getImage(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, user -> {
            FileSystemResource fileSystemResource = new FileSystemResource("C:/images/avatar/" + user.getId() + ".jpg");
            if(fileSystemResource.exists())
                return ResponseEntity.ok(fileSystemResource);
            return ResponseEntity.status(404).build();
        });
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteImage(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, user -> {
            if(user.getRole().equals("user"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);
            File file = new File("C:/images/avatar/" + user.getId() + ".jpg");
            if(file.exists())
                file.delete();
            return ResponseEntity.ok(user.getId());
        });
    }

}

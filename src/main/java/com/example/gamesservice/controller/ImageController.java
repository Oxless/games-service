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
@RequestMapping("/images")
public class ImageController {

    private final SessionValidator sessionValidator;

    public ImageController(SessionValidator sessionValidator) {
        this.sessionValidator = sessionValidator;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> uploadFile(HttpSession httpSession, @PathVariable String id, @RequestBody MultipartFile file) {
        return sessionValidator.validate(httpSession, user -> {
            if(user.getRole().equals("user"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);
            try {
                file.transferTo(new File("C:/images/" + id + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(user.getId());
        });
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> getImage(HttpSession httpSession, @PathVariable String id) {
        return sessionValidator.validate(httpSession, user -> {
            FileSystemResource fileSystemResource = new FileSystemResource("C:/images/" + id + ".jpg");
            if(fileSystemResource.exists())
                return ResponseEntity.ok(fileSystemResource);
            return ResponseEntity.status(404).build();
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteImage(HttpSession httpSession, @PathVariable String id) {
        return sessionValidator.validate(httpSession, user -> {
            if(user.getRole().equals("user"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);
            File file = new File("C:/images/" + id + ".jpg");
            if(file.exists())
                file.delete();
            return ResponseEntity.ok(user.getId());
        });
    }

}

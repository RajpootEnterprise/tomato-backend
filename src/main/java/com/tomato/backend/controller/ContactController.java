package com.tomato.backend.controller;

import com.tomato.backend.dto.ApiResponse;
import com.tomato.backend.dto.ContactRequest;
import com.tomato.backend.entity.ContactMessage;
import com.tomato.backend.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    // Matches the "Contact Us" page on the frontend
    @PostMapping
    public ResponseEntity<ApiResponse<ContactMessage>> submitMessage(@Valid @RequestBody ContactRequest request) {
        ContactMessage saved = contactService.submitMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Message received, we'll get back to you soon", saved));
    }
}

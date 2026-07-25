package com.tomato.backend.serviceimpl;

import com.tomato.backend.dto.ContactRequest;
import com.tomato.backend.entity.ContactMessage;
import com.tomato.backend.repository.ContactMessageRepository;
import com.tomato.backend.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactMessageRepository contactMessageRepository;

    @Override
    public ContactMessage submitMessage(ContactRequest request) {
        ContactMessage message = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .message(request.getMessage())
                .build();
        return contactMessageRepository.save(message);
    }
}

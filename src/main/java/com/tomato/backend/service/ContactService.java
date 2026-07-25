package com.tomato.backend.service;

import com.tomato.backend.dto.ContactRequest;
import com.tomato.backend.entity.ContactMessage;

public interface ContactService {
    ContactMessage submitMessage(ContactRequest request);
}

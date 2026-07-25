package com.tomato.backend.repository;

import com.tomato.backend.entity.ContactMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactMessageRepository extends MongoRepository<ContactMessage, String> {
}

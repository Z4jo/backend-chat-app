package com.example.backendchatapp.repositary;

import com.example.backendchatapp.entity.TextMessage;
import org.springframework.data.repository.CrudRepository;

public interface TextMessageRepository extends CrudRepository<TextMessage, Long> {
}
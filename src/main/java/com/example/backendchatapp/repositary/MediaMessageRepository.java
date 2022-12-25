package com.example.backendchatapp.repositary;

import com.example.backendchatapp.entity.MediaMessage;
import org.springframework.data.repository.CrudRepository;

public interface MediaMessageRepository extends CrudRepository<MediaMessage, Long> {
}
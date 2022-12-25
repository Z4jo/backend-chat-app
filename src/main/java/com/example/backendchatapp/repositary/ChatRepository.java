package com.example.backendchatapp.repositary;

import com.example.backendchatapp.entity.Chat;
import com.example.backendchatapp.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
	Chat findChatByUsers(User user);
}
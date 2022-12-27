package com.example.backendchatapp.repositary;

import com.example.backendchatapp.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUserName(String UserName);
	Collection<NamesAndId> findAllByUserNameContaining(String substringOfUsername);

	 interface NamesAndId{
		Long getId();
		String getUserName();
	}
}
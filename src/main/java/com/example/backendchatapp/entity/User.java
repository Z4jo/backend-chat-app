package com.example.backendchatapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userDb")
@Getter
@Setter
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column (name = "username", nullable = false, unique = true)
	private String userName;

	@Column (name = "password", nullable = false)
	private String password;

	@JsonIgnore
	@ManyToMany(mappedBy = "users")
	private Collection<Chat> chats = new ArrayList<>();

}
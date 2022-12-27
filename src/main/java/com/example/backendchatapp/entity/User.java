package com.example.backendchatapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_db")
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

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.MERGE, optional = false)
	@JoinColumn(name = "authority_authority_id", nullable = false)
	private Authority authority;

}
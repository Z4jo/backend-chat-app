package com.example.backendchatapp.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@OneToMany(mappedBy = "chat", cascade = CascadeType.MERGE, orphanRemoval = true)
	@OrderBy("date DESC")
	private List<TextMessage> textMessages = new ArrayList<>();

	@OneToMany(mappedBy = "chat", cascade = CascadeType.MERGE, orphanRemoval = true)
	@OrderBy("date DESC")
	private List<MediaMessage> mediaMessages = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "chat_users",
			joinColumns = @JoinColumn(name = "chat_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Collection<User> users = new ArrayList<>();

}
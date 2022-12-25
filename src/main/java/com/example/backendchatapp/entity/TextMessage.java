package com.example.backendchatapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "text_message")
public class TextMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name= "date")
	private Timestamp date;

	@Column(name = "message")
	private String message;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "chat_id")
	private Chat chat;

	@OneToOne(cascade = CascadeType.MERGE, optional = false, orphanRemoval = true)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
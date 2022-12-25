package com.example.backendchatapp.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "media_message")
public class MediaMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "media_message")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type= "org.hibernate.type.ImageType")
	private byte[] blobData;

	@Column( name = "date")
	private Timestamp date;

	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "chat_id")
	private Chat chat;

}
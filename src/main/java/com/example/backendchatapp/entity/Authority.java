package com.example.backendchatapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authority")
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authority_id", nullable = false)
	private Long authority_id;
	@Column(name = "name_of_authority",nullable = false)
	private String nameOfAuthority;

	@OneToMany(mappedBy = "authority", cascade = CascadeType.MERGE, orphanRemoval = true)
	private List<User> users = new ArrayList<>();

}

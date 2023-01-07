package com.example.backendchatapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageConsumersWrapper {
	private List<Long> consumers;
	private String message;
}


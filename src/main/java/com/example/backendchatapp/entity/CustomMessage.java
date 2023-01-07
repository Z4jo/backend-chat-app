package com.example.backendchatapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CustomMessage(@JsonProperty("textMessage") String text,
                            @JsonProperty("image") byte[] image,
                            @JsonProperty("consumerId")Long id,
                            @JsonProperty("header")String header
)
		implements Serializable {
}

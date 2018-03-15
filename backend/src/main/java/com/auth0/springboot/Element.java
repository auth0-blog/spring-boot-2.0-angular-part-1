package com.auth0.springboot;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
class Element {

	@Id
	private String id;

	private String path;

	private String fill;

	private String stroke;

	private int strokeWidth = 1;
}

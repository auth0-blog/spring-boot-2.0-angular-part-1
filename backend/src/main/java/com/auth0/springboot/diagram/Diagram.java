package com.auth0.springboot.diagram;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
class Diagram {

	@Id
	private String id;

	private String title;

	@LastModifiedDate
	private Date lastModifiedDate;

	private List<Element> elements = new ArrayList<>();
}

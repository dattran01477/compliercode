package com.itcode.itcodeweb.model.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(collection = "code_template")
public class CodeTemplate {

	@Id
	private String id;

	@Field("name")
	private String name;

	@Field("func_code")
	private String codeFunction;

	@Field("assert_code")
	private String assertFunction;

	@Field("result_code")
	private String resultFunction;

	public String getTemplate() {
		if (codeFunction == null || assertFunction == null || resultFunction == null) {
			new Throwable("any template content be null");
			return null;
		}
		String result = codeFunction + "\n" + assertFunction + "\n" + resultFunction + "\n";
		return result;
	}
}

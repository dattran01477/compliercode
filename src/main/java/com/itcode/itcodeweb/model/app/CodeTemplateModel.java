package com.itcode.itcodeweb.model.app;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeTemplateModel {

	private Long id;

	private String name;

	@NonNull
	private String codeFunction;

	@NonNull
	private String assertFunction;

	@NonNull
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

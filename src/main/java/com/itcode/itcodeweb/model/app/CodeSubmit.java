package com.itcode.itcodeweb.model.app;

import com.itcode.itcodeweb.data.ComplierEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CodeSubmit {
	protected Code codeSubmit;
	
	protected ComplierEnum language;
}

package com.itcode.itcodeweb.model.app;

import com.itcode.itcodeweb.data.ComplierEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Code {
	ComplierEnum language;
	
	Boolean isRunOnlyCode; 
	
	String code;
}

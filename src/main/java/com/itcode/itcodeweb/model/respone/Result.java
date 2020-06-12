package com.itcode.itcodeweb.model.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Result {
	private Boolean completed;
	
	private String error;
	
	private int failed;
	
	private int passed;
	
}

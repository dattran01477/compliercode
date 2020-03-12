package com.itcode.itcodeweb.model.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TestCase {
	private String descrition;
	
	private String code;
	
	private Boolean isPassed;
}

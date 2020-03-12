package com.itcode.itcodeweb.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileUtilssTest {

	private final String testFilePath = System.getProperty("user.dir") + "/test-file";

	@BeforeEach
	public void beforeTestRemoveFolder() {
		File fileTmp = new File(testFilePath);
		try {
			if (!fileTmp.exists()) {
				fileTmp.mkdir();
			}
		} catch (Exception e) {
			
		}
	}

	@Test
	public void testRemoveFolder() {
			assertEquals(true, FileUtils.removeFile(testFilePath)); 
	}
}

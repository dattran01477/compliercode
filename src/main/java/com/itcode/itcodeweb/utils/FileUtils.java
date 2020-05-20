package com.itcode.itcodeweb.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

	public static void writeFile(String path, String code) {
		try {
			createFile(path);
			FileWriter myWriter = new FileWriter(path);
			myWriter.write(code);
			myWriter.close();
			log.debug("file was saved!");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public static Boolean removeFile(String path) {
		try {

			File f = new File(path); // file to be delete
			org.apache.commons.io.FileUtils.deleteDirectory(f);
			if (f.exists()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void createFile(String path) {
		try {
			File myObj = new File(path);
			myObj.getParentFile().mkdirs();
			if (myObj.createNewFile()) {
				log.info("File created: " + myObj.getName());
			} else {
				log.info("File already exists.");
			}
		} catch (IOException e) {
			log.error("An error occurred.");
			e.printStackTrace();
		}
	}

	public static String readFile(String path) {
		String result = "";
		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				result += myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			log.error("An error occurred.");
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean coppyFolderAndAllowExcuteable(File source, File dest) {
		try {
			org.apache.commons.io.FileUtils.copyDirectory(source, dest);
			setExcuteableAllFileInFolder(dest);
		} catch (IOException e) {
			log.error("----Can coppy folder from:" + source.getPath() + " to:" + dest.getPath() + "----");
			e.printStackTrace();
		}
		return false;
	}

	public static void setExcuteableAllFileInFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				setExcuteableAllFileInFolder(fileEntry);
			} else {
				fileEntry.setExecutable(true, false);
				fileEntry.setReadable(true, false);
			}
		}
	}
}

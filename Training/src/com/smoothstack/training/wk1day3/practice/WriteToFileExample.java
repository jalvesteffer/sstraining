package com.smoothstack.training.wk1day3.practice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author jalveste
 *
 */
public class WriteToFileExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Enter what you want to write to the file.");
		Scanner scan = new Scanner(System.in);
		String data = scan.nextLine();
//		writeWithOutputStream(data);
//		writeWithFileWriter(data);
		writeWithFiles(data);
	}

	/**
	 * @param data
	 */
	private static void writeWithFiles(String data) {
		try {
			Files.write(Paths.get("resources/outputfiles/outputFile.txt"), data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to write data to File");
		}
	}

	/**
	 * @param data
	 */
	private static void writeWithFileWriter(String data) {
		try (FileWriter fileWriter = new FileWriter(new File("resources/outputfiles/outputFile.txt"))) {
			fileWriter.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to write data to File");
		}

	}

	/**
	 * @param data
	 */
	private static void writeWithOutputStream(String data) {
		try (FileOutputStream fos = new FileOutputStream(new File("resources/outputfiles/outputFile.txt"))) {
			fos.write(data.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to write data to File");
		}

	}

}

package com.advent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Logger {
	static String filename = null;
	static String outputFolder = "output/";
	static String shortDateFolder;
	static boolean filesCreated = false;

	private static PrintWriter out;


	public Logger(String filename) {
		Logger.filename = filename;
		clear();
	}
	public static void log(String s) {
		String prefix = "[" + LocalDateTime.now() + "] ";
		if (filename == null) {
			filename = LocalDateTime.now().toString().replaceAll("\\.", ":").replaceAll(":", "_") + ".log";
		}
		System.out.println(prefix + s);
		fileLog(prefix + s);
	}
	public static void log() {
		log("");
	}
	public static void log(Object o) { log(o.toString()); }
	public static void log(Object[] o) {
		String out = "";
		for (Object obj: o) {
			out += obj;
		}
		log(out);
	}
	public static void log(Object[][] objss) {
		for (Object[] objs: objss) {
			log(objs);
		}
	}
	public static void log(int i) {
		log(""+i);
	}
	public static void log(long l) {
		log(""+l);
	}
	public static void log(double d) {
		log(""+d);
	}
	public static void log(boolean b) { log (""+b); }
	public static void log(char c) { log("" + c); }
	public static void log(float f) { log ("" + f); }
	public static void error(Exception e) {
		e.printStackTrace();
		for (StackTraceElement i: e.getStackTrace()) {
			fileLog(i);
		}
	}
	private static void fileLog(Object o) {
		fileSetup();
		try {
			out.println(o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void fileSetup() {
		if (!filesCreated) {
			File file = new File(outputFolder);
			if (!file.exists()) {
				file.mkdir();
			}
			LocalDateTime today = LocalDateTime.now();
			shortDateFolder = Utils.pad(today.getMonthValue() + "", 2, "0") + "_" + Utils.pad(today.getDayOfMonth() + "", 2, "0") + "_" + today.getYear() + "/";
			file = new File(outputFolder + shortDateFolder);
			if (!file.exists()) {
				file.mkdir();
			}
			file = new File(outputFolder + shortDateFolder + filename);
			if (file.exists()) {
				clear();
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					error(e);
				}
			}
			filesCreated = true;
			try {
				FileWriter fw = new FileWriter(outputFolder + shortDateFolder + filename, true);
				BufferedWriter bw = new BufferedWriter(fw);
				out = new PrintWriter(bw);
			} catch (Exception e) {
				error(e);
			}
		}

	}

	public static void closeInput() {
		out.close();
	}

	public static void clear() {
		File f = new File(outputFolder + filename);
		PrintWriter writer;
		try {
			writer = new PrintWriter(f);
			writer.close();
		} catch (FileNotFoundException e) {
			log("Error with clearing contents of file");
			error(e);
		}
		
	}
}

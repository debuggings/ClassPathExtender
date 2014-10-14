package net.debugging.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class ClassPathExtender {
	final static String VersionInfo = "Manifest-Version: 1.0\n";
	final static String ClassPathStart = "Class-Path: ";
	final static String ClassPathLineStart = "\n  ";
	final static String Continuation = "\n ";
	final static String EndOfFile = "\n\n";
	final static String ManifestPath = "META-INF/MANIFEST.MF";
	final static String JarFileSuffix = "-index.jar";
	final static int MaxLineLength = 65;

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			usage();
			return;
		}
		if (args[0].equals(".")) {
			createFile(new File(""));
		} else {
			File f = new File(args[0]);
			if (f == null || !f.exists() || !f.isDirectory()) {
				System.out
						.println("Folder does not exist or is not a directory:"
								+ args[0]);
			} else {
				createFile(f);
			}
		}
	}

	private static void usage() {
		System.out.println("USAGE:java -jar ClassPathExtender.jar FOLDER_PATH");
		System.out
				.println("  Where: FOLDER_PATH is \".\"(without quotes) or a folder path");
	}

	private static void doneUsage(String fileName) {
		System.out.println("jar file generated successfully:" + fileName);
		System.out.println("please put this file into the jvm classpath");
	}

	private static void pathConfirm(File path) {
		System.out.println("Listing jars in folder:" + path.getAbsolutePath());
	}

	private static void noJarFound(String fileName) {
		System.out.println("no jar file found in:" + fileName);
	}

	private static String readNewName(String fileName, String str)
			throws IOException {
		BufferedReader strin = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println("jar file exists£º" + fileName + str + JarFileSuffix);
		System.out.print("please enter a new part after '" + fileName
				+ "' before '" + JarFileSuffix + ":");
		return strin.readLine();
	}

	private static void createFile(File path) throws IOException {
		path = new File(path.toURI().normalize());
		String fileName = path.getAbsoluteFile().getName();
		File f = new File(fileName + JarFileSuffix);
		String str = "";
		while (f.exists()) {
			str = readNewName(fileName, str);
			f = new File(fileName + str + JarFileSuffix);
		}
		f.createNewFile();
		fillFileContent(f, path);
	}

	private static void fillFileContent(File file1, File path)
			throws FileNotFoundException, IOException {
		Map<String, byte[]> manifest = new HashMap<String, byte[]>();
		StringBuilder fileContent = new StringBuilder();

		pathConfirm(path);
		final String jarFileName = file1.getName();
		File[] files = path.getAbsoluteFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// System.out.println(name);
				if (!new File(dir.getAbsolutePath() + "\\" + name)
						.isDirectory()
						&& name.endsWith(".jar")
						&& (!name.equals(jarFileName))) {
					return true;
				}
				return false;
			}
		});

		if (files.length < 1) {
			file1.deleteOnExit();
			noJarFound(path.getAbsolutePath());
			return;
		}

		fileContent.append(VersionInfo);
		fileContent.append(ClassPathStart);

		for (File f : files) {
			String p = f.toURI().normalize().toString();
			fileContent.append(ClassPathLineStart);
			if (p.length() > MaxLineLength) {
				int start = 0;
				int end = MaxLineLength;
				while (end < p.length()) {
					fileContent.append(p.substring(start, end));
					fileContent.append(Continuation);
					start = end;
					end += MaxLineLength;
				}
				fileContent.append(p.substring(start, p.length()));
			} else {
				fileContent.append(p);
			}
		}

		fileContent.append(EndOfFile);

		manifest.put(ManifestPath, fileContent.toString().getBytes());
		new ClassPathExtender()
				.createJar(new FileOutputStream(file1), manifest);
		doneUsage(file1.getAbsolutePath());
	}

	/**
	 * Writes the JAR file.
	 * 
	 * @param outStream
	 *            The file output stream were to write the JAR.
	 * @param all
	 *            The map of all classes to output.
	 * @throws IOException
	 *             if an I/O error has occurred
	 */
	public void createJar(FileOutputStream outStream, Map<String, byte[]> all)
			throws IOException {
		JarOutputStream jar = new JarOutputStream(outStream);
		for (Entry<String, byte[]> entry : all.entrySet()) {
			String name = entry.getKey();
			JarEntry jar_entry = new JarEntry(name);
			jar.putNextEntry(jar_entry);
			jar.write(entry.getValue());
			jar.closeEntry();
		}
		jar.flush();
		jar.close();
	}

}

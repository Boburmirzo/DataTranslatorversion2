package ru.technicaltask.datavendor.dao.impl;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.technicaltask.datavendor.dao.TranslatorFilesReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation for TranslatorFilesReader
 */
@Component
public class TranslatorFilesReaderImpl implements TranslatorFilesReader {
	
	private static String DATA_FOLDER = "data/";

	@Override
	public List<String> readData(String fileName) {
		StringBuilder dataFileName = new StringBuilder(DATA_FOLDER).append(fileName);
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(dataFileName.toString()).getFile());

		List<String> result = Lists.newArrayList();
		try{
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				result.add(scanner.nextLine());
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}

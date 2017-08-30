package ru.technicaltask.datavendor.dao;

import java.util.List;

/**
 * Interface for files reader
 */
public interface TranslatorFilesReader {
	
	/** Get the file for the given fileName, read the data and return a collection representing the 
	 * collection of file lines. */
	public List<String> readData(String fileName);
	
}

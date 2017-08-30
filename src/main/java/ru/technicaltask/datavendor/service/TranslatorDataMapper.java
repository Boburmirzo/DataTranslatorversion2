package ru.technicaltask.datavendor.service;


import ru.technicaltask.datavendor.entity.TranslationData;

import java.util.List;

/**
 * This is the interface defining Data Mapper
 */
public interface TranslatorDataMapper<T> {
	
	/** Map a list of line from data file, into a TranslationData */
	public TranslationData<T> toTranslationData(List<String> data);
	
}

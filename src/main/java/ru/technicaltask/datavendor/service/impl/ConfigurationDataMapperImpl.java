package ru.technicaltask.datavendor.service.impl;


import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.technicaltask.datavendor.commons.TranslatorKeys;
import ru.technicaltask.datavendor.entity.TranslationData;
import ru.technicaltask.datavendor.service.TranslatorDataMapper;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This is TranslatorDataMapper implementation for Configuration file mapping
 */
@Component
public class ConfigurationDataMapperImpl implements TranslatorDataMapper<String> {

	@Override
	public TranslationData<String> toTranslationData(List<String> data) {
		TranslationData<String> result = new TranslationData<String>();
		Map<Integer, String> columns = Maps.newHashMap();
		for(String line:data){
			StringTokenizer tokenizer = new StringTokenizer(line, TranslatorKeys.TAB_REGEX);
			String tokenOne = tokenizer.hasMoreTokens()? tokenizer.nextToken():StringUtils.EMPTY;
			String tokenTwo = tokenizer.hasMoreTokens()? tokenizer.nextToken():StringUtils.EMPTY;
			result.append(tokenOne, tokenTwo);
		}
		return result;
	}
	
}

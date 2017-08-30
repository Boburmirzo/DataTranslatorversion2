package ru.technicaltask.datavendor.service.impl;


import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.technicaltask.datavendor.commons.TranslatorKeys;
import ru.technicaltask.datavendor.dao.TranslatorFilesReader;
import ru.technicaltask.datavendor.entity.TranslationData;
import ru.technicaltask.datavendor.service.TranslatorDataMapper;
import ru.technicaltask.datavendor.service.TranslatorService;

import java.util.List;

/**
 * This is the Translator Bean, that will read all the files and produce the output
 */
@Service
public class TranslatorServiceImpl implements TranslatorService {

	@Autowired
	private TranslatorFilesReader dataReader;

	@Autowired
	@Qualifier("vendorDeliveryDataMapperImpl")
	private TranslatorDataMapper vendorDeliveryDataMapper;

	@Autowired
	@Qualifier("configurationDataMapperImpl")
	private TranslatorDataMapper configurationDataMapper;

	@Value("${vendor-flat-data-file-name}")
	private String vendorFlatDataFileName;

	@Value("${configuration-data-file-name}")
	private String configurationDataFileName;

	@Value("${vendor-specific-file-name}")
	private String vendorSpecificFileName;
	
	@Override
	public List<String> translate(){
		// Retrieving data to translate
		List<String> vendorFlatData = dataReader.readData(vendorFlatDataFileName);
		TranslationData<TranslationData<String>> vendorTranslationData = vendorDeliveryDataMapper.toTranslationData(vendorFlatData);

		// Retrieving configuration data
		List<String> configData = dataReader.readData(configurationDataFileName);
		TranslationData<String> configTranslationData = configurationDataMapper.toTranslationData(configData);

		// Retrieving vendor specific data
		List<String> specificData = dataReader.readData(vendorSpecificFileName);
		TranslationData<String> vendorSpecificData = configurationDataMapper.toTranslationData(specificData);
		
		List<String> result = Lists.newArrayList();
		
		// Translating the files
		for(String key: vendorSpecificData.getValues().keySet()){
			String id = vendorSpecificData.getValue(key);
			StringBuilder translatedHeader = new StringBuilder();
			StringBuilder translatedValues = new StringBuilder(id);

			TranslationData<String> vendorTranslation = vendorTranslationData.getValue(key);
			for(String configKey: configTranslationData.getValues().keySet()){
				// Collecting translated Header
				appendToStringBuilder(translatedHeader, configTranslationData.getValue(configKey));

				// Collecting translated Values
				String value = vendorTranslation.getValue(configKey);
				if(StringUtils.isNotBlank(value)){
					appendToStringBuilder(translatedValues, value);
				}
			}
			result.add(translatedHeader.toString());
			result.add(translatedValues.toString());
		}

		return result;
	}
	
	private static void appendToStringBuilder(StringBuilder strBuilder, String value){
		if(strBuilder.length() != 0){
			strBuilder.append(TranslatorKeys.TAB_CHAR);
		}
		strBuilder.append(value);
	}
}

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
 * This Mapper will turn a String from Vendor Delivery Data File where words are separated via tabs, into TranslationData
 */
@Component
public class VendorDeliveryDataMapperImpl implements TranslatorDataMapper<TranslationData<String>> {
	
	@Override
	public TranslationData<TranslationData<String>> toTranslationData(List<String> data){
		TranslationData<TranslationData<String>> result = new TranslationData<TranslationData<String>>();
		boolean isFirstLine = true;
		Map<Integer, String> columns = Maps.newHashMap();
		for(String line:data){
			StringTokenizer tokenizer = new StringTokenizer(line, TranslatorKeys.TAB_REGEX);
			if(isFirstLine){
				columns = prepareColumnsReference(tokenizer);
				isFirstLine = false;
			}
			else{
				String firstToken = tokenizer.hasMoreTokens()? tokenizer.nextToken():StringUtils.EMPTY;
				TranslationData<String> details = prepareTranslationData(columns, tokenizer);
				result.append(firstToken, details);
			}
		}
		
		return result;
	}
	
	private static Map<Integer, String> prepareColumnsReference(StringTokenizer stringTokenizer){
		Map<Integer, String> columns = Maps.newHashMap();
		int i=0;
		while(stringTokenizer.hasMoreTokens()){
			String token = stringTokenizer.nextToken();
			columns.put(i,token);
			i++;
		}
		return columns;
	}
	
	private static TranslationData<String> prepareTranslationData(Map<Integer, String> columns, StringTokenizer tokenizer){
		TranslationData<String> vendorDetails = new TranslationData<String>();
		int i=1;
		while(tokenizer.hasMoreTokens()){
			String token = tokenizer.nextToken();
			vendorDetails.append(columns.get(i), token);
			i++;
		}
		return vendorDetails;
	}
	
}

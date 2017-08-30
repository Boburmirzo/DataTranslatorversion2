package ru.technicaltask.datavendor.entity;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * This class define a translation data.
 */
public class TranslationData<T> {

	private Map<String, T> values;

	public TranslationData(){
		values = Maps.newTreeMap();
	}

	public Map<String, T> getValues() {
		return values;
	}

	public T getValue(String code) {
		T value = null;
		if(StringUtils.isNotBlank(code)){
			value = values.get(StringUtils.upperCase(code));
		}
		return value;
	}

	public void append(String code, T value){
		if(StringUtils.isBlank(code)){
			return;
		}
		values.put(StringUtils.upperCase(code), value);
	}
}

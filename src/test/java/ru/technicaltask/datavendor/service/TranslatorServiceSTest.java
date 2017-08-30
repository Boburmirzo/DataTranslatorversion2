package ru.technicaltask.datavendor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test class for TranslatorService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/translator-test-context.xml")
public class TranslatorServiceSTest {

	@Autowired
	private TranslatorService translatorService;
	
	@Test
	public void shouldProperlyExecuteTranslation(){
		// Act
		List<String> translations = translatorService.translate();
		
		// Assert
		System.out.println(translations);
		assertThat(translations, not(nullValue()));
		assertThat(translations.size(), is(2));
		assertThat(translations, hasItem("OURID\tOURCOL1\tOURCOL3"));
		assertThat(translations, hasItem("OURIDXXX\tVAL21\tVAL23"));
	}
	
}

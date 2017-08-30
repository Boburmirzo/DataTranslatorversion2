package ru.technicaltask.datavendor.service;

import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.technicaltask.datavendor.entity.TranslationData;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Test class for ConfigurationDataMapperImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/translator-test-context.xml")
public class ConfigurationDataMapperTest {
	
	@Autowired
	@Qualifier("configurationDataMapperImpl")
	private TranslatorDataMapper mapper;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldThrowNPEWhenTranslationDataIsNull(){
		// Assert
		exception.expect(NullPointerException.class);

		// Act
		TranslationData<TranslationData<String>> result = mapper.toTranslationData(null);
	}

	@Test
	public void shouldReturnEmptyTranslationWhenDataIsEmpty(){
		// Act
		TranslationData<TranslationData<String>> result = mapper.toTranslationData(Lists.newArrayList());

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.getValues(), not(nullValue()));
		assertThat(result.getValues().keySet(), hasSize(0));
	}

	@Test
	public void shouldMapDataWhenGivenTranslationData(){
		// Arrange
		List<String> data = Lists.newArrayList();
		data.add("OURMMCOL0\\tOURIDMM");
		data.add("OURMMCOL1\\tVALMM21");
		data.add("OURMMCOL4\\tVALMM24");

		// Act
		TranslationData<String> result = mapper.toTranslationData(data);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.getValues(), not(nullValue()));
		assertThat(result.getValues().keySet(), hasSize(3));
		assertThat(result.getValues().keySet(), hasItem("OURMMCOL0"));
		assertThat(result.getValues().keySet(), hasItem("OURMMCOL1"));
		assertThat(result.getValues().keySet(), hasItem("OURMMCOL4"));

		assertThat(result.getValue("OURMMCOL0"), is("OURIDMM"));
		assertThat(result.getValue("OURMMCOL1"), is("VALMM21"));
		assertThat(result.getValue("OURMMCOL4"), is("VALMM24"));
	}
	
}

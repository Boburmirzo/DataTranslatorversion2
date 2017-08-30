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
 * Test for VendorDeliveryDataMapper
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/translator-test-context.xml")
public class VendorDeliveryDataMapperTest {
	
	@Autowired
	@Qualifier("vendorDeliveryDataMapperImpl")
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
		// Arrange
		List<String> data = Lists.newArrayList();

		// Act
		TranslationData<TranslationData<String>> result = mapper.toTranslationData(data);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.getValues(), not(nullValue()));
		assertThat(result.getValues().keySet(), hasSize(0));
	}

	@Test
	public void shouldMapDataWhenGivenTranslationData(){
		// Arrange
		List<String> data = Lists.newArrayList();
		data.add("OURKID\\tOURKCOL1\\tOURKCOL3");
		data.add("OURIDK\\tVALK21\\tVALK23");

		// Act
		TranslationData<TranslationData<String>> result = mapper.toTranslationData(data);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.getValues(), not(nullValue()));
		assertThat(result.getValues().keySet(), hasSize(1));
		assertThat(result.getValues().keySet(), hasItem("OURIDK"));

		TranslationData<String> resultingData = result.getValue("OURIDK");
		assertThat(resultingData, not(nullValue()));
		assertThat(resultingData.getValue("OURKCOL1"), is("VALK21"));
		assertThat(resultingData.getValue("OURKCOL3"), is("VALK23"));
	}
	
}

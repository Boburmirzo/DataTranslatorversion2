package ru.technicaltask.datavendor.dao;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Test class for TranslatorFilesReaderImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/translator-test-context.xml")
public class TranslatorFilesReaderTest {
	
	@Autowired
	private TranslatorFilesReader translatorFilesReader;

	@Value("${vendor-flat-data-file-name}")
	private String vendorFlatDataFileName;

	@Value("${configuration-data-file-name}")
	private String configurationDataFileName;

	@Value("${vendor-specific-file-name}")
	private String vendorSpecificFileName;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void shouldThrowNPEWhenfileNameIsNull(){
		// Assert
		exception.expect(NullPointerException.class);
		
		// Act
		translatorFilesReader.readData(null);
	}

	@Test
	public void shouldThrowFileNotFoundExceptionWhenfileNameIsEmpty(){
		// Act
		List<String> result = translatorFilesReader.readData(StringUtils.EMPTY);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, hasSize(0));
	}
	
	@Test
	public void shouldReadVendorFlatFile(){
		// Arrange
		String fileName = vendorFlatDataFileName;
		
		// Act
		List<String> result = translatorFilesReader.readData(fileName);
		
		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, hasSize(3));
		assertThat(result, hasItem("COL0    COL1    COL2    COL3"));
		assertThat(result, hasItem("ID1 VAL11   VAL12   VAL13"));
		assertThat(result, hasItem("ID2 VAL21   VAL22   VAL23"));
	}

	@Test
	public void shouldReadConfigurationDataFile(){
		// Arrange
		String fileName = configurationDataFileName;

		// Act
		List<String> result = translatorFilesReader.readData(fileName);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, hasSize(3));
		assertThat(result, hasItem("COL0    OURID"));
		assertThat(result, hasItem("COL1    OURCOL1"));
		assertThat(result, hasItem("COL3    OURCOL3"));
	}

	@Test
	public void shouldVendorSpecificDataFile(){
		// Arrange
		String fileName = vendorSpecificFileName;

		// Act
		List<String> result = translatorFilesReader.readData(fileName);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, hasSize(1));
		assertThat(result, hasItem("ID2 OURIDXXX"));
	}
	
}

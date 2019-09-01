package org.mac.javaanalyzer.core.out;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mac.javaanalyzer.core.AnalysisException;
import org.mac.javaanalyzer.core.TextAnalyserare;
import org.mac.javaanalyzer.core.model.Definition;
import org.mac.javaanalyzer.core.model.Funktion;
import org.mac.javaanalyzer.core.model.Kommando;
import org.mac.javaanalyzer.core.model.KommandoSekvens;
import org.mac.javaanalyzer.core.model.Metod;
import org.mac.javaanalyzer.core.model.Sekvens;
import org.mac.javaanalyzer.core.model.VariabelDefinition;

public class TestAnalysis {
	private static final String BIN_TEST_OUTPUT = "./bin/testdata/output/";
	private static final String BIN_TEST_OUTPUT_MY_FILE_XLSX = "./bin/testdata/output/myFile.xlsx";

	@BeforeClass
	public static void setupDirs() {
		File path = Paths.get(BIN_TEST_OUTPUT).toAbsolutePath().toFile();
		if (!path.exists()) {
			path.mkdirs();
		}
	}

	@Before
	public void cleanDirs() {
		File path = Paths.get(BIN_TEST_OUTPUT_MY_FILE_XLSX).toAbsolutePath().toFile();
		if (path.exists()) {
			path.delete();
		}
	}

	@Test
	public void constructor_init_shouldWork() {
		new TextAnalyserare();
	}

	@Test
	public void kommandosekvens_constructor_init_shouldWork() {
		new KommandoSekvens();
	}

	@Test
	public void kommandosekvens_getVariabelMap() {
		Sekvens kommandoSekvens = new KommandoSekvens();
		Map<String, VariabelDefinition> variabelMap = kommandoSekvens.getVariabelMap();
		Assert.assertNotNull(variabelMap);
	}

	@Test
	public void kommandosekvens_getKommandon() {
		Sekvens kommandoSekvens = new Metod();
		Definition kommandoFirst = new Definition("apa");
		kommandoFirst.setVariabelDefinition(new VariabelDefinition("java.lang.String"));

		kommandoSekvens.addKommando(kommandoFirst);
		Kommando kommandoSecond = new Definition("foo");
		kommandoSekvens.addKommando(kommandoSecond);

		Funktion kommandoThird = new Funktion();

		Definition kommandoFourth = new Definition("bar");

		kommandoThird.addArgument(kommandoFirst.getVariabelDefinition());
		kommandoThird.setCallbackDefinition(kommandoFourth);
		kommandoSekvens.addKommando(kommandoThird);
		kommandoSekvens.addKommando(kommandoFourth);

		Iterable<Kommando> kommandon = kommandoSekvens.getKommandon();
		Assert.assertNotNull(kommandon);
		Assert.assertEquals(4, kommandon.spliterator().estimateSize());
		Assert.assertEquals(3, kommandoSekvens.getVariabelMap().size());

	}

	@Test(expected = NullPointerException.class)
	public void textAnalyserare_nullText_variableDefinitionPredicate() throws AnalysisException {
		TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test(null);
	}

	@Test
	public void textAnalyserare_randomText_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v;");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_randomTextT_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(false));
	}

	@Test
	public void textAnalyserare_randomTextB_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v=;");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(false));
	}

	@Test
	public void textAnalyserare_randomTextQ_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v= new;");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(false));
	}

	@Test
	public void textAnalyserare_randomTextC_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v= new apa);");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(false));
	}

	@Test
	public void textAnalyserare_randomTextD_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v=new apa();");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_randomTextE_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v  =  new apa();");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_randomTextF_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v  =  new apa(;");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(false));
	}

	@Test
	public void textAnalyserare_randomTextG_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v = new apa();");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_randomTextH_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v = new apa(a);");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_randomTextI_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate()
				.test("jgjhgfd v = new apa(a,b,c,d,e);");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_randomTextJ_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate().test("jgjhgfd v = new apa(a,b);");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_randomTextK_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate()
				.test("jgjhgfd v = new apa(a, b , c, ff, e);");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_textWithRowBreak_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate()
				.test("jgjhgfd v = new   apa(a, b , c,\n ff,\r\n e);");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_randomTextN_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate()
				.test("jgjhgfd v = apa(a, b , c, ff, e);");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(false));
	}

	@Test
	public void textAnalyserare_randomTextO_variableDefinitionPredicate() throws AnalysisException {
		boolean kommando = TextAnalyserare.VARIABLE_DEFINITION_PATTERN.asPredicate()
				.test("jgjhgfd v = new apa(a, b , c, ff, e);");
		Assert.assertThat("Predikat returnerade ej förväntat svar", kommando, IsEqual.equalTo(true));
	}

	@Test
	public void textAnalyserare_identifieraVariabelOchKlass_variableDefinitionPredicate() throws AnalysisException {
		TextAnalyserare analyserare = new TextAnalyserare();
		String text = "jgjhgfd v = new   apa(a, b , c,\n ff,\r\n e);";
		Kommando kommando = analyserare.parse(text);
		Definition definition = (Definition) kommando;
		Assert.assertThat(String.format("Variabelnamn identifierades inte rätt för '%s'", text), definition.getKey(),
				IsEqual.equalTo("v"));
		Assert.assertThat(String.format("Klassnamn identifierades inte rätt för '%s'", text), definition.getKey(),
				IsEqual.equalTo("jgjhgfd"));
	}
}

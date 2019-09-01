package org.mac.javaanalyzer.core.out;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.SubstringMatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mac.processflow.core.JavaFileReader;
import org.mac.processflow.core.KlassDefinition;
import org.mac.processflow.core.SektionX;

public class TestFileRead {

	@Test
	public void test() throws IOException {

		URL uri = JavaFileReader.class.getClassLoader().getResource("org/mac/processflow/testdata.file");

		KlassDefinition obj = JavaFileReader.readFromFile(new File(uri.getFile()));
		List<SektionX> sektion = obj.getImports();

		//Imports och package
		Assert.assertThat(sektion.size(), IsEqual.equalTo(19));
		
		assertListContains(sektion, StringContains.containsString("package org.springframework.boot.devtools"));
		assertListContains(sektion, StringContains.containsString("import java.util.ArrayList"));
		assertListContains(sektion, StringContains.containsString("import java.util.Collection"));
		assertListContains(sektion, StringContains.containsString("import java.util.List"));
		assertListContains(sektion, StringContains.containsString("import org.springframework.boot.Banner"));
		assertListContains(sektion, StringContains.containsString("import org.springframework.boot.ResourceBanner"));
		assertListContains(sektion, StringContains.containsString("import org.springframework.boot.SpringApplication"));
		assertListContains(sektion,
				StringContains.containsString("import org.springframework.boot.WebApplicationType"));
		assertListContains(sektion, StringContains
				.containsString("import org.springframework.boot.context.config.AnsiOutputApplicationListener"));
		assertListContains(sektion, StringContains
				.containsString("import org.springframework.boot.context.config.ConfigFileApplicationListener"));
		assertListContains(sektion, StringContains
				.containsString("import org.springframework.boot.context.logging.ClasspathLoggingApplicationListener"));
		assertListContains(sektion, StringContains
				.containsString("import org.springframework.boot.context.logging.LoggingApplicationListener"));
		assertListContains(sektion, StringContains
				.containsString("import org.springframework.boot.devtools.remote.client.RemoteClientConfiguration"));
		assertListContains(sektion,
				StringContains.containsString("import org.springframework.boot.devtools.restart.RestartInitializer"));
		assertListContains(sektion, StringContains
				.containsString("import org.springframework.boot.devtools.restart.RestartScopeInitializer"));
		assertListContains(sektion,
				StringContains.containsString("import org.springframework.boot.devtools.restart.Restarter"));
		assertListContains(sektion,
				StringContains.containsString("import org.springframework.context.ApplicationContextInitializer"));
		assertListContains(sektion,
				StringContains.containsString("import org.springframework.context.ApplicationListener"));
		assertListContains(sektion,
				StringContains.containsString("import org.springframework.core.io.ClassPathResource"));

		//Klass
		Assert.assertThat(obj.getText(), IsEqual.equalTo("APA"));
		
	}

	private static void assertListContains(List<SektionX> items, Matcher<String> matcher) {
		Assert.assertTrue("Hittade inte sträng i lista", items.stream().anyMatch(s -> matcher.matches(s.getText())));
	}

}

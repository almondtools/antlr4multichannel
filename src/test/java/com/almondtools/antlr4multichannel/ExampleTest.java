package com.almondtools.antlr4multichannel;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.junit.Test;

import com.almondtools.antlr4multichannel.ExampleParser.TemplateContext;


public class ExampleTest {

	@Test
	public void testEnable() throws Exception {
		ExampleParser parser = new ExampleParser(new MultiChannelTokenStream(new ExampleLexer(new ANTLRInputStream(
			"{\nthis is text\n@f1( f2( { this too} ))\ntext after\n}" 
			))));
		TemplateContext template = parser.template();
		String text = template.getText();
		assertThat("whitespace in templates should be preserved", text, containsString("{\nthis is text\n"));
		assertThat("whitespace in templates should be preserved", text, containsString("{ this too}"));
		assertThat("whitespace in templates should be preserved", text, containsString("\ntext after\n}"));
		assertThat("whitespace in code should be ignored", text, containsString("@f1(f2({ this too}))"));
	}

}

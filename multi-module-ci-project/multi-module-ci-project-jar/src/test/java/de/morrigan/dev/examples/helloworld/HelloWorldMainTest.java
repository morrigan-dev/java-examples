package de.morrigan.dev.examples.helloworld;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

public class HelloWorldMainTest {

	private static ListAppender appender;

	@ClassRule
	public static LoggerContextRule init = new LoggerContextRule("log4j2-test.xml");

	@BeforeClass
	public static void setupLogging() {
		appender = init.getListAppender("List");
	}

	@Before
	public void setUp() {
		appender.clear();
	}

//	@Test
	public void testMainStartsProgramm() {
		HelloWorldMain.main(null);

		List<LogEvent> logEvents = appender.getEvents();
		List<String> logMessages = logEvents.stream()
				.map(event -> event.getMessage().getFormattedMessage())
				.collect(Collectors.toList());

		assertThat(logMessages.get(0), is(equalTo("Programm startet")));
		assertThat(logMessages.get(logMessages.size()-1), is(equalTo("Programm beendet")));
	}
}

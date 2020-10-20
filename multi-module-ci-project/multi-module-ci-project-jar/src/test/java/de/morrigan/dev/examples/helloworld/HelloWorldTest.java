package de.morrigan.dev.examples.helloworld;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldTest {

	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(HelloWorldTest.class);
	
	private static ListAppender appender;

	@ClassRule
	public static LoggerContextRule init = new LoggerContextRule("log4j2-test.xml");

	@BeforeClass
	public static void setupLogging() {
		appender = init.getListAppender("List");
	}

	/* Programmteil, der getestet wird */
	private HelloWorld sut;

	@Before
	public void setUp() {
		sut = HelloWorld.Factory.create();
		appender.clear();
		appender.countDownLatch = new CountDownLatch(1);
	}

	@After
	public void tearDown() {
		sut = null;
	}

	@Test
	public void testPrintHelloWorldLogDifferentLevelSuccessfully() throws Exception {
		sut.printHelloWorld();
		appender.countDownLatch.await(1, TimeUnit.SECONDS);

		List<LogEvent> logEvents = appender.getEvents();
		Map<String, Integer> loglevels = new HashMap<String, Integer>();
		for (LogEvent event : logEvents) {
			String logLevel = event.getLevel().name();
			loglevels.merge(logLevel, 1, Integer::sum); // Zähle Vorkommen der verschiedenen Log-Level
		}
		List<String> logMessages = logEvents.stream()
				.map(event -> event.getMessage().getFormattedMessage())
				.collect(Collectors.toList());

		assertThat(loglevels.size(), is(equalTo(5)));
		assertThat(loglevels.values(), everyItem(equalTo(1)));
		assertThat(loglevels.keySet(), containsInAnyOrder("ERROR", "WARN", "INFO", "DEBUG", "TRACE"));
		assertThat(logMessages, everyItem(containsString("Hello World!")));
	}

	@Test
	public void testGetHelloWorldReturnsCorrectMessage() {
		String expected = "Hello World!";
		String actual = sut.getHelloWorld();

		assertEquals(expected, actual);
	}
}

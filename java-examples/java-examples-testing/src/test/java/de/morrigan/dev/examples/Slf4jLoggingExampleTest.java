package de.morrigan.dev.examples;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public class Slf4jLoggingExampleTest {

  private static ListAppender appender;

  @ClassRule
  public static LoggerContextRule init = new LoggerContextRule("log4j2-test.xml");

  @BeforeClass
  public static void setupLogging() {
    appender = init.getListAppender("List");
  }

  /* Programmteil, der getestet wird */
  private Slf4jLoggingExample sut;

  @Before
  public void setUp() {
    sut = Slf4jLoggingExample.Factory.create();
    appender.clear();
  }

  @After
  public void tearDown() {
    sut = null;
  }

  @Test
  public void testMainStartsProgramm() throws Exception {
    Slf4jLoggingExample.main(null);

    List<LogEvent> logEvents = appender.getEvents();
    List<String> logMessages = logEvents.stream()
        .map(event -> event.getMessage().getFormattedMessage())
        .collect(Collectors.toList());

    assertThat(logMessages.get(0), is(equalTo("Programm startet")));
    assertThat(logMessages.get(logMessages.size() - 1), is(equalTo("Programm beendet")));
  }

  @Test
  public void testPrintHelloWorldLogDifferentLevelSuccessfully() throws Exception {
    sut.printHelloWorld();

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

package de.morrigan.dev.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Einfache Hello-World Beispielklasse für dieses Projekt, welche die Meldung "Hello World!" auf unterschiedliche Arten
 * ausgibt.
 * 
 * @author morrigan
 * @since 0.0.1
 */
public class Slf4jLoggingExample {

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(Slf4jLoggingExample.class);

  private static final String MSG = "Hello World!";

  /**
   * Programmeinsteig, der die Ausgabe einer "Hello World!" Nachricht anstößt.
   * 
   * @param args Es sind keine Programmargumente vorhanden
   */
  public static void main(String[] args) {
    LOG.info("Programm startet");
    Slf4jLoggingExample helloWorld = Slf4jLoggingExample.Factory.create();
    helloWorld.printHelloWorld();
    LOG.info("Programm beendet");
  }

  /**
   * Factory Klasse zur Erzeugung einer {@link Slf4jLoggingExample} Instanz.
   * 
   * @author morrigan
   * @since 0.0.1
   */
  public static class Factory {

    /**
     * Erzeugt eine neue Instanz der {@link Slf4jLoggingExample} Klasse.
     * 
     * @return eine neue Instanz der Klasse
     */
    public static Slf4jLoggingExample create() {
      return new Slf4jLoggingExample();
    }

    /* Statische Factory Klasse */
    private Factory() {
      super();
    }
  }

  /* Erzeugung dieser Klasse soll immer über die Factory geschehen! */
  private Slf4jLoggingExample() {
    super();
  }

  /**
   * Gibt eine Nachricht über einen Logger auf verschiedenen Log-Level Stufen aus.
   */
  public void printHelloWorld() {
    LOG.error(MSG);
    LOG.warn(MSG);
    LOG.info(MSG);
    LOG.debug(MSG);
    LOG.trace(MSG);
  }

  /**
   * Liefert eine Nachricht als Text zurück.
   * 
   * @return eine Nachricht
   */
  public String getHelloWorld() {
    return MSG;
  }
}

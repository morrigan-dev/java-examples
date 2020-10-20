package de.morrigan.dev.examples.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Einfache Hello-World Beispielklasse für dieses Projekt, welche die Meldung "Hello World!" auf unterschiedliche Arten
 * ausgibt.
 * 
 * @author morrigan
 * @since 0.0.1
 */
public class HelloWorldMain {
	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(HelloWorldMain.class);

	/**
	 * Programmeinsteig, der die Ausgabe einer "Hello World!" Nachricht anstößt.
	 * 
	 * @param args Es sind keine Programmargumente vorhanden
	 */
	public static void main(String[] args) {
		LOG.info("Programm startet");
		HelloWorld helloWorld = HelloWorld.Factory.create();
		helloWorld.printHelloWorld();
		LOG.info("Programm beendet");
	}
	
	/* Klasse dient lediglich für den Programmstart */
	private HelloWorldMain() {
		super();
	}
}

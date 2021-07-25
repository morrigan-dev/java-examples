package de.morrigan.dev.examples.mina.sshd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Einfache Hello-World Beispielklasse für dieses Projekt, welche die Meldung "Hello World!" auf unterschiedliche Arten
 * ausgibt.
 * 
 * @author morrigan
 * @since 0.0.1
 */
public class HelloWorld {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

	private static final String MSG = "Hello World!";

	/**
	 * Factory Klasse zur Erzeugung einer {@link HelloWorld} Instanz.
	 * 
	 * @author morrigan
	 * @since 0.0.1
	 */
	public static class Factory {

		/**
		 * Erzeugt eine neue Instanz der {@link HelloWorld} Klasse.
		 * 
		 * @return eine neue Instanz der Klasse
		 */
		public static HelloWorld create() {
			return new HelloWorld();
		}
		
		/* Statische Factory Klasse */
		private Factory() {
			super();
		}
	}

	/* Erzeugung dieser Klasse soll immer über die Factory geschehen! */
	private HelloWorld() {
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

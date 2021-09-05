package de.morrigan.dev.examples.mina.sshd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SshConnectionTest {

  @Test
  public void testConstruction() {
    SshConnection sut = new SshConnection("hostname", "username", "password", 42);

    assertEquals("hostname", sut.getHostname());
    assertEquals("username", sut.getUsername());
    assertEquals("password", sut.getPassword());
    assertEquals(42, sut.getPort());
  }
}
